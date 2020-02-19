<template>
  <div class="center-staff">
    <div class="search-bar">
      <div class="search-bar-title">一般筛选：</div>
      <div class="form-wrap">
        <input type="text" class="form-control" v-model="listParams.nickName" placeholder="请输入职员名" @keyup.enter="getList(1)">
      </div>
      <div class="btn-fill-main" @click="getList(1)">搜索</div>
      <div class="right">
        <a class="btn-main add-staff" @click="showModal()">新增职员</a>
        <div class="search-bar-title">批量操作：</div>
        <a class="btn-fill-org-main" @click="batchDel()">删除账号</a>
      </div>
    </div>
    <table class="table table-text">
      <thead>
        <tr>
          <th class="dot" :class="{'active':checkAll}" @click="selectAll()"></th>
          <th class="table-w80">编号</th>
          <th class="table-w120">职员名称</th>
          <th class="table-w120">职员账户</th>
          <th class="table-w120">职员权限</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
      <tr v-for="(item, index) in listData" :key="index+'list'">
        <td class="dot" :class="{'active':item.checked}" @click="selectItem(item)"></td>
        <td>{{item.id}}</td>
        <td class="elips">
          <el-popover
                placement="top"
                width="260"
                trigger="hover">
                <span>{{item.nickName}}</span>
                <span class="ellipsis" slot="reference">{{item.nickName}}</span>
              </el-popover>
        </td>
        <td>{{item.account}}</td>
        <td>{{item.roleNames}}</td>
        <td>
          <a class="link" @click="showModal(item)">编辑</a>
          <a class="link" @click="resetPassword(item)">重置密码</a>
          <!-- <a class="link" @click="changePwd(item)"></a> -->
          <a class="link" @click="delStaff(item.id)">删除</a>
        </td>
      </tr>
      </tbody>
    </table>
    <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />

    <el-dialog :title="modalParams.id?'编辑职员':'新增职员'" :visible.sync="showStaffModal" width="450px">
      <div class="dialog-form">
        <div class="form-wrap">
          <div class="form-name">职员名称</div>
          <input class="form-control" maxlength="6" placeholder="限制6个字符" v-model="modalParams.nickName"/>
        </div>
        <div class="form-wrap">
          <div class="form-name">职员角色</div>
          <select class="form-control" v-model="modalParams.roleIds">
            <option value="">请选择角色</option>
            <option v-for="item in roleList" :key="item.id" :value="item.id">{{item.roleName}}</option>
          </select>
        </div>
        <div class="form-wrap" v-show="!modalParams.id">
          <div class="form-name">账户名</div>
          <input class="form-control" v-model="modalParams.account"/>
        </div>
        <div class="form-wrap" v-show="!modalParams.id">
          <div class="form-name">账户密码</div>
          <input class="form-control" type="password" v-model="modalParams.password"/>
        </div>
        <div class="form-wrap" v-show="!modalParams.id">
          <div class="form-name">确认密码</div>
          <input class="form-control" type="password" v-model="modalParams.confirmPassword"/>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-org-main" @click="saveStaff()">保存</button>
        <button class="btn-fill-grey-main" @click="showStaffModal = false">取消</button>
      </span>
    </el-dialog>

    <el-dialog title="更改密码" :visible.sync="showResetModal" width="450px">
      <div class="dialog-form">
        <div class="form-wrap">
          <div class="form-name">原密码</div>
          <input class="form-control" v-model="resetParams.originalPassword"/>
        </div>
        <div class="form-wrap">
          <div class="form-name">新密码</div>
          <input class="form-control" v-model="resetParams.newPassword"/>
        </div>
        <div class="form-wrap">
          <div class="form-name">确认密码</div>
          <input class="form-control" v-model="resetParams.confirmPassword"/>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-org-main" @click="saveReset()">保存</button>
        <button class="btn-fill-grey-main" @click="showResetModal = false">取消</button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
import Pagination from '@/components/pagination';
export default {
  data() {
    return {
      showStaffModal: false,
      showResetModal: false,
      checkAll: false,
      listData: [],
      total: 0,
      listParams: {
        page: 1,
        limit: 10,
        nickName: ''
      },
      roleList: [],
      modalParams: {
        nickName: '',
        roleIds: '',
        password: '',
        confirmPassword: ''
      },
      resetParams: {
        originalPassword: '',
        newPassword: '',
        confirmPassword: ''
      }
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.getRoleList();
      this.getList(1);
    },
    getRoleList() {
      this.api.role.all({}).then((res) => {
        this.roleList = res.data;
      });
    },
    getList(page) {
      this.listParams.page = page;
      this.api.staff.list(this.listParams).then((res) => {
        this.listData = res.data.dataList;
        this.total = res.data.total;
      });
    },
    showModal(item) {
      this.showStaffModal = true;
      this.modalParams = {
        nickName: '',
        roleIds: '',
        account: '',
        password: '',
        confirmPassword: ''
      };
      if(item) {
        this.modalParams.id = item.id;
        this.api.staff.detail({id: item.id}).then((res) => {
          this.modalParams = res.data;
        });
      }
    },
    resetPassword(item) {
      this.$msgbox({
        title: '',
        message: '确认重置密码？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.staff.initPassword({id: item.id}).then(() => {
            this.$message.success({message:'成功', showClose:true});
            this.getList(1);
          });
        }
      });
    },
    changePwd(item) {
      this.resetParams = {
        id: item.id,
        originalPassword: '',
        newPassword: '',
        confirmPassword: ''
      };
      this.showResetModal = true;
    },
    saveReset() {
      if(this.resetParams.newPassword !== this.resetParams.confirmPassword) {
        this.$message.info({message:'两次输入的密码不一致', showClose:true});
      } else {
        this.api.staff.resetPassword(this.resetParams).then((res) => {
          this.$message.success({message:res.message, showClose:true});
          this.showResetModal = false;
          this.getList(1);
        });
      }
    },
    saveStaff() {
      let params = {
        nickName: this.modalParams.nickName,
        roleIds: this.modalParams.roleIds
      };
      if(this.modalParams.id) {
        params.id = this.modalParams.id;
        this.api.staff.edit(params).then((res) => {
          this.$message.success({message:res.message, showClose:true});
          this.showStaffModal = false;
          this.getList(1);
        });
      } else {
        if(this.modalParams.password !== this.modalParams.confirmPassword) {
          this.$message.info({message:'两次输入的密码不一致', showClose:true});
        } else {
          params.account = this.modalParams.account;
          params.password = this.modalParams.password;
          this.api.staff.add(params).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.showStaffModal = false;
            this.getList(1);
          });
        }
      }
    },
    delStaff(id) {
      this.$msgbox({
        title: '',
        message: '确认删除？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.staff.del({id: id}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
      });
    },
    batchDel() {
      let ids = [];
      if(this.listData.length>0) {
        this.listData.forEach(item => {
          if(item.checked) {
            ids.push(item.id);
          }
        });
      }
      ids = ids.toString();
      if(ids) {
        this.delStaff(ids);
      } else {
        this.$message.info({message:'至少选择一个用户', showClose:true});
      }
    },
    selectItem(item) {
      this.checkAll = true;
      item.checked = !item.checked;
      this.listData.forEach((item) => {
        if(!item.checked) {
          this.checkAll = false;
        }
      });
    },
    selectAll() {
      this.checkAll = !this.checkAll;
      this.listData.forEach((item) => {
        if(this.checkAll) {
          item.checked = true;
        } else {
          item.checked = false;
        }
      });
    }
  },
  components: { Pagination }
}
</script>
<style scoped lang='scss'>
  .center-staff{
    background: #fff;
    .search-bar{
      .right{
        float: right;
        .add-staff{
          margin-right: 20px;
        }
      }
    }
    .table{
      .link{
        margin-right: 10px;
      }
    }
    .dialog-form{
      .form-wrap{
        .form-name{
          width: 70px;
          text-align: left;
        }
        .form-control{
          width: 290px;
        }
      }
    }
  }
 /deep/.elips {
   width: 100%;
   overflow: hidden;
   text-overflow: ellipsis;
   white-space: nowrap;
 }
</style>
