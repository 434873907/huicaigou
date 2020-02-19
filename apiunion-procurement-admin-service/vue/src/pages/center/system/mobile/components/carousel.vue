<template>
  <div class="carousel-set">
    <div class="title">
      <div class="name">轮播海报</div>
      <div class="link underline pull-right">恢复默认</div>
      <div class="link underline pull-right" @click="save()">确定</div>
    </div>
    <div class="module-content">
      <div class="form-wrap">
        <div class="form-name form-name-small">图片比例</div>
        <el-radio v-model="formData.styleAndData.proportion" :label="1.5">3:2</el-radio>
        <el-radio v-model="formData.styleAndData.proportion" :label='2'>2:1</el-radio>
        <el-radio v-model="formData.styleAndData.proportion" :label='3'>
          <input type="number" @click="formData.styleAndData.proportion=3" @click.stop class="form-control" placeholder="自定义" v-model="formData.proportion">
        </el-radio>
      </div>

      <div class="form-wrap">
        <div class="form-name form-name-small">填充方式</div>
        <el-radio v-model="formData.styleAndData.scaleType" :label='0'>填充</el-radio>
        <el-radio v-model="formData.styleAndData.scaleType" :label='1'>按比例</el-radio>
      </div>
      <div class="form-wrap" v-if="false">
        <div class="form-name form-name-small">标题</div>
        <input type="text" class="form-control" v-model="formData.title">
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
      <div style="display: flex;align-items: center">
        <div class="btn-fill-org-main base-margin-bottom" @click="addData()">添加一个背景图</div>
        <div class="tips" style="margin-bottom: 20px">(尺寸建议请依据图片比例上传，大小不超过1M)</div>
      </div>
      <div class="blue-wrap" v-for="(item,index) in formData.styleAndData.imageList" :key="index + 'No'">
        <div class="el-icon-close pull-right" @click="deleteJump(index)"></div>
        <div class="img-wrap inline-block">
          <div class="img-alt">
            <img :src="item.url" v-if="item.url">
          </div>
          <upload uploadType='2' :index="index" v-on:uploadSuccess="uploadSuccess">
            <div class="link underline">更换图片</div>
          </upload>
        </div>
        <div class="form-content inline-block">
          <div class="form-wrap">
            <div class="form-name">跳转</div>
            <select class="form-control" v-model="item.jump.jumpType" @change="changeSelect($event, item, index)">
              <option value="">请选择</option>
              <option v-for="jump in jumpList" :key='jump.code' :value="jump.code">{{jump.remark}}</option>
            </select>
          </div>
          <div class="form-wrap" v-show="item.jump.jumpType != 28">
            <div class="form-name" >{{msgTitleReg[item.jump.jumpType] || '活动ID'}}</div>
            <input type="number" v-if="item.jump.jumpType != 8 && item.jump.jumpType != 24" class="form-control" v-model="item.jump.jumpData" :disabled="!item.jump.jumpType" @blur="pureNumber(item.jump.jumpType,index)" :class="{'bg-gray':!item.jump.jumpType}" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))">
            <input type="text" v-else class="form-control" v-model="item.jump.jumpData" :disabled="!item.jump.jumpType" @blur="pureNumber(item.jump.jumpType,index)" :class="{'bg-gray':!item.jump.jumpType}">
          </div>
          <div class="form-wrap" v-if="item.jump.jumpType == 28">
          <div class="form-name">分类选择</div>
            <div class="form-group">
              <div class="flex-wrap">
                <select class="form-control" style="max-width: 90px" v-model="item.jump.jumpDateClass['first']" @change="changeFirst($event, item, index)">
                  <option value="">请选择</option>
                  <option v-for="category in getFirstList" :key='category.id' :value="category.id">{{category.name}}</option>
                </select>
                <select class="form-control" style="max-width: 90px" v-model="item.jump.jumpDateClass.second" @change="changeSecond($event, item, index)">
                  <option value="">请选择</option>
                  <option v-for="category in getSecondList[item.jump.jumpDateClass['first']]" :key='category.id' :value="category.id">{{category.name}}</option>
                </select>
                <select class="form-control" style="max-width: 90px" v-model="item.jump.jumpDateClass.third">
                  <option value="">请选择</option>
                  <option v-for="category in getThirdList[item.jump.jumpDateClass.second]" :key='category.id' :value="category.id">{{category.name}}</option>
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
// let {log}=console;
export default {
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
      allList: [],
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
      formData:{
        layoutType:1,
        name:'轮播海报',
        module:'carousel',
        title:'',
        proportion:'',
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
          proportion:1.5,
          scaleType:0,
          duration:'',
          indicatorStyle:'',
          imageList:[
            {
              url:'',
              jump:{
                jumpType:'',
                jumpData:'',
                jumpDateClass: {first: '', second: '', third: ''}
              }
            }
          ]
        }
      }
    }
  },
  methods:{
    pureNumber(type,i){
      let val=this.formData.styleAndData.imageList[i].jump.jumpData;
      if(type==7||type==26){
        this.formData.styleAndData.imageList[i].jump.jumpData=val.match(/\d/ig) ? val.match(/\d/ig).join('') : '';
      }
    },
    setUpload(data){
      this.formData.styleAndData.backgroundStyle.imageUrl = data.data;
    },
    getEditData(data, allList){
      this.getThree()
      this.allList = allList
      this.formData = this.extend(this.formData,data);
      let {styleAndData, styleAndData: {imageList}} = this.formData
      styleAndData.imageList = imageList.map(r => {
         if (r.jump && r.jump.jumpType == 28) {
           let arr = r.jump.jumpData.split('-') || ['', '', '']
           r.jump.jumpDateClass = {first: arr[0], second: arr[1], third: arr[2]}
         }
         return r
      }) || []
      if(this.formData.styleAndData.proportion != 1.5 && this.formData.styleAndData.proportion !=2){
        this.formData.proportion = this.formData.styleAndData.proportion;
        this.formData.styleAndData.proportion = 3;
      }
      this.$set(this.formData, 'styleAndData', styleAndData)
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
            jumpData:'',
            jumpDateClass: {first: '', second: '', third: ''}
          }
        });
    },
    uploadSuccess(result){
      this.formData.styleAndData.imageList[result.index].url = result.data;
    },
    save(){
      this.formData.styleAndData.padding = this.formData.styleAndData.paddingArr.toString();
      if(this.formData.styleAndData.proportion == 3){
        this.formData.styleAndData.proportion = this.formData.proportion;
      }
      let {styleAndData, styleAndData: {imageList}} = this.formData
      styleAndData.imageList = imageList.map(r => {
          if (r.jump && r.jump.jumpType == 28) {
            r.jump.jumpData = `${r.jump.jumpDateClass.first || ''}-${r.jump.jumpDateClass.second || ''}-${r.jump.jumpDateClass.third || ''}`
          }
          return r
      })
      let st = false
      let ms = ''
      for (let k = 0; k<imageList.length; k++) {
         if (!imageList[k].jump.jumpData) {
            ms = this.msgReg[imageList[k].jump.jumpType] && ('请'+this.msgReg[imageList[k].jump.jumpType]) || '请选择跳转类型'
            st = true
            break;
         }
         let type = imageList[k].jump.jumpType
         if (type !=28 && type != 24 && type != 8) {
          if (isNaN(imageList[k].jump.jumpData)) {
            st = true
            ms = '请'+this.msgReg[imageList[k].jump.jumpType]
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
    },
    changeSelect (e, item, index) {
       let {jump: {jumpType}} = item
       let {styleAndData, styleAndData: {imageList}} = this.formData
       if (jumpType == 28) {
          imageList[index].jump.jumpDateClass = {first: '', second: '', third: ''}
          styleAndData.imageList = imageList
          this.$set(this.formData, 'styleAndData', styleAndData)
       }
    },
    changeFirst (e, item, index) {
       let {styleAndData, styleAndData: {imageList}} = this.formData
       imageList[index].jump.jumpDateClass.second = ''
       imageList[index].jump.jumpDateClass.third = ''
       styleAndData.imageList = imageList
      this.$set(this.formData, 'styleAndData', styleAndData)
      let data = JSON.parse(JSON.stringify(this.formData))
      this.formData = ''
      this.formData = data
    },
    changeSecond (e, item, index) {
       let {styleAndData, styleAndData: {imageList}} = this.formData
       imageList[index].jump.jumpDateClass.third = ''
       styleAndData.imageList = imageList
       this.$set(this.formData, 'styleAndData', styleAndData)
    },
    getThree () {

    }
  },
  computed: {
      getFirstList () {
         let {allList} = this
         let list = allList.map(r => {
            let item  = {
              id: r.id,
              name: r.name,
              parentId: r.parentId
            }
            return item
         }) || []
         return list
      },
      getSecondList () {
         let {allList} = this
         let arr = {}
         allList.forEach((r) => {
             let arrs = []
             if (r.children && r.children.length) {
               r.children.forEach((rr) => {
                  arrs.push({
                    id: rr.id,
                    name: rr.name
                  })
               })
             }
             arr[r.id] = arrs
         })
         return arr
      },
      getThirdList () {
        let {allList} = this
        let arr = {}
        allList.forEach((r) => {
             if (r.children && r.children.length) {
               r.children.forEach((rr) => {
                   let ar = []
                   if (rr.children && rr.children.length) {
                     rr.children.forEach((rrr) => {
                          ar.push({
                            id: rrr.id,
                            parentId: rr.id,
                            name: rrr.name
                          })
                     })
                   }
                   arr[rr.id] = ar
               })
             }
         })
         return arr
      }
  },
  created() {
    this.api.index.jumpType({layoutType:this.formData.layoutType}).then(result=>{
      this.jumpList = result.data;
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
  .carousel-set{
    background: #fff;
    border-radius: 4px;
  }
  .bg-gray{
    background: #f1f1f1;
  }
  .tips{
    color: #999999;
    font-size: 14px;
    margin-left: 10px;
  }
  /*.form-control{*/
  /*  max-width: 90px;*/
  /*}*/
</style>
