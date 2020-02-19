<template>
  <div class="center-order-count">
    <div class="top-wrap">
      <div class="total-count">
        <span>总销量：</span>
        <span class="num">22123123</span>
      </div>
      <div class="search-bar">
        <div class="form-wrap">
          <div class="form-name">商品名称：</div>
          <input type="text" class="form-control">
        </div>
        <div class="form-wrap">
          <div class="form-name">品牌名称：</div>
          <input type="text" class="form-control">
        </div>
        <div class="form-wrap">
          <div class="form-name">商品类别：</div>
          <select type="text" class="form-control"></select>
        </div>
      </div>
      <div class="search-bar">
        <div class="form-wrap">
          <div class="form-name">商品编码：</div>
          <input type="text" class="form-control">
        </div>
        <div class="form-wrap">
          <div class="form-name">交易时间：</div>
          <input type="text" class="form-control">
        </div>
        <button class="btn-fill-main">搜索</button>
        <button class="btn-fill-main">导出订单</button>
      </div>
    </div>
    <div class="list-wrap">
      <table class="table">
        <thead>
          <tr>
            <th>序号</th>
            <th>主图</th>
            <th>商品名称</th>
            <th>品牌</th>
            <th>商品品类</th>
            <th>商品编号</th>
            <th>采购量</th>
            <th>采购额（元）</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>January</td>
            <td>$100</td>
            <td>$100</td>
            <td>$100</td>
            <td>$100</td>
            <td>$100</td>
            <td>$100</td>
            <td>$100</td>
          </tr>
          </tbody>
      </table>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    msg: String
  },
  created() {

  }
}
</script>
<style scoped lang='scss'>
  .center-order-count{
    .top-wrap{
      background: $white-color;
    }
    .total-count{
      margin-bottom: 10px;
      padding-left: 20px;
      line-height: 68px;
      border-bottom: 1px solid $border-color;
      span{
        font-weight: 900;
      }
      .num{
        margin-left: 10px;
        font-size: 24px;
        color:$main-color;
        vertical-align: top;
      }
    }
    .search-bar{
      border-bottom: 0;
      .form-name{
        font-weight: 900;
      }
      button{
        margin-right: 10px;
      }
    }
    .list-wrap{
      margin-top: 20px;
    }
  }
</style>