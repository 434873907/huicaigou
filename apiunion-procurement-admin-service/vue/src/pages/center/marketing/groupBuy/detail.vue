<template>
  <div class="buy-limit-detail">
    <h2 class="big-title">编辑活动</h2>
    <div class="wrap">
      <div class="form-wrap">
        <div class="form-name">团购名称:</div>
        <input type="text" class="form-control" v-model="detailData.name">
      </div>
      <div class="form-wrap">
        <div class="form-name">活动时间:</div>
        <el-date-picker class="timepicker-control" size="small" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择开始时间" v-model="detailData.startTime"></el-date-picker>
        <el-date-picker class="timepicker-control" size="small" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择结束时间" v-model="detailData.endTime"></el-date-picker>
      </div>
      <div class="form-wrap">
        <div class="form-name">海报图片:</div>
        <upload class="banner" uploadType='2' v-on:uploadSuccess="uploadSuccess">
          <a class="btn-main" v-show="!detailData.banner">上传</a>
          <img :src="detailData.banner" v-show="detailData.banner"/>
        </upload>
      </div>
      <div class="form-wrap">
        <div class="form-name">禁用优惠券:</div>
        <el-radio name="disabled" v-model="detailData.disableCoupons" :label="1">是</el-radio>
        <el-radio name="disabled" v-model="detailData.disableCoupons" :label="0">否</el-radio>
      </div>
      <div class="form-wrap">
        <div class="form-name">团购商品:</div>
        <a class="select-goods" @click="showGoodsModal=true">+ 选择商品</a>
      </div>
      <div class="form-wrap">
        <div class="form-name">商品信息:</div>
      </div>
      <div class="goods-list">
        <table>
          <thead>
            <tr>
              <th class="w300">商品名称</th>
              <th class="w150" v-show="!userConfig.singleChannel">渠道</th>
              <th class="w150">SKU</th>
              <th class="w100">库存</th>
              <th class="w100">原价</th>
              <!-- <th>剩余库存</th> -->
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in itemData.items" :key="item.skuId">
              <td :rowspan="itemData.items.length" v-if="index==0" class="item-name">
                  <img :src="itemData.mainImg" />
                  <span>{{itemData.itemName}}</span>
              </td>
              <td :rowspan="itemData.items.length" v-if="!userConfig.singleChannel&&index==0">{{itemData.channelName}}</td>
              <td>{{item.skuValue}}</td>
              <td>{{item.stockNum}}</td>
              <td>{{item.price}}</td>
              <td>
                <a class="link" @click="removeSku(index)">删除</a>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="form-wrap">
        <div class="form-name">货币:</div>
        <select class="form-control" v-model="detailData.currency" :class="{'unset-select':!detailData.currency}">
          <option :value="0">请选择</option>
          <option v-for="item in currencyList" :key="item.code" :value="item.code">{{item.mixName}}</option>
        </select>
      </div>
      <div class="form-wrap">
        <div class="form-name">团购价:</div>
        <ApiInput v-model="detailData.groupPrice"/>
      </div>
      <div class="form-wrap">
        <div class="form-name">团购库存:</div>
        <ApiInput v-model="detailData.stockNum"/>
      </div>
      <div class="form-wrap">
        <div class="form-name">是否限购:</div>
        <el-radio name="disabled" v-model="detailData.limit" :label="1">是</el-radio>
        <el-radio name="disabled" v-model="detailData.limit" :label="0">否</el-radio>
      </div>
      <div class="form-wrap" v-show="detailData.limit">
        <div class="form-name">限购数量:</div>
        <ApiInput v-model="detailData.limitNum"/>
      </div>
      <div class="btn-main" @click="save()">保存</div>
    </div>
    <selectActGoods v-if="showGoodsModal" :type="'group'" v-on:dialogGoods="dialogGoods" v-on:dialogGoodsCancel="dialogGoodsCancel"></selectActGoods>
  </div>
</template>

<script>
import selectActGoods from '@/components/selectActGoods/index.vue';
export default {
  components: { selectActGoods },
  data() {
    return {
      showGoodsModal: false,
      itemData:{
        itemName: '',
        mainImg: '',
        channelName: '',
        items: []
      },
      currencyList:[],
      detailData: {
        name:'',
        startTime:'',
        endTime:'',
        banner:'',
        itemId:'',
        skuIds:[],
        channelType:'',
        groupPrice:'',
        stockNum:'',
        limit:0,
        limitNum:'',
        currency:0,
        disableCoupons:0
      }
    }
  },
  created() {
    let id = this.$route.params.id;
    this.getCurrency();
    if(id){
      this.api.groupBuy.get({id:id}).then((res) => {
        this.detailData.name =  this.extend(this.detailData ,res.data);
        this.itemData = {
          itemName: res.data.itemName,
          mainImg: res.data.itemImage,
          channelName: res.data.channelName,
          items: []
        }
        res.data.skuInfoList.forEach(sku => {
          this.itemData.items.push({
            skuId: sku.skuId,
            skuValue: sku.skuValue,
            stockNum: sku.stock,
            price: sku.price || sku.originalPrice
          });
        });
      });
    }
  },
  methods: {
    getCurrency() {
      this.api.common.currency({}).then((res) => {
        this.currencyList = res.data;
        this.currencyList.forEach(item => {
          item.mixName = item.symbol + ' ' + item.name;
        });
      });
    },
    dialogGoods(res) {
      this.itemData = {
        itemName: '',
        mainImg: '',
        channelName: '',
        items: []
      };
      res.goodsList.forEach(item => {
        this.detailData.itemId = item.itemId;
        this.detailData.skuIds = [];
        this.detailData.channelType = item.selectedChannel;
        this.itemData.mainImg = item.mainImg;
        this.itemData.itemName = item.itemName;
        this.itemData.channelName = item.selectedChannelName;
        item.selectedSku.forEach(sku => {
          this.itemData.items.push({
            skuId: sku.skuId,
            skuValue: sku.skuValue,
            stockNum: sku.stockNum,
            price: sku.price
          });
        });
      });
      this.showGoodsModal = false;
    },
    dialogGoodsCancel() {
      this.showGoodsModal = false;
    },
    uploadSuccess(res) {
      this.detailData.banner = res.data;
    },
    removeSku(index) {
      this.itemData.items.splice(index, 1);
    },
    save(){
      let params = this.copy(this.detailData);
      if(!params.itemId) {
        this.$message.info({message: '请选择商品', showClose: true});
        return
      }
      if(!params.limit) {
        delete params.limitNum;
      }
      this.itemData.items.forEach(sku => {
        params.skuIds.push(sku.skuId);
      });
      params.skuIds = params.skuIds.join(',');
      let api = 'add';
      if(this.$route.params.id){
        params.id = this.$route.params.id;
        api = 'update';
      }
      this.api.groupBuy[api](params).then(res => {
        this.$message.success({message: res.message, showClose: true});
        this.$router.push({ name: '团购' });
      });
    }
  }
}
</script>

<style lang="scss">
  .buy-limit-detail{
    .big-title{
      color: #333333;
      font-size: 14px;
      font-weight: bold;
      padding-bottom: 10px;
    }
    .wrap{
      background: #fff;
      padding: 10px 20px;
      box-shadow: 0 2px 3px #E5E5E5;
      border-radius: 4px;
      .form-wrap{
        margin-bottom: 15px;
        .banner{
          display: inline-block;
          vertical-align: top;
          img{
            max-width: 200px;
            max-height: 200px;
          }
        }
        .form-name{
          vertical-align: top;
          line-height: 30px;
          width: 80px;
        }
        .form-control{
          width: 160px;
        }
        .timepicker-control{
          width: 160px;
          margin-right: 15px;
        }
        .select-goods{
          display: inline-block;
          vertical-align: top;
          width: 110px;
          height: 30px;
          border: 1px dashed #F06941;
          color: #F06941;
          text-align: center;
          line-height: 30px;
          margin-right: 10px;
          border-radius: 15px;
          cursor: pointer;
        }
        .el-radio{
          line-height: 30px;
        }
        &:last-child{
          margin-bottom: 0;
        }
      }
      .goods-list{
        margin-bottom: 15px;
        table {
          width: 1000px;
          border-collapse: collapse;
          border-spacing: 0;
          border-radius: 4px;
          overflow: hidden;
          box-shadow: 0 0 0 1px $border-color;
          .w300{
            width: 300px;
          }
          .w150{
            width: 150px;
          }
          .w100{
            width: 100px;
          }
          thead {
            background: #EBEFFC;
          }
          .item-name{
            img{
              display: inline-block;
              vertical-align: middle;
              max-width: 54px;
              max-height: 54px;
            }
            span{
              display: inline-block;
              vertical-align: middle;
              width: 210px;
              white-space: normal;
              word-break: break-all;
              padding-left: 10px;
              line-height: 18px;
            }
          }
          tr {
            th{
              white-space: nowrap;
            }
            th, td {
              padding: 10px;
              text-align: left;
              line-height: 20px;
              vertical-align: middle;
              .link{
                margin-right: 10px;
              }
            }
          }
        }
      }
    }
    .el-dialog__body{
      padding: 0;
    }
  }
</style>
