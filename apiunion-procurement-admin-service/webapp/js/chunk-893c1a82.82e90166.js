(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-893c1a82"],{7862:function(t,e,s){},"7d73":function(t,e,s){},"8d0f":function(t,e,s){"use strict";var i=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"select-brand"},[s("el-dialog",{attrs:{title:"品牌选择:",visible:t.brandShow,width:"815px","before-close":t.cancelBrand},on:{"update:visible":function(e){t.brandShow=e}}},[s("div",{staticClass:"dialog-form border-bottom"},[s("div",{staticClass:"search-item"},[s("span",{staticClass:"title"},[t._v("品牌筛选：")]),s("input",{directives:[{name:"model",rawName:"v-model",value:t.brandName,expression:"brandName"}],staticClass:"form-control",attrs:{type:"text",placeholder:"请输入品牌名称"},domProps:{value:t.brandName},on:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.getBrand(e)},input:function(e){e.target.composing||(t.brandName=e.target.value)}}})]),s("div",{staticClass:"search-item"},[s("span",{staticClass:"title"},[t._v("品牌英文名筛选：")]),t._l(t.letterList,function(e,i){return s("span",{key:i+"letter",staticClass:"letter",class:{active:t.currentLetter===e},on:{click:function(s){return t.selectLetter(e)}}},[t._v(t._s(e))])})],2)]),s("div",{staticClass:"dialog-form"},[s("div",{staticClass:"search-item selected－brand"},[s("span",{staticClass:"title"},[t._v("已选品牌：")]),t._l(t.selectedBrandList,function(e,i){return s("span",{key:i+"selected",staticClass:"selected-item"},[t._v("\n            "+t._s(e.name)+"\n            "),s("a",{attrs:{href:"javascript:;"},on:{click:function(e){return t.removeSelect(i)}}},[t._v("X")])])})],2),s("div",{staticClass:"search-item selected-box"},t._l(t.searchBrandList,function(e,i){return s("span",{key:i+"brand",staticClass:"brand-item"},[s("a",{staticClass:"link",on:{click:function(s){return t.selectItem(e)}}},[t._v(t._s(e.name))])])}),0)]),s("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[s("button",{staticClass:"btn-main",on:{click:function(e){return t.exportBrand()}}},[t._v("确定")]),s("button",{staticClass:"btn-fill-grey-main",on:{click:function(e){return t.cancelBrand()}}},[t._v("取消")])])])],1)},n=[],c=(s("ac6a"),{name:"selectBrand",props:{multiply:{default:!0},selected:{default:""}},data:function(){return{brandShow:!0,searchBrandList:[],selectedBrandList:[],brandName:"",currentLetter:"",letterList:["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","#"]}},created:function(){this.init()},methods:{showDialog:function(){this.brandShow=!0},init:function(){this.selected.length>0&&(this.selectedBrandList=this.copy(this.selected)),this.getBrand()},getBrand:function(){var t=this;this.api.brand.letterList({name:this.brandName,letter:this.currentLetter}).then(function(e){t.searchBrandList=e.data})},selectLetter:function(t){this.currentLetter===t?this.currentLetter="":this.currentLetter=t,this.getBrand()},selectItem:function(t){if(this.multiply)if(this.selectedBrandList.length>0){var e=!1;this.selectedBrandList.forEach(function(s){s.id===t.id&&(e=!0)}),e||this.selectedBrandList.push(t)}else this.selectedBrandList.push(t);else this.selectedBrandList=[],this.selectedBrandList.push(t)},removeSelect:function(t){this.selectedBrandList.splice(t,1)},exportBrand:function(){0!==this.selectedBrandList.length?this.$emit("dialogBrand",{brandList:this.selectedBrandList}):this.$message.info({message:"请选择品牌",showClose:!0})},cancelBrand:function(){this.brandShow=!0,this.$emit("dialogBrandCancel")}}}),a=c,r=(s("b557"),s("2877")),l=Object(r["a"])(a,i,n,!1,null,"3f6ec815",null);e["a"]=l.exports},a4d1:function(t,e,s){"use strict";var i=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"select-category"},[s("el-dialog",{attrs:{title:"类目选择:",visible:t.showCategory,width:"815px","before-close":t.cancelCategory},on:{"update:visible":function(e){t.showCategory=e}}},[s("div",{staticClass:"selected-cate"},[s("span",{staticClass:"title"},[t._v("已选类目")]),t._l(t.selectedCategoryList,function(e,i){return s("span",{key:e.id,staticClass:"item"},[t._v("\n        "+t._s(e.name)+"\n        "),s("span",{staticClass:"remove-select",on:{click:function(e){return t.removeSelected(i)}}},[t._v("X")])])})],2),s("div",{staticClass:"dialog-form"},[s("div",{staticClass:"left-bar"},[t._l(t.cateTree,function(e){return s("div",{key:e.id,staticClass:"first-item",class:{active:t.currentBar.id===e.id},on:{click:function(s){return s.stopPropagation(),t.selectFirst(e)}}},[t._v("\n          "+t._s(e.name)+"\n          ")])}),s("div",{staticClass:"selectAll",on:{click:function(e){return e.stopPropagation(),t.allSelect()}}},[s("div",{staticClass:"dot",class:{active:t.firstAllSelect}}),s("span",[t._v("全选")])])],2),s("div",{staticClass:"right-content"},t._l(t.currentBar.children,function(e){return s("div",{key:e.id,staticClass:"second-item"},[s("div",{staticClass:"second-item-title"},[s("h2",[t._v(t._s(e.name))]),s("div",{directives:[{name:"show",rawName:"v-show",value:!e.checked,expression:"!item.checked"}],staticClass:"selectAll",on:{click:function(s){return s.stopPropagation(),t.selectAll(2,e,!0)}}},[s("div",{staticClass:"dot",class:{active:e.checked}}),s("span",[t._v("全选")])]),s("div",{directives:[{name:"show",rawName:"v-show",value:e.checked,expression:"item.checked"}],staticClass:"selectAll",on:{click:function(s){return s.stopPropagation(),t.selectAll(2,e,!1)}}},[s("div",{staticClass:"dot",class:{active:e.checked}}),s("span",[t._v("全选")])])]),s("div",{staticClass:"third-content"},t._l(e.children,function(i){return s("span",{key:i.id,staticClass:"third-item",on:{click:function(s){return t.selectThird(i,e.parentId)}}},[t._v(t._s(i.name))])}),0)])}),0)]),s("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[s("button",{staticClass:"btn-main",on:{click:function(e){return t.exportCategory()}}},[t._v("确定")]),s("button",{staticClass:"btn-fill-grey-main",on:{click:function(e){return t.cancelCategory()}}},[t._v("取消")])])])],1)},n=[],c=(s("ac6a"),{props:{multiply:{default:!0},selected:{default:[]}},data:function(){return{showCategory:!0,cateTree:[],currentBar:{},selectedCategoryList:[],firstAllSelect:!1}},created:function(){this.selected.length>0&&(this.selectedCategoryList=this.copy(this.selected)),this.getCateTree()},methods:{getCateTree:function(){var t=this;this.api.category.tree({}).then(function(e){t.cateTree=e.data,t.cateTree.forEach(function(e){t.$set(e,"checked",!1),e.children&&e.children.length>0&&e.children.forEach(function(e){t.$set(e,"checked",!1),e.children&&e.children.length>0&&e.children.forEach(function(e){t.$set(e,"checked",!1)})})}),t.currentBar=t.cateTree[0],t.console("cate:",t.cateTree)})},selectAll:function(t,e,s){var i=this;if(1===t)if(e.checked=s,!0===s)e.children&&e.children.length>0&&e.children.forEach(function(t){t.checked=!0,t.children&&t.children.length>0&&t.children.forEach(function(e){i.selectThird(e,t.parentId)})});else{var n=[];if(e.children&&e.children.length>0&&e.children.forEach(function(t){t.checked=!1,t.children&&t.children.length>0&&t.children.forEach(function(t){n.push(t.id)})}),this.selectedCategoryList.length>0&&n.length>0){var c=[];this.selectedCategoryList.forEach(function(t){-1===n.indexOf(t.id)&&c.push(t)}),this.selectedCategoryList=c}}else if(e.checked=s,!0===s)e.children&&e.children.length>0&&e.children.forEach(function(t){i.selectThird(t,e.parentId)});else{var a=[];if(e.children&&e.children.length>0&&e.children.forEach(function(t){a.push(t.id)}),this.selectedCategoryList.length>0&&a.length>0){var r=[];this.selectedCategoryList.forEach(function(t){-1===a.indexOf(t.id)&&r.push(t)}),this.selectedCategoryList=r}}},allSelect:function(){var t=this;this.firstAllSelect=!this.firstAllSelect,this.firstAllSelect?this.cateTree.forEach(function(e){t.selectAll(1,e,!0)}):this.selectedCategoryList=[]},selectFirst:function(t){this.currentBar=t},selectThird:function(t,e){if(t.firstId=e,this.multiply){var s=!1;this.selectedCategoryList.forEach(function(e){e.id===t.id&&(s=!0)}),s||this.selectedCategoryList.push(t)}else 0!==this.selectedCategoryList.length&&(this.selectedCategoryList=[]),this.selectedCategoryList.push(t)},removeSelected:function(t){this.selectedCategoryList.splice(t,1)},exportCategory:function(){0!==this.selectedCategoryList.length?this.$emit("dialogCategory",{categoryList:this.selectedCategoryList}):this.$message.info({message:"请选择至少一个类目",showClose:!0})},cancelCategory:function(){this.showCategory=!0,this.$emit("dialogCategoryCancel")}}}),a=c,r=(s("bf41"),s("2877")),l=Object(r["a"])(a,i,n,!1,null,"280844be",null);e["a"]=l.exports},b557:function(t,e,s){"use strict";var i=s("7862"),n=s.n(i);n.a},bf41:function(t,e,s){"use strict";var i=s("7d73"),n=s.n(i);n.a}}]);
//# sourceMappingURL=chunk-893c1a82.82e90166.js.map