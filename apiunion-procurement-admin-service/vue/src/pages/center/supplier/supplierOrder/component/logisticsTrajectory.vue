<template>
    <div>
        <el-dialog
                title="物流轨迹"
                :visible.sync="showTrajectory"
                width="608px"
        >
            <div>
                <div class="trajectoryTitle">
                    <div class="titleText"><span>物流公司：{{trajectoryObj.expressCompany}}</span></div>
                    <div class="titleText">
                        <span>物流单号：{{trajectoryObj.expressCompany}}</span>
                    </div>

                </div>
                <div>
                    <p class="trajectoryItem" v-for="item of trajectoryObj.expressDetails">{{item.time}}:{{item.express}}</p>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
                <div class="closeBtn" @click="showTrajectory = false">关闭</div>
              </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "logisticsTrajectory",
        data(){
          return {
              showTrajectory:false,
              trajectoryObj:{}
          }
        },
        mounted() {

        },
        methods:{
            getOrderPkgExpress(){
                this.api.supplier.getOrderPkgExpress({
                    pkgNo:this.$parent.orderObj.pkgNo,
                    orderNum:this.$parent.orderObj.orderNum
                }).then(res=>{
                    this.trajectoryObj = res.data;
                })
            }
        }
    }
</script>

<style scoped>
    .trajectoryTitle{
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding-bottom: 20px;
        margin-bottom: 20px;
        border-bottom:1px solid rgba(229,229,229,1);
        margin-top: 10px;
    }
    .titleText{
        font-size: 14px;
        color: #333333;
        font-weight: 600;
    }
    .closeBtn{
        width:86px;
        height:30px;
        background:rgba(225,55,108,1);
        border-radius:100px;
        text-align: center;
        line-height: 30px;
        color: #ffffff;
        margin: 0 auto;
        cursor: pointer;
    }
    .trajectoryItem{
        margin-bottom: 20px;
        line-height: 1.5;
    }
    /deep/ .el-dialog__footer{
        padding-top: 0 ;
    }
</style>
