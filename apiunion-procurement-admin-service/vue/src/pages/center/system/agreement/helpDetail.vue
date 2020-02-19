<template>
  <div class="agreement-help-detail">
    <div class="operateBox">
        <h2 class="big-title">操作</h2>
        <div class="save-inner">
            <a href="javascript:;" class="btn-main" @click="addConfig()">新增</a>
            <a href="javascript:;" class="btn-main" @click="saveAgreement()">保存</a>
            <router-link :to="{ name: '系统协议'}" class="btn-fill-main">返回</router-link>
        </div>
    </div>
    <h2 class="agreement-title">编辑协议</h2>
    <div class="editor-wrap">
      <div class="form-wrap">
        <div class="form-name">协议名称:</div>
        <span>PC端帮助中心</span>
      </div>
      <div class="form-wrap form-last">
        <div class="form-name">备注:</div>
        <span>PC端帮助中心内容配置</span>
      </div>
      <div class="content-list" v-for="(item,index) in list" :key="index">
        <div class="form-wrap">
            <div class="form-name">标题{{index + 1}}:</div>
            <input type="text" class="form-control" placeholder="请输入标题" v-model="item.title">
        </div>
        <editor v-model="item.content"></editor>
      </div>
    </div>
    <div class="save-box" v-if="false">
      <div class="save-inner">
        <a href="javascript:;" class="btn-main" @click="addConfig()">新增</a>
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
      list:[]
    }
  },
  created() {
    this.getAgreement();
  },
  methods: {
    getAgreement() {
      this.api.system.helpConfig().then((res) => {
        this.list = res.data.helpConfig || [];
      });
    },
    addConfig(){
        this.list.push({
            title:'',
            content:''
        })
    },
    saveAgreement() {
        let params = [];
        this.list.forEach(item => {
            item.title && params.push(item);
        });
        this.api.common.updateAccountConfig({
            configType:'help_config',
            configContent:JSON.stringify(params)
        }).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.$router.push({name: '系统协议'});
        });
    }
  }
}
</script>

<style lang="scss" scoped>
  .agreement-help-detail{
    padding-bottom: 50px;
    position: relative;
    .agreement-title{
      color: #333333;
      font-size: 14px;
      font-weight: bold;
      padding-bottom: 10px;
    }
    .content-list{
        margin-bottom: 40px;
        .form-wrap{
            margin-bottom: 20px;
        }
    }
    .save-boxs {
      text-align: center;
      .save-inner {
        a {
          margin-right: 10px;
        }
      }
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
        width: 252px;
        background: #fff;
        padding: 20px 30px;
        border-radius: 4px;
        .btn-main{
            margin-bottom: 10px;
        }
        .btn-main:first-child{
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
  @media screen and (max-width: 1500px) {
      /deep/ .save-boxs {
        display: block;
    }
  }
  @media screen and (min-width: 1501px) {
      /deep/ .save-boxs {
        display: none;
    }
  }
</style>
