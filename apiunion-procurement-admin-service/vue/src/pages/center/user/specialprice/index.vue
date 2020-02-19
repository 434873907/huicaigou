<template>
  <div class="center-order">
     <div class="list-wrap">
      <div class="search-bar">
        <div class="search-bar-title">一般筛选：</div>
        <div class="form-wrap">
          <div class="form-name">商家名称</div>
          <input type="text" class="form-control">
        </div>
        <button class="btn-fill-main">搜索</button>
      </div>
      <table class="table">
         <thead>
           <tr>
             <th class="dot"></th>
             <th>商品名称</th>
             <th>条形码</th>
             <th>商家名称</th>
             <th>商家等级</th>
             <th>采购价（￥）</th>
             <th>高级会员价（￥）</th>
           </tr>
         </thead>
         <tbody>
          <tr>
            <td class="dot active"></td>
            <td>January</td>
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
  .center-order{

  }
</style>