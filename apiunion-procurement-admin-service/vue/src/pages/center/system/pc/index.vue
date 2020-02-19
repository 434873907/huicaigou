<template>
  <div class="center-pc">
    <div class="tab-bar">
      <div class="tab-item" :class="{'active':tab === 1}" @click="changeTab(1)">轮播</div>
      <div class="tab-item" :class="{'active':tab === 2}" @click="changeTab(2)">顶部tab</div>
    </div>
    <div class="search-bar">
      <a class="btn-fill-main" v-show="!isMove" @click="startMove()">调整顺序</a>
      <a class="btn-main saveMove" v-show="isMove" @click="saveMove()">保存顺序</a>
      <a class="btn-fill-main" v-show="isMove" @click="cancelMove()">取消</a>
      <a class="btn-main" @click="bannerDetail()" v-show="tab === 1 && (!isMove)">新增</a>
      <a class="btn-main" @click="tabDetail()" v-show="tab === 2 && (!isMove)">新增</a>
    </div>
    <table class="table" v-show="tab === 1">
      <thead>
        <tr>
          <th class="table-w240">图片</th>
          <th class="table-w140">标题</th>
          <th class="table-w180">跳转类型</th>
          <th class="table-w180">跳转目标</th>
          <th class="table-w330">起止时间</th>
          <th class="table-w80">状态</th>
          <th class="table-w180">操作</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in tableData" :key="item.id">
          <td>
            <el-popover
              placement="top"
              width="260"
              trigger="hover">
              <img style="width:100%;height:100%" :src="item.imageUrl">
              <img slot="reference" :src="item.imageUrl" class="banner" />
            </el-popover>
          </td>
          <td>
            <el-popover
              placement="top"
              width="260"
              trigger="hover">
              <span>{{item.title}}</span>
              <span slot="reference" class="ellipsis">{{item.title}}</span>
            </el-popover>
          </td>
          <td>{{getTypeName(item.jumpType)}}</td>
          <td>
            <!-- <el-popover
              placement="top"
              width="260"
              trigger="hover">
              <p v-show="item.conditionList && item.conditionList.length>0" v-for="condition in item.conditionList" :key="condition">{{condition}}</p>
              <span v-show="!item.conditionList || item.conditionList.length===0">{{item.jumpStr}}</span>
              <span slot="reference" class="ellipsis">{{item.jumpStr}}</span>
            </el-popover> -->
            <el-popover
              placement="top"
              width="260"
              trigger="hover">
              <span>{{item.jumpStr}}</span>
              <span slot="reference" class="ellipsis">{{item.jumpStr}}</span>
            </el-popover>
          </td>
          <td>
            {{item.startTime}} ~ {{item.endTime}}
          </td>
          <td>
            {{item.statusStr}}
          </td>
          <td v-show="!isMove">
            <a class="link" v-show="!item.status" @click="setStatus(item, 1)">启用</a>
            <a class="link" v-show="item.status" @click="setStatus(item, 0)">禁用</a>
            <a class="link" @click="editItem(item)">编辑</a>
            <a class="link" @click="delItem(item)">删除</a>
          </td>
          <td v-show="isMove">
            <a class="link" v-show="index!=0" @click="moveBanner(index)">上移</a>
            <a class="link" v-show="index!=tableData.length-1" @click="moveBanner(index, 1)">下移</a>
          </td>
          <td></td>
        </tr>
      </tbody>
    </table>
    <table class="table" v-show="tab === 2">
      <thead>
        <tr>
          <th class="table-w180">标题</th>
          <th class="table-w340">匹配条件</th>
          <th class="table-w330">起止时间</th>
          <th class="table-w80">状态</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in tabData" :key="item.id">
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
              <p v-for="(condition, condition_index) in item.conditionList" :key="condition_index+'condition_hover'">{{condition}}</p>
              <div slot="reference"><span v-for="condition in item.conditionList" class="ellipsis" :key="condition+'condition'">{{condition}}</span></div>
            </el-popover>
          </td>
          <td>
            {{item.startTime}} ~ {{item.endTime}}
          </td>
          <td>
            {{item.statusStr}}
          </td>
          <td v-show="!isMove">
            <a class="link" v-show="!item.status" @click="setTabStatus(item, 1)">启用</a>
            <a class="link" v-show="item.status" @click="setTabStatus(item, 0)">禁用</a>
            <a class="link" @click="tabDetail(item)">编辑</a>
            <a class="link" @click="delTabItem(item)">删除</a>
          </td>
          <td v-show="isMove">
            <a class="link" v-show="index!=0" @click="moveTab(index)">上移</a>
            <a class="link" v-show="index!=tabData.length-1" @click="moveTab(index, 1)">下移</a>
          </td>
        </tr>
      </tbody>
    </table>
    <el-dialog :title="modalParams.id?'编辑轮播':'新增轮播'" :visible.sync="showModal" width="600px">
      <div class="dialog-form">
        <div class="form-wrap">
          <div class="form-name">标题:</div>
          <input class="form-control" v-model="modalParams.title" placeholder="请输入标题">
        </div>
        <div class="form-wrap">
          <div class="form-name">跳转至:</div>
          <select class="form-control jump-to" v-model="modalParams.jump.jumpType">
            <option v-for="item in jumpTypeList" :key="item.id" :value="item.id">{{item.name}}</option>
          </select>
          <input type="text" class="form-control jump-content" v-model="modalParams.jump.jumpData" />
        </div>
        <div class="form-wrap upload-wrap">
          <div class="form-name">图片上传:</div>
          <img class="cateImg" :src="modalParams.imageUrl" />
          <a href="javascript:;" class="addCateImg">
            <upload uploadType='2' v-on:uploadSuccess="uploadSuccess">
              {{modalParams.imageUrl?'更改图片':'点击上传图片'}}
            </upload>
          </a>
        </div>
        <div class="form-wrap">
          <div class="form-name">起止时间:</div>
          <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择" v-model="modalParams.startTime"></el-date-picker>
          <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择" v-model="modalParams.endTime"></el-date-picker>
        </div>
        <div class="form-wrap">
          <div class="form-name">是否启用:</div>
          <el-radio v-model="modalParams.status" :label="1">启用</el-radio>
          <el-radio v-model="modalParams.status" :label="0">禁用</el-radio>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-org-main" @click="saveBanner()">保存</button>
        <button class="btn-fill-grey-main" @click="showModal = false">取消</button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tab: 1,
      tableData: [],
      tabData: [],
      showModal: false,
      isMove: false,
      jumpTypeList: [
        { id: 26, name: '活动'},
        { id: 24, name: '链接'},
        { id: 31, name: '组合条件'},
        { id: 8, name: '搜索'},
        { id: 32, name: '顶部Tab'}
      ],
      modalParams: {
        title: '',
        imageUrl: '',
        jump: {
          jumpType: '',
          jumpData: ''
        },
        startTime: '',
        endTime: '',
        status: 1
      }
    }
  },
  created() {
    this.getList();
    this.getTabList();
    if (localStorage.getItem('tabDetail')) {
      this.changeTab(2)
      localStorage.removeItem('tabDetail')
    }
  },
  methods: {
    getTypeName (type) {
      const {jumpTypeList} = this
      let str = ''
      for (let k =0; k< jumpTypeList.length; k++) {
        if (jumpTypeList[k].id == type) {
          str = jumpTypeList[k].name
          break
        }
      }
      return str
    },
    changeTab(tab) {
      this.tab = tab;
      this.cancelMove();
    },
    getList() {
      this.api.system.bannerList({}).then((res) => {
        this.tableData = res.data;
      });
    },
    startMove() {
      this.isMove = true;
    },
    saveMove() {
      let ids = [];
      if(this.tab == 1) {
        this.tableData.forEach(item => {
          ids.push(item.id);
        });
        ids = JSON.stringify(ids);
        this.api.system.moveBanner({ids: ids}).then((res) => {
          this.$message.success({message: res.message, showClose: true});
          this.isMove = false;
          this.getList();
        });
      } else {
        this.tabData.forEach(item => {
          ids.push(item.id);
        });
        ids = JSON.stringify(ids);
        this.api.system.moveTab({ids: ids}).then((res) => {
          this.$message.success({message: res.message, showClose: true});
          this.isMove = false;
          this.getTabList();
        });
      }
    },
    cancelMove() {
      this.isMove = false;
      if(this.tab == 1) {
        this.getList();
      } else {
        this.getTabList();
      }
    },
    moveBanner(index, down) {
      let current = this.copy(this.tableData[index]);
      let pos = index + (down ? 1 : -1);
      let bot = this.copy(this.tableData[pos]);
      this.$set(this.tableData, index, bot);
      this.$set(this.tableData, pos, current);
    },
    moveTab(index, down) {
      let current = this.copy(this.tabData[index]);
      let pos = index + (down ? 1 : -1);
      let bot = this.copy(this.tabData[pos]);
      this.$set(this.tabData, index, bot);
      this.$set(this.tabData, pos, current);
    },
    getTabList() {
      this.api.system.tabList({}).then((res) => {
        this.tabData = res.data;
      });
    },
    bannerDetail(item) {
      let href = '';
      if(item && item.id){
        href = this.$router.resolve({ path: 'pc/bannerDetail/' + item.id});
      } else {
        href = this.$router.resolve({ name: 'banner详情' });
      }
      window.open(href.href, '_blank');
      // this.modalParams = {
      //   title: '',
      //   imageUrl: '',
      //   jump: {
      //     jumpType: '',
      //     jumpData: ''
      //   },
      //   startTime: '',
      //   endTime: '',
      //   status: 1
      // };
      // this.showModal = true;
    },
    tabDetail(item) {
      let href = '';
      if(item && item.id){
        href = this.$router.resolve({ path: 'pc/tabDetail/' + item.id});
      } else {
        href = this.$router.resolve({ name: 'tab详情' });
      }
      window.open(href.href, '_blank');
    },
    setStatus(item, status) {
      this.$msgbox({
        title: '',
        message: status ? '确认启用?' : '确认禁用?',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.system.setBannerStatus({id: item.id, status: status}).then((res) => {
            this.$message.success({message: res.message, showClose: true});
            this.getList();
          });
        }
      });
    },
    setTabStatus(item, status) {
      this.$msgbox({
        title: '',
        message: status ? '确认启用?' : '确认禁用?',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.system.setTabStatus({id: item.id, status: status}).then((res) => {
            this.$message.success({message: res.message, showClose: true});
            this.getTabList();
          });
        }
      });
    },
    editItem(item) {
      this.bannerDetail(item);
      // this.api.system.bannerDetail({id: item.id}).then((res) => {
      //   this.modalParams = res.data;
      //   this.showModal = true;
      // });
    },
    delItem(item) {
      this.$msgbox({
        title: '',
        message: '确认删除?',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.system.delBanner({id: item.id}).then((res) => {
            this.$message.success({message: res.message, showClose: true});
            this.getList();
          });
        }
      });
    },
    delTabItem(item) {
      this.$msgbox({
        title: '',
        message: '确认删除?',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.system.delTab({id: item.id}).then((res) => {
            this.$message.success({message: res.message, showClose: true});
            this.getTabList();
          });
        }
      });
    },
    uploadSuccess(res) {
      this.modalParams.imageUrl = res.data;
    },
    saveBanner() {
      let params = this.copy(this.modalParams);
      params.jump = JSON.stringify(params.jump);
      if(this.modalParams.id) {
        this.api.system.editBanner(params).then((res) => {
          this.$message.success({message: res.message, showClose: true});
          this.showModal = false;
          this.getList();
        });
      } else {
        this.api.system.addBanner(params).then((res) => {
          this.$message.success({message: res.message, showClose: true});
          this.showModal = false;
          this.getList();
        });
      }
    }
  }
}
</script>
<style lang='scss'>
  .center-pc{
    .search-bar{
      background: #fff;
      .btn-main{
        margin-left: 20px;
      }
      .saveMove{
        margin-left: 0;
        margin-right: 10px;
      }
    }
    .table{
      background: #fff;
      .link{
        margin-right: 10px;
      }
      .banner{
        max-width: 200px;
      }
      tbody{
        tr{
          background: #fff;
          td{
            border-bottom: 1px solid $border-color;
          }
        }
      }
    }
    .dialog-form{
      .form-name{
        display: inline-block;
        vertical-align: top;
        line-height: 30px;
        width: 70px;
      }
      .el-radio{
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
        width: 400px;
      }
      .jump-to{
        width: 190px;
        margin-right: 20px;
      }
      .jump-content{
        width: 190px;
      }
      .timepicker-control{
        width: 190px;
        margin-right: 20px;
        .el-input__inner{
          height: 30px;
          line-height: 30px;
        }
        .el-input__icon{
          line-height: 30px;
        }
      }
    }
  }
</style>
