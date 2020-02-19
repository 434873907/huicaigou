<template>
  <div class="select-brand">
    <el-dialog title="供应商选择:" :visible.sync="supplierShow" width="815px" :before-close="cancelSupplier">
        <div class="dialog-form border-bottom">
          <div class="search-item">
            <span class="title">供应商筛选：</span>
            <input type="text" class="form-control" placeholder="请输入供应商名称" v-model="supplierName" @keyup.enter="getSupplier" />
          </div>
        </div>
        <div class="dialog-form">
          <div class="search-item selected－brand">
            <span class="title">已选供应商：</span>
            <span class="selected-item" v-for="(item, index) in selectedSupplierList" :key="index+'selected'">
              {{item.supplierName}}
              <a href="javascript:;" @click="removeSelect(index)">X</a>
            </span>
          </div>
          <div class="search-item selected-box">
            <span class="brand-item" v-for="(item, index) in searchSupplierList" :key="index+'supplier'">
              <a class="link" @click="selectItem(item)">{{item.supplierName}}</a>
            </span>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <button class="btn-main" @click="exportSupplier()">确定</button>
          <button class="btn-fill-grey-main" @click="cancelSupplier()">取消</button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'selectSupplier',
  data() {
    return {
      supplierShow: true,
      searchSupplierList: [],
      selectedSupplierList: [],
      supplierName: ''
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    showDialog(){
      this.supplierShow = true;
    },
    init() {
      // 获取供应商列表
      this.getSupplier();
    },
    getSupplier() {
      this.api.goods.supplier({name: this.supplierName}).then((res) => {
        this.searchSupplierList = res.data;
      });
    },
    selectItem(item) {
      if(this.selectedSupplierList.length>0){
        let exist = false;
        this.selectedSupplierList.forEach((supplier) => {
          if(supplier.id === item.id){
            exist = true;
          }
        });
        if(!exist){
          this.selectedSupplierList.push(item);
        }
      } else {
        this.selectedSupplierList.push(item);
      }
    },
    removeSelect(index) {
      this.selectedSupplierList.splice(index, 1);
    },
    exportSupplier() {
      this.$emit('dialogSupplier', { supplierList: this.selectedSupplierList });
    },
    cancelSupplier() {
      this.supplierShow = true;
      this.$emit('dialogSupplierCancel');
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
        width: 100px;
      }
      .form-control{
        width: 660px;
      }
      .letter{
        cursor: pointer;
        margin-right: 14px;
        &.active{
          color: #F06941;
        }
      }
      .selected-item{
        margin-top: 4px;
        height: 22px;
        line-height: 22px;
        padding: 0 8px;
        border-radius: 22px;
        background: $main-color;
        color: #fff;
        margin-right: 10px;
        font-size: 12px;
        a{
          color: #fff;
          font-size: 12px;
          line-height: 22px;
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
