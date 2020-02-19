<template>
  <div class="nav-set">
    <div class="title">
      <div class="name">底栏设置</div>
      <div class="link underline pull-right">恢复默认</div>
      <div class="link underline pull-right" @click="save()">确定</div>
    </div>
    <div class="module-content">
      <div class="inline-block">底栏图片</div>
      <div class="inline-block">
        <div class="nav-item" v-for="(item , index) in formData.navList" :key="item.key">
          <div class="icon-content" :class="{long:index>1}">
            <upload v-on:uploadSuccess="beforeUploadSuccess" :index='index' class="icon-img">
              <img :src="item.beforeClickingImg" placeholder="点击前名称" v-show="item.beforeClickingImg">
              <span v-show="!item.beforeClickingImg">点击上传</span>
            </upload>
            <input type="text" class="form-control" v-model="item.beforeClickingName">
            <upload v-on:uploadSuccess="afterUploadSuccess" :index="index" class="icon-img">
              <span v-show="!item.afterClickingImg">点击上传</span>
              <img :src="item.afterClickingImg" v-show="item.afterClickingImg">
            </upload>
            <input type="text" class="form-control" placeholder="点击后名称" v-model="item.afterClickingName">
            <div class="red-link underline" @click="addLink(item)">添加跳转</div>
            <div class="el-icon-close" v-if="index>1" @click="deleteItem(index)"></div>
          </div>
          <div class="link-content" v-show="!!item.link">
            <div class="form-wrap">
              <div class="form-name">跳转方式</div>
              <select class="form-control" v-model="item.jump.jumpType">
                <option value=0>请选择</option>
                <option v-for="jump in jumpList" :key='jump.code' :value="jump.code">{{jump.remark}}</option>
              </select>
              <input type="text" class="form-control" v-model="item.jump.jumpData" placeholder="链接/活动ID">
            </div>
          </div>
        </div>
        <div style="display: flex;align-items: center">
          <div class="add-nav" @click="addNav()">
            新增底栏入口
          </div>
          <div>
            (尺寸建议为64x64px，大小不超过1M)
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
      jumpList:[],
      formData:{
        type:'',
        name:'底栏配置',
        module:'nav',
        navList:[]
      }
    }
  },
  methods:{
    beforeUploadSuccess(data){
      this.formData.navList[data.index].beforeClickingImg = data.data;
    },
    afterUploadSuccess(data){
      this.formData.navList[data.index].afterClickingImg = data.data;
    },
    deleteItem(index) {
      this.formData.navList.splice(index,1);
      this.formData.navList.forEach((nav,index)=>{
        nav.key = 'No' + index;
      });
    },
    addNav(){
      this.formData.navList.push({
        key:'No' + this.formData.navList.length,
        beforeClickingImg:'',
        beforeClickingName:'',
        afterClickingImg:'',
        afterClickingName:'',
        jump:{
          jumpType:0,
          jumpData:''
        }
      });
    },
    addLink(item){
      this.$set(item,'link',{});
    },
    save(){
      this.$emit('listenSet',this.formData);
    },
    getData(result) {
      if(result.data){
        result.data.data.forEach((item,index)=>{
          item.key = 'No' + index;
        });
        this.formData.navList = result.data.data;
      }else{
        for(let i = 0;i<4;i++){
          this.formData.navList.push({
            key:'No' + i,
            beforeClickingImg:'',
            beforeClickingName:'',
            afterClickingImg:'',
            afterClickingName:'',
            jump:{
              jumpType:'',
              jumpData:''
            }
          });
        }
      }
    }
  },
  created() {
    this.api.common.jumpType().then(result=>{
      this.jumpList = result.data;
    });
  }
}
</script>
<style lang='scss'>
  .nav-set{
    background: #fff;
    border-radius: 4px;
    .inline-block{
      display: inline-block;
      vertical-align: top;
    }
    .nav-item{
      margin-left: 10px;
      margin-bottom: 10px;
      .icon-content{
        display: flex;
        margin-bottom: 10px;
        width: 340px;
        align-items: center;
        justify-content: space-between;
        &.long{
          width: 367px;
        }
        .icon-img{
          overflow: hidden;
          border-radius: 4px;
          background: #BDDEFF;
          span{
            display: block;
            padding-top: 5px;
            line-height: 1.2;
            color:#666;
          }
        }
        .icon-img,img{
          width: 40px;
          height: 40px;
        }
        .form-control{
          width: 80px;
        }
      }
      .link-content{
        select.form-control{
          min-width: 110px;
          margin-right: 10px;
        }
      }
    }
    .add-nav{
      margin-left: 10px;
      width: 100px;
      text-align: center;
      border:1px dotted $org-color;
      padding: 10px 0;
      color:$org-color;
      cursor: pointer;
    }
  }
</style>
