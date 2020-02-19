<template>
  <div class="center-order-count">
    <div class="top-wrap">
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
         <div class="form-wrap">
          <div class="form-name">供应商：</div>
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
            <th>销售量</th>
            <th>销售额（元）</th>
            <th>供应商</th>
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
    .search-bar{
      border-bottom: 0;
      .form-name{
        width: 70px;
        text-align: right;
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