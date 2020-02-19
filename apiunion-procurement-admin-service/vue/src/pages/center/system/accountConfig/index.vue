<template>
  <div class="wrap accountConfig">
    <div class="config-item">
      <span class="title">平台标题:</span>
      <input type="text" class="form-control" v-model="configData.name" />
    </div>
    <div class="config-item">
      <span class="title">平台Logo:</span>
      <upload uploadType='2' v-on:uploadSuccess="uploadSuccess">
        <a class="upload-logo" v-show="!configData.logo">点击上传Logo</a>
        <img class="logo" :src="configData.logo" v-show="configData.logo" height="30">
      </upload>
      <span class="tips">(尺寸建议为100x100px，大小不超过1M)</span>
    </div>
    <div class="config-item">
      <span class="title">管理端Logo:</span>
      <upload uploadType='2' v-on:uploadSuccess="uploadSuccess1" :class="{'bg-pink':configData.logoBg}">
        <a class="upload-logo" v-show="!configData.logoBg">点击上传Logo</a>
        <img class="logo" :src="configData.logoBg" v-show="configData.logoBg" height="30">
      </upload>
      <span class="tips">(尺寸建议为480x78px，大小不超过1M)</span>
    </div>
    <div class="config-item">
      <span class="title">采购端Logo:</span>
      <upload uploadType='2' v-on:uploadSuccess="uploadSuccess2" :class="{'bg-pink':configData.logoFg}">
        <a class="upload-logo" v-show="!configData.logoFg">点击上传Logo</a>
        <img class="logo" :src="configData.logoFg" v-show="configData.logoFg" height="30">
      </upload>
      <span class="tips">(尺寸建议为480x76px，大小不超过1M)</span>
    </div>
<!--    登录页logo-->
    <div class="config-item">
      <span class="title">登录页Logo:</span>

      <upload uploadType='2' v-on:uploadSuccess="uploadSuccess4">
        <a class="upload-logo" v-show="!configData.pcLoginLogo">点击上传Logo</a>
        <img class="logo" :src="configData.pcLoginLogo" v-show="configData.pcLoginLogo" height="30">
      </upload>
      <span class="tips">(尺寸建议为750x160px，大小不超过1M)</span>
    </div>

    <div class="config-item">
      <span class="title">pc登录背景:</span>
      <upload uploadType='2' v-on:uploadSuccess="uploadSuccess3">
        <a class="upload-logo" v-show="!configData.backgroundUrl">点击上传Logo</a>
        <img class="logo" :src="configData.backgroundUrl" v-show="configData.backgroundUrl" height="30">
      </upload>
      <span class="tips">(尺寸建议为1920x860px，大小不超过1M)</span>
    </div>
    <div class="config-item">
      <span class="title">网站描述:</span>
      <textarea class="form-control textarea" v-model="configData.description"></textarea>
    </div>
    <div class="config-item">
      <span class="title">网站关键词:</span>
      <textarea class="form-control textarea-low" placeholder="可批量输入，以','隔开" v-model="keywords"></textarea>
      <a class="btn-fill-main" @click="addKeyword()">添加</a>
    </div>
    <div class="config-item keyword-row" v-show="keywordList.length>0">
      <span class="title"></span>
      <div class="keyword-content">
        <span class="keyword-item" v-for="(item, index) in keywordList" :key="index+'keyword'">
          {{item}}
          <i @click="removeKeyword(index)">X</i>
        </span>
      </div>
    </div>
    <div class="config-item">
      <span class="title">备案信息:</span>
      <input type="text" class="form-control long-input" v-model="configData.filingInfo" placeholder="例,浙ICP备1234567号 接口联盟©2018 APIUNION ALL Right Reserved." />
    </div>
    <div class="config-item">
      <span class="title"></span>
      <a class="btn-main" @click="saveConfig()">保存</a>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      keywords: '',
      keywordList: [],
      configData: {
        name: '',
        logo: '',
        logoBg: '',
        filingInfo: '',
        description: '',
        logoFg:'',
        backgroundUrl:'',
        pcLoginLogo:"",
      }
    }
  },
  created() {
    this.getConfig();
  },
  methods: {
    getConfig() {
      this.api.common.pageConfig().then((res) => {
        this.configData.name = res.data.name;
        this.configData.logo = res.data.logo;
        this.configData.filingInfo = res.data.filingInfo;
        this.configData.description = res.data.description;
        this.configData.logoBg = res.data.logoBg;
        this.configData.logoFg = res.data.logoFg;
        this.configData.backgroundUrl = res.data.backgroundUrl;
        this.configData.pcLoginLogo = res.data.pcLoginLogo;
        if(res.data.keywords) {
          this.keywordList = res.data.keywords.split(',');
        }
      });
    },
    addKeyword() {
      if(!this.keywords) {
        this.$message.info({message: '请填写关键词', showClose: true});
        return
      }
      let keywords = this.copy(this.keywords);
      let reg = new RegExp( '，' , "g" );
      keywords = (keywords.replace(reg, ',')).split(',');
      if(keywords && keywords.length>0) {
        let targetKeywords = [];
        keywords.forEach(item => {
          if(item && (this.keywordList.indexOf(item)) === -1 ) {
            targetKeywords.push(item);
          }
        });
        this.keywordList = this.keywordList.concat(targetKeywords);
      }
    },
    removeKeyword(index) {
      this.keywordList.splice(index, 1);
    },
    uploadSuccess(res) {
      this.configData.logo = res.data;
    },
    uploadSuccess1(res) {
      this.configData.logoBg = res.data;
    },
    uploadSuccess2(res) {
      this.configData.logoFg = res.data;
    },
    uploadSuccess3(res) {
      this.configData.backgroundUrl = res.data;
    },
    uploadSuccess4(res) {
      this.configData.pcLoginLogo = res.data;
    },
    saveConfig() {
      if(this.keywordList.length==0) {
        this.$message.info({message: '请填写网站关键词', showClose: true});
        return
      }
      let params = {
        name: this.configData.name,
        logo: this.configData.logo,
        filingInfo: this.configData.filingInfo,
        description: this.configData.description,
        logoBg: this.configData.logoBg,
        logoFg: this.configData.logoFg,
        backgroundUrl: this.configData.backgroundUrl,
        pcLoginLogo:this.configData.pcLoginLogo
      };
      params.keywords = this.keywordList.join(',');
      this.api.common.updatePageConfig(params).then((res) => {
        this.$message.success({message: res.message, showClose: true});
      });
    }
  }
}
</script>

<style lang="scss" scoped>
  .accountConfig{
    background: #fff;
    padding: 20px;
    border-radius: 4px;
    .config-item{
      padding-bottom: 15px;
      &:last-child{
        padding-bottom: 0;
      }
      .title{
        display: inline-block;
        vertical-align: top;
        line-height: 30px;
        width: 94px;
      }
      .keyword-content{
        display: inline-block;
        vertical-align: top;
        width: 728px;
      }
      .form-control{
        width: 728px;
      }
      .btn-fill-main{
        display: inline-block;
        vertical-align: top;
        margin-left: 10px;
        margin-top: 5px;
      }
      .long-input{
        width: 728px;
      }
      .textarea{
        height: 200px;
        width: 700px;
      }
      .textarea-low{
        width: 700px;
        height: 60px;
      }
      .keyword-item{
        display: inline-block;
        vertical-align: top;
        height: 30px;
        background: $main-color;
        color: #fff;
        border-radius: 30px;
        line-height: 30px;
        padding: 0 10px;
        margin-right: 10px;
        margin-bottom: 15px;
        i{
          color: #fff;
          font-style: normal;
          padding: 0 5px;
          cursor: pointer;
        }
      }
      .upload-container{
        display: inline-block;
        vertical-align: top;
      }
      .upload-logo{
        display: inline-block;
        vertical-align: top;
        width: 130px;
        height: 30px;
        border: 1px dashed #F06941;
        color: #F06941;
        text-align: center;
        line-height: 30px;
        margin-right: 10px;
        border-radius: 15px;
      }
      .logo{
        max-width: 200px;
        max-height: 100px;
      }
      .tips{
        display: inline-block;
        vertical-align: top;
        line-height: 30px;
        color: #999;
        font-size: 14px;
        margin-left: 10px;
      }
    }
    .keyword-row{
      padding-bottom: 0;
    }
  }
  .bg-pink{
    background: #E1376C;
  }
</style>
