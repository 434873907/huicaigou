<template>
  <div class="store-shop-detail">
    <div class="operateBox">
        <h2 class="big-title">操作</h2>
        <div class="save-inner">
            <a href="javascript:;" class="btn-main" @click="saveShop()">保存</a>
            <router-link :to="{ name: '店铺管理'}" class="btn-fill-main">返回</router-link>
        </div>
    </div>
    <h2 class="big-title">店铺详情</h2>
    <div class="wrap">
      <div class="form-wrap">
        <div class="form-name">店铺名称</div>
        <input class="form-control" v-model="shopParams.name" placeholder="" />
      </div>
      <div class="form-wrap">
        <div class="form-name" :class="{'line60':shopParams.logoUrl}">店铺Logo</div>
        <div class="imgBox">
          <div class="content">
            <img class="logo-url" :src="shopParams.logoUrl" v-show="shopParams.logoUrl" />
          </div>
        </div>
        <upload uploadType='2' v-on:uploadSuccess="uploadSuccess" :class="{'mt15':shopParams.logoUrl}" >
          <a class="shop-img" v-show="!shopParams.logoUrl">上传店铺Logo</a>
          <a class="btn-fill-main" v-show="shopParams.logoUrl">更换</a>
          <span style="display: inline-block;margin-top: 10px;color: #999;font-size: 14px">(尺寸建议为100x100px，大小不超过1M)</span>
        </upload>
      </div>
      <div class="form-wrap">
        <div class="form-name">店铺简介</div>
        <textarea class="form-control textarea" v-model="shopParams.desc"></textarea>
      </div>
      <div class="form-wrap">
        <div class="form-name">是否自营</div>
        <el-radio v-model="shopParams.isSelf" :label="1">是</el-radio>
        <el-radio v-model="shopParams.isSelf" :label="0">否</el-radio>
      </div>
      <div class="form-wrap">
        <div class="form-name">发货时间(天)</div>
        <input class="form-control" v-model="shopParams.deliverDays" placeholder='请输入天数,用"-"连接' />
      </div>
      <div class="form-wrap">
        <div class="form-name">到货时间(天)</div>
        <input class="form-control" v-model="shopParams.arrivalDays" placeholder='请输入天数,用"-"连接' />
      </div>
      <div class="form-wrap">
        <div class="form-name">结算货币</div>
        <select class="form-control" v-model="shopParams.currency" :class="{'unset-select':!shopParams.currency}">
          <option value="">请选择货币</option>
          <option v-for="item in currencyList" :key="item.code" :value="item.code">{{item.symbol}} {{item.name}}</option>
        </select>
      </div>
      <div class="form-wrap" style="margin-bottom: 0">
        <div class="form-name">店铺状态</div>
        <el-radio v-model="shopParams.status" :label="1">启用</el-radio>
        <el-radio v-model="shopParams.status" :label="0">禁用</el-radio>
      </div>
    </div>
    <div class="save-box">
      <h2 class="title">操作</h2>
      <div class="save-inner">
        <a href="javascript:;" class="btn-main" @click="saveShop()">保存</a>
        <router-link :to="{ name: '店铺管理'}" class="btn-fill-main">返回</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: '',
      currencyList: [],
      shopParams: {
        name: '',
        logoUrl: '',
        desc: '',
        currency: '',
        isSelf: 1,
        status: 1
      }
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.id = this.$route.params.id || '';
      // 获取货币列表
      this.api.common.currency({}).then((res) => {
        this.currencyList = res.data;
      });
      if(this.id) {
        this.getDetail(this.id);
      }
    },
    getDetail(id) {
      this.api.store.index({id: id}).then((res) => {
        this.shopParams = res.data;
        if(!this.shopParams.currency) {
          this.shopParams.currency = '';
        }
      });
    },
    uploadSuccess(res) {
      this.shopParams.logoUrl = res.data;
    },
    saveShop() {
      let params = this.copy(this.shopParams);
      if(this.id) {
        params.id = this.id;
        this.api.store.edit(params).then((res) => {
          this.$message.success({message:res.message, showClose:true});
          this.$router.push({ name: '店铺管理' });
        });
      } else {
        this.api.store.add(params).then((res) => {
          this.$message.success({message:res.message, showClose:true});
          this.$router.push({ name: '店铺管理' });
        });
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  .store-shop-detail{
    // padding-right: 260px;
    position: relative;
    .big-title{
      color: #333333;
      font-size: 14px;
      font-weight: bold;
      padding-bottom: 10px;
    }
    .wrap{
      background: #fff;
      margin-bottom: 20px;
      padding: 20px 20px;
      background: #fff;
      box-shadow: 0 2px 3px #E5E5E5;
      border-radius: 4px;
      .form-wrap{
        margin-bottom: 15px;
        .form-name{
          vertical-align: top;
          line-height: 30px;
          width: 80px;
        }
        .form-control{
          width: 280px;
        }
        .textarea{
          height: 60px;
          width: 254px;
        }
        .line60{
          line-height: 60px;
        }
        .mt15{
          margin-top: 15px;
        }
        .upload-container{
          display: inline-block;
          vertical-align: top;
        }
        .el-radio{
          line-height: 30px;
        }
        .imgBox{
          display: inline-block;
          margin-right: 20px;
          border: 1px solid #e5e5e5;
          border-radius: 4px;
          .content{
            width:60px;
            height: 60px;
            display: flex;
            justify-content: center;
            align-items: center;
            img{
              max-width: 100%;
              max-height: 100%
            }
          }
        }
        .logo-url{
          display: inline-block;
          max-width: 60px;
          max-height: 60px;

        }
        .shop-img{
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
        }
      }
    }
    .save-box{
      position: fixed;
      top: 56px;
      right: 15px;
      .title{
        font-size: 14px;
        font-weight: bold;
        color: #333333;
        padding: 10px 0;
      }
      .save-inner{
        background: #fff;
        padding: 20px 30px;
        border-radius: 4px;
        .btn-main{
            margin-right: 20px;
        }
      }
    }
  }
</style>
