<template>
  <div class="center-channel-manage">
      <div class="search-bar">
        <div class="btn-main" @click="addShow = true">新增渠道</div>
      </div>
      <table class="table">
         <thead>
           <tr>
             <th class="dot"></th>
             <th>渠道名称</th>
             <th>状态</th>
             <th>操作</th>
           </tr>
         </thead>
         <tbody>
          <tr>
            <td class="dot active"></td>
            <td>January</td>
            <td>$100</td>
            <td>$100</td>
          </tr>
          </tbody>
      </table>
      <el-dialog title="新增品牌" :visible.sync="addShow" width="468px">
        <div class="dialog-form">
          <div class="form-wrap">
            <div class="form-name">渠道名称</div>
            <input class="form-control"/>
          </div>
          <div class="form-wrap">
            <div class="form-name">状态</div>
            <el-radio v-model="radio" label="1">启用</el-radio>
            <el-radio v-model="radio" label="2">禁用</el-radio>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <button class="btn-org-main" @click="addType()">保存</button>
          <button class="btn-fill-grey-main" @click="addShow = false">取消</button>
        </span>
      </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      addShow:false,
      radio:1
    }
  },
  methods:{
    selectLetter(item) {
      this.currentLetter = item;
    }
  },
  created() {

  }
}
</script>
<style scoped lang='scss'>
  .center-channel-manage{
    background: $white-color;
    .dialog-form{
      .form-name{
        width: 60px; 
      }
      .form-control{
        width: 300px;
      }
      .form-group{
        width: 380px;
      }
    }
  }
</style>