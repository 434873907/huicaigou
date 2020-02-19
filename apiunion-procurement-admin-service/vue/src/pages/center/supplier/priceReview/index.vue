<template>
    <div class="center-commodity">
        <div class="tab-bar">
            <div class="tab-item" :class="{'active':listParams.status===0}" @click="changeStatus('')">全部商品</div>
            <div class="tab-item" :class="{'active':listParams.status===1}" @click="changeStatus(1)">已上架</div>
            <div class="tab-item" :class="{'active':listParams.status===2}" @click="changeStatus(2)">已下架</div>
        </div>
        <div class="type-wrap" v-show="firstCateList.length>0">
            <div class="type-content-wrap">
                <div class="type-title">所属类目：</div>
                <div class="type-content border-bottom">
                    <div class="type-item" v-for="(item, index) in firstCateList" :key="index+'first'" :class="{'active':selectedCate.first===item.cateId}">
                        <a href="javascript:;" class="ellipsis" :class="{'pr40':item.count>99}" @click="selectCate(1, item)">
                            {{item.cateName}}({{item.count}})
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
                <div class="search-bar-title">其它筛选：</div>
                <div class="form-wrap">
                    <div class="form-name"></div>
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
                    <div class="form-name marLeft">发货方式</div>
                    <select class="form-control" placeholder="选择商品发货方式" v-model="listParams.supplierId" style="margin-right: 20px" >
                        <option value="">全部</option>
                        <option v-for="item in supplyList" :key="item.id" :value="item.id">{{item.supplierName}}</option>
                    </select>
                    <div class="form-wrap form-wrap-last">
                        <div class="form-name">提交时间</div>
                        <el-date-picker
                                class="timepicker-control"
                                type="datetime"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                placeholder="请选择"
                                v-model="listParams.begainTime"
                        ></el-date-picker>--
                        <el-date-picker
                                class="timepicker-control"
                                type="datetime"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                placeholder="请选择"
                                v-model="listParams.endTime"
                        ></el-date-picker>
                    </div>
                    <button class="btn-fill-main" @click="getList(1)">搜索</button>
                    <button class="btn-fill-grey-main" @click="clearSearch">清空</button>
                </div>
            </div>
            <div class="control-wrap" style="border-bottom: 1px solid #e5e5e5">
                <div class="control-item">
                    <div class="btn-fill-org-main marRight10"  @click="changeTypeList(1)">批量通过</div>
                    <div class="btn-fill-org-main"  @click="changeTypeList(2)">批量驳回</div>
                </div>
            </div>
            <table class="table"  ref="table">
                <thead>
                <tr>
                    <th class="dot" :class="{'active':checkAll}" @click="selectAll()" ref="goCheckAll"></th>
                    <th class="table-w80">主图</th>
                    <th class="table-w200">商品名称</th>
                    <th class="table-w200">供价变化</th>
                    <th class="table-w120" v-show="listParams.status==0">调价后利润</th>
                    <th class="table-w100">发货方式</th>
                    <th class="table-w140" >供货商</th>
                    <th class="table-w160">更新时间</th>
                    <th v-show="listParams.status==0">操作</th>
                    <th v-show="listParams.status!=0">审核结果</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item, index) in tableData" :key="index+'table'" :class="{'selectedTr':item.checked}">
                    <td class="dot" :class="{'active':item.checked}" @click="selectItem(item)"></td>
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
                        <div>
                            <div>
                                {{item.itemName}}
                            </div>
                            <div>
                                {{item.skuDesc}}
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="printBox">
                            <div>{{item.priceCurrent}}</div>
                            <div class="printArrowbox">
                                <div class="addPrint">涨{{item.profitRate}}</div>
                                <img src="../../../../assets/printArrow.png" alt="">
                            </div>
                            <div>{{item.priceApply}}</div>
                        </div>
                    </td>
                    <td v-show="listParams.status==0">
                       <div>
                           调价后利润
                       </div>
                    </td>
                    <td>发货方式</td>
                    <td>
                       {{item.supplierName}}
                    </td>
                    <td >{{item.modifyTime}} </td>
                    <td v-show="listParams.status==0">
                      <span class="linkWrapEdit">
                        <a href="javascript:;" class="link base-margin-right" @click="passBtn(1,item)">通过</a>
                        <a href="javascript:;" class="link base-margin-right" @click="refuseBtn(2,item)">驳回</a>
                      </span>
                    </td>
                    <td v-show="listParams.status!=0">
                        <span v-show="item.status==1">审核通过</span>
                        <span v-show="item.status==2">已驳回</span>
                    </td>
                </tr>
                </tbody>
            </table>
            <pagination ref="pagination" v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getGoodsList(listParams.page)" />
            <noData v-if="total===0"></noData>
        </div>
        <el-dialog title="驳回涨价申请" :visible.sync="showRefuse" width="540px">
            <div class="dialog-form">
                <div class="form-wrap">
                    <textarea placeholder="请输入驳回原因，最多50字" v-model="inputVal" class="form-control-text"></textarea>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
          <button class="btn-org-main" @click="refuseConfrim(2)">保存</button>
          <button class="btn-fill-grey-main" @click="addShow = false">取消</button>
        </span>
        </el-dialog>
        <el-dialog title="温馨提示" :visible.sync="showHint" width="772px">
            <div class="dialog-form">
                <div class="titlebox">
                    <div>
                        以下商品因供价上涨导致利润率低于额定利润率(4%)，销售端商品已变为待审核状态。
                    </div>
                    <div class="marTop10" >
                        您可前往商品页面进行修改售价等恢复商品的销售
                    </div>
                </div>
                <div>
                    <table class="table"  ref="table">
                        <thead>
                        <tr>
                            <th class="table-w70">主图</th>
                            <th class="table-w180">商品名称</th>
                            <th class="table-w80">当前售价</th>
                            <th class="table-w100">当前企业价</th>
                            <th class="table-w80">最新供价</th>
                            <th class="table-w60" >利润率</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(item, index) in lowList" :key="index+'table'">
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
                                <div>
                                    <div>
                                        {{item.itemName}}
                                    </div>
                                    <div>
                                        {{item.skuDesc}}
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div>
                                   {{item.salePrice}}
                                </div>
                            </td>
                            <td>{{item.enterprisePrice}}</td>
                            <td>
                                {{item.priceApply}}
                            </td>
                            <td >利润率 </td>
                            <td>
                                  <span class="linkWrapEdit">
                                    <a href="javascript:;" class="link base-margin-right" @click="editItem(item)">查看商品</a>
                                  </span>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
          <button class="btn-org-main" @click="addShow = showHint">知道了</button>
        </span>
        </el-dialog>

    </div>
</template>
<script>
    import Pagination from '@/components/pagination';
    import noData from '@/components/noData';
    import selSupplierList from '@/components/selSupplyPrint'
    export default {
        data() {
            return {
                inputVal:"",
                supplyList:[],
                showRefuse:false,
                showHint:false,
                firstCateList: [],
                selectedCate: {
                    first:'',
                },
                checkAll: false,
                total: -1,
                listParams: {
                    page: 1,
                    limit: 20,
                    status: 0,
                    keyword: '',
                    supplierIds:'',
                    endTime:"",
                    begainTime:""
                },
                tableData: [],
                statusList:[ //状态列表
                    {id:1,name:'已上架'},
                    {id:2,name:'已下架'}
                ],
                inventoryStatus:[ //供应商销售关闭类型
                    {name:'待审核',id:0},
                    {id:1,name:'审核已通过'},
                    {id:2,name:'审核已驳回'},
                ],
                userConfig:{},
                systemInfo:{},
                searchParams:{},
                itemObj:{},
                selItem:[],
                lowList:[]
            }
        },
        created() {
            this.init();
            this.userConfig = JSON.parse(localStorage.userConfig);
            this.systemInfo = JSON.parse(localStorage.systemInfo);
        },
        methods: {
            //驳回
            refuseBtn(status,item){
                this.showRefuse = true;
                this.itemObj = item;
            },
            //确认驳回
            refuseConfrim(status){
                this.passBtn(status,this.itemObj);
            },
            //通过
            passBtn(status,item,num){
                if(status==1){
                    this.$msgbox({
                        title: '',
                        message: '确认审核通过么？',
                        showCancelButton: true,
                        confirmButtonText: '确定',
                        cancelButtonText: '取消'
                    }).then(action => {
                        if(action === 'confirm') {
                            let approveParams = []
                            if(num){
                                approveParams = this.selIdArr
                            }else {
                                approveParams = [{
                                    supplierId:item.supplierId,
                                    approveId:item.id
                                }]
                            }
                            this.api.supplier.approvePrice({
                                desc:status==2?this.inputVal:"",
                                status:status,
                                approveParams:approveParams
                            }).then(res=>{
                                this.$message.success({message:res.message, showClose:true});
                                this.getList(1);
                                this.lowList = this.selItem.filter((item)=>{
                                    return item.needRemind==1
                                });
                                if(this.lowList.length>0){
                                    this.showHint = true
                                }
                            })
                        }
                    });
                }else {
                    let approveParams = []
                    if(num){
                        approveParams = this.selIdArr
                    }else {
                        approveParams = [{
                            supplierId:item.supplierId,
                            approveId:item.id
                        }]
                    }
                    this.api.supplier.approvePrice({
                        desc:status==2?this.inputVal:"",
                        status:status,
                        approveParams:approveParams
                    }).then(res=>{
                        this.showRefuse = false;
                        this.$message.success({message:res.message, showClose:true});
                        this.getList(1);
                    })
                }
            },
            editItem(item) {
                let path = `/center/goods/commodity/detail/${item.id}/${this.listParams.status}`
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
                        this.selIdArr.push({
                            supplierId:item.supplierId,
                            approveId:item.id
                        });
                        this.selItem.push(item);
                    }
                });
            },
            selectAll() {
                this.selIdArr = [];
                this.checkAll = !this.checkAll;
                this.tableData.forEach((item) => {
                    if(this.checkAll) {
                        item.checked = true;
                        this.selIdArr.push({
                            supplierId:item.supplierId,
                            approveId:item.id
                        })
                        this.selItem.push(item);
                    } else {
                        item.checked = false;
                        this.selIdArr = [];
                    }
                });
            },
            //批量通过
            changeTypeList(num){
                if(this.selIdArr.length==0){
                    this.$message.info('请先选择')
                    return false
                };
                if(this.num==1){
                    this.passBtn(1,{},num)
                }else if(this.num==2){
                    this.passBtn(2,{},num)
                }
            },
            //清空
            clearSearch(){

            },
            dataCatgory(data){
                this.dataMass.cateName1=data.firstName;
                this.dataMass.cateId1=data.first;

            },
            init(val) {
                this.checkAll = false
                // 获取一级类目列表
                this.getCate(0, '');
                // 初始化商品列表
                let initPage = val?val:1;
                this.getGoodsList(initPage);
            },

            getCate() {
                this.api.supplier.queryCountByCategory({status:this.listParams.status}).then((res) => {
                        this.firstCateList = res.data;
                });
            },

            selectCate(type, item) {
                if(this.selectedCate.first !== item.cateId){
                    this.selectedCate.first = item.cateId;
                } else {
                    this.selectedCate.first = '';
                }
                this.getGoodsList(1);
            },

            editItem(item) {
                this.$router.push({
                    path:'goodsDetail',
                    query:{
                        itemId:item.id
                    }
                })
            },
            //搜索
            search(page){
                console.log(this.$refs.selSupplier)
                this.listParams.supplierIds = this.$refs.selSupplier.idsList;
                this.getGoodsList(page).then(params=>{
                    this.getCate(0, '',params)
                })

            },
            getGoodsList(page) {
                return new Promise((resolve,reject)=>{
                    this.listParams.page = page;
                    let params = this.copy(this.listParams);
                    params.cateId =  this.selectedCate.first || '';
                        this.searchParams = params;
                        this.api.supplier.queryPriceApproveList(params).then((res) => {
                            this.tableData = res.data.dataList;
                            this.total = res.data.total;
                            resolve(params)
                        });
                });

            },

            changeStatus(status) {
                this.listParams.status = status;
                this.getGoodsList(1).then((params)=>{
                    this.getCate(0, '',params)
                });
            },
        },
        filters:{
        },

        components: {
            'Pagination': Pagination,
            'noData': noData,
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
    /deep/ .el-dialog__body{
        padding: 20px;
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
    .printBox{
        display: flex;
        align-items: center;
    }
    .printArrowbox{
        position: relative;
        box-sizing: border-box;
        padding: 0 2px;
        margin: 0 auto;
        img{
            width: 48px;
            height: 7px;
        }
    }
    .addPrint{
        font-size: 12px;
        color: #E1376C;
        position: absolute;
        bottom: 10px;
    }
    .addCol{
        color: #20B73F;
    }
    .jianCol{
        color: #E61128;
    }
    .form-control-text{
        width: 500px;
        height: 150px;
    }
    .dialog-form{
        padding-left: 0 !important;
    }
    .titlebox{
        border-bottom: 1px solid #e5e5e5;
        box-sizing: border-box;
        padding-bottom: 20px;
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
