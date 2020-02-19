<template>
  <div class="upload-container">
    <button class="btn-fill-main" @click="dialogVisible=true">上传图片
    </button>
    <el-dialog :visible.sync="dialogVisible" title="图片上传">
      <div class="dialog-form">
        <div class="img-item" v-for="(item, index) in arr" :key="index+'img'">
          <img :src="item.url"/>
          <img src="~assets/goods/remove_spec.png" class="remove-img" @click="removeImg(index)" />
        </div>
        <a href="javascript:;" class="addGoodsImg">
          <upload uploadType='2' v-on:uploadSuccess="uploadSuccess">
            <img src="~/assets/goods/addGoodsImg.png"/>
          </upload>
        </a>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="handleSubmit">确定</button>
        <button class="btn-fill-grey-main" @click="dialogVisible = false">取消</button>
      </span>
      <!-- <button @click="dialogVisible = false">取 消</button>
      <button type="primary" @click="handleSubmit">确 定</button> -->
    </el-dialog>
  </div>
</template>

<script>
// import { getToken } from 'api/qiniu'

export default {
  name: 'EditorSlideUpload',
  data() {
    return {
      dialogVisible: false,
      listObj: {},
      fileList: [],
      arr: []
    }
  },
  methods: {
    uploadSuccess(res) {
      this.arr.push({
        name: '',
        url: res.data
      });
    },
    removeImg(index) {
      this.arr.splice(index, 1);
    },
    handleSubmit() {
      this.$emit('successCBK', this.arr);
      this.dialogVisible = false;
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
// .editor-slide-upload {
//   margin-bottom: 20px;
//   /deep/ .el-upload--picture-card {
//     width: 100%;
//   }
// }
.upload-container{
  .btn-fill-main{
    height: 26px;
    line-height: 24px;
  }
  .dialog-form{
    .addGoodsImg{
      display: inline-block;
      vertical-align: top;
      width: 100px;
      height: 100px;
      text-align: center;
      border: 1px dashed $border-color;
      cursor: pointer;
      .upload-container{
        height: 100%;
        .image-uploader{
          height: 100%;
          .el-upload{
            width: 100%;
            height: 100%;
          }
        }
      }
      img{
        margin-top: 40px;
      }
    }
    .img-item {
      display: inline-block;
      vertical-align: top;
      width: 100px;
      height: 100px;
      margin-right: 20px;
      position: relative;
      img {
          height: 100px;
          width: 100px;
          object-fit:contain;
      }
      .remove-img{
        position: absolute;
        width: 20px;
        height: 20px;
        border-radius: 20px;
        right: -10px;
        top: -10px;
        cursor: pointer;
        border: none;
      }
    }
  }
}
</style>
