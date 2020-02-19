<template>
  <div class="center-system-notice">
    <div class="search-bar">
      <div class="search-bar-title">一般筛选：</div>
      <span class="text">关键字</span>
      <input type="text" class="form-control" v-model="listParams.key" @keyup.enter="getList(1)"/>
      <span class="text text-time">发布时间</span>
      <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择" v-model="listParams.beginTime"></el-date-picker>
      <span class="time-mid">－</span>
      <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择" v-model="listParams.endTime"></el-date-picker>
      <a class="btn-fill-main" @click="getList(1)">搜索</a>
      <router-link :to="{ name: '通知详情'}" class="btn-main">新增公告</router-link>
    </div>
    <table class="table table-text">
      <thead>
        <tr>
          <th class="table-w200">标题</th>
          <th class="table-w300">内容</th>
          <th class="table-w100">跳转方式</th>
          <th class="table-w180">跳转至</th>
          <th class="table-w170">发布时间</th>
          <th class="table-w80">状态</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in tableData" :key="item.id">
          <td>
            <el-popover
              placement="top"
              width="260"
              trigger="hover">
              <span>{{item.title}}</span>
              <span class="ellipsis" slot="reference">{{item.title}}</span>
            </el-popover>
          </td>
          <td>
            <el-popover
              placement="top"
              width="260"
              trigger="hover">
              <span>{{item.content}}</span>
              <span class="ellipsis" slot="reference">{{item.content}}</span>
            </el-popover>
          </td>
          <td>{{item.typeName}}</td>
          <td>
            <el-popover class="ellipsis"
              placement="top"
              width="260"
              trigger="hover">
              <span>{{item.url}}</span>
              <span class="ellipsis" slot="reference">{{item.url}}</span>
            </el-popover>
          </td>
          <td>{{item.issueTime}}</td>
          <td>
            <span class="active-status" v-show="item.status">启用</span>
            <span class="disable-status" v-show="!item.status">禁用</span>
          </td>
          <td>
            <a class="link" v-show="!item.status" @click="setStatus(item, 1)">启用</a>
            <a class="link" v-show="item.status" @click="setStatus(item, 0)">禁用</a>
            <a class="link" @click="editNotice(item)">编辑</a>
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
      total: -1,
      typeList: [],
      listParams: {
        page: 1,
        limit: 10,
        key: '',
        beginTime: '',
        endTime: ''
      },
      tableData: []
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.api.notice.type().then((res) => {
        this.typeList = res.data;
      });
      this.getList(1);
    },
    getList(page) {
      this.listParams.page = page;
      this.api.notice.list(this.listParams).then((res) => {
        this.tableData = res.data.dataList;
        this.total = res.data.total;
        this.tableData.forEach(item => {
          this.typeList.forEach(type => {
            if(item.type === type.code) {
              item.typeName = type.remark;
            }
          });
        });
      });
    },
    setStatus(item, type) {
      this.$msgbox({
        title: '',
        message: type === 1 ? '确认启用？' : '确认禁用？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.notice.setStatus({id: item.id, status: type}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
      });
    },
    editNotice(item) {
      let path = 'notice/detail/' + item.id;
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
          this.api.notice.del({id: item.id}).then((res) => {
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
<style lang='scss'>
  .center-system-notice{
    background: #fff;
    .search-bar{
      .text{
        margin-right: 10px;
      }
      .text-time{
        margin-left: 20px;
      }
      .time-mid{
        padding: 0 10px;
      }
      .form-control{
        width: 160px;
      }
      .el-input, .el-input__inner{
        height: 30px;
        // width: 160px;
      }
      .el-input__icon{
        line-height: 30px;
      }
      .btn-fill-main, .btn-main{
        margin-left: 20px;
      }
    }
    .table{
      .link{
        margin-right: 10px;
      }
    }
  }
</style>
