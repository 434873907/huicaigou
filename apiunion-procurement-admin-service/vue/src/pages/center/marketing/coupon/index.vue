<template>
  <div class="promotion">
    <div class="list-wrap">
      <div class="search-bar">
        <span class="search-bar-title">一般筛选:</span>
        <span class="normal-title">券名称</span>
        <input type="text" class="form-control" placeholder="请输入" v-model="listParams.keyWord" @keyup.enter="getList()" />
        <span class="normal-title">状态</span>
        <select class="form-control" v-model="listParams.couponStatus" :class="{'unset-select':!listParams.couponStatus}">
          <option value="">全部</option>
          <option v-for="item in statusList" :key="item.id" :value="item.id">{{item.name}}</option>
        </select>
        <a href="javascript:;" class="btn-fill-main" @click="getList()">搜索</a>
        <a href="javascript:;" class="btn-fill-main" @click="getList(true)">清空</a>
        <router-link class="btn-main" :to="{ name: '优惠券详情' }">新增优惠券</router-link>
      </div>
    </div>
    <table class="table table-text">
      <thead>
        <tr>
           <th class="table-w100">优惠劵ID</th>
          <th class="table-w180">优惠券名称</th>
          <th class="table-w100">状态</th>
          <th class="table-w100">发放数量</th>
          <th class="table-w120">优惠券类型</th>
          <th class="table-w100">已领取</th>
          <th class="table-w100">已使用</th>
          <th class="table-w180">有效时间</th>
          <th class="table-w180">创建时间</th>
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
              <span>{{item.couponName}}</span>
              <span slot="reference">{{item.couponName}}</span>
            </el-popover>
          </td>
          <td>{{statusName[item.couponStatus]}}</td>
          <td>{{item.couponCount}}</td>
          <td>{{item.couponType}}</td>
          <td>{{item.sendCouponCount}}</td>
          <td>{{item.usedCouponCount}}</td>
          <td>{{item.validateTime}}</td>
          <td>{{item.createTime}}</td>
          <td>
            <router-link class="link" :to="{ path: 'coupon/detail/' + item.id }" target="_blank">查看</router-link>
            <!-- <a class="link" @click="delCoupon(item)">删除</a> -->
            <template v-if="item.couponStatus == 0 || item.couponStatus == 1">
            <router-link class="link" :to="{ path: 'coupon/send-coupon/' + item.id+'/'+item.couponName }" >发劵</router-link>
            <a class="link" @click="invalidCoupon(item)">失效</a>
            </template>
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
  data() {
    return {
      statusList: [
        {name:'进行中', id:1},
        {name:'未开始', id:2},
        {name:'已结束', id:3},
        {name:'已失效', id:4},
      ],
      statusName: ['未开始', '进行中', '已结束', '已失效'],
      total: 0,
      tableData: [],
      listParams: {
        limit:10,
        page:1,
        keyWord:'',
        couponStatus:''
      }
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.getList();
    },
    getList(reload) {
      if(reload) {
        this.listParams = {
          limit:10,
          page:1,
          keyWord:'',
          couponStatus:''
        };
      }
      let params = this.copy(this.listParams);
      if(!params.couponStatus) {
        delete params.couponStatus;
      }
      this.api.coupon.list(params).then((res) => {
        this.tableData = res.data.dataList;
        this.total = res.data.total;
      });
    },
    delCoupon(item) {
      this.$msgbox({
        title: '',
        message: '确认删除？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.coupon.del({couponConfigId: item.id}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(true);
          });
        }
      });
    },
    // 失效
    invalidCoupon (item) {
      this.$msgbox({
        title: '温馨提示',
        message: '失效后，优惠劵将不再可放或领取，但已领取的优惠劵仍可继续使用，确认失效吗？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        center: true
      }).then(action => {
        if(action === 'confirm') {
           this.api.coupon.cancelCoupon({couponConfigId: item.id}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(true);
          });
        }
      });
    },
    goSendCoupon () {
      this.$router.push({
        path: '/center/marketing/coupon'
      })
    }
  },
  components: {
    'Pagination': Pagination,
    'noData': noData
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
      a{
        color: $link-color;
        margin-right: 12px;
      }
    }
  }
</style>