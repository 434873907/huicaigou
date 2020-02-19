<template>
  <div class="closet-set">
    <div class="title">
      <div class="name">壁橱布局</div>
      <div class="link underline pull-right">恢复默认</div>
      <div class="link underline pull-right" @click="save()">确定</div>
    </div>
    <div class="module-content">
      <div class="goods-source">
        <div class="form-wrap">
          <div class="form-name form-name-middle">添加商品来源</div>
          <select class="form-control" v-model="formData.styleAndData.dataType">
            <option value="">请选择</option>
            <option v-for="jump in jumpList" :key='jump.code' :value="jump.code">{{jump.remark}}</option>
          </select>
        </div>
        <div class="form-wrap" v-if="formData.styleAndData.dataType == 8">
          <div class="form-name form-name-middle">关键词</div>
          <input type="text" class="form-control" v-model="formData.styleAndData.headerImage.jump.jumpData">
        </div>
        <div class="form-wrap" v-if="formData.styleAndData.dataType == 24">
          <div class="form-name form-name-middle">链接地址</div>
          <input type="text" class="form-control" v-model="formData.styleAndData.headerImage.jump.jumpData">
        </div>
        <div class="form-wrap" v-show="formData.styleAndData.dataType == 26">
          <div class="form-name form-name-middle">活动ID</div>
          <input type="number" class="form-control" v-model="formData.styleAndData.headerImage.jump.jumpData" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))">
        </div>
        <div class="form-wrap" v-show="formData.styleAndData.dataType == 7">
          <div class="form-name form-name-middle">品牌ID</div>
          <input type="number" class="form-control" v-model="formData.styleAndData.headerImage.jump.jumpData" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))">
        </div>
        <div class="form-wrap" v-show="formData.styleAndData.dataType == 6">
          <div class="form-name form-name-middle">商品ID</div>
          <input type="number" class="form-control" v-model="formData.styleAndData.headerImage.jump.jumpData" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))">
        </div>
        <div class="form-wrap" v-show="formData.styleAndData.dataType == 28">
          <div class="form-name form-name-middle">分类选择</div>
          <div class="form-group">
            <div class="flex-wrap">
              <select class="form-control" v-model="categoryData.first.id">
                <option value="">请选择</option>
                <option v-for="category in categoryData.first.data" :key='category.id' :value="category.id">{{category.name}}</option>
              </select>
              <select class="form-control" v-model="categoryData.second.id">
                <option value="">请选择</option>
                <option v-for="category in categoryData.second.data" :key='category.id' :value="category.id">{{category.name}}</option>
              </select>
              <select class="form-control" v-model="categoryData.third.id">
                <option value="">请选择</option>
                <option v-for="category in categoryData.third.data" :key='category.id' :value="category.id">{{category.name}}</option>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="form-wrap">
        <div class="form-name form-name-small">标题名称</div>
        <input type="text" maxlength="8" class="form-control" v-model="formData.title">
        <div class="text-type">
          <span>对齐</span>
          <img :class="{active:formData.styleAndData.titleStyle.align.horizontalAlign===1}" @click="titlePosition(1)" src="~assets/system/mobile/textLeft.png" draggable="false">
          <img :class="{active:formData.styleAndData.titleStyle.align.horizontalAlign==2}" @click="titlePosition(2)" src="~assets/system/mobile/textCenter.png" draggable="false">
          <img :class="{active:formData.styleAndData.titleStyle.align.horizontalAlign==3}" @click="titlePosition(3)" src="~assets/system/mobile/textRight.png" draggable="false">
        </div>
      </div>
      <div class="form-wrap" v-if="false">
        <div class="form-name form-name-small">标题风格</div>
        <el-radio v-model="formData.styleAndData.titleStyleType"
          :label='radio.id' v-for="radio in styleSelect.titleStyle" :key="radio.id">
          {{radio.styleName}}
        </el-radio>
      </div>
      <div class="layout">
        <div class="layout-name">模块布局</div>
        <div class="layout-img"></div>
      </div>
      <div class="add-bg" style="margin-bottom: 0">
        <upload class="img-alt" v-on:uploadSuccess="uploadSuccess">
          <div class="up-tip" v-if="!formData.styleAndData.headerImage.url">
            <svg class="icon" aria-hidden="true">
                <use xlink:href="#icon-img-default"></use>
            </svg>
            <div class="link underline">添加一个背景图</div>
          </div>
          <img :src="formData.styleAndData.headerImage.url" v-if="formData.styleAndData.headerImage.url">
        </upload>
        <div class="tips">(建议尺寸为750X任意高度，大小不超过1M)</div>
      </div>
      <div class="other-set-wrap">
        <div class="form-wrap">
          <div class="form-name">模块其他设置</div>
          <el-radio v-model="formData.styleAndData.styleType"
            :label='radio.id' v-for="radio in styleSelect.other" :key="radio.id">
            {{radio.styleName}}
          </el-radio>
          <el-radio v-model="formData.styleAndData.styleType" :label='0'>自定义</el-radio>
        </div>
        <div class="other-set" v-show="formData.styleAndData.styleType == 0">
            <div class="form-wrap form-flex-wrap">
              <div class="form-name form-name-middle">模块间距</div>
              <div class="form-flex">
                <span>上</span>
                <input maxlength="3" type="text" class="form-control" v-model="formData.styleAndData.paddingArr[1]">
                <span>下</span>
                <input maxlength="3" type="text" class="form-control" v-model="formData.styleAndData.paddingArr[3]">
                <span>左</span>
                <input maxlength="3" type="text" class="form-control" v-model="formData.styleAndData.paddingArr[0]">
                <span>右</span>
                <input maxlength="3" type="text" class="form-control" v-model="formData.styleAndData.paddingArr[2]">
              </div>
            </div>
            <div class="form-wrap">
              <div class="form-name form-name-middle">模块背景</div>
              <el-color-picker v-model="formData.styleAndData.backgroundStyle.color"></el-color-picker>
              <upload v-on:uploadSuccess="setUpload" class="form-name add-img">
                <div v-show="!formData.styleAndData.backgroundStyle.imageUrl">上传图片</div>
                <el-popover
                  v-show="formData.styleAndData.backgroundStyle.imageUrl"
                  placement="right"
                  width="200"
                  trigger="hover">
                  <img style="width:100%;height:100%" :src="formData.styleAndData.backgroundStyle.imageUrl">
                  <span slot="reference">查看图片</span>
                </el-popover>
              </upload>
              <div class="form-name add-img" v-show="formData.styleAndData.backgroundStyle.imageUrl"
                @click="formData.styleAndData.backgroundStyle.imageUrl=''">删除图片</div>
            </div>
            <div class="form-wrap">
              <div class="form-name form-name-middle">分割线显示</div>
              <el-radio v-model="formData.styleAndData.hideDividers" :label='0'>显示</el-radio>
              <el-radio v-model="formData.styleAndData.hideDividers" :label='1'>不显示</el-radio>
            </div>
            <div class="form-wrap">
              <div class="form-name form-name-middle">分割线高度</div>
              <input type="text" class="form-control" v-model="formData.styleAndData.divider.height">
            </div>
            <div class="form-wrap">
              <div class="form-name form-name-middle">分割线颜色</div>
              <el-color-picker v-model="formData.styleAndData.divider.color"></el-color-picker>
            </div>
            <div class="form-wrap">
              <div class="form-name">海报布局设置</div>
            </div>
            <div class="form-wrap">
              <div class="form-name">列表上移高度</div>
              <input type="text" class="form-control" v-model="formData.styleAndData.offset">
              <div class="form-name">列表右移宽度</div>
              <input type="text" class="form-control" v-model="formData.styleAndData.footerPadding">
            </div>
            <div class="form-wrap">
              <div class="form-name">商品名称颜色</div>
              <el-color-picker v-model="formData.styleAndData.itemStyle.nameStyle.foreground"></el-color-picker>
              <div class="form-name">商品价格颜色</div>
              <el-color-picker v-model="formData.styleAndData.itemStyle.priceStyle.foreground"></el-color-picker>
            </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    msg: String
  },
  data() {
    return {
      msgReg: {
        '26': '填写活动ID',
        '28': '选择类目',
        '7': '填写品牌ID',
        '8': '填写搜索关键词',
        '6': '填写商品ID',
        '24': '填写链接'
      },
      msgTitleReg: {
        '26': '活动ID',
        '7': '品牌ID',
        '8': '关键词',
        '6': '商品ID',
        '24': '链接'
      },
      jumpList:[],
      categoryData:{
        first:{
          id:'',
          data:[]
        },
        second:{
          id:'',
          data:[]
        },
        third:{
          id:'',
          data:[]
        }
      },
      styleSelect:{
        titleStyle:[],
        displayStyle:[],
        other:[]
      },
      formData:{
        layoutType:2,
        name:'壁橱',
        module:'closet',
        title:'',
        styleAndData:{
          styleType:0,
          padding:'',
          paddingArr:['','','',''],
          backgroundStyle:{
            imageUrl:'',
            color:''
          },
          hideDividers:0,
          divider:{
            height:'',
            color:''
          },
          titleStyleType:0,
          titleStyle:{
            align:{
              horizontalAlign:0
            }
          },
          headerImage:{
            url:'',
            jump:{
                jumpType:'',
                jumpData:''
              }
          },
          footerPadding:'',
          offset:'',
          showShadow:'',
          itemStyle:{
            nameStyle:{foreground:''},
            priceStyle:{foreground:''}
          },
          dataTypeName:'',
          dataType:'',
          value:''
        }
      }
    }
  },
  methods:{
    getEditData(data){
      this.console(data);
      this.formData = this.extend(this.formData,data);
    },
    getFirstList() {
      this.api.category.categoryNode({parentId:0}).then((res) => {
        this.categoryData.first.data = res.data;
        let type = this.formData.styleAndData.value.split('-');
        this.categoryData.first.id = type[0] || '';
        this.categoryData.second.id = type[1] || '';
        this.categoryData.third.id = type[2] || '';
      });
    },
    setUpload(data){
      this.formData.styleAndData.backgroundStyle.imageUrl = data.data;
    },
    uploadSuccess(data){
      this.formData.styleAndData.headerImage.url = data.data;
    },
    titlePosition(type){
      this.formData.styleAndData.titleStyle.align.horizontalAlign = type;
    },
    save(){
      this.formData.styleAndData.headerImage.jump.jumpType = this.formData.styleAndData.dataType;
      let type = this.formData.styleAndData.dataType || ''
      let msg = this.msgReg[type] || ''
      switch (type) {
          case 28 :
            if (this.categoryData.first.id && this.categoryData.second.id && this.categoryData.third.id) {
              msg = ''
            } else {
              msg ='请选择类目'
            }
          break
      }
      if (!this.formData.styleAndData.headerImage.jump.jumpData) {
         if (!type) {
           this.$message.error('请选择添加商品来源')
           return
         }
         if (msg) {
            msg= '请' + msg
            this.$message.error(msg)
            return
         }
      } else {
        if (type == 24) {
          let urlReg = /(https):\/\/([\w.]+\/?)\S*/
          if (!urlReg.test(this.formData.styleAndData.headerImage.jump.jumpData)) {
            this.$message.error('请输入正确的链接地址（如https://www.xxx.com)')
            return
          }
        }
        if (type !=28 && type != 24 && type != 8) {
          if (isNaN(this.formData.styleAndData.headerImage.jump.jumpData)) {
            this.$message.error(msg)
            return
          }
        }
      }
      if(this.formData.styleAndData.dataTypeName == '类目详情'){
        this.formData.styleAndData.headerImage.jump.jumpData = `${this.categoryData.first.id}-${this.categoryData.second.id}-${this.categoryData.third.id}` || this.formData.styleAndData.value;
      }
      this.formData.styleAndData.value = this.formData.styleAndData.headerImage.jump.jumpData;
      this.formData.styleAndData.padding = this.formData.styleAndData.paddingArr.toString();
      this.$emit('listenSet',this.formData);
    }
  },
  created() {
    this.api.index.jumpType({layoutType:this.formData.layoutType}).then(result=>{
      this.jumpList = result.data;
      this.jumpList.forEach(item => {
        if(item.code == this.formData.styleAndData.dataType && item.remark == '类目详情'){
          this.formData.styleAndData.dataTypeName = item.remark;
          this.getFirstList();
        }
      });
    });
    this.api.index.getModuleStyle({layoutType:this.formData.layoutType}).then(result=>{
      if(result.data){
        result.data.forEach(item=>{
          switch(item.part){
            case 1:
              this.styleSelect.other.push(item);
            break;
            case 2:
              this.styleSelect.titleStyle.push(item);
            break;
            case 3:
              this.styleSelect.displayStyle.push(item);
            break;
          }
        });
        this.formData.styleAndData.titleStyleType = this.styleSelect.titleStyle[0].id;
        this.formData.styleAndData.styleType = this.styleSelect.other[0].id;
      }
    });
  },
  watch: {
    'categoryData.first.id': function(val){
      if(!val){
          return
      }
      this.api.category.categoryNode({parentId:val}).then((res) => {
        this.categoryData.second.data = res.data;
      });
    },
    'categoryData.second.id': function(val){
      if(!val){
          return
      }
      this.api.category.categoryNode({parentId:val}).then((res) => {
        this.categoryData.third.data = res.data;
      });
    },
    'formData.styleAndData.dataType':function(val){
      if(!val){
          return
      }
      this.jumpList.forEach(item => {
        if(item.code == val){
          this.formData.styleAndData.dataTypeName = item.remark;
          !this.categoryData.first.data.length && item.remark == '类目详情' && this.getFirstList();
        }
      });
    }
  }
}
</script>
<style lang='scss'>
  .closet-set{
    background: #fff;
    border-radius: 4px;
    .goods-source{
      margin-bottom: 15px;
      padding: 10px;
      border:1px dotted $org-color;
      border-radius: 2px;
      .form-group{
        .form-control{
          margin-right: 10px;
          max-width: 110px;
          &:last-child{
            margin-right: 0;
          }
        }
      }
    }
    .layout{
      margin-bottom: 15px;
      .layout-name{
        display: inline-block;
        margin-right: 10px;
        vertical-align: top;
      }
      .layout-img{
        display: inline-block;
        width: 361px;
        height: 237px;
        background: url(~assets/system/mobile/closet/closet.png) top center;
      }
    }
    .other-set{
      .form-wrap{
        .form-name:nth-of-type(2){
          margin-left: 10px;
        }
      }
    }
  }
  .tips{
    color: #999999;
    font-size: 14px;
    text-align: center;
    box-sizing: border-box;
    padding-top: 10px;
  }
</style>
