<template>
  <div class="api-sql-run">
    <textarea class="form-control" placeholder="请输入SQL" v-model="sql"></textarea>
    <button class="btn btn-primary" @click="sqlRun()">生成SQL</button>
    <textarea class="form-control sqlResult" readonly v-model="sqlResult"></textarea>
  </div>
</template>
<script>
export default {
  data () {
    return {
      sql:'',
      sqlResult:''
    }
  },
  methods:{
    sqlRun(){
        if(!this.sql){
            this.$message.info('请输入SQL');
            return;
        }
        this.api.common.sqlRun({
            sql:this.sql
        }).then(res=>{
            this.sqlResult = res.data;
        });
    }
  },
  created() {
    
  }
}
</script>
<style scoped lang='scss'>
  .api-sql-run{
    padding: 50px;
    .form-control{
        padding: 10px;
        width: 100%;
        height: 200px;
        min-height: 200px;
        resize: vertical;
    }
    .btn{
        margin: 15px 0;
    }
    .sqlResult{
        min-height: 470px;
    }
  }
</style>
