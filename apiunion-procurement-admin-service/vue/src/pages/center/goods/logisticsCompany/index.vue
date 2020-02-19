<template>
    <div class="center-brand">
        <div class="search-bar">
            <div class="form-wrap brandSelect">
                <div class="form-name">一般筛选：</div>
                <input type="text" class="form-control" style="width: 368px" placeholder="请输入物流公司名称" v-model="listParams.name" @keyup.enter="getList(1)">
            </div>
            <div class="btn-fill-main" @click="getList(1)">搜索</div>
            <div class="btn-main pull-right" @click="addLogisticsCompany()">添加物流公司</div>
        </div>
        <table class="table table-text">
            <thead>
            <tr>
                <th class="table-w150">物流公司</th>
                <th class="table-w120">物流Logo</th>
                <th class="table-w150">物流类型</th>
                <th class="table-w200">官网地址</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in listData" :key="index+'list'">
                <td>{{item.companyName}}</td>
                <td>
                    <el-popover
                            placement="top"
                            width="200"
                            trigger="hover" class="imgBoxCont">
                        <img style="width:100%;height:100%" :src="item.icon">
                        <img  class="ellipsis imgBox" slot="reference" :src="item.icon" />
                    </el-popover>
                </td>
                <td>
                    {{item.type==1?'国内物流':'海外物流'}}
                </td>
                <td>
                    {{item.officialUrl}}
                </td>
                <td>
                    <a class="link" @click="editLogisticsCompany(item)">编辑</a>
                    <a class="link" @click="removeItem(item)">删除</a>
                </td>
            </tr>
            </tbody>
        </table>
        <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
        <noData v-if="total===0"></noData>
        <el-dialog :title="id?'编辑物流公司':'添加物流公司'" :visible.sync="addShow" width="622px">
            <div class="dialog-form">
                <div class="form-wrap">
                    <div class="form-name">物流名称</div>
                    <input v-model="params.companyName" class="form-control"/>
                </div>

                <div class="form-wrap upload-wrap">
                    <div class="form-name">类目图标</div>
                    <img class="cateImg" v-show="params.icon" :src="params.icon" />
                    <a href="javascript:;" class="addCateImg">
                        <upload uploadType='2' v-on:uploadSuccess="uploadSuccess">
                            {{params.icon?'更改Logo':'点击上传Logo'}}
                        </upload>
                    </a>
                </div>
                <div class="form-wrap">
                    <div class="form-name">所属国家</div>
                    <select  class="form-control" v-model="params.type">
                        <option v-for="(item, index) in typeList" v-bind:value="item.id" :key="index+'id'">{{item.name}}</option>
                    </select>
                </div>
                <div class="form-wrap">
                    <div class="form-name">官网地址</div>
                    <textarea v-model="params.officialUrl" class="form-control-text"></textarea>
                </div>

            </div>
            <span slot="footer" class="dialog-footer">
          <button class="btn-org-main" @click="addLogisticsCompanyfrim">保存</button>
          <button class="btn-fill-grey-main" @click="addShow = false">取消</button>
        </span>
        </el-dialog>


    </div>
</template>
<script>
    import SelectBrand from '@/components/selectBrand/index.vue';
    import Pagination from '@/components/pagination';
    import noData from '@/components/noData';
    export default {
        data() {
            return {
                addShow:false,
                showBrand:false,
                total: -1,
                listParams: {
                    page:1,
                    limit:10,
                    name:'',
                },
                typeList:[
                    {
                        id:"1",
                        name:"国内物流"
                    },{
                        id:"2",
                        name:"海外物流"
                    }
                ],
                params:{
                    companyName:'',
                    type:"",
                    officialUrl:"",
                    icon:""
                },
                listData: [],
                id:'',
                countList:[]
            }
        },
        methods:{
            init() {
                this.getList(1);
            },
            //新增物流
            addLogisticsCompany(){
                this.id = '';
                this.params ={
                    companyName:'',
                    type:"",
                    officialUrl:"",
                    icon:""
                };
                this.addShow = true;

            },
            //删除
            removeItem(item) {
                this.$msgbox({
                    title: '',
                    message: '确认删除？',
                    showCancelButton: true,
                    confirmButtonText: '确定',
                    cancelButtonText: '取消'
                }).then(action => {
                    if(action === 'confirm') {
                        this.api.feetemplate.deleteExpressCompany({id: item.id}).then((res) => {
                            this.$message.success({message:res.message, showClose:true});
                            this.getList(1);
                        });
                    }
                });
            },
            //编辑
            editLogisticsCompany(item){
                this.addShow = true;
                this.id = item.id;
                this.params.companyName = item.companyName
                this.params.type = item.type
                this.params.officialUrl = item.officialUrl
                this.params.icon = item.icon
            },
            getList(page) {
                this.listParams.page = page;
                this.api.feetemplate.expressCompanyList(this.listParams).then((res) => {
                    this.listData = res.data.dataList;
                    this.total = res.data.total;
                });
            },
            uploadSuccess(res) {
                console.log(res.data)
                this.params.icon = res.data;
                console.log(this.params)
            },
            //保存
            addLogisticsCompanyfrim(){
                if(this.id){
                    this.api.feetemplate.updateExpressCompany({
                        ...this.params,
                        id:this.id
                    }).then(res=>{
                        this.$message.success({message:res.message, showClose:true});
                        this.getList();
                        this.addShow = false
                    })
                }else {
                    this.api.feetemplate.addExpressCompany({
                        ...this.params
                    }).then(res=>{
                        this.$message.success({message:res.message, showClose:true});
                        this.getList();
                        this.addShow = false
                    })
                }
            }
        },
        created() {
            this.init();
        },
        components: { Pagination, noData,SelectBrand}
    }
</script>
<style scoped lang='scss'>
    .center-brand{
        background: $white-color;
        .brandSelect{
            margin-right: 14px;
        }
        .table{
            .link{
                margin-right: 20px;
            }
            .ellipsis{
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;
                max-width: 100%;
            }
        }
        .search-bar{
            .form-name{
                font-weight: 600;
            }
        }
        .letter-wrap{
            display: inline-block;
            margin-right: 40px;
            user-select: none;
            .letter-item{
                display: inline-block;
                padding: 0 5px;
                cursor: pointer;
                &.active{
                    color:$org-color;
                }
            }
        }
        .dialog-form{
            padding-left: 0;
            box-sizing: border-box;
            .form-name{
                vertical-align: top;
                line-height: 30px;
                width: auto;
            }
            .form-name-title{
                color: #999999;
                font-size: 14px;
            }
            .form-control{
                width: 514px;
            }
            .form-control-text{
                width: 508px;
                border-radius: 4px;
                height: 58px;
            }
        }
    }
    .brandBox{
        display: flex;
        align-items: center;
        box-sizing: border-box;
        padding: 0px 60px 20px 60px;
        justify-content: space-between;
    }
    .brandItem{
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;


    }

    .brandImg{
        width: 52px;
        height: 52px;
    }
    .brandText{
        font-size: 14px;
        color: #999999;
        margin-top: 10px;
        white-space:nowrap;/*规定段落中的文本不进行换行*/
        overflow:hidden;/*内容会被修剪，并且其余内容是不可见的。*/
        text-overflow:ellipsis;
        max-width: 110px;
    }
    .remarkBox{
        box-sizing: border-box;
        padding: 0 10px;
        border-top: 1px solid #e5e5e5;
    }
    .remarkItem{
        box-sizing: border-box;
        padding: 20px 30px 0 30px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    .remark{
        font-size: 14px;
        color: #999999;
        margin-top: 10px;
    }
    .arrow{
        width: 52px;
        height: 13px;
        margin-top: 10px;
    }
    .form-wrap{
        display: flex;
        align-items: center;
    }
    .cateImg{
        width: 52px;
        height: 52px;
        margin-right: 15px;
    }
</style>
