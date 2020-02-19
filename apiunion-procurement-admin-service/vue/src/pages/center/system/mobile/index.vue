<template>
  <div class="center-mobile" v-if="errorInfo">
    <div class="tab-bar">
      <div class="tab-item" :class="{active:editType==1}" @click="changeTab(1)">首页</div>
      <div class="tab-item" :class="{active:editType==2}" @click="changeTab(2)">底栏</div>
    </div>
    <div class="example-wrap" ref="exampleWrap">
      <div class="example-content">
        <div class="status-bar">
          <img src="~assets/system/mobile/statusBar.png" draggable="false">
          <span class="time">{{time}}</span>
        </div>
        <div class="el-icon-plus wrap-add" v-if="!moduleList.length" @click="addModule('up')"></div>
        <draggable v-model="moduleList" @end="dragEnd" :options="dragOption">
            <div v-for="(item,index) in moduleList" :key="index + 'No'"
              class="example-item" :class="{active:item.index == editItem.index}"
              @click="selectEditItem(item)">
              <div class="edit-control" v-if="item.index == editItem.index">
                <div class="add add-up" :class="{active:editItem.addType=='up'}" @click.stop="addModule('up')"><i class="el-icon-plus"></i></div>
                <div class="add add-down" :class="{active:editItem.addType=='down'}"  @click.stop="addModule('down')"><i class="el-icon-plus"></i></div>
                <div class="delete" @click.stop="deleteModule()"><i class="el-icon-close"></i></div>
              </div>
              <div class="module-title" :style="{textAlign:['', 'left','center','right'][item.styleAndData&&item.styleAndData.titleStyle&&item.styleAndData.titleStyle.align&&item.styleAndData.titleStyle.align.horizontalAlign||1]}" v-if="item.title">{{item.title}}</div>
              <div :class="'example-' + item.layoutType" v-if="item.layoutType == 1">
                  <el-carousel trigger='click' height="230px">
                      <el-carousel-item v-for='(img,index) in item.styleAndData.imageList' :key="index + 'img'">
                          <img :src='img.url' draggable="false"/>
                      </el-carousel-item>
                  </el-carousel>
              </div>
              <div :class="'example-' + item.layoutType" v-if="item.layoutType == 2">
              </div>
              <div :class="'example-' + item.layoutType" v-if="item.layoutType == 3">
                <img src="~assets/system/mobile/goodsList.png">
              </div>
              <div :class="'example-' + item.layoutType" v-if="item.layoutType == 4">
                <img src="~assets/system/mobile/flashSale.png">
              </div>
              <div :class="'example-' + item.layoutType" v-if="item.layoutType == 5">
                  <vuescroll :ops="scrollOption" ref="vs">
                    <div class="scrollPageBox"
                    :style="{background:`${item.styleAndData.backgroundStyle.color} url(${item.styleAndData.backgroundStyle.imageUrl}) top center /100% no-repeat`}">
                      <div class="scrollPageItem" ref='scrollPageItem'
                      :style="{
                          paddingRight:`${item.styleAndData.columnSpacing}px`,
                          width:`${(item.styleAndData.imgWidthProportion || 1)  * 422}px`,
                          height:`${(item.styleAndData.imgWidthProportion || 1) * 422 / item.styleAndData.proportion}px`
                        }"
                      v-for='(img,index) in item.styleAndData.imageList' :key='index'>
                        <img :src="img.url" draggable="false"/>
                      </div>
                    </div>
                  </vuescroll>
              </div>
              <div :class="'example-' + item.layoutType" v-if="item.layoutType == 6">
                  <el-carousel trigger='click' height="230px" type="card">
                      <el-carousel-item v-for='img in item.styleAndData.imageList' :key='img.url'>
                          <img :src='img.url' draggable="false"/>
                      </el-carousel-item>
                  </el-carousel>
              </div>
              <div :class="'example-' + item.layoutType" v-if="item.layoutType == 7"
                :style="{background:`${item.styleAndData.backgroundStyle.color} url(${item.styleAndData.backgroundStyle.imageUrl}) top center /100% no-repeat`, height: getCubeRealyHeight(item.styleAndData)}">
                  <div class="select-cube" v-for='cube in item.styleAndData.magicItems' :key="cube.key"
                      :style="{
                        left:cube.startX * 420/item.styleAndData.column + 'px',
                        top:cube.startY * 420/item.styleAndData.row + 'px',
                        width:(cube.endX - cube.startX) * 420/item.styleAndData.column + 'px',
                        height:(cube.endY - cube.startY) * 420/item.styleAndData.row + 'px',
                        padding:`${item.styleAndData.spacing/2}px`
                      }">
                    <img :src="cube.image.url" v-if="cube.image.url">
                    <span class="name" v-if="!cube.image.url">{{(cube.endX - cube.startX)*150}}*{{(cube.endY - cube.startY)*150}}或同比例</span>
                  </div>
              </div>
              <div :class="'example-' + item.layoutType" v-if="item.layoutType == 8">
                <img src="~assets/system/mobile/groupBuy.png">
              </div>
            </div>
        </draggable>
        <div class="bottom-bar" v-show="bottomData.length>0">
          <div class="bottom-item" v-for="(item, index) in bottomData" :key="index+'bot'" @click="changeBottomClick(index)">
            <img :src="item.beforeClickingImg" v-show="!item.clicked" />
            <p v-show="!item.clicked">{{item.beforeClickingName}}</p>
            <img :src="item.afterClickingImg" v-show="item.clicked" />
            <p v-show="item.clicked">{{item.afterClickingName}}</p>
          </div>
        </div>
      </div>
    </div>
    <div class="set-content">
      <div v-if="editType==1 && editItem.index != undefined">
        <div class="new-module" v-if="!!editItem.addType">
          <div class="title">
            <div class="name">新增组件</div>
            <div class="link underline pull-right">恢复默认</div>
          </div>
          <div class="content">
            <div class="item" v-for="item in componentsList" :key="item.name" @click="changeModule(item)">
              {{item.remark}}
            </div>
          </div>
        </div>
        <div class="module-set">
          <carousel ref="module1" v-if="formData.code==1" v-on:listenSet="getSet"></carousel>
          <closet ref="module2" v-if="formData.code==2" v-on:listenSet="getSet"></closet>
          <goodsList ref="module3" v-if="formData.code==3" v-on:listenSet="getSet"></goodsList>
          <flashSale ref="module4" v-if="formData.code==4" v-on:listenSet="getSet"></flashSale>
          <scrollPage ref="module5" v-if="formData.code==5" v-on:listenSet="getSet"></scrollPage>
          <gallery ref="module6" v-if="formData.code==6" v-on:listenSet="getSet"></gallery>
          <cube ref="module7" v-if="formData.code==7" v-on:listenSet="getSet"></cube>
          <groupBuy ref="module8" v-if="formData.code==8" v-on:listenSet="getSet"></groupBuy>
        </div>
      </div>
      <div v-if="editType==2" class="module-set">
        <navSet v-on:listenSet="getNavConfig" ref="navSet"></navSet>
      </div>
    </div>
  </div>
</template>
<script>
import draggable from 'vuedraggable'
import carousel from './components/carousel';
import cube from './components/cube';
import closet from './components/closet';
import flashSale from './components/flashSale';
import scrollPage from './components/scrollPage';
import gallery from './components/gallery';
import goodsList from './components/goodsList';
import groupBuy from './components/groupBuy';
import navSet from './components/nav';
import vuescroll from 'vuescroll';

export default {
  components:{
    draggable,
    carousel,
    cube,
    closet,
    flashSale,
    scrollPage,
    gallery,
    goodsList,
    groupBuy,
    navSet,
    vuescroll
  },
  data(){
    return {
      allList: [],
      errorInfo:true,
      componentsList:[],
      editType:1, // 首页或底栏编辑
      formData : {},
      moduleList:[],
      time:'',
      editItem:{},
      bottomData: [],
      dragOption:{disabled:true,scroll:true, animation: 150,scrollSensitivity:200},
      scrollOption: {
        vuescroll: {
          mode: 'slide',
          scroller: {
            bouncing: false,
            penetrationDeceleration: 0,
            penetrationAcceleration: 0
          },
          snapping: {
            enable: true,
            width: 422
          }
        },
        scrollPanel: {
            scrollingY: false
        },
        rail: {
          size: 0
        }
      }
    }
  },
  methods: {
    // 去除混合排序多余的高度
    getCubeRealyHeight (data) {
      const {magicItems, column, row} = data
      let sigleHeight = 420 / row
      let list = magicItems.map(r => {
        return r.endY
      })
      let max = Math.max(...list) || ''
      return max && (max * sigleHeight + 'px') || '420px'
    },
    changeTab(type) {
      // 切换首页或底栏编辑
      this.editType = type;
      if(this.editType === 2) {
        this.editItem = {};
        this.getBottomBar();
      }
    },
    dragEnd(e){
      this.moduleList.forEach((item,index)=>{
        item.index = index;
      });
      this.editItem.index = e.newIndex;
      let {id, styleAndData} = this.editItem
      this.api.index.getModule({
        id,
      }).then(result=>{
        if (result.success) {
          styleAndData = result.data && result.data.styleAndData
          delete this.editItem.styleAndData
          this.$set(this.editItem, 'styleAndData', styleAndData)
          this.save('updateModule',this.copy(this.editItem))
        }
      })
    },
    getBottomBar() {
      this.api.index.getBottomBar().then(result=>{
        this.$nextTick(() => {
          if(this.editType === 2) {
            this.$refs.navSet.getData(this.copy(result));
          }
        });
        if (!result.data){
          return;
        }
        this.bottomData = result.data.data || [];
        if(this.bottomData.length>0) {
          this.bottomData.forEach(item => {
            this.$set(item, 'clicked', false);
          });
        }
      });
    },
    changeBottomClick(key) {
      this.bottomData.forEach((item, index) => {
        if(key == index) {
          item.clicked = !item.clicked;
        } else {
          item.clicked = false;
        }
      });
    },
    selectEditItem(item){
      this.editType = 1;
      if(this.editItem.index == item.index){
        this.editItem = {};
        this.dragOption.disabled = true;
      }else{
        this.dragOption.disabled = false;
        this.editItem = this.copy(item);
        this.getEdit();
      }
      this.formData = {
        code:this.editItem.layoutType,
        remark:this.editItem.name
      }
    },
    getEdit(){
      this.api.index.getModule({
        id:this.editItem.id
      }).then(result=>{
        this.$nextTick(() => {
          if(result.data.styleAndData && result.data.styleAndData.padding){
            result.data.styleAndData.paddingArr = result.data.styleAndData.padding.split(',');
          }
          if (this.$refs['module'+this.editItem.layoutType]) {
            this.$refs['module'+this.editItem.layoutType].getEditData(result.data, this.allList)
          }
        });
      });
    },
    addModule(type){
      // 模块点击加号
      this.formData = {};
      if(!this.moduleList.length){
        this.editItem = {index:0,addType:'down'};
        return;
      }
      type = this.editItem.addType == type ? '' : type;
      this.$set(this.editItem,'addType',type);
      if(!type){
        this.formData = {
          code:this.editItem.layoutType,
          remark:this.editItem.name
        }
      }
    },
    deleteModule(){
      // 模块点击删除
      this.$msgbox({
        title: '',
        message: '确认删除？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.index.deleteModule({id: this.editItem.id}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.moduleList.splice(this.editItem.index,1);
            this.moduleList.forEach((item,index)=>{
              item.index = index;
            });
            this.editItem = {};
          });
        }
      });
    },
    changeModule(item){
      // 新增模块时切换模块
      this.formData = item;
    },
    getNavConfig(data){
      this.api.index.updateBottomBar({
        data:JSON.stringify(data.navList)
      }).then(()=>{
        this.$message.success({message:'修改成功', showClose:true});
        this.getBottomBar();
      })
    },
    getSet(data){
      // 编辑模块点击确定
      let temp = this.copy(data);
      this.console(temp);
      
      if(!this.editItem.addType){
        temp.index = this.editItem.index;
        this.save('updateModule',this.copy(temp),() => {
          this.moduleList[this.editItem.index] = ''
          this.$set(this.moduleList,this.editItem.index,temp)
        });
        return
      }
      if(this.editItem.addType == 'up'){
        temp.index = this.editItem.index;
        this.editItem.index++;
      }
      if(this.editItem.addType == 'down'){
        temp.index = this.moduleList.length?(this.editItem.index + 1):0;
      }
      if(data.layoutType==3){
        temp.index = this.moduleList.length;
      }
      this.save('addModule',this.copy(temp),()=>{
        this.moduleList.splice(temp.index,0,temp);
        this.moduleList.forEach((item,index)=>{
          item.index = index;
        });
      });
    },
    save(type,data,callBack){
      data.order = data.index;
      data.styleAndData = JSON.stringify(data.styleAndData);
      this.api.index[type](data).then(result=>{
        callBack && callBack();
        if(type == 'addModule'){
          this.moduleList[data.index].id = result.data;
        }
        this.$message.success({message:result.message, showClose:true});
      });
    },
    startTime() {
      var today = new Date();
      var h = today.getHours();
      var m = today.getMinutes();
      var meridiem = 'AM';
      // var s = today.getSeconds()
      // add a zero in front of numbers<10
      m = this.checkTime(m);
      // s = this.checkTime(s)
      if(h > 12){
        h-=12;
        meridiem = ' PM';
      }
      this.time = h + ":" + m + meridiem;
      setTimeout(()=>{
        this.startTime();
      },1000);
    },
    checkTime(i){
      if (i<10){i="0" + i}
      return i;
    },
    getAllList () {
      this.api.category.tree({}).then(res=>{
           this.allList = res.data
      })
    }
  },
  created() {
    this.startTime();
    this.getBottomBar();
    this.api.index.moduleType({}).then(result=>{
      this.componentsList = result.data;
      this.formData = result.data[0];
    });
    this.api.index.moduleList({}).then(result=>{
      if(result.data && result.data[0]){
        result.data[0].moduleList.forEach((item,index)=>{
          item.styleAndData.titleStyle = item.title.titleStyle;
          item.title = item.title.titleText;
          item.index = index;
        });
        this.moduleList = result.data[0].moduleList;
      }
      this.$nextTick(()=>{
        this.dragOption.scroll = this.$refs.exampleWrap;
      });
    }).catch((res)=>{
      if(res.code){
        this.errorInfo = false
      }
    });
    this.getAllList()
  }
}
</script>
<style lang='scss' >
  .center-mobile{
    padding-bottom: 40px;
    .tab-bar{
      user-select: none;
    }
    .example-wrap{
      display: inline-block;
      vertical-align: top;
      min-height: 700px;
      max-height: 1000px;
      overflow-x: hidden;
      overflow-y: auto;
    }
    .example-content{
      width: 450px;
      padding: 27px 10px 60px;
      min-height: 700px;
      background: $white-color;
      border:1px solid $link-color;
      position: relative;
      border-radius: 2px;
      .status-bar{
        position: absolute;
        z-index: 10;
        top:0;
        left: 0;
        height: 20px;
        width: 100%;
        text-align: center;
        color:$white-color;
        line-height: 20px;
        background: #333;
        user-select: none;
        img{
          width: 436px;
        }
        .time{
          position: absolute;
          width: 100px;
          left: 50%;
          margin-left: -50px;
          z-index: 10;
        }
      }
      .wrap-add{
        display: block;
        font-size: 50px;
        margin: 20px auto;
        text-align: center;
        color:$main-color;
        cursor: pointer;
      }
      .example-item{
        margin-bottom: 20px;
        cursor: pointer;
        position: relative;
        width: 422px;
        box-sizing: content-box;
        &.active{
          border-radius: 4px;
          border:3px dotted $main-color;
        }
        .edit-control{
          position: absolute;
          left: 0;
          top: 0;
          bottom: 0;
          width: 100%;
          .add,.delete{
            width: 20px;
            height: 20px;
            border-radius: 10px;
            text-align: center;
            position: absolute;
            z-index: 20;
          }
          i{
            font-size: 15px;
            line-height: 21px;
            font-weight: 900;
            color:$white-color;
          }
          .add{
            background: $main-color;
            left: 50%;
            margin-left: -10px;
            &.active{
               animation: namemf 1.5s linear infinite;
            }
            @keyframes namemf{
                from{
                    transform: scale(0.8) rotate(0deg) ;
                }
                50%{
                    transform: scale(1.5) rotate(180deg) ;
                }
                to{
                    transform:scale(0.8) rotate(360deg);
                }
            }
          }
          .add-up{
            top: -10px;
          }
          .add-down{
            bottom: -10px;
          }
          .delete{
            right: -10px;
            top: -10px;
            background: #999;
          }
        }
        .module-title{
          padding: 10px;
        }
        .example-6 .el-carousel--card{
          overflow: hidden;
        }
      }
      .bottom-bar{
        background: #fff;
        display: flex;
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 60px;
        border: 1px dashed #E1376C;
        z-index: 10;
        .bottom-item{
          flex: 1;
          height: 60px;
          text-align: center;
          font-size: 0;
          img{
            display: inline-block;
            width: 24px;
            height: 24px;
            margin: 10px 0 7px;
          }
          p{
            font-size: 12px;
            color: #333;
          }
        }
      }
    }
    .set-content{
      display: inline-block;
      margin-left: 30px;
      vertical-align: top;
      .new-module{
        width: 510px;
        background: $white-color;
        border-radius: 4px;
        box-shadow: 0 1px 1px 0 $border-color;
        .title{
          padding: 10px 20px;
          border-bottom: 1px solid #ddd;
          .name{
            display: inline-block;
            font-weight: 600;
          }
        }
        .content{
          padding: 15px 20px 5px;
          display: flex;
          flex-wrap: wrap;
          .item{
            width: 86px;
            margin: 0 10px 10px 0;
            text-align: center;
            color:$main-color;
            border:1px solid $main-color;
            background: #FFDFE9;
            border-radius: 4px;
            line-height: 30px;
            cursor: pointer;
            &:nth-child(5n){
              margin-right:0;
            }
          }
        }
      }
    }
  }
  .module-set{
    width: 510px;
    margin-top: 20px;
    .title{
      padding: 10px 20px;
      border-bottom: 1px solid #ddd;
      .name{
        display: inline-block;
        font-weight: 600;
      }
      .pull-right{
        margin-left: 10px;
      }
    }
    .module-content{
      padding: 15px 20px;
    }
    .text-type{
      display: flex;
      justify-content: space-between;
      margin-left: 20px;
      margin-top: 8px;
      width: 140px;
      float: right;
      span{
        line-height: 21px;
      }
      img{
        padding: 1px;
        user-select: none;
        cursor: pointer;
        border:2px solid transparent;
        &.active{
          border:2px solid $org-color;
          border-radius: 2px;
        }
      }
    }
    .blue-wrap{
      padding: 10px;
      margin-bottom: 10px;
      background: #F5F8FC;
      border-radius: 2px;
      .img-wrap{
        margin-right: 14px;
        width: 80px;
        height: 100px;
        vertical-align: top;
        text-align: center;
        .img-alt{
          margin-bottom: 10px;
          width: 80px;
          height: 80px;
          background: #D8D8D8;
          img{
            width: 100%;
            height: 100%;
          }
        }
      }
      .form-control{
        width: 220px;
      }
    }
    .add-bg{
      margin-bottom: 20px;
      .img-alt{
        height: 150px;
        background: #F5F8FC;
        text-align: center;
        .up-tip{
            padding-top: 40px;
        }
        .icon{
          color:#BDDEFF;
          font-size: 50px;
          margin-bottom: 10px;
        }
        img{
          width: 100%;
          height: 150px;
        }
      }
    }
    .other-set-wrap{
      padding-top: 15px;
      border-top: 1px solid $border-color;
    }
    .el-radio__label,.el-checkbox__label{
      font-weight: 600;
    }
    .form-wrap{
      margin-bottom: 15px;
    }
    .inlin-block{
      display: inline-block;
    }
    .form-name-small{
      width: 60px;
      text-align: right;
    }
    .form-name-middle{
      width: 86px;
      text-align: right;
    }
    .form-name-big{
      width: 100px;
      text-align: right;
    }
    .other-set{
      padding: 10px 15px;
      border-radius: 2px;
      border: 1px dotted $org-color;
      .form-control{
        width: 120px;
      }
      .form-flex-wrap{
        display: flex;
        align-items: center;
      }
      .form-flex{
        display: flex;
        width: 320px;
        justify-content: space-between;
        align-items: center;
        span{
          font-weight: 600;
        }
        input{
          width: 54px;
          text-align: center;
        }
      }
      .add-img{
        margin-left: 10px;
        padding: 8px 20px 5px;
        color:$org-color;
        border: 1px dotted $org-color;
        cursor: pointer;
      }
    }
  }
  .example-1{
    width: 100%;
    height: 230px;
    background: #BDDEFF;
    display: flex;
    align-items: center;
    justify-content:center;
    .icon{
      font-size: 48px;
      color:#fff;
    }
    .el-carousel{
      width: 100%;
      overflow: hidden;
    }
    img{
      width: 422px;
      height: 230px;
    }
  }
  .example-2{
    width: 100%;
    height: 295px;
    background: url(~assets/system/mobile/closet/closet.png) no-repeat;
    background-size:100%;
  }
  .example-3{
    img{
      width: 100%;
    }
  }
  .example-4{
    img{
      width: 100%;
    }
  }
  .example-5{
    overflow: hidden;
    .scrollPageBox {
      white-space: nowrap;
      padding: 10px 0 10px 10px;
      .scrollPageItem {
        display: inline-block;
        height: 100%;
        img {
          width: 100%;
          height: 100%;
          display: block;
        }
      }
    }
  }
  .example-6{
    width: 100%;
    height: 260px;
    .el-carousel{
      width: 100%;
    }
    img{
      width: 100%;
      height: 100%;
    }
  }
  .example-7{
    margin: 0 auto;
    width: 420px;
    height: 420px;
    position:relative;
    background: #BDDEFF;
    .select-cube{
      position: absolute;
      z-index: 5;
      display: flex;
      align-items: center;
      cursor: pointer;
      user-select: none;
      .name{
        text-align: center;
        display: block;
        width: 100%;
        color:$main-color;
      }
      img{
        display: block;
        width: 100%;
        height: 100%;
      }
    }
  }
  .example-8{
    img{
      width: 100%;
    }
  }
  
</style>
