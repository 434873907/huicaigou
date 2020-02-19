<template>
  <div class="store-shop">
    <div class="search-bar">
      <input type="text" class="form-control" placeholder="请输入店铺名称" v-model.trim="listParams.name" @keyup.enter="getList(1)"/>
      <select class="form-control" v-model="listParams.status" :class="{'unset-select':!listParams.status}">
        <option value="">店铺状态</option>
        <option v-for="item in statueList" :key="item.id" :value="item.id">{{item.name}}</option>
      </select>
      <div class="btn-fill-main" @click="getList(1)">搜索</div>
      <router-link :to="{ name: '店铺详情'}" class="btn-main">新增店铺</router-link>
    </div>
    <table class="table">
      <thead>
        <tr>
          <th class="table-w100">店铺ID</th>
          <th class="table-w100">店铺LOGO</th>
          <th class="table-w180">店铺名称</th>
          <th class="table-w110">发货时限</th>
          <th class="table-w110">到货时间</th>
          <th class="table-w110">商品数量</th>
          <th class="table-w110">上架商品数</th>
          <th class="table-w110">下架商品数</th>
          <th class="table-w110">店铺状态</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in tableData" :key="item.id">
          <td>{{item.id}}</td>
          <td>
            <el-popover
              placement="top"
              width="200"
              trigger="hover" class="imgBoxCont">
              <img :src="item.logoUrl" style="width:100%;height:100%">
              <img slot="reference" :src="item.logoUrl" class="imgBox" style="width:54px;height:54px;object-fit:contain;"/>
            </el-popover>
          </td>
          <td>
            <el-popover class="ellipsis"
              placement="top"
              width="200"
              trigger="hover">
              <span>{{item.name}}</span>
              <span slot="reference" >{{item.name}}</span>
            </el-popover>
          </td>
          <td>{{item.deliverDays}}</td>
          <td>{{item.arrivalDays}}</td>
          <td>{{item.itemNumber}}</td>
          <td>{{item.onShelfItemNum}}</td>
          <td>{{item.offShelfItemNum}}</td>
          <td>
            <span v-show="item.status">启用</span>
            <span v-show="!item.status">禁用</span>
          </td>
          <td>
            <a class="link" @click="editItem(item)">编辑</a>
            <a class="link" v-show="!item.status" @click="setStatus(item, 1)">启用</a>
            <a class="link" v-show="item.status" @click="setStatus(item, 0)">禁用</a>
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
      letter: ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','#'],
      currentLetter: '',
      statueList: [
        {id:1,name:'启用'},
        {id:0,name:'禁用'}
      ],
      tableData: [],
      total: -1,
      listParams: {
        page: 1,
        limit: 10,
        name: '',
        status: ''
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
      this.api.store.list(this.listParams).then((res) => {
        this.tableData = res.data.dataList;
        this.total = res.data.total;
      });
    },
    selectLetter(item) {
      if(this.currentLetter === item) {
        this.currentLetter = '';
      } else {
        this.currentLetter = item;
      }
      this.listParams.letter = this.copy(this.currentLetter);
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
          this.api.store.setStatus({id: item.id, status: status}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
      });
    },
    editItem(item) {
      let path = 'shop/detail/'+item.id;
      window.open(path, '_blank');
    }
  },
  components: {
    Pagination,
    noData
  }
}
</script>

<style lang="scss" scoped>
  .store-shop{
    background: #fff;
    .search-bar{
      .form-control, .btn-fill-main{
        margin-right: 20px;
      }
      .letter-wrap{
        display: inline-block;
        margin-right: 40px;
        user-select: none;
        .letter-item{
          display: inline-block;
          padding: 0 7px;
          cursor: pointer;
          &.active{
            color:$org-color;
          }
        }
      }
    }
    .table{
      .link{
        margin-right: 10px;
      }
    }
  }
</style>
