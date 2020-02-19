<template>
  <div class="trade-invoice">
    <table class="table">
      <thead>
        <tr>
          <th>订单号</th>
          <th>账号</th>
          <th>用户名称</th>
          <th>订单金额</th>
          <th>订单状态</th>
          <th>发票类型</th>
          <th>发票抬头</th>
          <th>单位名称</th>
          <th>税号</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in tableData" :key="item.id">
          <td>{{item.orderNum}}</td>
          <td>{{item.account}}</td>
          <td>{{item.nickName}}</td>
          <td>{{item.orderAmount}}</td>
          <td>{{item.orderStruts}}</td>
          <td>{{item.invoiceTypeStr}}</td>
          <td>{{item.invoiceHead}}</td>
          <td>{{item.companyName}}</td>
          <td>{{item.taxNumber}}</td>
          <td>
            <a class="link" @click="itemDetail(item)">查看详情</a>
            <a class="link" @click="makeInvoice(item)">开票</a>
          </td>
        </tr>
      </tbody>
    </table>
    <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
    <noData v-if="total===0"></noData>
    <el-dialog title="开票详情" :visible.sync="showInvoiceDetail" width="450px">
      <div class="dialog-form">
        <div class="invoice-row">
          <span class="title">订单号:</span>
          <span class="content">{{currentItem.orderNum}}</span>
        </div>
        <div class="invoice-row">
          <span class="title">发票类型:</span>
          <span class="content">{{currentItem.invoiceTypeStr}}</span>
        </div>
        <div class="invoice-row">
          <span class="title">发票抬头:</span>
          <span class="content">{{currentItem.invoiceHeadStr}}</span>
        </div>
        <div class="invoice-row">
          <span class="title">单位名称:</span>
          <span class="content">{{currentItem.companyName}}</span>
        </div>
        <div class="invoice-row">
          <span class="title">税号:</span>
          <span class="content">{{currentItem.taxNumber}}</span>
        </div>
        <div class="invoice-row">
          <span class="title">注册地址:</span>
          <span class="content">{{currentItem.registeredAddress}}</span>
        </div>
        <div class="invoice-row">
          <span class="title">注册电话:</span>
          <span class="content">{{currentItem.registeredPhone}}</span>
        </div>
        <div class="invoice-row">
          <span class="title">开户银行:</span>
          <span class="content">{{currentItem.bank}}</span>
        </div>
        <div class="invoice-row">
          <span class="title">银行账号:</span>
          <span class="content">{{currentItem.bankAccount}}</span>
        </div>
        <div class="invoice-row">
          <span class="title">联系电话:</span>
          <span class="content">{{currentItem.phone}}</span>
        </div>
        <div class="invoice-row">
          <span class="title">接收地址:</span>
          <span class="content">{{currentItem.deliveryInfo}}</span>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-fill-grey-main" @click="showInvoiceDetail = false">关闭</button>
      </span>
    </el-dialog>
    <el-dialog title="上传发票信息" :visible.sync="showInvoiceUpload" width="450px">
      <div class="dialog-form">
        <div class="invoice-row">
          <span class="title">订单号:</span>
          <span class="content">{{currentInvoice.orderNum}}</span>
        </div>
        <div class="invoice-row">
          <span class="title">发票类型:</span>
          <span class="content">{{currentInvoice.invoiceTypeStr}}</span>
        </div>
        <div class="invoice-row">
          <span class="title">发票抬头:</span>
          <span class="content">{{currentInvoice.invoiceHeadStr}}</span>
        </div>
        <div class="invoice-row">
          <span class="title">单位名称:</span>
          <span class="content">{{currentInvoice.companyName}}</span>
        </div>
        <div class="invoice-row">
          <span class="title">税号:</span>
          <span class="content">{{currentInvoice.taxNumber}}</span>
        </div>
        <div class="invoice-row invoice-input-row input-row">
          <span class="title">发票代码:</span>
          <input type="text" class="form-control" v-model="invoiceParams.invoiceCode" />
        </div>
        <div class="invoice-row invoice-input-row">
          <span class="title">发票号码:</span>
          <input type="text" class="form-control" v-model="invoiceParams.invoiceNum" />
        </div>
        <div class="invoice-row invoice-input-row">
          <span class="title">电子发票:</span>
          <upload uploadType='5' v-on:uploadSuccess="uploadInvoice" v-show="!invoiceParams.image">
            <a class="upload-invoice">上传电子发票</a>
          </upload>
          <upload uploadType='5' v-on:uploadSuccess="uploadInvoice" v-show="invoiceParams.image">
            <a class="uploaded">{{invoiceParams.fileName}}</a>
          </upload>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-org-main" @click="submitInvoice()">提交</button>
        <button class="btn-fill-grey-main" @click="showInvoiceUpload = false">关闭</button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import pagination from '@/components/pagination';
import noData from '@/components/noData';
export default {
  components: { pagination, noData },
  data() {
    return {
      showInvoiceDetail: false,
      showInvoiceUpload: false,
      currentItem: {},
      currentInvoice: {},
      invoiceParams: {
        id: '',
        image: '',
        invoiceCode: '',
        invoiceNum: '',
        fileName: ''
      },
      listParams: {
        page: 1,
        limit: 20,
        invoiceStatus: 0
      },
      total: 0,
      tableData: []
    }
  },
  created() {
    this.getList(1);
  },
  methods: {
    getList(page) {
      this.listParams.page = page;
      this.api.invoice.list(this.listParams).then((res) => {
        this.tableData = res.data.dataList;
        this.total = res.data.total;
      });
    },
    itemDetail(item) {
      this.showInvoiceDetail = true;
      this.api.invoice.detail({id: item.id}).then((res) => {
        this.currentItem = res.data;
      });
    },
    makeInvoice(item) {
      this.showInvoiceUpload = true;
      this.currentInvoice = item;
      this.invoiceParams.id = item.id;
    },
    submitInvoice() {
      if(!this.invoiceParams.invoiceCode) {
        this.$message.info({message: '请输入发票代码', showClose: true});
        return
      }
      if(!this.invoiceParams.invoiceNum) {
        this.$message.info({message: '请输入发票号码', showClose: true});
        return
      }
      this.api.invoice.uploadImg(this.invoiceParams).then((res) => {
        this.$message.success({message: res.message, showClose: true});
        this.showInvoiceUpload = false;
        this.getList(1);
      });
    },
    uploadInvoice(res) {
      this.invoiceParams.image = res.data;
      this.invoiceParams.fileName = res.name;
    }
  }
}
</script>

<style lang="scss" scoped>
  .trade-invoice{
    background: #fff;
    .table{
      .link{
        margin-right: 10px;
      }
    }
    .dialog-form{
      .invoice-row{
        padding-bottom: 10px;
        .title, .content{
          display: inline-block;
          vertical-align: top;
          line-height: 20px;
        }
        .title{
          text-align: right;
          width: 80px;
          padding-right: 10px;
        }
        .upload-container{
          display: inline-block;
          vertical-align: top;
        }
        .content{
          width: 270px;
          white-space: normal;
          word-break: break-all;
        }
        .upload-invoice{
          display: inline-block;
          vertical-align: top;
          border: 1px dashed #F06941;
          color: #F06941;
          text-align: center;
          line-height: 30px;
          width: 120px;
          border-radius: 15px;
        }
        .uploaded{
          display: inline-block;
          vertical-align: top;
          line-height: 30px;
          color: #3A62E1;
          font-size: 14px;
        }
      }
      .invoice-input-row{
        padding: 15px 0 0;
        .title, .content{
          line-height: 30px;
        }
      }
      .input-row{
        border-top: 1px solid $border-color;
      }
    }
  }
</style>
