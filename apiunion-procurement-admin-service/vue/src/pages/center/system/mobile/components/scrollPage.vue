<template>
  <div class="scrollPage-set">
    <div class="title">
      <div class="name">划动翻页</div>
      <div class="link underline pull-right">恢复默认</div>
      <div class="link underline pull-right" @click="save()">确定</div>
    </div>
    <div class="module-content">
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
        <el-radio v-model="formData.styleAndData.titleStyleType"
          :label='radio.id' v-for="radio in styleSelect.titleStyle" :key="radio.id">
          {{radio.styleName}}
        </el-radio>
      </div>
      <div class="form-wrap">
        <div class="form-name form-name-middle">展示风格</div>
        <el-radio v-model="formData.styleAndData.displayStyleType"
          :label='radio.id' v-for="radio in styleSelect.displayStyle" :key="radio.id">
          {{radio.styleName}}
        </el-radio>
        <el-radio v-model="formData.styleAndData.displayStyleType" :label='0'>自定义</el-radio>
      </div>
      <div class="other-set base-margin-bottom" v-show="formData.styleAndData.displayStyleType==0">
        <div class="form-wrap">
          <div class="form-name form-name-middle">单图占比</div>
          <input type="text" class="form-control" v-model="formData.styleAndData.imgWidthProportion">
          <div class="form-name base-margin-left">1表示单图占满</div>
        </div>
        <div class="form-wrap">
          <div class="form-name form-name-middle">单图宽高比</div>
          <input type="text" class="form-control" v-model="formData.styleAndData.proportion">
          <div class="form-name base-margin-left">宽/高</div>
        </div>
        <div class="form-wrap">
          <div class="form-name form-name-middle">图片间距</div>
          <input type="text" class="form-control" v-model="formData.styleAndData.columnSpacing">
          <!-- <div class="form-name base-margin-left">宽/高</div> -->
        </div>
        <div class="form-wrap">
          <div class="form-name form-name-middle">填充方式</div>
          <el-radio v-model="formData.styleAndData.scaleType" :label='0'>填充</el-radio>
          <el-radio v-model="formData.styleAndData.scaleType" :label='1'>按比例</el-radio>
        </div>
      </div>
<!--      <div class="btn-fill-org-main base-margin-bottom" @click="addData()">添加一页</div>-->
      <div style="display: flex;align-items: center">
        <div class="btn-fill-org-main base-margin-bottom" @click="addData()">添加一页</div>
        <div class="tips">(尺寸建议为640x350px，大小不超过1M)</div>
      </div>
      <div class="blue-wrap"  v-for="(item,index) in formData.styleAndData.imageList" :key="index + 'No'">
        <div class="el-icon-close pull-right" @click="deleteJump(index)"></div>
        <div class="img-wrap inline-block">
          <div class="img-alt">
            <img :src="item.url" v-if="item.url">
          </div>
          <upload :index="index" v-on:uploadSuccess="uploadSuccess" class="link underline">更换图片</upload>
        </div>
        <div class="form-content inline-block">
          <div class="form-wrap">
            <div class="form-name">跳转</div>
            <select class="form-control" v-model="item.jump.jumpType" @change="categoryChange(item,'first',0,true)">
              <option value="">请选择</option>
              <option v-for="jump in jumpList" :key='jump.code' :value="jump.code">{{jump.remark}}</option>
            </select>
          </div>
          <div class="form-wrap" v-show="item.jump.jumpType != 28">
            <div class="form-name" >{{msgTitleReg[item.jump.jumpType] || '活动ID'}}</div>
            <input type="number" v-if="item.jump.jumpType != 8 && item.jump.jumpType != 24" class="form-control" v-model="item.jump.jumpData" :disabled="!item.jump.jumpType"  :class="{'bg-gray':!item.jump.jumpType}" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))">
            <input type="text" v-else class="form-control" v-model="item.jump.jumpData" :disabled="!item.jump.jumpType"  :class="{'bg-gray':!item.jump.jumpType}">
          </div>
          <div class="form-wrap" v-show="item.jump.jumpType == 28">
            <div class="form-name">分类选择</div>
              <div class="form-group">
                <div class="flex-wrap">
                  <select class="form-control" v-model="item.categoryData.first.id" @change="categoryChange(item,'second',item.categoryData.first.id)">
                    <option value="">请选择</option>
                    <option v-for="category in item.categoryData.first.data" :key='category.id' :value="category.id">{{category.name}}</option>
                  </select>
                  <select class="form-control" v-model="item.categoryData.second.id" @change="categoryChange(item,'third',item.categoryData.second.id)">
                    <option value="">请选择</option>
                    <option v-for="category in item.categoryData.second.data" :key='category.id' :value="category.id">{{category.name}}</option>
                  </select>
                  <select class="form-control" v-model="item.categoryData.third.id">
                    <option value="">请选择</option>
                    <option v-for="category in item.categoryData.third.data" :key='category.id' :value="category.id">{{category.name}}</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
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
      formData:{
        layoutType:5,
        name:'划动翻页',
        module:'scrollPage',
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
          displayStyleType:0,
          imgWidthProportion:'',
          proportion:'',
          columnSpacing:'',
          scaleType:0,
          imageList:[{
            url:'',
            jump:{
              jumpType:'',
              jumpData:''
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
            }
          }]
        }
      }
    }
  },
  methods:{
    categoryChange(item,index,parentId,init=false){
      if(item.jump.jumpType == 28){
        this.api.category.categoryNode({parentId:parentId}).then((res) => {
          item.categoryData[index].data = res.data;
          if(!init){
            item.categoryData[index].id = '';
            if(index == 'second'){
              item.categoryData.third.data = '';
              item.categoryData.third.id = '';
            }
          }
        });
      }else{
        item.jump.jumpData = '';
      }
    },
    getFirstList(item) {
      this.api.category.categoryNode({parentId:0}).then((res) => {
        item.categoryData.first.data = res.data;
        let type = item.jump.jumpData.split('-');
        item.categoryData.first.id = type[0] || '';
        item.categoryData.second.id = type[1] || '';
        item.categoryData.third.id = type[2] || '';
        if(type[0]){
          this.categoryChange(item,'second',type[0],true)
        }
        if(type[1]){
          this.categoryChange(item,'third',type[1],true);
        }
      });
    },
    getEditData(data){
      this.formData = this.extend(this.formData,data);
      this.formData.styleAndData.imageList.forEach(item=>{
        this.$set(item,'categoryData',{
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
        });
        if(item.jump.jumpType == 28){
          this.getFirstList(item,true);
        }
      });
    },
    setUpload(data){
      this.formData.styleAndData.backgroundStyle.imageUrl = data.data;
    },
    uploadSuccess(result){
      this.formData.styleAndData.imageList[result.index].url = result.data;
    },
    deleteJump(index){
      this.formData.styleAndData.imageList.splice(index,1)
    },
    titlePosition(type){
      this.formData.styleAndData.titleStyle.align.horizontalAlign = type;
    },
    addData(){
      this.formData.styleAndData.imageList.push({
        url:'',
        jump:{
          jumpType:'',
          jumpData:''
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
          }
      });
    },
    save(){
      this.formData.styleAndData.padding = this.formData.styleAndData.paddingArr.toString();
      this.formData.styleAndData.imageList.forEach(val=>{
        if(val.jump.jumpType == 28){
          val.jump.jumpData = `${val.categoryData.first.id}-${val.categoryData.second.id}-${val.categoryData.third.id}` || val.jump.jumpData;
        }
      })
      let st = false
      let ms = ''
      let {styleAndData, styleAndData: {imageList}} = this.formData
      for (let k = 0; k<imageList.length; k++) {
         if (!imageList[k].jump.jumpData) {
            ms = imageList[k].jump.jumpType && this.msgReg[imageList[k].jump.jumpType] && ('请'+(this.msgReg[imageList[k].jump.jumpType] || '选择跳转类型')) || '请选择跳转类型'
            st = true
            break;
         }
         let type = imageList[k].jump.jumpType
         if (type !=28 && type != 24 && type != 8) {
          if (isNaN(imageList[k].jump.jumpData)) {
            st = true
            ms = this.msgReg[imageList[k].jump.jumpType] && ('请'+this.msgReg[imageList[k].jump.jumpType]) || '请选择跳转类型'
            break
          }
        }
      }
      if (st) {
        this.$message.error( ms)
        return
      }
      let list = imageList.filter(r => {
        return r.jump.jumpType == 24
      }) || []
      if (list.length) {
        let status = false
        let urlReg = /(https):\/\/([\w.]+\/?)\S*/
         for (let k=0; k < list.length; k++) {
           if (!urlReg.test(list[k].jump.jumpData)) {
              status = true
              break
           }
         }
         if (status) {
           this.$message.error('请输入正确的链接地址（如https://www.xxx.com)')
            return
          }
      }
      this.$emit('listenSet',this.formData);
    }
  },
  created() {
    this.api.index.jumpType({layoutType:this.formData.layoutType}).then(result=>{
      this.jumpList = result.data;
    });
    this.api.index.getModuleStyle({layoutType:this.formData.layoutType}).then(result => {
      if(!result.data){
        return;
      }
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
      this.formData.styleAndData.displayStyleType = this.styleSelect.displayStyle[0].id;
    });
  },
  watch: {
    // 'categoryData.first.id': function(val){
    //   if(!val){
    //       return
    //   }
    //   this.api.category.categoryNode({parentId:val}).then((res) => {
    //     this.categoryData.second.data = res.data;
    //   });
    // },
    // 'categoryData.second.id': function(val){
    //   if(!val){
    //       return
    //   }
    //   this.api.category.categoryNode({parentId:val}).then((res) => {
    //     this.categoryData.third.data = res.data;
    //   });
    // },
    'formData.styleAndData.imageList':function(val){
      if(!val){
          return
      }

    }
  }
}
</script>
<style lang='scss'>
.module-set{
  .scrollPage-set{
    background: #fff;
    border-radius: 4px;
    .blue-wrap{
      .img-wrap{
        margin-right: 10px;
      }
      .form-group .flex-wrap .form-control{
        max-width: 92px;
      }
    }
  }
}
.tips{
  color: #999999;
  font-size: 14px;
  margin-left: 10px;
  margin-bottom: 10px;
}

</style>
