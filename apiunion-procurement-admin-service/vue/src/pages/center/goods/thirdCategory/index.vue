<template>
  <div class="center-third-category">
       <div class="search-bar">
        <div class="search-bar-title">类目筛选：</div>
        <div class="form-wrap">
          <div class="form-name">一级类目</div>
          <select class="form-control" v-model="listParams.firstCategoryId" :class="{'unset-select':!listParams.firstCategoryId}">
            <option value="">请选择</option>
            <option v-for="(item, index) in firstCateList" :value="item.id" :key="index+'first'">{{item.name}}</option>
          </select>
        </div>
        <div class="form-wrap">
          <div class="form-name">二级类目</div>
          <select class="form-control" v-model="listParams.secondCategoryId" :class="{'unset-select':!listParams.secondCategoryId}">
            <option value="">请选择</option>
            <option v-for="(item, index) in secondCateList" :value="item.id" :key="index+'second'">{{item.name}}</option>
          </select>
        </div>
        <div class="form-wrap">
          <div class="form-name">类目名称</div>
          <input type="text" class="form-control" v-model="listParams.name" @keyup.enter="getList(1)">
        </div>
        <div class="btn-fill-main" @click="getList(1)">搜索</div>
        <div class="btn-main pull-right" @click="openAddModal()">新增三级类目</div>
      </div>
      <table class="table">
         <thead>
           <tr>
             <th class="table-w80">类目ID</th>
             <th class="table-w120">三级类目名称</th>
             <th class="table-w120">类目图标</th>
             <th class="table-w160">层级</th>
             <th class="table-w80">状态</th>
             <th>操作</th>
           </tr>
         </thead>
         <tbody>
          <tr v-for="(item, index) in listData" :key="index+'list'">
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
                width="200"
                trigger="hover">
                <img style="width:100%;height:100%" :src="item.logoUrl">
                <img slot="reference" :src="item.logoUrl" />
              </el-popover>
            </td>
            <td>{{item.hierarchy}}</td>
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
      <el-dialog title="新增三级类目" :visible.sync="addShow" width="576px">
        <div class="dialog-form">
          <div class="form-wrap">
            <div class="form-name">类目名称</div>
            <input class="form-control" v-model="modalParams.name" maxlength="10" placeholder="最多10个字符"/>
          </div>
          <div class="form-wrap">
            <div class="form-name">从属一级类目</div>
            <select class="form-control" v-model="modalFirst" :class="{'unset-select':!modalFirst}">
              <option value="">请选择</option>
              <option v-for="(item, index) in firstCateList" :value="item.id" :key="index+'first'">{{item.name}}</option>
            </select>
          </div>
          <div class="form-wrap">
            <div class="form-name">从属二级类目</div>
            <select class="form-control" v-model="modalParams.parentId" :class="{'unset-select':!modalParams.parentId}">
              <option value="">请选择</option>
              <option v-for="(item, index) in secondCateModalList" :value="item.id" :key="index+'second'">{{item.name}}</option>
            </select>
          </div>
          <div class="form-wrap upload-wrap">
            <div class="form-name">类目图标</div>
            <!-- <a href="javascript:;" class="upload-img">点击上传图标</a> -->
            <img class="cateImg" :src="modalParams.logoUrl" />
            <a href="javascript:;" class="addCateImg">
              <upload uploadType='2' v-on:uploadSuccess="uploadSuccess">
                {{modalParams.logoUrl?'更改图标':'点击上传图标'}}
              </upload>
            </a>
          </div>
            <div class="form-wrap upload-wrap">
                <div style="margin-left: 30px"><span style="font-size: 14px;color: #999999">(尺寸建议为100x100px，大小不超过1M)</span></div>
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
      firstCateList: [],
      secondCateList: [],
      modalFirst: '',
      firstCateModalList: [],
      secondCateModalList: [],
      total: -1,
      listParams: {
        firstCategoryId:'',
        secondCategoryId:'',
        name:'',
        page:1,
        limit:10
      },
      modalParams: {
        name: '',
        depth: 3,
        parentId: '',
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
      this.getFirstList();
    },
    getList(page) {
      // if(reload){
      //   this.listParams = {
      //     firstCategoryId:'',
      //     secondCategoryId:'',
      //     name:'',
      //     page:1,
      //     limit:10
      //   }
      // }
      this.listParams.page = page;
      this.api.category.thirdCate(this.listParams).then((res) => {
        this.console(res);
        this.listData = res.data.dataList;
        this.total = res.data.total;
      });
    },
    getFirstList() {
      this.api.category.categoryNode({parentId:0}).then((res) => {
        this.firstCateList = res.data;
        this.listParams.firstCategoryId = '';
      });
    },
    openAddModal(item) {
      this.modalParams.depth = 3;
      if(item && item.id){
        this.modalParams.id = item.id;
        this.modalParams.name = item.name;
        this.modalParams.logoUrl = item.logoUrl || '';
        this.api.category.detail({id: item.id}).then((res) => {
          let cate1 = res.data.firstClassCategoryId;
          let cate2 = res.data.secondClassCategoryId;
          this.modalFirst = cate1;
          this.api.category.categoryNode({parentId: this.modalFirst}).then((res) => {
            this.secondCateModalList = res.data;
            this.modalParams.parentId = cate2;
          });
        });
      } else {
        this.modalFirst = '';
        this.secondCateModalList = [];
        this.modalParams.parentId = '';
        this.modalParams.name = '';
        delete this.modalParams.id;
      }
      this.addShow = true;
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
    saveCate() {
      if(!this.modalParams.name) {
        this.$message.info({message:'请输入类目名称', showClose:true});
        return
      }
      if(!this.modalFirst) {
        this.$message.info({message:'请选择从属一级类目', showClose:true});
        return
      }
      if(!this.modalParams.parentId) {
        this.$message.info({message:'请选择从属二级类目', showClose:true});
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
  components: { Pagination, noData },
  watch: {
    'listParams.firstCategoryId': function(val){
      if(!val){
          return
      }
      this.api.category.categoryNode({parentId:val}).then((res) => {
        this.secondCateList = res.data;
        this.listParams.parentId = '';
      });
    },
    'modalFirst': function(val){
      if(!val){
          return
      }
      this.api.category.categoryNode({parentId:val}).then((res) => {
        this.secondCateModalList = res.data;
        this.modalParams.parentId = '';
      });
    }
  }
}
</script>
<style lang='scss'>
  .center-third-category{
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
    .el-dialog__body .dialog-form{
      padding-left:20px;
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
      .upload-img{
        display: inline-block;
        vertical-align: top;
        width: 100px;
        height: 30px;
        color: #F06941;
        border: 1px dashed #F06941;
        text-align: center;
        line-height: 30px;
        border-radius: 15px;
      }
      .form-name{
        width: 88px;
        line-height: 30px;
        vertical-align: top;
      }
      .form-control{
        width: 380px;
      }
      .form-group{
        width: 380px;
      }
    }
    .btn-org-main{
      background: #E1376C;
    }
  }
</style>
