<template>
  <div class="trade-invoiced">
    <table class="table">
      <thead>
        <tr>
          <th class="table-w160">订单号</th>
          <th class="table-w120">账号</th>
          <th class="table-w80">用户名称</th>
          <th class="table-w80">订单金额</th>
          <th class="table-w80">订单状态</th>
          <th class="table-w80">发票类型</th>
          <th class="table-w80">发票抬头</th>
          <th class="table-w160">单位名称</th>
          <th class="table-w160">税号</th>
          <th class="table-w100">发票图片</th>
          <th class="table-w160">发票代码</th>
          <th class="table-w160">发票号码</th>
          <th class="table-w100">操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in tableData" :key="item.id">
          <td>
            <el-popover class="ellipsis"
              placement="right"
              width="260"
              trigger="hover">
              <span>{{item.orderNum}}</span>
              <span slot="reference">{{item.orderNum}}</span>
            </el-popover>
          </td>
          <td>
            <el-popover class="ellipsis"
              placement="right"
              width="260"
              trigger="hover">
              <span>{{item.account}}</span>
              <span slot="reference">{{item.account}}</span>
            </el-popover>
          </td>
          <td>{{item.nickName}}</td>
          <td>{{item.orderAmount}}</td>
          <td>{{item.orderStruts}}</td>
          <td>{{item.invoiceTypeStr}}</td>
          <td>{{item.head}}</td>
          <td>
            <el-popover class="ellipsis"
              placement="right"
              width="260"
              trigger="hover">
              <span>{{item.companyName}}</span>
              <span slot="reference">{{item.companyName}}</span>
            </el-popover>
          </td>
          <td>
            <el-popover class="ellipsis"
              placement="right"
              width="260"
              trigger="hover">
              <span>{{item.taxNumber}}</span>
              <span slot="reference">{{item.taxNumber}}</span>
            </el-popover>
          </td>
          <td>
            <el-popover
              placement="right"
              width="260"
              trigger="hover">
              <img style="width:100%;height:100%" :src="item.image">
              <img slot="reference" :src="item.image" />
            </el-popover>
          </td>
          <td>
            <el-popover class="ellipsis"
              placement="right"
              width="260"
              trigger="hover">
              <span>{{item.invoiceCode}}</span>
              <span slot="reference">{{item.invoiceCode}}</span>
            </el-popover>
          </td>
          <td>
            <el-popover class="ellipsis"
              placement="right"
              width="260"
              trigger="hover">
              <span>{{item.invoiceNum}}</span>
              <span slot="reference">{{item.invoiceNum}}</span>
            </el-popover>
          </td>
          <td>
            <a class="link" @click="itemDetail(item)">查看详情</a>
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
      currentItem: {},
      listParams: {
        page: 1,
        limit: 20,
        invoiceStatus: 1
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
    }
  }
}
</script>

<style lang="scss" scoped>
  .trade-invoiced{
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
