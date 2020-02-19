<template>
    <div>
        <div class="dataBox">
            <div class="dataLeft">
                <div class="dayWork">
                    日常工作
                </div>
                <div class="dataList">
                    <div class="dataListItem">
                        <div>待发货</div>
                        <div class="dayCol">{{dataObj.WAIT_DELIVERED}}</div>
                    </div>
                    <div class="dataListItem borderLeft" >
                        <div>待审批</div>
                        <div class="dayCol">{{dataObj.WAIT_APPROVED}}</div>
                    </div>
                    <div class="dataListItem borderLeft">
                        <div>库存报警</div>
                        <div class="dayCol">{{dataObj.INVENTORY_WARN}}</div>
                    </div>
                </div>
            </div>
            <div class="dataRight">
                <div class="dayWork">
                    超时告警
                </div>
                <div class="dataList">
                    <div class="dataListItem">
                        <div>发货超时</div>
                        <div class="timeoutCol">{{dataObj.DELIVERY_TIMEOUT}}</div>
                    </div>
                    <div class="dataListItem borderLeft" >
                        <div>物流超时</div>
                        <div class="timeoutCol">{{dataObj.LOGISTICS_TIMEOUT}}</div>
                    </div>
                    <div class="dataListItem borderLeft">
                        <div>清关超时</div>
                        <div class="timeoutCol">{{dataObj.CUSTOMS_CLEARANCE}}</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="saleData">
            <div class="dayWork">
                销售数据
            </div>
            <div class="sale">
                <div class="saleNumBox">
                    <div class="marTopBox">
                        <div class="form-name marRight10">货主</div>
                        <select class="form-control" v-model="supplierId" style="margin-right: 20px" >
                            <option value="">全部</option>
                            <option v-for="item in supplyList" :key="item.id" :value="item.id">{{item.supplierName}}</option>
                        </select>
                        <div class="lookBtn">查看</div>
                    </div>
                </div>
                <div class="saleList">
                    <div class="saleItem marRight10">
                        <div class="saleCol">昨日销售额</div>
                        <div class="saleFont marTop15"> <span class="fontMoney">￥</span>{{dataObj.YESTERDAY_SALES}}</div>
                    </div>
                    <div class="saleItem marRight10 dayBack">
                        <div class="saleCol">今日销售额</div>
                        <div class="saleFont marTop15"><span class="fontMoney">￥</span>{{dataObj.TODAY_SALES}}</div>
                    </div>
                    <div class="saleItem marRight10 weekBack">
                        <div class="saleCol">本周销售额</div>
                        <div class="saleFont marTop15"><span class="fontMoney">￥</span>{{dataObj.WEEK_SALES}}</div>
                    </div>
                    <div class="saleItem monthBack">
                        <div class="saleCol">本月销售额</div>
                        <div class="saleFont marTop15"><span class="fontMoney">￥</span>{{dataObj.MONTH_SALES}}</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="saleTrend">
            <div class="dayWork">
                销售数据走势
            </div>
            <div class="sale">
                <div class="saleNumBox">
                    <div class="form-wrap form-wrap-last marTopBox">
                        <div class="form-name">起止时间</div>
                        <el-date-picker
                                class="timepicker-control"
                                type="datetime"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                placeholder="开始时间"
                                v-model="startTime"
                        ></el-date-picker> --
                        <el-date-picker
                                class="timepicker-control"
                                type="datetime"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                placeholder="结束时间"
                                v-model="endTime"
                        ></el-date-picker>
                        <div class="lookBtn marLeft20" @click="statisticByDay">查看</div>
                    </div>
                </div>

                <div ref="echarts" style="height: 400px">

                </div>
            </div>
        </div>
    </div>
</template>

<script>

    import * as echarts from 'echarts/lib/echarts';
    // 引入折线图。
    import 'echarts/lib/chart/line';
    // 引入提示框组件、标题组件、工具箱组件。
    import 'echarts/lib/component/tooltip';
    import 'echarts/lib/component/legend';
    import 'echarts/lib/component/title';
    export default {
        name: "index",
        data(){
          return {
              supplyList:[],
              supplierId:"",
              startTime:'',
              endTime:"",
              listPrice:[],
              listNum:[],
              listX:[],
              dataObj:{}
          }
        },
        mounted() {
            this.supplierOrderSummary();
            this.statisticByDay();
        },
        methods:{
            supplierOrderSummary(){
              this.api.supplier.supplierOrderSummary({}).then(res=>{
                this.dataObj = res.data;
              })
            },
            statisticByDay(){
              this.api.supplier.statisticByDay({
                  startTime:this.startTime,
                  endTime:this.endTime
              }).then(res=>{
                res.data.dataList.forEach((item)=>{
                    this.listX.push(item.date)
                    this.listPrice.push(item.price)
                    this.listNum.push(item.num)
                    this.initCharts()
                })
              })
            },
            initCharts() {
                console.log(this.$refs.echarts)
                this.chart = echarts.init(this.$refs.echarts);
                 this.setOptions();
            },
            setOptions() {
                this.chart.setOption({
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['交易额', '订单数']
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        toolbox: {
                            feature: {
                                saveAsImage: {}
                            }
                        },
                        xAxis: {
                            type: 'category',
                            boundaryGap: false,
                            splitLine: {
                                show: true,
                                lineStyle: {
                                    color: '#EEEEEE',
                                    width: 1
                                }
                            },
                            data: this.listX
                        },
                        yAxis: {
                            type: 'value',
                            splitLine: {
                                show: true,
                                lineStyle: {
                                    color: '#EEEEEE',
                                    width: 1
                                }
                            }
                        },
                        series: [
                            {
                                name: '交易额',
                                type: 'line',
                                stack: '总量',
                                data: this.listPrice
                            },
                            {
                                name: '订单数',
                                type: 'line',
                                stack: '总量',
                                data: this.listNum
                            }
                        ],
                    color:['#FF8EB1','#A3B9FF']
            });
            }
        }
    }
</script>

<style scoped lang="scss">
.dataBox{
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 20px;
}
    .dataLeft{
        flex: 1;
        background: white;
        margin-right: 20px;
    }
    .dataRight{
        flex: 1;
        background: white;
    }
    .dayWork{
        font-size: 14px;
        color: #333333;
        font-weight: 600;
        box-sizing: border-box;
        padding:15px 20px;
        border-bottom: 1px solid #d8d8d8;
    }
    .dataList{
        display: flex;
        align-items: center;
        justify-content: space-between;
        box-sizing: border-box;
        padding: 20px 0 20px 20px;
    }
    .dataListItem{
        flex: 1;
        justify-content: center;

    }
    .borderLeft{
        border-left: 1px solid #d8d8d8;
        padding-left: 15px;
    }
    .dayCol{
        margin-top: 10px;
        color: #3A62E1;
        font-size: 30px;
        font-weight: 600;
    }
    .timeoutCol{
        margin-top: 10px;
        color: #E1376C;
        font-size: 30px;
        font-weight: 600;
    }
    .saleData{
        background: #ffffff;
    }
    .marTopBox{
        display: flex;
        align-items: center;
    }
    .saleNumBox{
        box-sizing: border-box;
        padding: 20px 0;
    }
    .lookBtn{
        width:78px;
        height:30px;
        border-radius:100px;
        border:1px solid rgba(225,55,108,1);
        text-align: center;
        line-height: 30px;
        color: #E1376C;
        cursor: pointer;
    }
    .sale{
        box-sizing: border-box;
        padding: 0 20px 20px 20px;
    }
    .saleList{
        display: flex;
    }
    .saleItem{
        flex: 1;
        height:112px;
        background:linear-gradient(135deg,rgba(255,108,154,1) 0%,rgba(255,164,113,1) 100%);
        border-radius:4px;
        box-sizing: border-box;
        padding: 30px 20px 20px 20px;
    }
    .saleFont{
        color: #ffffff;
        font-weight: 600;
        font-size: 28px;
    }
    .saleCol{
        color: #ffffff;
        font-size: 16px;
    }
    .fontMoney{
        font-size: 22px;
    }
    .dayBack{
        height:112px;
        background:linear-gradient(135deg,rgba(158,79,255,1) 0%,rgba(217,143,255,1) 100%);
        border-radius:4px;
    }
    .weekBack{
        height:112px;
        background:linear-gradient(135deg,rgba(89,128,250,1) 0%,rgba(83,180,246,1) 100%);
        border-radius:4px;
    }
    .monthBack{
        height:112px;
        background:linear-gradient(135deg,rgba(54,193,223,1) 0%,rgba(76,219,244,1) 100%);
        border-radius:4px;
    }
    .saleTrend{
        background: #ffffff;
        margin-top: 20px;
    }

</style>
