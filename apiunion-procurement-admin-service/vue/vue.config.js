const path = require('path');
function resolve (dir) {
    return path.join(__dirname, dir)
}
module.exports = {
  outputDir:'../webapp',
  indexPath:'WEB-INF/thymeleaf/web/views/index.html',
  devServer: {
    open: true, //浏览器自动打开页面
    // host: "0.0.0.0", //如果是真机测试，就使用这个IP
    host: "192.168.0.108", //如果是真机测试，就使用这个IP
    port: 9090,
    https: false,
    hotOnly: false, //热更新（webpack已实现了，这里false即可）
    proxy: {
          //配置跨域
          '/api': {
              target: "https://at.huidinghuo.com",
              // target: "http://10.10.1.140:9050",
              // target: "https://at.huidinghuo.com",
              // target: "https://at.apiunion.com",
              ws:true,
              changOrigin:true,
              pathRewrite:{
                  '^/api':'/'
              }
          }
      }
  },
  chainWebpack: (config)=>{
    config.resolve.alias
      .set('assets',resolve('src/assets'))
      .set('components',resolve('src/components'))
  },
  css: {
    loaderOptions: {
      sass: {
        data: `
          @import "src/common/scss/variable.scss";
          @import "src/common/scss/common.scss";
          @import "src/common/scss/elCustom.scss";
        `
      }
    }
  }
};
