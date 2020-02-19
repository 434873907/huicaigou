<template>
    <div class="contain">
        <SupplierDetail v-if="index==2" :detailObj="detailObj"></SupplierDetail>
       <div v-else class="dialog-form">
                <el-form ref="form"  :model="form" label-width="110px">
                    <div class="form-gorup-content">
                        <div class="form-group-name">基本信息：</div>
                        <div class="boxBack">
                            <el-form-item label="供货商名称"  prop="supplierName">
                                <el-input :disabled="index==1&&form.apiType" v-model="form.supplierName" size="mini"></el-input>
                                <span class="marLeft fontCol">（限制25字符）</span>
                            </el-form-item>
                            <el-form-item label="供货商类型" v-if="apiTypeList.length>1">
                                <el-select @change="changeApiType" :disabled="index==1" v-model="form.apiType" size="mini" placeholder="请选择" value-key="type">
                                    <el-option :label="item.name" :value="item.type" v-for="item in apiTypeList" :key="item.type+'type'"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item v-show="form.apiType" label="联系人"  prop="supplierName">
                                <el-input v-model="form.contacts" size="mini"></el-input>
                                <span class="marLeft fontCol">（限制10字符）</span>
                            </el-form-item>
                            <el-form-item v-show="form.apiType" label="联系电话"  prop="supplierName">
                                <el-input v-model="form.phone" size="mini"></el-input>
                            </el-form-item>
                            <el-form-item label="供货商状态" prop="status">
                                <el-radio-group v-model="form.status">
                                    <el-radio :label="1">启用</el-radio>
                                    <el-radio :label="0">禁用</el-radio>
                                </el-radio-group>
                            </el-form-item>
                            <el-form-item label="发货方式" prop="sourceItemAttr.channelTypes">
                                <el-select v-model="form.sourceItemAttr.channelTypes" class="select-multiple" multiple size="mini" placeholder="" :class="!form.sourceItemAttr.channelTypes || form.sourceItemAttr.channelTypes.length < 2 ? 'special' : ''" ref="selectss">
                                    <el-option v-for="item in channelList"
                                               :key="item.channelType"
                                               :label="item.channelTypeName"
                                               :value="item.channelType">
                                    </el-option>
                                </el-select>
                                <span class="marLeft fontCol">（支持选择多个发货方式）</span>
                            </el-form-item>
                        </div>
                    </div>
                    <div v-if="form.apiType">
                        <div class="form-gorup-content" v-show="form.apiType==1">
                            <div class="form-group-name">账户信息：</div>
                            <div class="boxBack">
                                <el-form-item label="登录账号">
                                    <el-input :disabled="index==1" v-model="form.account" size="mini" class="appId"></el-input>
                                    <span class="marLeft fontCol">（由不少于6位的字母和数字组成，首位必须为字母，限制15字符）</span>
                                </el-form-item>
                                <el-form-item label="登录密码">
                                    <el-input v-model="form.password" size="mini"></el-input>
                                    <span class="marLeft fontCol">（由不少于6位的字母和数字组成，限制15字符）</span>
                                </el-form-item>
                            </div>
                        </div>
                        <div class="form-gorup-content" v-show="form.apiType!=1">
                            <div class="form-group-name">授权信息：</div>
                            <div class="boxBack">
                                <el-form-item label="App Id">
                                    <el-input v-model="form.apiAuth.appId" size="mini" class="appId"></el-input>
                                </el-form-item>
                                <el-form-item label="App Secret">
                                    <el-input v-model="form.apiAuth.secretKey" size="mini"></el-input>
                                </el-form-item>
                            </div>

                        </div>
                        <div class="form-gorup-content no-border">
                            <div class="form-group-name">商品属性：</div>
                            <div class="boxBack">
                                <el-form-item label="目标销售店铺">
                                    <el-select v-model="form.targetItemAttr.shopId" size="mini" placeholder="请选择目标店铺">
                                        <el-option :label="item.name" :value="item.id" v-for="item in shopList" :key="item.id"></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="默认运费模板">
                                    <el-select v-model="form.targetItemAttr.expressFeeTemplateId" size="mini" placeholder="请选择运费模板">
                                        <el-option :label="item.templateName" :value="item.id" v-for="item in feetemplate" :key="item.id"></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="价格策略类型">
                                    <el-select v-model="form.priceStrategy.type" size="mini" placeholder="请选择价格策略类型">
                                        <el-option label="百分比上浮(%)" :value="1"></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="售价上浮比例">
                                    <div style="display: flex">
                                        <el-row style="width:400px;" class="rate-box">
                                            <el-col :span="22"><input type="number" v-model="form.priceStrategy.priceUpPercent"></el-col>
                                            <el-col :span="2">%</el-col>
                                        </el-row>
                                    </div>
                                </el-form-item>
                                <el-form-item label="原价上浮比例">
                                    <div style="display: flex">
                                        <el-row style="width:400px;" class="rate-box">
                                            <el-col :span="22">
                                                <input type="number" v-model="form.priceStrategy.originalPriceUpPercent">
                                            </el-col>
                                            <el-col :span="2">%</el-col>
                                        </el-row>
                                    </div>

                                </el-form-item>
                            </div>

                        </div>
                    </div>
                    <div class="btnBox">
                            <div class="btn-org-main" type="primary" @click="saveSupplier('form')">保存</div>
                            <div class="btn-fill-grey-main marLeft" @click="cancel">取消</div>
                    </div>

                </el-form>
            </div>
        <div>
            <div v-show="index==2">
                <div class="btn-org-main" type="primary" @click="editItem">编辑</div>
                <div class="btn-fill-grey-main marLeft" @click="cancel">返回</div>
            </div>

        </div>
    </div>
</template>

<script>
    import SupplierDetail from './component/SupplierDetail'
    export default {
        name: "index",
        components:{
            SupplierDetail
        },
        data(){
            return {
                detailObj:{},
                form:{
                    supplierName:'',
                    contacts:'',
                    phone:'',
                    account:'',
                    password:'',
                    apiType:0,
                    apiAuth:{
                        appId:'',
                        secretKey:''
                    },
                    targetItemAttr:{
                        shopId:'',
                        expressFeeTemplateId:''
                    },
                    sourceItemAttr:{
                        channelTypes:[]
                    },
                    priceStrategy:{
                        type:1,
                        priceUpPercent:'',
                        originalPriceUpPercent:''
                    },
                    status:1
                },
                rules:{
                    supplierName: [{ required: true, message: '请输入供货商名称', trigger: 'blur' }]
                },
                feetemplate:[],
                shopList:[],
                channelList:[],
                apiTypeList:[{name:'不同步',type:0}],
                dataTemp:[],//全选 的存储数据
                index:'',
                id:""
            }
        },
        mounted(){
            let params = this.$route.query;
            this.index = params.index?params.index:this.index;
            if(params.index==2){
                this.id = params.id;
                this.getDetail();
            }
            this.init();


        },
        methods:{
            init(){
                this.api.goods.channel().then((res) => {
                    this.channelList = res.data;
                    this.channelList.forEach(item => {
                        this.$set(item, 'checked', true);
                        this.dataTemp.push(item.channelType) //为全选做准备
                    });
                });
                this.api.supplier.apiType().then(res=>{
                    this.apiTypeList = this.apiTypeList.concat(res.data);
                });
                this.api.feetemplate.list({limit:9999}).then((res) => {
                    this.feetemplate = res.data.dataList;
                });
                this.api.store.list({
                    ...this.listParams,
                    status:1
                }).then((res) => {
                    this.shopList = res.data.dataList;
                });
            },
            //获取详情
            getDetail(){
              this.api.supplier.detail({
                  id:this.id
              }).then(res=>{
                  this.detailObj =res.data;
                  this.form = Object.assign({},this.form,res.data);
                  this.form.sourceItemAttr = {
                      channelTypes:[]
                  };
                  if(res.data.channelTypes){
                      res.data.channelTypes.forEach(data => {
                          this.form.sourceItemAttr.channelTypes.push(data.channelType);
                      });

                  } else {
                      this.form.sourceItemAttr.channelTypes = res.data.sourceItemAttr.channelTypes;
                  }
              })
            },
            //置为编辑状态
            editItem(){
                this.index = 1;
            },
            //切换供货商类型
            changeApiType(val){
              console.log(val);
            },
            //保存  新增和编辑
            saveSupplier(formName) {
                let params = this.copy(this.form);
                // let params = this.form;

                //不同步时的供应商对应的渠道
                let CHarr = [];
                if(params.sourceItemAttr.channelTypes.length){
                    params.sourceItemAttr.channelTypes.forEach((item)=>{
                        CHarr.push(item);
                    })
                }
                params.channelTypes = CHarr;
                let api = params.id ? 'edit' : 'add';
                if(!params.id) {
                    delete params.id;
                }
                if(params.apiType==1&&this.index!=1){
                    delete params.apiAuth
                }
                if(params.apiType==2){
                    delete  params.account
                    delete  params.password
                }
                if(!params.apiType){
                    delete params.apiAuth;
                    delete params.targetItemAttr;
                    delete params.priceStrategy;
                    delete params.sourceItemAttr;
                } else {
                    delete params.channelTypes;
                }

                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.api.supplier[api](params).then((res) => {
                            this.$message.success({message:res.message, showClose:true});
                            this.$router.push('supplierList')
                        });
                    } else {
                        console.log('error submit!!');
                    }
                });
            },
            cancel(){
              this.$router.go(-1)
            },
            showSelect (selects) {
                this.$refs[selects].$children[1].$refs.input.click()
            }
        }
    }
</script>

<style lang='scss' scoped>
    .contain{
        /*background: #ffffff;*/
        box-sizing: border-box;
        padding: 20px;
    }
    /deep/.search-bar {
        padding-bottom: 0;
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
        margin-bottom: 20px;
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
    .el-form-item{
        margin-bottom: 0px;
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
    .marLeft{
        margin-left: 10px;
    }
    .marBottom{
        margin-bottom: 20px;
    }
    .boxBack{
        background: #ffffff;
        box-sizing: border-box;
        padding: 15px 10px 5px 10px;
    }
    .btnBox{

    }

</style>
