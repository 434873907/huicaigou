<template>
  <div class="flashSale-set">
    <div class="title">
      <div class="name">团购</div>
      <div class="link underline pull-right">恢复默认</div>
      <div class="link underline pull-right" @click="save()">确定</div>
    </div>
    <div class="module-content">
      <div class="form-wrap">
        <div class="form-name form-name-middle">商品来源</div>
        <div class="form-name">团购商品</div>
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
        <el-radio v-model="formData.styleAndData.titleStyleType" :label='0'>大字</el-radio>
        <el-radio v-model="formData.styleAndData.titleStyleType" :label='1'>精致</el-radio>
      </div>
      <div class="form-wrap">
        <div class="form-name form-name-middle">商品展示风格</div>
        <el-radio v-model="formData.styleAndData.productDisplayStyle" :label='0'>宽松</el-radio>
        <el-radio v-model="formData.styleAndData.productDisplayStyle" :label='1'>紧凑</el-radio>
        <el-radio v-model="formData.styleAndData.productDisplayStyle" :label='9'>自定义</el-radio>
      </div>
      <div class="other-set" v-show="formData.styleAndData.productDisplayStyle == 9">
        <div class="form-wrap">
          <div class="form-name">商品图片宽高比</div>
        </div>
        <div class="form-wrap">
          <div class="form-name form-name-middle">商品间距</div>
          <input type="text" class="form-control" v-model="formData.styleAndData.productSpacing">
        </div>
        <div class="form-wrap">
          <div class="form-name form-name-middle">品名颜色</div>
          <el-color-picker v-model="formData.styleAndData.titleColor"></el-color-picker>
          <div class="form-name form-name-middle">价格颜色</div>
          <el-color-picker v-model="formData.styleAndData.priceColor"></el-color-picker>
        </div>
        <div class="form-wrap">
          <div class="form-name form-name-middle">倒计时颜色</div>
          <el-color-picker v-model="formData.styleAndData.countdownColor"></el-color-picker>
        </div>
      </div>
      <div class="other-set-wrap">
        <div class="form-wrap">
          <div class="form-name">模块其他设置</div>
          <el-radio v-model="formData.styleAndData.styleType" :label='9'>默认</el-radio>
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
      radio:'',
      formData:{
        layoutType:8,
        name:'团购',
        module:'flashSale',
        title:'',
        styleAndData:{
          styleType: 0,
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
          displayContent:[1,2,3,4],
          titleStyleType:0,
          titleStyle:{
            align:{
              horizontalAlign:0
            }
          },
          productDisplayStyle:0
        }
      }
    }
  },
  methods:{
    getEditData(data){
      this.formData = this.extend(this.formData,data);
    },
    setUpload(data){
      this.formData.styleAndData.backgroundStyle.imageUrl = data.data;
    },
    titlePosition(type){
      this.formData.styleAndData.titleStyle.align.horizontalAlign = type;
    },
    save(){
      this.formData.styleAndData.padding = this.formData.styleAndData.paddingArr.toString();
      this.$emit('listenSet',this.formData);
    }
  },
  created() {

  }
}
</script>
<style lang='scss'>
  .flashSale-set{
    background: #fff;
    border-radius: 4px;
  }
</style>
