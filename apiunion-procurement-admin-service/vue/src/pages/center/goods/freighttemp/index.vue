<template>
  <div class="center-freight-temp">
     <div class="list-wrap">
      <div class="search-bar">
        <router-link class="btn-main" :to="{ name: '运费模板详情' }">新建模板</router-link>
      </div>
      <table class="table table-text">
         <thead>
           <tr>
             <th class="table-w180">模板名称</th>
             <!-- <th class="table-w180">供应商</th> -->
             <th class="table-w120">是否包邮</th>
             <th class="table-w80">状态</th>
             <th>操作</th>
           </tr>
         </thead>
         <tbody>
          <tr v-for="item in tableData" :key="item.id">
            <td>
              <el-popover class="ellipsis"
                placement="right"
                width="260"
                trigger="hover">
                <span>{{item.templateName}}</span>
                <span slot="reference">{{item.templateName}}</span>
              </el-popover>
            </td>
            <!-- <td>{{item.supplierName}}</td> -->
            <td>
              <span v-show="item.isFree">是</span>
              <span v-show="!item.isFree">否</span>
            </td>
            <td>
              <span class="active-status" v-show="item.status">启用</span>
              <span class="disable-status" v-show="!item.status">禁用</span>
            </td>
            <td>
              <a class="link" @click="editItem(item, 1)">编辑</a>
              <a class="link" @click="editItem(item, 2)">复制新建</a>
              <a class="link" v-show="!item.status" @click="setTempStatus(item, 1)">启用</a>
              <a class="link" v-show="item.status" @click="setTempStatus(item, 0)">禁用</a>
              <a class="link" @click="removeTemp(item)">删除</a>
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
  data() {
    return {
      tableData: [],
      total: -1,
      listParams: {
        page: 1,
        limit: 10
      }
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.getList(1);
    },
    getList(page) {
      this.listParams.page = page;
      this.api.feetemplate.list(this.listParams).then((res) => {
        this.tableData = res.data.dataList;
        this.total = res.data.total;
      });
    },
    setTempStatus(item, type) {
      this.$msgbox({
        title: '',
        message: type === 1 ? '确认启用？' : '确认禁用？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.feetemplate.setStatus({id: item.id, status: type}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
      });
    },
    editItem(item, type) {  // 1:编辑, 2:复制新建
      let path = 'freighttemp/detail/'+item.id+'/'+type;
      window.open(path, '_blank');
    },
    removeTemp(item) {
      this.$msgbox({
        title: '',
        message: '确认删除？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.feetemplate.del({id: item.id}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
      });
    }
  },
  components: {
    'Pagination': Pagination,
    'noData': noData
  }
}
</script>
<style scoped lang='scss'>
  .center-freight-temp{
    .table{
      .link{
        margin-right: 10px;
      }
    }
  }
</style>