<template>
  <div class="center-goods-stock">
    <div class="tab-bar">
      <div class="tab-item" :class="{'active':listParams.status===1}" @click="changeTab(1)">全部</div>
      <!-- <div class="tab-item" :class="{'active':listParams.status===2}" @click="changeTab(2)">库存警告</div> -->
      <div class="tab-item" :class="{'active':listParams.status===3}" @click="changeTab(3)">已下架</div>
    </div>
    <div class="list-wrap">
      <div class="search-bar">
        <div class="search-bar-title">商品属性：</div>
        <button class="btn-fill-main" @click="openDialog('showSupplierModal')">选择供应商</button>
        <span class="btn-small" v-for="(item, index) in searchSupplierList" :key="index+'supplier'">
          {{item.supplierName}}
          <a href="javascript:;" @click="removeSearchSupplier(index)">X</a>
        </span>
        <button class="btn-fill-org-main" @click="openDialog('showBrandModal')">选择品牌</button>
        <span class="btn-org-small" v-for="(item, index) in searchBrandList" :key="index+'brand'">
          {{item.name}}
          <a href="javascript:;" @click="removeSearchBrand(index)">X</a>
        </span>
        <div class="search-bar-title">关键字：</div>
        <div class="form-wrap">
          <select class="form-control" v-model="listParams.keywordType" :class="{'unset-select':!listParams.keywordType}">
            <option v-for="(item, index) in keywordTypeList" v-bind:value="item.id" :key="index+'channel'">{{item.name}}</option>
          </select>
          <input type="text" class="form-control" v-model="listParams.keyword" @keyup.enter="getList(1)">
        </div>
        <select class="form-control" v-model="listParams.channelType" :class="{'unset-select':!listParams.channelType}">
          <option v-for="(item, index) in channelList" v-bind:value="item.channelType" :key="index+'channel'">{{item.channelTypeName}}</option>
        </select>
        <button class="btn-fill-main search-btn" @click="getList(1)">搜索</button>
        <button class="btn-fill-grey-main" @click="clearSearch()">清空</button>
      </div>
      <table class="table">
        <thead>
          <tr>
            <th class="table-w80">商品ID</th>
            <th class="table-w80">主图</th>
            <th class="table-w200">商品名称</th>
            <th class="table-w110">品牌</th>
            <th class="table-w130">型号</th>
            <th class="table-w120">条形码</th>
            <th class="table-w120" v-show="!userConfig.singleChannel">渠道</th>
            <th class="table-w220">供应商</th>
            <th class="table-w80">库存</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in listData" :key="item.itemId">
            <td>{{item.itemId}}</td>
            <td>
              <el-popover
                placement="top"
                width="200"
                trigger="hover" class="imgBoxCont">
                <img style="width:100%;height:100%" :src="item.imageUrl">
                <img slot="reference" class="imgBox" :src="item.imageUrl" />
              </el-popover>
            </td>
            <td>
              <el-popover
                placement="top"
                width="200"
                trigger="hover">
                <span>{{item.itemName}}</span>
                <span slot="reference"  class="productName">{{item.itemName}}</span>
              </el-popover>
            </td>
            <td>
              <el-popover
                placement="top"
                width="200"
                trigger="hover">
                <span>{{item.brand}}</span>
                <span slot="reference"  class="ellipsis">{{item.brand}}</span>
              </el-popover>
            </td>
            <td>
              <el-popover 
                placement="top"
                width="200"
                trigger="hover">
                <span>{{item.skuValue}}</span>
                <span slot="reference" class="ellipsis">{{item.skuValue}}</span>
              </el-popover>
            </td>
            <td>{{item.skuInventoryList[0].upc}}</td>
            <td v-show="!userConfig.singleChannel">
              <el-popover 
                placement="top"
                width="200"
                trigger="hover">
                <span>{{item.channel}}</span>
                <span slot="reference" class="ellipsis">{{item.channel}}</span>
              </el-popover>
            </td>
            <td>
              <el-popover
                placement="top"
                width="200"
                trigger="hover">
                <span>{{item.supplierNames}}</span>
                <span slot="reference" class="ellipsis">{{item.supplierNames}}</span>
              </el-popover>
            </td>
            <td>{{item.inventoryNum}}</td>
            <td>
              <el-popover popper-class="popper-goodsstock"
                placement="bottom-end"
                width="770"
                trigger="click">
                <table class="table table-text seek-export-table">
                  <tbody>
                    <tr v-for="(sup, sup_index) in item.skuInventoryList" :key="sup_index+'skuInventory'">
                      <td class="table-w120" v-show="userConfig.singleChannel"></td>
                      <td class="table-w160" :class="{'skuValueBox':!userConfig.singleChannel}">{{sup.skuValue}}</td>
                      <td class="table-w120">{{sup.upc}}</td>
                      <td class="table-w120" v-show="!userConfig.singleChannel">{{sup.channel}}</td>
                      <td class="table-w120">
                        <el-popover 
                          placement="top"
                          width="260"
                          trigger="hover">
                          <p v-for="(supInv, supInv_index) in sup.supplierInventory" :key="supInv_index+'supInventory'">{{supInv}}</p>
                          <span slot="reference" class="ellipsis">{{sup.supplierNames}} <i class="el-icon-warning"></i></span>
                        </el-popover>
                      </td>
                      <td class="table-spe table-w80">{{sup.inventoryNum}}</td>
                    </tr>
                  </tbody>
                </table>
                <a slot="reference" class="link seek-export">展开查看</a>
              </el-popover>
            </td>
          </tr>
        </tbody>
      </table> 
      <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
      <noData v-if="total===0"></noData>
    </div>
    <SelectBrand v-if="showBrandModal" ref="showBrandModal" v-on:dialogBrand="dialogBrand" v-on:dialogBrandCancel="dialogBrandCancel"></SelectBrand>
    <SelectSupplier v-if="showSupplierModal" ref="showSupplierModal" v-on:dialogSupplier="dialogSupplier" v-on:dialogSupplierCancel="dialogSupplierCancel"></SelectSupplier>
  </div>
</template>
<script>
import selectBrand from '@/components/selectBrand/index.vue';
import selectSupplier from '@/components/selectSupplier/index.vue';
import Pagination from '@/components/pagination';
import noData from '@/components/noData';
export default {
  components: {
    'SelectBrand': selectBrand,
    'SelectSupplier': selectSupplier,
    'Pagination': Pagination,
    'noData': noData
  },
  data() {
    return {
      showSupplierModal: false,
      showBrandModal: false,
      searchSupplierList: [],
      searchBrandList: [],
      keywordTypeList: [
        {id:'',name:'请选择关键词类型'},
        {id:1,name:'商品ID'},
        {id:2,name:'商品名称'},
        {id:3,name:'条形码'}
      ],
      channelList: [{channelType:'', channelTypeName:'请选择商品渠道'}],
      listParams: {
        page: 1,
        limit: 20,
        status: 1,
        keywordType: '',
        keyword: '',
        channelType: ''
      },
      total: -1,
      listData: []
    }
  },
  created() {
    this.init();
  },
  methods: {
    openDialog(index){
      this[index] = true;

    },
    init() {
      // 获取渠道列表
      this.getChannelList();
      this.getList(1);
    },
    getChannelList() {
      this.api.goods.channel({}).then((res) => {
        this.channelList = this.channelList.concat(res.data);
      });
    },
    getList(page) {
      this.listParams.page = page;
      let params = this.copy(this.listParams);
      if(!params.keywordType && params.keyword) {
        this.$message.info({message: '请选择关键词类型', showClose:true});
        return
      }
      if(!params.keywordType && !params.keyword) {
        delete params.keywordType;
        delete params.keyword;
      }
      if(this.searchBrandList.length>0){
        let brandId = [];
        this.searchBrandList.forEach((item) => {
          brandId.push(item.id);
        });
        params.brandIds = brandId.join(',');
      }
      if(this.searchSupplierList.length>0){
        let supplier = [];
        this.searchSupplierList.forEach((item) => {
          supplier.push(item.id);
        });
        params.supplierIds = supplier.join(',');
      }
      this.api.order.inventory(params).then((res) => {
        this.listData = res.data.dataList;
        this.total = res.data.total;
      });
    },
    clearSearch() {
      this.listParams = {
        page: 1,
        limit: 20,
        status: 1,
        keywordType: '',
        keyword: '',
        channelType: ''
      }
      this.searchBrandList = [];
      this.searchSupplierList = [];
      this.getList(1);
    },
    changeTab(tab) {
      this.listParams.status = tab;
      this.getList(1);
    },
    dialogBrand(res) {
      this.searchBrandList = res.brandList;
      this.showBrandModal = false;
    },
    dialogBrandCancel() {
      this.showBrandModal = false;
    },
    removeSearchBrand(index) {
      this.searchBrandList.splice(index, 1);
    },
    dialogSupplier(res) {
      this.searchSupplierList = res.supplierList;
      this.showSupplierModal = false;
    },
    dialogSupplierCancel() {
      this.showSupplierModal = false;
    },
    removeSearchSupplier(index) {
      this.searchSupplierList.splice(index, 1);
    }
  }
}
</script>
<style scoped lang='scss'>
  .center-goods-stock{
    .search-bar{
      .search-btn{
        margin-left: 20px;
      }
      .btn-fill-main{
        margin-right: 10px;
      }
      .btn-fill-org-main{
        margin-right: 10px;
      }
      .btn-small{
        margin-right: 10px;
        a{
          color: #fff;
        }
      }
      .btn-org-small{
        margin-right: 10px;
        a{
          color: #fff;
        }
      }
    }
    .table{
      .seek-export{
        display: inline-block;
        vertical-align: top;
        width: 56px;
        overflow: visible;
      }
      .productName{
        width:180px;
        max-height:40px;
        margin:16px 0px 16px 0px;
        line-height: 20px;
        word-break: break-all;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        overflow: hidden;
    }
    }
  }
  .seek-export-table{
    // padding-left: 10px;
    .skuValueBox{
      padding-left:30px;
    }
    .table-w160{
      width: 160px;
    }
    .table-w120{
      width: 120px;
    }
    .table-w100{
      width: 100px;
    }
    .table-spe{
      padding-left: 101px;
    }
  }
</style>
<style lang='scss'>
  .popper-goodsstock{
    left: 607px!important;
  }
</style>