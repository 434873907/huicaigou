<template>
    <div class="center-supply-account">
        <div class="list-wrap">
            <div class="search-bar">
                <div>
                    <div class="search-bar-title">供应商筛选：</div>
                    <div class="form-wrap">
                        <div class="form-name">类型</div>
                        <select class="form-control" style="margin-right: 20px" v-model="search.apiTypes" :class="{'unset-select':!search.type}">
                            <option value="">请选择供应商类型</option>
                            <option v-for="item in apiTypeList" :key="item.type" :value="item.type">{{item.name}}</option>
                        </select>
                        <div class="form-name">关键字</div>
                        <input type="text" style="width:300px;" placeholder="请输入供货商名称进行搜索" v-model="search.supplierName" class="form-control">
                        <div class="form-name">供货商状态</div>
                        <select class="form-control" style="margin-right: 20px" v-model="search.status" :class="{'unset-select':!search.id}">
                            <option value="">全部状态</option>
                            <option v-for="item in statusList" :key="item.id" :value="item.id">{{item.name}}</option>
                        </select>
                    </div>
                    <button class="btn-fill-main" style="margin-right: 30px" @click="getList(1)">搜索</button>
                </div>
                <div>
                    <button class="btn-main" @click="addSupply()">新增供应商</button>
                </div>
            </div>
            <table class="table table-text">
                <thead>
                <tr>
                     <th class="table-w100">供货商ID</th>
                    <th class="table-w120">供货商名称</th>
                    <th class="table-w100">联系电话</th>
                    <th class="table-w100">商品数量</th>
                    <th class="table-w120">供货商类型</th>
                    <th class="table-w180">发货方式</th>
                    <th class="table-w120">供货商状态</th>
                    <th class="table-w120">创建时间</th>
                    <th >操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in listData" :key="item.id">
                    <td>{{item.id}}</td>
                    <td>
                        <el-popover
                                placement="top"
                                width="260"
                                trigger="hover">
                            <span>{{item.supplierName}}</span>
                            <span class="ellipsis" slot="reference">{{item.supplierName}}</span>
                        </el-popover>
                    </td>
                    <td>{{item.phone}}</td>
                    <td>{{item.itemNum}}</td>
                    <td>{{item.apiTypeName}}</td>
                    <td>
                        <el-popover
                                placement="top"
                                width="260"
                                trigger="hover">
                            <span>{{item.channelTypeNames || '全部发货方式'}}</span>
                            <span class="ellipsis" slot="reference">{{item.channelTypeNames || '全部发货方式'}}</span>
                        </el-popover>
                    </td>
                    <td>{{item.status ? '启' : '禁'}}用</td>
                    <td>{{item.createTime}}</td>
                    <td>
                        <a class="link" @click="lookItem(item)">查看</a>
                    </td>
                </tr>
                </tbody>
            </table>
            <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
            <noData v-if="total===0"></noData>
        </div>
    </div>
</template>
<script>
    import Pagination from '@/components/pagination';
    import noData from '@/components/noData';
    export default {
        data() {
            return {
                modalShow:false,
                search:{
                    supplierName:"",
                    apiTypes:"",
                    status:''
                },
                levelList:[],
                total: -1,
                listData: [],
                isEdit: false,
                channelList: [],
                listParams: {
                    page: 1,
                    limit: 10
                },
                statusList:[
                    {
                        id:'1',
                        name:'启用'
                    },{
                        id:'0',
                        name:'禁用'
                    }
                ],
                apiTypeList:[{name:'不同步',type:0}],

            }
        },
        created() {
             this.init();
        },
        methods:{
            init() {
                this.getList(1);
                this.api.supplier.apiType().then(res=>{
                    this.apiTypeList = this.apiTypeList.concat(res.data);
                });

            },
            //查看供应商详情
            lookItem(item){
                this.$router.push({
                    path:'addSupplier',
                    query:{
                        index:2,
                        id:item.id
                    }
                })
            },
            //列表数据获取
            getList(page) {
                this.listParams.page = page;
                this.listParams = {
                    ...this.listParams,
                    ...this.search
                };
                this.api.supplier.list(this.listParams).then((res) => {
                    this.listData = res.data.dataList;
                    this.total = res.data.total;
                });
            },
            //新增供应商按钮初始化数据
            addSupply() {
                this.$router.push({
                    path: `/center/supplier/addSupplier`,
                })
            },


            //删除供货商
            delItem(item) {
                this.$msgbox({
                    title: '',
                    message: '确认删除？',
                    showCancelButton: true,
                    confirmButtonText: '确定',
                    cancelButtonText: '取消'
                }).then(action => {
                    if(action === 'confirm') {
                        this.api.supplier.del({id: item.id}).then((res) => {
                            this.$message.success({message:res.message, showClose:true});
                            this.getList(1);
                        });
                    }
                });
            },
        },
        components: {
            Pagination,
            noData
        }
    }
</script>
<style lang='scss' scoped>
    /deep/.search-bar {
        padding-bottom: 0;
        display: flex;
        justify-content: space-between;
    }
    .center-supply-account{
        .table{
            .link{
                margin-right: 20px;
            }
        }
        .form-wrap {
            margin-right: 0;
            .form-name {
                margin-bottom: 10px;
            }
            .form-control {
                margin-bottom: 10px;
            }
        }
        .btn-fill-main {
            margin-right: 20px;
        }
        .btn-main {
            margin-bottom: 10px;
        }
        .dialog-form{
            .form-name{
                width: 60px;
                text-align: right;
                vertical-align: top;
                line-height: 30px;
            }
            .form-control{
                width: 320px;
            }
            .channel-control{
                display: inline-block;
                vertical-align: top;
                width: 320px;
                .el-checkbox{
                    line-height: 30px;
                    margin-right: 15px;
                }
                .el-checkbox+.el-checkbox{
                    margin-left: 0;
                }
            }
        }
        .form-gorup-content{
            margin-bottom: 15px;
            // border-bottom:1px solid rgba(229,229,229,1);
            .rate-box {
                border: 1px solid #dcdfe6;
                height: 28px;
                box-sizing: border-box;
                border-radius: 4px;
                line-height: 28px;
                vertical-align: middle;
                margin-top: 6px;
                .el-col {
                    height: 100%;
                    &:last-child {
                        text-align: center;
                    }
                    input {
                        background: transparent;
                        width: 100%;
                        height: 100%;
                        vertical-align: top;
                        padding-left: 15px;
                    }
                }
            }
        }
        .form-group-name{
            margin-bottom: 15px;
            padding-left: 8px;
            font-size: 14px;
            font-weight: 600;
            position: relative;
            &::before{
                content: " ";
                position: absolute;
                height: 13px;
                width: 4px;
                left: 0;
                background: #F06941;
            }
        }
        .el-form-item{
            margin-bottom: 13px;
            .el-form-item__error {
                padding-top: 0;
            }
            .el-form-item__content{
                // margin-left:167px!important;
                .btn-org-main{
                    background:#E1376C;
                    margin-left:63px;
                }
                .el-input--mini{
                    width: 400px;
                }
                .btn-org-main{
                    margin-right:20px;
                }
                .select-multiple{
                    margin-bottom: 10px;
                }
                .send-goods-msg {
                    position: absolute;
                    padding-left: 15px;
                    height: 40px;
                    line-height: 40px;
                    left: 0;
                    top: 0;
                    font-size:12px;
                    color: #606266;
                }
                .el-select {
                    width: 400px;
                }
            }
            .appId,.itemInfo{
                display: inline-block;
            }
            .itemInfo{
                margin-left: 10px;
            }
        }
        .no-border{
            border:0;
        }
    }
    .remark-textarea{
        height: 500px;
    }
    /deep/ .el-input{
        width: auto;
    }
    /deep/.select-multiple {
        &.special {
            .el-input {
                input {
                    height: 28px !important;
                }
            }
        }
    }
</style>
