import ajax from "./ajax.js";
import apiUrlList from "./api.js";
import cookie from "./cookIe.js";
import loading from "./loading.js";
import is from "is";
import { Message } from 'element-ui';
import copy from './copy';
import extend from './extend';
import apiJump from './jump';
import router from '../router';


ajax.beforeEach((res,next) => {
    if (cookie.getCookie() && cookie.getCookie().token) {
        res.data.token = cookie.getCookie().token;
    }
    next();
})

ajax.afterEach((res, next) => {
    if (res) {
        next();
    } else {
        loading.hideLoading();
        next();
    }
});

//post 请求封装
var post = (url, data = {}, success = () => { }, error = () => { }, head = {}) => {
    ajax({ url, data, success, error, type: 'POST', head })
};

//promise 请求封装
var pPost = (url, data = {}) => {
    return new Promise(function(resolve,reject){
        loading.apiShowLoading();
        data = copy(data)
        Object.keys(data).forEach(key => {
            if(typeof data[key] == 'object'){
                data[key] = JSON.stringify(data[key]);
            }
        });
        post(url, data, (res) => {
            loading.apiHideLoading();
            if ((res.code && res.code.toLowerCase() == 'success') || res.success) {
                resolve(res);
            } else if (res.code == "ERR_TOKEN_EXPIRED") {
                Message({message:'token过期，请重新登录', showClose: true});
                cookie.deleteCookie('token');                  //删除本地cookie
                delete localStorage.user;
                // location.href="/login";
                router.push({
                    path:'/login',
                    query:{
                        redirect: location.hostname
                    }
                })
            } else if (res.code == "ERR_PAGE_NOT_FOUND") {
              Message({message:res.message, showClose: true});
            } else if (res.code == "ERR_UNAUTHORIZED") {
                location.href="/center/noAuth/notice";
            } else {
              Message({message:res.message, showClose: true});
              reject(res);
            }
        }, () => {
            loading.apiHideLoading();
            Message({message:'接口出错', showClose: true});
            reject('接口出错');
        }, () => {
            loading.apiHideLoading();
            Message({message:'网络出错', showClose: true});
        });
    })
};
//请求封装
var api = {
    static:apiUrlList.static
};
Object.keys(apiUrlList.apiUrlList).forEach((itemFirst)=>{
    api[itemFirst] = {};
    Object.keys(apiUrlList.apiUrlList[itemFirst]).forEach((item)=>{
        var configApi = apiUrlList.apiUrlList[itemFirst][item];
        api[itemFirst][item] = (function (configApi) {
            return function () {
                var action = configApi;
                return pPost(action, arguments[0]);
            }
        })(configApi);
    })
});

function log (){
    for(let key in arguments){
        arguments[key] ? console.log(JSON.parse(JSON.stringify(arguments[key]))) : console.log(arguments[key]);
    }
}

export default {
    get(url, data = {}, success = () => { }, error = () => { }, head = {}) {
        ajax({ url, data, success, error, type: 'GET' }, head)
    },
    post: post,
    api: api,
    loading: loading,
    operateCookie: cookie,
    is: is,
    cookie: cookie,
    scroll: scroll,
    console:log,
    copy:copy,
    apiJump:apiJump,
    extend:extend
}
