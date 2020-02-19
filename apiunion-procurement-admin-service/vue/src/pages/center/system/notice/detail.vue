<template>
  <div class="notice-detail">
    <div class="operateBox">
      <h2 class="big-title">操作</h2>
      <div class="save-inner">
        <a href="javascript:;" class="btn-main" @click="saveNotice()">保存</a>
        <router-link :to="{ name: '系统通知'}" class="btn-fill-main">返回</router-link>
      </div>
    </div>
    <h2 class="big-title">公告详情</h2>
    <div class="wrap">
      <div class="form-wrap">
        <div class="form-name">公告标题</div>
        <input class="form-control long-input" placeholder="请输入公告标题" v-model="noticeParams.title" />
      </div>
      <div class="form-wrap">
        <div class="form-name">公告内容</div>
        <textarea class="form-control long-textarea" placeholder="请输入公告内容" v-model="noticeParams.content"></textarea>
      </div>
      <div class="form-wrap">
        <div class="form-name">跳转方式</div>
        <select class="form-control type-select" v-model="noticeParams.type" :class="{'unset-select':!noticeParams.type}">
          <option value="">请选择跳转方式</option>
          <option v-for="item in typeList" :key="item.code" :value="item.code">{{item.remark}}</option>
        </select>
      </div>
      <div class="form-wrap" v-show="noticeParams.type==24">
        <div class="form-name">目标网页</div>
<!--        <input type="text" style="width: 474px" class="form-control" v-model.trim="noticeParams.url" placeholder="请输入搜索词"  @blur="regKeys = true" @focus="regKeys = false"/>-->
        <el-input class="myInput" style="width: 426px" placeholder="请输入目标网页" v-model="noticeParams.url">
          <template slot="prepend">http://</template>
        </el-input>
      </div>
<!--      <div class="form-wrap" v-show="noticeParams.type">-->
<!--        <div class="form-name" v-for="item in typeList" :key="item.code" v-show="noticeParams.type === item.code">{{item.remark}}</div>-->
<!--        <input class="form-control type-select" placeholder="请输入" v-model="noticeParams.url" />-->
<!--      </div>-->
      <div class="form-wrap">
        <div class="form-name">发布时间</div>
        <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择" v-model="noticeParams.issueTime"></el-date-picker>
        <!-- <span class="time-mid">－</span>
        <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择" v-model="noticeParams.endTime"></el-date-picker> -->
      </div>
      <div class="form-wrap">
        <div class="form-name">失效时间</div>
        <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择" v-model="noticeParams.invalidTime"></el-date-picker>
      </div>
      <div class="form-wrap" style="margin-bottom: 0">
        <div class="form-name">是否启用</div>
        <el-radio v-model="noticeParams.status" :label="1">启用</el-radio>
        <el-radio v-model="noticeParams.status" :label="0">禁用</el-radio>
      </div>
    </div>
    <div class="save-box">
      <h2 class="title">操作</h2>
      <div class="save-inner">
        <a href="javascript:;" class="btn-main" @click="saveNotice()">保存</a>
        <router-link :to="{ name: '系统通知'}" class="btn-fill-main">返回</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: '',
      typeList: [{code: 24, remark: "网页"}],
      noticeParams: {
        issueTime: '',
        status: 1,
        type: '',
        invalidTime:"",
        // url:"https://"
        url:""
      }
    }
  },
  created() {
    this.id = this.$route.params.id || '';
    this.init();
    if(this.id) {
      this.getDetail(this.id);
    }
  },
  methods: {
    init() {
      // this.getType();
    },
    getDetail(id) {
      this.api.notice.detail({id: id}).then((res) => {
        this.noticeParams = res.data;
      });
    },
    getType() {
      this.api.notice.type().then((res) => {
        this.typeList = res.data;
      });
    },
    saveNotice() {
      if(this.noticeParams.issueTime>this.noticeParams.invalidTime){
        this.$message.info('失效时间不能大于发布时间')
        return false
      }

      if(this.id) {
        this.noticeParams.id = this.id;
        if(!!this.noticeParams.url){
          this.noticeParams.url = this.noticeParams.url.indexOf('http')>-1?this.noticeParams.url:'https://'+this.noticeParams.url
        }
        this.api.notice.edit(this.noticeParams).then((res) => {
          this.$message.success({message:res.message, showClose:true});
          this.$router.push({ name: '系统通知' });
        });
      } else {
        if(!!this.noticeParams.url){
          this.noticeParams.url = this.noticeParams.url.indexOf('http')>-1?this.noticeParams.url:'https://'+this.noticeParams.url
        }
        this.api.notice.add(this.noticeParams).then((res) => {
          this.$message.success({message:res.message, showClose:true});
          this.$router.push({ name: '系统通知' });
        });
      }
    }
  }
}
</script>

<style lang="scss">
  .notice-detail{
    // padding-right: 260px;
    position: relative;
    .save-box{
      position: fixed;
      top: 56px;
      right: 15px;
      .title{
        font-size: 14px;
        font-weight: bold;
        color: #333333;
        padding: 10px 0;
      }
      .save-inner{
        background: #fff;
        padding: 20px 30px;
        border-radius: 4px;
        .btn-main{
            margin-left: 10px;
        }
      }
    }
    .big-title{
      color: #333333;
      font-size: 14px;
      font-weight: bold;
      padding-bottom: 10px;
    }
    .wrap{
      background: #fff;
      margin-bottom: 20px;
      padding: 20px 20px;
      background: #fff;
      box-shadow: 0 2px 3px #E5E5E5;
      border-radius: 4px;
      // width:calc(100% - 26px);
      .form-wrap{
        margin-bottom: 15px;
        .long-input{
          width: 474px;
        }
        .long-textarea{
          width: 448px;
          height: 60px;
        }
        .type-select{
          width: 474px;
        }
        .form-name{
          vertical-align: top;
          line-height: 30px;
          width: 70px;
        }
        .time-mid{
          padding: 0 10px;
        }
        .el-input__inner{
          height: 30px;
          width: 474px;
        }
        .el-input__icon, .el-radio__label{
          line-height: 30px;
        }
      }
    }
  }
  .el-input{
    width: auto;
  }
  .btn-fill-main{
    margin-right: 10px;
  }
</style>
<style lang="scss">
  .el-input-group__append, .el-input-group__prepend{
    padding: 0 10px;
    color: #666666;
  }
  .el-radio+.el-radio{
    margin-left: 20px;
  }
  .myInput{
    .el-input__inner{
      width: 410px !important;
    }
  }
</style>
