<template>
    <div>
        <div class="detailBox">
            <div class="form-group-name">供货商信息：</div>
            <div class="boxBack">
                <el-row :gutter="20" class="marBottom">
                    <el-col :span="12">
                        <div class="grid-content bg-purple">
                            创建时间：{{detailObj.createTime}}
                        </div>
                    </el-col>
                    <el-col :span="12">
                        <div class="grid-content bg-purple">
                            供货商ID：{{detailObj.id}}
                        </div>
                    </el-col>
                </el-row>
                <el-row :gutter="20" class="marBottom">
                    <el-col :span="12">
                        <div class="grid-content bg-purple">
                            供货商名称：{{detailObj.supplierName}}
                        </div>
                    </el-col>
                    <el-col :span="12" v-if="detailObj.apiType">
                        <div class="grid-content bg-purple">
                            联系电话：{{detailObj.phone}}
                        </div>
                    </el-col>
                </el-row>
                <el-row :gutter="20" class="marBottom">
                    <el-col :span="12">
                        <div class="grid-content bg-purple">
                            供货商类型：{{detailObj.apiTypeName}}
                        </div>
                    </el-col>
                    <el-col :span="12" v-if="detailObj.apiType">
                        <div class="grid-content bg-purple">
                            联系人：{{detailObj.contacts}}
                        </div>
                    </el-col>
                </el-row>
                <el-row :gutter="20" class="marBottom">
                    <el-col :span="12" v-if="detailObj.sourceItemAttr">
                        <div class="grid-content bg-purple">
                            发货方式：{{detailObj.sourceItemAttr.channelNames}}
                        </div>
                    </el-col>
                    <el-col :span="12">
                        <div class="grid-content bg-purple">
                            供货商状态：{{detailObj.status==1?'启用':'禁用'}}
                        </div>
                    </el-col>
                </el-row>
            </div>
            <div v-if="detailObj.apiType">
                <div class="form-group-name">账户信息：</div>
                <div class="boxBack">
                    <el-row :gutter="20" class="marBottom" v-show="detailObj.apiType==1">
                        <el-col :span="24">
                            <div class="">
                                登录账号：{{detailObj.account}}<a class="clickText marLeft" type="text" @click="resetPassword">重置密码</a>
                            </div>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20" class="marBottom" >
                        <el-col :span="24">
                            <div class="">
                                AppID：{{detailObj.apiAuth.appId}}<a v-show="detailObj.apiType==1" class="clickText marLeft" @click="lookSecret">查看AppSecret</a>
                            </div>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20" class="marBottom" v-show="detailObj.apiType!=1">
                        <el-col :span="24">
                            <div class="">
                                AppSecret：{{detailObj.apiAuth.secretKey}}
                            </div>
                        </el-col>
                    </el-row>
                </div>

            </div>

            <div v-if="detailObj.apiType">
                <div class="form-group-name">商品销售信息：</div>
                <div class="boxBack">
                    <el-row :gutter="20" class="marBottom">
                        <el-col :span="12">
                            <div class="grid-content bg-purple">
                                目标销售店铺：{{detailObj.targetItemAttr.shopName}}
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div class="grid-content bg-purple">
                                默认运费模板：{{detailObj.targetItemAttr.expressFeeTemplateName}}
                            </div>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20" class="marBottom">
                        <el-col :span="12">
                            <div class="grid-content bg-purple">
                                价格策略类型：{{detailObj.priceStrategy.type}}
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div class="grid-content bg-purple">
                                售价上浮比例：{{detailObj.priceStrategy.priceUpPercent}}
                            </div>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20" class="marBottom">
                        <el-col :span="25">
                            <div class="grid-content bg-purple">
                                原价上浮比例：{{detailObj.priceStrategy.originalPriceUpPercent}}
                            </div>
                        </el-col>
                    </el-row>
                </div>

            </div>

        </div>
        <el-dialog
                title="查看"
                :visible.sync="dialogVisible"
                width="30%"
               >
            <span v-if="detailObj.apiAuth">  AppSecret：{{detailObj.apiAuth.secretKey}}</span>
            <span slot="footer" class="dialog-footer">
           <span class="btnStyle" @click="dialogVisible = false">关闭</span>
  </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "SupplierDetail",
        props:['detailObj'],
        data(){
            return{
                showSecretKey:false,
                dialogVisible:false
            }
        },
        methods:{
            lookSecret(){
              // this.showSecretKey = true
              this.dialogVisible = true

            },
            resetPassword(){
                this.$msgbox({
                    title: '',
                    message: '确认重置密码么？',
                    showCancelButton: true,
                    confirmButtonText: '确定',
                    cancelButtonText: '取消'
                }).then(action => {
                    if(action === 'confirm') {
                        this.api.supplier.resetSupplierPassword({
                            supplierId:this.detailObj.id
                        }).then(res=>{
                            this.$message.success({message:res.message, showClose:true});
                        })
                    }
                });

            },
        }
    }
</script>

<style lang="scss" scoped>
    .marBottom{
        margin-bottom: 20px;
    }
    .form-group-name{
        margin-bottom: 10px;
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
    .boxBack{
        background: #ffffff;
        box-sizing: border-box;
        padding: 20px 20px 1px 20px;
        margin-bottom: 20px;
    }
    .marLeft{
        margin-left: 20px;
    }
</style>
