<template>
  <div class="coupon-detail">
    <h2 class="coupon-title">基本信息</h2>
    <div class="coupon-wrap">
      <div class="info-item">
        <span class="title">优惠券类型</span>
        <select type="text" class="form-control" v-model="couponParams.couponType" :disabled="readonly">
          <option v-for="item in couponTypeList" :key="item.id" :value="item.id">{{item.name}}</option>
        </select>
      </div>
      <div class="info-item" v-if="couponParams.couponType==1">
        <span class="title">优惠码</span>
        <input type="text" class="form-control" placeholder="单行输入" v-model="couponParams.couponCode" :disabled="readonly" />
      </div>
      <div class="info-item">
        <span class="title">优惠券名称</span>
        <input type="text" class="form-control" placeholder="单行输入" v-model="couponParams.couponName" :disabled="readonly" />
      </div>
      <div class="info-item">
        <span class="title">优惠券数量</span>
        <input type="number" class="form-control" v-model="couponParams.couponCount" :disabled="readonly" />
        <span class="text">张</span>
      </div>
    </div>
    <h2 class="coupon-title rule-info">使用规则</h2>
    <div class="coupon-wrap">
      <div class="rule-item">
        <span class="title">优惠券{{couponParams.couponValueType===0?'面额':'折扣'}}</span>
        <input type="number" class="form-control denomination" v-model="couponParams.couponValue" :disabled="readonly" />
        <span class="pl10" v-show="couponParams.couponValueType===0">元</span>
        <span class="pl10" v-show="couponParams.couponValueType===1">折</span>
      </div>
      <div class="rule-item">
        <span class="title">券值类型</span>
        <el-radio v-model="couponParams.couponValueType" :label="0" :disabled="readonly">
          <span class="text">金额</span>
        </el-radio>
        <el-radio v-model="couponParams.couponValueType" v-if="false" :label="1" class="valueTypeLast" :disabled="readonly">
          <span class="text">折扣</span>
        </el-radio>
      </div>
      <div class="rule-item">
        <span class="title">使用门槛</span>
        <el-radio v-model="limitType" :label="0" :disabled="readonly">
          <span class="text">不限制</span>
        </el-radio>
        <el-radio v-model="limitType" :label="1" class="threshold-box" :disabled="readonly">
          <span class="text">满</span><input type="text" class="form-control threshold" v-model="couponParams.orderPriceLimit" :disabled="readonly || limitType === 0" /><span class="text pl10">元可以使用</span>
        </el-radio>
      </div>
      <!-- <div class="rule-item">
        <span class="title">每人限领</span>
        <select class="form-control limit">
          <option value="">不限制</option>
        </select>
      </div> -->
      <div class="rule-item">
        <span class="title">有效期</span>
        <div class="validity">
          <el-radio v-model="timeType" :label="1" :disabled="readonly">
            <span class="text">固定日期</span>
            <span class="time-text pl20 pr10">生效时间</span>
            <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择" v-model="couponParams.startTime" :disabled="readonly || timeType === 0"></el-date-picker>
            <span class="time-text pl20 pr10">过期时间</span>
            <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择" v-model="couponParams.endTime" :disabled="readonly || timeType === 0"></el-date-picker>
          </el-radio>
        </div>
        <div class="validity timeType">
          <el-radio v-model="timeType" :label="0" :disabled="readonly">
            <span class="text">领券</span>
            <input type="text" class="form-control threshold" v-model="couponParams.afterDays" :disabled="readonly || timeType === 1" />
            <span class="text pl10">天内有效</span>
          </el-radio>
        </div>
      </div>
      <div class="rule-item goods-item">
        <span class="title">适用范围</span>
        <el-radio v-model="couponParams.conditionType" :label="0" :disabled="readonly">所有商品</el-radio>
        <el-radio v-model="couponParams.conditionType" :label="1" :disabled="readonly">按类目</el-radio>
        <el-radio v-model="couponParams.conditionType" :label="2" :disabled="readonly">按品牌</el-radio>
        <el-radio v-model="couponParams.conditionType" :label="3" :disabled="readonly">指定商品</el-radio>
        <!-- <el-radio v-model="couponParams.conditionType" :label="4" :disabled="readonly">指定店铺</el-radio> -->
      </div>
      <div class="rule-item">
        <div class="selected-rule">
          <a href="javascript:;" class="select-goods" @click="showCategoryModal = true" v-show="couponParams.conditionType === 1 && !readonly">选择类目+</a>
          <a href="javascript:;" class="select-goods" @click="showBrandModal = true" v-show="couponParams.conditionType === 2 && !readonly">选择品牌+</a>
          <a href="javascript:;" class="select-goods" @click="showGoodsModal = true" v-show="couponParams.conditionType === 3 && !readonly">选择商品+</a>
          <a href="javascript:;" class="select-goods" @click="showShopModal = true" v-show="couponParams.conditionType === 4 && !readonly">选择店铺+</a>
          <span class="selected-item" v-for="(item, index) in selectCategoryList" :key="index+'cate'" v-show="couponParams.conditionType === 1">
            {{item.name}}
            <span class="remove-icon" @click="removeCategory(index)" v-show="!readonly">X</span>
          </span>
          <span class="selected-item" v-for="(item, index) in selectBrandList" :key="index+'brand'" v-show="couponParams.conditionType === 2">
            {{item.name}}
            <span class="remove-icon" @click="removeBrand(index)" v-show="!readonly">X</span>
          </span>
          <table class="table select-goods-table" v-show="couponParams.conditionType === 3 && selectGoodsList.length>0">
            <thead>
              <tr>
                <th class="table-w440">商品</th>
                <th v-show="!readonly">操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in selectGoodsList" :key="index+'goods'">
                <td>
                  <img :src="item.mainImageUrl" />
                  <span class="name">{{item.name}}</span>
                </td>
                <td v-show="!readonly">
                  <a class="link" @click="removeGoods(index)">删除</a>
                </td>
              </tr>
            </tbody>
          </table>
          <span class="selected-item" v-for="(item, index) in selectShopList" :key="index+'shop'" v-show="couponParams.conditionType === 4">
            {{item.name}}
            <span class="remove-icon" @click="removeShop(index)" v-show="!readonly">X</span>
          </span>
        </div>
      </div>
      <div class="rule-item">
        <span class="title">优惠券说明</span>
        <textarea class="form-control remark" v-model="couponParams.desc" :disabled="readonly"></textarea>
      </div>
    </div>
    <div class="bot-btn">
      <a href="javascript:;" class="btn-main" @click="saveCoupon()" v-show="!readonly">保存</a>
      <router-link class="btn-fill-main" :to="{ name: '优惠券管理' }">返回</router-link>
    </div>

    <selectGoods v-if="showGoodsModal" v-on:dialogGoods="dialogGoods" v-on:dialogGoodsCancel="dialogGoodsCancel"></selectGoods>
    <selectBrand v-if="showBrandModal" v-on:dialogBrand="dialogBrand" v-on:dialogBrandCancel="dialogBrandCancel"></selectBrand>
    <selectShop v-if="showShopModal" v-on:dialogShop="dialogShop" v-on:dialogShopCancel="dialogShopCancel"></selectShop>
    <selectCategory v-if="showCategoryModal" v-on:dialogCategory="dialogCategory" v-on:dialogCategoryCancel="dialogCategoryCancel"></selectCategory>

  </div>
</template>
<script>
import selectCategory from '@/components/selectCategory/index.vue';
import selectBrand from '@/components/selectBrand/index.vue';
import selectGoods from '@/components/selectGoods/index.vue';
import selectShop from '@/components/selectShop/index.vue';
export default {
  data() {
    return {
      id: '',
      readonly: false,
      showGoodsModal: false,
      showBrandModal: false,
      showCategoryModal: false,
      showShopModal: false,
      limitType: 0,
      timeType: 1,
      selectCategoryList: [],
      selectBrandList: [],
      selectShopList: [],
      selectGoodsList: [],
      couponTypeList: [
        {id:0, name:'优惠券'}
        // {id:1, name:'优惠码'}
      ],
      couponParams: {
        couponType: 0,
        couponValueType: 0,
        conditionType: 0,
        startTime: '',
        endTime: '',
        desc: ''
      }
    }
  },
  created() {
    // 获取商品id
    this.id = this.$route.params.id || '';
    if(this.id) {
      this.api.coupon.detail({couponConfigId: this.id}).then((res) => {
        this.readonly = true;
        this.couponParams.couponType = res.data.couponType;
        this.couponParams.couponName = res.data.couponName;
        this.couponParams.couponValueType = res.data.couponValueType;
        this.couponParams.couponValue = res.data.couponValue;
        this.couponParams.couponCount = res.data.couponCount;
        this.couponParams.desc = res.data.desc || '';
        this.couponParams.conditionType = res.data.conditionType;
        this.couponParams.timeType = res.data.timeType;
        if(res.data.timeType === 1) {
          this.couponParams.startTime = res.data.startTime;
          this.couponParams.endTime = res.data.endTime;
        } else {
          this.couponParams.afterDays = res.data.afterDays;
        }
        if(res.data.orderPriceLimit) {
          this.limitType = 1;
          this.couponParams.orderPriceLimit = res.data.orderPriceLimit;
        } else {
          this.limitType = 0;
        }
        if(res.data.conditionType === 1) {
          this.selectCategoryList = res.data.condition;
        } else if (res.data.conditionType === 2) {
          this.selectBrandList = res.data.condition;
        } else if (res.data.conditionType === 3) {
          this.selectGoodsList = res.data.condition;
        } else if (res.data.conditionType === 4) {
          this.selectShopList = res.data.condition;
        } else {
          this.couponParams.conditionType = 0;
        }
      });
    }
  },
  methods: {
    dialogCategory(cates) {
      this.selectCategoryList = cates.categoryList;
      this.showCategoryModal = false;
    },
    dialogCategoryCancel() {
      this.showCategoryModal = false;
    },
    dialogGoods(goods) {
      this.selectGoodsList = goods.goodsList;
      this.showGoodsModal = false;
    },
    dialogGoodsCancel() {
      this.showGoodsModal = false;
    },
    dialogShop(res) {
      this.selectShopList = res.shopList;
      this.showShopModal = false;
    },
    dialogShopCancel() {
      this.showShopModal = false;
    },
    removeShop(index) {
      this.selectShopList.splice(index, 1);
    },
    dialogBrand(brands) {
      this.selectBrandList = brands.brandList;
      this.showBrandModal = false;
    },
    dialogBrandCancel() {
      this.showBrandModal = false;
    },
    removeCategory(index) {
      this.selectCategoryList.splice(index, 1);
    },
    removeBrand(index) {
      this.selectBrandList.splice(index, 1);
    },
    removeGoods(index) {
      this.selectGoodsList.splice(index, 1);
    },
    checkComplete() {
      if(this.couponParams.couponType && !this.couponParams.couponCode) {
        return false
      }
      if(!this.couponParams.couponName) {
        return false
      }
      if(!this.couponParams.couponCount) {
        return false
      }
      if(!this.couponParams.couponValue) {
        return false
      }
      return true;
    },
    saveCoupon() {
      if(!this.checkComplete()) {
        this.$message.info({message:'请将所有信息填写完整', showClose:true});
        return false
      }
      let params = {
        couponType: this.couponParams.couponType,
        couponName: this.couponParams.couponName,
        couponValueType: this.couponParams.couponValueType,
        couponValue: this.couponParams.couponValue,
        couponCount: this.couponParams.couponCount,
        desc: this.couponParams.desc,
        conditionType: this.couponParams.conditionType,
        condition: {}
      };
      if(this.couponParams.couponType === 1) {
        params.couponCode = this.couponParams.couponCode || '';
        if(!params.couponCode) {
          this.$message.info({message:'请填写优惠码', showClose:true});
          return
        }
      }
      if(this.limitType === 0) {
        params.orderPriceLimit = 0;
      } else {
        params.orderPriceLimit = this.couponParams.orderPriceLimit;
      }
      params.timeType = this.timeType;
      if(this.timeType === 0) {
        params.afterDays = this.couponParams.afterDays;
        delete params.startTime;
        delete params.endTime;
      } else {
        params.startTime = this.couponParams.startTime;
        params.endTime = this.couponParams.endTime;
      }
      if(this.couponParams.conditionType === 1) {
        let ids = [];
        this.selectCategoryList.forEach(item => {
          ids.push(item.id);
        });
        params.condition.categories = ids;
      } else if (this.couponParams.conditionType === 2) {
        let ids = [];
        this.selectBrandList.forEach(item => {
          ids.push(item.id);
        });
        params.condition.brands = ids;
      } else if (this.couponParams.conditionType === 3) {
        let ids = [];
        this.selectGoodsList.forEach(item => {
          ids.push(item.id);
        });
        params.condition.items = ids;
      } else if (this.couponParams.conditionType === 4) {
        let ids = [];
        this.selectShopList.forEach(item => {
          ids.push(item.id);
        });
        params.condition.shopIds = ids;
      } else {
        params.condition = {};
      }
      params.condition = JSON.stringify(params.condition);
      this.api.coupon.add(params).then((res) => {
        this.$message.success({message:res.message, showClose:true});
        if (res.success) {
          this.$router.go(-1)
        }
      });
    }
  },
  components: {
    selectCategory,
    selectBrand,
    selectGoods,
    selectShop
  }
}
</script>
<style lang='scss'>
  .coupon-detail{
    padding-bottom: 50px;
    .coupon-title{
      font-size: 14px;
      color: #333333;
      font-weight: bold;
      padding-bottom: 10px;
    }
    .rule-info{
      padding-top: 20px;
    }
    .coupon-wrap{
      background: #fff;
      border-radius: 4px;
      padding: 20px;
      .info-item{
        padding-bottom: 15px;
        .title{
          display: inline-block;
          vertical-align: top;
          width: 80px;
          line-height: 30px;
        }
        .form-control{
          width: 240px;
        }
        .text{
          padding-left: 10px;
        }
        &:last-child{
          padding-bottom: 0;
        }
      }
      .rule-item{
        padding-bottom: 15px;
        .el-radio{
          vertical-align: top;
          span{
            line-height: 30px;
          }
        }
        .selected-rule{
          margin-left: 80px;
          width: 574px;
          .selected-item{
            display: inline-block;
            vertical-align: top;
            height: 30px;
            line-height: 30px;
            padding: 0 8px;
            background: $main-color;
            color: #fff;
            border-radius: 22px;
            margin-left: 10px;
            margin-bottom: 10px;
            .remove-icon{
              display: inline-block;
              vertical-align: top;
              height: 30px;
              line-height: 30px;
              padding: 0 4px;
              cursor: pointer;
            }
          }
          .select-goods-table{
            margin-top: 15px;
            border: 1px solid $border-color;
            border-radius: 4px;
            tbody{
              tr{
                td{
                  img{
                    display: inline-block;
                    vertical-align: top;
                  }
                  .name{
                    display: inline-block;
                    vertical-align: top;
                    line-height: 30px;
                    margin-left: 10px;
                  }
                }
                &:last-child{
                  td{
                    border: none;
                  }
                }
              }
            }
          }
        }
        .valueTypeLast{
          margin-left: 58px;
        }
        .title{
          display: inline-block;
          vertical-align: top;
          width: 80px;
          text-align: left;
          color: #333333;
          font-size: 14px;
          line-height: 30px;
        }
        .denomination{
          width: 240px;
        }
        .remark{
          width: 548px;
          height: 80px;
        }
        .check-circle{
          display: inline-block;
          vertical-align: top;
          width: 12px;
          height: 12px;
          border-radius: 12px;
          border: 1px solid #DDDDDD;
          margin-top: 8px;
          cursor: pointer;
        }
        .check-circle-active{
          background: $org-color;
          border-color: $org-color;
        }
        .text{
          display: inline-block;
          vertical-align: top;
          font-size: 14px;
          color: #333333;
          font-weight: bold;
          line-height: 30px;
        }
        .time-text{
          display: inline-block;
          vertical-align: top;
          font-size: 14px;
          color: #333333;
          line-height: 30px;
        }
        .threshold-box{
          margin-left: 45px;
        }
        .threshold{
          width: 76px;
          margin-left: 16px;
        }
        .limit{
          width: 240px;
        }
        .validity{
          display: inline-block;
          vertical-align: top;
          .validity-item{
            padding-bottom: 10px;
          }
          .el-input__icon{
            line-height: 30px;
          }
        }
        .timeType{
          padding-top: 15px;
          margin-left: 80px;
          display: block;
        }
        .el-date-editor{
          width: 160px;
          height: 30px;
          .el-input__inner{
            display: inline-block;
            height: 100%;
          }
        }
        .select-goods{
          display: inline-block;
          vertical-align: top;
          width: 100px;
          height: 30px;
          border: 1px dashed $org-color;
          color: $org-color;
          font-size: 14px;
          text-align: center;
          line-height: 30px;
          cursor: pointer;
        }
        .pl10{
          padding-left: 10px;
        }
        .pl20{
          padding-left: 20px;
        }
        .pr10{
          padding-right: 10px;
        }
        .pr20{
          padding-right: 20px;
        }
        &:last-child{
          padding-bottom: 0;
        }
      }
      .goods-item{
        padding-bottom: 3px;
      }
    }
    .select-goods, .select-category{
      .el-dialog__body{
        padding: 0;
      }
    }
    .el-dialog{
      .el-dialog__body{
        .dialog-form{
          padding: 0;
          .dialog-search{
            background: #F5F5F5;
            padding: 10px 20px;
            border-bottom: 1px solid $border-color;
            .form-control{
              width: 120px;
              min-width: 120px;
              margin-right: 10px;
            }
            .input-box{
              display: inline-block;
              vertical-align: top;
              .form-control{
                width: 200px;
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
        }
      }
    }
    .bot-btn{
      padding-top: 20px;
      a{
        margin-right: 20px;
      }
    }
  }
</style>
