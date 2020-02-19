<template>
  <div class="center-role">
    <div class="list-wrap">
      <div class="search-bar">
        <button class="btn-main" @click="addRoleFunc">新增角色</button>
      </div>
      <table class="table table-text">
         <thead>
           <tr>
             <!-- <th class="dot"></th> -->
             <th class="table-w120">角色</th>
             <th class="table-w440">角色名单</th>
             <th>操作</th>
           </tr>
         </thead>
         <tbody>
          <tr v-for="item in tableData" :key="item.id">
            <!-- <td class="dot"></td> -->
            <td>{{item.roleName}}</td>
            <td>
              <el-popover
                placement="top"
                width="260"
                trigger="hover">
                <span>{{item.userName}}</span>
                <span class="ellipsis" slot="reference">{{item.userName}}</span>
              </el-popover>
            </td>
            <td>
              <a href="javascript:;" class="link mr20" @click="editItem(item)">编辑</a>
              <a href="javascript:;" class="link" @click="delRole(item)">删除</a>
            </td>
          </tr>
          </tbody>
      </table>
      <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
      <noData v-if="total===0"></noData>
    </div>
    <el-dialog title="新增角色" :visible.sync="addRole" width="468px">
      <div class="dialog-form">
        <div class="role-bar">
          <span class="title">角色名称</span>
          <input type="text" maxlength="6" placeholder="限制6个字符" class="form-control role-name" v-model="roleName" />
        </div>
        <div class="role-content">
          <span class="title">选择菜单</span>
          <div class="roles-inner" v-if="addRole">
            <el-scrollbar class="roles" :noresize="true" :native="false">
              <el-tree
                :data="menuData"
                show-checkbox
                node-key="id"
                ref="tree"
                highlight-current
                :props="defaultProps">
              </el-tree>
            </el-scrollbar>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="saveRole()">保存</button>
        <button class="btn-fill-grey-main" @click="addRole = false">取消</button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import Pagination from '@/components/pagination';
import noData from '@/components/noData';
export default {
  data() {
    return {
      id: '',
      addRole: false,
      menuData: [{id:'@all',title:'最高权限'}],
      defaultProps: {
        children: 'menus',
        label: 'title'
      },
      total: 0,
      tableData: [],
      listParams: {
        page:1,
        limit:10
      },
      roleName: ''
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.getList(1);
      this.getMenu();
    },
    addRoleFunc(){
      this.roleName = ''
      this.id = '';
      // this.menuData = [{id:'@all',title:'最高权限'}],
      this.defaultProps = {
        children: 'menus',
        label: 'title'
      }
      this.menuData = []
      this.api.common.menu({}).then((res) => {
        this.menuData = res.data;
        this.addRole = true
      });
    },
    getList(page) {
      this.listParams.page = page;
      this.api.role.list(this.listParams).then((res) => {
        this.tableData = res.data.dataList;
        this.total = res.data.total;
      });
    },
    getMenu() {
      this.api.common.menu({}).then((res) => {
        this.menuData = this.menuData.concat(res.data);
      });
    },
    editItem(item) {
      this.id = item.id;
      this.api.role.index({id: item.id}).then((res) => {
        this.addRole = true;
        this.roleName = res.data.roleName;
        if(res.data.menuPermissions === '@all') {
          setTimeout(() => {
            this.$refs.tree.setCheckedKeys(['@all']);
          });
        } else {
          setTimeout(() => {
            let menus = res.data.menuPermissions.split(',');
            let lastMenu = [];
            menus.forEach(menu => {
              let level = menu.split('-').length;
              if(level === 3) {
                lastMenu.push(menu);
              }
            });
            this.$refs.tree.setCheckedKeys(lastMenu);
          });
        }
      });
    },
    saveRole() {
      let ids = this.$refs.tree.getCheckedKeys() || [];
      let params = [];
      if(ids.length === 0) {
        this.$message.info({message:'请选择至少一个菜单', showClose:true});
      } else {
        if(ids.indexOf('@all') !== -1) {
          params = ['@all'];
        } else {
          let newIds = [];
          ids.forEach(item => {
            newIds = newIds.concat(this.createNewArr(item));
          });
          newIds.forEach(item => {
            if(params.indexOf(item) === -1) {
              params.push(item);
            }
          });
        }
        if(this.id) {
          this.api.role.edit({id: this.id, roleName: this.roleName, menuPermissions: params.join(',')}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.addRole = false;
            this.id = '';
            this.getList(1);
          });
        } else {
          this.api.role.add({roleName: this.roleName, menuPermissions: params.join(',')}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.addRole = false;
            this.getList(1);
          });
        }
      }
    },
    delRole(item) {
      this.$msgbox({
        title: '',
        message: '确认删除？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.role.del({id: item.id}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
      });
    },
    createNewArr(item) {
      let oriArr = item.split('-');
      let targetArr = [];
      oriArr.forEach((i, index) => {
        targetArr.push(oriArr.slice(0,index+1).join('-'));
      });
      return targetArr;
    }
  },
  components: {
    'Pagination': Pagination,
    'noData': noData
  }
}
</script>
<style lang='scss'>
  .center-role{
    .table{
      .mr20{
        margin-right: 20px;
      }
    }
    .el-dialog{
      .dialog-form{
        padding: 0;
        .title{
          display: inline-block;
          vertical-align: top;
          width: 68px;
          line-height: 30px;
          font-size: 14px;
          color: #333333;
        }
        .role-bar{
          .role-name{
            width: 356px;
          }
        }
        .role-content{
          padding-top: 10px;
          .roles-inner{
              display: inline-block;
              vertical-align: top;
              width: 356px;
              height: 330px;
            .roles{
              height: 100%;
              .el-scrollbar__wrap{
                overflow-x:hidden;
              }
              .el-checkbox{
                line-height: 30px;
              }
              .level2{
                padding-left: 14px;
              }
              .level3{
                padding-left: 14px;
              }
            }
          }
        }
      }
    }
  }
</style>
