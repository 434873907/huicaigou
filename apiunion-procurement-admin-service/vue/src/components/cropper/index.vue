<template>
  <div class="cropper-content">
    <el-dialog title="图片裁剪:" :visible.sync="showCropper" width="1000px" :before-close="cancelCropper">
      <div class="dialog-form">
        <vueCropper class="vue-cropper"
          ref="cropper"
          :img="option.img"
          :autoCrop="true"
          :outputSize="option.size"
          :outputType="option.outputType"
          :infoTrue="option.infoTrue"
          :fixed="option.fixed"
          :fixedBox="option.fixedBox"
          :fixedNumber="option.fixedNumber"
          :centerBox="option.centerBox"
          :autoCropWidth="option.autoCropWidth"
          :autoCropHeight="option.autoCropHeight"
          :enlarge="option.enlarge"
          @realTime="realTime"
        ></vueCropper>
        <div class="cropper-set">
          <upload uploadType='2' v-on:uploadSuccess="setCropperImg">
            <a class="upload-img" v-show="!option.img">上传图片</a>
            <a class="upload-img" v-show="option.img">更改图片</a>
          </upload>
          <h2 v-show="option.img">图片设置</h2>
          <div class="set-row" v-show="option.img">
            <a class="btn-fill-main" @click="setSize(1)">放大</a>
            <a class="btn-fill-main" @click="setSize(-1)">缩小</a>
          </div>
          <div class="set-row" v-show="option.img">
            <a class="btn-fill-main" @click="setRotate(1)">顺时针旋转</a>
            <a class="btn-fill-main" @click="setRotate(2)">逆时针旋转</a>
          </div>
          <h2 v-show="option.img">截取预览</h2>
          <div :style="previewStyle" v-show="option.img"> 
            <div :style="previews.div">
              <img :src="previews.url" :style="previews.img">
            </div>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="dialogCropper()">裁剪并上传</button>
        <button class="btn-fill-grey-main" @click="cancelCropper()">取消</button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { VueCropper } from "vue-cropper";
import core from '@/core';
function sendForm (form, url, callback, errorCallback) {
  var xmlHttp;
  if (window.XMLHttpRequest) {
    xmlHttp = new XMLHttpRequest();
  } else if (window.ActiveXObject) {
    // eslint-disable-next-line
    xmlHttp = new ActiveXObject("Microsoft.xmlHttp");
  } else {
    console.log("Your browser does not support xmlHttp.");
    return;
  }
  xmlHttp.onreadystatechange = function () {
    if (xmlHttp.readyState == 4) {
      var result = '';
      try {
        result = JSON.parse(xmlHttp.responseText);
      } catch (error) {
        result = xmlHttp.responseText;
      }
      if (xmlHttp.status == 200) {
        if (typeof callback == 'function') {
          callback(result);
        }
      } else {
        if (typeof errorCallback == 'function') {
          errorCallback(result);
        } else {
          result.message && alert(result.message)
        }
      }
    }
  };
  xmlHttp.open("post", url, false);
  xmlHttp.send(form);
}
export default {
  components: { VueCropper },
  props: {
    imgUrl: String,
    fixedNumber: Array
  },
  data() {
    return {
      showCropper: true,
      option: {
        img: '',
        size: 1,
        fixed: true,
        centerBox: false,
        fixedNumber: [1.875, 1],
        outputType: 'png',
        infoTrue: false,
        fixedBox: true,
        autoCropWidth:480,
        autoCropHeight:120,
        enlarge:4,
        original:true
      }, 
      previews: {},
      previewStyle: {}
    }
  },
  created() {
    if(this.imgUrl) {
      this.option.img = this.imgUrl;
    }
    if(this.fixedNumber) {
      this.option.fixedNumber = this.fixedNumber;
    }
  },
  methods: {
    showDialog(){
      this.showCropper = true;
    },
    setCropperImg(res) {
      this.option.img = res.data;
    },
    setSize(size) {
      this.$refs.cropper.changeScale(size);
    },
    setRotate(type) {
      if(type === 1) {
        this.$refs.cropper.rotateRight();
      } else {
        this.$refs.cropper.rotateLeft();
      }
    },
    realTime(data) {
      let previews = data;
      let h = 0.5;
      this.previewStyle = {
        width: previews.w + "px",
        height: previews.h + "px",
        overflow: "hidden",
        margin: "0",
        zoom: h
      };
      this.previews = data;
    },
    dialogCropper() {
      // this.$refs.cropper.getCropData((data) => {
      //   // do something
      //   console.log(data)  
      // });
      this.$refs.cropper.getCropBlob((data) => {
        var formData = new window.FormData();
        formData.append('uploadType', 2);
        formData.append('file', data, '品牌海报');
        let url = core.api.static.upload;
        if (location.hostname == 'localhost' && url.substring(0, 4) != 'http' && location.port != '9050') {
            url = `/api/${url}`;
        }
        sendForm(formData, url, (result) => {
          this.$emit('dialogCropper', result.data);
        });
      });
    },
    cancelCropper() {
      this.showCropper = true;
      this.$emit('dialogCropperCancel');
    }
  }
}
</script>

<style lang="scss">
  .cropper-content{
    .dialog-form{
      .vue-cropper{
        display: inline-block;
        vertical-align: top;
        width:600px;
        height:400px;
        .crop-info{
          display: none;
        }
      }
      .cropper-set{
        display: inline-block;
        vertical-align: top;
        width: 300px;
        margin-left: 20px;
        .upload-img{
          display: inline-block;
          vertical-align: top;
          height: 30px;
          padding: 0 15px;
          color: #F06941;
          border: 1px dashed #F06941;
          line-height: 30px;
          border-radius: 15px;
        }
        h2{
          color: #333;
          font-weight: bold;
          font-size: 14px;
          padding: 15px 0 10px;
        }
        .set-row{
          margin-bottom: 10px;
          .btn-fill-main{
            margin-right: 15px;
          }
        }
        .previews-box{
          img{
            max-width: 300px;
            max-height: 160px;
          }
        }
      }
    }
  }
</style>
