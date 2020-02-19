<template>
  <div class="center-supply-account">
    <div class="list-wrap">
     <div class="search-bar">
         <div class="search-bar-title">供应商筛选：</div>
         <div class="form-wrap">
           <div class="form-name">类型</div>
           <select class="form-control" style="margin-right: 20px" v-model="search.apiTypes" :class="{'unset-select':!search.type}">
             <option value="">请选择供应商类型</option>
             <option v-for="item in apiTypeList" :key="item.type" :value="item.type">{{item.name}}</option>
           </select>
           <div class="form-name">关键字</div>
           <input type="text" style="width:300px;" placeholder="请输入供货商名称进行搜索" v-model="search.supplierName" class="form-control">
         </div>
         <button class="btn-fill-main" style="margin-right: 30px" @click="getList(1)">搜索</button>
        <!-- <div class="search-bar-title">会员筛选：</div> -->
        <!-- <div class="form-wrap">
          <div class="form-name">用户昵称</div>
           <input type="text" class="form-control" placeholder="请输入用户昵称">
        </div>
        <button class="btn-fill-main">搜索</button>  -->
        <button class="btn-main" @click="addSupply()">新增供应商</button>
      </div>
      <table class="table table-text">
         <thead>
           <tr>
             <!-- <th class="table-w100">账户ID</th> -->
             <th class="table-w220">供货商名称</th>
             <th class="table-w100">商品数量</th>
             <th class="table-w160">供货商类型</th>
             <!-- <th class="table-w180">同步方式</th> -->
             <th class="table-w180">发货方式</th>
             <th class="table-w120">供货商状态</th>
             <!-- <th class="table-w180" v-show="!userConfig.singleChannel">渠道</th>          -->
             <th >操作</th>
           </tr>
         </thead>
         <tbody>
          <tr v-for="item in listData" :key="item.id">
            <td>
              <el-popover
                placement="top"
                width="260"
                trigger="hover">
                <span>{{item.supplierName}}</span>
                <span class="ellipsis" slot="reference">{{item.supplierName}}</span>
              </el-popover>
            </td>
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
            <td>
              <a class="link" @click="editItem(item)">编辑</a>
              <a class="link" @click="delItem(item)">删除</a>
            </td>
          </tr>
          </tbody>
      </table>
      <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
      <noData v-if="total===0"></noData>
    </div>
    <el-dialog :title="isEdit?'编辑供应商':'新增供应商'" :visible.sync="modalShow" width="590px">
      <div class="dialog-form">
        <el-form ref="form" :rules="rules" :model="form" label-width="110px">
          <div class="form-gorup-content">
            <div class="form-group-name">基本信息：</div>
            <el-form-item label="供货商名称"  prop="supplierName">
              <el-input v-model="form.supplierName" size="mini"></el-input>
            </el-form-item>
            <el-form-item label="供货商类型" v-if="apiTypeList.length>1">
              <el-select v-model="form.apiType" size="mini" placeholder="请选择" value-key="type">
                <el-option :label="item.name" :value="item.type" v-for="item in apiTypeList" :key="item.type+'type'"></el-option>
              </el-select>
            </el-form-item>
<!--            <p>{{form.sourceItemAttr.channelTypes}}</p>-->
            <el-form-item label="发货方式" prop="sourceItemAttr.channelTypes" v-if="form.apiType==0">
              <el-select v-model="form.sourceItemAttr.channelTypes" class="select-multiple" multiple size="mini" placeholder="" :class="!form.sourceItemAttr.channelTypes || form.sourceItemAttr.channelTypes.length < 2 ? 'special' : ''" ref="selectss">
                <el-option v-for="item in channelList"
                    :key="item.channelType"
                    :label="item.channelTypeName"
                    :value="item.channelType">
                </el-option>
              </el-select>
              <span class="send-goods-msg" v-if="!form.sourceItemAttr.channelTypes || form.sourceItemAttr.channelTypes.length == 0" @click="showSelect('selectss')">全部发货方式</span>
            </el-form-item>
            <el-form-item label="供货商状态" prop="status">
            <el-radio-group v-model="form.status">
              <el-radio :label="1">启用</el-radio>
              <el-radio :label="0">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
          </div>
          <div v-if="form.apiType">
            <div class="form-gorup-content">
              <div class="form-group-name">授权信息：</div>
              <el-form-item label="App Id">
                <el-input v-model="form.apiAuth.appId" size="mini" class="appId"></el-input>
              </el-form-item>
              <el-form-item label="App Secret">
                <el-input v-model="form.apiAuth.secretKey" size="mini"></el-input>
              </el-form-item>
            </div>
            <div class="form-gorup-content">

              <div class="form-group-name">商品筛选：</div>
              <el-form-item label="发货方式">

                <el-select v-model="form.sourceItemAttr.channelTypes" class="select-multiple" :class="!form.sourceItemAttr.channelTypes || form.sourceItemAttr.channelTypes.length < 2 ? 'special' : ''" multiple size="mini" placeholder="" ref="selects">
                  <el-option v-for="item in channelList"
                      :key="item.channelType"
                      :label="item.channelTypeName"
                      :value="item.channelType"></el-option>
                </el-select>
                <span class="send-goods-msg" v-if="!form.sourceItemAttr.channelTypes || form.sourceItemAttr.channelTypes.length == 0" @click="showSelect('selects')">全部发货方式</span>
              </el-form-item>
            </div>
            <div class="form-gorup-content no-border">
              <div class="form-group-name">商品属性：</div>
              <el-form-item label="目标店铺">
                <el-select v-model="form.targetItemAttr.shopId" size="mini" placeholder="请选择目标店铺">
                  <el-option :label="item.name" :value="item.id" v-for="item in shopList" :key="item.id"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="运费模板">
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
                  <div>
                    <span>
                         <el-popover class="ellipsis" placement="top-end" width="200" trigger="hover">
                            <div class="alertBox">
                              指基于商品的供货商进行上浮
                            </div>
                            <span class="operation" slot="reference">
                              <svg class="icon" aria-hidden="true">
                                <use xlink:href="#icon-help-fill" />
                              </svg>
                            </span>
                          </el-popover>
                    </span>
                  </div>
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
                  <div>
                    <span>
                         <el-popover class="ellipsis" placement="top-end" width="200" trigger="hover">
                            <div class="alertBox">
                              指基于商品的原价进行上浮
                            </div>
                            <span class="operation" slot="reference">
                              <svg class="icon" aria-hidden="true">
                                <use xlink:href="#icon-help-fill" />
                              </svg>
                            </span>
                          </el-popover>
                    </span>
                  </div>
                </div>

              </el-form-item>
            </div>
          </div>
          <el-form-item>
            <div class="btn-org-main" type="primary" @click="saveSupplier('form')">保存</div>
            <div class="btn-fill-grey-main" @click="modalShow = false">取消</div>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>


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
        apiTypes:""
      },
      levelList:[],
      total: -1,
      listData: [],
      isEdit: false,
      channelList: [],
      feetemplate:[],
      shopList:[],
      listParams: {
        page: 1,
        limit: 10
      },
      form:{
        supplierName:'',
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
      apiTypeList:[{name:'不同步',type:0}],
      rules:{
        supplierName: [{ required: true, message: '请输入供货商名称', trigger: 'blur' }]
      },
      dataTemp:[],//全选 的存储数据

    }
  },
  created() {
    this.init();
  },
  methods:{
    init() {
      this.getList(1);
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
      this.isEdit = false;
      this.modalShow = true;
      this.form = {
        supplierName:'',
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
      };
      // this.form.sourceItemAttr.channelTypes=JSON.parse(JSON.stringify(this.dataTemp)); //dataTemp永远不动

    },

    //编辑按钮
    editItem(item) {
      this.isEdit = true;
      this.api.supplier.detail({id: item.id}).then((res) => {
        // this.form = res.data;
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
        this.modalShow = true;

      });
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
      // let arr = []
      // params.sourceItemAttr.channelTypes.forEach(val=>{
      //  val.channelType ? arr.push(val.channelType): ''
      // })
      // params.sourceItemAttr.channelTypes = arr
      let api = params.id ? 'edit' : 'add';
      if(!params.id) {
        delete params.id;
      }
      if(!params.apiType){
        delete params.apiAuth;
        delete params.targetItemAttr;
        delete params.priceStrategy;
        delete params.sourceItemAttr;
        // params.apiAuth = {
        //   appId:'',
        //   secretKey:''
        // };
        // params.targetItemAttr = {
        //   shopId:'',
        //   expressFeeTemplateId:''
        // };
        // params.priceStrategy = {
        //   type:1,
        //   priceUpPercent:'',
        //   originalPriceUpPercent:''
        // };
        // params.sourceItemAttr = {
        //   channelTypes:[]
        // };
      } else {
        delete params.channelTypes;
        // params.channelTypes = [];
      }

      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.api.supplier[api](params).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.modalShow = false;
            this.getList(1);
          });
        } else {
          console.log('error submit!!');
        }
      });
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
    showSelect (selects) {
      this.$refs[selects].$children[1].$refs.input.click()
    }
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
  }
  .center-supply-account{
    .table{
      .link{
        margin-right: 20px;
      }
    }
    .form-wrap {
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
