<template>
  <div class="center-feedback">
    <div class="tab-bar">
      <div class="tab-item active">已处理</div>
      <div class="tab-item">待处理</div>
    </div>
     <table class="table">
         <thead>
           <tr>
             <th class="dot"></th>
             <th class="table-w160">反馈时间</th>
             <th class="table-w100">用户名</th>
             <th class="table-w140">用户联系方式</th>
             <th>反馈内容</th>
             <th class="table-w140">附件</th>
             <th class="table-w60">操作</th>
           </tr>
         </thead>
         <tbody>
          <tr>
            <td class="dot active"></td>
            <td>January</td>
            <td>$100</td>
            <td>$100</td>
            <td>
              <el-popover class="ellipsis"
                placement="right"
                width="260"
                trigger="hover">
                <span style="width:100%;height:100%">反馈内容反馈内容反馈内容反馈内容反馈内容反馈内容反馈内容反馈内容反馈内容反馈内容</span>
                <span slot="reference">反馈内容反馈内容反馈内容反馈内容反馈内容反馈内容</span>
              </el-popover>
            </td>
            <td>$100</td>
            <td>
              <div class="link" @click="openDeal()">处理</div>
            </td>
          </tr>
          </tbody>
      </table>
      <el-dialog title="反馈处理：" :visible.sync="dealShow" width="450px">
        <div class="user-feedback">
          <div class="deal-title">用户反馈</div>
          <div class="deal-content">
            <div class="user-info">
              <span class="info-title">用户名：</span>
            </div>
            <div class="user-info">
              <span class="info-title">联系方式：</span>
              </div>
            <div class="user-info">
              <span class="info-title">反馈时间：</span>
              </div>
            <div class="user-info">
              <span class="info-title">反馈内容：</span>
              </div>
          </div>
        </div>
        <div class="user-feedback">
          <div class="deal-title">处理信息</div>
          <div class="deal-content">
            <div class="textarea-title">回复内容：</div>
            <textarea class="textarea"></textarea>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <button class="btn-org-main" @click="addType()">保存</button>
          <button class="btn-fill-grey-main" @click="dealShow = false">取消</button>
        </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      dealShow:false
    }
  },
  methods: {
    openDeal() {
      this.dealShow = true;
    }
  },
  created() {

  }
}
</script>
<style scoped lang='scss'>
  .center-feedback{
    .table{
      background: $white-color;
    }
    .user-feedback{
      margin-bottom: 20px;
      border:1px solid #ddd;
      .deal-title{
        padding-left: 10px;
        font-weight: 900;
        line-height: 30px;
        background: #f5f5f5;
      }
      .deal-content{
        padding: 15px 10px;
        .user-info{
          margin-bottom: 15px;
          .info-title{
            width: 80px;
            text-align: right;
          }
        }
        .textarea-title{
          display: inline-block;
          vertical-align: top;
        }
        .textarea{
          display: inline-block;
          width: 300px;
          height: 130px;
        }
      }
    }
  }
</style>
