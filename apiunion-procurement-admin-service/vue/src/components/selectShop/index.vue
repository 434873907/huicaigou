<template>
  <div class="select-shop">
    <el-dialog title="店铺选择:" :visible.sync="shopShow" width="815px" :before-close="cancelShop">
        <div class="dialog-form border-bottom">
          <div class="search-item">
            <span class="title">店铺名称：</span>
            <input type="text" class="form-control" placeholder="请输入店铺名称" v-model="shopName" @keyup.enter="getShop" />
          </div>
        </div>
        <div class="dialog-form">
          <div class="search-item selected－shop">
            <span class="title">已选店铺：</span>
            <span class="selected-item" v-for="(item, index) in selectedShopList" :key="index+'selected'">
              {{item.name}}
              <a href="javascript:;" @click="removeSelect(index)">X</a>
            </span>
          </div>
          <div class="search-item selected-box">
            <span class="shop-item" v-for="(item, index) in searchShopList" :key="index+'shop'">
              <a class="link" @click="selectItem(item)">{{item.name}}</a>
            </span>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <button class="btn-main" @click="exportShop()">确定</button>
          <button class="btn-fill-grey-main" @click="cancelShop()">取消</button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'selectShop',
  props: {
    multiply: {   // 店铺多选:true, 店铺单选:false, 默认多选
      default: true
    }
  },
  data() {
    return {
      shopShow: true,
      searchShopList: [],
      selectedShopList: [],
      shopName: ''
    }
  },
  created() {
    this.init();
  },
  methods: {
    showDialog(){
      this.shopShow = true;
    },
    init() {
      // 获取店铺列表
      this.getShop(); 
    },
    getShop() {
      this.api.store.allShop({name: this.shopName}).then((res) => {
        this.searchShopList = res.data;
      });
    },
    selectItem(item) {
      if(this.multiply) {
        if(this.selectedShopList.length>0){
          let exist = false;
          this.selectedShopList.forEach((shop) => {
            if(shop.id === item.id){
              exist = true;
            }
          });
          if(!exist){
            this.selectedShopList.push(item);
          }
        } else {
          this.selectedShopList.push(item);
        }
      } else {
        this.selectedShopList = [];
        this.selectedShopList.push(item);
      }
    },
    removeSelect(index) {
      this.selectedShopList.splice(index, 1);
    },
    exportShop() {
      if(this.selectedShopList.length==0) {
        this.$message.info({message:'请选择店铺', showClose:true});
        return
      }
      this.$emit('dialogShop', { shopList: this.selectedShopList });
    },
    cancelShop() {
      this.shopShow = true;
      this.$emit('dialogShopCancel');
    }
  }
}
</script>

<style lang='scss' scoped>
.select-shop {
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
    .selected－shop{
      padding-top: 10px;
      padding-bottom: 0;
    }
    .selected-box{
      max-height: 200px;
      overflow: auto;
      .shop-item{
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