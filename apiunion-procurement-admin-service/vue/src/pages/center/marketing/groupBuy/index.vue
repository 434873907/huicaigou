<template>
  <div class="groupBuy">
    <div class="tab-bar solid">
      <div class="tab-item" :class="{'active':listParams.status===0}" @click="statusSearch(0)">全部</div>
      <div class="tab-item" :class="{'active':listParams.status===1}" @click="statusSearch(1)">未开始</div>
      <div class="tab-item" :class="{'active':listParams.status===2}" @click="statusSearch(2)">进行中</div>
      <div class="tab-item" :class="{'active':listParams.status===3}" @click="statusSearch(3)">已结束</div>
      <div class="tab-item" :class="{'active':listParams.status===4}" @click="statusSearch(4)">未启用</div>
    </div>
    <div class="list-wrap">
      <div class="search-bar">
        <div class="form-wrap">
          <div class="form-name">关键词:</div>
          <input type="text" class="form-control" v-model="listParams.keyword" />
        </div>
        <a href="javascript:;" class="btn-fill-main" @click="getList()">搜索</a>
        <router-link :to="{ name: '团购详情'}" class="btn-main" target="_blank" >新增活动</router-link>
      </div>
    </div>
    <table class="table">
      <thead>
        <tr>
          <th class="table-w80">活动ID</th>
          <th class="table-w160">活动名称</th>
          <th class="table-w80">活动海报</th>
          <th class="table-w160">商品名称</th>
          <th class="table-w80">商品ID</th>
          <th class="table-w340">活动时间</th>
          <th class="table-w80">参团人数</th>
          <th class="table-w80">活动状态</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in tableData" :key="item.id">
          <td>{{item.id}}</td>
          <td>
            <el-popover class="ellipsis"
              placement="right"
              width="260"
              trigger="hover">
              <span>{{item.name}}</span>
              <span slot="reference">{{item.name}}</span>
            </el-popover>
          </td>
          <td>
            <el-popover
              placement="right"
              width="260"
              trigger="hover">
              <img style="width:100%;height:100%" :src="item.banner">
              <img slot="reference" :src="item.banner" />
            </el-popover>
          </td>
          <td>
            <el-popover class="ellipsis"
              placement="right"
              width="260"
              trigger="hover">
              <span>{{item.itemName}}</span>
              <span slot="reference">{{item.itemName}}</span>
            </el-popover>
          </td>
          <td>{{item.itemId}}</td>
          <td>{{item.actTime}}</td>
          <td>{{item.participateNum}}</td>
          <td>{{item.status ? '启用' : '禁用'}}</td>
          <td>
            <a class="link" @click="editItem(item)">编辑</a>
            <a class="link" @click="setStatus(item, 1)" v-show="!item.status">启用</a>
            <a class="link" @click="setStatus(item, 0)" v-show="item.status">禁用</a>
            <a class="link" @click="deleteItem(item)">删除</a>
          </td>
        </tr>
      </tbody>
    </table>
    <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList()" />
    <noData v-if="total===0"></noData>
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
        status: 0,
        page: 1,
        limit: 20,
        keyword: ''
      }
    }
  },
  created() {
    this.getList();
  },
  methods: {
    statusSearch(status) {
      this.listParams.status = status;
      this.getList();
    },
    deleteItem(item){
      this.$msgbox({
        title: '',
        message: '确认删除？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.groupBuy.delete({id: item.id}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList();
          });
        }
      });
    },
    editItem(item){
      let href = this.$router.resolve({ path:'groupBuy/detail/' + item.id});
      window.open(href.href, '_blank');
    },
    setStatus(item, status) {
      this.$msgbox({
        title: '',
        message: status ? '确定启用?' : '确定禁用?',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.groupBuy.updateStatus({id: item.id, status: status}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList();
          });
        }
      });
    },
    getList() {
      this.api.groupBuy.list(this.listParams).then((res) => {
        this.tableData = res.data.dataList;
        this.total = res.data.total;
      });
    }
  }
}
</script>

<style lang="scss" scoped>
  .groupBuy{
    .list-wrap{
      .timepicker-control{
        margin-right: 10px;
      }
      .btn-main{
        margin-left: 25px;
      }
    }
    .table{
      background: #fff;
      .link{
        margin-right: 10px;
      }
    }
  }
</style>
