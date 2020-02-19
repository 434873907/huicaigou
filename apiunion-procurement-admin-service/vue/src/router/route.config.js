import navConfig from './nav.config.json';

const loadComponent = function(path) {
  return r => require([`../pages${path}/index.vue`], r);
};
let centerIndex = '';
const registerRoute = navConfig => {
  let route = [];
  navConfig.forEach((nav, navIndex) => {
    if (nav.groups) {
      centerIndex = navIndex;
      var navRoute = {
        path: nav.path,
        name: nav.name,
        component: loadComponent(nav.path),
        children: []
      };
      nav.groups.forEach((group,groupIndex) => {
        group.list.forEach((groupChild , groupChildIndex) => {
          groupChild.children.forEach(child => {
            let routePath = nav.path + group.path + child.path;
            child.path = (group.path + child.path).slice(1);
            child.routePath = routePath;
            navRoute.children.push({
              path: child.path,
              name: child.title,
              meta: {
                hideBreadNav:child.hideBreadNav,
                title:child.title,
                parent:{
                  name:group.groupName,
                  index:groupIndex
                },
                group:{
                  name:groupChild.title,
                  index:groupChildIndex
                }
              },
              component: loadComponent(routePath)
            });
          });
        });
      });
      route.push(navRoute);
    } else {
      addRoute(nav);
    }
  });
  function addRoute(page) {
    const component = loadComponent(page.path);
    let child = {
      path: page.path,
      meta: {
        hideBreadNav:page.hideBreadNav,
        title: page.title || page.name,
        description: page.description
      },
      name: page.title || page.name,
      component: component
    };
    route.push(child);
  }
  return route;
};

let route = registerRoute(navConfig);
const defaultPath = '/login';
// {
//   "name": "API 联盟",
//   "path": "/home"
// },

route.push({
  path: '/run',
  meta: {
    title: 'sql生成',
    description: 'sql生成'
  },
  name:'sql生成',
  component: r => require(['../pages/home/sqlRun.vue'], r)
});

route[centerIndex].children = route[centerIndex].children.concat([
  {
    path: 'system/agreement/detail/:id?',
    name: '协议配置',
    meta: {
      hideBreadNav: true,
      group: {name: "协议配置", index: 3},
      parent: {name: "系统", index: 1},
      title: "协议配置详情"
    },
    component: r => require(['../pages/center/system/agreement/detail.vue'], r)
  },
  {
    path: 'system/agreement/helpDetail',
    name: '帮助中心配置',
    meta: {
      hideBreadNav: true,
      group: {name: "协议配置", index: 3},
      parent: {name: "系统", index: 1},
      title: "帮助中心配置"
    },
    component: r => require(['../pages/center/system/agreement/helpDetail.vue'], r)
  },
  {
    path: 'goods/commodity/detail/:id?/:status?',
    name: '商品详情',
    meta: {
      hideBreadNav: true,
      group: {name: "商品库", index: 0},
      parent: {name: "商品", index: 2},
      title: "商品详情"
    },
    component: r => require(['../pages/center/goods/commodity/detail.vue'], r)
  },
  {
    path: 'trade/order/detail/:userId?/:orderNum?/:orderStatus?',
    name: '订单详情',
    meta: {
      hideBreadNav: false,
      group: {name: "订单", index: 0},
      parent: {name: "交易", index: 3},
      title: "订单详情"
    },
    component: r => require(['../pages/center/trade/order/detail.vue'], r)
  },
  {
    path: 'goods/brand/detail/:id?',
    name: '品牌详情',
    meta: {
      hideBreadNav: true,
      group: {name: "品牌管理", index: 3},
      parent: {name: "商品", index: 2},
      title: "品牌详情"
    },
    component: r => require(['../pages/center/goods/brand/detail.vue'], r)
  },
  {
    path: 'store/shop/detail/:id?',
    name: '店铺详情',
    meta: {
      hideBreadNav: true,
      group: {name: "店铺", index: 0},
      parent: {name: "店铺", index: 4},
      title: "店铺详情"
    },
    component: r => require(['../pages/center/store/shop/detail.vue'], r)
  },
  {
    path: 'marketing/coupon/detail/:id?',
    name: '优惠券详情',
    meta: {
      hideBreadNav: true,
      group: {name: "优惠券", index: 1},
      parent: {name: "营销", index: 5},
      title: "品牌详情"
    },
    component: r => require(['../pages/center/marketing/couponDetail/index.vue'], r)
  },
  {
    path: 'marketing/coupon/send-coupon/:id?/:name?',
    name: '发劵',
    meta: {
      hideBreadNav: true,
      group: {name: "优惠券", index: 1},
      parent: {name: "营销", index: 5},
      title: "发劵"
    },
    component: r => require(['../pages/center/marketing/coupon/send-coupon/index.vue'], r)
  },
  {
    path: 'goods/freighttemp/detail/:id?/:isCopy?',
    name: '运费模板详情',
    meta: {
      hideBreadNav: true  ,
      group: {name: "运费模板", index: 0},
      parent: {name: "商品", index: 2},
      title: "新建运费模板"
    },
    component: r => require(['../pages/center/goods/freighttemp/detail.vue'], r)
  },
  {
    path: 'system/notice/detail/:id?',
    name: '通知详情',
    meta: {
      hideBreadNav: true  ,
      group: {name: "通知", index: 0},
      parent: {name: "系统", index: 1},
      title: "新增通知"
    },
    component: r => require(['../pages/center/system/notice/detail.vue'], r)
  },
  {
    path: 'system/pc/bannerDetail/:id?',
    name: 'banner详情',
    meta: {
      hideBreadNav: true  ,
      group: {name: "配置", index: 1},
      parent: {name: "系统", index: 1},
      title: "banner"
    },
    component: r => require(['../pages/center/system/pc/bannerDetail.vue'], r)
  },
  {
    path: 'system/pc/tabDetail/:id?',
    name: 'tab详情',
    meta: {
      hideBreadNav: true  ,
      group: {name: "配置", index: 1},
      parent: {name: "系统", index: 1},
      title: "顶部tab"
    },
    component: r => require(['../pages/center/system/pc/tabDetail.vue'], r)
  },
  {
    path: 'marketing/promotion/detail/:id?',
    name: '活动详情',
    meta: {
      hideBreadNav: true  ,
      group: {name: "营销活动", index: 0},
      parent: {name: "营销", index: 5},
      title: "新增活动"
    },
    component: r => require(['../pages/center/marketing/promotion/detail.vue'], r)
  },
  {
    path: 'marketing/buyLimit/detail/:id?',
    name: '限时抢购详情',
    meta: {
      hideBreadNav: true  ,
      group: {name: "营销活动", index: 0},
      parent: {name: "营销", index: 5},
      title: "限时抢购详情"
    },
    component: r => require(['../pages/center/marketing/buyLimit/detail.vue'], r)
  },
  {
    path: 'marketing/groupBuy/detail/:id?',
    name: '团购详情',
    meta: {
      hideBreadNav: true  ,
      group: {name: "营销活动", index: 0},
      parent: {name: "营销", index: 5},
      title: "团购详情"
    },
    component: r => require(['../pages/center/marketing/groupBuy/detail.vue'], r)
  },
  {
    path: 'marketing/presell/detail/:id?',
    name: '预售详情',
    meta: {
      hideBreadNav: true  ,
      group: {name: "营销活动", index: 0},
      parent: {name: "营销", index: 5},
      title: "预售详情"
    },
    component: r => require(['../pages/center/marketing/presell/detail.vue'], r)
  },
  {
    path: 'user/levelmanage/detail/:id?',
    name: '会员等级详情',
    meta: {
      hideBreadNav: true  ,
      group: {name: "采购商管理", index: 1},
      parent: {name: "用户", index: 7},
      title: "会员等级详情"
    },
    component: r => require(['../pages/center/user/levelmanage/detail.vue'], r)
  },
  {
    path: 'system/systemConfig/upload',
    name: '内部上传',
    meta: {
      hideBreadNav: true,
      group: {name: "配置", index: 1},
      parent: {name: "系统", index: 1},
      title: "内部上传"
    },
    component: r => require(['../pages/center/system/systemConfig/upload.vue'], r)
  },
]);
route = route.concat([
  {
    path: '',
    redirect: defaultPath
  },
  {
    path: '*',
    redirect: defaultPath
  }
]);

export default route;
