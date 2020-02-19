<template>
  <div class="goodsList-set">
    <div class="title">
      <div class="name">商品列表</div>
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
        <!-- <div class="form-wrap" v-show="formData.styleAndData.dataType == 26">
          <div class="form-name form-name-middle">活动ID</div>
          <input v-model="formData.styleAndData.value" class="form-control">
        </div> -->
        <div class="form-wrap" v-if="formData.styleAndData.dataType == 8">
          <div class="form-name form-name-middle">关键词</div>
          <input type="text" class="form-control" v-model="formData.styleAndData.value">
        </div>
        <div class="form-wrap" v-if="formData.styleAndData.dataType == 24">
          <div class="form-name form-name-middle">链接地址</div>
          <input type="text" class="form-control" v-model="formData.styleAndData.value">
        </div>
        <div class="form-wrap" v-show="formData.styleAndData.dataType == 26">
          <div class="form-name form-name-middle">活动ID</div>
          <input type="number" class="form-control" v-model="formData.styleAndData.value" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))">
        </div>
        <div class="form-wrap" v-show="formData.styleAndData.dataType == 7">
          <div class="form-name form-name-middle">品牌ID</div>
          <input type="number" class="form-control" v-model="formData.styleAndData.value" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))">
        </div>
        <div class="form-wrap" v-show="formData.styleAndData.dataType == 6" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))">
          <div class="form-name form-name-middle">商品ID</div>
          <input type="number" class="form-control" v-model="formData.styleAndData.value" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))">
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
        <div class="form-name form-name-middle">标题名称</div>
        <input type="text" maxlength="8" class="form-control" v-model="formData.title">
        <div class="text-type">
          <span>对齐</span>
          <img :class="{active:formData.styleAndData.titleStyle.align.horizontalAlign===1}" @click="titlePosition(1)" src="~assets/system/mobile/textLeft.png" draggable="false">
          <img :class="{active:formData.styleAndData.titleStyle.align.horizontalAlign==2}" @click="titlePosition(2)" src="~assets/system/mobile/textCenter.png" draggable="false">
          <img :class="{active:formData.styleAndData.titleStyle.align.horizontalAlign==3}" @click="titlePosition(3)" src="~assets/system/mobile/textRight.png" draggable="false">
        </div>
      </div>
      <div class="form-wrap" v-if="false">
        <div class="form-name form-name-middle">标题风格</div>
        <el-radio v-model="formData.styleAndData.titleStyleType" class="radio-big-width"
          :label='radio.id' v-for="radio in styleSelect.titleStyle" :key="radio.id">
          {{radio.styleName}}
        </el-radio>
      </div>
      <div class="form-wrap">
        <div class="form-name form-name-middle">列表样式</div>
        <el-radio v-model="formData.styleAndData.listStyleType" :label='0' class="radio-big-width">单列</el-radio>
        <el-radio v-model="formData.styleAndData.listStyleType" :label='1'>双列</el-radio>
      </div>
      <el-checkbox-group v-if="false" v-model="formData.styleAndData.displayContent">
        <div class="form-wrap">
          <div class="form-name form-name-middle">显示内容</div>
          <div class="checkbox-group-wrap">
            <el-checkbox :label='item.code' class="radio-big-width" v-for="(item,index) in content" :key="index+'No'">{{item.remark}}</el-checkbox>
          </div>
        </div>
      </el-checkbox-group>
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
              <div class="form-name form-name-middle">行间距</div>
              <input type="text" class="form-control" v-model="formData.styleAndData.rowSpacing">
              <div class="form-name form-name-small">列间距</div>
              <input type="text" class="form-control" v-model="formData.styleAndData.columnSpacing">
            </div>
            <div class="form-wrap" v-if="false">
              <div class="form-name form-name-middle" >品名颜色</div>
              <el-color-picker v-if="false" v-model="formData.styleAndData.itemStyle.titleStyle.foreground"></el-color-picker>
              <div class="form-name form-name-small">价格颜色</div>
              <el-color-picker v-model="formData.styleAndData.itemStyle.priceStyle.foreground"></el-color-picker>
            </div>
            <div class="form-wrap" v-if="false">
              <div class="form-name form-name-middle">折扣字体大小</div>
              <input type="text" class="form-control" v-model="formData.styleAndData.itemStyle.discountStyle.fontSize">
              <div class="form-name form-name-small">折扣颜色</div>
              <el-color-picker v-model="formData.styleAndData.itemStyle.discountStyle.foreground"></el-color-picker>
            </div>
            <div class="form-wrap" v-if="false">
              <div class="form-name form-name-middle">折扣划线</div>
              <el-radio v-model="formData.styleAndData.itemStyle.discountStyle.lineThrough" :label='true'>是</el-radio>
              <el-radio v-model="formData.styleAndData.itemStyle.discountStyle.lineThrough" :label='false'>否</el-radio>
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
      styleSelect:{
        titleStyle:[],
        displayStyle:[],
        other:[]
      },
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
      content:[],
      formData:{
        layoutType:3,
        name:'商品列表',
        module:'goodsList',
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
          listStyleType:0,
          imgWidth:'',
          rowSpacing:'',
          columnSpacing:'',
          shopCart:'',
          itemStyle:{
            titleStyle:{foreground:''},
            priceStyle:{foreground:''},
            discountStyle:{
              foreground:'',
              fontSize:'',
              lineThrough:true
            }
          },
          displayContent:[],
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
    titlePosition(type){
      this.formData.styleAndData.titleStyle.align.horizontalAlign = type;
    },
    save(){
      if(this.formData.styleAndData.dataType == 28){
        this.formData.styleAndData.value = `${this.categoryData.first.id}-${this.categoryData.second.id}-${this.categoryData.third.id}` || this.formData.styleAndData.value;
        this.formData.firstCate = this.categoryData.first.id;
        this.formData.secondCate = this.categoryData.second.id;
        this.formData.thridCate = this.categoryData.third.id;
      }
      let type = this.formData.styleAndData.dataType || ''
      let msg = this.msgReg[type] || ''
         msg= '请' + msg
      if (!this.formData.styleAndData.value) {
         if (!type) {
           this.$message.error('请选择添加商品来源')
           return
         }
         this.$message.error(msg)
         return
      } else {
        if (type == 24) {
          let urlReg = /(https):\/\/([\w.]+\/?)\S*/
          if (!urlReg.test(this.formData.styleAndData.value)) {
            this.$message.error('请输入正确的链接地址（如https://www.xxx.com)')
            return
          }
        }
        if (type !=28 && type != 24 && type != 8) {
          if (isNaN(this.formData.styleAndData.value)) {
            this.$message.error(msg)
            return
          }
        }
      }
      this.formData.styleAndData.padding = this.formData.styleAndData.paddingArr.toString();
      this.$emit('listenSet',this.formData);
    }
  },
  created() {
    this.api.index.goodsListContent().then(result=>{
      this.content = result.data;
    });
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
  .goodsList-set{
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
          &:last-child{
            margin-right: 0;
          }
        }
      }
    }
    .checkbox-group-wrap{
      display: inline-block;
      width: 259px;
      vertical-align: top;
      .radio-big-width{
        width: 50%;
        overflow: hidden;
        text-overflow: ellipsis;
        margin: 0 0 10px;
      }
      .el-checkbox__inner{
        width: 14px;
        height: 14px;
        border-radius: 14px;
      }
    }
    .radio-big-width{
      width: 100px;
    }
  }
</style>
