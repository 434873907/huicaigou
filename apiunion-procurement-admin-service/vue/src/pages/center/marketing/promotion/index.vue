<template>
  <div class="promotion">
    <div class="tab-bar solid">
      <div class="tab-item" :class="{'active':listParams.status===0}" @click="statusSearch(0)">全部</div>
      <div class="tab-item" :class="{'active':listParams.status===1}" @click="statusSearch(1)">未开始</div>
      <div class="tab-item" :class="{'active':listParams.status===2}" @click="statusSearch(2)">已开始</div>
      <div class="tab-item" :class="{'active':listParams.status===3}" @click="statusSearch(3)">已结束</div>
      <div class="tab-item" :class="{'active':listParams.status===4}" @click="statusSearch(4)">未启用</div>
    </div>
    <div class="list-wrap">
      <div class="search-bar">
        <span class="search-bar-title">一般筛选:</span>
        <span class="normal-title">活动类型</span>
        <select class="form-control" v-model="listParams.actType" :class="{'unset-select':!listParams.actType}">
          <option value="">请选择</option>
          <option v-for="item in typeList" :key="item.code" :value="item.code">{{item.remark}}</option>
        </select>
        <span class="normal-title">关键字</span>
        <input type="text" class="form-control" placeholder="请输入" v-model.trim="listParams.keyword" @keyup.enter="getList(1)"/>
        <span class="normal-title">显示端</span>
        <select class="form-control" v-model="listParams.displayTerminal" :class="{'unset-select':!listParams.displayTerminal}">
          <option value="">全部</option>
          <option v-for="item in terminalList" :key="item.code" :value="item.code">{{item.remark}}</option>
        </select>
        <a href="javascript:;" class="btn-fill-main" @click="getList(1)">搜索</a>
        <a href="javascript:;" class="btn-fill-main" @click="clearSearch()">清空</a>
        <router-link :to="{ name: '活动详情'}" class="btn-main">新增活动</router-link>
      </div>
    </div>
    <table class="table table-text">
      <thead>
        <tr>
          <th class="table-w80">活动ID</th>
          <th class="table-w200">活动名称</th>
          <th class="table-w100">活动类型</th>
          <th class="table-w160">开始时间</th>
          <th class="table-w160">结束时间</th>
          <th class="table-w100">活动状态</th>
          <!-- <th>限时抢购</th> -->
          <th class="table-w100">显示在PC</th>
          <th class="table-w110">显示在APP</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in tableData" :key="item.id">
          <td>{{item.id}}</td>
          <td>
            <el-popover
                placement="top"
                width="260"
                trigger="hover">
                <span>{{item.actName}}</span> 
                <span slot="reference" class="ellipsis actName">{{item.actName}}</span>
              </el-popover>
          </td>
          <td>{{item.actTypeStr}}</td>
          <td>{{item.startTime}}</td>
          <td>{{item.endTime}}</td>
          <td>
            <span class="active-status" v-show="item.statusStr==='未开启'">{{item.statusStr}}</span>
            <span class="disable-status" v-show="item.statusStr==='已结束'">{{item.statusStr}}</span>
            <span class="during-status" v-show="item.statusStr==='进行中'">{{item.statusStr}}</span>
          </td>
          <!-- <td>xxx</td> -->
          <td>
            <span class="active-status" v-show="item.displayPC">是</span>
            <span class="disable-status" v-show="!item.displayPC">否</span>
          </td>
          <td>
            <span class="active-status" v-show="item.displayAPP">是</span>
            <span class="disable-status" v-show="!item.displayAPP">否</span>
          </td>
          <td>
            <a class="link" @click="editItem(item)">编辑</a>
            <a class="link" v-show="!item.status" @click="setStatus(item, 1)">启用</a>
            <a class="link" v-show="item.status" @click="setStatus(item, 0)">禁用</a>
            <a class="link" @click="delItem(item)">删除</a>
          </td>
        </tr>
      </tbody>
    </table>
    <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
    <noData v-if="total===0"></noData>
  </div>
</template>
<script>
import Pagination from '@/components/pagination';
import noData from '@/components/noData';
export default {
  data() {
    return {
      typeList: [],
      terminalList: [],
      tableData: [],
      total: -1,
      listParams: {
        limit:10,
        page:1,
        status:0,
        actType:'',
        displayTerminal: ''
      }
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.api.activity.typeList({}).then((res) => {
        this.typeList = res.data;
      });
      this.api.activity.display({}).then((res) => {
        this.terminalList = res.data;
      });
      this.getList(1);
    },
    getList(page) {
      this.listParams.page = page;
      let params = this.copy(this.listParams);
      if(!params.actType) {
        delete params.actType;
      }
      if(!params.displayTerminal) {
        delete params.displayTerminal;
      }
      this.api.activity.list(params).then((res) => {
        this.tableData = res.data.dataList;
        this.total = res.data.total;
      });
    },
    clearSearch() {
      this.listParams = {
        limit:10,
        page:1,
        status:0,
        actType:'',
        displayTerminal: ''
      };
      this.getList(1);
    },
    statusSearch(status) {
      this.listParams.status = status;
      this.getList(1);
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
          this.api.activity.setStatus({id: item.id, status: status}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
      });
    },
    delItem(item) {
      this.$msgbox({
        title: '',
        message: '确定删除？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.activity.delAct({id: item.id}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
      });
    },
    editItem(item) {
      let path = 'promotion/detail/'+item.id;
      window.open(path, '_blank');
    }
  },
  components: {
    Pagination,
    noData
  }
}
</script>
<style scoped lang='scss'>
  .promotion{
    .list-wrap{
      .search-bar{
        span{
          display: inline-block;
          vertical-align: top;
          line-height: 30px;
        }
        .normal-title{
          color: #333333;
          font-size: 14px;
          padding-right: 10px;
        }
        .form-control{
          margin-right: 20px;
        }
        .btn-fill-main{
          margin-right: 10px;
        }
      }
    }
    .table{
      background: #fff;
      .actName{
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        max-width: 100%;
      }
      .link{
        margin-right: 12px;
      }
    }
  }
</style>