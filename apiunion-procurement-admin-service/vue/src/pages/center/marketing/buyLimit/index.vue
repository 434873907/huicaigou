<template>
  <div class="buy-limit">
    <div class="list-wrap">
      <div class="search-bar">
        <div class="form-wrap">
          <div class="form-name">活动日期:</div>
          <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  size="small" placeholder="请选择" v-model="listParams.startTime"></el-date-picker>
          <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  size="small" placeholder="请选择" v-model="listParams.endTime"></el-date-picker>
        </div>
        <a href="javascript:;" class="btn-fill-main" @click="getList(1)">搜索</a>
        <router-link :to="{ name: '限时抢购详情'}" class="btn-main" target="_blank" >新增活动</router-link>
      </div>
    </div>
    <table class="table table-text">
      <thead>
        <tr>
          <th class="table-w120">活动ID</th>
          <th class="table-w340">活动时间</th>
          <th class="table-w120">商品数</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody v-for="(item,i) in tableData" :key="i">
        <tr>
          <td>{{item.activityId}}</td>
          <td>{{item.startTime}}至{{item.endTime}}</td>
          <td @click="item.showGoods=!item.showGoods" class="img-td">
            {{item.itemAmount}}
            <img src="~/assets/goods/buyLimitDown.png" v-show="!item.showGoods" />
            <img src="~/assets/goods/buyLimitUp.png" v-show="item.showGoods" />
          </td>
          <td>
            <a class="link" @click="editItem(item)">编辑</a>
            <a class="link" @click="deleteItem(item)">删除</a>
          </td>
        </tr>
        <tr class="goods-row" v-show="item.showGoods">
          <td colspan="4">
            <div class="goods-item" v-for="(goods, goods_index) in item.activityItems" :key="goods_index">
              <img :src="goods.itemImg" />
              <span class="goods-name goods-middle"><a>{{goods.itemTitle}}</a></span>
              <span class="goods-spec goods-middle"><a>{{goods.spec}}</a></span>
              <span class="goods-price goods-middle"><a>抢购价:{{goods.flashSalePrice}}</a></span>
              <span class="goods-num goods-middle"><a>限购数量:{{goods.num}}</a></span>
              <a class="link goods-middle" v-show="goods_index!=0" @click="moveGoods(item, goods, goods_index, 1)">上移</a>
              <a class="link goods-middle" v-show="goods_index!=item.activityItems.length-1" @click="moveGoods(item, goods, goods_index, 0)">下移</a>
            </div>
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
  components: { Pagination, noData },
  data() {
    return {
      total: 0,
      tableData: [],
      listParams: {
        page: 1,
        limit: 20,
        startTime: '',
        endTime: ''
      }
    }
  },
  created() {
    this.getList(1);
  },
  methods: {
    getList(page) {
      this.listParams.page = page;
      this.api.activity.buyLimit(this.listParams).then((res) => {
        this.tableData = res.data.dataList;
        this.tableData.forEach(item => {
          if(!item.showGoods) {
            this.$set(item, 'showGoods', false);
          }
        });
        this.total = res.data.total;
      });
    },
    editItem(item){
      let href = this.$router.resolve({ path:'buyLimit/detail/' + item.activityId});
      window.open(href.href, '_blank');
    },
    moveGoods(item, goods, goods_index, direction) {
      let params = {
        activityId: item.activityId,
        itemId: goods.itemId,
        index: goods_index,
        direction: direction
      };
      this.api.activity.moveBuyLimit(params).then((res) => {
        this.$message.success({message: res.message, showClose: true});
        this.getList(1);
      });
    },
    deleteItem(item) {
      this.$msgbox({
        title: '',
        message: '确认删除？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.activity.delBuyLimit({activityId: item.activityId}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
      });
    }
  }
}
</script>

<style lang="scss" scoped>
  .buy-limit{
    background: #fff;
    .list-wrap{
      .timepicker-control{
        margin-right: 10px;
      }
      .btn-main{
        margin-left: 25px;
      }
    }
    .table{
      .img-td{
        cursor: pointer;
        img{
          display: inline-block;
          vertical-align: top;
          margin-left: 5px;
          margin-top: 3px;
        }
      }
      .goods-row{
        background: #F5F8FC;
        td{
          padding: 0;
          .goods-item{
            padding: 10px 10px 0;
            img{
              display: inline-block;
              vertical-align: top;
              max-height: 54px;
              max-width: 54px;
            }
            span{
              display: inline-block;
              vertical-align: top;
              white-space: normal;
              word-break: break-all;
            }
            .goods-name{
              font-size: 14px;
              line-height: 20px;
              width: 320px;
              padding-left: 10px;
              height: 54px;
            }
            .goods-spec{
              margin-left: 40px;
              width: 140px;
              height: 54px;
            }
            .goods-price{
              padding-left: 10px;
              width: 130px;
              height: 54px;
            }
            .goods-num{
              padding-left: 10px;
              width: 130px;
              height: 54px;
            }
            &:last-child{
              padding-bottom: 10px;
            }
            .goods-middle{
              vertical-align: middle;
              a{
                display: flex;
                height: 54px;
                align-items: center;
              }
            }
          }
        }
      }
      .link{
        margin-right: 10px;
      }
    }
  }
</style>
