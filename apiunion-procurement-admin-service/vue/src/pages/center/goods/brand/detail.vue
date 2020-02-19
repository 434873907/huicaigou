<template>
  <div class="brand-detail">
    <h2 class="title">品牌编辑</h2>
    <div class="brand-wrap">
      <div class="brand-item">
        <span class="brand-title">品牌中文名称</span>
        <input type="text" class="form-control" v-model="detailParams.name" maxlength="10" placeholder="最多10个字符" />
      </div>
      <div class="brand-item">
        <span class="brand-title">品牌英文名称</span>
        <input type="text" class="form-control" v-model="detailParams.originalName" />
      </div>
      <div class="brand-item">
        <span class="brand-title">别名</span>
        <input type="text" class="form-control" v-model="detailParams.alias"/>
        <span class="tip">(多个别名使用英文“;”隔开)</span>
      </div>
      <div class="brand-item">
        <span class="brand-title">品牌LOGO</span>
        <img class="logoUrl" :src="detailParams.logoUrl" v-show="detailParams.logoUrl" />
        <upload uploadType='2' v-on:uploadSuccess="uploadSuccess">
          <a href="javascript:;" class="upload-img" v-show="!detailParams.logoUrl">点击上传图片</a>
          <a class="link" v-show="detailParams.logoUrl">更改</a>
          <span class="tips" v-show="!detailParams.logoUrl">(尺寸建议为100x100px，大小不超过1M)</span>
        </upload>
      </div>
      <div class="brand-item">
        <span class="brand-title">品牌海报</span>
        <img class="posterUrl" :src="detailParams.posterUrl" v-show="detailParams.posterUrl" />
        <a href="javascript:;" class="upload-img" v-show="!detailParams.posterUrl" @click="openCropperModal()">点击上传海报</a>
        <a class="link" v-show="detailParams.posterUrl" @click="openCropperModal()">更改海报</a>
        <span class="tips" v-show="!detailParams.posterUrl">(尺寸建议为750x任意高度，大小不超过1M)</span>
      </div>
      <div class="brand-item">
        <span class="brand-title">状态</span>
        <el-radio v-model="detailParams.status" :label="1">启用</el-radio>
        <el-radio v-model="detailParams.status" :label="0">禁用</el-radio>
      </div>
      <div class="brand-item">
        <span class="brand-title">品牌描述</span>
        <textarea class="form-control remark" v-model="detailParams.desc"></textarea>
      </div>
    </div>
    <div class="bot-btn">
      <a href="javascript:;" class="btn-main" @click="saveBrand()">保存</a>
      <router-link class="btn-fill-main" :to="{ name: '品牌管理'}">返回</router-link>
    </div>
    <Cropper v-if="showCropper" v-on:dialogCropper="dialogCropper" v-on:dialogCropperCancel="dialogCropperCancel" :imgUrl="detailParams.posterUrl" :fixedNumber="[1.875, 1]"></Cropper>
  </div>
</template>

<script>
import Cropper from '@/components/cropper/index.vue';
export default {
  components: { Cropper },
  data() {
    return {
      status: '1',
      id: '',
      showCropper: false,
      detailParams: {
        status: 1,
        logoUrl: '',
        posterUrl: ''
      }
    }
  },
  created() {
    this.id = this.$route.params.id || '';
    if(this.id) {
      this.api.brand.detail({id: this.id}).then((res) => {
        this.detailParams = res.data;
      });
    }
  },
  methods: {
    openCropperModal() {
      this.showCropper = true;
    },
    dialogCropper(res) {
      this.detailParams.posterUrl = res.url;
      this.showCropper = false;
    },
    dialogCropperCancel() {
      this.showCropper = false;
    },
    uploadSuccess(res) {
      this.detailParams.logoUrl = res.data;
    },
    saveBrand() {
      if(!this.detailParams.name) {
        this.$message.info({message:'请输入品牌中文名称', showClose:true});
        return
      }
      if(!this.detailParams.originalName) {
        this.$message.info({message:'请输入品牌英文名称', showClose:true});
        return
      }
      if(!this.detailParams.logoUrl) {
        this.$message.info({message:'请上传图标', showClose:true});
        return
      }
      this.api.brand[this.id?'update':'add'](this.detailParams).then((res) => {
        this.$message.success({message:res.message, showClose:true});
        this.$router.push({ name: '品牌管理' });
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.brand-detail{
  .title{
    font-size: 14px;
    color: #333333;
    font-weight: bold;
    padding-bottom: 10px;
  }
  .brand-wrap{
    padding: 20px 20px 30px;
    background: #fff;
    border-radius: 4px;
    .brand-item{
      padding-bottom: 15px;
      .tips{
        display: inline-block;
        vertical-align: top;
        color: #999;
        font-size: 14px;
        padding-left: 10px;
        line-height: 30px;
      }
      .logoUrl{
        display: inline-block;
        vertical-align: top;
        max-width: 30px;
        max-height: 30px;
      }
      .posterUrl{
        display: inline-block;
        vertical-align: top;
        max-width: 300px;
        max-height: 160px;
      }
      .upload-container{
        display: inline-block;
        vertical-align: top;
      }
      &:last-child{
        padding-bottom: 0;
      }
      .brand-title{
        display: inline-block;
        vertical-align: top;
        font-size: 14px;
        color: #333333;
        text-align: left;
        width: 96px;
        line-height: 30px;
      }
      .form-control{
        width: 300px;
      }
      .upload-img{
        display: inline-block;
        vertical-align: top;
        width: 100px;
        height: 30px;
        font-size: 14px;
        color: $org-color;
        border: 1px dashed $org-color;
        text-align: center;
        line-height: 30px;
      }
      .link{
        line-height: 30px;
        margin-left: 15px;
      }
      .el-radio{
        line-height: 30px;
      }
      .another-name{
        height: 80px;
        width: 274px;
      }
      .remark{
        width: 274px;
        height: 100px;
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
