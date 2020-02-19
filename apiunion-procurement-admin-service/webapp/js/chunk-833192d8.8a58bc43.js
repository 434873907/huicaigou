(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-833192d8"],{"12c6":function(t,a,e){},"72eb":function(t,a,e){"use strict";var s=e("12c6"),i=e.n(s);i.a},d5cc:function(t,a,e){"use strict";e.r(a);var s=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"tab-detail"},[e("h2",{staticClass:"title"},[t._v(t._s(t.id?"编辑":"新增")+"tab")]),e("div",{staticClass:"tab-wrap"},[e("div",{staticClass:"tab-item"},[e("span",{staticClass:"tab-title"},[t._v("标题")]),e("input",{directives:[{name:"model",rawName:"v-model",value:t.tabParams.title,expression:"tabParams.title"}],staticClass:"form-control",attrs:{type:"text"},domProps:{value:t.tabParams.title},on:{input:function(a){a.target.composing||t.$set(t.tabParams,"title",a.target.value)}}})]),t._l(t.tabParams.conditionList,function(a,s){return e("div",{key:s+"tab",staticClass:"tab-item"},[e("span",{staticClass:"tab-title"},[t._v(t._s(0===s?"组合条件":""))]),e("div",{staticClass:"item-condition"},[e("div",{staticClass:"condition-row"},[e("span",{staticClass:"condition-title"},[t._v("类目")]),e("a",{staticClass:"btn-fill-main",on:{click:function(e){return t.selectItemCate(a,s)}}},[t._v("选择类目")]),t._l(a.cateList,function(i,n){return e("span",{key:n+"cate"+s,staticClass:"btn-small"},[t._v("\n            "+t._s(i.name)),e("a",{staticClass:"delete-icon",on:{click:function(e){return t.removeItemCate(a,n)}}},[t._v("X")])])})],2),e("div",{staticClass:"condition-row"},[e("span",{staticClass:"condition-title"},[t._v("品牌")]),e("a",{staticClass:"btn-fill-main",on:{click:function(e){return t.selectItemBrand(a,s)}}},[t._v("选择品牌")]),t._l(a.brandList,function(i,n){return e("span",{key:n+"brand"+s,staticClass:"btn-small"},[t._v("\n            "+t._s(i.name)),e("a",{staticClass:"delete-icon",on:{click:function(e){return t.removeItemBrand(a,n)}}},[t._v("X")])])})],2),e("div",{staticClass:"condition-row"},[e("span",{staticClass:"condition-title"},[t._v("店铺")]),e("el-select",{staticClass:"multipleSelect",attrs:{multiple:"",size:"small",placeholder:"请选择"},model:{value:a.shopIdList,callback:function(e){t.$set(a,"shopIdList",e)},expression:"item.shopIdList"}},t._l(t.shopList,function(t){return e("el-option",{key:t.id,attrs:{label:t.name,value:t.id}})}),1)],1),e("div",{directives:[{name:"show",rawName:"v-show",value:!t.userConfig.singleChannel,expression:"!userConfig.singleChannel"}],staticClass:"condition-row"},[e("span",{staticClass:"condition-title"},[t._v("渠道")]),e("el-select",{staticClass:"multipleSelect",attrs:{multiple:"",size:"small",placeholder:"请选择"},model:{value:a.channelTypeList,callback:function(e){t.$set(a,"channelTypeList",e)},expression:"item.channelTypeList"}},t._l(t.channelList,function(t){return e("el-option",{key:t.channelType,attrs:{label:t.channelTypeName,value:t.channelType}})}),1)],1),e("div",{staticClass:"condition-btn"},[e("a",{directives:[{name:"show",rawName:"v-show",value:0===s,expression:"index===0"}],on:{click:function(a){return t.addConditionItem()}}},[t._v("添加")]),e("a",{directives:[{name:"show",rawName:"v-show",value:s>0,expression:"index>0"}],staticClass:"delete-condition",on:{click:function(a){return t.removeConditionItem(s)}}},[t._v("删除")])])])])}),e("div",{staticClass:"tab-item"},[e("span",{staticClass:"tab-title"},[t._v("起止时间")]),e("el-date-picker",{staticClass:"timepicker-control",attrs:{type:"datetime","value-format":"yyyy-MM-dd HH:mm:ss",size:"small",placeholder:"请选择","picker-options":t.pickerOption},model:{value:t.tabParams.startTime,callback:function(a){t.$set(t.tabParams,"startTime",a)},expression:"tabParams.startTime"}}),e("el-date-picker",{staticClass:"timepicker-control",attrs:{type:"datetime","value-format":"yyyy-MM-dd HH:mm:ss",size:"small",placeholder:"请选择","picker-options":t.pickerOption},model:{value:t.tabParams.endTime,callback:function(a){t.$set(t.tabParams,"endTime",a)},expression:"tabParams.endTime"}})],1),e("div",{staticClass:"tab-item"},[e("span",{staticClass:"tab-title"},[t._v("是否启用")]),e("el-radio",{staticClass:"radio",attrs:{label:1},model:{value:t.tabParams.status,callback:function(a){t.$set(t.tabParams,"status",a)},expression:"tabParams.status"}},[t._v("启用")]),e("el-radio",{staticClass:"radio",attrs:{label:0},model:{value:t.tabParams.status,callback:function(a){t.$set(t.tabParams,"status",a)},expression:"tabParams.status"}},[t._v("禁用")])],1)],2),e("div",{staticClass:"bot-btn"},[e("a",{staticClass:"btn-main",attrs:{href:"javascript:;"},on:{click:function(a){return t.saveTab()}}},[t._v("保存")]),e("router-link",{staticClass:"btn-fill-main",attrs:{to:{name:"电脑端"}}},[t._v("返回")])],1),t.showCategoryModal?e("selectCategory",{attrs:{selected:t.selectedCateList},on:{dialogCategory:t.dialogCategory,dialogCategoryCancel:t.dialogCategoryCancel}}):t._e(),t.showBrandModal?e("selectBrand",{attrs:{selected:t.selectedBrandList},on:{dialogBrand:t.dialogBrand,dialogBrandCancel:t.dialogBrandCancel}}):t._e()],1)},i=[],n=(e("7f7f"),e("ac6a"),e("a4d1")),o=e("8d0f"),c={components:{selectCategory:n["a"],selectBrand:o["a"]},data:function(){return{id:"",showCategoryModal:!1,showBrandModal:!1,cateList:[],brandList:[],shopList:[],channelList:[],currentCateItem:-1,selectedCateList:[],currentBrandItem:-1,selectedBrandList:[],pickerOption:{disabledDate:function(t){return t.getTime()<Date.now()-864e5}},tabParams:{id:"",title:"",startTime:"",endTime:"",status:0,conditionList:[{cateList:[],brandList:[],shopIdList:[],channelTypeList:[]}]}}},created:function(){var t=this;this.id=this.$route.params.id||"",console.log(this.id),this.api.store.allShop({}).then(function(a){t.shopList=a.data}),this.api.goods.channel({}).then(function(a){t.channelList=a.data}),this.id&&this.getDetail()},methods:{getDetail:function(){var t=this;this.api.category.thirdCate({}).then(function(a){t.cateList=a.data.dataList,t.api.brand.letterList({}).then(function(a){t.brandList=a.data,t.api.system.tabDetail({id:t.id}).then(function(a){t.tabParams.id=a.data.id,t.tabParams.title=a.data.title,t.tabParams.startTime=a.data.startTime,t.tabParams.endTime=a.data.endTime,t.tabParams.status=a.data.status,t.tabParams.conditionList=a.data.conditionList,t.tabParams.conditionList.forEach(function(a){t.$set(a,"cateList",[]),t.$set(a,"brandList",[]),a.categoryUnitList.forEach(function(e){t.cateList.forEach(function(t){t.id===e.cateId3&&a.cateList.push({name:t.name,id:t.id})})}),a.brandIdList.forEach(function(e){t.brandList.forEach(function(t){t.id===e&&a.brandList.push(t)})})})})})})},addConditionItem:function(){this.tabParams.conditionList.push({cateList:[],brandList:[],shopIdList:[],channelTypeList:[]})},removeConditionItem:function(t){this.tabParams.conditionList.splice(t,1)},selectItemCate:function(t,a){this.currentCateItem=this.copy(a),this.selectedCateList=t.cateList||[],this.showCategoryModal=!0},removeItemCate:function(t,a){t.cateList.splice(a,1)},selectItemBrand:function(t,a){this.currentBrandItem=this.copy(a),this.selectedBrandList=t.brandList||[],this.showBrandModal=!0},removeItemBrand:function(t,a){t.brandList.splice(a,1)},dialogCategory:function(t){this.currentCateItem>-1?(this.tabParams.conditionList[this.currentCateItem].cateList=t.categoryList,this.showCategoryModal=!1,this.currentCateItem=-1):this.$message.info({message:"系统错误",showClose:!0})},dialogCategoryCancel:function(){this.showCategoryModal=!1},dialogBrand:function(t){this.currentBrandItem>-1?(this.tabParams.conditionList[this.currentBrandItem].brandList=t.brandList,this.showBrandModal=!1,this.currentBrandItem=-1):this.$message.info({message:"系统错误",showClose:!0})},dialogBrandCancel:function(){this.showBrandModal=!1},saveTab:function(){var t=this,a=this.copy(this.tabParams),e=new Date(a.startTime),s=new Date(a.endTime);if(e>=s)return this.$message.info("结束时间应在开始时间之后"),0;a.conditionList.forEach(function(t){t.categoryUnitList=[],t.brandIdList=[],t.cateList&&t.cateList.length>0&&t.cateList.forEach(function(a){t.categoryUnitList.push({cateId1:0,cateId2:0,cateId3:a.id})}),t.brandList&&t.brandList.length>0&&t.brandList.forEach(function(a){t.brandIdList.push(a.id)}),delete t.cateList,delete t.brandList,delete t.empty}),a.conditionList=JSON.stringify(a.conditionList),a.id?this.api.system.editTab(a).then(function(a){t.$message.success({message:a.message,showClose:!0}),t.$router.push({name:"电脑端"})}):this.api.system.addTab(a).then(function(a){t.$message.success({message:a.message,showClose:!0}),t.$router.push({name:"电脑端"})})}}},r=c,l=(e("72eb"),e("2877")),d=Object(l["a"])(r,s,i,!1,null,"7aa76787",null);a["default"]=d.exports}}]);
//# sourceMappingURL=chunk-833192d8.8a58bc43.js.map