<template>
  <div class="center-commodity">
    <div class="tab-bar">
      <div class="tab-item" :class="{'active':listParams.status===0}" @click="changeStatus(0)">全部类型</div>
      <div class="tab-item" :class="{'active':listParams.status===1}" @click="changeStatus(1)">新增商品</div>
      <div class="tab-item" :class="{'active':listParams.status===2}" @click="changeStatus(2)">新增规格</div>
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

        <select class="form-control" v-model="listParams.itemChannelTypes" :class="{'unset-select':!listParams.itemChannelTypes}" v-show="!userConfig.singleChannel">
          <option v-for="(item, index) in channelList" v-bind:value="item.channelType" :key="index+'channel'">{{item.channelTypeName}}</option>
        </select>
        <button class="btn-fill-main" @click="search(1)">搜索</button>
        <button class="btn-fill-grey-main" @click="clearSearch">清空</button>
      </div>
      <div class="control-wrap">

        <div class="control-item">
          <div class="btn-fill-org-main"  @click="changeTypeList()">批量通过</div>
          <div class="btn-fill-org-main"  @click="changeTypeList()">批量驳回</div>
          <div class="btn-fill-org-main"  @click="changeTypeList()">批量修改类目</div>
          <div class="btn-fill-main" @click="massCheck(0,0,true)">快速审核</div>
        </div>
        <div class="btn-fill-main" style="margin-left:10px;" @click="openExportModal()">导出商品明细</div>
        <div class="btn-fill-main" style="margin-left:10px;" @click="openExportModal()">查看已审列表</div>
      </div>
      <table class="table">
         <thead>
           <tr>
             <th class="dot" :class="{'active':checkAll}" @click="selectAll()" ref="goCheckAll"></th>
             <th class="table-w100">商品ID</th>
             <th class="table-w80">主图</th>
             <th class="table-w200">商品名称</th>
             <th v-show="listParams.status!=5" class="table-w100">品牌</th>
             <th class="table-w100">规格</th>
             <th v-show="listParams.status==5" class="table-w100">价格变化</th>
             <th class="table-w90" v-show="listParams.status!=5">分类</th>
             <th class="table-w120">发货方式</th>
             <th v-show="listParams.status==5" class="table-w100">供货商</th>
             <th class="table-w80" v-show="listParams.status!=5">价格</th>
             <th class="table-w90" v-show="listParams.status!=5">类型</th>
             <th class="table-w160">更新时间</th>
             <th >操作</th>
           </tr>
         </thead>
         <tbody>
          <tr v-for="(item, index) in tableData" :key="index+'table'" :class="{'selectedTr':item.checked}">
            <td class="dot" :class="{'active':item.checked}" @click="selectItem(item)"></td>
            <td>{{item.itemId}}</td>
            <td>
              <el-popover
                placement="top"
                width="200"
                trigger="hover">
                <img style="width:100%;height:100%" :src="item.mainImageUrl">
                <img slot="reference" :src="item.mainImageUrl" />
              </el-popover>
            </td>
            <td>
              <el-popover
                placement="top"
                width="200"
                trigger="hover">
                <span>{{item.itemName}}</span>
                <span class="productName cursor" slot="reference" @click="editItem(item)">{{item.itemName}}</span>
              </el-popover>
            </td>
            <td v-show="listParams.status!=5"><span class="brandName">{{item.brandName}}</span></td>
            <td>
              <el-popover
                placement="top"
                width="200"
                trigger="hover">
                <span class="linkWrap">{{item.sku}}</span>
                <span slot="reference" class="productName curso">{{item.sku}}</span>
              </el-popover>
            </td>
            <td v-show="listParams.status==5">价格变化</td>
            <td v-show="listParams.status!=5"><span v-if="item.cateName3" class="productName">{{item.cateName3}}</span><span v-else>未分类</span></td>
            <td class="transportWays"><div class="productName">{{item.channelName}}</div></td>
            <!-- <td>4</td> -->
            <td v-show="listParams.status!=5" class="price">{{item.price}}</td>
            <td v-show="listParams.status!=5">
              <span v-if="item.type==1">新增商品</span>
              <span v-if="item.type==2">新增规格</span>
              <span v-if="item.type==3">新增供应商</span>
              <span v-if="item.type==4">价格变更</span>
            </td>
            <td v-show="listParams.status==5" class="price">供货商</td>
            <td>
              <span class="linkWrap myTime">
                 {{item.modifyTime}}
              </span>

            </td>
            <td>
              <span class="linkWrapEdit" v-show="listParams.status!=5">
                  <a href="javascript:;" class="link base-margin-right" v-if="false" @click="examItem(item)">审核</a>
                  <a href="javascript:;" class="link base-margin-right" @click="eidt(item)">审核编辑</a>
                  <a href="javascript:;" class="link base-margin-right" @click="changeTypeList(item)">修改类目</a>
              </span>
              <span v-show="listParams.status==5">
                <a href="javascript:;" class="link base-margin-right" @click="eidt(item)">通过</a>
                <a href="javascript:;" class="link base-margin-right" @click="eidt(item)">驳回</a>
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
    <adminGoods ref="adminGoodsModel" v-if="adminGoodsData.dialogVisible" :adminGoodsData="adminGoodsData" :page="listParams"></adminGoods>
    <SelectBrand v-if="showBrandModal" ref="showBrandModal" v-on:dialogBrand="dialogBrand" v-on:dialogBrandCancel="dialogBrandCancel"></SelectBrand>
    <SelectShop v-if="showShopModal" ref="showShopModal" v-on:dialogShop="dialogShop" v-on:dialogShopCancel="dialogShopCancel"></SelectShop>

    <el-dialog title="商品快速审核" :visible.sync="dataMass.show" width="1000px">
      <div class="massCheckBox">
<!--        商品信息栏-->
        <div class="basePadding">
          <div class="fr">
            <span class="btnRed" @click="rotate(0)">上一个</span>
            <span class="btnRed" @click="rotate(1)">下一个</span>
          </div>
          <div class="imgBox" style="display:flex;justify-content: center;align-items;">
            <img :src="dataMass.imageList[0].url" alt="" v-if="dataMass&& dataMass.imageList &&dataMass.imageList.length" style="width:100%;">
          </div>
          <div class="des">
            <p class="tit"> <span style="font-weight: 600;cursor: pointer" @click="goGoodsDetail(dataMass)">{{dataMass.name}}</span> </p>
            <p><span>所属分类 : </span>
              <span>{{dataMass.cateName1}}</span>
              <span v-if="!dataMass.cateName1">未分类</span>
              <i class="el-icon-caret-right" style="margin:0 5px;" v-if="dataMass.cateName2"></i><span>{{dataMass.cateName2}}</span>
              <i class="el-icon-caret-right" style="margin:0 5px;" v-if="dataMass.cateName3"></i><span>{{dataMass.cateName3}}</span>
              <span class="alter" @click="alter">修改</span>
            </p>
            <p><span>所属品牌 : </span><span>{{dataMass.brandName}}</span>  <span class="alter" @click="showBrandModalSingle = true">修改</span>  商品ID：<span>{{dataMass.id}}</span></p>
            <p class="pri">{{dataMass.priceRange}} </p>
          </div>
        </div>
<!--        控制栏一-->
        <div class="mass-control">

          <div class="fr" >
            <span style="margin-right: 20px;" class="font-bold">商品状态:</span>
            <span style="margin-right: 20px;"><el-radio v-model="dataMass.status" label="1"><span class="font-bold">上架</span></el-radio>
            <el-radio v-model="dataMass.status" label="2"><span class="font-bold">下架</span></el-radio></span>
          </div>
          <p>
            <el-checkbox v-model="checkAllskus" style="margin-right: 10px" @change="checkAllFun($event)" ></el-checkbox><span>全选</span>

            <span style="margin-right: -10px;margin-left: 10px">批量操作：</span>
<!--            控制页面进行批量操作的-->
            <span class="btnBla" @click="saleFun(2)" :class="{'active':othMass.markSale==2}">销售开启</span>
            <span class="btnBla" @click="saleFun(1)" :class="{'active':othMass.markSale==1}">销售关闭</span>
            <span class="btnBla" @click="vertFun(2)" :class="{'active':othMass.markVert==2}">审核通过</span>
            <span class="btnBla" @click="vertFun(1)" :class="{'active':othMass.markVert==1}">审核不通过</span>
           </p>

        </div>
        <table class="table">
          <thead>
           <tr>
                 <th class="table-w50"> <el-checkbox v-model="checkAllskus" style="margin-right: 10px" @change="checkAllFun($event)" ></el-checkbox></th>
                 <th class="table-w178">供货商名称</th> <th class="table-w93">供货商旧价</th> <th class="table-w93">供货商新价</th> <th class="table-w93">供货商库存</th>
                <th class="table-w73">利润率</th> <th class="table-w80">审核类型</th> <th class="table-w80">销售开关</th> <th>审核开关</th><th></th>
              </tr>
          </thead>

        </table>
        <div v-for="(item,index) in dataMass.skuList" :key="index">
  <!--        控制栏二 对应skuChannels-->
          <div v-for="(sku,si) in item.skuChannels" :key="si">
          <div class="massSize" >
            <el-checkbox v-model="sku.checked" style="margin-right: 10px" @change="checkSku($event,sku,index,si)"></el-checkbox>
            <span style="margin-right: 20px;display:inline-block;width:150px;" class="noraw" >规格{{index+1}}：{{dataMass.unit && item.skuValues &&item.skuValues[0] && item.skuValues[0].skuValue ? `${item.skuValues[0].skuValue}-${item.quantity}${dataMass.unit}装` :  `${item.quantity}${dataMass.unit}装` || '无规格'}}</span>
            <span style="margin-right: 20px;">发货方式：{{sku.channelName || ''}}</span>
            <span style="margin-right: 20px;" v-if="userConfig.hasWeiNiPrice&&dataMass.id!=1&&dataMass.id!='' && sku.refPrice && sku.refPrice.weiniPrice">唯妮价：￥{{sku.refPrice && sku.refPrice.weiniPrice || ''}}</span>
            <span  style="">售价：</span>
            <el-input placeholder="请输入内容" v-model="sku.price" size="mini" @blur="changeLiRunPerson(sku, si, item, index)" style="width:100px;margin-right: 10px;">
              <i  slot="prefix" style="line-height: 28px;color:#000;font-style: normal">￥</i>
            </el-input>
            <span  >企业价：</span>

            <el-input placeholder="请输入内容" v-model="sku.enterprisePrice" size="mini" style="width:100px;margin-right: 10px;">
              <i  slot="prefix" style="line-height: 28px;color:#000;font-style: normal">￥</i>
            </el-input>
            <span  >原价：</span>

            <el-input placeholder="请输入内容" v-model="sku.originalPrice" size="mini" style="width:100px;margin-right: 10px;">
              <i  slot="prefix" style="line-height: 28px;color:#000;font-style: normal">￥</i>
            </el-input>
          </div>
            <table class="baseTable table">
              <tbody>
              <tr v-for="(v,i) in sku.supplierStocks" :key="i">
                <td class="table-w50" style="padding: 0 0 0 20px;width:50px !important;">
                    <el-checkbox label="" @change="checkRadio" v-model="v.radio"></el-checkbox>
                </td>
                <td class="table-w178 noraw">{{v.supplierName}}</td>
                <td class="table-w93">{{v.approvedChannelPrice}}</td>
                <td class="table-w93">{{v.channelPrice}}</td>
                <td class="table-w93">{{v.stock}}</td>
                <td class="table-w73">{{v.profitRate}}</td>
                <td class="table-w80">{{v.approveType | typeName}}</td>
                <td class="table-w80"><el-switch v-model="v.status" :active-value="1" :inactive-value="0" active-color="#13ce66" @change="switchSS($event,index,si,i)"></el-switch></td>
                <td><el-switch v-model="v.approvedStatus" :disabled="v.approvedStatus && !v.changeApprovedStatus ? true : false" :active-value="1" :inactive-value="0" active-color="#13ce66" @change="switchTT($event,index,si,i)"></el-switch></td>
                <td></td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
       <div style="text-align: center;line-height: 50px;" slot="footer">
          <span class="btnRed-re" @click="upDate">保存并下一个</span>
          <span class="btnBla" @click="testFun()">关闭审核</span>
        </div>
    </el-dialog>
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
import changeType from '../commodity/components/changeType.vue'
import exam from '../commodity/components/exam.vue'
import adminGoods from '../commodity/components/adminGoods.vue'
import selSupplierList from '../commodity/components/selSupplierList.vue'
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
        status: 0,
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
    // this.massCheck(10000,0);
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
    alter(){
      this.changeTypeData = {id:this.dataMass.id}
      this.changeTypeData.dialogVisible = true;
      this.changeTypeData.cr="noAjax";
    },
    upDate(){
      let params=JSON.parse(JSON.stringify(this.dataMass))
      params.addImages=params.imageList;
      params.categoryId=params.cateId3;
      if (this.checkIsShenHe(params)) {
      this.api.goods.update(params).then(res => {
        log(res) //在这里补一个跳转
        return res;
      }).then(res=>{
        if (res.success){
          this.$message.info('保存成功')
          this.checkAllskus = false

          this.rotate(1,true);
        } else {
          this.$message.error('保存失败')
        }
      }).catch((err)=>{
        log(err)
      })
      } else {
        this.$msgbox({
          type: "warning",
          title: "友情提示",
          message:
            "您当前存在审核不通过的商品规格或供应商，是否继续保存？（保存后商品将不含有审核不通过的规格数据）",
          showClose: true,
          showCancelButton: true,
          cancelButtonText: "关闭",
          confirmButtonText: "我已知晓，继续保存",
          center: true,
          beforeClose: (action, instance, done) => {
            if (action === "confirm") {
                params = this.controlShenHe(params)
                this.api.goods.update(params).then(res => {
                  return res;
                }).then(res=>{
                  if (res.success){
                    this.$message.info('保存成功')
                    this.checkAllskus = false
                    this.rotate(1,true);
                  } else {
                    this.$message.error('保存失败')
                  }
                }).catch((err)=>{
                  log(err)
                })
                done()
            } else if (action === "cancel") {
                done()
                this.dataMass.show = false
            }
          }
        });
      }
    },
    checkIsShenHe (params) {
       let {skuList} = params
       skuList = skuList || []
       let status = true
       skuList.forEach(r => {
          if(r.skuChannels && r.skuChannels.length) {
              r.skuChannels.forEach(rr => {
                 if(rr.supplierStocks && rr.supplierStocks.length) {
                   rr.supplierStocks.forEach(rrr => {
                      if (rrr.approvedStatus == 0) {
                          status = false
                      }
                   })
                 }
              })
          }
       })
       return status
    },
    // 处理审核
    controlShenHe (params) {
       let {skuList} = params
       skuList = skuList || []
       skuList.forEach(r => {
          if(r.skuChannels && r.skuChannels.length) {
              r.skuChannels.forEach(rr => {
                 if(rr.supplierStocks && rr.supplierStocks.length) {
                   rr.supplierStocks.forEach(rrr => {
                      if (rrr.approvedStatus == 0) {
                          rrr.approvedStatus = 2
                      }
                   })
                 }
              })
          }
       })
       params.skuList = skuList
       return  params
    },
    mutualRadio(){ //选中 及选中效果渲染到页面上
      let len=0;
      this.dataMass.skuList.forEach((v1)=>{
        v1.skuChannels.forEach((v2)=>{
          v2.supplierStocks.forEach((v3)=>{
            if(v3.radio){
              if(this.othMass.markSale){
                v3.status=this.othMass.markSale-1;
              }
              if(this.othMass.markVert){
                if (v3.changeApprovedStatus) {
                   v3.approvedStatus=this.othMass.markVert-1;
                } else {
                  if (v3.approvedStatus != 1) {
                    v3.approvedStatus=this.othMass.markVert-1;
                    v3.changeApprovedStatus = true
                  }
                }
              }
              len++
            }
          })
        })
      })
      // log(len);
      if(len==0){
        this.$message.warning({message:'请先选择供应商', showClose:true});
        this.othMass.markSale=0
        this.othMass.markVert=0
      }
    },

    saleFun(i){
      if(this.othMass.markSale==i){
        this.othMass.markSale=0
      }else{
        this.othMass.markSale=i
        // 功能要写在这
        this.mutualRadio()
      }

    },
    vertFun(i){
      if(this.othMass.markVert==i){
        this.othMass.markVert=0
      }else{
        this.othMass.markVert=i
        this.mutualRadio()
      }
    },
    checkAllFun(v){
      this.othMass.markSale=0
      this.othMass.markVert=0
      this.dataMass.skuList.forEach((v1)=>{
        v1.skuChannels.forEach((v2)=>{
          v2.checked=v;
          v2.supplierStocks.forEach((v3)=>{
            v3.radio=v;
          })
        })
      })
    },
    checkSku(v,item,i1,i2){
      this.othMass.markSale=0
      this.othMass.markVert=0
      item.supplierStocks.forEach((v3,i3)=>{
        // log(this.dataMass.skuList[i1].skuChannels[i2].supplierStocks[i3]['radio'])
        this.dataMass.skuList[i1].skuChannels[i2].supplierStocks[i3]['radio']=v
        // this.$set(this.dataMass.skuList[i1].skuChannels[i2].supplierStocks[i3],'radio',v);
      })
    },
      checkRadio(){
        this.othMass.markSale=0
        this.othMass.markVert=0
      },
    switchTT(v,i1,i2,i3){
      this.$set(this.dataMass.skuList[i1].skuChannels[i2].supplierStocks[i3],'approvedStatus',v);
      this.$set(this.dataMass.skuList[i1].skuChannels[i2].supplierStocks[i3],'changeApprovedStatus',1);
    },
    switchSS(v,i1,i2,i3){
      this.$set(this.dataMass.skuList[i1].skuChannels[i2].supplierStocks[i3],'status',v);
    },
    massCheck(itemId,direction,flag,saveFlag){
      if(flag){
        this.dataMass.sortTime = ''
        this.selIndex =0
        this.exceptItemId = []
      }
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
      delete params.priceRange
      delete params.limit
      delete params.page
      delete params.supplierOfflineType
      delete params.status
      // let pms = {
      //   itemId,
      //   direction,
      //   ...params
      // }
      this.selIdArr = Array.from(new Set(this.selIdArr));
      if(this.selIdArr.length>0&&(this.selIdArr.length==this.selIndex||this.selIndex<0)){
        this.$message.info('没有更多商品了');
        if(this.selIndex<0){
          this.selIndex = 0
        }else if(this.selIdArr.length==this.selIndex){
          this.selIndex = this.selIdArr.length-1
        }
        return false
      }
      console.log(itemId)
      let pms = {};
      if(this.selIdArr.length>0){
         pms = {
          direction,
          itemId:this.selIdArr[this.selIndex],
          exceptItemId:this.selIndex==0?0:this.selIdArr[this.selIndex-1],
           sortTime:this.dataMass.modifyTime?this.dataMass.sortTime:"",
          ...params
        };
      }else {
        console.log(direction)
        if(itemId&&direction&&!saveFlag){
          this.exceptItemId.push(itemId)
        }else if(direction==0&&this.exceptItemId.length>0){
          itemId =  this.exceptItemId.pop();
        }else if(direction==0&&this.exceptItemId.length==0&&!flag){
          this.$message.info('没有更多商品了');
          return  false
        }
        console.log(this.exceptItemId)
        //  pms = {
        //   direction,
        //   exceptItemId:itemId,
        //    sortTime:this.dataMass.sortTime?this.dataMass.sortTime:"",
        //   ...params
        // }
        pms = {
          direction,
          exceptItemId:this.exceptItemId,
          itemId:direction==1?0:itemId,
          sortTime:this.dataMass.sortTime?this.dataMass.sortTime:"",
          ...params
        }
      }

      this.api.goods.fastApproved(pms).then(res=>{
        res.data.status+="";
        res.data.skuList.forEach((v1)=>{
          v1.skuChannels.forEach((v2)=>{
            v2.checked=false;
            v2.supplierStocks.forEach((v3)=>{
              v3.radio=false;
            })
          })
        })
        res.data.show=true;
        this.dataMass=res.data;
      }).catch(()=>{
        this.exceptItemId.pop();
        this.dataMass.show=false;
      })
    },

    testFun(){
      // log(this.dataMass[])
      this.dataMass.show=false;
      this.$set(this.dataMass,"show",false);
      this.othMass = {
        markSale:0,
        markVert:0
      }
      // log(this.dataMass)
    },
    apiFastApproved(itemId,direction){
      return this.api.goods.fastApproved({itemId,direction})
    },
    rotate(direction,saveFlag){
      // log(direction);return;
      let id=this.dataMass.id
      this.checkAllskus = false
      this.othMass = {
        markSale:0,
        markVert:0
      }
      if(direction==1){
        this.selIndex+=1;
      }else {
          this.selIndex-=1;
      }
      this.massCheck(id,direction,false,saveFlag);
    },
    closeChangeType () {
      delete this.changeTypeData.dialogVisible
      this.$set(this.changeTypeData, 'dialogVisible', false)

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

    changeTypeList(item){
      delete this.changeTypeData.cr;
       if(item){
        this.changeTypeData = {id:item.itemId}
        this.changeTypeData.dialogVisible = true
      } else {
          let idArr = []
          this.tableData.forEach((val) => {
              if(val.checked){
                idArr.push(val.itemId)
              }
          });
          idArr.length ? this.changeTypeData = {id:JSON.stringify(idArr)} : this.changeTypeData = {}
          if(this.changeTypeData.id){
              this.changeTypeData.dialogVisible = true
          }else{
            this.$message.error({message:'请先选择商品', showClose:true});
          }
      }
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
