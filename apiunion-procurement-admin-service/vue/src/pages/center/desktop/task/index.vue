<template>
  <div class="center-task">
    <div class="flex-wrap">
      <div class="flex-item">
        <div class="content-title">订单提醒</div>
        <div class="content-item">
          <div class="status-item active">
            <span>待审核</span>
            <span>122</span>
          </div>
          <div class="status-item">
            <span>已发货</span>
            <span>122</span>
          </div>
          <div class="status-item">
            <span>待发货</span>
            <span>122</span>
          </div>
          <div class="status-item">
            <span>已完成</span>
            <span>122</span>
          </div>
          <div class="status-item">
            <span>退款</span>
            <span>122</span>
          </div>
        </div>
      </div>
      <div class="flex-item">
        <div class="content-title">商品管理</div>
        <div class="content-item">
          <div class="status-item">
            <span>已上架</span>
            <span>122</span>
          </div>
          <div class="status-item">
            <span>已下架</span>
            <span>122</span>
          </div>
          <div class="status-item">
            <span>库存警告</span>
            <span>122</span>
          </div>
          <div class="status-item"></div>
          <div class="status-item"></div>
        </div>
      </div>
    </div>
    <div class="count-wrap">
      <div class="echarts-wrap" ref='echarts' style="height:510px;"></div>
      <div class="data">
        <div class="data-title">销售数据</div>
        <div class="data-mouth">
          <div class="title">本月销售额(元)</div>
          <div class="time">THIS MONTH</div>
          <div class="price">188,244.00</div>
        </div>
        <div class="time-data">
          <div class="time-data-item">
            <div class="data-item-name">昨日销售额(元)</div>
            <div class="data-en-name">YESTERDAY</div>
            <div class="data-item-price">18,244.00</div>
          </div>
          <div class="time-data-item">
            <div class="data-item-name">今日销售额(元)</div>
            <div class="data-en-name">TODAY</div>
            <div class="data-item-price">18,244.00</div>
          </div>
          <div class="time-data-item">
            <div class="data-item-name">本周销售额(元)</div>
            <div class="data-en-name">THIS WEEK</div>
            <div class="data-item-price">18,244.00</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
// 引入 echarts 主模块。
import * as echarts from 'echarts/lib/echarts';
// 引入折线图。
import 'echarts/lib/chart/line';
// 引入提示框组件、标题组件、工具箱组件。
import 'echarts/lib/component/tooltip';
import 'echarts/lib/component/legend';
import 'echarts/lib/component/title';

export default {
  methods: {
    initCharts() {
      this.chart = echarts.init(this.$refs.echarts);
      this.setOptions();
    },
    setOptions() {
      this.chart.setOption({
        baseOption:{
          title:{
            text:'销售趋势',
            left:24,
            textStyle:{
              color:'#E1376C',
              fontSize:24,
              fontWeight:600
            }
          },
          tooltip: {},
          toolbox: {},
          legend: {
            data:[
              {
                name:'订单数',
                icon:'none',
                textStyle:{
                  color:'#F06941',
                  fontSize:14,
                  fontWeight:600
                }
              },
              {
                name:'交易额',
                icon:'none',
                textStyle:{
                  color:'#3A62E1',
                  fontSize:14,
                  fontWeight:600
                }
              }
            ],
            right:55,
            top:0
          },
          xAxis: {
            type : 'category',
            boundaryGap : false,
            axisTick:{
              show:false
            },
            axisLine:{
              lineStyle:{
                color:'#ddd'
              }
            },
            axisLabel:{
              margin:'24',
              color:'#999',
              fontSize:'16',
              // align:'left'
            },
            data:['2018-06-07','2018-06-08','2018-06-09','2018-06-10','2018-06-11','2018-06-12']
          },
          yAxis: [
            {
              boundaryGap:false,
              type: 'value',
              max: 1200,
              splitNumber:6,
              axisTick:{
                show:false
              },
              offset:25,
              interval:200,
              axisLine:{
                show:false
              },
              axisLabel:{
                margin:'60',
                color:'#F06941',
                fontSize:'18',
                align:'left'
              }
            },
            {
              boundaryGap:false,
              max: 120,
              splitNumber:6,
              interval:20,
              type: 'value',
              axisTick:{
                show:false
              },
              axisLine:{
                show:false
              },
              axisLabel:{
                formatter: '{value}k',
                margin:'60',
                color:'#3A62E1',
                fontSize:'18',
                align:'right'
              }
            }
          ],
          series: [
            {
              name: '订单数',
              type: 'line',
              smooth: true,
              showSymbol:true,
              markArea: {
                  silent: true,
                  data: [[{
                      xAxis: '2018-06-07'
                  }, {
                      xAxis: '2018-06-11'
                  }]]
              },
              lineStyle:{
                color:'#F06941',
                width:4
              },
              data: [100,600,230,500,723,322]
            },
            {
              name:'交易额',
              type: 'line',
              smooth: true,
              showSymbol:true,
              yAxisIndex:1,
              lineStyle:{
                color:'#3A62E1',
                width:4
              },
              markArea: {
                  silent: true,
                  data: [[{
                      xAxis: '2018-06-07'
                  }, {
                      xAxis: '2018-06-11'
                  }]]
              },
              data: [5,40.30,33.40,95.50,62.03,45.66]
            }
          ]
        },
        media:[
          {
            query:{maxWidth:1100},
            option:{
              legend:{right:40},
              yAxis:[{offset:20}]
            }
          },
          {
            query:{maxWidth:1060},
            option:{
              legend:{right:30},
              yAxis:[{offset:15}]
            }
          },
          {
            query:{maxWidth:1010},
            option:{
              legend:{right:30},
              yAxis:[{offset:10}]
            }
          },
          {
            query:{maxWidth:950},
            option:{
              legend:{right:20},
              yAxis:[{offset:0}]
            }
          }
        ]
      });
    }
  },
  mounted() {
    this.initCharts();
    window.onresize = this.chart.resize;
  },
  created() {

  }
}
</script>
<style scoped lang='scss'>
  .center-task{
    min-width: 1400px;
    .flex-wrap{
      display: flex;
      .flex-item{
        height: 240px;
        background: $white-color;
        flex: 1;
        display: flex;
        overflow: hidden;
        border-radius: 4px;
        box-shadow: 0 7px 19px 0 rgba(205,221,234,0.47);
        .content-title{
          padding: 24px;
          width:200px;
          height: 240px;
          writing-mode: vertical-lr;
          color: $white-color;
          font-size: 24px;
          letter-spacing: 12px;
          font-weight: 600;
        }
        .content-item{
          flex: 1;
          padding: 20px 40px;
          display: flex;
          flex-wrap: wrap;
          .status-item{
            display: flex;
            align-items: center;
            justify-content: space-between;
            width: 50%;
            padding-right:40px;
            font-size: 16px;
            border-right: 1px solid $border-color;
            border-bottom: 1px solid $border-color;
            white-space: nowrap;
            span:last-child{
              font-weight: 600;
              position: relative;
              &::after{
                content: " ";
                width: 12px;
                height: 12px;
                background: $org-color;
                border-radius: 6px;
                position: absolute;
                right: -26px;
                top: 2px;
              }
            }
            &:nth-child(2n){
              padding-left: 40px;
              padding-right: 30px;
              border-right: 0;
            }
            &:nth-child(5),&:nth-child(6){
              border-bottom: 0;
            }
            &.active{
              color:$main-color;
              & span::after{
                background: $main-color;
              }
            }
          }
        }
        &:first-child{
          margin-right: 30px;
          .content-title{
            background: url(~@/assets/desktop/task/order.png) no-repeat;
          }
        }
        &:last-child{
          .content-title{
            background: url(~@/assets/desktop/task/goods.png) no-repeat;
          }
        }
      }
    }
    .count-wrap{
      display: flex;
      height: 560px;
      margin-bottom: 60px;
      margin-top: 30px;
      padding-top:30px; 
      background: $white-color;
      border-radius: 4px;
      box-shadow: 0 7px 19px 0 rgba(205,221,234,0.47);
      .echarts-wrap{
        flex: 2;
      }
      .data{
        width: 400px;
        margin-left: 60px;
        margin-right: 40px;
        .data-title{
          margin-bottom: 35px; 
          font-size: 24px;
          font-weight: 600;
          color:$main-color;
        }
        .data-mouth{
          height: 150px;
          margin-bottom: 50px;
          padding:24px 20px;
          background: url(~@/assets/desktop/task/priceData.png) no-repeat;
          box-shadow: 0 7px 12px 0 rgba($color: $main-color, $alpha: 0.37);
          color:$white-color;
          .title{
            font-size: 24px;
            font-weight: 600;
          }
          .time{
            margin: 10px 0 24px;
            font-weight: 600;
          }
          .price{
            font-size: 36px;
            font-weight: 600;
          }
        }
        .time-data{
          .time-data-item{
            margin-bottom: 20px;
            padding-left: 28px;
            padding-bottom: 20px;
            border-bottom: 1px solid $border-color;
            position: relative;
            &::before{
              content: " ";
              width: 8px;
              height: 40px;
              background: $main-color;
              position: absolute;
              left: 0;
              top: 0;
            }
            .data-item-name{
              margin-bottom: 10px;
              font-size: 16px;
              font-weight: 600;
            }
            .data-en-name{
              color:#666;
            }
            .data-item-price{
              float: right;
              margin-top: -30px;
              font-size: 24px;
              font-weight: 600;
              color:$main-color;
            }
            &:nth-child(2){
              &::before{
                background: #A855E5;
              }
              .data-item-price{
                color: #A855E5;
              }
            }
            &:nth-child(3){
              &::before{
                background: #517ADC;
              }
              .data-item-price{
                color: #517ADC;
              }
            }
          }
        }
      }
    }
  }
</style>
