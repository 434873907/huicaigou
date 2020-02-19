<template>
  <div class="refund-detail-modal">
    <el-dialog title="退款详情:" :visible.sync="showRefundDetail" width="760px" :before-close="cancelRefund">
      <div class="dialog-form">
        <header class="table-row title-row">
          <div class="table-cell table-w150">操作时间</div>
          <div class="table-cell table-w100">商品金额</div>
          <div class="table-cell table-w100">退款金额</div>
          <div class="table-cell table-w100">状态</div>
          <div class="table-cell table-w70">操作人</div>
          <div class="table-cell">操作</div>
        </header>
        <div class="table-row content-row" v-for="(item, index) in refundList" :key="item.id" :class="{'content-even-row':index%2==1}">
          <div class="table-cell table-w150">{{item.createTime}}</div>
          <div class="table-cell table-w100 price">{{item.currency}}{{item.itemAmount}}</div>
          <div class="table-cell table-w100 refund-price">{{item.currency}}{{item.refundAmount}}</div>
          <div class="table-cell table-w100">
            <span v-show="item.refundStatus==-2">退款失败</span>
            <span v-show="item.refundStatus==-1">审核失败</span>
            <span v-show="item.refundStatus==0">退款审核中</span>
            <span v-show="item.refundStatus==1">退款中</span>
            <span v-show="item.refundStatus==2">已退款</span>
          </div>
          <div class="table-cell table-w70">{{item.operator}}</div>
          <div class="table-cell">
            <a class="link" @click="toggleRefund(item)">{{item.showRefund ? '收起' : '展开'}}退款商品</a>
            <a class="link" @click="confirmRefund(item, 1)">确认退款</a>
            <a class="link" @click="confirmRefund(item, 0)">撤销</a>
          </div>

          <div class="itemInfo" v-show="item.showRefund">
            <table class="table table-text">
              <thead>
                <tr>
                  <th class="table-w200">商品名称</th>
                  <th>供应商名称</th>
                  <th>退款类型</th>
                  <th>商品价格</th>
                  <th>退款金额</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="refund in item.refundItemInfos" :key="refund.refundItemId">
                  <td>
                    <el-popover class="ellipsis"
                      placement="right"
                      width="260"
                      trigger="hover">
                      <span>{{refund.itemName}}</span>
                      <span slot="reference">{{refund.itemName}}</span>
                    </el-popover>
                  </td>
                  <td>
                    <el-popover class="ellipsis"
                      placement="right"
                      width="260"
                      trigger="hover">
                      <span>{{refund.supplierName}}</span>
                      <span slot="reference">{{refund.supplierName}}</span>
                    </el-popover>
                  </td>
                  <td>{{refund.refundType}}</td>
                  <td>{{item.currency}}{{refund.itemAmount}}</td>
                  <td>
                    <span v-show="!refund.isEdit">{{item.currency}}{{refund.refundAmount}}</span>
                    <input type="number" class="form-control refund-input" v-show="refund.isEdit" v-model="refund.refundAmount" />
                  </td>
                  <td>
                    <a class="link" @click="startEdit(refund)" v-show="!refund.isEdit">更改退款金额</a>
                    <a class="link" @click="saveItem(refund)" v-show="refund.isEdit">保存</a>
                    <a class="link" @click="cancelItem(refund)" v-show="refund.isEdit">取消</a>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="dialog-form" v-show="showFlow">
        <div class="statement-title">退款流水:</div>
        <div class="table-row title-row statement-title-row">
          <div class="table-cell table-w150">操作时间</div>
          <div class="table-cell table-w100">商品金额</div>
          <div class="table-cell table-w100">退款金额</div>
          <div class="table-cell table-w100">状态</div>
          <div class="table-cell">操作人</div>
        </div>
        <div class="table-row content-row" v-for="(item, index) in extraList" :key="item.id" :class="{'content-even-row':index%2==1}">
          <div class="table-cell table-w150">{{item.createTime}}</div>
          <div class="table-cell table-w100 price">{{item.currency}}{{item.itemAmount}}</div>
          <div class="table-cell table-w100 refund-price">{{item.currency}}{{item.refundAmount}}</div>
          <div class="table-cell table-w100">
            <span v-show="item.refundStatus==-2">退款失败</span>
            <span v-show="item.refundStatus==-1">审核失败</span>
            <span v-show="item.refundStatus==0">退款审核中</span>
            <span v-show="item.refundStatus==1">退款中</span>
            <span v-show="item.refundStatus==2">已退款</span>
          </div>
          <div class="table-cell table-w70">{{item.operator}}</div>
        </div>
      </div>
      <div class="dialog-form">
        <div class="statement-detail">
          <span class="detail-text">订单支付金额</span>
          <span class="detail-price">{{extraData.currency}}{{extraData.payAmount}}</span>
          <span class="detail-text">商品退款金额</span>
          <span class="detail-price">{{extraData.currency}}{{extraData.itemRefundAmount}}</span>
          <span class="detail-text">额外退款金额</span>
          <span class="detail-price">{{extraData.currency}}{{extraData.extraRefundAmount}}</span>
          <a class="check-detail" @click="showFlow=!showFlow">{{showFlow?'收起':'查看'}}流水</a>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="saveRefund()">确定</button>
        <button class="btn-fill-grey-main" @click="cancelRefund()">取消</button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: {
    refundParams: {
      default: {}
    },
  },
  data() {
    return {
      showRefundDetail: true,
      showFlow: false,
      extraData: {},
      refundList: [],
      extraList: []
    }
  },
  created() {
    this.getDetail();
  },
  methods: {
    getDetail() {
      this.api.order.refundDetail(this.refundParams).then((res) => {
        // 如有已展开的退款商品则记录
        let showIds = [];
        let showRefundIds = [];
        if(this.refundList.length>0) {
          this.refundList.forEach(item => {
            if(item.showRefund) {
              showIds.push(item.id);
            }
            if(item.refundItemInfos && item.refundItemInfos.length>0) {
              item.refundItemInfos.forEach(refund => {
                if(refund.isEdit) {
                  showRefundIds.push(refund.refundItemId);
                }
              });
            }
          });
        }

        this.extraData = res.data;
        this.refundList = res.data.waitConfirmRefundList;
        this.refundList.forEach(item => {
          if(showIds.indexOf(item.id) != -1) {
            this.$set(item, 'showRefund', true);
          }
          if(!item.showRefund) {
            this.$set(item, 'showRefund', false);
          }
          if(item.refundItemInfos && item.refundItemInfos.length>0) {
            this.$set(item, 'refundItemInfos', item.refundItemInfos);
            item.refundItemInfos.forEach(refund => {
              if(showRefundIds.indexOf(refund.refundItemId) != -1) {
                this.$set(item, 'isEdit', true);
              }
              if(!refund.isEdit) {
                this.$set(refund, 'isEdit', false);
              }
            });
          }
        });
        this.extraList = res.data.completeRefundList;
        // this.extraList.forEach(item => {
        //   if(!item.showRefund) {
        //     this.$set(item, 'showRefund', false);
        //   }
        //   if(item.refundItemInfos && item.refundItemInfos.length>0) {
        //     item.refundItemInfos.forEach(refund => {
        //       this.$set(refund, 'idEdit', false);
        //     });
        //   }
        // });
      });
    },
    toggleRefund(item) {
      item.showRefund = !item.showRefund;
    },
    startEdit(refund) {
      refund.isEdit = true;
    },
    saveItem(refund) {
      let params = {
        userId: this.refundParams.userId,
        amount: refund.refundAmount,
        id: refund.refundItemId
      }
      if(!params.amount) {
        this.$message.info({message: '请填写退款金额', showClose: true});
        return
      }
      this.api.order.resetAmount(params).then((res) => {
        this.$message.success({message: res.message, showClose: true});
        this.getDetail();
      });
    },
    cancelItem(refund) {
      refund.isEdit = false;
      this.getDetail();
    },
    confirmRefund(item, isPass) {
      this.$msgbox({
        title: '',
        message: isPass ? '确认退款？' : '确认撤销？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          let params = {
            userId: this.refundParams.userId,
            orderRefundId: item.id,
            isPass: isPass
          };
          this.api.order.applyRefund(params).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getDetail();
          });
        }
      });
    },
    saveRefund() {
      this.$emit('refundDetail', {});
    },
    cancelRefund() {
      this.showRefundDetail = true;
      this.$emit('cancelRefundDetail');
    }
  }
}
</script>

<style lang="scss">
  .refund-detail-modal{
    .el-dialog__body{
      padding: 0;
      .dialog-form{
        padding: 0;
        .table-row{
          .table-cell{
            display: inline-block;
            vertical-align: top;
            line-height: 50px;
            padding-left: 20px;
            color: #333333;
            font-size: 12px;
            font-weight: normal;
            .link{
              margin-right: 10px;
            }
            .resetRefund{
              width: 60px;
              min-width: 60px;
            }
          }
          .table-w150{
            width: 150px;
          }
          .table-w140{
            width: 140px;
          }
          .table-w130{
            width: 130px;
          }
          .table-w120{
            width: 120px;
          }
          .table-w100{
            width: 100px;
          }
          .table-w90{
            width: 90px;
          }
          .table-w70{
            width: 70px;
          }
          .itemInfo{
            padding: 20px;
            background: #fff;
            border-bottom: 1px solid #eee;
            .table{
              box-shadow: 0 0 2px #ddd;
              .refund-input{
                width: 80px;
              }
              .link{
                margin-right: 10px;
              }
            }
          }
        }
        .statement-title{
          padding-left: 20px;
          color: $main-color;
          font-weight: bold;
          line-height: 50px;
          border-top: 1px solid $border-color;
        }
        .title-row{
          border-bottom: 1px solid $border-color;
          .table-cell{
            font-size: 14px;
            color: #666666;
            font-weight: bold;
          }
        }
        .statement-title-row{
          border: none;
          background: #f5f5f5;
        }
        .content-row{
          background: #fff;
          .price{
            font-weight: bold;
          }
          .refund-price{
            color: $main-color;
            font-weight: bold;
          }
          &:nth-of-type(even) {
            background: #F2F4F7;
          }
        }
        // .content-even-row {
        //   background: #F2F4F7;
        // }
        .statement-detail{
          text-align: right;
          padding-right: 20px;
          padding-top: 50px;
          span{
            display: inline-block;
            vertical-align: top;
            font-size: 16px;
            font-weight: bold;
            color: #333333;
            line-height: 46px;
          }
          .detail-price{
            color: $main-color;
            padding: 0 20px 0 5px;
          }
          .check-detail{
            display: inline-block;
            vertical-align: top;
            color: $main-color;
            border: 1px solid $main-color;
            width: 86px;
            height: 30px;
            text-align: center;
            line-height: 28px;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 8px;
          }
        }
      }
    }
    .el-dialog__footer{
      padding: 20px;
      border-top: 1px solid $border-color;
    }
  }
</style>

