<template>
    <div class="center-order">
        <div class="tab-bar tab-bar-Box">
            <div>
                <div
                        class="tab-item"
                        v-for="item in statusList"
                        :key="item.id"
                        :class="{'active':item.id==listParams.status}"
                        @click="changeStatus(item)"
                >{{item.name}} </div>
            </div>
            <div>
                <a class="clickText abnormalTest" @click="showExplainBtn">异常说明?</a>
            </div>

        </div>
        <div class="list-wrap">
            <div class="search-bar">
                <div class="search-bar-title">其它筛选：</div>
                <div class="form-wrap">
                    <div class="form-name">订单筛选</div>
                    <input
                            type="text"
                            class="form-control mediaInput"
                            placeholder="请输入订单号、收货等信息"
                            v-model.trim="listParams.keyword"
                            @keyup.enter="getList(1)"
                            style="width: 200px;"
                    />
                </div>
                <div class="marTopBox">
                    <div class="form-name marLeft">供货商</div>
                    <select class="form-control" v-model="listParams.supplierId" style="margin-right: 20px" >
                        <option value="">全部供货商</option>
                        <option v-for="item in supplyList" :key="item.id" :value="item.id">{{item.supplierName}}</option>
                    </select>
                    <div class="form-wrap form-wrap-last">
                        <div class="form-name">推送时间</div>
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
                    <button class="btn-fill-main" @click="getList(1)">搜索</button>
                    <div class="exportBox pull-right">
                        <button class="btn-fill-main" @click="openExportModal()">导出订单</button>
                    </div>
                </div>

            </div>
            <table class="table table-text">
                <thead>
                <tr>
                    <th class="table-w170">推送时间</th>
                    <th class="table-w180">包裹号</th>
                    <th class="table-w150">供应商名称</th>
                    <th class="table-w100">收货人</th>
                    <th class="table-w120">收货电话</th>
                    <th class="table-w100">包裹状态</th>
                    <th class="table-w80">物流单号</th>
                    <th class="table-w80">异常状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in tableData" :key="item.id">
                    <td>
                        {{item.createTime}}
                    </td>
                    <td>
                        <a class="link ellipsis" @click="seekTrajectory(item)">{{item.pkgNo}}</a>
                    </td>
                    <td>{{item.supplierName}}</td>
                    <td>
                        {{item.recipientName}}
                    </td>
                    <td >
                        {{item.recipientPhone}}
                    </td>
                    <td >
                        {{item.expressStatusName}}
                    </td>
                    <td>{{item.expressNum?item.expressNum:'--'}}</td>
                    <td>
                        {{item.abnormalStatusName}}
                    </td>
                    <td>
                        <a class="link ellipsis" @click="seekDetail(item)">订单详情</a>
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
        <el-dialog title="异常订单说明" :visible.sync="showExplain" width="620px">
            <div>
                <div   class="marTop" style="margin-top: 20px">
                    <h2 class="titltText"> 1、发货超时</h2>
                    <p class="fontCol">
                        订单创建后(以推送至供货商成功时间进行计算)，国内、保税、香港直邮的订单超过48小时没有物流单号，海外其他直邮超过72小时没有物流单号。
                    </p>
                    <p>
                        备注：此处的时间为工作日时间，周末及节假日需要去除；
                    </p>
                </div>
                <div   class="marTop">
                    <h2 class="titltText"> 2、物流超时</h2>
                    <p class="fontCol">
                        填写单号后超过24小时（国内、保税）或48小时（香港直邮、海外其他直邮）没有第一条物流轨迹的即为物流超时；
                    </p>
                </div>
                <div   class="marTop">
                    <h2 class="titltText"> 3、物流停滞</h2>
                    <p class="fontCol">
                        有物流轨迹，但当前轨迹距到达下一个轨迹前超过48个小时没有最新的物流轨迹则为物流停滞（已签收的则不计在内）
                    </p>
                    <p>
                        备注：物流停滞的异常可能在清关前也可能在清关后；
                    </p>
                </div>
                <div   class="marTop">
                    <h2 class="titltText"> 4、清关超时</h2>
                    <p class="fontCol">
                        进入清关状态后，168小时没有下一条物流轨迹；
                    </p>
                </div>
                <div   class="marTop">
                    <h2 class="titltText"> 5、签收超时</h2>
                    <p class="fontCol">
                        A.国内、保税填了运单号，但是168小时内未签收;
                    </p>
                    <p class="fontCol">
                        B.香港直邮填了运单号，但是336小时内未签收;
                    </p>
                    <p class="fontCol">
                        C.海外其他直邮填了运单号，但是480小时内未签收;
                    </p>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
          <button class="btn-fill-grey-main closeBtn" style="margin-top: 10px"  @click="showExplain=false">关闭</button>
        </span>
        </el-dialog>
        <logisticsTrajectory ref="trajectory"></logisticsTrajectory>


    </div>
</template>
<script>
    import Pagination from "@/components/pagination";
    import noData from "@/components/noData";
    import logisticsTrajectory from '../supplierOrder/component/logisticsTrajectory'

    export default {
        components: {
            Pagination,
            noData,
            logisticsTrajectory
        },
        props: {
            msg: String
        },
        data() {
            return {
                searchShopList:[],
                supplyList:[],
                showExplain:false,
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
                    status: -1,
                    keyword: "",
                    startTime: "",
                    endTime: "",
                    supplierIds:''
                },
                total: -1,
                showExportModal:false,
                tableData: [],
                importLogRadio: 1,
                exportProgress: 0,
                exportKey: "",
                statusList:[],
                orderObj:{}
            };
        },
        created() {
            this.showExportModal = false
            this.init();
            this.orderExpressStatusList();
            this.getOrderSupplierList();
        },

        methods: {
            //获取供货商列表
            getOrderSupplierList(){
                this.api.supplier.getOrderSupplierList().then(res=>{
                    this.supplyList = res.data;
                })
            },
            //查看物流轨迹
            seekTrajectory(item){
                this.orderObj = item;
                this.$refs.trajectory.showTrajectory = true;
                this.$refs.trajectory.getOrderPkgExpress();

            },
            //获取包裹状态
            orderExpressStatusList(){
                this.api.supplier.supplierOrderAbnormalStatusList().then(res=>{
                    this.statusList = res.data;
                    this.listParams.status = this.statusList[0].id
                })
            },

            init() {
                this.getList(1);
            },
            showExplainBtn(){
                this.showExplain = true
            },
            getList(page){
                this.listParams.page = page;
                let params = {
                    page: this.listParams.page,
                    limit: this.listParams.limit,
                    status: this.listParams.status,
                    keyword: this.listParams.keyword
                };
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

                    params.supplierIds = this.listParams.supplierIds;

                this.api.supplier.queryAbnormalSupplierOrderList(params).then(res => {
                    this.tableData = res.data.dataList;
                    this.total = res.data.total;
                });
            },

            //异常说明弹窗

            changeStatus(item) {
                this.listParams.status = item.id;
                this.listParams.page = 1;
                this.getList(1);
            },

            seekDetail(item) {
                let routeData = this.$router.resolve({
                    path: "/center/supplier/supplierOrderDetail/",
                    query: {
                        userId:item.userId,
                        orderNum:item.orderNum,
                        orderStatus:item.orderStatus,
                    }
                });
                window.open(routeData.href, '_blank');

            },

            openExportModal() {
                this.exportProgress = 0;
                this.exportKey = "";
                this.showExportModal = true;
            },

            // export ----------------
            exportOrder() {
                let params = {
                    status: this.copy(this.listParams.status)
                };
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

                params.supplierId = this.listParams.supplierId
                params.abnormal = true

                this.exportProgress = 0;
                this.exportKey = "";
                this.api.supplier.downloadOrder(params).then(res => {
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
        }
    };
</script>
<style lang='scss'>
    .center-order {
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
        border: none !important;
    }
    .tab-bar-Box{
        display: flex;
        justify-content: space-between;
    }
    .abnormalTest{
        margin-right: 20px;
        border-bottom: 1px solid #3A62E1;
    }
    @media screen and (max-width: 1560px) {
        .mediaInput {
            width: 810px !important;
        }
        .marTopBox{
            margin-top: 10px;
        }
        .marLeft{
            margin-left: 80px;
        }
    }
    @media screen and (min-width: 1560px) {
        .mediaInput {
            width: 200px;
        }
        .marTopBox{
            display: inline-block;
        }
    }
</style>
<style>
    .el-dialog__body{
        box-sizing: border-box;
        padding: 10px 20px;
    }
</style>
