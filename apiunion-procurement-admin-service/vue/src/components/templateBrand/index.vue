<template>
  <div class="select-brand">
    <el-dialog title="品牌选择:" :visible.sync="brandShow" width="815px" :before-close="cancelBrand">
        <div class="dialog-form border-bottom">
          <div class="search-item">
            <span class="title">品牌筛选：</span>
            <input type="text" class="form-control" placeholder="请输入品牌名称" v-model="brandName" @keyup.enter="getBrand" />
          </div>
        </div>
        <div class="dialog-form">
          <div class="search-item selected－brand">
            <span class="title">已选品牌：</span>
            <span class="selected-item" v-for="(item, index) in selectedBrandList" :key="index+'selected'">
              {{item.nameZh}}
              <a href="javascript:;" @click="removeSelect(index)">X</a>
            </span>
          </div>
          <div class="search-item selected-box">
            <span class="brand-item" v-for="(item, index) in searchBrandList" :key="index+'brand'">
              <a class="link" @click="selectItem(item)">{{item.nameZh}}</a>
            </span>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <button class="btn-main" @click="exportBrand()">{{isNext?'下一步':'确定'}}</button>
          <button class="btn-fill-grey-main" @click="cancelBrand()">取消</button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'selectBrand',
  props: {
    multiply: {   // 品牌多选:true, 类目单选:false, 默认多选
      default: true
    },
    selected: {   // 打开模态框前已选择的品牌
      default: ''
    },
    isNext:{
      default:false
    }
  },
  data() {
    return {
      brandShow: true,
      searchBrandList: [],
      selectedBrandList: [],
      brandName: '',
      currentLetter: '',
    }
  },
  created() {
    this.init();
  },
  methods: {
    showDialog(){
      this.brandShow = true;
    },
    init() {
      if(this.selected.length>0) {
        this.selectedBrandList = this.copy(this.selected);
      }
      // 获取品牌列表
      this.getBrand();
    },
    getBrand() {
      this.api.template.queryBrandLibraryList({brandName: this.brandName,countryCode:''}).then((res) => {
        this.searchBrandList = res.data.dataList;
      });
    },
    selectLetter(letter) {
      if(this.currentLetter === letter){
        this.currentLetter = '';
      }else{
        this.currentLetter = letter;
      }
      this.getBrand();
    },
    selectItem(item) {
      if(this.multiply) {
        if(this.selectedBrandList.length>0){
          let exist = false;
          this.selectedBrandList.forEach((brand) => {
            if(brand.id === item.id){
              exist = true;
            }
          });
          if(!exist){
            this.selectedBrandList.push(item);
          }
        } else {
          this.selectedBrandList.push(item);
        }
      } else {
        this.selectedBrandList = [];
        this.selectedBrandList.push(item);
      }
    },
    removeSelect(index) {
      this.selectedBrandList.splice(index, 1);
    },
    exportBrand() {
      if(this.selectedBrandList.length===0) {
        this.$message.info({message:'请选择品牌', showClose:true});
        return
      }
      console.log(this.selectedBrandList)
      this.$emit('dialogBrand', { brandList: this.selectedBrandList });
    },
    cancelBrand() {
      this.brandShow = true;
      this.$emit('dialogBrandCancel');
    }
  }
}
</script>

<style lang='scss' scoped>
.select-brand {
  .border-bottom{
    border-bottom: 1px solid $border-color;
  }
  .dialog-form{
    padding-left: 0;
    .search-item{
      padding-bottom: 10px;
      span{
        display: inline-block;
        vertical-align: top;
        line-height: 30px;
      }
      .title{
        color: #333;
        font-weight: bold;
        font-size: 14px;
        width: 82px;
      }
      .form-control{
        width: 670px;
      }
      .letter{
        cursor: pointer;
        margin-right: 14px;
        &.active{
          color: #F06941;
        }
      }
      .selected-item{
        margin-top: 10px;
        height: 22px;
        line-height: 22px;
        padding: 0 20px 0 8px;
        border-radius: 22px;
        background: $main-color;
        color: #fff;
        margin-right: 10px;
        font-size: 12px;
        max-width: 200px;
        overflow: hidden;
        text-overflow: ellipsis;
        position: relative;
        a{
          position: absolute;
          color: #fff;
          font-size: 12px;
          top: 0;
          right: 10px;
        }
      }
    }
    .selected－brand{
      padding-top: 10px;
      padding-bottom: 0;
    }
    .selected-box{
      max-height: 200px;
      overflow: auto;
      .brand-item{
        display: inline-block;
        vertical-align: top;
        width: 20%;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        .link{
          display: inline-block;
          vertical-align: top;
          line-height: 30px;
          color: #333;
          border-radius: 30px;
          padding: 0 8px;
          border: 1px solid #fff;
          max-width: 100%;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          &:hover{
            color: $main-color;
            border: 1px solid $main-color;
          }
        }
      }
    }
  }
}
</style>
