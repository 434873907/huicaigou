<template>
  <div class="center-goods-stock">
    <div class="tab-bar">
      <div class="tab-item active">全部</div>
      <div class="tab-item">库存警告</div>
      <div class="tab-item">已下架</div>
      <div class="btn-main pull-right">导入</div>
    </div>
    <div class="list-wrap">
      <div class="search-bar">
        <div class="search-bar-title">其他筛选：</div>
        <div class="form-wrap">
          <div class="form-name">关键字</div>
          <select class="form-control"></select>
          <input type="text" class="form-control">
        </div>
        <div class="btn-fill-main">选择供应商</div>
        <div class="btn-fill-main">清空</div>
        <div class="btn-fill-main">搜索</div>
      </div>
      <table class="table">
        <thead>
          <tr>
            <th class="dot"></th>
            <th>供应商名称</th>
            <th>宝贝ID</th>
            <th>主图</th>
            <th>商品名称</th>
            <th>品牌</th>
            <th>型号</th>
            <th>条形码</th>
            <th>库存</th>
            <th>渠道</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="dot active"></td>
            <td>asd</td>
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
  .center-goods-stock{
    .search-bar{
      .btn-fill-main{
        margin-right: 10px;
      }
    }
  }
</style>