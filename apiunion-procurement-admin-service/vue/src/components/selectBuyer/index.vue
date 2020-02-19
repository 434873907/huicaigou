<template>
  <div class="select-brand">
    <el-dialog title="采购商选择:" :visible.sync="buyerShow" width="815px" :before-close="cancelBuyer">
        <div class="dialog-form border-bottom">
          <div class="search-item">
            <span class="title">采购商筛选：</span>
            <input type="text" class="form-control" placeholder="请输入采购商名称" v-model="buyerName" @keyup.enter="getBuyer" />
          </div>
        </div>
        <div class="dialog-form">
          <div class="search-item selected－brand">
            <span class="title">已选采购商：</span>
            <span class="selected-item" v-for="(item, index) in selectedBuyerList" :key="index+'selected'">
              {{item.userName}}
              <a href="javascript:;" @click="removeSelect(index)">X</a>
            </span>
          </div>
          <div class="search-item selected-box">
            <span class="brand-item" v-for="(item, index) in searchBuyerList" :key="index+'buyer'">
              <a class="link" @click="selectItem(item)">{{item.userName}}</a>
            </span>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <button class="btn-main" @click="exportBuyer()">保存</button>
          <button class="btn-fill-grey-main" @click="cancelBuyer()">取消</button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'selectBuyer',
  data() {
    return {
      buyerShow: true,
      searchBuyerList: [],
      selectedBuyerList: [],
      buyerName: ''
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      // 获取采购商列表
      this.getBuyer(); 
    },
    getBuyer() {
      this.api.goods.buyer({name: this.buyerName}).then((res) => {
        this.searchBuyerList = res.data.dataList;
      });
    },
    selectItem(item) {
      if(this.selectedBuyerList.length>0){
        let exist = false;
        this.selectedBuyerList.forEach((buyer) => {
          if(buyer.userName === item.userName){
            exist = true;
          }
        });
        if(!exist){
          this.selectedBuyerList.push(item);
        }
      } else {
        this.selectedBuyerList.push(item);
      }
    },
    removeSelect(index) {
      this.selectedBuyerList.splice(index, 1);
    },
    exportBuyer() {
      this.$emit('dialogBuyer', { buyerList: this.selectedBuyerList });
    },
    cancelBuyer() {
      this.buyerShow = true;
      this.$emit('dialogBuyerCancel');
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