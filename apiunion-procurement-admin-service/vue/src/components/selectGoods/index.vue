<template>
  <div class="select-goods">
    <el-dialog title="添加商品" :visible.sync="showGoodsModal" width="610px" :before-close="cancelGoods">
      <div class="dialog-form">
        <div class="dialog-search">
          <select class="form-control" v-model="listParams.brandIds" :class="{'unset-select':!listParams.brandIds}">
            <option value="">品牌选择</option>
            <option v-for="item in brandList" :value="item.id" :key="item.id">{{item.name}}</option>
          </select>
          <select class="form-control" v-model="listParams.cateId" :class="{'unset-select':!listParams.cateId}">
            <option value="">类目选择</option>
            <option v-for="item in cateList" :value="item.id" :key="item.id">{{item.name}}</option>
          </select>
          <div class="input-box">
            <input type="text" class="form-control" placeholder="商品名称、编码或条形码" v-model="listParams.keyword" />
            <i class="el-icon-search" @click="getList()"></i>
          </div>
          <a href="javascript:;" class="toggle-btn" v-show="toggleAction == 1" @click="toggleAction = 2">已选择商品</a>
          <a href="javascript:;" class="toggle-btn" v-show="toggleAction == 2" @click="toggleAction = 1">全部商品</a>
        </div>
        <div class="goods-title">
          <span class="goods">{{toggleAction == 2 ? '已选' : ''}}商品</span>
          <span class="oprate">操作</span>
        </div>

        <div class="goods-item" :class="{'selected-item':item.checked}" v-for="item in tableData" :key="item.id" v-show="toggleAction == 1">
          <div class="item-img">
            <img :src="item.mainImageUrl" />
          </div>
          <div class="item-name">
            <h2>{{item.name}}</h2>
            <p>商品编号：{{item.id}}</p>
          </div>
          <div class="item-oprate">
            <a class="link" @click="selectItem(item)">选取</a>
          </div>
        </div>
        <pagination v-show="total>0 && toggleAction == 1" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList()" />
        
        <div class="selected-goods" v-show="toggleAction == 2">
          <div class="goods-item" v-for="(item, index) in selectedGoodsList" :key="item.id">
            <div class="item-img">
              <img :src="item.mainImageUrl" />
            </div>
            <div class="item-name">
              <h2>{{item.name}}</h2>
              <p>商品编号：{{item.id}}</p>
            </div>
            <div class="item-oprate">
              <a class="link" @click="removeItem(index)">删除</a>
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
  data() {
    return {
      showGoodsModal: true,
      toggleAction: 1,
      cateList: [],
      brandList: [],
      tableData: [],
      total: 0,
      listParams: {
        // status:1,
        statusList: [1],
        limit:10,
        page:1,
        brandIds:'',
        cateId:''
      },
      selectedGoodsList: []
    }
  },
  created() {
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
      this.api.goods.list(this.listParams).then((res) => {
        this.tableData = res.data.dataList;
        this.total = res.data.total;
        if(this.selectedGoodsList.length>0) {
          this.tableData.forEach(goods => {
            this.selectedGoodsList.forEach(selected => {
              if(goods.id === selected.id) {
                goods.checked = true;
              }
            });
          });
        }
      });
    },
    selectItem(item) {
      let exist = false;
      this.selectedGoodsList.forEach(goods => {
        if(item.id === goods.id) {
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
    removeItem(index) {
      this.selectedGoodsList.splice(index, 1);
    },
    exportGoods() {
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

<style lang="scss" scoped>
.select-goods{
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
          width: 206px;
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
        width: 480px;
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
      }
    }
    .selected-item{
      background: #eee;
    }
    .goods-item{
      margin: 0 20px;
      padding: 10px 0;
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
        width: 370px;
        margin-left: 28px;
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
      .item-oprate{
        display: inline-block;
        vertical-align: top;
        margin-left: 28px;
        padding-top: 3px;
        a{
          color: $link-color;
          font-size: 14px;
        }
      }
    }
  }
}
</style>

