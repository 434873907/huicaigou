<template>
  <div class="center-order">
    <div class="tab-bar">
      <div
        class="tab-item"
        v-for="item in statusList"
        :key="item.id"
        :class="{'active':item.id==listParams.status}"
        @click="changeStatus(item)"
      >{{item.name}}<span v-if="item.id == 7||item.id == 1||item.id == 2||item.id == 6">({{item.num}})</span> </div>
    </div>
    <div class="list-wrap">
      <div class="search-bar">
        <div class="search-bar-title">商品属性：</div>
        <button class="btn-fill-main" @click="showSupplierModal=true">选择供应商</button>
        <span class="btn-small" v-for="(item, index) in searchSupplierList" :key="index+'supplier'">
          {{item.supplierName}}
          <a href="javascript:;" @click="removeSearchSupplier(index)">X</a>
        </span>
        <button class="btn-fill-org-main" @click="showBuyerModal=true">选择采购商</button>
        <span class="btn-org-small" v-for="(item, index) in searchBuyerList" :key="index+'buyer'">
          {{item.userName}}
          <a href="javascript:;" @click="removeSearchBuyer(index)">X</a>
        </span>
<!--          <button class="btn-fill-org-main" @click="showShopModal=true">选择店铺</button>-->
<!--          <span class="btn-org-small btn-select-shop-item" v-for="(item, index) in searchShopList" :key="index+'shop'">-->
<!--          {{item.name}}-->
<!--          <a href="javascript:;" @click="removeSearchShop(index)">X</a>-->
<!--        </span>-->
      </div>
      <div
        class="search-bar"
        v-if="(listParams.status == 6) && errorList.length>0"
      >
          <div class="anomalyBox">
              <div>
                  <div class="search-bar-title">异常类型：</div>
                  <div class="errorListBox">
                      <div
                              class="item"
                              v-for="(item,index) in errorList"
                              :key="index+'errorType'"
                              :class="{'active':item.active}"
                              @click="getErrorType(item)"
                      >{{item.abnormalTypeName}}({{item.orderNum}})</div>
                  </div>
              </div>
              <div class="explain" @click="showExplainBtn">
                  异常说明?
              </div>
          </div>

      </div>
      <div class="search-bar">
        <div class="search-bar-title">其它筛选：</div>
        <div class="form-wrap">
          <div class="form-name">关键字</div>
          <!-- <select class="form-control" v-model="listParams.keywordType" :class="{'unset-select':!listParams.keywordType}">
            <option v-for="item in keywordList" :key="item.id" :value="item.id">{{item.name}}</option>
          </select>-->
          <input
            type="text"
            class="form-control"
            placeholder="请输入订单号、收货等信息"
            v-model.trim="listParams.keyword"
            @keyup.enter="getList(1)"
            style="width: 200px;"
          />
        </div>
        <div class="form-wrap form-wrap-last">
          <div class="form-name">下单时间</div>
          <el-date-picker
            class="timepicker-control"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择"
            v-model="listParams.startTime"
          ></el-date-picker>
          <el-date-picker
            class="timepicker-control"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择"
            v-model="listParams.endTime"
          ></el-date-picker>
        </div>
          <div class="form-wrap">
              <div class="form-name">订单来源</div>
              <select  placeholder="请选择订单来源" class="form-control" :class="{'unset-select':!listParams.createPlatform}" v-model="listParams.createPlatform">
                <option v-for="item in orderSource" :key="item.code" :value="item.code">{{item.name}}</option>
              </select>
          </div>
          <div class="form-wrap ">
              <div class="form-name">是否标记</div>
              <select  placeholder="请选择" class="form-control" :class="{'unset-select':!listParams.tagStatus}" v-model="listParams.tagStatus">
                  <option v-for="item in tagList" :key="item.code" :value="item.code">{{item.name}}</option>
              </select>
          </div>
        <button  class="btn-fill-main marTopFlag" @click="getList(1)">搜索</button>
<!--        <div class="exportBox pull-right">-->
<!--          <button class="btn-fill-main" @click="openExportModal()">导出订单</button>-->
<!--          <button class="btn-fill-main" style="margin-right: 0" @click="openImportModal()">导入物流信息</button>-->
<!--        </div>-->
      </div>
      <div class="importBox">
          <div>
              <button class="btn-fill-main" style="margin-right: 0" @click="openImportModal()">导入物流信息</button>
          </div>
          <div>
              <button class="btn-fill-main " @click="openExportModal()">订单导出</button>
<!--              <button class="btn-fill-main marLeft" @click="openExportModal()">财务导出</button>-->
<!--              <button class="btn-fill-main marLeft" @click="openExportModal()">运单导出</button>-->
          </div>
      </div>
      <table class="table table-text">
        <thead>
          <tr>
            <th class="table-w170">用户下单时间</th>
            <th class="table-w180">订单号</th>
            <!-- <th class="table-w200">包裹号</th> -->
            <th class="table-w150">采购商</th>
            <th class="table-w100" v-show="!userConfig.singleChannel">发货方式</th>
            <th class="table-w120">供应商</th>
            <!-- <th class="table-w100">发货方式</th> -->
            <th class="table-w60">商品数</th>
            <th class="table-w80">支付总额</th>
            <!-- <th class="table-w80">成本价</th> -->
            <th class="table-w80">收货人</th>
            <th class="table-w80">异常</th>
            <th class="table-w80">订单状态</th>
            <th class="table-w90">订单来源</th>
            <th class="table-w100" v-show="listParams.status==8">关闭原因</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in tableData" :key="item.id">
            <td>
              <el-popover class="ellipsis" placement="top" width="200" trigger="hover">
                <span>{{item.createTime}}</span>
                <span slot="reference" class="hidden ellipsis">{{item.createTime}}</span>
              </el-popover>
            </td>
            <td>
<!--              有原订单号的显示原订单号-->
                <el-popover placement="top" width="200" trigger="hover" :content="'原订单号：'+item.userOrderNum" v-if="item.userOrderNum">
                  <a class="link ellipsis" @click="seekDetail(item)"  slot="reference">{{item.orderNum}}</a>
                </el-popover>
              <a class="link ellipsis" @click="seekDetail(item)" v-if="!item.userOrderNum">{{item.orderNum}}</a>
            </td>
            <!-- <td>包裹号</td> -->
            <td>
              <el-popover placement="top" width="200" trigger="hover">
                <span v-if="item.buyerRemark">{{item.buyer}}{{'('+item.buyerRemark+")"}}</span>
                <span v-if="!item.buyerRemark">{{item.buyer}}</span>
                <span v-if="item.buyerRemark" slot="reference" class="hidden ellipsis">{{item.buyer}}{{'('+item.buyerRemark+")"}}</span>
                <span v-if="!item.buyerRemark" slot="reference" class="hidden ellipsis">{{item.buyer}}</span>
              </el-popover>
            </td>
            <td v-show="!userConfig.singleChannel">
              <el-popover class="ellipsis" placement="top" width="200" trigger="hover">
                <span>{{item.channel}}</span>
                <span class="ellipsis" slot="reference">{{item.channel}}</span>
              </el-popover>
            </td>
            <td >
              <el-popover class="ellipsis" placement="top" width="200" trigger="hover">
                <span>{{item.supplier}}</span>
                <span class="ellipsis" slot="reference">{{item.supplier}}</span>
              </el-popover>
            </td>
            <!-- <td>发货方式</td> ￥{{item.supplyPrice}}-->
            <td>{{item.itemCount}}</td>
            <td class="overShow">
              <el-popover placement="top" width="200" trigger="hover">
                <span class="price-status">￥{{item.supplyPrice}}</span>
                <span slot="reference" class="price-status">￥{{item.supplyPrice}}</span>
              </el-popover>
            </td>
            <!-- <td class="overShow">
              <el-popover
                placement="top"
                width="200"
                trigger="hover">
                <span class="price-status">￥{{item.purchasePrice}}</span>
                <span slot="reference" class="price-status">￥{{item.purchasePrice}}</span>
              </el-popover>
            </td>-->
            <td>
              <span class="ellipsis">{{item.receiver}}</span>
            </td>
            <td class="supplier-color">
              <el-popover class="ellipsis" placement="top" width="200" trigger="hover" :content="item.abnormals">
                <span class="ellipsis" slot="reference">{{item.abnormals}}</span>
              </el-popover>
            </td>
            <td>
              <span v-show="item.orderStatus==1||item.orderStatus == 3">待付款</span>
              <span v-show="item.orderStatus==100">待发货</span>
              <span v-show="item.orderStatus==200">待收货</span>
              <span v-show="item.orderStatus==300">已收货</span>
              <span v-show="item.orderStatus==400">订单取消</span>
              <span v-show="item.orderStatus==500">退货退款</span>
              <span v-show="item.orderStatus==600">已驳回</span>
              <span v-show="item.orderStatus==700">已关闭</span>
            </td>
            <td>{{item.createPlatform}}</td>
            <td v-show="listParams.status==8">
              <span v-show="item.closedByRefund==0">超时关闭</span>
              <span v-show="item.closedByRefund==1">退款关闭</span>
            </td>
            <td>
                <a v-show="item.tagStatus==0" class="link link-op" @click="tagOrderDoBtn(item,1)">标记</a>
                <a v-show="item.tagStatus==1" class="link link-op" @click="tagOrderDoBtn(item,0)">取消标记</a>
              <!-- <a class="link link-op" v-show="listParams.status==1" @click="refuseOrder(item)">全部退款</a> -->
<!--                <a-->
<!--                        class="link link-op"-->
<!--                        v-show="item.orderStatus==1"-->
<!--                        @click="openBackModal(item)"-->
<!--                >关闭订单</a>-->
              <a
                class="link link-op"
                v-show="listParams.status==1||listParams.status==2||listParams.status==3"
                @click="openBackModal(item)"
              >主动退款</a>
              <!-- <a class="link link-op" v-show="item.orderStatus==200" @click="confirmReceive(item)">确认收货</a> -->
              <a
                class="link link-op"
                v-show="listParams.status == 5 && item.isApplyRefund"
                @click="applyPass(item)"
              >通过审核</a>
              <a class="link link-op" v-show="item.orderStatus == 3" @click="payConfirm(item)">确认收款</a>
              <a
                class="link link-op"
                v-show="(listParams.status == 5 && !item.isApplyRefund) || (listParams.status == 8 && item.closedByRefund==1)"
                @click="checkRefundDetail(item)"
              >退款详情</a>
              <a href="javascript:;" class="link link-op" v-if="item.orderStatus == 100 && item.supportPushOrder == 1" @click="pushOrder(item)">推单</a>
<!--              <a href="javascript:;" class="link link-op" v-if="listParams.status== 1" @click="pushOrder(item)">推单</a>-->
            </td>
          </tr>
        </tbody>
      </table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="listParams.page"
        :limit.sync="listParams.limit"
        @pagination="getList(listParams.page)"
      />
      <noData v-if="total===0"></noData>
    </div>
    <el-dialog title="订单导出:" :visible.sync="showExportModal" width="468px">
      <div class="dialog-form export-dialog">
        <el-progress
          :text-inside="true"
          :stroke-width="14"
          :percentage="exportProgress"
          color="#F06941"
        ></el-progress>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="exportOrder()" v-show="!exportKey">开始导出</button>
        <a class="btn-main" @click="downloadOrder()" v-show="exportKey">点击下载</a>
        <button class="btn-fill-grey-main" @click="showExportModal=false">取消</button>
      </span>
    </el-dialog>
    <el-dialog title="物流导入:" :visible.sync="showImportModal" width="815px">
      <div class="dialog-form import-dialog">
        <div class="import-box">
          <multiple-file-uploader
            :postURL="postUrl"
            successMessagePath
            errorMessagePath
            @upload-success="successHandler"
            ref="multipleUpload"
            :post-data="{'type':importLogRadio}"
          ></multiple-file-uploader>
        </div>
        <div class="radio-box">
          <el-radio v-model="importLogRadio" :label="1">
            <span class="radio-item">补充新物流节点</span>
            <span class="tips">在原物流公司及运单后新增物流信息</span>
          </el-radio>
        </div>
        <div class="radio-box">
          <el-radio v-model="importLogRadio" :label="2">
            <span class="radio-item">覆盖原物流信息</span>
            <span class="tips">覆盖原物流公司及运单信息</span>
          </el-radio>
        </div>
        <div class="error-box" v-show="false">
          <p>信息错误，请检查后重新上传全部信息。</p>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="importLogistics()">确认导入</button>
        <button class="btn-fill-grey-main" @click="showImportModal=false">取消</button>
      </span>
    </el-dialog>
      <el-dialog title="异常订单说明" :visible.sync="showExplain" width="620px">
         <div>
             <div   class="marTop" style="margin-top: 20px">
                 <h2 class="titltText"> 1、实名认证失败</h2>
                 <p class="fontCol">
                     指用户身份证号码与姓名不匹配导致订单无法正常推送至第三方（不需要推送至第三方的无需关注该条异常情况);
                 </p>
             </div>
             <div   class="marTop">
                 <h2 class="titltText"> 2、推单失败</h2>
                 <p class="fontCol">
                     指系统主动或者人工手动推单至第三方平台时，因某种原因导致推单失败（不需要推送至第三方的无需关注该条异常情况);
                 </p>
             </div>
             <div   class="marTop">
                 <h2 class="titltText"> 3、推单超时</h2>
                 <p class="fontCol">
                     用户支付订单后，对于商品来源于第三方的商品，会主动将订单推送至第三方平台若10分钟内未推送（指推送状态仍为未推送），则记为推单超时;
                 </p>
             </div>
             <div   class="marTop">
                 <h2 class="titltText"> 4、发货超时</h2>
                 <p class="fontCol">
                     用户支付订单后，48小时内还未发货的订单，系统会对其进行标记，记为发货超时。
                 </p>
             </div>
         </div>
          <span slot="footer" class="dialog-footer">
          <button class="btn-fill-grey-main closeBtn" style="margin-top: 10px"  @click="showExplain=false">关闭</button>
        </span>
      </el-dialog>
      <selectShop v-if="showShopModal" ref="showShopModal" v-on:dialogShop="dialogShop" v-on:dialogShopCancel="dialogShopCancel"></selectShop>

      <selectSupplier
      v-if="showSupplierModal"
      v-on:dialogSupplier="dialogSupplier"
      v-on:dialogSupplierCancel="dialogSupplierCancel"
    ></selectSupplier>
    <selectBuyer
      v-if="showBuyerModal"
      v-on:dialogBuyer="dialogBuyer"
      v-on:dialogBuyerCancel="dialogBuyerCancel"
    ></selectBuyer>
    <applyForRefund
      v-if="showRefundModal"
      ref="showRefundModal"
      :refundParams="refundParams"
      v-on:refund="refund"
      v-on:cancelRefund="refundCancel"
    ></applyForRefund>
    <refundDetail
      v-if="showRefundDetail"
      :refundParams="refundDetailParams"
      v-on:refundDetail="closeRefundDetail"
      v-on:cancelRefundDetail="closeRefundDetail"
    ></refundDetail>
  </div>
</template>
<script>
import Pagination from "@/components/pagination";
import noData from "@/components/noData";
import selectSupplier from "@/components/selectSupplier/index.vue";
import selectShop from '@/components/selectShop/index.vue';
import selectBuyer from "@/components/selectBuyer/index.vue";
import applyForRefund from "@/components/applyForRefund/index.vue";
import refundDetail from "@/components/refundDetail/index.vue";
import MultipleFileUploader from "@/components/multipleFileUploader/MultipleFileUploader.vue";
let {log} = console;
export default {
  components: {
    Pagination,
    noData,
    selectSupplier,
    selectBuyer,
    MultipleFileUploader,
    applyForRefund,
    refundDetail,
    selectShop
  },
  props: {
    msg: String
  },
  data() {
    return {
      searchShopList:[],
      orderSource:[],
        tagList:[{
            name:'请选择',
            code:''
        },{
          name:'已标记',
            code:'1'
        },{
            name:'未标记',
            code:'0'
        }],
      showExplain:false,
      showShopModal:false,
      showSupplierModal: false,
      showBuyerModal: false,
      showImportModal: false,
      showExportModal: false,
      showRefundModal: false,
      showRefundDetail: false,
      // postUrl: 'https://at.apiunion.com/order/upload_express.do',
      postUrl: "/order/upload_express.do",
      searchSupplierList: [],
      searchBuyerList: [],
      refundParams: {
        userId: "",
        orderNum: ""
      },
      refundDetailParams: {},
      listParams: {
        page: 1,
        limit: 10,
        status: 0,
        // keywordType: '',
        keyword: "",
        startTime: "",
        endTime: "",
        createPlatform:'',
          tagStatus:""
      },
      total: -1,
      statusList: [
        { name: "全部", id: 0 },
        { name: "待付款", id: 7 },
        { name: "待发货", id: 1 },
        { name: "待收货", id: 2 },
        { name: "已完成", id: 3 },
        { name: "已驳回", id: 4 },
        { name: "退货退款", id: 5 },
        { name: "已关闭", id: 8 },
        { name: "异常", id: 6 }
      ],
      keywordList: [
        { name: "全部", id: "" },
        { name: "商品ID", id: 1 },
        { name: "商品名称", id: 2 },
        { name: "收货人姓名", id: 3 },
        { name: "订单号", id: 4 }
      ],
      tableData: [],
      importLogRadio: 1,
      exportProgress: 0,
      exportKey: "",
      errorList: [],
        statusOrderNumMap:{1:8,2:102,3:20,4:22,5:8,6:23,7:99,8:0},
    };
  },
  created() {
    this.showExportModal = false
    // 各tab数量
      this.getStatusList()
      this.init();
    this.orderCreatePlatform()
  },

  methods: {
      //标记取消标记
      tagOrderDoBtn(item,num){
          let str =num==1?'确认标记？':"确认取消标记？"
          this.$msgbox({
              title: "",
              message: str,
              showCancelButton: true,
              confirmButtonText: "确定",
              cancelButtonText: "取消"
          }).then(action => {
              if (action === "confirm") {
                  this.api.order.tagOrderDo({
                      orderNum:item.orderNum,
                      tagStatus:num,
                      userId:item.userId
                  }).then(res=>{
                      this.$message.success({ message: res.message, showClose: true });
                      this.getList();
                  })
              }
          });

      },
      //获取订单来源
      orderCreatePlatform(){
        this.api.order.orderCreatePlatform().then(res=>{
            this.orderSource = res.data
            this.orderSource.unshift({
                name:'请选择订单来源',
                code:''
            })
        })
      },
    getStatusList (param = {}) {
        this.api.order.orderNum(param).then(res => {
          // log(res.data.statusOrderNumMap)
          this.statusList.forEach((v,i)=>{
              this.statusList[i].num=res.data.statusOrderNumMap[v.id];
          })
          let list = JSON.parse(JSON.stringify(this.statusList)) || []
          this.statusList = []
          this.statusList = list
          list = null
      }).catch((err)=>{
          log(err)
      })
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
    init() {
      this.getErrorList();
      this.getList(1);
    },
    getErrorList() {
      this.api.order.abnormalType({}).then(res => {
        this.errorList = res.data;
      });
      this.$nextTick(() => {
        this.errorList.forEach(val => {
          this.$set(val, "active", false);
        });
      });
    },
    getErrorType(item) {
      this.errorList.forEach(val => {
        if (val.abnormalType == item.abnormalType) {
          if (!val.active) {
            val.active = true
          } else {
            val.active = false
          }
        } else {
            val.active = false
        }
      });
      this.$forceUpdate();
      this.getList(1);
    },
    openImportModal() {
      this.showImportModal = true;
    },
    openExportModal() {
      this.exportProgress = 0;
      this.exportKey = "";
      this.showExportModal = true;
    },
    getList(page){
      this.listParams.page = page?page:this.listParams.page;
      let params = {
        page: this.listParams.page,
        limit: this.listParams.limit,
        status: this.listParams.status,
        keyword: this.listParams.keyword,
        createPlatform: this.listParams.createPlatform,
          tagStatus: this.listParams.tagStatus
      };
      this.errorList.forEach(val => {
        if (
          val.active &&
          (this.listParams.status == 0 || this.listParams.status == 6)
        ) {
          params.abnormalType = val.abnormalType;
        }
      });
      if (page) {
        params.page = page;
      }
      if (this.listParams.startTime && !this.listParams.endTime) {
        this.$message.info({ message: "请输入正确的时间", showClose: true });
        return;
      }
      if (!this.listParams.startTime && this.listParams.endTime) {
        this.$message.info({ message: "请输入正确的时间", showClose: true });
        return;
      }
      if (this.listParams.startTime && this.listParams.endTime) {
        params.startTime = this.listParams.startTime;
        params.endTime = this.listParams.endTime;
      }
      if (this.searchSupplierList.length > 0) {
        let ids = [];
        this.searchSupplierList.forEach(item => {
          ids.push(item.id);
        });
        params.supplierIds = ids.join(",");
      }
      if (this.searchBuyerList.length > 0) {
        let ids = [];
        this.searchBuyerList.forEach(item => {
          ids.push(item.userId);
        });
        params.userIds = ids.join(",");
      }
      this.getStatusList(params)
      this.api.order.list(params).then(res => {
        this.tableData = res.data.dataList;
        this.total = res.data.total;
      });
    },
    applyPass(item) {
      this.$msgbox({
        title: "",
        message: "确认通过审核？",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(action => {
        if (action === "confirm") {
          let params = {
            userId: item.userId,
            orderNum: item.orderNum,
            isPass: 1
          };
          this.api.order.applyPass(params).then(res => {
            this.$message.success({ message: res.message, showClose: true });
            this.getList(1);
          });
        }
      });
    },
    confirmReceive(item) {
      this.$msgbox({
        title: "",
        message: "确认收到订单所有商品？",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(action => {
        if (action === "confirm") {
          this.api.order.applyPass({ orderNum: item.orderNum }).then(res => {
            this.$message.success({ message: res.message, showClose: true });
            this.getList(1);
          });
        }
      });
    },
      //异常说明弹窗
      showExplainBtn(){
        this.showExplain = true;
      },
    payConfirm(item) {
      this.$msgbox({
        title: "",
        message: "是否确认收款？",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(action => {
        if (action === "confirm") {
          this.api.order
            .payConfirm({
              userId: item.userId,
              orderNum: item.orderNum
            })
            .then(res => {
              this.$message.success({ message: res.message, showClose: true });
              this.getList(1);
            });
        }
      });
    },
    applyRefund(item) {
      this.$msgbox({
        title: "",
        message: "确认退款？",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(action => {
        if (action === "confirm") {
          let params = {
            userId: item.userId,
            orderRefundId: item.orderRefundId,
            isPass: 1
          };
          this.api.order.applyRefund(params).then(res => {
            this.$message.success({ message: res.message, showClose: true });
            this.getList(1);
          });
        }
      });
    },
    checkRefundDetail(item) {
      this.refundDetailParams = {
        userId: item.userId,
        orderNum: item.orderNum
      };
      this.showRefundDetail = true;
    },
    closeRefundDetail() {
      this.showRefundDetail = false;
      this.getList(1);
    },
    changeStatus(item) {
      this.listParams.status = item.id;
      this.listParams.page = 1;
      // if(this.listParams.status == 0){
      //   this.errorList.forEach(val=>{
      //     val.active = false
      //   })
      // }
      this.getList(1);
    },
    refuseOrder(item) {
      let user = JSON.parse(localStorage.user);
      this.$msgbox({
        title: "",
        message: "确认驳回？",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(action => {
        if (action === "confirm") {
          let params = {
            userId: item.userId,
            orderNum: item.orderNum,
            operator: user.nickName
          };
          this.api.order.refuse(params).then(res => {
            this.$message.success({ message: res.message, showClose: true });
            this.getList(1);
          });
        }
      });
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
    },
    dialogBuyer(res) {
      this.searchBuyerList = res.buyerList;
      this.showBuyerModal = false;
    },
    dialogBuyerCancel() {
      this.showBuyerModal = false;
    },
    removeSearchBuyer(index) {
      this.searchBuyerList.splice(index, 1);
    },
    seekDetail(item) {
      let path =
        "order/detail/" +
        (item.userId ? item.userId : "") +
        "/" +
        (item.orderNum ? item.orderNum : "") +
        "/" +
        (item.orderStatus ? item.orderStatus : "");
      window.open(path, "_blank");
    },
    openBackModal(item) {
      this.refundParams.userId = item.userId;
      this.refundParams.orderNum = item.orderNum;
      this.showRefundModal = true;
    },
    refund(res) {
      this.console("refund:", res);
      this.showRefundModal = false;
      this.getList(1);
    },
    refundCancel() {
      this.showRefundModal = false;
    },

    // upload ----------------
    successHandler(resultMsg) {
      if (resultMsg.success) {
        this.getList(1);
      }
    },
    importLogistics() {
      this.$refs.multipleUpload.onSubmit();
    },

    // export ----------------
    exportOrder() {
      let params = {
        status: this.copy(this.listParams.status)
      };
      // if(this.listParams.keyword) {
      //   if(!this.listParams.keywordType) {
      //     this.$message.info({message:'情选择关键字类型', showClose:true});
      //     return
      //   } else {
      //     params.keywordType = this.listParams.keywordType;
      //     params.keyword = this.listParams.keyword;
      //   }
      // }
      // if(this.listParams.keywordType) {
      //   if(!this.listParams.keyword) {
      //     this.$message.info({message:'情输入关键字', showClose:true});
      //     return
      //   } else {
      //     params.keywordType = this.listParams.keywordType;
      //     params.keyword = this.listParams.keyword;
      //   }
      // }
      if (this.listParams.startTime && !this.listParams.endTime) {
        this.$message.info({ message: "请输入正确的时间", showClose: true });
        return;
      }
      if (!this.listParams.startTime && this.listParams.endTime) {
        this.$message.info({ message: "请输入正确的时间", showClose: true });
        return;
      }
      if (this.listParams.startTime && this.listParams.endTime) {
        params.startTime = this.listParams.startTime;
        params.endTime = this.listParams.endTime;
      }
      if (this.searchSupplierList.length > 0) {
        let ids = [];
        this.searchSupplierList.forEach(item => {
          ids.push(item.id);
        });
        params.supplierIds = ids.join(",");
      }
      if (this.searchBuyerList.length > 0) {
        let ids = [];
        this.searchBuyerList.forEach(item => {
          ids.push(item.userId);
        });
        params.userIds = ids.join(",");
      }
      this.exportProgress = 0;
      this.exportKey = "";
      this.api.order.exportOrder(params).then(res => {
        let key = res.data;
        let exportTimer = setInterval(() => {
          this.api.progress.rate({ data: key }).then(res => {
            if (res.success) {
              if (res.data.finished) {
                this.exportProgress = res.data.percent;
                this.exportKey = key;
                clearInterval(exportTimer);
              } else {
                this.exportProgress = res.data.percent;
              }
            } else {
              this.$message.info({ message: res.message, showClose: true });
              this.exportProgress = 0;
              clearInterval(exportTimer);
            }
          });
        }, 1000);
      });
    },
    downloadOrder() {
      this.showExportModal = false
      window.open(
        "/common/progress_result.do?data=" + this.exportKey,
        "_blank"
      );
    },
    // 推单
    pushOrder (item) {
      const {userId, orderNum} = item
      this.$msgbox({
        title: "订单推单",
        message: "确定要对该订单进行推单么？",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(action => {
        if (action === "confirm") {
          let params = {
            userId,
            orderNum
          }
          this.api.order.pushOrder(params).then(res => {
            this.$message.success({ message: res.message, showClose: true });
            this.getList(1);
          });
        }
      });
    }
  }
};
</script>
<style lang='scss'>
.center-order {
    min-width: 1280px !important;
  // .timepicker-control{
  //   .el-input__inner{
  //     width:172px!important;
  //     margin-right: 0px!important;
  //   }
  //   margin-right: 20px;
  // }
  .search-bar {
    // display: flex;
    .errorListBox {
      display: inline-block;
      .item {
        cursor: pointer;
        display: inline-block;
        border-radius: 100px;
        border: 1px solid #e1376c;
        padding: 8px 10px;
        color: #e1376c;
        margin-right: 10px;
        font-size: 14px;
        line-height: 12px;
        &.active {
          background: #e1376c;
          color: #fff;
        }
      }
    }
    button {
      margin-right: 10px;
    }
    .btn-org-small,
    .btn-small {
      margin-right: 10px;
      margin-top:4px;
      a {
        color: #fff;
        padding-left: 5px;
      }
    }
  }
  .form-wrap {
    .el-input__inner {
      width: 195px;
      height: 30px;
      // margin-right: 10px;
    }

    .el-input__icon {
      line-height: 30px;
    }
  }
  .list-wrap .table {
    td {
      padding: 10px 12px 10px 0;
    }
    td:nth-of-type(1) {
      padding-left: 20px;
    }
  }
  .table {
    .link-op {
      margin-right: 10px;
        line-height: 20px;
    }
    .supplier-color {
      color: #f06941;
    }
    .hidden {
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      max-width: 100%;
    }
  }
  .el-dialog__body {
    .import-dialog {
      padding: 0;
      .import-box {
        padding: 0 10px;
        .import-btn {
          display: inline-block;
          vertical-align: top;
          height: 30px;
          line-height: 30px;
          color: #f06941;
          border: 1px dashed #f06941;
          padding: 0 6px;
          margin-right: 20px;
          border-radius: 15px;
        }
        .btn-org-small {
          margin: 5px 10px 10px 0;

          .close-btn {
            padding: 0 6px;
          }
        }
      }
      .radio-box {
        padding: 15px 10px 0 10px;
        .radio-item {
          color: #333333;
          font-size: 14px;
          font-weight: bold;
        }
        .tips {
          padding-left: 10px;
          font-size: 14px;
          color: #f06941;
        }
      }
      .error-box {
        margin: 15px 10px 0;
        padding: 10px;
        border: 1px solid $border-color;
        border-radius: 4px;
        p {
          color: #333;
          font-size: 14px;
          line-height: 16px;
          padding-bottom: 8px;
          &:last-child {
            padding-bottom: 0;
          }
        }
      }
      .uploadBox {
      }
    }
    .dialog-back {
      padding: 0;
      .table {
        padding-left: 0;
        .back-goods-img {
          display: inline-block;
          vertical-align: top;
          img {
            max-width: 54px;
            max-height: 54px;
          }
        }
        .back-goods-name {
          display: inline-block;
          vertical-align: top;
          width: 260px;
          margin-left: 10px;
          h2 {
            font-size: 14px;
            line-height: 18px;
            height: 36px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
          }
          p {
            font-size: 14px;
            max-width: 100%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            margin-top: 5px;
          }
        }
      }
    }
  }
  .el-progress-bar__outer {
    background-color: #f7f7f7;
  }
  .export-dialog {
    padding-left: 0;
  }
    .fontCol{
        color: #333333;
        margin-top: 10px;
        line-height: 1.4;
    }
    .titltText{
        font-size: 16px;
        font-weight: 600;
    }
}
</style>
<style lang='scss' scoped>
.overShow {
  overflow: hidden;
  white-space: nowrap;
  word-break: normal;
  text-overflow: ellipsis;
  color: #e1376c;
}
.center-order {
  .list-wrap {
    .search-bar {
      .form-wrap-last {
        margin: 0;
      }
      .form-wrap {
        .el-date-editor {
          width: 210px !important;
        }
      }
    }
  }
}
    .anomalyBox{
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .explain{
        cursor: pointer;
        color: #3A62E1;
        border-bottom:1px solid #3a62e1;
        margin-right: 10px;
    }
    .marTop{
        margin-top: 25px;
    }
    .closeBtn{
        color: #ffffff;
        background: #E1376C;
    }
</style>
<style>
    .el-dialog__body{
        box-sizing: border-box;
        padding: 10px 20px;
    }
</style>
<style scoped lang="scss">
    .importBox{
        box-sizing: border-box;
        padding: 10px 20px;
        display: flex;
        justify-content: flex-start;
        align-items: center;
        div {
          margin-right: 10px;
        }
    }
    .marLeft{
        margin-left: 10px;
    }
    @media screen and (max-width: 1650px) {
        .marTopFlag{
            margin-top: 10px;
        }
    }
    @media screen and (min-width: 1650px) {
        .marTopFlag{
            margin-top: 0px;
        }
    }

</style>
