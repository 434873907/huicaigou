<template>
  <div class="center-freight-temp-add">
    <div class="page-title">新建运费模板</div>
    <div class="list-wrap">
      <div class="form-wrap">
          <div class="form-name form-require">模板名称</div>
          <input class="form-control long-form" type="text" v-model="tempParams.templateName"/>
      </div>
      <div class="form-wrap" v-show="!userConfig.singleChannel">
          <div class="form-name">渠道</div>
          <select class="form-control long-form" v-model="tempParams.channel" :class="{'unset-select':!tempParams.channel}">
            <option value="">请选择</option>
            <option v-for="item in channelList" :key="item.channelType" :value="item.channelType">{{item.channelTypeName}}</option>
          </select>
      </div>
      <div class="form-wrap">
          <div class="form-name">货币</div>
          <select class="form-control long-form" v-model="tempParams.currency" :class="{'unset-select':!tempParams.currency}">
            <option value="">请选择</option>
            <option v-for="item in currencyList" :key="item.code" :value="item.code">{{item.mixName}}</option>
          </select>
      </div>
      <div class="form-wrap">
        <div class="form-name form-require">是否包邮</div>
        <el-radio v-model="tempParams.isFree" :label="1">包邮</el-radio>
        <el-radio v-model="tempParams.isFree" :label="0">自定义运费</el-radio>
      </div>
      <div class="form-wrap">
        <div class="form-name form-require">部分地区不发货</div>
        <el-radio v-model="tempParams.isNoSend" :label="0">否</el-radio>
        <el-radio v-model="tempParams.isNoSend" :label="1">是</el-radio>
        <span class="link underline" @click="showCarryMode(3)" v-show="!tempParams.undeliverableArea">设置不发货地区</span>
        <span v-show="tempParams.undeliverableArea"> 
          <span @click="showCarryMode(3)" class="link underline">更换</span>
            <el-popover class="inline-block"
              placement="right"
              width="400"
              trigger="hover">
              <span>{{tempParams.undeliverableArea}}</span>
              <span slot="reference">（已选择{{tempParams.noSendNum}}城市）</span>
            </el-popover>
        </span>
      </div>
      <div class="form-wrap" v-show="!tempParams.isFree">
        <div class="form-name form-require">计价方式</div>
        <el-radio v-model="valuationModel" :label="1">按件数</el-radio>
        <el-radio v-model="valuationModel" :label="2">按重量</el-radio>
      </div>
       <div class="form-wrap" v-show="!tempParams.isFree">
        <div class="form-name">运输方式</div>
        <el-radio v-model="transportation" :label="1">快递</el-radio>
      </div>
      <div class="table-form" v-show="!tempParams.isFree">
        <div class="table-title-form">
          <div class="form-wrap">
            <div class="form-name">默认运费</div>
            <ApiInput v-model="tempParams.carryMode.defaultCarryMode.firstLimit"></ApiInput>
          </div>
          <div class="form-wrap">
            <div class="form-name">{{valuationModel===1?'件':''}}{{valuationModel===2?'kg':''}}内</div>
            <ApiInput v-model="tempParams.carryMode.defaultCarryMode.firstPrice"></ApiInput>
          </div>
           <div class="form-wrap">
            <div class="form-name">{{currencyName}}，每增加</div>
            <ApiInput v-model="tempParams.carryMode.defaultCarryMode.secondLimit"></ApiInput>
          </div>
          <div class="form-wrap">
            <div class="form-name">{{valuationModel===1?'件':''}}{{valuationModel===2?'kg':''}}，增加运费</div>
            <ApiInput v-model="tempParams.carryMode.defaultCarryMode.secondPrice"></ApiInput>
            <div class="form-name">{{currencyName}}</div>
          </div>
        </div>
        <table>
          <tr class="th">
            <th class="left-padding">运送到</th>
            <th>首件数({{valuationModel===1?'件':''}}{{valuationModel===2?'kg':''}})</th>
            <th>首费{{currencyName?'('+currencyName+')':''}}</th>
            <th>续件数({{valuationModel===1?'件':''}}{{valuationModel===2?'kg':''}})</th>
            <th>续费{{currencyName?'('+currencyName+')':''}}</th>
            <th>操作</th>
          </tr>
          <tr v-for="(item, index) in tempParams.carryMode.carryModeList" :key="index+'sp'">
            <td class="left-padding">
              <span class="small-font" v-show="!item.region">未添加地区</span>
              <span class="region" v-show="item.region">{{item.region}}</span>
              <span class="link underline" @click="showCarryMode(1,index)">编辑</span>
            </td>
            <td><ApiInput v-model="item.firstLimit"></ApiInput></td>
            <td><ApiInput v-model="item.firstPrice"></ApiInput></td>
            <td><ApiInput v-model="item.secondLimit"></ApiInput></td>
            <td><ApiInput v-model="item.secondPrice"></ApiInput></td>
            <td class="org-font" @click="removeSp(index)">
              <span class="remove-sp">删除</span>
            </td>
          </tr>
        </table>
        <div class="addSp">
          <a class="link underline set-price" @click="addSp()">为指定地区城市设置运费</a>
        </div>
      </div>
      <el-checkbox v-model="tempParams.isFreeByIf" :true-label="1" :false-label="0" v-show="!tempParams.isFree">指定条件设置包邮</el-checkbox>
      <div class="table-form table-form-wrap" v-show="!tempParams.isFree && tempParams.isFreeByIf">
        <table>
          <tr class="th">
            <th class="left-padding free-condition">选择地区</th>
            <th>选择运送方式</th>
            <th>包邮条件</th>
            <th class="free-condition-coop">操作</th>
          </tr>
          <tr v-for="(item, index) in tempParams.freeConditionList" :key="index+'freeConf'">
            <td class="left-padding">
              <span class="small-font" v-show="!item.region">未添加地区</span>
              <span class="region" v-show="item.region">{{item.region}}</span>
              <span class="link underline" @click="showCarryMode(3, index)">编辑</span>
            </td>
            <td>
              <select class="form-control" v-model="item.type">
                <option v-for="opt in freeConditionType" :key="opt.id" :value="opt.id">{{opt.name}}</option>
              </select>
            </td>
            <td>
              <input type="text" class="form-control" v-model="item.limit">
              <span class="ml10">{{item.type===1?'件':(currencyName?currencyName:'')}}包邮</span>
            </td>
            <td class="org-font">
              <span class="el-icon-plus" @click="addConf()" v-show="index === 0"></span>
              <span class="el-icon-minus" @click="removeConf(index)" v-show="index !== 0"></span>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <button class="btn-main save" @click="saveTemp()">保存</button>
    <router-link :to="{ name: '运费模板'}" class="btn-fill-main">返回</router-link>
    <expressAddress v-if="showAddressModal" v-on:dialogExpressAddress="dialogAddress" v-on:dialogExpressAddressCancel="dialogAddressCancel"></expressAddress>
  </div>
</template>
<script>
import expressAddress from '@/components/expressAddress/index.vue';

export default {
  data() {
    return { 
      id: '',
      isCopy: '',
      showAddressModal:false,
      valuationModel: 2,  // 0：包邮，1：计件，2：计重
      transportation: 1,
      freeConditionType: [
        {id: 1, name: '按件包邮'},
        {id: 2, name: '按价包邮'}
      ],
      currentType: '',
      currentIndex: '',
      currencyName: '',
      channelList: [],
      currencyList: [],
      tempParams:{
        templateName: '',
        isFree: 1,
        isFreeByIf: 0,
        status: 1,
        isNoSend:0,
        undeliverableArea:'',
        channel: '',
        currency: '',
        carryMode: {
          defaultCarryMode: {
            firstLimit: '',
            firstPrice: '',
            secondLimit: '',
            secondPrice: ''
          },
          carryModeList: [
            {
              region: '',
              firstLimit: '',
              firstPrice: '',
              secondLimit: '',
              secondPrice: ''
            }
          ]
        },
        freeConditionList: [
          {
            region: '',
            type: 1,
            limit: ''
          }
        ]
      }
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      // 获取模板id
      this.id = this.$route.params.id || '';
      this.isCopy = this.$route.params.isCopy || '';
      this.api.goods.channel({}).then((res) => {
        this.channelList = res.data;
      });
      this.api.common.currency({}).then((res) => {
        this.currencyList = res.data;
        this.currencyList.forEach(item => {
          item.mixName = item.symbol+' '+item.name;
        });
      });
      if(this.id) {
        this.getDetail(this.id);
      }
    },
    showCarryMode(type, index) {
      this.currentType = type;
      this.currentIndex = index;
      this.showAddressModal = true;
    },
    dialogAddress(res) {
      if(this.currentType === 1) {
        this.tempParams.carryMode.carryModeList[this.currentIndex].region = res.address.join(',');
      } else if (this.currentType === 2) {
        this.tempParams.freeConditionList[this.currentIndex].region = res.address.join(',');
      }
      if(this.currentType == 3){
        this.tempParams.undeliverableArea = res.address.join(',');
        this.tempParams.noSendNum = res.address.length;
      }
      this.dialogAddressCancel();
    },
    dialogAddressCancel() {
      this.currentType = '';
      this.currentIndex = '';
      this.showAddressModal = false;
    },
    getDetail(id) {
      this.api.feetemplate.detail({id: id}).then((res) => {
        this.tempParams.templateName = res.data.templateName;
        this.tempParams.isFree = res.data.isFree;
        this.tempParams.currency = res.data.currency;
        if(res.data.undeliverableArea){
          this.tempParams.isNoSend = 1;
          this.tempParams.undeliverableArea = res.data.undeliverableArea;
        }
        if(!this.tempParams.isFree) {
          this.valuationModel = res.data.valuationModel;
          this.tempParams.isFreeByIf = res.data.isFreeByIf;
          this.tempParams.carryMode = JSON.parse(res.data.carryMode);
          this.tempParams.freeConditionList = JSON.parse(res.data.freeCondition);
        }
      });
    },
    addSp() {
      this.tempParams.carryMode.carryModeList.push({
        region: '',
        firstLimit: '',
        firstPrice: '',
        secondLimit: '',
        secondPrice: ''
      });
    },
    removeSp(index) {
      this.tempParams.carryMode.carryModeList.splice(index, 1);
    },
    addConf() {
      this.tempParams.freeConditionList.push({
        region: '',
        type: 1,
        limit: ''
      });
    },
    removeConf(index) {
      this.tempParams.freeConditionList.splice(index, 1);
    },
    saveTemp() {
      let params = this.copy(this.tempParams);
      params.valuationModel = this.valuationModel;
      if(this.tempParams.isFree === 1) {
        params.valuationModel = 0;
      }
      if(!this.tempParams.isNoSend){
        delete params.undeliverableArea;
      }
      params.carryMode = JSON.stringify(params.carryMode);
      params.freeConditionList = JSON.stringify(params.freeConditionList);
      let api = 'add';
      if(this.id && this.isCopy == 1) {
        api = 'edit';
        params.id = this.id;
      }
      this.api.feetemplate[api](params).then((res) => {
        this.$message.success({message:res.message, showClose:true});
        this.$router.push({ name: '运费模板' });
      });
    }
  },
  components: {
    expressAddress
  },
  watch: {
    'tempParams.currency': function(val) {
      this.currencyName = '';
      this.currencyList.forEach(item => {
        if(val === item.code) {
          this.currencyName = item.name;
        }
      });
    }
  }
}
</script>
<style lang='scss'>
  .center-freight-temp-add{
    padding-bottom: 50px;
   .page-title{
     font-weight: 900;
   }
   .list-wrap{
     margin: 10px 0 20px;
     padding: 24px 20px;
     .form-wrap{
       margin-bottom: 15px;
       .link{
         margin-left: 10px;
       }
     }
     .form-name{
       min-width: 60px;
     }
     .long-form{
       width: 330px;
     }
    .table-form{
      overflow: hidden;
      margin: 20px 0;
      padding-bottom: 15px;
      width: 800px;
      border:1px solid $border-color;
      border-radius: 4px;
     .table-title-form{
       padding: 15px 20px 10px;
       background: #F5F5F5;
       .form-wrap{
         display: inline-block;
         .form-name{
           width:auto ;
         }
         .form-control{
           width: 60px;
           margin: 0 10px;
         }
       }
     }
     .addSp{
       padding-top: 10px;
     }
     table{
       width: 100%;
      .th{
        text-align: left;
        color:#666;
        font-weight: 600;
        line-height: 40px;
        border-bottom: 1px solid $border-color;
      }
      td{
        padding: 10px 10px 10px 0;
        vertical-align: middle;
        .ml10{
          margin-left: 10px;
        }
        .remove-sp{
          cursor: pointer;
        }
      }
      .small-font{
        font-size: 12px;
        color:#333;
        font-weight: 600;
      }
      .region{
        line-height: 24px;
      }
      .left-padding{
        white-space: normal;
        word-break: break-all;
        padding-left: 20px;
        width: 340px;
      }
      .free-condition{
        width: 450px;
      }
      .free-condition-coop{
        width: 44px;
      }
      .form-control{
        width: 80px;
      }
      .link{
        margin-left: 10px;
        font-size: 12px;
      }
     }
     .set-price{
       font-size: 12px;
       margin-left: 20px;
      } 
    }
    .table-form-wrap{
      padding: 0;
      .th{
        background: #F5F5F5;
      }
      .el-icon-plus, .el-icon-minus{
        font-size: 16px;
        cursor: pointer;
      }
      select{
        min-width: 110px;
      }
    }
   }
   .save{
     margin-right: 10px;
   }
  }
</style>