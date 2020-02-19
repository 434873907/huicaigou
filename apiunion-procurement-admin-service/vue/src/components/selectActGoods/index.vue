<template>
  <div class="select-goods">
    <el-dialog title="选择商品" :visible.sync="showGoodsModal" width="670px" :before-close="cancelGoods">
      <div class="dialog-form">
        <div class="dialog-search">
          <select class="form-control" v-model="listParams.brandId" :class="{'unset-select':!listParams.brandId}">
            <option value="">品牌选择</option>
            <option v-for="item in brandList" :value="item.id" :key="item.id">{{item.name}}</option>
          </select>
          <select class="form-control" v-model="listParams.cateId" :class="{'unset-select':!listParams.cateId}">
            <option value="">类目选择</option>
            <option v-for="item in cateList" :value="item.id" :key="item.id">{{item.name}}</option>
          </select>
          <div class="input-box">
            <input type="text" class="form-control" placeholder="商品名/商品ID/条形码" v-model="listParams.keyword" @keyup.enter="getList" />
            <i class="el-icon-search" @click="getList()"></i>
          </div>
          <a href="javascript:;" class="toggle-btn" v-show="toggleAction == 1" @click="toggleAction = 2">已选择商品</a>
          <a href="javascript:;" class="toggle-btn" v-show="toggleAction == 2" @click="toggleAction = 1">全部商品</a>
        </div>
        <div class="goods-title">
          <span class="goods" :class="{'single-channel-goods':userConfig.singleChannel}">{{toggleAction == 2 ? '已选' : ''}}商品</span>
          <span class="channel" v-show="!userConfig.singleChannel">渠道</span>
          <span class="spec">规格</span>
          <span class="oprate">操作</span>
        </div>

        <div class="goods-item" :class="{'selected-item':item.checked}" v-for="item in tableData" :key="item.itemId" v-show="toggleAction == 1">
          <div class="item-img">
            <img :src="item.mainImg" />
          </div>
          <div class="item-name" :class="{'single-channel-name':userConfig.singleChannel}">
            <h2>{{item.itemName}}</h2>
            <p>商品编号：{{item.itemId}}</p>
          </div>
          <div class="item-channel" v-show="!userConfig.singleChannel">
            <select class="form-control" v-model="item.selectedChannel" @change="changeChannel(item)">
              <option v-for="channel in item.channelInfoList" :key="channel.channelType" :value="channel.channelType">{{channel.channelName}}</option>
            </select>
          </div>
          <div class="item-spec">
            <a class="link spec-btn" @click="item.showSku=!item.showSku">选择规格</a>
          </div>
          <div class="item-oprate">
            <a class="link" @click="selectItem(item)" v-show="!item.checked">选取</a>
            <span class="tips" v-show="item.checked">已选取</span>
          </div>
          <div class="sku-inner" v-show="item.showSku">
            <h5>规格选择</h5>
            <div class="sku-box">
              <span class="sku-item" v-for="sku in item.skuList" :key="sku.skuId" :class="{'select-sku':sku.checked}" @click="selectSku(item, sku)">{{sku.skuValue}}</span>
            </div>
          </div>
        </div>
        <pagination v-show="total>0 && toggleAction == 1" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList()" />
        
        <div class="selected-goods" v-show="toggleAction == 2">
          <div class="goods-item" v-for="(item, index) in selectedGoodsList" :key="item.itemId">
            <div class="item-img">
              <img :src="item.mainImg" />
            </div>
            <div class="item-name" :class="{'single-channel-name':userConfig.singleChannel}">
              <h2>{{item.itemName}}</h2>
              <p>商品编号：{{item.itemId}}</p>
            </div>
            <div class="item-channel" v-show="!userConfig.singleChannel">
              <span class="text">{{item.selectedChannelName}}</span>
            </div>
            <div class="item-spec">
              <span class="text" v-for="(s, s_index) in item.selectedSku" :key="s.skuId">{{s.skuValue}}<i v-show="s_index<item.selectedSku.length-1">、</i></span>
            </div>
            <div class="item-oprate">
              <a class="link" @click="removeItem(item, index)">删除</a>
            </div>
          </div>
        </div>

      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="exportGoods()">确认添加</button>
        <button class="btn-fill-grey-main" @click="cancelGoods()">取消</button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/pagination';
export default {
  props: {
    presell: {
      default: 0
    },
    gift: {
      default: 0
    },
    type: {
      default: ''
    }
  },
  data() {
    return {
      showGoodsModal: true,
      toggleAction: 1,
      cateList: [],
      brandList: [],
      tableData: [],
      total: 0,
      listParams: {
        limit:10,
        page:1,
        brandId:'',
        cateId:'',
        keyword:''
      },
      selectedGoodsList: []
    }
  },
  created() {
    this.listParams.presell = this.presell;
    this.listParams.gift = this.gift;
    this.init();
  },
  methods: {
    init() {
      this.getBrandList();
      this.getCateList();
      this.getList();
    },
    getBrandList() {
      this.api.brand.letterList({}).then((res) => {
        this.brandList = res.data;
      });
    },
    getCateList() {
      this.api.category.categoryNode({parentId: 0}).then((res) => {
        this.cateList = res.data;
      });
    },
    getList() {
      let params = this.copy(this.listParams);
      if(!params.brandId) {
        delete params.brandId;
      }
      if(!params.cateId) {
        delete params.cateId;
      }
      this.api.goods.actGoods(params).then((res) => {
        this.tableData = res.data.dataList;
        this.total = res.data.total;
        this.tableData.forEach(item => {
          if(item.channelInfoList && item.channelInfoList.length>0) {
            item.channelInfoList.forEach(channel => {
              if(channel.skuInfoList && channel.skuInfoList.length>0) {
                channel.skuInfoList.forEach(s => {
                  this.$set(s, 'checked', false);
                });
              }
            });
            this.$set(item, 'selectedChannel', item.channelInfoList[0].channelType);
            this.$set(item, 'selectedChannelName', item.channelInfoList[0].channelName);
            this.$set(item, 'selectedChannelPrice', item.channelInfoList[0].price);
            this.$set(item, 'skuList', item.channelInfoList[0].skuInfoList);
            this.$set(item, 'showSku', false);
            this.$set(item, 'selectedSku', []);
          }
        });
        if(this.selectedGoodsList.length>0) {
          this.tableData.forEach(goods => {
            this.selectedGoodsList.forEach(selected => {
              if(goods.itemId === selected.itemId) {
                goods.checked = true;
              }
            });
          });
        }
      });
    },
    selectSku(item, sku) {
      sku.checked = !sku.checked;
      if(sku.checked) {
        item.selectedSku.push(sku);
      } else {
        item.selectedSku.forEach((s, i) => {
          if(s.skuId === sku.skuId) {
            item.selectedSku.splice(i, 1);
          }
        });
      }
      if(item.checked) {
        item.checked = false;
        let pos = 0;
        this.selectedGoodsList.forEach((select, select_index) => {
          if(select.itemId === item.itemId) {
            pos = select_index;
          }
        });
        if(pos || pos === 0) {
          this.selectedGoodsList.splice(pos, 1);
        }
      }
    },
    selectItem(item) {
      // 检测是否切换了渠道
      let changedSku = [];
      item.channelInfoList.forEach(channel => {
        if(item.selectedChannel === channel.channelType) {
          channel.skuInfoList.forEach(sku => {
            if(sku.checked) {
              changedSku.push(sku);
            }
          });
          item.selectedSku = changedSku;
        }
      });
      if(item.selectedSku.length === 0) {
        this.$message.info({message: '请选择规格', showClose: true});
        return
      }
      // 检测是否已选择
      let exist = false;
      this.selectedGoodsList.forEach(goods => {
        if(item.itemId === goods.itemId) {
          exist = true;
        }
      });
      if(!exist) {
        this.$message.success({message:'添加成功', showClose:true});
        this.selectedGoodsList.push(item);
        item.checked = true;
      } else {
        this.$message.info({message:'不可添加相同的商品', showClose:true});
      }
    },
    changeChannel(item) {
      if(item.channelInfoList) {
        let skuList = [];
        item.channelInfoList.forEach(channel => {
          if(item.selectedChannel === channel.channelType) {
            skuList = channel.skuInfoList;
          }
        });
        item.skuList = skuList;
        item.skuList.forEach(sku => {
          sku.checked = false;
        });
      }
    },
    removeItem(item, index) {
      this.selectedGoodsList.splice(index, 1);
      this.tableData.forEach(goods => {
        if(item.itemId === goods.itemId) {
          goods.checked = false;
        }
      });
    },
    exportGoods() {
      if(this.type && this.selectedGoodsList.length>1) {
        switch (this.type) {
          case 'group':
            this.$message.info({message: '团购只能选择一种商品', showClose: true});
            break
        }
        return
      }
      this.$emit('dialogGoods', { goodsList: this.selectedGoodsList });
    },
    cancelGoods() {
      this.showGoodsModal = true;
      this.$emit('dialogGoodsCancel');
    }
  },
  components: {
    Pagination
  }
}
</script>

<style lang="scss">
.select-goods{
  .el-dialog__body{
    padding: 0;
    .dialog-form{
      padding: 0;
    }
  }
  .dialog-form{
    padding: 0;
    .pagination-container{
      padding: 10px 16px;
    }
    .dialog-search{
      background: #F5F5F5;
      padding: 10px 20px;
      border-bottom: 1px solid $border-color;
      .form-control{
        width: 120px;
        min-width: 120px;
        margin-right: 10px;
        background: #fff;
      }
      .input-box{
        display: inline-block;
        vertical-align: top;
        position: relative;
        .form-control{
          width: 260px;
        }
        .el-icon-search{
          position: absolute;
          right: 18px;
          top: 7px;
          cursor: pointer;
        }
      }
      .toggle-btn{
        display: inline-block;
        vertical-align: top;
        background: $org-color;
        color: #fff;
        width: 90px;
        height: 28px;
        border-radius: 4px;
        text-align: center;
        line-height: 28px;
      }
    }
    .goods-title{
      border-bottom: 1px solid $border-color;
      .goods{
        display: inline-block;
        vertical-align: top;
        width: 300px;
        font-size: 14px;
        color: #666666;
        line-height: 42px;
        margin-left: 20px;
        font-weight: bold;
      }
      .single-channel-goods{
        width: 420px;
      }
      .channel{
        display: inline-block;
        vertical-align: top;
        width: 128px;
        font-size: 14px;
        color: #666666;
        line-height: 42px;
        margin-left: 20px;
        font-weight: bold;
      }
      .spec{
        display: inline-block;
        vertical-align: top;
        width: 87px;
        font-size: 14px;
        color: #666666;
        line-height: 42px;
        margin-left: 20px;
        font-weight: bold;
      }
      .oprate{
        display: inline-block;
        vertical-align: top;
        font-size: 14px;
        color: #666666;
        line-height: 42px;
        font-weight: bold;
        margin-left: 20px;
      }
    }
    .selected-item{
      background: #eee;
    }
    .goods-item{
      padding: 10px 20px;
      border-bottom: 1px solid $border-color;
      .item-img{
        display: inline-block;
        vertical-align: top;
        img{
          width: 54px;
          height: 54px;
          border-radius: 4px;
          border: 1px solid $border-color;
        }
      }
      .item-name{
        display: inline-block;
        vertical-align: top;
        width: 230px;
        margin-left: 20px;
        h2{
          font-size: 12px;
          line-height: 16px;
          color: #333333;
          font-weight: bold;
        }
        p{
          color: #666666;
          font-size: 12px;
          padding-top: 7px;
        }
      }
      .single-channel-name{
        width: 350px;
      }
      .item-channel{
        display: inline-block;
        vertical-align: top;
        padding-left: 15px;
        .form-control{
          width: 130px;
          min-width: 130px;
        }
      }
      .item-spec{
        display: inline-block;
        vertical-align: top;
        padding-left: 20px;
        .spec-btn{
          display: inline-block;
          vertical-align: top;
          color: #F06941;
          border: 1px dashed #F06941;
          border-radius: 15px;
          width: 80px;
          line-height: 26px;
          text-align: center;
        }
      }
      .item-oprate{
        display: inline-block;
        vertical-align: top;
        margin-left: 28px;
        padding-top: 3px;
        .link{
          color: $link-color;
          font-size: 14px;
          line-height: 24px;
        }
        .tips{
          line-height: 24px;
          color: #999;
        }
      }
      .sku-inner{
        margin-top: 10px;
        padding: 10px 20px;
        background: #F5F8FC;
        h5{
          font-size: 14px;
        }
        .sku-box{
          .sku-item{
            display: inline-block;
            vertical-align: top;
            padding: 5px 20px;
            color: #333;
            border: 1px dashed #999999;
            background: #fff;
            border-radius: 4px;
            margin-top: 10px;
            margin-right: 10px;
            cursor: pointer;
          }
          .select-sku{
            color: #F06941;
            border: 1px dashed #F06941;
          }
        }
      }
    }
    .selected-goods{
      .goods-item{
        .item-channel{
          display: inline-block;
          vertical-align: top;
          padding-left: 15px;
          width: 150px;
        }
        .single-channel-name{
          width: 350px;
        }
        .item-spec{
          display: inline-block;
          vertical-align: top;
          padding-left: 15px;
          width: 95px;
        }
        .text{
          line-height: 16px;
        }
      }
    }
  }
}
</style>
