<template>
  <div class="center-commodity">
    <div class="tab-bar">
      <div class="tab-item" :class="{'active':listParams.status===0}" @click="changeStatus(0)">全部商品</div>
      <div class="tab-item" :class="{'active':listParams.status===1}" @click="changeStatus(1)">已上架</div>
      <div class="tab-item" :class="{'active':listParams.status===2}" @click="changeStatus(2)">已下架</div>
    </div>
    <div class="type-wrap" v-show="firstCateList.length>0">
      <div class="type-content-wrap">
        <div class="type-title">一级类目：</div>
        <div class="type-content border-bottom">
          <div class="type-item" v-for="(item, index) in firstCateList" :key="index+'first'" :class="{'active':selectedCate.first===item.id}">
            <a href="javascript:;" class="ellipsis" :class="{'pr40':item.count>99}" @click="selectCate(1, item)">
              {{item.name}}({{item.count}})
            </a>
          </div>
        </div>
      </div>
      <div class="type-content-wrap" v-show="secondCateList.length>0">
        <div class="type-title">二级类目：</div>
        <div class="type-content">
          <div class="type-item" v-for="(item, index) in secondCateList" :key="index+'second'" :class="{'active':selectedCate.second===item.id}">
            <a href="javascript:;" class="ellipsis" :class="{'pr40':item.count>99}" @click="selectCate(2, item)">
              {{item.name}}({{item.count}})
            </a>
          </div>
        </div>
      </div>
      <div class="type-content-wrap" v-show="thirdCateList.length>0">
        <div class="type-title">三级类目：</div>
        <div class="type-content">
          <div class="type-item" v-for="(item, index) in thirdCateList" :key="index+'third'" :class="{'active':selectedCate.third===item.id}">
            <a href="javascript:;" class="ellipsis" :class="{'pr40':item.count>99}" @click="selectCate(3, item)">
              {{item.name}}({{item.count}})
            </a>
          </div>
        </div>
      </div>
    </div>
    <div class="type-wrap">
      <selSupplierList ref="selSupplier"></selSupplierList>
    </div>
    <div class="list-wrap">
      <div class="search-bar">
        <div class="search-bar-title">商品属性：</div>
        <button class="btn-fill-org-main" @click="openDialog('showBrandModal')">选择品牌</button>
        <span class="btn-org-small" v-for="(item, index) in searchBrandList" :key="index+'brand'">
          {{item.name}}
          <a href="javascript:;" @click="removeSearchBrand(index)">X</a>
        </span>
        <button class="btn-fill-org-main" @click="openDialog('showShopModal')">选择店铺</button>
        <span class="btn-org-small btn-select-shop-item" v-for="(item, index) in searchShopList" :key="index+'shop'">
          {{item.name}}
          <a href="javascript:;" @click="removeSearchShop(index)">X</a>
        </span>
        <select class="form-control" v-model="listParams.itemChannelTypes" :class="{'unset-select':!listParams.itemChannelTypes}" v-show="!userConfig.singleChannel">
          <option v-for="(item, index) in channelList" v-bind:value="item.channelType" :key="index+'channel'">{{item.channelTypeName}}</option>
        </select>
      </div>
      <div class="search-bar">
        <div class="search-bar-title">其他筛选：</div>
        <div class="form-wrap">
          <div class="form-name">关键字</div>
          <select class="form-control" v-model="listParams.keywordType" :class="{'unset-select':!listParams.keywordType}">
            <option v-for="(item, index) in keywordTypeList" v-bind:value="item.id" :key="index+'channel'">{{item.name}}</option>
          </select>
          <input type="text" class="form-control" v-model="listParams.keyword" @keyup.enter="search(1)">
        </div>
        <div class="form-wrap">
          <div class="form-name">价格区间</div>
          <input type="number" class="form-control priceAmount" v-model="listParams.minPrice" @keyup.enter="search(1)">
          <span class="price-mid">~</span>
          <input type="number" class="form-control priceAmount" v-model="listParams.maxPrice" @keyup.enter="search(1)">
        </div>
        <div class="form-wrap">
          <div class="form-name">库存区间</div>
          <input type="number" class="form-control priceAmount" v-model="listParams.minSupplierStock" @keyup.enter="search(1)">
          <span class="price-mid">~</span>
          <input type="number" class="form-control priceAmount" v-model="listParams.maxSupplierStock" @keyup.enter="search(1)">
        </div>
        <select v-if="listParams.status===0" class="form-control" v-model="listParams.approveType" :class="{'unset-select':!listParams.approveType}">
          <option value="0" selected disabled style="display: none;">请选择审核类型</option>
          <option v-for="(item, index) in approveTypeList" v-bind:value="item.id" :key="index+'id'">{{item.name}}</option>
        </select>
        <select v-if="(listParams.status===1||listParams.status===2)&&userConfig.key!='guojiu'" class="form-control" v-model="listParams.supplierOfflineType" :class="{'unset-select':!listParams.supplierOfflineType}">
          <option value="0" selected disabled style="display: none;">请选择销售关闭原因</option>
          <option v-for="(item, index) in supplierOfflineTypeList" v-bind:value="item.id" :key="index+'id'">{{item.name}}</option>
        </select>
        <button class="btn-fill-main" @click="search(1)">搜索</button>
        <button class="btn-fill-grey-main" @click="clearSearch">清空</button>
      </div>
      <div class="control-wrap">
        <div class="control-item" v-if="listParams.status!==0">
          <!--          新增隐藏掉-->
          <router-link  :to="{ name: '商品详情',params:{status:listParams.status}}" class="btn-fill-org-main" v-if="userConfig.key=='guojiu'">新增商品</router-link>
          <!-- <div class="btn-fill-main">批量导入</div> -->
<!--          <div class="setGroup btn-fill-org-main" @click="setGroup()" v-if="userConfig.hasItemGroup">设为同组</div>-->
          <div class="btn-fill-main" @click="batchSetStatus(1)" v-show="listParams.status===2">批量上架商品</div>
          <div class="btn-fill-main" @click="batchSetStatus(2)" v-show="listParams.status===1">批量下架商品</div>
        </div>
        <div class="btn-fill-main" style="margin-left:10px;" @click="openExportModal()">导出商品明细</div>
      </div>
      <table class="table" v-if="listParams.status!==0" ref="table">
         <thead>
           <tr>
             <th class="dot" :class="{'active':checkAll}" @click="selectAll()"></th>
             <th class="table-w60">商品ID</th>
             <th class="table-w80">主图</th>
             <th class="table-w200">商品名称</th>
             <th class="table-w120">品牌</th>
             <th class="table-w200">规格</th>
             <th class="table-w100">价格</th>
             <th class="table-w140" v-show="!userConfig.singleChannel">发货方式</th>
<!--             <th class="table-w80" v-if="userConfig.hasItemGroup">是否分组</th>-->
             <th class="table-w160" v-if="listParams.status==1">上架时间</th>
             <th class="table-w160" v-if="listParams.status==2">下架时间</th>
             <th>操作</th>
           </tr>
         </thead>
         <tbody>
          <tr v-for="(item, index) in tableData" :key="index+'table'" :class="{'selectedTr':item.checked}">
            <td class="dot" :class="{'active':item.checked}" @click="selectItem(item)"></td>
            <td>{{item.id}}</td>
            <td>
              <el-popover
                placement="top"
                width="200"
                trigger="hover" class="imgBoxCont">
                <img style="width:100%;height:100%" :src="item.mainImageUrl">
                <img slot="reference" class="imgBox" :src="item.mainImageUrl" />
              </el-popover>
            </td>
            <td>
              <el-popover
                placement="top"
                width="200"
                trigger="hover">
                <span >{{item.name}}</span>
                <span class="productName cursor"  slot="reference" @click="editItem(item)">{{item.name}}</span>
              </el-popover>
            </td>
            <td><span class="brandName">{{item.brandName}}</span></td>
            <td>
              <el-popover
                placement="top"
                width="200"
                trigger="hover">
                <span class="linkWrap">
                  {{item.skuListStr}}
                </span>
                <span slot="reference" class="productName cursor">
                  {{item.skuListStr}}
                </span>
              </el-popover>
            </td>
            <td class="price">{{item.price}}</td>
            <td v-show="!userConfig.singleChannel">
                <el-popover
                  placement="top"
                  width="260"
                  trigger="hover">
                  <span>
                  <span v-for="(sku, channels_index) in item.channels" :key="channels_index+'sku'">
                      {{sku}}
                      <span v-show="channels_index != item.channels.length-1">、</span>
                  </span>
                  </span>
                  <span slot="reference" class="transportStyle ellipsis">
                      <span v-for="(sku, sku_index) in item.channels" :key="sku_index+'sku'">
                          {{sku}}
                          <span v-show="sku_index != item.channels.length-1">、</span>
                      </span>
                  </span>
              </el-popover>
            </td>
<!--            <td v-if="userConfig.hasItemGroup"><span v-if="item.groupId">是</span><span v-else>否</span></td>-->
            <td v-if="listParams.status==1"> <span class="linkWrap myTime">{{item.onlineTime}}</span> </td>
            <td v-if="listParams.status==2"> <span class="linkWrap myTime">{{item.offlineTime}}</span> </td>
            <td>
              <span class="linkWrapEdit">
                <a href="javascript:;" class="link base-margin-right" @click="editItem(item)">编辑</a>
                <a href="javascript:;" class="link base-margin-right" v-show="item.status===2" @click="setStatus(item, 1)">上架</a>
                <a href="javascript:;" class="link base-margin-right" v-show="item.status===1" @click="setStatus(item, 2)">下架</a>
                <a href="javascript:;" class="link base-margin-right" @click="deleItem(item)">删除</a>
<!--                <a href="javascript:;" class="link base-margin-right"  v-show="item.status===1 && userConfig.hasItemGroup" @click="adminGroup(item, 2)">管理同组商品</a>-->
              </span>
            </td>
          </tr>
          </tbody>
      </table>
      <pagination ref="pagination" v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getGoodsList(listParams.page)" />
      <noData v-if="total===0"></noData>
    </div>
    <!-- 导出商品明细 -->
    <el-dialog title="订单导出:" :visible.sync="showExportModal" width="468px">
      <div class="dialog-form export-dialog">
        <el-progress :text-inside="true" :stroke-width="14" :percentage="exportProgress" color="#F06941"></el-progress>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="exportOrder()" v-show="!exportKey">开始导出</button>
        <a class="btn-main" @click="downloadOrder()" v-show="exportKey">点击下载</a>
        <button class="btn-fill-grey-main" @click="showExportModal=false">取消</button>
      </span>
    </el-dialog>
    <changeType ref="changeTypeComponent"  :changeTypeData = "changeTypeData" v-if="changeTypeData.dialogVisible" :cr="changeTypeData.cr" @dataCatgory="dataCatgory"></changeType>
    <exam ref="examComponent" :examData="examData" v-if="examData.dialogVisible"></exam>
    <setGroup v-if="groupData.dialogVisible" :groupData="groupData" ref="setGroupModel"></setGroup>
    <adminGoods ref="adminGoodsModel" v-if="adminGoodsData.dialogVisible" :adminGoodsData="adminGoodsData" :page="listParams"></adminGoods>
    <SelectBrand v-if="showBrandModal" ref="showBrandModal" v-on:dialogBrand="dialogBrand" v-on:dialogBrandCancel="dialogBrandCancel"></SelectBrand>
    <SelectShop v-if="showShopModal" ref="showShopModal" v-on:dialogShop="dialogShop" v-on:dialogShopCancel="dialogShopCancel"></SelectShop>
    <SelectBrand
      v-if="showBrandModalSingle"
      v-on:dialogBrand="dialogBrandSingle"
      v-on:dialogBrandCancel="showBrandModalSingle = false"
      :multiply="false"
    ></SelectBrand>
  </div>
</template>
<script>
import selectBrand from '@/components/selectBrand/index.vue';
import selectShop from '@/components/selectShop/index.vue';
import Pagination from '@/components/pagination';
import noData from '@/components/noData';
import changeType from './components/changeType.vue'
import exam from './components/exam.vue'
import setGroup from './components/group.vue'
import adminGoods from './components/adminGoods.vue'
import selSupplierList from './components/selSupplierList.vue'
let {log}=console;
export default {
  data() {
    return {
      othMass:{
        markSale:0,
        markVert:0
      },
      selIdArr:[],
      showBrandModalSingle:false,
      checkAllskus:false,
      testChecked:false,
      testRadio:"1",//要字符串的类型
      testData:new Array(2).fill({
        radio:false,name:"公司",price1:5,price2:10,store:10,rate:0.2,type:"价格变更",sale:1,check:1
      }),
      dataMass:{
        show:false, //批量审核的弹窗
      },
      testInput:"",
      showBrandModal: false,
      showSupplierModal: false,
      showShopModal: false,
      firstCateList: [],
      secondCateList: [],
      thirdCateList: [],
      searchBrandList: [],
      searchSupplierList: [],
      searchShopList: [],
      selectedCate: {
        first:'',
        second:'',
        third:''
      },
      checkAll: false,
      total: -1,
      channelList: [{channelType:'', channelTypeName:'请选择商品渠道'}],
      keywordTypeList: [
        {id:'',name:'请选择关键词类型'},
        {id:1,name:'商品ID'},
        {id:2,name:'商品名称'},
        {id:3,name:'条形码'}
      ],
      exceptItemId:[],
      listParams: {
        page: 1,
        limit: 20,
        status: 1,
        itemChannelTypes: '',
        keywordType: '',
        keyword: '',
        minPrice:'',
        maxPrice: '',
        approveType: 0,
        supplierIds:'',
        supplierOfflineType:0,
        maxSupplierStock: '',
        minSupplierStock: '',
        searchParams:{},
      },
      tableData: [],
      examData:{dialogVisible:false},
      changeTypeData:{dialogVisible:false},
      groupData:{num:0,dialogVisible:false},
      adminGoodsData:{dialogVisible:false},
      approveTypeList:[ //审核类型
        {id:0,name:'请选择审核类型'},
        {id:1,name:'新增商品'},
        {id:2,name:'新增规格'},
        {id:3,name:'新增供应商'},
        {id:4,name:'价格变更'}
      ],
      supplierOfflineTypeList:[ //供应商销售关闭类型
        {id:0,name:'请选择销售关闭原因'},
        {id:1,name:'手动关闭'},
        {id:2,name:'库存原因'},
        {id:3,name:'价格原因'}
      ],
      showExportModal: false,//导出商品明细dailog
      exportProgress: 0,//进度条
      exportKey: '',
      errorList:[],
      userConfig:{},
      systemInfo:{},
      selIndex:0
    }
  },
  created() {
    this.init();
    this.userConfig = JSON.parse(localStorage.userConfig);
    this.systemInfo = JSON.parse(localStorage.systemInfo);
  },
  methods: {
    dataCatgory(data){
      // log(data);
      this.dataMass.cateName1=data.firstName;
      this.dataMass.cateName2=data.secondName;
      this.dataMass.cateName3=data.thirdName;

      this.dataMass.cateId1=data.first;
      this.dataMass.cateId2=data.second;
      this.dataMass.cateId3=data.third;

    },
    init(val) {
      this.checkAll = false
      this.adminGoodsData.dialogVisible = false;
      this.changeTypeData.dialogVisible = false
      this.examData.dialogVisible = false
      this.groupData.dialogVisible = false
      // 获取一级类目列表
      this.getCate(0, '');
      // 初始化商品列表
      let initPage = val?val:1;
      this.getGoodsList(initPage);
      // 获取渠道列表
      this.getChannelList();
    },
    deleItem(item){
      this.$msgbox({
        title: '',
        message: '确认删除？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.goods.deleGoods({ids:item.id}).then(res=>{
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.$message.success({message:res.message, showClose:true});
            this.getGoodsList(1);
          })
        }
      });


    },
    examItem(item){
      this.examData = item
      this.$nextTick(()=>{
        this.$refs.examComponent.dialogVisible = true
      })
    },
    eidt(item){
      localStorage.setItem('groupGoodsInfo',JSON.stringify(item))
      let path = `/center/goods/commodity/detail/${item.itemId}/${this.listParams.status}`;
      window.open(path, '_blank');
    },
    openDialog(index){
      this[index] = true;
    },
    getCate(level, id,params) {
      let pId = id ? id : 0;
      this.api.category.categoryNode({parentId:pId,status:1,itemStatus:this.listParams.status,...params}).then((res) => {
          if(level===0){
              this.firstCateList = res.data;
              this.secondCateList = [];
              this.thirdCateList = [];
          } else if (level===1){
              this.secondCateList = res.data;
              this.thirdCateList = [];
          }else{
              this.thirdCateList = res.data;
          }
      });
    },
    getChannelList() {
      this.api.goods.channel({}).then((res) => {
        let arr = this.channelList.concat(res.data);
        for (let i=0, len=arr.length; i<len; i++) {
          for (let j=i+1; j<len; j++) {
              if (arr[i].channelType == arr[j].channelType) {
                  arr.splice(j, 1);
                  // splice 会改变数组长度，所以要将数组长度 len 和下标 j 减一
                  len--;
                  j--;
              }
          }
        }
        this.channelList = arr
      });
    },
    selectCate(type, item) {
      if(type === 1){
          if(this.selectedCate.first !== item.id){
              this.getCate(1, item.id,this.searchParams);
              this.selectedCate.first = item.id;
              this.selectedCate.second = '';
              this.selectedCate.third = '';
          } else {
            this.selectedCate.first = '';
            this.selectedCate.second = '';
            this.selectedCate.third = '';
            this.secondCateList = [];
            this.thirdCateList = [];
          }
      } else if (type === 2){
          if(this.selectedCate.second !== item.id){
              this.getCate(2, item.id,this.searchParams);
              this.selectedCate.second = item.id;
              this.selectedCate.third = '';
          } else {
            this.selectedCate.second = '';
              this.selectedCate.third = '';
              this.thirdCateList = [];
          }
      }else{
        if(this.selectedCate.third !== item.id) {
          this.selectedCate.third = item.id;
        } else {
          this.selectedCate.third = '';
        }
      }
      this.getGoodsList(1);
    },
    goGoodsDetail(item){
      localStorage.setItem('groupGoodsInfo',JSON.stringify(item))
      let path = ''
        path = `commodity/detail/${item.id}/${this.listParams.status}`
      window.open(path, '_blank');
    },
    editItem(item) {
      localStorage.setItem('groupGoodsInfo',JSON.stringify(item))
      // this.$emit()
      let path = ''
      if (this.listParams.status) {
        path = `commodity/detail/${item.id}/${this.listParams.status}`
      } else {
        path = `commodity/detail/${item.itemId}/${this.listParams.status}`
      }
      window.open(path, '_blank');
    },
    //上下架
    setStatus(item, status) {
      this.$msgbox({
        title: '',
        message: status === 1 ? '确认上架？' : '确认下架？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.goods.setStatus({ids: item.id, status}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getGoodsList(1);
          });
        }
      });
    },
    batchSetStatus(status) {
      let selected = [];
      this.tableData.forEach((item) => {
        if(item.checked) {
          selected.push(item.id);
        }
      });
      let ids = selected.join();
      if(!ids) {
        this.$message.info({message:'请选择至少一件商品', showClose:true});
      } else {
        this.$msgbox({
          title: '',
          message: status === 1 ? '确认批量上架？' : '确认批量下架？',
          showCancelButton: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(action => {
          if(action === 'confirm') {
            this.api.goods.setStatus({ids: ids, status}).then((res) => {
              this.$message.success({message:res.message, showClose:true});
              this.getGoodsList(1);
            });
          }
        });
      }
    },
    selectItem(item) {
      this.checkAll = true;
      this.selIdArr = [];
      item.checked = !item.checked;
      this.tableData.forEach((item) => {
        if(!item.checked) {
          this.checkAll = false;
        }else {
          this.selIdArr.push(item.itemId)
        }
      });
    },
    selectAll() {
      this.selIdArr = [];
      this.checkAll = !this.checkAll;
      this.tableData.forEach((item) => {
        if(this.checkAll) {
          item.checked = true;
          this.selIdArr.push(item.itemId)
        } else {
          item.checked = false;
          this.selIdArr = [];
        }
      });
    },
    //搜索
    search(page){
      this.selIdArr = [];
      this.listParams.supplierIds = this.$refs.selSupplier.idsList.join(',');
      this.getGoodsList(page).then(params=>{
        this.getCate(0, '',params)
      })

    },
    getGoodsList(page, status = true) {
      return new Promise((resolve,reject)=>{
        this.listParams.page = page;
        let params = this.copy(this.listParams);
        params.priceRange = JSON.stringify(params.priceRange);
        if(this.searchBrandList.length>0){
          let brandId = [];
          this.searchBrandList.forEach((item) => {
            brandId.push(item.id);
          });
          params.brandIds = brandId.join(',');
        }
        // if(this.searchSupplierList.length>0){
        //   let supplier = [];
        //   this.searchSupplierList.forEach((item) => {
        //     supplier.push(item.id);
        //   });
        //   params.supplierIds = supplier.join(',');
        // }
        if(this.searchShopList.length>0){
          let shop = [];
          this.searchShopList.forEach((item) => {
            shop.push(item.id);
          });
          params.shopIds = shop.join(',');
        }
        params.cateId = this.selectedCate.third || this.selectedCate.second || this.selectedCate.first || '';
        if(params.status!=0){
          params.statusList = JSON.stringify([params.status])
          delete params.status;
          this.searchParams = params;
          this.api.goods.list(params).then((res) => {
            res.data.dataList.forEach((item) => {
              item.checked = false;
            });
            this.tableData = res.data.dataList;
            this.total = res.data.total;
            resolve(params)
          });
        } else {
          this.searchParams = params;
          this.api.goods.unapproved(params).then(res=>{
            res.data.dataList.forEach((item) => {
              item.checked = false
            });
            this.tableData = res.data.dataList;
            this.total = res.data.total;
            resolve(params)
          })

        }
      });

    },
    clearSearch() {
      this.listParams.page = 1;
      this.listParams.limit = 20;
      this.listParams.itemChannelTypes = '';
      this.listParams.keywordType = '';
      this.listParams.keyword = '';
      this.listParams.minPrice = '';
      this.listParams.maxPrice = '';
      this.listParams.approveType = 0;
      this.listParams.supplierOfflineType = 0;
      this.listParams.maxSupplierStock = ''
      this.listParams.minSupplierStock = ''
      this.selectedCate = {
        first:'',
        second:'',
        third:''
      }
      this.secondCateList = [],
      this.thirdCateList = [],
      this.searchBrandList = [];
      this.searchSupplierList = [];
      this.searchShopList = []
      this.getGoodsList(1);
    },
    changeStatus(status) {
      this.listParams.status = status;
      if (status == 0) {
        this.listParams.maxSupplierStock = ''
        this.listParams.minSupplierStock = ''
      }
      this.getGoodsList(1).then((params)=>{
        this.getCate(0, '',params)
      });
    },
    dialogBrand(brands) {
      this.searchBrandList = brands.brandList;
      this.showBrandModal = false;
    },
    dialogBrandCancel() {
      this.showBrandModal = false;
    },
    removeSearchBrand(index) {
      this.searchBrandList.splice(index, 1);
    },
    dialogShop(res) {
      this.searchShopList = res.shopList;
      this.showShopModal = false;
    },
    dialogShopCancel() {
      this.showShopModal = false;
    },
    removeSearchShop(index) {
      this.searchShopList.splice(index, 1);
    },
    //导出商品明细
    openExportModal() {
      this.exportProgress = 0;
      this.exportKey = '';
      this.showExportModal = true;
    },
    exportOrder() {
      let params = this.copy(this.listParams);
      params.priceRange = JSON.stringify(params.priceRange);
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
      if(this.searchShopList.length>0){
        let shop = [];
        this.searchShopList.forEach((item) => {
          shop.push(item.id);
        });
        params.shopIds = shop.join(',');
      }
      params.cateId = this.selectedCate.third || this.selectedCate.second || this.selectedCate.first || '';
      params.statusList = JSON.stringify([params.status]);
      delete params.status;
      this.exportProgress = 0;
      this.exportKey = '';
      this.api.goods.downloadItem(params).then((res) => {
        let key = res.data;
        let exportTimer = setInterval(() => {
          this.api.progress.rate({data: key}).then((res) => {
              if(res.success) {
                  if(res.data.finished) {
                      this.exportProgress = res.data.percent;
                      this.exportKey = key;
                      clearInterval(exportTimer);
                  } else {
                      this.exportProgress = res.data.percent;
                  }
              } else {
                  this.$message.info({message:res.message, showClose:true});
                  this.exportProgress = 0;
                  clearInterval(exportTimer);
              }
          });
        }, 1000);
      });
    },
    downloadOrder() {
      window.open('/common/progress_result.do?data='+this.exportKey, '_blank');
    },
    dialogBrandSingle (item) {
      const {brandList:[{id, name}]} = item
      this.dataMass.brandName = name
      this.dataMass.brandId = id
      this.showBrandModalSingle = false
    },
    changeLiRunPerson (item, i, itemt, ii) {
      let {skuList} = this.dataMass
      let skuChannels = skuList[ii].skuChannels || []
      let {supplierStocks, price} =  item
      for (let k=0; k<supplierStocks.length ; k++) {
        let ps = parseInt((price - supplierStocks[k].channelPrice) / supplierStocks[k].channelPrice * 10000) / 100 + '%'
        supplierStocks[k].profitRate = ps
      }
      skuChannels[i].supplierStocks = supplierStocks || []
      skuList[ii].skuChannels = skuChannels || []
      delete this.dataMass.skuList
      this.$set(this.dataMass, 'skuList', skuList)
    }
  },
  filters:{
    typeName:function (cin) {
      switch (cin-0) {
        case 1: return '新增商品';
        case 2: return '新增规格';
        case 3: return '新增供应商';
        case 4: return '供应价变化';
        default: return "无";
      }
    },
    skuName:function (cin) {
      if(cin.length==0){
        return "无规格"
      }else{
        return cin[0].skuName;
      }
    }
  },

  components: {
    'SelectBrand': selectBrand,
    'SelectShop': selectShop,
    'Pagination': Pagination,
    'noData': noData,
    'changeType':changeType,
    'exam':exam,
    'setGroup':setGroup,
    'adminGoods':adminGoods,
    selSupplierList
  }
}
</script>
<style scoped lang='scss'>
  .center-commodity{
    .type-wrap{
      margin-bottom: 10px;
      padding: 0 20px 0;
      background: $white-color;
      .type-content-wrap{
        display: flex;
        padding: 10px 0;
        border-top:1px solid $border-color;
        &:first-child{
          border: none;
        }
        .type-title{
          /*width: 90px;*/
          font-weight: 600;
          line-height: 30px;
          margin-right: 10px;
        }
        .type-content{
          display: flex;
          flex: 1;
          flex-wrap:wrap;
          align-content:center;
          .type-item{
            min-width: 80px;
            line-height: 30px;
            border-radius: 15px;
            margin-right: 10px;
            border-radius: 15px;
            .ellipsis{
              /*padding-right: 30px;*/
              width: auto;
              max-width: 100%;
              position: relative;
              .item-count{
                position: absolute;
                right: 5px;
                top: 0;
              }
            }
            .pr40{
              /*padding-right: 30px;*/
            }
            a{
              display: inline-block;
              vertical-align: top;
              color: #333;
              height: 28px;
              line-height: 28px;
              padding: 0 10px;
              border-radius: 28px;
              border: 1px solid #fff;
            }
          }
          .active{
            a{
              border:1px solid rgba($color: $main-color, $alpha: 0.6);
              color:$main-color;
            }
          }
        }
      }
    }
    .table{
      .ellipsis{
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        max-width: 100%;
      }
      .linkWrap{
        // max-height:40px;
        margin:16px 0px 16px 0px;
        line-height: 20px;
        word-break: break-all;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        overflow: hidden;
      }
      .linkWrapEdit{
        // max-height:40px;
        margin:16px 0px 16px 0px;
        line-height: 20px;
        /*word-break: break-all;*/
        /*text-overflow: ellipsis;*/
        /*display: -webkit-box;*/
        /*-webkit-box-orient: vertical;*/
        /*-webkit-line-clamp: 2;*/
        /*overflow: hidden;*/
      }
      .transportWays{
        color:#F06941FF
      }
      .transportStyle{
          color:#F06941
      }
      .price{
        color:#E1376CFF;
        font-weight:600;
      }
      .productName{
        /*width:180px;*/
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
      .brandName{
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
      .skuName{
        width:140px;
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
    .search-bar{
      width: 100%;
      display: flex;
      align-items: center;
      flex-wrap: wrap;
      padding-top: 0;
      button,.form-control{
        margin-right: 10px;
        margin-top: 10px;
      }
      .priceAmount{
        width:90px;
      }
      .search-bar-title {
        margin-top: 10px;
      }
      .btn-org-small, .btn-small{
        margin-right: 10px;
        a{
          color: #fff;
          padding-left: 5px;
        }
      }
      .price-mid{
        padding-right: 10px;
      }
      .btn-select-shop{
        color: #3A62E1;
        border:1px solid rgba($color: #3A62E1, $alpha: 0.6);
      }
      .btn-select-shop-item{
        background: #3A62E1;
        border-color: #3A62E1;
      }
    }
    .control-wrap{
      display: flex;
      // justify-content: space-between;
      padding: 10px;
      .control-item{
        display: flex;
        align-items: center;
        margin-left: 10px;
        .setGroup{
          margin-left:10px;
        }
        .title{
          font-size:14px;
          font-weight:600;
          color:rgba(51,51,51,1);
          line-height:20px;
          padding-left:10px;
        }
        .btn-fill-main{
          margin-left: 10px;
        }
      }
    }
  }
  .massCheckBox{
    height: 500px;
    overflow: auto;
    margin: 0 -20px;
    .font-bold{
      font-weight: bold;
    }
    .basePadding{
      padding: 0 20px 20px;
      border-bottom: 6px solid #F2F4F7;
    }
    .wid800{
      width: 800px;
    }
    .baseTable {
      width: 100%;
      text-align: left;
      font-size: 12px;
      // border-bottom: 1px solid #d8d8d8;
      &.top-tables {
        border-bottom: 0;
        tr {
          height: 35px;
          line-height: 35px;
          width: 100%;
          display: flex;
          th {
            font-size: 12px;
            color: #333;
            font-weight: bold;
            flex:1;
          }
        }
      }
      tr {
        height: 50px;
        line-height: 50px;
        background: transparent;
        th {
          color: #666;
          border-bottom: 1px solid #d8d8d8;
        }

        th, td {
          padding-left: 20px;
          flex: 1;
          box-sizing: border-box;
          padding: 0;
          font-size: 12px;
        }
      }
      tbody {
        tr {
          background: #fff;
          padding: 0;
          margin: 0;
          td {
            border-bottom: 1px solid #d8d8d8;
          }
        }
      }
    }
    .imgBox{
      width: 120px;
      height: 120px;
      border: 1px solid #ddd;
      float: left;
      background: #fff;
    }
    .des{
      margin-left: 140px;
      height: 130px;
      .tit{
        font-weight: bold;
        font-size: 14px;
        /*cursor: pointer;*/
      }
      p{
        line-height: 30px;
        font-size: 14px;
      }
      .pri{
        color: #E1376C;
        font-size: 20px;
        font-weight: 600;
      }
      .alter{
        margin-left: 30px;
        color: rgb(58, 98, 225);
        cursor: pointer;
        margin-right: 30px;
      }
    }
    .btnRed{
      color: #E1376C;
      background: #fff;
      display: inline-block;
      min-width: 86px;
      padding: 0 14px;
      border-radius: 100px;
      text-align: center;
      line-height: 28px;
      height: 30px;
      border: 1px solid #E1376C;
      cursor: pointer;
      margin-left: 10px;
    }
    .btnRed-re{
      color: #fff;
      background: #E1376C;
      display: inline-block;
      min-width: 86px;
      padding: 0 14px;
      border-radius: 100px;
      text-align: center;
      line-height: 28px;
      height: 30px;
      border: 1px solid #E1376C;
      cursor: pointer;
      margin-left: 10px;
    }
    .btnBla{
      color: #666;
      background: #fff;
      display: inline-block;
      min-width: 86px;
      padding: 0 14px;
      border-radius: 100px;
      text-align: center;
      line-height: 28px;
      height: 30px;
      border: 1px solid #dcdcdc;
      cursor: pointer;
      margin-left: 10px;
      &.active{
        color: #E1376C;
        border: 1px solid #E1376C;
      }
    }
    .fr{
      float: right;
    }
    .mass-control{
      padding: 10px 20px;
      border-top: 6px solid #F2F4F7;
      border-bottom: 1px solid #E5E5E5;
      color: #333;
      font-size: 14px;
       p {
         span {
           font-weight: bold;
         }
       }
      .fr{
        line-height: 30px;
      }
    }
    .massSize{
      padding: 10px 20px;
      background: #F2F4F7;
      border-top: 1px solid #e6e6e6;
      border-bottom: 1px solid #e6e6e6;
    }
  }
  /deep/.btnRed-re{
      color: #fff;
      background: #E1376C;
      display: inline-block;
      min-width: 86px;
      padding: 0 14px;
      border-radius: 100px;
      text-align: center;
      line-height: 28px;
      height: 30px;
      border: 1px solid #E1376C;
      cursor: pointer;
      margin-left: 10px;
    }
    /deep/.btnBla{
      color: #666;
      background: #fff;
      display: inline-block;
      min-width: 86px;
      padding: 0 14px;
      border-radius: 100px;
      text-align: center;
      line-height: 28px;
      height: 30px;
      border: 1px solid #dcdcdc;
      cursor: pointer;
      margin-left: 10px;
      &.active{
        color: #E1376C;
        border: 1px solid #E1376C;
      }
    }
    /deep/.el-checkbox__inner {
      border-radius: 50%;
      }
    /deep/.noraw {
      width: 100%;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    /deep/.cursor {
      cursor: pointer;
    }
  .time{
    width: 120px;
  }
  @media screen and (max-width: 1560px)  {
      .myTime {
          width: 80px !important;
      }
    .table-w160{
      width: 100px !important;
    }
  }
  @media screen and (min-width: 1560px)  {
      .myTime {
          width: 140px !important;
          min-width: 140px !important;
      }
    .table-w160{
      width: 160px !important;
    }
  }
  .btn-fill-main{
    padding: 0 10px;
  }
</style>
