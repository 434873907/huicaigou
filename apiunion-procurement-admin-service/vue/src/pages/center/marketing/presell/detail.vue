<template>
  <div class="presell-detail">
    <h2 class="big-title">编辑活动</h2>
    <div class="wrap">
      <div class="form-wrap">
        <div class="form-name">活动名称:</div>
        <input type="text" class="form-control" v-model="detailData.name">
      </div>
      <div class="form-wrap">
        <div class="form-name">定金支付时间:</div>
        <el-date-picker class="timepicker-control" size="small" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择开始时间" v-model="detailData.depositStartTime"></el-date-picker>
        <el-date-picker class="timepicker-control" size="small" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择结束时间" v-model="detailData.depositEndTime"></el-date-picker>
      </div>
      <div class="form-wrap">
        <div class="form-name">尾款支付时间:</div>
        <el-date-picker class="timepicker-control" size="small" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择开始时间" v-model="detailData.tailStartTime"></el-date-picker>
        <el-date-picker class="timepicker-control" size="small" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择结束时间" v-model="detailData.tailEndTime"></el-date-picker>
      </div>
      <div class="form-wrap">
        <div class="form-name">活动商品:</div>
        <a class="select-goods" @click="showGoodsModal=true">+ 选择商品</a>
        <span class="form-tip">(仅添加设置为预售商品，非预售商品无法参加此活动)</span>
      </div>
      <div class="form-wrap">
        <div class="form-name">商品信息:</div>
      </div>
      <div class="goods-list">
        <table class="table">
          <thead>
            <tr>
              <th class="w300">商品名称</th>
              <th class="w150" v-show="!userConfig.singleChannel">渠道</th>
              <th class="w150">SKU</th>
              <th class="w100">商品价格</th>
              <th class="w100">库存</th>
              <th class="w100">限售</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody v-for="(item, index) in itemData" :key="index">
            <tr v-for="(val, index1) in item.skuInfoList" :key="index1">
              <td class="item-name" :rowspan="item.skuInfoList.length+1" v-if="index1 == 0">
                <img :src="item.mainImg" />
                <span><a>{{item.itemName}}</a></span>
              </td>
              <td :rowspan="item.skuInfoList.length+1" v-show="!userConfig.singleChannel">{{itemData.channelName}}</td>
              <td>{{val.skuValue}}</td>
              <td>{{val.price}}</td>
              <td>{{val.stockNum}}</td>
              <td><ApiInput v-model="val.limitNum"/></td>
              <td>
                <a class="link" @click="removeSku(index1,item.itemName)">删除</a>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="form-wrap">
        <div class="form-name">货币:</div>
        <select class="form-control" v-model="detailData.currency">
          <option :value="0">请选择</option>
          <option v-for="item in currencyList" :key="item.code" :value="item.code">{{item.mixName}}</option>
        </select>
      </div>
      <div class="form-wrap">
        <div class="form-name">定金金额:</div>
        <ApiInput v-model="detailData.depositAmount" />
      </div>
      <div class="form-wrap">
        <div class="form-name">尾款金额:</div>
        <ApiInput v-model="detailData.tailAmount"/>
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
      <div class="form-wrap">
        <div class="form-name form-name-long">活动结束后商品:</div>
        <el-radio name="disabled" v-model="detailData.statusAfterAct" :label="1">正常销售</el-radio>
        <el-radio name="disabled" v-model="detailData.statusAfterAct" :label="0">自动下架</el-radio>
      </div>
      <div class="btn-main" @click="save()">保存</div>
    </div>
    <selectActGoods v-if="showGoodsModal" :presell="1" v-on:dialogGoods="dialogGoods" v-on:dialogGoodsCancel="dialogGoodsCancel"></selectActGoods>
  </div>
</template>

<script>
import selectActGoods from '@/components/selectActGoods/index.vue';
export default {
  components: { selectActGoods },
  data() {
    return {
      showGoodsModal: false,
      isDisabled: 1,
      currencyList:[],
      itemData:{
        // itemName: '',
        // mainImg: '',
        // channelName: '',
        // items: []
        // itemInfo:{
        //   itemName: '',
        //   mainImg: '',
        //   channelType:'',
        //   channelName: '',
        //   itemId:'',
        //   skuInfoList:[]
        // }
      },
      detailData: {
        name:'',
        depositStartTime:'',
        depositEndTime:'',
        tailStartTime:'',
        tailEndTime:'',
        itemId:'',
        skuSellLimit:'',
        channelType:'',
        currency:0,
        depositAmount:'',
        tailAmount:'',
        limit:0,
        limitNum:'',
        statusAfterAct:''
      }
    }
  },
  created() {
    let id = this.$route.params.id;
    this.getCurrency();
    if(id){
      this.api.presell.get({id:id}).then((res) => {
        this.detailData = res.data;
        this.detailData.itemId = res.data.itemInfo.itemId;
        this.itemData[res.data.itemInfo.itemName] = res.data.itemInfo;
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
      var count = 0;
      for(let i in this.itemData){
          count ++;
          i
      }
      if(count >= 1 ){
        this.$message.error({message:'不能修改活动信息', showClose:true});
      } else {
        res.goodsList.forEach(item => {
            this.detailData.itemId = item.itemId;
            this.detailData.skuSellLimit = [];
            this.detailData.channelType = item.selectedChannel;
            item.selectedSku.forEach(sku => {
              this.itemData[item.itemName] = this.itemData[item.itemName] ? this.itemData[item.itemName] : {}
              this.itemData[item.itemName].skuInfoList = this.itemData[item.itemName].skuInfoList ? this.itemData[item.itemName].skuInfoList : []
              this.itemData[item.itemName].skuInfoList.push({
                skuId: sku.skuId,
                skuValue: sku.skuValue,
                stockNum: sku.stockNum,
                price: sku.price,
                limitNum:'',
              });
            });
            this.itemData[item.itemName].channelType = item.selectedChannel
            this.itemData[item.itemName].channelName = item.selectedChannelName
            this.itemData[item.itemName].itemId = item.itemId
            this.itemData[item.itemName].itemName = item.itemName
            this.itemData[item.itemName].mainImg = item.mainImg
        });
      }
      this.showGoodsModal = false;
    },
    dialogGoodsCancel() {
      this.showGoodsModal = false;
    },
    removeSku(index,name) {
      this.itemData[name].skuInfoList.splice(index,1);
      this.$forceUpdate()
    },
    save(){
      let api = 'add';
      if(this.detailData.id){
        api = 'update';
      }
      this.detailData.skuSellLimit = [];
      for( var i in this.itemData){
        this.itemData[i].skuInfoList.forEach(item=>{
          this.detailData.skuSellLimit.push({
            skuId:item.skuId,
            limitNum:item.limitNum
          });
        })
      }
      this.detailData.limitNum = this.detailData.limitNum || 0;
      this.detailData.skuSellLimit = JSON.stringify(this.detailData.skuSellLimit);
      this.api.presell[api](this.detailData).then(() => {
        this.$message.success({message:'成功', showClose:true});
        this.$router.push({ path: '/center/marketing/presell' });
      });
    }
  }
}
</script>

<style lang="scss">
  .presell-detail{
    .big-title{
      color: #333333;
      font-size: 14px;
      font-weight: bold;
      padding-bottom: 10px;
    }
    .wrap{
      background: #fff;
      padding: 20px 20px;
      box-shadow: 0 2px 3px #E5E5E5;
      border-radius: 4px;
      .form-wrap{
        margin-bottom: 15px;
        .form-name{
          vertical-align: top;
          line-height: 30px;
          width: 100px;
        }
        .form-name-long{
          width: 120px;
        }
        .form-tip{
          vertical-align: top;
          line-height: 30px;
        }
        .timepicker-control{
          width: 164px;
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
              vertical-align: top;
              max-width: 54px;
              max-height: 54px;
            }
            span{
              display: inline-block;
              vertical-align: top;
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
            td{
              height: 75px;
            }
            .item-name{
              a{
                display: flex;
                height: 54px;
                align-items: center;
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