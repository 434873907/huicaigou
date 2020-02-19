<template>
  <div class="agreement-detail">
    <div class="operateBox">
        <h2 class="big-title">操作</h2>
        <div class="save-inner">
            <a href="javascript:;" class="btn-main" @click="saveAgreement()">保存</a>
            <router-link :to="{ name: '系统协议'}" class="btn-fill-main">返回</router-link>
        </div>
    </div>
    <h2 class="agreement-title">编辑协议</h2>
    <div class="editor-wrap">
      <div class="form-wrap">
        <div class="form-name">协议名称:</div>
        <span v-for="item in agreementList" :key="item.id" v-show="item.id == id">{{item.name}}</span>
      </div>
      <div class="form-wrap form-last">
        <div class="form-name">备注:</div>
        <span v-for="item in agreementList" :key="item.id" v-show="item.id == id">{{item.remark}}</span>
      </div>
      <div class="form-wrap form-last" v-show="false">
        <div class="form-name">协议操作:</div>
        <a class="link">预览</a>
        <a class="link">下载</a>
      </div>
      <editor v-model="content"></editor>
    </div>
    <div class="save-box">
      <h2 class="title">操作</h2>
      <div class="save-inner">
        <a href="javascript:;" class="btn-main" @click="saveAgreement()">保存</a>
        <router-link :to="{ name: '系统协议'}" class="btn-fill-main">返回</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import editor from '@/components/tinymce/tinymce.vue';
export default {
  components: {editor},
  data() {
    return {
      id: '',
      content: '',
      agreementList: [
        {id: 1, name: '平台服务协议', remark: '注册页-平台服务协议'},
        {id: 2, name: '用户注册协议', remark: '个人页-设置-用户注册协议'},
        {id: 3, name: '使用帮助', remark: '个人页-使用帮助'},
        {id: 4, name: '正品保证', remark: '个人页-正品保证'},
        {id: 5, name: '保障说明', remark: '商品详情页-保障'},
        {id: 6, name: '运费说明|购买须知', remark: '商品详情页-运费说明|购买须知'},
        {id: 7, name: '充值协议', remark: '充值协议'},
        {id: 8, name: '用户隐私协议', remark: '注册页-用户隐私协议'}
      ]
    }
  },
  created() {
    this.id = this.$route.params.id || '';
    if(this.id) {
      this.getAgreement();
    }
  },
  methods: {
    getAgreement() {
      this.api.system.agreementDetail({type: this.id}).then((res) => {
        this.content = res.data.content;
      });
    },
    saveAgreement() {
      let params = {
        type: this.id,
        content: this.content
      };
      this.api.system.agreement(params).then((res) => {
        this.$message.success({message:res.message, showClose:true});
        this.$router.push({name: '系统协议'});
      });
    }
  }
}
</script>

<style lang="scss" scoped>
  .agreement-detail{
    // padding-right: 260px;
    padding-bottom: 50px;
    position: relative;
    .agreement-title{
      color: #333333;
      font-size: 14px;
      font-weight: bold;
      padding-bottom: 10px;
    }
    .save-box{
      position: fixed;
      top: 55px;
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
    .editor-wrap{
      padding: 20px;
      background: #fff;
      border-radius: 4px;
      .form-wrap{
        .form-name{
          display: inline-block;
          vertical-align: top;
          width: 70px;
          line-height: 30px;
        }
        span{
          display: inline-block;
          vertical-align: top;
          line-height: 30px;
          font-weight: bold;
        }
        .link{
          display: inline-block;
          vertical-align: top;
          line-height: 30px;
          margin-right: 15px;
        }
      }
      .form-last{
        padding-bottom: 40px;
      }
    }
  }
</style>
