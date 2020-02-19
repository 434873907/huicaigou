<template>
  <div class="api-index">
    <div class="top-wrap">
      <div class="nav-wrap clear-fix">
        <img src="~assets/home/whiteLogo.png" class="nav-logo pull-left">
        <ul class="pull-left nav-ul">
          <li>首页</li>
          <li>商品中心</li>
          <li>订单中心</li>
          <li>营销组件</li>
          <li>用户中心</li>
          <li>系统管理</li>
          <li>联系我们</li> 
        </ul>
        <div class="pull-right right-nav" v-show="!user.id">
          <router-link :to="{ name: '登录'}">登录</router-link>
          <router-link :to="{ name: '注册'}">注册</router-link>
        </div>
        <div class="pull-right right-nav" v-show="!!user.id">
           <router-link :to="{ name: user.firstMenu}">{{user.nickName}}</router-link>
        </div>
      </div>
    </div>
    <div class="goods-center">
      <div class="triangle-title">
        <p class="zh">商品中心</p>
        <p class="en">COMMODITY CENTER</p>
      </div>
      <div class="goods-center-content">
        <div class="flex-between">
          <div class="flex-item">
            <img src="~assets/home/type.png" class="title-img">
            <div class="title-name">类目管理</div>
            <div class="desc">定义你的商城类目</div>
            <div class="desc">增加、修改、删除您的商品类</div>
            <div class="desc">目，上传、更改类目图片</div>
            <img src="~assets/home/pLogo.png" class="logo-img">
          </div>
          <div class="flex-item">
              <img src="~assets/home/goods.png" class="title-img">
              <div class="title-name">商品库</div>
              <div class="desc">使用商品库接口批量导入商品</div>
              <div class="desc">数据，在管理后台轻松管理所</div>
              <div class="desc">有SKU，更支持定制多平台商</div>
              <div class="desc">品数据同步</div>
              <img src="~assets/home/pLogo.png" class="logo-img">
          </div>
          <div class="flex-item">
              <img src="~assets/home/supply.png" class="title-img">
              <div class="title-name">供应链</div>
              <div class="desc">管理您的商城供应商</div>
              <div class="desc">批量记录、统计供应商各项商</div>
              <div class="desc">品、销售数据，形成管理后台</div>
              <div class="desc">可视化报表</div>
              <img src="~assets/home/pLogo.png" class="logo-img">
          </div>
        </div>
      </div>
      <div class="show-more">查看更多</div>
    </div>
    <div class="order-center">
      <div class="triangle-title">
        <p class="zh">订单中心</p>
        <p class="en">ORDER CENTER</p>
      </div>
      <div class="flex-between">
          <div class="flex-item">
            <img src="~assets/home/shopcar.png" class="title-img">
            <div class="title-name">购物车</div>
            <div class="line"></div>
            <div class="desc">您的商城必备模块，支持链接活动、活动价计</div>
            <div class="desc">算、修改删除商品等功能</div>
          </div>
          <div class="flex-item">
              <img src="~assets/home/settle.png" class="title-img">
              <div class="title-name">订单结算</div>
              <div class="line"></div>
              <div class="desc">定制您的商城结算方式，支持活动减免、优惠</div>
              <div class="desc">券、会员等多种方式组合结算</div>
          </div>
          <div class="flex-item">
            <img src="~assets/home/manage.png" class="title-img">
            <div class="title-name">订单管理</div>
            <div class="line"></div>
            <div class="desc">全流程管理您的商城订单，支持订单生成、订单查</div>
            <div class="desc">询、各状态更改、异常处理、可视化管理等功能</div>
          </div>
        </div>
        <div class="show-more">查看更多</div>
    </div>
    <div class="sale-user">

    </div>
    <div class="sys-manage">
      <div class="sys-wrap">
        <div class="sys-div p1">系统管理－页面配置</div>
        <div class="sys-div p2">系统管理－模块配置</div>
        <div class="sys-div p3">系统管理－标签配置</div>
        <div class="sys-div-content">
          <div class="sys-title">系统管理</div>
          <div class="sys-desc">简单几步系统配置让您的商城更加充实，选择需要添加的系统管理模块，对接完毕之后即可通过后台管理模块配置您的商城首页、各模块内容、灵活配色等，全方位管理商城页面。</div>
          <div class="show-more">查看更多</div>
        </div>
      </div>
    </div>
    <div class="bottom-wrap">
      <div class="desc-wrap">
        <div class="bottom-title">API联盟·狂风计划</div>
        <div class="bottom-desc">尝试一种新的模式  轻松搭建自定义电商平台  有效提升开发效率  节约合作伙伴成本</div>
        <div class="join-btn">加入我们</div>
      </div>
    </div>
    <div class="content-bottom">
      <div class="content-wrap">
        <div>关注我们</div>
        <div>API联盟微博</div>
        <div>API联盟微信公众号</div>
        <div>商务合作：hr@hzchengzi.com</div>
        <div>0571-86483760</div>
      </div>
      <div class="desc">浙ICP备14037498号-3 折疯了海淘©2015 zhefengle.com,ALL Rights</div>
      <div class="desc">橙子信息科技（杭州）有限公司 版权所有</div>
    </div>
  </div>
</template>
<script>

export default {
  props: {
    msg: String
  },
  data () {
    return {
      user:{
        firstMenu: ''
      }
    }
  },
  created() {
    if(localStorage.user){
      this.user = JSON.parse(localStorage.user);
      this.$set(this.user, 'firstMenu', localStorage.firstMenu);
    }
  }
}
</script>
<style scoped lang='scss'>
  .api-index{
    background: #fff;
    .top-wrap{
      width: 100%;
      height: 973px;
      background: url('~assets/home/top.png') top center no-repeat;
      .nav-wrap{
        width: 1200px;
        margin: 0 auto;
        .nav-logo{
          display: inline-block;
          margin-top: 15px;
          margin-right: 42px;
        }
        .nav-ul{
          display: flex;
          width: 560px;
          padding-top: 34px;
          color:#fff;
          justify-content: space-between;
          li{
            padding-bottom: 8px;
            line-height: 1;
            font-size: 16px;
            &.active{
              border-bottom: 2px solid #fff;
            }
          }
        }
        .right-nav{
          margin-top: 20px;
          a{
            display: inline-block;
            margin-left: 16px;
            width: 80px;
            line-height: 36px;
            text-align: center;
            background: #7C41CE;
            color:#fff;
            font-size: 18px;
            border-radius: 18px;
          }
        }
      }
    }
    .triangle-title{
      text-align: center;
      .zh{
        margin-bottom: 18px;
        font-size: 30px;
        color:#333;
        position: relative;
        z-index: 10;
        &::after{
          content: " ";
          width: 0;
          height: 0;
          display: block;
          position: absolute;
          z-index: -1;
          top: -22px;
          left: 50%;
          margin-left: -60px;
          border-right: 60px solid transparent;
          border-left: 60px solid transparent;
          border-bottom: 60px solid #F0E9FA;
        }
      }
      .en{
        font-size: 24px;
        color:rgba(114, 51, 204, 0.25);
      }
    }
    .flex-between{
      display: flex;
      justify-content: space-between;
      width: 1200px;
      margin: 0 auto;
      .flex-item{
        text-align: center;
        background: #fff;
        border-right: 18px;
        box-shadow: 0 2px 7px 0 rgba(72, 101, 174, 0.27);
      }
    }
    .goods-center{
      margin-top: 100px;
      .goods-center-content{
        padding-top: 37px;
        height: 540px;
        background: url('~assets/home/circle.png') top center no-repeat;
        .flex-item{
          width: 300px;
          height: 460px;
          position: relative;
          .title-img{
            margin: 42px auto 38px;
          }
          .title-name{
            margin-bottom: 28px;
            font-size: 18px;
            color: #3C1B7D;
            white-space: 0.5px;
          }
          .desc{
            color:#666;
            line-height: 22px;
          }
          .logo-img{
            position: absolute;
            bottom: 28px;
            left: 50%;
            margin-left: -22.5px;
          }
        }
      }
      .show-more{
        margin: 42px auto 92px;
      }
    }
    .order-center{
      padding: 120px 0 80px;
      background: $main-bg-color;
      .triangle-title{
        margin-bottom: 80px;
      }
      .flex-item{
        width: 370px;
        height: 380px;
        .title-img{
          width: 150px;
          margin: 44px auto 24px;
        }
        .title-name{
          font-size: 18px;
          color:#333;
        }
        .line{
          width: 36px;
          height: 2px;
          background: #7233CC;
          margin: 25px auto 22px;
        }
        .desc{
          color:#666;
          line-height: 22px;
        }
      }
      .show-more{
        margin: 102px auto 0;
      }
    }
    .sale-user{
      height: 1660px;
      background: url('~assets/home/saleAndUser.png') top center no-repeat;
    }
    .sys-manage{
      height: 984px;
      background: url('~assets/home/sysBg.png') top center no-repeat;
      .sys-wrap{
        width: 1200px;
        margin: 0 auto;
        position: relative;
      }
      .sys-div{
        position: absolute;
        border-radius: 18px;
        text-align: center;
        font-size: 18px;
        color:#fff;
      }
      .p1{
        top: 173px;
        left: 0;
        width: 310px;
        height: 434px;
        text-align: left;
        padding-top: 250px;
        padding-left: 85px;
        background: url('~assets/home/sysP1.png') top center no-repeat;
        background-size:100%; 
      }
       .p2{
        top: 41px;
        left: 350px;
        width: 272px;
        height: 340px;
        padding-top: 240px;
        background: url('~assets/home/sysP2.png') top center no-repeat;
      }
       .p3{
        top: 409px;
        left: 350px;
        width: 181px;
        height: 224px;
        padding-top: 160px;
        font-size: 14px;
        color:#7233CC;
        background: url('~assets/home/sysP3.png') top center no-repeat;
      }
      .sys-div-content{
        position: absolute;
        top: 200px;
        left: 700px;
        .sys-title{
          margin-bottom: 30px;
          font-size: 46px;
          color:#7233CC;
        }
        .sys-desc{
          width: 490px;
          line-height: 22px;
          color:#666;
        }
        .show-more{
          margin-top: 40px;
        }
      }
    }
    .bottom-wrap{
      padding-top: 1px;
      padding-bottom: 1px;
      background: #f8f8f8;
      text-align: center;
      .desc-wrap{
        width: 900px;
        margin: -128px auto 0;
      }
      .bottom-title{
        font-size: 36px;
        color:#7233CC;
      }
      .bottom-desc{
        margin: 30px auto 45px;
        font-size: 24px;
        color:#333;
      }
      .join-btn{
        margin: 0 auto 117px;
        width: 281px;
        cursor: pointer;
        line-height: 60px;
        border-radius: 30px;
        color:#fff;
        font-size: 24px;
        text-align: center;
        background:linear-gradient(to right,#6264E8,#7233CC);
      }
    }
    .show-more{
      width: 200px;
      line-height: 60px;
      color: #fff;
      text-align: center;
      border-radius: 30px;
      font-size: 24px;
      cursor: pointer;
      background: #7233CC;
      box-shadow: 0 5px 19px 0 rgba(28,95,176,0.3)
    }
    .content-bottom{
      padding-bottom: 48px;
      background: #f8f8f8;
      .content-wrap{
        width: 1200px;
        padding-bottom: 32px;
        margin: 0 auto 32px;
        display: flex;
        justify-content: space-around;
        border-bottom:1px solid #979797;
      }
      .desc{
        text-align: center;
        color:#666;
        line-height: 20px;
      }
    }
  }
</style>
