<template>
  <div class="audit">
    <div class="tab">
      <span :class="{'active':auditStatusList==0}" @click="toggle(0)">待审核</span>
      <span :class="{'active':auditStatusList==1}" @click="toggle(1)">审核通过</span>
      <span :class="{'active':auditStatusList==2}" @click="toggle(2)">审核未通过</span>
    </div>
    <div class="list-wrap">

      <table class="table">
         <thead>
            <tr>
              <th class="table-w100">用户名称</th>
              <th class="table-w100">账号</th>
              <th class="table-w160">注册时间</th>
              <th class="table-w100">联系人</th>
              <th class="table-w100">账号类型</th>
              <th class="table-w120">邮箱</th>
              <th class="table-w100">资质</th>
              <th class="table-w160">银行账户信息</th>
              <th class="table-w100">审核状态</th>
              <th >操作</th>
            </tr>
         </thead>
         <tbody>
            <tr v-for="item in tableData" :key="item.id">
              <td>
                <el-popover
                 placement="top"
                  width="260"
                  trigger="hover">
                  <span>{{item.name}}</span>
                  <span class="ellipsis" slot="reference">{{item.name}}</span>
                </el-popover>
              </td>
              <td>
                <el-popover
                 placement="top"
                  width="260"
                  trigger="hover">
                  <span>{{item.account}}</span>
                  <span class="ellipsis" slot="reference">{{item.account}}</span>
                </el-popover>
              </td>
              <td>{{item.registerTime}}</td>
              <td>{{item.contact}}</td>
              <td>{{item.accountType}}</td>
              <td>
                <el-popover
                 placement="top"
                  width="260"
                  trigger="hover">
                  <span>{{item.email}}</span>
                  <span class="ellipsis" slot="reference">{{item.email}}</span>
                </el-popover>
              </td>
              <td>
                <el-popover
                 placement="top"
                  width="800"
                  trigger="hover">
                  <img style="width:100%;height:100%;object-fit:contain;" :src="item.certificatePhoto">
                  <img slot="reference" :src="item.certificatePhoto" />
                </el-popover>
              </td>
              <td>
                <el-popover
                 placement="top"
                  width="260"
                  trigger="hover">
                  <span>{{item.bankAccountInfo}}</span>
                  <span class="ellipsis" slot="reference">{{item.bankAccountInfo}}</span>
                </el-popover>
              </td>
              <td>
                <span v-show="item.auditStatus==0">待审核</span>
                <span v-show="item.auditStatus==2">审核未通过</span>
                <span v-show="item.auditStatus==1">审核通过</span>
              </td>
              <td>
                <a class="link" @click="markShow = true;marklist.id=item.id;" v-show="item.auditStatus==0">通过</a>
                <!-- <a class="link" @click="auditItem(item, 1)" v-show="item.auditStatus==0">通过</a> -->
                <a class="link" @click="auditItem(item, 2)" v-show="item.auditStatus==0">不通过</a>
                <a class="link" @click="info(item)">采购商详情</a>
              </td>
            </tr>
          </tbody>
      </table>
      <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
      <noData v-if="total===0"></noData>
      <!-- 审核时备注信息 -->
      <el-dialog title="审核时备注" :visible.sync="markShow" width="300px">
        <div>
         <span>备注：</span><input class="form-control" v-model="marklist.remark" maxlength="5" placeholder="请输入备注(至多5个字)">
        </div>
         <span slot="footer" class="dialog-footer">
          <button class="btn-org-main" @click="auditUser()">保存</button>
          <button class="btn-fill-grey-main" @click="markShow = false">取消</button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import Pagination from '@/components/pagination';
import noData from '@/components/noData';
export default {
  components: { Pagination, noData },
  data() {
    return {
      total: 0,
      tableData: [],
      listParams: {
        page: 1,
        limit: 20
      },
      markShow: false,
      marklist:{
        id:'',
        auditStatus: 1,
        remark: ''
      },
      auditStatusList:0
    }
  },
  created() {
    this.getList(1);
  },
  methods: {
    info(item){
      // console.log(item);return;
      this.$router.push({
        path:'/center/user/detail',
        query:{
          id:item.id
        }
      })
    },
    toggle(i){
      this.auditStatusList=i;
      this.getList(1);
    },
    getList(page) {
      this.listParams.page = page;
      this.listParams.auditStatusList=this.auditStatusList;
      this.api.staff.auditList(this.listParams).then((res) => {
        this.tableData = res.data.dataList;
        this.total = res.data.total;
      });
    },
    auditItem(item, status) {
      this.$msgbox({
        title: '',
        message: status==1 ? '确定审核通过?' : '确定审核不通过?',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.staff.auditUser({id: item.id, auditStatus:status}).then((res) => {
            this.$message.success({message: res.message, showClose: true});
            this.getList(1);
          });
        }
      });
    },
    //用户认证
    auditUser() {
      this.api.user.userAudit(this.marklist).then((res) => {
        this.$message.success({message: res.message, showClose: true});
        this.markShow = false;
        this.getList();
      });
    }
  }
}
</script>

<style lang="scss" scoped>
  .audit{
    .table{
      .link{
        margin-right: 10px;
      }
    }
  }
  .tab{
    margin-bottom: 10px;
    span{
      height: 40px;
      padding: 0 20px;
      display: inline-block;
      line-height: 40px;
      background: #fff;
      color: #000;
      font-size: 14px;
      margin-right: 10px;
      border-radius: 2px;
      cursor: pointer;
      &.active{
        background: #E1376C;
        color: #fff;
      }
    }
  }
  /deep/td {
    line-height: 20px;
  }
</style>
