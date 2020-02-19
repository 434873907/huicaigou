import Vue from 'vue'
import App from './App.vue'
import router from './router';
import core from './core';
import ApiInput from './components/input';
Vue.directive('noMoreClick', {
  inserted(el, binding) {
    el.addEventListener('click', () => {
      if (!el.disabled) {
        el.disabled = true
        el.classList.add('is-disabled')
        setTimeout(() => {
          el.disabled = false
          el.classList.remove('is-disabled')

        }, binding.value || 3000)
      }
    })
  }
})
import {
  Dialog,
  Radio,
  Checkbox,
  CheckboxGroup,
  Scrollbar,
  Tree,
  RadioGroup,
  Icon,
  Input,
  Select,
  Option,
  DatePicker,
  TimePicker,
  Progress,
  Message,
  Pagination,
  Upload,
  MessageBox,
  Popover,
  ColorPicker,
  Carousel,
  CarouselItem,
  InputNumber,
  Switch,
  Form,
  FormItem,
  Tabs,
  Button,
  TabPane,
  Table,
  TableColumn,
  Row,
  Col
 } from 'element-ui';
Message.install = function (Vue) {
  Vue.prototype.$message = Message
}
MessageBox.install = function (Vue) {
  Vue.prototype.$msgbox = MessageBox
}
Vue.use(Dialog);
Vue.use(Radio);
Vue.use(Button);
Vue.use(InputNumber);
Vue.use(Table);
Vue.use(TableColumn);
Vue.use(Checkbox);
Vue.use(CheckboxGroup);
Vue.use(Scrollbar);
Vue.use(Tree);
Vue.use(RadioGroup);
Vue.use(Icon);
Vue.use(Input);
Vue.use(Select);
Vue.use(Option);
Vue.use(DatePicker);
Vue.use(TimePicker);
Vue.use(Progress);
Vue.use(Message);
Vue.use(Pagination);
Vue.use(Upload);
Vue.use(MessageBox);
Vue.use(Popover);
Vue.use(ColorPicker);
Vue.use(Carousel);
Vue.use(CarouselItem);
Vue.use(Switch);
Vue.use(Form);
Vue.use(FormItem);
Vue.use(Tabs);
Vue.use(TabPane);
Vue.use(Row)
Vue.use(Col)
Vue.prototype.$msgbox = MessageBox;
Vue.prototype.$alert = MessageBox.alert;
Vue.prototype.$confirm = MessageBox.confirm;
Vue.prototype.$prompt = MessageBox.prompt;
Vue.prototype.$notify = Notification;
Vue.prototype.$message = Message;
import upload from './components/upload';
Vue.component('upload',upload);
Vue.component('ApiInput',ApiInput);
for(let key in core){
  Vue.prototype[key] = core[key];
}
router.beforeEach((to, from, next) => {
  if(to.path === '/login'){
    Vue.prototype.api.common.pageConfig().then((res) => {
      localStorage.setItem('systemInfo',JSON.stringify(res.data));
      let data = JSON.parse(localStorage.systemInfo);
      var link = document.querySelector("link[rel*='icon']") || document.createElement('link');
      link.type = 'image/x-icon';
      link.rel = 'shortcut icon';
      link.href = data.logo;
      document.getElementsByTagName('head')[0].appendChild(link);
      document.title = to.meta.title + '-' + JSON.parse(localStorage.getItem('systemInfo')).name + '管理后台';
    });

  }
  Vue.prototype.userConfig = JSON.parse(localStorage.userConfig || '{"systemInfo":{}}') || {};

  if(localStorage.systemInfo){
    let data = JSON.parse(localStorage.systemInfo);
    var link = document.querySelector("link[rel*='icon']") || document.createElement('link');
    link.type = 'image/x-icon';
    link.rel = 'shortcut icon';
    link.href = data.logo;
    document.getElementsByTagName('head')[0].appendChild(link);
  }
  //设置动态title
  if (to.meta.title ) {
    console.log(to)
    if(to.path !== '/login'){
      if(localStorage.getItem('systemInfo')){
        document.title = to.meta.title + '-' + JSON.parse(localStorage.getItem('systemInfo')).name + '管理后台';
      }else{
        document.title = to.meta.title + '-'  + '管理后台';
      }
    }
  }
  next();

});

router.afterEach(() => {
  // document.title = route.name || 'Api Union';
});

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
