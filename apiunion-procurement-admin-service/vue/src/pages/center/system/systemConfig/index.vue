import filter from '@/components/filter';
<template>
  <div class="systemConfig">
    <div class="tab-bar">
      <div class="tab-item" v-for="item in typeList" :key="item.id" :class="{'active':currentType==item.id}" @click="changeType(item.id)">{{item.name}}</div>
    </div>
    <div class="config-content">
      <div class="config-row" v-show="currentType=='sms_account'">
        <span class="config-name">短信运营商</span>
        <select class="form-control" v-model="configData.channel" :disabled="!isEdit">
          <option v-for="item in smsList" :key="item.channel" :value="item.channel">{{item.channelName}}</option>
        </select>
      </div>
      <!-- <div class="config-row" v-show="currentType=='ses_account'">
        <span class="config-name">邮件运营商</span>
        <select class="form-control" v-model="configData.channel" :disabled="!isEdit">
          <option v-for="item in smsList" :key="item.channel" :value="item.channel">{{item.channelName}}</option>
        </select>
      </div> -->
      <div class="config-row" v-if="currentType=='service_qq_account'">
          <span class="config-name">客服类型</span>
          <el-radio-group v-model="configData.type" :disabled="!isEdit" @change="serviceType">
            <el-radio :label="0">QQ</el-radio>
            <el-radio :label="1">微信</el-radio>
            <!-- <el-radio :label="2">在线客服</el-radio> -->
          </el-radio-group>
      </div>
      <div class="config-row" v-show="currentType!='default_register_type'">
        <span class="config-name">{{currentType=='service_qq_account'?'':''}}账号</span>
        <input type="text" class="form-control" v-model="configData.account" :disabled="!isEdit" />
      </div>
      <div class="config-row" v-show="currentType=='service_qq_account'">
        <span class="config-name">在线时间</span>
        <el-time-picker
          format="HH:mm"
          value-format="HH:mm"
          v-model="online.start"
          placeholder="任意时间点"
          :disabled="!isEdit"
          >
        </el-time-picker>
        -
        <el-time-picker
          format="HH:mm"
          value-format="HH:mm"
          v-model="online.end"
          placeholder="任意时间点"
          :disabled="!isEdit"
          >
        </el-time-picker>
      </div>
      <div class="config-row" v-show="currentType=='service_qq_account'">
        <span class="config-name">二维码图片</span>
        <upload uploadType='2' v-on:uploadSuccess="uploadSuccess" :disabled="!isEdit">
            <span v-show="!qrCode">上传图片</span>
            <img :src="qrCode" v-show="qrCode"/>
        </upload>
      </div>
      <div class="config-row" v-show="currentType!='service_qq_account'&&currentType!='default_register_type'">
        <span class="config-name">密码</span>
        <input :type="checkPassword?'text':'password'" class="form-control" v-model="configData.password" :disabled="!isEdit" />
        <i class="el-icon-view" :class="{'active':checkPassword}" @click="checkPassword=!checkPassword"></i>
      </div>
      <div class="config-row" v-show="currentType=='sms_account'||currentType=='ses_account'">
        <span class="config-name">签名</span>
        <input type="text" class="form-control" v-model="configData.sign" :disabled="!isEdit" />
      </div>
      <div class="config-row" v-show="currentType=='default_register_type'">
        <div class="config-row">
          <span class="config-name">默认注册类型</span>
          <el-radio-group v-model="registerType" :disabled="!isEdit" @change="serviceType">
            <el-radio style="min-width: 130px" :label="0">注册为个人用户</el-radio>
            <el-radio style="min-width: 130px" :label="1">注册为企业用户</el-radio>
          </el-radio-group>
        </div>
        <div class="config-row">
          <span class="config-name">是否开放切换</span>
          <el-radio-group v-model="switchType" :disabled="!isEdit" @change="serviceType">
            <el-radio style="min-width: 130px" :label="0">不开放</el-radio>
            <el-radio style="min-width: 130px" :label="1">开放 <span class="switchCol"> &nbsp;&nbsp;(开放后，用户注册时可主动切换为其他用户类型进行注册)</span></el-radio>
          </el-radio-group>
        </div>
      </div>
      <div class="config-row config-row-agin" style="padding-bottom: 0">
        <button class="btn-main" @click="startEdit()" v-show="!isEdit">编辑</button>
        <button class="btn-main" @click="saveConfig()" v-show="isEdit">保存</button>
        <button class="btn-fill-main" @click="cancelEdit()" v-show="isEdit">取消</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isEdit: false,
      checkPassword: false,
      currentType: 'sms_account',
      smsList: [],
      typeList: [
        { id: 'sms_account', name: '短信运营商'},
        // { id: 'ses_account', name: '邮件运营商'},
        { id: 'service_qq_account', name: '在线客服'},
        { id: 'service_email_account', name: '客服邮箱'},
        { id: 'default_register_type', name: '用户注册'}
      ],
      configData: {},
      online:{
        start:'',
        end:''
      },
      qrCode:'',
      qqConfigList: [],
      defaultRegister:null,
      switchType:0,
      registerType:0
    }
  },
  created() {
    this.api.common.smsList().then((res) => {
      this.smsList = res.data;
    });
    this.getConfig();
  },
  methods: {
    uploadSuccess(res){
      this.qrCode = res.data;
    },
    getConfig() {
      this.api.common.accountConfig({configType: this.currentType}).then((res) => {
        if(this.currentType == 'service_qq_account'){
          // this.online.start = this.configData.onlineTime.split('-')[0]||'';
          // this.online.end = this.configData.onlineTime.split('-')[1]||'';
          // this.qrCode = this.configData.qrCode;
          let list = res.data || []
          this.qqConfigList = list
          let arr = list.filter(r => {
            return r.alive == 1
          }) || []
          let item = {}
          if (arr.length) {
            item = arr [0]
          } else {
            item = list[0]
          }
          this.online.start = item.onlineTime && item.onlineTime.split('-')[0]||''
          this.online.end = item.onlineTime && item.onlineTime.split('-')[1]||''
          this.qrCode = item.qrCode || ''
          this.configData = JSON.parse(JSON.stringify(item))
        } else {
          this.configData = res.data || {};
          this.defaultRegister =JSON.parse(res.data.configValue).defaultRegister;
          if(!!this.defaultRegister){
            this.switchType =(this.defaultRegister==1||this.defaultRegister==2)?0:1;
            this.registerType = (this.defaultRegister==1||this.defaultRegister==3)?0:1;
          }
        }
      });
    },
    changeType(type) {
      this.currentType = type;
      this.checkPassword = false;
      this.getConfig();
    },
    startEdit() {
      this.isEdit = true;
    },
    cancelEdit() {
      this.isEdit = false;
      this.checkPassword = false;
      this.getConfig();
    },
    saveConfig() {
      let params = {
        configType: this.currentType,
        configContent: {
          account: this.configData.account,
          password: this.configData.password,
        }
      };
      switch(this.currentType) {
        case 'sms_account':
        case 'ses_account':
          params = {
            configType: this.currentType,
            configContent: {
              channel: this.configData.channel,
              account: this.configData.account,
              password: this.configData.password,
              sign: this.configData.sign,
            }
          }
          break
        case 'service_qq_account':
          params.configContent.qrCode = this.qrCode;
          params.configContent.onlineTime = `${this.online.start}-${this.online.end}`
          params.configContent.type = this.configData.type
          params.configContent.alive = 1
          break;
        case 'default_register_type':
          delete params.configContent.account;
          delete params.configContent.password;
          params.configContent.defaultRegister = (this.switchType==0&&this.registerType==0)?1:(this.switchType==0&&this.registerType==1)?2:(this.switchType==1&&this.registerType==0)?3:4
          break
      }
      this.console(this.configData);
      params.configContent = JSON.stringify(params.configContent);
      let p = params
      if (this.currentType == 'service_qq_account') {
        let {qqConfigList, configData, configData: {type}} = this
        // let m = 0
        // for (let k = 0; qqConfigList.length; k++) {
        //   if (qqConfigList[k].type == this.configData.type) {
        //       m = k
        //   } else {
        //       qqConfigList[k].alive = 0
        //   }
        // }
        // this.$set(this.qqConfigList, m, params.configContent)
        // p.configContent = this.qqConfigList
          let status = false
          for (let k=0; k< qqConfigList.length; k++) {
              if (qqConfigList[k].type == type) {
                status = true
                break
              }
          }
          if (qqConfigList.length == 1) {
              if (status) {
                  configData.type = Number(configData.type)
                  configData.qrCode = this.qrCode
                  configData.onlineTime = this.online.start + '-' + this.online.end
                  this.$set(this.qqConfigList, 0, configData)
              } else {
                configData.type = Number(configData.type)
                configData.qrCode = this.qrCode
                configData.onlineTime = this.online.start + '-' + this.online.end
                this.$set(this.qqConfigList, 1, configData)
              }
          } else {
              let m = 0
              for (let k = 0; k< qqConfigList.length; k++) {
                if (qqConfigList[k].type == Number(configData.type)) {
                  m = k
                  break
                }
              }
              configData.qrCode = this.qrCode
              configData.onlineTime = this.online.start + '-' + this.online.end
              configData.type = Number(configData.type)
              this.$set(this.qqConfigList, m, configData)
          }
          for (let k=0; k< this.qqConfigList.length; k++) {
            if (this.qqConfigList[k].type == configData.type) {
              this.qqConfigList[k].alive = 1
            } else {
              this.qqConfigList[k].alive = 0
            }
          }
          p.configContent = this.qqConfigList || []
      }
      this.api.common.updateAccountConfig(p).then((res) => {
        this.$message.success({message: res.message, showClose: true});
        this.isEdit = false;
        this.checkPassword = false;
        this.getConfig();
      });
    },
    // 切换客服
    serviceType () {
      const {configData, configData:{type}, qqConfigList} = this
      let status = false
      let item = {
        type
      }
      for (let k=0; k< qqConfigList.length; k++) {
        if (qqConfigList[k].type == type) {
          item = qqConfigList[k]
          status = true
          break
        }
      }
      if (qqConfigList.length == 1) {
          if (status) {
              configData.type = Number(!configData.type)
              configData.qrCode = this.qrCode
              configData.onlineTime = this.online.start + '-' + this.online.end
              this.$set(this.qqConfigList, 1, configData)
          } else {
             configData.type = Number(!configData.type)
             configData.qrCode = this.qrCode
             configData.onlineTime = this.online.start + '-' + this.online.end
             this.$set(this.qqConfigList, 0, configData)
          }
      } else {
          let m = 0
          for (let k = 0; k< qqConfigList.length; k++) {
            if (qqConfigList[k].type == Number(!configData.type)) {
              m = k
              break
            }
          }
          configData.qrCode = this.qrCode
          configData.onlineTime = this.online.start + '-' + this.online.end
          configData.type = Number(!configData.type)
          this.$set(this.qqConfigList, m, configData)
      }
      this.online.start = item.onlineTime && item.onlineTime.split('-')[0]||''
      this.online.end = item.onlineTime && item.onlineTime.split('-')[1]||''
      this.qrCode = item.qrCode || ''
      this.configData = {}
      this.configData = JSON.parse(JSON.stringify(item))
    }
  }
}
</script>

<style lang="scss" scoped>
  .systemConfig{
    .tab-bar{
      .tab-item{
        padding: 0 10px;
      }
    }
    .config-content{
      background: #fff;
      padding: 20px 20px;
      border-radius: 4px;
      .config-row{
        padding-bottom: 5px;
        .config-name{
          display: inline-block;
          vertical-align: top;
          width: 110px;
          line-height: 30px;
          text-align: right;
          padding-right: 20px;
        }
        .config-info{
          display: inline-block;
          vertical-align: top;
          line-height: 30px;
        }
        .form-control{
          width: 200px;
        }
        .btn-main{
          margin-right: 15px;
        }
        .el-icon-view{
          font-size: 18px;
          color: #999;
          margin-left: 10px;
          cursor: pointer;
        }
        .active{
          color: $main-color;
        }
        .el-radio-group {
          height: 30px;
          line-height: 38px;
          .el-radio{

          }
        }
      }
      .upload-container{
        display: inline-block;
        line-height: 30px;
        img{
          width: 100px;
          height: 100px;
        }
      }
    }
  }
  .switchCol{
    color: #666666;
  }
  .config-row-agin{
    padding-bottom: 0;
  }
</style>
<style>
  .el-radio__label{
    padding-left: 8px;
  }
</style>
