<template>
  <div class="api-login">
    <div class="loginBox">
      <div class="loginContent">
        <div class="loginLogo">
          <img src="~assets/login/loginLogo.png" />
        </div>
        <!-- <h2>SIGN IN</h2> -->
        <h4>欢迎登录商城管理后台</h4>
        <!-- 给item加loginItemFocus为选中样式,加loginItemWarning为警告样式 -->
        <div class="loginItem" :class="{'loginItemFocus':focusName}">
          <div class="form-name">帐号</div>
          <input type="text" autofocus="autofocus" v-model="account" @keyup.enter="login"  class="form-control" placeholder="请输入帐号" v-on:focus="focusInput(1)" @blur="blurInput(1)" />
        </div>
        <div class="loginItem" :class="{'loginItemFocus':focusPwd}">
          <div class="form-name">密码</div>
          <input type="password" v-model="password" @keyup.enter="login" class="form-control" placeholder="请输入管理密码" v-on:focus="focusInput(2)" @blur="blurInput(2)" />
        </div>
        <button @click="login" class="loginBtn">
          立即登录
          <!-- <img src="~assets/login/btnTriangle.png" /> -->
        </button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      focusName: false,
      focusPwd: false,
      account:'',
      password:''
    }
  },
  watch:{
    '$route' :{
      handler:function (to,from) {

      },
      immediate:true
    }
  },
  methods: {
    focusInput(type) {
      if(type == 1){
        this.focusName = true;
        this.focusPwd = false;
      }else{
        this.focusName = false;
        this.focusPwd = true;
      }
    },
    blurInput(type) {
      if(type == 1){
        this.focusName = false;
      }else{
        this.focusPwd = false;
      }
    },
    login(){
      if(this.account===''){
        this.$message.error('请输入账号');
        return false;
      }
      if(this.password===''){
        this.$message.error('请输入密码');
        return false;
      }
      this.api.user.login({
        account:this.account,
        password:this.password
      }).then(result=>{
        this.console(result);
        // 获取后台菜单
        localStorage.user = JSON.stringify(result.data);
        this.api.common.menu({}).then((res) => {
          localStorage.menu = JSON.stringify(res.data);
          this.api.common.pageConfig({}).then((data) => {
            localStorage.systemInfo = JSON.stringify(data.data);
          });
          this.api.common.config({}).then((config) => {
            localStorage.userConfig = JSON.stringify(config.data);
            if(this.$route.query.redirect){
              this.$router.go(-1);
            }else {
              this.$router.push({ name: res.data[0].menus[0].menus[0].title });
            }

          });


        });
      });
    },
  },
  created() {
    console.log(this.$route.query.redirect)
  }
}
</script>
<style scoped lang='scss'>
  .api-login{
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    overflow: auto;
    background: linear-gradient(to right, $org-color, $main-color);
    // background: url('~@/assets/login/loginbg.png') no-repeat center;
    display: flex;
    justify-content: center;
    align-items: center;
    .loginBox{
      background: url('~@/assets/login/loginbg.png') no-repeat center;
      width: 850px;
      height: 520px;
      padding-top: 50px;
      display:flex;
      justify-content: center;
      .loginContent{
        width: 338px;
        // margin-left: 454px;
        .loginLogo{
          width: 100px;
          height: 100px;
          margin: 0 auto;
          img{
            width: 100%;
            height: 100%;
          }
        }
        h2{
          color: #3E5872;
          font-size: 32px;
          font-weight: bold;
          text-align: center;
          padding-top: 24px;
        }
        h4{
          color: #66899D;
          font-size: 24px;
          text-align: center;
          padding-top: 10px;
          line-height: 32px;
        }
        .loginItem{
          border-bottom: 1px solid #999999;
          padding-bottom: 5px;
          position: relative;
          margin-top: 36px;
          .form-name{
            color: #66899D;
            font-size: 18px;
          }
          .form-control{
            width: 278px;
            border: none;
            background: transparent;
            padding: 0 12px;
            line-height: 30px;
            font-size: 18px;
            color: #333;
            &::placeholder{
              color: #999999;
            }
          }
          &::after{
            content: '';
            position: absolute;
            top: 10px;
            right: 0;
            width: 8px;
            height: 8px;
            border-radius: 8px;
            background: #999999;
          }
        }
        .loginItemFocus{
          border-bottom: 1px solid #3E5872;
          &::after{
            background: #3E5872;
          }
        }
        .loginItemWarning{
          border-bottom: 1px solid $main-color;
          &::after{
            background: $main-color;
          }
        }
        .loginBtn{
          display: inline-block;
          vertical-align: top;
          width: 192px;
          height: 40px;
          background: #f54882;
          border-radius: 50px;
          box-shadow: 0 2px 10px 0 rgba(255, 142, 182, 1);
          text-align: center;
          line-height: 40px;
          font-size: 18px;
          color: #333333;
          margin: 50px 0 0 70px;
          border: none;
          color:#fff;
          img{
            display: inline-block;
            vertical-align: top;
            margin-top: 19px;
            margin-left: 6px;
          }
        }
      }
    }
  }
</style>
