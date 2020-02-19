<template>
  <div class="center-level-manage">
    <div class="list-wrap">
      <div class="search-bar">
        <router-link class="btn-main" :to="{ name: '会员等级详情' }">新增会员等级</router-link>
      </div>
      <table class="table table-text">
         <thead>
           <tr>
             <th class="table-w200">规则名</th>
             <th class="table-w160">时间限制</th>
             <th class="table-w340">等级限制</th>
             <th class="table-w100">状态</th>
             <th>操作</th>
           </tr>
         </thead>
         <tbody>
          <tr v-for="item in tableData" :key="item.id">
            <td>{{item.ruleName}}</td>
            <td>{{item.timeLimit}}</td>
            <td>
              <el-popover 
                placement="top"
                width="260"
                trigger="hover">
                <span>{{item.levelString}}</span>
                <span class="ellipsis" slot="reference">{{item.levelString}}</span>
              </el-popover>
            </td>
            <td>
              <span v-show="item.status">启用</span>
              <span v-show="!item.status">禁用</span>
            </td>
            <td>
              <a class="link" @click="setStatus(item, 1)" v-show="!item.status">启用</a>
              <a class="link" @click="setStatus(item, 0)" v-show="item.status">禁用</a>
              <a class="link" @click="editItem(item)">编辑</a>
              <a class="link" @click="delItem(item)">删除</a>
            </td>
          </tr>
          </tbody>
      </table>
      <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
      <noData v-if="total===0"></noData>
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
      listParams: {
        page: 1,
        limit: 20
      },
      tableData: [],
      total: 0
    }
  },
  created() {
    this.getList(1)
  },
  methods: {
    getList(page) {
      this.listParams.page = page;
      this.api.staff.levelList(this.listParams).then((res) => {
        this.tableData = res.data.dataList;
        this.total = res.data.total;
      });
    },
    setStatus(item, status) {
      this.$msgbox({
        title: '',
        message: status === 1 ? '确认启用？' : '确认禁用？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.staff.setLevelStatus({id: item.id, status: status}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
      });
    },
    editItem(item) {
      let path = 'levelmanage/detail/'+item.id;
      window.open(path, '_blank');
    },
    delItem(item) {
      this.$msgbox({
        title: '',
        message: '确认删除？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.staff.delLevel({id: item.id}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
      });
    }
  }
}
</script>
<style scoped lang='scss'>
  .center-level-manage{
    .table{
      .link{
        margin-right: 10px;
      }
    }
  }
</style>