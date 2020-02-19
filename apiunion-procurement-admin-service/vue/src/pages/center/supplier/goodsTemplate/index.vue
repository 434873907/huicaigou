<template>
    <div class="center-commodity" style="min-width: 1250px">
        <div class="tab-bar">
            <div class="tab-item" :class="{'active':listParams.status===0}" @click="changeStatus(0)">全部商品</div>
            <div class="tab-item" :class="{'active':listParams.status===2}" @click="changeStatus(2)">已发布</div>
            <div class="tab-item" :class="{'active':listParams.status===1}" @click="changeStatus(1)">待发布</div>
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
        </div>
        <div class="list-wrap">
            <div class="search-bar">
                <div class="search-bar-title">一般筛选：</div>
                <div class="form-wrap">
                    <input type="text" class="form-control" v-model="listParams.keyword" @keyup.enter="search(1)">
                </div>
                <div class="form-wrap marTop10">
                    <div class="form-name">修改时间</div>
                    <el-date-picker
                            class="timepicker-control"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="请选择"
                            v-model="listParams.modifyTimeBegin"
                    ></el-date-picker>  -
                    <el-date-picker
                            class="timepicker-control"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="请选择"
                            v-model="listParams.modifyTimeEnd"
                    ></el-date-picker>
                </div>
                <button class="btn-fill-main" @click="search(1)">搜索</button>
                <button class="btn-fill-grey-main" @click="addGoods">新增商品</button>
            </div>
            <table class="table"  ref="table">
                <thead>
                <tr>
                    <th class="table-w60">商品ID</th>
                    <th class="table-w80">主图</th>
                    <th class="table-w200">商品名称</th>
                    <th class="table-w120">品牌</th>
                    <th class="table-w200">规格</th>
                    <th class="table-w100">价格</th>
                    <th class="table-w140" >所属类目</th>
                    <th class="table-w80">使用商家</th>
                    <th class="table-w160" >商品状态</th>
                    <th class="table-w160" >修改时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item, index) in tableData" :key="index+'table'" :class="{'selectedTr':item.checked}">
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
                    <td>
                        {{item.cateName}}
                    </td>
                    <td>{{item.supplierNum}}</td>
                    <td> {{item.status==1?'待发布':'已发布'}} </td>
                    <td> {{item.modifyTime}} </td>
                    <td>
                      <span class="linkWrapEdit">
                        <a href="javascript:;" class="link base-margin-right" @click="editItem(item)">编辑</a>
                        <a  href="javascript:;" class="link base-margin-right" v-show="!(item.status==1&&item.supplierNum>0)" @click="deleItem(item)">删除</a>
                      </span>
                    </td>
                </tr>
                </tbody>
            </table>
            <pagination ref="pagination" v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getGoodsList(listParams.page)" />
            <noData v-if="total===0"></noData>
        </div>
    </div>
</template>
<script>
    import selectBrand from '@/components/selectBrand/index.vue';
    import selectShop from '@/components/selectShop/index.vue';
    import Pagination from '@/components/pagination';
    import noData from '@/components/noData';
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
                exceptItemId:[],
                listParams: {
                    page: 1,
                    limit: 20,
                    status: 0,
                    keyword: '',
                    modifyTimeBegin:'',
                    modifyTimeEnd:'',
                },
                tableData: [],
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
            //去编辑
            editItem(item){
                this.$router.push({
                    path:'addGoodsTemplate',
                    query:{
                        templateItemId:item.id
                    }
                })
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
            //新增商品
            addGoods(){
              this.$router.push({
                  path:'addGoodsTemplate'
              })
            },
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
                // 获取一级类目列表
                 this.getCate(0, '');
                // 初始化商品列表
                let initPage = val?val:1;
                this.getGoodsList(initPage);

            },
            deleItem(item){
                this.$msgbox({
                    title: '',
                    message: '你确定要删除该商品吗？删除后无法恢复该商品',
                    showCancelButton: true,
                    confirmButtonText: '确定',
                    cancelButtonText: '取消'
                }).then(action => {
                    if(action === 'confirm') {
                        this.api.template.deleteTemplateItem({templateItemId:item.id}).then(res=>{
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

            eidt(item){
                localStorage.setItem('groupGoodsInfo',JSON.stringify(item))
                let path = `/center/goods/commodity/detail/${item.itemId}/${this.listParams.status}`;
                window.open(path, '_blank');
            },

            getCate(level, id,params) {
                this.api.template.queryTemplateCategoryCount({...params}).then((res) => {
                    this.firstCateList = res.data;
                });
            },

            selectCate(type, item) {
                    if(this.selectedCate.first !== item.id){
                        this.selectedCate.first = item.id;
                    } else {
                        this.selectedCate.first = '';
                    }

                this.getGoodsList(1);
            },
            goGoodsDetail(item){
                localStorage.setItem('groupGoodsInfo',JSON.stringify(item))
                let path = ''
                path = `commodity/detail/${item.id}/${this.listParams.status}`
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
                this.getGoodsList(page).then(params=>{
                    this.getCate(0, '',params)
                })

            },
            getGoodsList(page) {
                return new Promise((resolve,reject)=>{
                    this.listParams.page = page;
                    this.listParams.cateId = this.selectedCate.first;
                        this.api.template.queryTemplateTtemList(this.listParams).then((res) => {
                            this.tableData = res.data.dataList;
                            this.total = res.data.total;
                            resolve(this.listParams)
                        });
                });

            },

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
            'noData': noData
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

