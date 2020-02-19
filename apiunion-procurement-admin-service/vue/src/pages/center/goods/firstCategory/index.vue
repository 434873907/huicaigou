<template>
  <div class="center-first-category">
    <div class="search-bar">
      <div class="search-bar-title">类目筛选：</div>
      <div class="form-wrap">
        <div class="form-name">类目名</div>
        <input type="text" class="form-control" v-model="listParams.name" @keyup.enter="getList(1)">
      </div>
      <div class="btn-fill-main" @click="getList(1)">搜索</div>
      <div class="btn-main pull-right" @click="openAddModal">新增一级类目</div>
    </div>
    <table class="table">
        <thead>
          <tr>
            <th class="table-w80">类目ID</th>
            <th class="table-w120">一级类目</th>
            <th class="table-w120">类目图标</th>
            <th class="table-w120">二级类目数</th>
            <th class="table-w180">二级类目</th>
            <th class="table-w80">状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in listData" :key="index+'list'">
          <td>{{item.id}}</td>
          <td>
            <el-popover
              placement="top"
              width="260"
              trigger="hover">
              <span>{{item.name}}</span>
              <span slot="reference" class="ellipsis">{{item.name}}</span>
            </el-popover>
          </td>
          <td>
            <el-popover
              placement="top"
              width="200"
              trigger="hover" class="imgBoxCont">
              <img style="width:100%;height:100%" :src="item.logoUrl">
              <img  class="ellipsis imgBox" slot="reference" :src="item.logoUrl" />
            </el-popover>
          </td>
          <td>{{item.childrenNum}}</td>
          <td>
            <el-popover
              placement="top"
              width="260"
              trigger="hover">
              <span>{{item.children}}</span>
              <span class="ellipsis" slot="reference">{{item.children}}</span>
            </el-popover>
          </td>
          <td>
            <span class="active-status" v-show="item.status">启用</span>
            <span class="disable-status" v-show="!item.status">禁用</span>
          </td>
          <td>
            <a class="link" @click="openAddModal(item)">编辑</a>
            <a class="link" @click="removeItem(item)">删除</a>
            <a class="link" @click="setStatus(item, 1)" v-show="!item.status">启用</a>
            <a class="link" @click="setStatus(item, 0)" v-show="item.status">禁用</a>
          </td>
        </tr>
        </tbody>
    </table>
    <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
    <noData v-if="total===0"></noData>
    <el-dialog :title="modalParams.id?'编辑一级类目':'新增一级类目'" :visible.sync="addShow" width="450px">
      <div class="dialog-form">
        <div class="form-wrap">
          <div class="form-name">类目名称</div>
          <input class="form-control" v-model="modalParams.name" maxlength="10" placeholder="最多10个字符">
        </div>
        <div class="form-wrap upload-wrap">
          <div class="form-name">类目图标</div>
          <img class="cateImg" :src="modalParams.logoUrl" />
          <a href="javascript:;" class="addCateImg">
            <upload uploadType='2' v-on:uploadSuccess="uploadSuccess">
              {{modalParams.logoUrl?'更改图标':'点击上传图标'}}
            </upload>
          </a>
        </div>
        <div class="form-wrap upload-wrap">
          <div style="margin-left: 13px"><span style="font-size: 14px;color: #999999">(尺寸建议为100x100px，大小不超过1M)</span></div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-org-main" @click="saveCate()">保存</button>
        <button class="btn-fill-grey-main" @click="addShow = false">取消</button>
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
      addShow:false,
      total: -1,
      listParams: {
        depth:1,
        page:1,
        limit:10
      },
      modalParams: {
        name: '',
        logoUrl: ''
      },
      listData: []
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
      // if(reload){
      //   this.listParams = {
      //     depth:1,
      //     page:1,
      //     limit:10
      //   }
      // }
      this.listParams.page = page;
      this.api.category.cate(this.listParams).then((res) => {
        this.listData = res.data.dataList;
        this.total = res.data.total;
      });
    },
    removeItem(item) {
      this.$msgbox({
        title: '',
        message: '确认删除？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.category.del({id: item.id}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
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
          this.api.category.setStatus({id: item.id, status: status}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
      });
    },
    openAddModal(item) {
      this.modalParams.parentId = 0;
      this.modalParams.depth = 1;
      if(item.id){
        this.modalParams.id = item.id;
        this.modalParams.name = item.name || '';
        this.modalParams.logoUrl = item.logoUrl || '';
      } else {
        this.modalParams = {
          parentId: 0,
          depth: 1,
          name: '',
          logoUrl: ''
        };
      }
      this.addShow = true;
    },
    saveCate() {
      if(!this.modalParams.name) {
        this.$message.info({message:'请输入类目名称', showClose:true});
        return
      }
      if(!this.modalParams.logoUrl) {
        this.$message.info({message:'请上传图标', showClose:true});
        return
      }
      if(this.modalParams.id){
        this.api.category.edit(this.modalParams).then((res) => {
          this.$message.success({message:res.message, showClose:true});
          this.addShow = false;
          this.getList(1);
        });
      } else {
        this.api.category.add(this.modalParams).then((res) => {
          this.$message.success({message:res.message, showClose:true});
          this.addShow = false;
          this.getList(1);
        });
      }
    },
    uploadSuccess(res) {
      this.modalParams.logoUrl = res.data;
    }
  },
  components: { Pagination, noData }
}
</script>
<style scoped lang='scss'>
  .center-first-category{
    background: $white-color;
    .table{
      .link{
        margin-right: 20px;
      }
      .ellipsis{
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        max-width: 100%;
      }
    }
    .dialog-form{
      padding-left:10px;
      .form-name{
        vertical-align: top;
        line-height: 30px;
      }
      .upload-wrap{
        .cateImg{
          max-width: 100px;
          max-height: 100px;
        }
        .addCateImg{
          display: inline-block;
          vertical-align: top;
          line-height: 30px;
          margin-left: 10px;
        }

      }
      .form-control{
        width: 300px;
      }
    }
    .btn-org-main{
      background: #E1376C;
    }
  }
</style>
