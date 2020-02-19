<template>
  <div class="level-detail">
    <h2 class="title">{{id ? '编辑' : '新增'}}会员等级</h2>
    <div class="level-wrap">
      <div class="level-item">
        <span class="level-title">会员等级名称</span>
        <input type="text" class="form-control" v-model="levelParams.ruleName" />
      </div>
      <!-- <div class="level-item">
        <span class="level-title">条件类型</span>
        <select class="form-control" v-model="levelParams.type">
          <option v-for="item in typeList" :key="item.id" :value="item.id">{{item.name}}</option>
        </select>
      </div> -->
      <div class="level-item">
        <span class="level-title">时间类型</span>
        <input type="text" class="form-control stort-input" v-show="levelParams.timeIntervalType!=0" v-model="levelParams.timeInterval" placeholder="请输入限制的数量" />
        <select class="form-control stort-input" v-model="levelParams.timeIntervalType">
          <option v-for="item in timeList" :key="item.id" :value="item.id">{{item.name}}</option>
        </select>
      </div>
      <div class="level-item">
        <span class="level-title">等级设置</span>
        <div class="level-box">
          <div class="rule-item" v-for="(item, index) in levelParams.levelRules" :key="index+'rule'">
            <!-- <ApiInput placeholder="请输入等级称号" class="stort-input" v-model="item.name"/>
            <ApiInput placeholder="请设置等级要求" class="stort-input" v-model="item.limitValue"/> -->
            <input type="number" class="form-control stort-input" placeholder="请输入等级称号" @keyup="handleInput($event.target,index,'name')" v-model="item.name" />
            <input type="number" class="form-control stort-input" placeholder="请设置等级要求" @input="handleInput($event.target,index,'limitValue')" v-model="item.limitValue" />
            <span class="text">元以上</span>
            <span class="op" @click="addRule()">+</span>
            <span class="op" v-show="index!=0" @click="removeRule(index)">-</span>
          </div>
        </div>
      </div>
      <div class="level-item">
        <span class="level-title">默认启用</span>
        <el-radio v-model="levelParams.status" :label="1" class="radio">是</el-radio>
        <el-radio v-model="levelParams.status" :label="0" class="radio">否</el-radio>
      </div>
    </div>
    <div class="bot-btn">
      <a href="javascript:;" class="btn-main" @click="saveLevel()">保存</a>
      <span class="btn-fill-main" @click="$router.go(-1)">返回</span>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: '',
      typeList: [
        { id: 1, name: '历史消费金额' }
      ],
      timeList: [
        { id: 0, name: '无限期' },
        { id: 1, name: '日' },
        { id: 2, name: '周' },
        { id: 3, name: '月' },
        { id: 4, name: '年' },
      ],
      levelParams: {
        // type: 1,
        ruleName: '',
        timeInterval: '',
        timeIntervalType: 0,
        status: 0,
        levelRules: [
          { name: '', limitValue: '' }
        ]
      }
    }
  },
  created() {
    this.id = this.$route.params.id || '';
    if(this.id) {
      this.getDetail();
    }
  },
  methods: {
    handleInput(target,index,key){
      // let str = this.levelParams.levelRules[index][key].replace(/\./g,'');
      // this.levelParams.levelRules[index][key] = str
      // console.log('11',this.levelParams.levelRules[index][key])

      if(!/^\d+$/.test(target.value)){
          this.levelParams.levelRules[index][key] = '';
      }

    },
    getDetail() {
      this.api.staff.levelDetail({id: this.id}).then((res) => {
        res.data.levelRules = JSON.parse(res.data.levelRules);
        this.levelParams = res.data;
      });
    },
    addRule() {
      this.levelParams.levelRules.push({
        name: '', 
        limitValue: ''
      });
    },
    removeRule(index) {
      this.levelParams.levelRules.splice(index, 1);
    },
    saveLevel() {
      let params = this.copy(this.levelParams);
      if(params.timeIntervalType == 0) {
        delete params.timeInterval;
      }
      params.levelRules = JSON.stringify(params.levelRules);
      let apiUrl = this.levelParams.id ? 'updateLevel' : 'addLevel';
      this.api.staff[apiUrl](params).then((res) => {
        this.$message.success({message: res.message, showClose: true});
        this.$router.push({name: '会员等级'});
      });
    }
  }
}
</script>

<style lang="scss" scoped>
  .level-detail{
    .title{
      font-size: 14px;
      color: #333333;
      font-weight: bold;
      padding-bottom: 10px;
    }
    .level-wrap{
      padding: 20px 20px 5px;
      background: #fff;
      border-radius: 4px;
      .level-item{
        padding-bottom: 15px;
        .level-title{
          display: inline-block;
          vertical-align: top;
          font-size: 14px;
          color: #333333;
          text-align: right;
          padding-right: 10px;
          width: 100px;
          line-height: 30px;
        }
        .form-control{
          width: 366px;
        }
        .stort-input{
          width: 178px;
          margin-right: 10px;
        }
        .level-box{
          display: inline-block;
          vertical-align: top;
          width: 600px;
          .rule-item{
            margin-bottom: 15px;
            .op{
              font-size: 20px;
              line-height: 24px;
              margin-left: 10px;
              font-weight: bold;
              cursor: pointer;
            }
            &:last-child{
              margin-bottom: 0;
            }
          }
        }
        .el-radio{
          line-height: 30px;
        }
      }
    }
    .bot-btn{
      padding-top: 20px;
      a{
        margin-right: 20px;
      }
    }
  }
</style>
