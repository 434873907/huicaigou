<template>
  <div class="upload-container">
    <el-upload
      :data="dataObj"
      :multiple="false"
      :show-file-list="false"
      :on-success="handleImageSuccess"
      :on-error="handleImageError"
      class="image-uploader"
      :before-upload='beforeUpload'
      :action="action">
      <slot></slot>
    </el-upload>
  </div>
</template>
<script>
import core from '@/core';
import { Promise } from 'q';

export default {
  name: 'SingleImageUpload',
  props: {
    uploadType: {
      type: String,
      default: '2'
    },
    options: {
      type: Object,
      default:() => {}
    },
    fileFolderId: {
      type: Number,
      default: -1
    },
    index:Number
  },
  data() {
    return {
      action:'',
      dataObj: {
        uploadType:''
      }
    }
  },
  computed: {
    imageUrl() {
      return this.value
    }
  },
  methods: {
    handleImageSuccess(val) {
      // val.index = this.index;
      let target = {
        data: val.data.url,
        name: val.data.fileName,
        index: this.index
      };
      this.$emit('uploadSuccess', target);
      this.loading.hideLoading();
    },
    handleImageError(){
      this.loading.hideLoading();
    },
    beforeUpload(file) {
      this.loading.showLoading();
      let _this = this
      const PromiseJudge = new Promise(function (resolve, reject) {
        let _URL = window.URL || window.webkitURL;
        let img = new Image();
        let valid = true;
        img.onload = function () {
          if(_this.options && _this.options.radio){
            if(_this.options.radio != img.width / img.height){
              valid = false;
            }
          }
        }
        valid ? resolve() : reject();
        img.src = _URL.createObjectURL(file);
      }).then(() => {
        return file;
      }).catch(() => {
        let message = '';
        if(this.options.radio){
          message = `图片宽高比限制为${this.options.radio}`;
        }
        _this.$message.error(message);
        this.loading.hideLoading();
        return PromiseJudge.reject();
      });
      return PromiseJudge;
    }
  },
  created(){
      this.action = core.api.static.upload;
      if (location.hostname == 'localhost' && this.action.substring(0, 4) != 'http' && location.port != '9050') {
          this.action = '/api/' + this.action;
      }
    // this.action = '/api/' + this.action;
      this.dataObj.uploadType = this.uploadType;
      if(this.fileFolderId != -1){
        this.dataObj.fileFolderId = this.fileFolderId;
      }
  },
  watch:{
    fileFolderId(val){
      if(val != -1){
        this.dataObj.fileFolderId = val;
      }
    }
  }
}
</script>
