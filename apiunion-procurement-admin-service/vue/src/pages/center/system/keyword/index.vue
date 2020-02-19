<template>
  <div class="system-keyword">
    <div class="search-bar">
      <div class="form-wrap">
        <div class="form-name">状态：</div>
        <select class="form-control" v-model="listParams.status" :class="{'unset-select':listParams.status===''}">
          <option value="">请选择关键词状态</option>
          <option v-for="item in statusList" :key="item.id" :value="item.id">{{item.name}}</option>
        </select>
      </div>
      <div class="form-wrap">
        <div class="form-name">关键字：</div>
        <input type="text" class="form-control" placeholder="请输入关键字" v-model="listParams.name">
      </div>
      <a class="btn-fill-main" @click="getList()">搜索</a>
      <a class="btn-main" @click="addKeyword()">新增关键词</a>
      <div class="pull-right">
        <a class="btn-fill-main" @click="batchDel()">批量删除</a>
      </div>
    </div>
    <table class="table table-text">
      <thead>
        <tr>
          <th class="dot" :class="{'active':checkAll}" @click="selectAll()"></th>
          <th class="table-w120">关键词</th>
          <th class="table-w120">跳转类型</th>
          <th class="table-w180">跳转至</th>
          <th class="table-w80">状态</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in listData" :key="index+'list'" :class="{'selectedTr':item.checked}">
          <td class="dot" :class="{'active':item.checked}" @click="selectItem(item)"></td>
          <td>{{item.name}}</td>
          <td>{{item.typeName}}</td>
          <td>
            <el-popover class="ellipsis"
              placement="right"
              width="260"
              trigger="hover">
              <span>{{item.jumpValue}}</span>
              <span slot="reference">{{item.jumpValue}}</span>
            </el-popover>
          </td>
          <td>
            <span v-show="item.status">启用</span>
            <span v-show="!item.status">禁用</span>
          </td>
          <td>
            <a class="link" v-show="!item.status" @click="setStatue(item, 1)">启用</a>
            <a class="link" v-show="item.status" @click="setStatue(item, 0)">禁用</a>
            <a class="link" @click="editKeyword(item)">编辑</a>
            <a class="link" @click="delItem(item)">删除</a>
          </td>
        </tr>
      </tbody>
    </table>
    <Pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList()" />
    <noData v-if="total===0"></noData>
    <el-dialog :title="isEdit?'编辑关键词':'新增关键词'" :visible.sync="modalShow" width="450px">
      <div class="dialog-form">
        <div class="form-wrap">
          <div class="form-name">关键词名称</div>
          <input class="form-control" placeholder="请输入关键词名称" v-model="modalParams.name"/>
        </div>
        <div class="form-wrap">
          <div class="form-name">跳转类型</div>
          <select class="form-control" v-model="modalParams.jumpType" :class="{'unset-select':!modalParams.jumpType}">
            <option value="">请选择跳转类型</option>
            <option v-for="item in typeList" :key="item.code" :value="item.code">{{item.remark}}</option>
          </select>
        </div>
        <div class="form-wrap" v-if="modalParams.jumpType===24 || modalParams.jumpType===26">
          <div class="form-name">跳转链接</div>
          <input class="form-control" :placeholder="modalParams.jumpType===24?'请输入跳转网址':'请输入活动ID'" v-model="modalParams.jumpValue"/>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-org-main" @click="saveKeyword()">保存</button>
        <button class="btn-fill-grey-main" @click="modalShow = false">取消</button>
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
      modalShow: false,
      isEdit: false,
      checkAll: false,
      typeList: [],
      statusList: [
        {id:1,name:'启用'},
        {id:0,name:'禁用'}
      ],
      listParams: {
        page:1,
        limit:10,
        status:'',
        name:''
      },
      modalParams: {
        jumpType: ''
      },
      listData: [],
      total: -1
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      // 获取跳转类型列表
      this.api.hotKeyword.typeList({}).then((res) => {
        this.typeList = res.data;
      });
      this.getList();
    },
    getList() {
      this.api.hotKeyword.list(this.listParams).then((res) => {
        this.listData = res.data.dataList;
        this.total = res.data.total;
        this.listData.forEach(item => {
          this.typeList.forEach(type => {
            if(item.jumpType === type.code) {
              item.typeName = type.remark;
            }
          });
        });
      });
    },
    addKeyword() {
      this.modalParams = {
        jumpType:''
      };
      this.isEdit = false;
      this.modalShow = true;
    },
    editKeyword(item) {
      this.api.hotKeyword.detail({id: item.id}).then((res) => {
        this.modalParams = {
          id: res.data.id,
          name: res.data.name,
          jumpType: res.data.jumpType || '',
          jumpValue: res.data.jumpValue
        };
        this.isEdit = true;
        this.modalShow = true;
      });
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
          this.api.hotKeyword.del({id: item.id}).then((res) => {
            this.$message.success({message: res.message, showClose: true});
            this.getList();
          });
        }
      });
    },
    setStatue(item, status) {
      this.$msgbox({
        title: '',
        message: status === 1 ? '确认启用？' : '确认禁用？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.hotKeyword.setStatus({id: item.id, status: status}).then((res) => {
            this.$message.success({message: res.message, showClose: true});
            this.getList();
          });
        }
      });
    },
    saveKeyword() {
      let api = this.modalParams.id ? 'edit' : 'add';
      if(this.modalParams.jumpType === 8) {
        this.modalParams.jumpValue = '/';
      }
      this.api.hotKeyword[api](this.modalParams).then((res) => {
        this.$message.success({message: res.message, showClose: true});
        this.modalShow = false;
        this.getList();
      });
    },
    selectItem(item) {
      this.checkAll = true;
      item.checked = !item.checked;
      this.listData.forEach((item) => {
        if(!item.checked) {
          this.checkAll = false;
        }
      });
    },
    selectAll() {
      this.checkAll = !this.checkAll;
      this.listData.forEach((item) => {
        if(this.checkAll) {
          item.checked = true;
        } else {
          item.checked = false;
        }
      });
    },
    batchDel() {
      let selected = [];
      this.listData.forEach(item => {
        if(item.checked) {
          selected.push(item.id);
        }
      });
      if(selected.length === 0) {
        this.$message.info({message:'请至少选择一个关键词', showClose: true});
      } else {
        let ids = selected.join(',');
        this.$msgbox({
          title: '',
          message: '确认删除？',
          showCancelButton: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(action => {
          if(action === 'confirm') {
            this.api.hotKeyword.batchDel({ids: ids}).then((res) => {
              this.$message.success({message: res.message, showClose: true});
              this.getList();
            });
          }
        });
      }
    }
  },
  components: {
    Pagination,
    'noData': noData
  }
}
</script>

<style lang="scss" scoped>
  .system-keyword{
    background: #fff;
    .search-bar{
      .btn-main{
        margin-left: 20px;
      }
    }
    .table{
      .link{
        margin-right: 10px;
      }
    }
    .dialog-form{
      .form-wrap{
        .form-name{
          display: inline-block;
          vertical-align: top;
          width: 70px;
          line-height: 30px;
          text-align: left;
        }
        .form-control{
          width: 260px;
        }
      }
    }
  }
</style>
