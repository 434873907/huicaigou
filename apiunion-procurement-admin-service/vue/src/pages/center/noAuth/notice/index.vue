<template>
  <div class="no-authBox">

    <div class="no-auth">
      <div>
        <img class="noAuthImg" src="../../../../assets/noAuth.png" alt="">
      </div>
      您暂无当前页面权限，请联系管理员添加
    </div>
  </div>
</template>

<script>
export default {
  name: 'no-auth'
}
</script>
<style scoped>
  .no-authBox{
    position: absolute;
    left: 0;
    right: 0;
    bottom: 0;
    top:0;
  }
  .no-auth{
    display: flex;
    width: 100%;
    height: 100%;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    color: #666666;
  }
  .noAuthImg{
    width: 128px;
    height: 128px;
    margin-bottom: 20px;
  }
</style>
