<template>
    <div class="order-detail">
        <div class="titleMar">
            订单详情
        </div>
        <div class="order-info">
            <div class="info">
                <div class="info-item">
                    <span class="name">下单时间</span>
                    <span class="symbol">:</span>
                    <span>{{detailObj.createTime}}</span>
                </div>

                <div class="info-item">
                    <span class="name">订单号</span>
                    <span class="symbol">:</span>
                    <span>{{detailObj.orderNum}}</span>
                </div>
            </div>

            <div class="info">
                <div class="info-item">
                    <span class="name">包裹状态</span>
                    <span class="symbol">:</span>
                    <span
                            class="red-color"
                    >{{pkgItem.expressStatusName}}</span>
                </div>
                <div class="info-item">
                    <span class="name">包裹号</span>
                    <span class="symbol">:</span>
                    <span>{{pkgItem.pkgNo}}</span>
                </div>
            </div>
            <div class="info">
                <div class="info-item">
                    <span class="name">库存状态</span>
                    <span class="symbol">:</span>
                    <span>{{pkgItem.lockStatusName}}</span>
                </div>
                <div class="info-item">
                    <span class="name">供货商</span>
                    <span class="symbol">:</span>
                    <span>{{detailObj.supplierName}}</span>
                </div>
            </div>
            <div class="info">
                <div class="info-item" v-show="pkgItem.deliveryTime">
                    <span class="name">发货时间</span>
                    <span class="symbol">:</span>
                    <span>{{pkgItem.deliveryTime}}</span>
                </div>
                <div class="info-item" v-show="pkgItem.abnormalStatusName">
                    <span class="name">异常状态</span>
                    <span class="symbol">:</span>
                    <span>{{pkgItem.abnormalStatusName}}</span>
                    <span @click="closeAbnormal" class="marLeft15 textCol">关闭异常</span>
                </div>
            </div>
        </div>
        <div class="titleMar">
            收货人信息
        </div>
        <div class="order-info">
            <div class="info">
                <div class="info-item">
                    <span class="name">收货人</span>
                    <span class="symbol">:</span>
                    <span>{{pkgItem.recipientName}}</span>
                </div>
                <div class="info-item">
                    <span class="name">收货电话</span>
                    <span class="symbol">:</span>
                    <span>{{pkgItem.recipientPhone}}</span>
                </div>
            </div>
            <div class="info">
                <div class="info-item">
                    <span class="name">收货地址</span>
                    <span class="symbol">:</span>
                    <span class="detial-address">
                        <el-popover placement="top" width="200" trigger="hover" class="imgBoxCont">
                                  <span>{{pkgItem.recipientAddress}}</span>
                                  <span slot="reference">{{pkgItem.recipientAddress}}</span>
                                </el-popover>
                        </span>
                </div>
                <div class="info-item">
                    <span class="name">身份证号</span>
                    <span class="symbol">:</span>
                    <span>{{pkgItem.recipientIdCard}}</span>
                    <span v-show="pkgItem.recipientIdCardPhotoF" class="marLeft20 textCol" @click="lookCard">查看身份证照片</span>
                </div>
            </div>
        </div>
        <div class="order-content">
            <div class="titleMar">
                商品信息
            </div>
            <div class="order-inner">
                <table class="inner">
                    <thead class="table-title">
                    <tr>
                        <th class="table-w100">商品信息</th>
                        <th class="table-w80">商品ID</th>
                        <th class="table-w100">商品品牌</th>
                        <th class="table-w100">商品条形码</th>
                        <th class="table-w100">发货方式</th>
                        <th class="table-w100">供货价</th>
                        <th class="table-w60">总数量</th>
                        <th class="table-w60">退货数量</th>
                    </tr>
                    </thead>
                    <tbody class="info-body">
                    <tr v-for="(goods, goods_index) in itemList" :key="goods_index+'goods'">
                        <td class="goods-info" @click="goGoodsDetail(goods,item)">
                            <el-popover placement="top" width="200" trigger="hover">
                                <img style="width:100%;height:100%" :src="goods.imageUrl" />
                                <img slot="reference" :src="goods.imageUrl" />
                            </el-popover>
                            <h4 class="item-title">{{goods.name}}</h4>
                            <el-popover placement="top" width="200" trigger="hover">
                                <p>规格:{{goods.sku}}</p>
                                <p slot="reference">规格:{{goods.sku}}</p>
                            </el-popover>
                        </td>
                        <td>{{goods.id}}</td>
                        <td>{{goods.brand}}</td>
                        <td>{{goods.upc}}</td>
                        <td>{{goods.channelName}}</td>
                        <td>{{goods.price}}</td>
                        <td>{{goods.num}}</td>
                        <td>{{goods.returnNum}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="packageBox">
            <div class="packageBtnBox">
                <div class="packageBtn" :class="[pkgIndex==index?'packActive':'']" @click="switchPkg(index)" v-for="(item,index) of expressDetailList">包裹{{index+1}}</div>
            </div>
        </div>
        <div class="order-content">
            <div class="order-inner">
                <h2>包裹信息</h2>
                <div class="paddingBox">
                    <div class="titleBox">
                        <div class="itemWidth">
                            物流公司：{{pkgItem.expressCompany}}
                        </div>
                        <div class="titleNum">
                          <span> 物流单号：{{pkgItem.expressNum}}</span><el-button type="text" class="lookBox" @click="showTrajectoryBtn" v-show="pkgItem.expressNum">查看物流轨迹</el-button><el-button type="text" v-show="pkgItem.expressNum">同步物流</el-button>
                        </div>

                    </div>
                    <div>
<!--                        备注：{{pkgItem.expressNum}}-->
                    </div>
                </div>
            </div>
        </div>
        <div class="order-content">
            <div class="order-inner">
                <div>
                    <h2>包裹商品明细
                        <el-button type="text" class="lookBox" @click="addLogisticsBtn">添加物流</el-button>

                    </h2>
                </div>

                <table class="inner">
                    <thead class="table-title">
                    <tr>
                        <th class="table-w100">商品信息</th>
                        <th class="table-w80">商品ID</th>
                        <th class="table-w100">商品品牌</th>
                        <th class="table-w100">商品条形码</th>
                        <th class="table-w100">发货方式</th>
                        <th class="table-w100">供货价</th>
                        <th class="table-w60">总数量</th>
                        <th class="table-w60">退货数量</th>
                    </tr>
                    </thead>
                    <tbody class="info-body">
                    <tr v-for="(goods, goods_index) in pkgItem.itemList" :key="goods_index+'goods'">
                        <td class="goods-info" @click="goGoodsDetail(goods,item)">
                            <el-popover placement="top" width="200" trigger="hover">
                                <img style="width:100%;height:100%" :src="goods.imageUrl" />
                                <img slot="reference" :src="goods.imageUrl" />
                            </el-popover>
                            <h4 class="item-title">{{goods.name}}</h4>
                            <el-popover placement="top" width="200" trigger="hover">
                                <p>规格:{{goods.sku}}</p>
                                <p slot="reference">规格:{{goods.sku}}</p>
                            </el-popover>
                        </td>
                        <td>{{goods.id}}</td>
                        <td>{{goods.brand}}</td>
                        <td>{{goods.upc}}</td>
                        <td >{{goods.channelName}}</td>
                        <td>{{goods.price}}</td>
                        <td>{{goods.num}}</td>
                        <td>{{goods.returnNum}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="titleMar">
            操作记录
        </div>
        <div class="order-control-log">
            <table class="table">
                <thead>
                <tr>
                    <th class="table-w200">操作时间</th>
                    <th>操作记录</th>
                    <th class="table-w100">操作人</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item, i) in  pkgItem.operateRecordList" :key="i">
                    <td>{{item.time}}</td>
                    <td class="noraw">{{item.description}}</td>
                    <td>{{item.operator}}</td>
                </tr>
                </tbody>
            </table>

        </div>
        <el-dialog title="添加物流:" :visible.sync="showLogisticsModal" width="608px">
            <div>
                <div>
                    <el-table
                            ref="multipleTable"
                            :data="pkgItem.itemList"
                            tooltip-effect="dark"
                            style="width: 100%"
                            @selection-change="handleSelectionChange">
                        <el-table-column
                                type="selection"
                                width="30">
                        </el-table-column>
                        <el-table-column
                                label="商品标题"
                                >
                            <template slot-scope="scope">{{ scope.row.name }}</template>
                        </el-table-column>
                        <el-table-column
                                prop="brand"
                                label="商品品牌"
                               >
                        </el-table-column>
                        <el-table-column
                                prop="upc"
                                label="商品条形码"
                                show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column
                                prop="sku"
                                label="商品规格"
                                show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column
                                prop="num"
                                label="数量"
                                show-overflow-tooltip>
                            <template slot-scope="scope">
                                <el-input  v-model="scope.row.num" :max="scope.row.num" :placeholder="'数量'+scope.row.num"></el-input>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <div>
                    <div class="dialog-form border-bottom pathBox">
                        <div class="search-item">
                            <span class="title marRight10">物流公司</span>
                            <select
                                    class="form-control"
                                    v-model="addLogisticsParams.expressCompanyId"
                                    :class="{'unset-select':!addLogisticsParams.expressCompanyId}"
                            >
                                <option value>请选择物流公司</option>
                                <option v-for="item in expCompany" :key="item.id" :value="item.id">{{item.name}}</option>
                            </select>
                        </div>
                        <div class="search-item marLeft20">
                            <span class="title marRight10">物流单号 </span>
                            <input
                                    type="text"
                                    class="form-control"
                                    placeholder="请输入物流单号"
                                    v-model="addLogisticsParams.expressNum"
                            />
                        </div>
                    </div>

                </div>
            </div>
            <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="saveLogistics()">保存</button>
        <button class="btn-fill-grey-main" @click="showLogisticsModal=false">取消</button>
      </span>
        </el-dialog>
        <el-dialog title="身份证照片:" :visible.sync="showDelLogisticsModal" width="468px">
            <div class="dialog-form">
                <img class="cardImg" :src="pkgItem.recipientIdCardPhotoF" alt="">
                <img class="cardImg marLeft10" :src="pkgItem.recipientIdCardPhotoB" alt="">
            </div>
            <span slot="footer" class="dialog-footer">
                <button class="btn-fill-grey-main" @click="showDelLogisticsModal=false">关闭</button>
            </span>
        </el-dialog>
<!--        <el-dialog-->
<!--                title="物流轨迹"-->
<!--                :visible.sync="showTrajectory"-->
<!--                width="608px"-->
<!--               >-->
<!--             <div>-->
<!--                 <div class="trajectoryTitle">-->
<!--                     <div class="titleText"><span>物流公司：中通快递</span></div>-->
<!--                     <div class="titleText">-->
<!--                         <span>物流单号：212121212</span>-->
<!--                     </div>-->

<!--                 </div>-->
<!--                 <div>-->
<!--                     <div>您的快递已到达河南省平顶山市郏县长桥镇</div>-->
<!--                 </div>-->
<!--             </div>-->
<!--             <span slot="footer" class="dialog-footer">-->
<!--                <div class="closeBtn" @click="showTrajectory = false">关闭</div>-->
<!--              </span>-->
<!--        </el-dialog>-->
        <logisticsTrajectory ref="trajectory"></logisticsTrajectory>

    </div>
</template>

<script>
    import Pagination from "@/components/pagination";
    import logisticsTrajectory from '../supplierOrder/component/logisticsTrajectory'

    export default {
        components: { Pagination,logisticsTrajectory},
        data() {
            return {
                detailObj:{},
                pkgIndex:0,
                multipleSelection:[],
                itemList:[],
                pkgItem:[],
                expressDetailList:[],
                logisticsTab: "",
                showLogisticsModal: false,
                showTrajectory:false,//物流轨迹
                showDelLogisticsModal: false,
                resetSupData: {},
                expCompany: [],
                addLogisticsParams: {
                    expressCompanyId: "",
                    expressNum: ""
                },
                controlLogList: [], // 操作记录
                total: 0,
                listParams: {
                    limit: 10,
                    page: 1
                },
                orderNum:'',
                orderStatus:"",
                orderObj:{}
            };
        },
        watch: {

        },
        created() {
            this.init();
        },
        methods: {
            init() {
                this.orderNum = this.$route.query.orderNum || "";
                this.orderStatus = this.$route.query.orderStatus || "";
                // 获取所有物流公司
                this.api.common.expressCompany({}).then(res => {
                    this.expCompany = res.data;
                });
                this.getDetail();
            },
            //关闭异常
            closeAbnormal(){
                this.$msgbox({
                    title: '异常处理',
                    message: '你确定要关闭该包裹的异常状态吗？',
                    showCancelButton: true,
                    confirmButtonText: '确定',
                    cancelButtonText: '取消'
                }).then(action => {
                    if(action === 'confirm') {
                        this.api.supplier.closeAbnormal({
                            orderNum:this.orderNum,
                            id:this.pkgItem.id
                        }).then(res=>{
                            this.$message.success({message:res.message, showClose:true});
                            this.getDetail();
                        })
                    }
                });
            },
            //查看身份证
            lookCard(){
                this.showDelLogisticsModal=true
            },
            //切换包裹
            switchPkg(index){
                this.pkgItem = this.expressDetailList[index];
                this.pkgIndex = index;
            },
            handleSelectionChange(val) {
                console.log(val)
                this.multipleSelection = val;
            },
            //显示物流轨迹
            showTrajectoryBtn(){
                 this.orderObj = {
                     orderNum:this.orderNum,
                     pkgNo:this.pkgItem.pkgNo
                 }
                this.$refs.trajectory.showTrajectory = true;
                this.$refs.trajectory.getOrderPkgExpress();
            },
            //添加物流
            addLogisticsBtn(){
              this.showLogisticsModal = true
            },
            getDetail() {
                this.api.supplier.getSupplierOrderDetail({orderNum:this.orderNum}).then(res => {
                    this.detailObj = res.data;
                    this.expressDetailList = res.data.expressDetailList;
                    this.itemList = res.data.itemList;
                    this.pkgItem = this.expressDetailList[0];
                });
            },



            saveLogistics() {
                let itemList = [];
                this.multipleSelection.forEach((item)=>{
                    itemList.push({
                        id:item.id,
                        num:item.num
                    })
                });
                console.log(itemList)
                this.api.supplier.addExpressNum({
                    ...this.addLogisticsParams,
                    orderNum:this.orderNum,
                    pkgNo:this.pkgItem.pkgNo,
                    itemList:itemList
                }).then(res => {
                    this.$message.success({ message: res.message, showClose: true });
                    this.showLogisticsModal = false;
                    setTimeout(()=>{
                        this.getDetail();
                    },1000)
                });
            }
        },

    };
</script>

<style lang="scss" scoped>
    .order-detail {
        .order-process {
            margin-left: -54px;
            padding-bottom: 22px;
            .process-item {
                display: inline-block;
                vertical-align: top;
                width: 220px;
                text-align: center;
                position: relative;
                .process-name {
                    color: #333333;
                    font-size: 14px;
                    padding-bottom: 14px;
                }
                .circle {
                    width: 14px;
                    height: 14px;
                    background: #fff;
                    border-radius: 14px;
                    border: 4px solid #cccccc;
                    margin-left: 104px;
                }
                .process-time {
                    color: #666666;
                    font-size: 12px;
                    padding-top: 10px;
                }
                &::after {
                    content: "";
                    position: absolute;
                    width: 175px;
                    height: 1px;
                    background: #ccc;
                    right: -88px;
                    top: 35px;
                }
                &:last-child {
                    &::after {
                        content: "";
                        width: 0;
                        height: 0;
                    }
                }
            }
            .process-item-active {
                .circle {
                    width: 14px;
                    height: 14px;
                    background: #fff;
                    border-radius: 14px;
                    border: 4px solid #70d008;
                    margin-left: 104px;
                }
                &::after {
                    background: #70d008;
                }
            }
        }
        .order-info {
            background: #fff;
            border-radius: 4px;
            padding: 20px 20px 10px 20px;
            h2 {
                color: #333333;
                font-size: 14px;
                font-weight: bold;
                padding-bottom: 15px;
            }
            .receiver {
                padding-top: 10px;
                padding-bottom: 15px;
                .reciverImg{
                    position: relative;
                    .dialogReciver{
                        width: 300px;
                        height: 200px;
                        display: none;
                        position: absolute;
                        left:25px;
                        top:0;
                        padding: 12px;
                        z-index: 10;
                        border-radius: 10px;
                        background-color: #fff;
                        border-color: transparent;
                        border-style: solid;
                        border: 1px solid #ebeef5;
                        box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
                    }
                    &:hover .dialogReciver{
                        display: block;
                    }
                }
            }
            .info {
                .info-item {
                    white-space: normal;
                    word-break: break-all;
                    padding-bottom: 10px;
                    display: inline-block;
                    vertical-align: top;
                    width: 450px;
                    .detial-address {
                        width: 364px;
                        white-space: nowrap;
                        overflow:hidden;
                        span {
                            text-overflow:ellipsis;
                            width: 100%;
                            overflow: hidden;
                        }
                    }
                    span {
                        display: inline-block;
                        vertical-align: top;
                        font-size: 14px;
                        color: #333333;
                        line-height: 24px;
                    }
                    .link {
                        font-size: 12px;
                        display: inline-block;
                        vertical-align: top;
                        width: 60px;
                        height: 24px;
                        text-align: center;
                        line-height: 22px;
                        border: 1px solid $main-color;
                        color: $main-color;
                        border-radius: 4px;
                    }
                    .detail {
                        font-size: 12px;
                        display: inline-block;
                        vertical-align: top;
                        width: 86px;
                        height: 24px;
                        text-align: center;
                        line-height: 22px;
                        border: 1px solid #ddd;
                        color: #666;
                        border-radius: 4px;
                        margin-left: 10px;
                        cursor: pointer;
                    }
                    .name {
                        width: 72px;
                        text-align-last: justify;
                    }
                    .symbol {
                        padding: 0 8px 0 2px;
                    }
                    .red-color {
                        color: $main-color;
                    }
                    &:last-child {
                        padding-bottom: 0;
                    }
                }
            }
        }
        .order-content {
            position: relative;
            .order-inner {
                margin-top: 10px;
                background: #fff;
                width: 100%;
                border-radius: 4px;
                h2 {
                    color: #333333;
                    font-size: 14px;
                    padding: 14px 16px;
                    border-bottom: 1px solid $border-color;
                    font-weight: bold;
                }
                .inner {
                    width: 100%;
                    tbody {
                        tr {
                            border-bottom: 1px solid $border-color;
                        }
                        .tips-top {
                            background: #f5f5f5;
                            border: none;
                            .check-log {
                                line-height: 44px;
                                margin-right: 20px;
                            }
                            .pkgStatus {
                                line-height: 44px;
                                margin-right: 20px;
                                font-size: 14px;
                                color: #333333;
                                font-weight: bold;
                                span {
                                    font-size: 14px;
                                    color: #333333;
                                    font-weight: bold;
                                }
                                .pkgSuccess {
                                    color: #07a129;
                                }
                                .pkgError {
                                    color: #e61128;
                                }
                            }
                        }
                        .table-tips {
                            font-size: 14px;
                            color: #333333;
                            line-height: 44px;
                            font-weight: bold;
                            margin-right: 40px;
                            .tips-value {
                                color: $main-color;
                                font-weight: bold;
                                padding-left: 10px;
                            }
                            a {
                                padding-left: 20px;
                                color: #5672dc;
                            }
                        }
                    }
                    th {
                        line-height: 50px;
                    }
                    .item-info {
                        min-width: 260px;
                    }
                    .table-w60 {
                        width: 60px;
                    }
                    .table-w80 {
                        width: 80px;
                    }
                    .table-w100 {
                        width: 100px;
                    }
                    .table-w120 {
                        width: 120px;
                    }
                    .table-w260 {
                        width: 260px;
                    }
                    th,
                    td {
                        text-align: left;
                        min-height: 50px;
                        padding-left: 20px;
                    }
                    .channelPrice {
                        font-weight: 500;
                    }
                    .price {
                        color: $main-color;
                        font-weight: 500;
                    }
                    .text-op-padding {
                        position: relative;
                        .table-op {
                            white-space: nowrap;
                            p {
                                margin-bottom: 25px;
                                display: inline-block;
                                margin-right: 10px;
                            }
                            .splitPkg {
                                font-size: 14px;
                                display: inline-block;
                                vertical-align: top;
                                width: 56px;
                                height: 30px;
                                text-align: center;
                                line-height: 28px;
                                border: 1px solid $main-color;
                                color: $main-color;
                                border-radius: 4px;
                                margin-left: 12px;
                            }
                            .resetSupModal {
                                font-size: 14px;
                                display: inline-block;
                                vertical-align: top;
                                width: 86px;
                                height: 30px;
                                text-align: center;
                                line-height: 28px;
                                border: 1px solid #dddddd;
                                color: #666666;
                                border-radius: 4px;
                            }
                        }
                    }
                    .goods-info {
                        position: relative;
                        padding: 20px 0 20px 110px;
                        cursor: pointer;
                        img {
                            position: absolute;
                            left: 20px;
                            top: 20px;
                            border-radius: 4px;
                            border: 1px solid $border-color;
                            max-height: 80px;
                            max-width: 80px;
                        }
                        .item-title {
                            vertical-align: top;
                            font-size: 14px;
                            color: #333333;
                            height: 40px;
                            line-height: 20px;
                            overflow: hidden;
                            text-overflow: ellipsis;
                            display: -webkit-box;
                            -webkit-line-clamp: 2;
                            -webkit-box-orient: vertical;
                        }
                        p {
                            font-size: 14px;
                            color: #666666;
                            line-height: 14px;
                            padding: 20px 0;
                            line-height: 20px;
                            height: 14px;
                            overflow: hidden;
                            text-overflow: ellipsis;
                            display: -webkit-box;
                            -webkit-line-clamp: 1;
                            -webkit-box-orient: vertical;
                        }
                    }
                    .info-body {
                        tr {
                            .supplier {
                                vertical-align: top;
                                font-size: 14px;
                                color: #333;
                                height: 40px;
                                line-height: 20px;
                                overflow: hidden;
                                text-overflow: ellipsis;
                                display: -webkit-box;
                                -webkit-line-clamp: 2;
                                -webkit-box-orient: vertical;
                            }
                        }
                    }
                }
            }
            .order-logistics {
                position: absolute;
                width: 300px;
                right: 0;
                top: 0;
                background: #fff;
                border-radius: 4px;
                overflow: hidden;
                .title {
                    height: 52px;
                    padding: 12px 20px;
                    border-bottom: 1px solid $border-color;
                    span {
                        color: #333;
                        line-height: 28px;
                        font-weight: bold;
                    }
                    .btn-main {
                        margin-right: 10px;
                    }
                }
                .logistics-inner {
                    padding: 10px;
                    .logistics-tab {
                        span {
                            display: inline-block;
                            vertical-align: top;
                            height: 32px;
                            line-height: 32px;
                            padding: 0 20px;
                            border-left: 1px solid #dddddd;
                            border-top: 1px solid #dddddd;
                            margin-bottom: -1px;
                            cursor: pointer;
                            &:last-child {
                                border-right: 1px solid #dddddd;
                            }
                        }
                        .active {
                            background: #f5f8fc;
                            border-bottom: 1px solid #f5f8fc;
                        }
                    }
                    .logistics-content {
                        background: #f5f8fc;
                        border: 1px solid #dddddd;
                        padding: 0 20px 10px;
                        .content-title {
                            padding-bottom: 10px;
                            border-bottom: 1px solid #dddddd;
                            h5 {
                                color: #333333;
                                font-weight: bold;
                                font-size: 14px;
                                padding-top: 10px;
                            }
                        }
                        p {
                            color: #333333;
                            font-size: 14px;
                            line-height: 20px;
                            padding-top: 10px;
                        }
                    }
                }
            }
        }
        .dialog-form {

            padding: 0;
            .search-item {
                /*padding-left: 20px;*/
                .form-control {
                    width: 205px;
                }
            }
            h3 {
                font-size: 16px;
                line-height: 22px;
                text-align: center;
                padding: 0 20px;
            }
        }
        .border-bottom {
            padding-bottom: 15px;
        }
        .split-dialog {
            .search-item {
                padding-top: 15px;
                .goods-name {
                    line-height: 30px;
                }
            }
        }
        .reset-sup {
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
                    width: 160px;
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
                .el-radio + .el-radio {
                    margin-left: 15px;
                }
            }
        }
        .order-control-log {
            background: #fff;
            overflow:hidden;
            margin-top: 10px;
            width: 100%;
            border-radius: 4px;
            border-bottom: 1px  solid #E5E5E5;
            h2 {
                height: 42px;
                padding: 0 20px;
                line-height: 42px;
                font-size: 14px;
                color: #333;
                font-weight: 500;
                border-bottom: 1px solid #E5E5E5;
            }
        }
    }
    .set-item {
        padding-bottom: 15px;
        .address-form-title {
            display: inline-block;
            vertical-align: top;
            width: 90px;
            line-height: 30px;
            text-align: right;
            font-size: 14px;
            color: #333;
            padding-right: 20px;
            position: relative;
            i {
                position: absolute;
                left: 0;
                top: 9px;
                font-size: 18px;
                color: $main-color;
                line-height: 1;
            }
            .i {
                left: 13px;
            }
        }
        .form-control {
            display: inline-block;
            vertical-align: top;
            width: 440px;
            font-size: 14px;
        }
        .select {
            width: 140px;
            min-width: 140px;
            margin-right: 10px;
        }
        .textarea {
            width: 440px;
            height: 70px;
        }
        .save {
            font-size: 18px;
            width: 160px;
            height: 50px;
            border-radius: 2px;
            border: 0;
            outline: medium;
            cursor: pointer;
            color: #fff;
            font-weight: bold;
            background: #f7316e;
            margin-left: 90px;
        }
    }
    /deep/.dialog-form {
        padding: 0;
        .set-item {
            padding-left: 20px;
            .my-el-select {
                &.select {
                    width: 140px;
                    border: 0 !important;
                    height: 36px;
                    .el-input {
                        height: 100%;
                        input {
                            height: 100%;
                        }
                        .el-input__icon {
                            line-height: 1;
                        }
                    }
                }
            }
        }
    }
    /deep/.noraw {
        width: 100%;
        overflow: hidden;
        text-overflow:ellipsis;
        white-space: nowrap;
    }
    .leftMar{
        margin-left: 15px;
    }
    .item-titleName {
        vertical-align: top;
        font-size: 14px;
        color: #333333;
        height: 40px;
        line-height: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
    }
    .paddingBox{
        box-sizing: border-box;
        padding: 15px;
    }
    .titleBox{
        display: flex;
        align-items: center;
        /*justify-content: space-between;*/
    }
    .lookBox{
        box-sizing: border-box;
        padding: 0 10px;
    }
    .titleNum{
        box-sizing: border-box;
        padding: 0 30px;
    }
    @media screen and (min-width: 1440px)  {
        /deep/.suplly {
            width: 180px !important;
        }
    }
    @media screen and (max-width: 1440px)  {
        /deep/.table-w260 {
            width: 170px !important;
            min-width: 170px !important;
        }
    }
    .titleMar{
        margin-bottom: 10px;
        margin-top: 20px;
        font-size: 14px;
        color: #333333;
        font-weight: 600;
    }
    .packageBox{
        margin: 20px 0 10px 0;
    }
    .packageBtn{
        width:80px;
        height:40px;
        background:#ffffff;
        border-radius:2px;
        text-align: center;
        line-height: 40px;
        margin-right: 10px;
        cursor: pointer;
    }
    .packActive{
        background:rgba(225,55,108,1);
        color: #ffffff;
    }
    .packageBtnBox{
        display: flex;
    }
    .itemWidth{
        width: 450px;
    }
    .trajectoryTitle{
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding-bottom: 20px;
        margin-bottom: 20px;
        border-bottom:1px solid rgba(229,229,229,1);
    }
    .titleText{
        font-size: 14px;
        color: #333333;
        font-weight: 600;
    }
    .closeBtn{
        width:86px;
        height:30px;
        background:rgba(225,55,108,1);
        border-radius:100px;
        text-align: center;
        line-height: 30px;
        color: #ffffff;
        margin: 0 auto;
        cursor: pointer;
    }
    .cardImg{
        width: 209px;
        height: 132px;
    }
    .pathBox{
        display: flex;
        align-items: center;
        box-sizing: border-box;
        margin-top: 20px;
    }
   /deep/ .el-dialog__footer{
        padding-top: 0;
    }
    .textCol{
        font-size: 14px !important;
        color: #3A62E1 !important;
        cursor: pointer;
    }


</style>
