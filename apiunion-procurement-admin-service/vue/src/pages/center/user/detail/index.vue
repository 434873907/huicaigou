<template>
    <div>
        <div class="panel">
            <p class="tit">用户信息:</p>
            <div class="pb">
                <div class="line">
                    <div class="label">注册时间:</div><div class="content">{{info.registerTime}}</div>
                </div>
                <div class="line">
                    <div class="label">手机号:</div><div class="content">{{info.phone}}</div>
                </div>
                <div class="line">
                    <div class="label">用户昵称:</div><div class="content">{{info.userName}}</div>
                </div>
                <div class="line">
                    <div class="label">登录名称:</div><div class="content">{{info.loginAccount || ''}}</div>
                </div>
                <div class="line">
                    <div class="label">联系人:</div><div class="content">{{info.contacts || ''}}</div>
                </div>
                 <div class="line">
                    <div class="label">微信昵称:</div><div class="content">{{info.wxNickName || ''}}</div>
                </div>
                <div class="line">
                    <div class="label">会员类型:</div>
                    <div class="content">{{info.userType}}</div>
                </div>
                <div class="line">
                    <div class="label">邮箱地址:</div><div class="content">{{info.email}}</div>
                </div>
                <div class="line">
                    <div class="label">账号状态:</div>
                    <div class="content">{{info.status | toStatus }}</div>
                    <div class="content link" @click="setDisable(info)">{{info.status | toStatusFun }}</div>
                </div>
            </div>

        </div>
        <div class="panel">
            <p class="tit">账户信息:</p>
            <div class="pb">
                <div class="line">
                    <div class="label">账户余额:</div>
                    <div class="content"><span class="price">{{info.balance}}</span>元</div>
                    <div class="content link" @click="openRechargeModal">交易流水</div>
                </div>
            </div>
        </div>
        <div class="panel">
            <p class="tit">其它信息:</p>
            <div class="pb">
                <div class="line">
                    <div class="label">用户备注:</div><div class="content">{{info.remark}}</div>
                    <div class="content link" @click="remark">修改备注</div>
                </div>
                <div class="line relation" v-if="info.invitationCode">
                    <div class="label">所属关系:</div>
                    <span >
                        {{info.invitationCodeCreator || ''}}（邀请码:{{info.invitationCode || '暂无'}}）
                    </span>
                </div>
            </div>
        </div>

        <el-dialog title="交易流水" :visible.sync="showRecharge" width="1040px" class="recharge-dialog">
            <div class="dialog-form">
                <table class="table table-text">
                    <thead>
                    <tr>
                        <th class="table-w180">交易时间</th>
                        <th class="table-w220">交易流水号</th>
                        <th class="table-w160">订单号</th>
                        <th>交易类型</th>
                        <th>交易金额</th>
                        <th>赠送金额</th>
                        <th class="table-w100">当前余额</th>
                        <th>交易状态</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item, index) in rechargeData" :key="index+'recharge'">
                        <td>{{item.createTime}}</td>
                        <td>{{item.flowNum}}</td>
                        <td>
                            <span class="orderItem" @click="goOrderDetail(item)" v-if="item.orderNum">{{item.orderNum}}</span>
                            <span v-if="!item.orderNum">无相关订单</span>
                        </td>
                        <td>
                            <span v-show="item.flowType==1">充值</span>
                            <span v-show="item.flowType==2">支付</span>
                            <span v-show="item.flowType==3">退款</span>
                            <span v-show="item.flowType==4">定金支付</span>
                            <span v-show="item.flowType==5">尾款支付</span>
                        </td>
                        <td class="price">￥{{item.account}}</td>
                        <td class="price">￥{{item.rewardAmount}}</td>
                        <td class="price ">￥{{item.balance}}</td>
                        <td class="status">
                            <span v-show="item.flowStatus==1">付款中</span>
                            <span v-show="item.flowStatus==2">已完成</span>
                            <span v-show="item.flowStatus==3">已关闭</span>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <pagination v-show="rechargeTotal>0" :total="rechargeTotal" :page.sync="rechargeParams.page" :limit.sync="rechargeParams.limit" @pagination="getRechargeList()" />
                <noData v-if="rechargeTotal===0"></noData>
            </div>
            <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="showRecharge = false">确定</button>
      </span>
        </el-dialog>

        <el-dialog title="修改备注" :visible.sync="dataReamrk.visible">
            <div style="position: relative;display: flex;">
         <textarea style="width:100%;height: 100px;"
                   class="form-control desc"
                   placeholder="请输入备注信息，最多15个字"
                   maxlength="15"
                   v-model="dataReamrk.remark"
         ></textarea>
                <span class="text-tips">{{dataReamrk.remark.length}}/15</span>
            </div>

            <p class="btnBox"><span class="btnRed" @click="remarkAbled">确 定</span><span class="btnBla" @click="dataReamrk.visible=false">取 消</span></p>
        </el-dialog>
    </div>
</template>
<script>
    let {log}=console;
    import Pagination from '@/components/pagination';
    import noData from '@/components/noData';
    export default {
        data(){
          return {
              info:{},
              showRecharge:false,
                rechargeData:undefined,
              rechargeTotal:undefined,
              rechargeParams: {
                  id: '',
                  limit: 10,
                  page: 1
              },
              dataReamrk:{
                  visible:false,
                  remark:"",
              }
          }
        },
        created() {
            if(this.$route.query.id){
                this.getDetail();
            }else{
                this.$router.back(-1);
            }
        },
        methods:{
            //去订单详情
            goOrderDetail(item){
                let href = this.$router.resolve({ path:'/center/trade/order/detail/'+this.rechargeParams.id +'/' + item.orderNum});
                window.open(href.href, '_blank');
            },
            getDetail(){
                this.api.consumer.detail({id:this.$route.query.id}).then((res) => {
                    this.info=res.data;
                })
            },
            setDisable(item) {
                let disable=item.status==0 ? 1 : 0;
                this.$msgbox({
                    title: '',
                    message: disable ? '确认禁用？' : '确认启用？',
                    showCancelButton: true,
                    confirmButtonText: '确定',
                    cancelButtonText: '取消'
                }).then(action => {
                    if(action === 'confirm') {
                        this.api.staff.disableUser({id: item.userId, disable: disable}).then((res) => {
                            this.$message.success({message:res.message, showClose:true});
                            this.getDetail();
                        });
                    }
                }).catch((err)=>{
                    log(err)
                });
            },
            openRechargeModal() {
                this.rechargeParams.id = this.info.userId;
                this.getRechargeList();
            },
            getRechargeList() {
                this.api.staff.recharge(this.rechargeParams).then((res) => {
                    this.rechargeData = res.data.dataList;
                    this.rechargeTotal = res.data.total;
                    this.showRecharge = true;
                });
            },
            remark(){
                let item= this.info;
                this.dataReamrk.remark=item.remark || "";
                this.dataReamrk.visible=true;
                this.dataReamrk.userId=item.userId;
            },
            remarkAbled(){
                this.api.consumer.remark({userId:this.dataReamrk.userId,remark:this.dataReamrk.remark}).then((res) => {
                    this.$message.success({message:res.message, showClose:true});
                    this.dataReamrk.visible=false;
                    return 'cc';
                }).then(()=>{
                    this.getDetail();
                })
            },
        },
        filters:{
            toStatus(cin){
                switch (cin-0) {
                    case 0:return "启用";
                    case 1:return "禁用";
                    default :return "数据错误";
                }
            },
            toStatusFun(cin){
                switch (cin-0) {
                    case 0:return "禁用";
                    case 1:return "启用";
                    default :return "数据错误";
                }
            }
        },
        components: {
            Pagination,
            noData
        }

    }
</script>
<style scoped lang='scss'>
    .panel{
        background: #fff;
        padding: 20px 20px 0;
        box-shadow:0px 1px 1px 0px rgba(229,229,229,1);
        border-radius:4px;
        margin-bottom: 10px;
        .tit{
            font-weight: bold;
            position: relative;
            &:before{
                content: "";
                position: absolute;
                width: 3px;
                height: 13px;
                background: #E1376C;
                left: -10px;
                top: -1px;
            }
        }

        .line{
            width: 340px;
            line-height: 20px;
            padding: 10px 0;
            display: inline-block;
            &.relation {
                width: 450px;
            }
            .label,.content{
                display: inline-block;
            }
            .label{
                color: #666;
                margin-right: 5px;
            }
            .content{
                color: #333;
                margin-left: 10px;
                &.link{
                    color: #3A62E1;
                    cursor: pointer;
                    white-space: nowrap;
                    display: inline-block;
                    margin-left: 20px;
                }
                .price {
                    margin-right: 5px;
                    font-weight: bold;
                }
            }
        }
        .pb{
            padding:  10px 0;
            width: 70%;
        }

    }
    .text-tips{
        position: absolute;
        width: 60px;
        text-align: right;
        right: 12px;
        bottom: 8px;
        height: 17px;
        font-size: 12px;
        font-family: PingFangSC;
        font-weight: 400;
        color: #999;
        line-height: 17px;
    }
    .btnBox{
        text-align: center;
        margin-top: 10px;
        .btnRed{
            display: inline-block;
            min-width: 86px;
            padding: 0 14px;
            border-radius: 100px;
            text-align: center;
            line-height: 28px;
            height: 30px;
            border: 1px solid #E1376C;
            cursor: pointer;
            background:#E1376C;
            margin-right: 10px;
            color: #fff;
        }
        .btnBla{
            display: inline-block;
            min-width: 86px;
            padding: 0 14px;
            border-radius: 100px;
            text-align: center;
            line-height: 28px;
            height: 30px;
            border: 1px solid #E1376C;
            cursor: pointer;
            color: #333;
            border: 1px solid rgba(204, 204, 204, 0.6);
            background: #fff;
        }
    }
    .orderItem{
        cursor: pointer;
        color: #3A62E1;
    }
</style>
