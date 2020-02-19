<template>
  <div class="apply-for-refund">
    <el-dialog title="退款:" :visible.sync="showRefund" width="1000px" :before-close="cancelRefund">
      <div class="dialog-form">
        <div class="table-title">
          <div class="cell cell-w260">商品</div>
          <div class="cell cell-w115">退款数量</div>
          <div class="cell cell-w130">供应商</div>
          <div class="cell cell-w100">商品金额</div>
          <div class="cell cell-w140">退款原因</div>
          <div class="cell cell-w140">退款金额</div>
          <div class="cell">状态</div>
        </div>
        <div class="pkg-item" v-for="(item, index) in refundData.orderItemList" :key="index+'refund'">
          <div class="pkgNo">
            <span>包裹号:</span>
            <span class="item-num">{{item.pkgNo}}</span>
          </div>
          <div class="goods-item" :class="{'selected-item':goods.active}" v-for="goods in item.pkgOrderItems" :key="goods.id">
            <div class="dot" :class="{'active':goods.checked}" @click="setDot(goods)"></div>
            <div class="cell cell-w260 goods-info">
              <div class="goods-img"><img :src="goods.mainImg" /></div>
              <div class="goods-name">
                <h2>{{goods.itemName}}</h2>
                <p>商品编号：{{goods.id}}</p>
              </div>
            </div>
            <div class="cell cell-w115 goods-number">
              <span class="reduce" @click="reduceGoods(goods)">-</span>
              <input type="text" v-model="goods.num" :disabled="!goods.checked" />
              <span class="add" @click="addGoods(goods)">+</span>
              <h4>最多退{{goods.maxNum}}件</h4>
            </div>
            <div class="cell cell-w130 goods-padding">
              <el-popover class="ellipsis"
                placement="right"
                width="260"
                trigger="hover">
                <span>{{goods.supplierName}}</span>
                <span slot="reference">{{goods.supplierName}}</span>
              </el-popover>
            </div>
            <div class="cell cell-w100 goods-padding goods-amount">{{goods.currency}} {{doChangeRate(goods.itemPrice * goods.num, 2)}}</div>
            <div class="cell cell-w140 goods-padding">
              <select class="form-control refund-input" v-model="goods.refundType" :disabled="!goods.checked" :class="{'unset-select':!goods.refundType}">
                <option value="">请选择</option>
                <option v-for="r in reasonList" :key="r.refundReason" :value="r.refundReason">{{r.refundReasonDesc}}</option>
              </select>
            </div>
            <div class="cell cell-w140 goods-padding">
              <div class="currency-input">
                <input type="text" class="form-control" v-model="goods.refundAmount" :disabled="!goods.checked" />
                <span class="currency">￥</span>
              </div>
            </div>
            <div class="cell goods-padding goods-status">
              <span v-show="goods.refundStatus === -1">退款失败</span>
              <span v-show="goods.refundStatus === 0">退款中</span>
              <span v-show="goods.refundStatus === 1">退款成功</span>
            </div>
          </div>
        </div>
        <div class="pkg-item refund-item" v-if="false"  v-for="(item, index) in refundData.refundInfoList" :key="index+'refunded'">
<!--          <div class="pkgNo">-->
<!--            <span>包裹号:</span>-->
<!--            <span class="item-num">{{item.pkgNo}}</span>-->
<!--          </div>-->
<!--          <div class="goods-item" v-for="goods in item.pkgOrderItems" :key="goods.id">-->
          <div class="goods-item">
            <div class="cell cell-w260 goods-info">
              <div class="goods-img"><img :src="item.mainImg" /></div>
              <div class="goods-name">
                <h2>{{item.itemName}}</h2>
                <p>商品编号：{{item.id}}</p>
              </div>
            </div>
            <div class="cell cell-w115 goods-number">{{item.num}}</div>
            <div class="cell cell-w100 goods-padding">{{item.supplierName}}</div>
            <div class="cell cell-w100 goods-padding goods-amount">{{item.currency}} {{item.itemPrice}}</div>
            <div class="cell cell-w140 goods-number refund-reason">{{item.refundTypeName}}</div>
            <div class="cell cell-w140 goods-padding refund-amount">{{item.currency}} {{item.refundAmount}}</div>
            <div class="cell goods-padding goods-status">
              <span v-show="item.refundStatus === -1">退款失败</span>
              <span v-show="item.refundStatus === 0">退款中</span>
              <span v-show="item.refundStatus === 1" class="refunded">退款成功</span>
            </div>
          </div>
        </div>
        <div class="extra-refund" v-show="refundData.extraRefundInfoList && refundData.extraRefundInfoList.length>0">额外退款:</div>
        <div class="extra-title" v-show="refundData.extraRefundInfoList && refundData.extraRefundInfoList.length>0">
          <div class="cell cell-w140">额外退款金额</div>
          <div class="cell cell-w140">收款平台</div>
          <div class="cell cell-w210">收款账户</div>
          <div class="cell cell-w210">收款人</div>
          <div class="cell cell-w210">备注</div>
        </div>
        <div class="extra-item" v-for="(item, index) in refundData.extraRefundInfoList" :key="index+'extra'">
          <div class="cell cell-w140">
            <div class="currency-input">
              <input type="text" class="form-control" v-model="item.refundAmount" />
              <div class="currency">￥</div>
            </div>
          </div>
          <div class="cell cell-w140">
            <select class="form-control refund-input" v-model="item.receivePlatform">
              <option value="">请选择</option>
              <option v-for="i in platformList" :key="i.code" :value="i.code">{{i.remark}}</option>
            </select>
          </div>
          <div class="cell cell-w210">
            <input type="text" class="form-control" placeholder="请填写收款账户" v-model="item.receiveAccount" />
          </div>
          <div class="cell cell-w210">
            <input type="text" class="form-control" placeholder="请填写收款人" v-model="item.receiveUserName" />
          </div>
          <div class="cell cell-w210">
            <input type="text" class="form-control" placeholder="退款原因" v-model="item.remark" />
          </div>
          <div class="cell goods-padding goods-status">
            <a class="link" @click="addExRefund()" v-show="index==0">新增&nbsp;</a>
            <a class="link" @click="removeExRefund(index)">删除</a>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="refund()">保存</button>
        <button class="btn-fill-grey-main" @click="cancelRefund()">取消</button>
        <button class="btn-fill-main pull-right" @click="addExRefund()" v-if="false">额外退款</button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: {
    refundParams: {
      default: {}
    }
  },
  data() {
    return {
      showRefund: true,
      reasonList: [],
      platformList: [],
      refundData: {}
    }
  },
  created() {
    this.init();
  },
  methods: {
    showDialog(){
      this.showRefund = true;
    },
    init() {
      this.api.order.refundReason({}).then((res) => {
        this.reasonList = res.data;
      });
      this.api.order.platform({}).then((res) => {
        this.platformList = res.data;
      });
      this.api.order.refund({orderNum: this.refundParams.orderNum, userId: this.refundParams.userId}).then((res) => {
        this.$set(this.refundData, 'orderItemList', res.data.orderItemList);
        this.$set(this.refundData, 'refundInfoList', res.data.refundInfoList);
        this.$set(this.refundData, 'extraRefundInfoList', res.data.extraRefundInfoList);
        if(this.refundData.orderItemList && this.refundData.orderItemList.length>0) {
          this.refundData.orderItemList.forEach(item => {
            item.pkgOrderItems.forEach(goods => {
              this.$set(goods, 'checked', false);
              this.$set(goods, 'refundType', '');
              this.$set(goods, 'maxNum', this.copy(goods.num));
            });
          });
        }
      });
    },
    addExRefund() {
      let newItem = {
        refundAmount: '',
        receivePlatform: '',
        receiveAccount: '',
        receiveUserName: '',
        remark: ''
      };
      if(!this.refundData.extraRefundInfoList) {
        this.$set(this.refundData, 'extraRefundInfoList', []);
        this.refundData.extraRefundInfoList.push(newItem);
      } else {
        this.refundData.extraRefundInfoList.push(newItem);
      }
    },
    removeExRefund(index) {
      this.refundData.extraRefundInfoList.splice(index, 1);
    },
    reduceGoods(item) {
      if(!item.checked) {
        this.$message.info({message: '请先选中该商品', showClose: true});
        return
      }
      if(item.num === 1) {
        this.$message.info({message: '数量不能为0', showClose: true});
      } else {
        item.num --;
      }
    },
    addGoods(item) {
      if(!item.checked) {
        this.$message.info({message: '请先选中该商品', showClose: true});
        return
      }
      if(item.num >= item.maxNum) {
        this.$message.info({message: '数量不能大于'+item.maxNum+'件', showClose: true});
        item.num = this.copy(item.maxNum);
      } else {
        item.num ++;
      }
    },
    setDot(item) {
      item.checked = !item.checked;
    },
    refund() {
      let refundOrderItems = [];
      if(this.refundData.orderItemList && this.refundData.orderItemList.length>0) {
        this.refundData.orderItemList.forEach(item => {
          item.pkgOrderItems.forEach(goods => {
            if(goods.checked) {
              let goodsItem = {
                orderItemId: goods.id,
                refundReason: goods.refundType,
                refundNum: goods.num,
                refundAmount: goods.refundAmount
              };
              refundOrderItems.push(goodsItem);
            }
          });
        });
      }
      if(this.refundData.extraRefundInfoList && this.refundData.extraRefundInfoList.length > 0) {
        this.refundData.extraRefundInfoList.forEach((goods) => {
          let completeParam = true;
          for(let key in goods){
            if(!goods[key]){
              completeParam = false;
              break;
            }
          }
          completeParam
            && refundOrderItems.push(goods)
            // : this.$message.info({message: `第${index}条额外退款信息不完整，请填写完整或删除该条额外退款信息`, showClose: true});
        });
      }
      if(refundOrderItems.length === 0) {
        this.$message.info({message: '请选择一个商品或者添加一个额外退款', showClose: true});
        return
      }
      let params = {
        userId: this.refundParams.userId,
        orderNum: this.refundParams.orderNum,
        refundOrderItems: JSON.stringify(refundOrderItems)
      }
      this.api.order.orderRefund(params).then((res) => {
        this.$message.success({message: res.message, showClose: true});
        this.$emit('refund', { });
      });
    },
    cancelRefund() {
      this.showRefund = true;
      this.$emit('cancelRefund');
    },
    // 处理 相乘 精度缺失的问题
    doChangeRate (f, digit = 1) {
      var m = Math.pow(10, digit)
      return parseInt(f * m, 10) / m
    }
  }
}
</script>

<style lang="scss">
  .apply-for-refund{
    .el-dialog__body{
      padding: 0;
      .dialog-form{
        padding: 0;
        .cell{
          display: inline-block;
          vertical-align: top;
          padding-left: 20px;
        }
        .cell-w260{
          width: 260px;
        }
        .cell-w115{
          width: 115px;
        }
        .cell-w130{
          width: 130px;
        }
        .cell-w140{
          width: 140px;
        }
        .cell-w210{
          width: 210px;
        }
        .cell-w100{
          width: 100px;
        }
        .table-title{
          padding: 0 20px;
          border-bottom: 1px solid $border-color;
          .cell{
            font-size: 14px;
            color: #666666;
            line-height: 50px;
            font-weight: bold;
          }
        }
        .currency-input{
          display: inline-block;
          vertical-align: top;
          height: 30px;
          width: 120px;
          position: relative;
          .currency{
            position: absolute;
            font-size: 14px;
            color: #333333;
            font-weight: bold;
            left: 5px;
            top: 1px;
          }
          .form-control{
            width: 120px;
            min-width: 120px;
            padding-left: 20px;
          }
        }
        .pkg-item{
          .pkgNo{
            background: #F5F5F5;
            padding-left: 20px;
            span{
              display: inline-block;
              vertical-align: top;
              line-height: 44px;
              font-weight: bold;
              color: #333;
            }
            .item-num{
              padding-left: 10px;
              color: $main-color;
            }
          }
          .goods-item{
            margin: 0 20px;
            padding: 10px 0;
            border-bottom: 1px solid $border-color;
            position: relative;
            &:last-child{
              border: none;
            }
            .dot{
              width: 12px;
              height: 12px;
              position: absolute;
              left: -6px;
              top: 35px;
              &::after{
                content: " ";
                width: 12px;
                height: 12px;
                border:1px solid  #ddd;
                border-radius: 7px;
                position: absolute;
                left: 0;
                top: 0;
              }
              &.active::after{
                background:$main-color;
                border:1px solid $main-color;
              }
            }
            .refund-input{
              width: 120px;
              min-width: 120px;
            }

            .goods-info{
              .goods-img{
                display: inline-block;
                vertical-align: top;
                width: 62px;
                height: 62px;
                border-radius: 4px;
                overflow: hidden;
                img{
                  max-width: 62px;
                  max-height: 62px;
                }
              }
              .goods-name{
                display: inline-block;
                vertical-align: top;
                margin-left: 20px;
                width: 156px;
                h2{
                  font-size: 12px;
                  color: #333333;
                  font-weight: bold;
                  line-height: 16px;
                  height: 32px;
                  overflow: hidden;
                  text-overflow: ellipsis;
                  display: -webkit-box;
                  -webkit-line-clamp: 2;
                  -webkit-box-orient: vertical;
                }
                p{
                  padding-top: 10px;
                  font-size: 12px;
                  color: #666666;
                }
              }
            }
            .goods-number{
              font-size: 0;
              padding-top: 10px;
              span{
                display: inline-block;
                vertical-align: top;
                width: 30px;
                height: 30px;
                font-size: 14px;
                color: #333333;
                border: 1px solid $border-color;
                text-align: center;
                line-height: 30px;
                cursor: pointer;
              }
              input{
                display: inline-block;
                vertical-align: top;
                width: 30px;
                height: 30px;
                font-size: 14px;
                border-top: 1px solid $border-color;
                border-bottom: 1px solid $border-color;
                background: #fff;
                text-align: center;
              }
              .reduce{
                border-top-left-radius: 4px;
                border-bottom-left-radius: 4px;
                -moz-user-select: none; /*火狐*/
                -webkit-user-select: none;  /*webkit浏览器*/
                -ms-user-select: none;   /*IE10*/
                -khtml-user-select: none; /*早期浏览器*/
                user-select: none;
              }
              .add{
                border-top-right-radius: 4px;
                border-bottom-right-radius: 4px;
                -moz-user-select: none; /*火狐*/
                -webkit-user-select: none;  /*webkit浏览器*/
                -ms-user-select: none;   /*IE10*/
                -khtml-user-select: none; /*早期浏览器*/
                user-select: none;
              }
              h4{
                font-size: 12px;
                color: #666;
                padding-top: 5px;
              }
            }
            .goods-status{
              span{
                color: $main-color;
                font-weight: bold;
              }
            }
            .goods-amount{
              font-weight: bold;
            }
            .goods-padding{
              padding-top: 15px;
              line-height: 30px;
            }
          }
          .selected-item{
            background: #f7f7f7;
          }
        }
        .refund-item{
          .goods-item{
            .goods-number{
              font-size: 14px;
              color: #333;
              line-height: 30px;
              text-align: center;
            }
            .refund-reason{
              text-align: left;
            }
            .refund-amount{
              font-weight: bold;
              color: $main-color;
            }
            .goods-status{
              .refunded{
                color: #333;
              }
            }
          }
        }
        .extra-refund{
          line-height: 40px;
          color: $main-color;
          font-weight: bold;
          padding-left: 20px;
          border-top: 1px solid $border-color;
        }
        .extra-title{
          background: #F5F5F5;
          .cell{
            font-size: 14px;
            color: #333;
            line-height: 44px;
          }
        }
        .extra-item{
          padding: 20px 0;
          border-bottom: 1px solid $border-color;
          .cell{
            line-height: 30px;
          }
          .refund-input{
            width: 120px;
            min-width: 120px;
          }
        }
      }
    }
  }
</style>

