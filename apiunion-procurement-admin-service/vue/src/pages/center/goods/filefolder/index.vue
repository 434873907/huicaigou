<template>
  <div class="goods-filefolder">
    <div class="top-wrap" ref="topWrapModel">
      <div class="pull-right btn-wrap">
        <span class="btn btn-main">
          <upload uploadType='2' :fileFolderId="currentFolder.id" v-on:uploadSuccess="uploadSuccess">上传</upload>
        </span>
        <span class="btn btn-fill-main" @click="saveNewFolder('open')">新建文件夹</span>
      </div>
    </div>
    <div class="main-content">
      <div class="tree-content">
        <el-tree
          node-key="id"
          :default-expanded-keys='[1]'
          :props="defaultProps"
          :data="folderList"
          @node-click="treeClick"
        >
        </el-tree>
      </div>
      <div class="file-content" ref="folderControl">
        <div class="folder-name">{{currentFolder.folderName}}</div>
        <div class="folder-control">
            <el-checkbox v-model="selectAll" @change="handleSelectAll">已选中{{selectNum}}个文件</el-checkbox>
            <div class="folder-ctr-btn" v-show="selectNum > 0">
              <span @click="batchRename" :class="{disable:selectNum>1}">重命名</span>
              <span @click="deleteFile" class="border-split">删除</span>
              <span @click="openMoveFolder">移动到</span>
            </div>
            <!-- <div class="pull-right rest-size">
              <div>已用 111M/300M</div>
              <el-progress :show-text="false" color="#E1376C" :stroke-width="8" :percentage="70"></el-progress>
            </div> -->
        </div>
        <div class="folder-list folder-first-list">
          <div class="folder-item" draggable="true" @drop="folderDrop(item)" @dragover='onDragover' @dragstart="folderDragstart(item)"
            v-for="item in currentFolder.childFolders" :key="item.id">
            <div class="folder-sign" @click="selectFolder(item)" @dblclick="openFolder(item)">
              <div class="folder-raido" :class="{active:item.active}"></div>
              <svg class="icon" aria-hidden="true">
                <use xlink:href="#icon-folder"></use>
              </svg>
            </div>
            <div class="folder-item-name">
              <div v-show="!item.rename" @dblclick="rename(item)">{{item.folderName}}</div>
              <input type="text" @keyup.enter="renameConfirm(item)"  @blur="renameConfirm(item)" class="folder-name-input" :ref="'name'+item.id"
                v-show="item.rename" v-model="item.folderName">
            </div>
          </div>
        </div>
        <div class="folder-list">
          <div class="img-item" draggable="true" @drop="folderDrop(item)" @dragover='onDragover' @dragstart="folderDragstart(item)"
          v-for="item in currentFolder.imgList" :key="item.imageUrlMd5">
            <div class="file-img-wrap" :class="{active:item.active}">
                <img :src="item.imageUrl" class="file-img" draggable="false" @click="selectImg(item)">
            </div>
            <div class="folder-item-name">
              <div v-show="!item.rename" @dblclick="rename(item)">{{item.name}}</div>
              <input type="text" @keyup.enter="renameConfirm(item)"  @blur="renameConfirm(item)" class="folder-name-input"
                :ref="'name'+item.imageUrlMd5" v-show="item.rename" v-model="item.name">
            </div>
          </div>
        </div>
      </div>
    </div>
    <el-dialog title="新建文件夹" :visible.sync="addFolder" width="300px">
      <div class="dialog-form">
        <div class="form-wrap">
          <div class="form-name">名称</div>
          <input class="form-control" v-model="newFolderName">
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-org-main" @click="saveNewFolder()">保存</button>
        <button class="btn-fill-grey-main" @click="addFolder = false">取消</button>
      </span>
    </el-dialog>
    <el-dialog title="移动文件" :visible.sync="moveFolder" width="450px">
      <div class="dialog-form">
        <el-tree
          v-if="moveFolder"
          node-key="id"
          :highlight-current="true"
          :default-expanded-keys='[1]'
          :props="defaultProps"
          :data="folderList"
          @node-click="selectTargetFolder"
        >
        </el-tree>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-org-main" @click="saveMoveFolder()">保存</button>
        <button class="btn-fill-grey-main" @click="moveFolder = false">取消</button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
let time = null;
let moveData = {
  fileFolderIds:[],
  imageMd5s:[],
};

export default {
  data() {
    return {
      selectAll:false,
      addFolder:false,
      moveFolder:false,
      newFolderName:'',
      folderList:[],
      currentFolder:{childFolders:[],imgList:[]},
      activeFolder:{},
      defaultProps: {
        children: 'childFolders',
        label: 'folderName'
      },
      isOpen:'',
    }
  },
  created() {
    this.$nextTick(() => {
      this.$refs.folderControl.onscroll = () => {
        if(this.$refs.folderControl.scrollTop>48) {
          if(!this.$refs.folderControl.classList.contains('fixed-folder')){
            this.$refs.folderControl.classList.add('fixed-folder');
          }
        } else {
          this.$refs.folderControl.classList.remove('fixed-folder');
        }
      }
    });
    this.api.folder.query({id:0}).then((res) => {
      this.folderList = [res.data];
      this.currentFolder = this.folderList[0] || res.data;
      this.getImg(this.currentFolder);
    });
    //控制top-wrap宽度
    this.isOpen = this.$parent.isOpen
    this.$parent.vm.$on('changeNavOpen',(val)=>{
      this.isOpen = val
    })
  },
  beforeDestroy() {
    this.$refs.folderControl.onscroll = '';
  },
  computed:{
    selectNum:function(){
      let num = 0;
      this.currentFolder.childFolders.forEach((item)=>{
        item.active && num++;
      });
      this.currentFolder.imgList && this.currentFolder.imgList.forEach((item)=>{
        item.active && num++;
      });
      return num;
    }
  },
  watch:{
    isOpen(val){
      let box = this.$refs.topWrapModel
      let val1 = "calc(100% - 200px)"
      let val2 = "calc(100% - 40px)"
      if(val){
         box.style.width = val1
         box.style.left = 160
      }else{
         box.style.width = val2
      }
    }
  },
  methods: {
    handleSelectAll(e){
      this.currentFolder.childFolders.forEach((item)=>{
        this.$set(item,'active',e);
      });
      this.currentFolder.imgList.forEach((item)=>{
        this.$set(item,'active',e);
      });
    },
    treeClick(item){
      this.openFolder(item);
    },
    selectFolder(item){
      clearTimeout(time);  //首先清除计时器
      time = setTimeout(() => {
        this.$set(item,'active',!item.active);
        this.selectAll = true;
        this.currentFolder.childFolders.forEach((item)=>{
          if(!item.active){
            this.selectAll = false;
          }
        });
      },300);
    },
    selectImg(item){
      this.$set(item,'active',!item.active);
    },
    rename(item){
      this.$set(item,'rename',true);
      this.$nextTick(()=>{
        this.$refs['name'+ (item.imageUrlMd5 ? item.imageUrlMd5 : item.id)][0].select();
      });
    },
    batchRename(){
      if(this.selectNum>1){
        return;
      }
      this.currentFolder.childFolders.forEach((item)=>{
        if(item.active){
          this.rename(item);
        }
      });
      this.currentFolder.imgList.forEach((item)=>{
        if(item.active){
          this.rename(item);
        }
      });
    },
    renameConfirm(item){
      if(!item.folderName && !item.name){
        return;
      }
      this.api.folder.rename({
        fileType:item.imageUrlMd5 ? 1 : 2,
        key:item.imageUrlMd5 ? item.imageUrlMd5 : item.id,
        name:item.folderName || item.name
      }).then(() => {
        this.$message.success({message:'成功', showClose:true});
        item.rename = false;
        if(!item.imageUrlMd5){
          this.api.folder.query({id:0}).then((res) => {
            this.folderList = res.data.childFolders;
          });
        }
      });
    },
    openFolder(item){
      clearTimeout(time);
      this.api.folder.query({id:item.id}).then((res) => {
        this.currentFolder = res.data;
        this.getImg(item);
      });
    },
    getImg(item){
      this.api.folder.getImg({
        id:item.id,
        limit:9999
      }).then((result) => {
        this.$set(this.currentFolder,'imgList',result.data.dataList);
      });
    },
    deleteFile(){
      let deleteFileFolderIds = [];
      let deleteImageMd5s = [];
      this.currentFolder.childFolders.forEach((item)=>{
        if(item.active){
          deleteFileFolderIds.push(item.id);
        }
      });
      this.currentFolder.imgList.forEach((item)=>{
        if(item.active){
          deleteImageMd5s.push(item.imageUrlMd5);
        }
      });
      if(!deleteFileFolderIds.length && !deleteImageMd5s.length){
        this.$message.info({message:'请选择文件', showClose:true});
        return;
      }
      this.$msgbox({
        title: '',
        message: '确认删除？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.folder.deleteFile({
            deleteFileFolderIds:JSON.stringify(deleteFileFolderIds),
            deleteImageMd5s:JSON.stringify(deleteImageMd5s)
          }).then(() => {
            this.$message.success({message:'成功', showClose:true});
            this.openFolder(this.currentFolder);
          });
        }
      });
    },
    saveNewFolder(open){
      if(open){
        this.addFolder = true;
        this.newFolderName = '';
        return;
      }
      if(!this.newFolderName){
        this.$message.info({message:'请输入文件夹名称', showClose:true});
        return;
      }
      this.api.folder.add({
        id:this.currentFolder.id,
        folderName:this.newFolderName
      }).then(() => {
        this.addFolder = false;
        this.api.folder.query({id:0}).then((res) => {
          this.folderList = res.data.childFolders;
        });
        this.openFolder(this.currentFolder);
      });
    },
    onDragover(e) {
      e.preventDefault();
    },
    folderDragstart(item){
      moveData.startId = item.id;
      if(item.imageUrlMd5){
        moveData.imageMd5s.push(item.imageUrlMd5);
      }else{
        moveData.fileFolderIds.push(item.id);
      }
    },
    folderDrop(item){
      if(item.id == moveData.startId || !!item.imageUrlMd5){
        moveData = {
          fileFolderIds:[],
          imageMd5s:[],
        }
        return;
      }
      this.selectTargetFolder(item);
      this.saveMoveFolder();
    },
    openMoveFolder(){
      this.currentFolder.childFolders.forEach((item)=>{
        if(item.active){
          moveData.fileFolderIds.push(item.id);
        }
      });
      this.currentFolder.imgList.forEach((item)=>{
        if(item.active){
          moveData.imageMd5s.push(item.imageUrlMd5);
        }
      });
      if(!moveData.fileFolderIds.length && !moveData.imageMd5s.length){
        this.$message.info({message:'请选择文件', showClose:true});
        return;
      }
      this.moveFolder = true;
    },
    selectTargetFolder(item){
      this.currentFolder.moveId = item.id || '';
    },
    saveMoveFolder(){
      if(!this.currentFolder.moveId){
        this.$message.info({message:'请选择目标文件夹', showClose:true});
        return;
      }
      this.api.folder.move({
        fileFolderIds:JSON.stringify(moveData.fileFolderIds),
        imageMd5s:JSON.stringify(moveData.imageMd5s),
        parentId:this.currentFolder.moveId
      }).then(() => {
        moveData = {
          fileFolderIds:[],
          imageMd5s:[],
        }
        this.moveFolder = false;
        this.currentFolder.moveId = '';
        this.$message.success({message:'成功', showClose:true});
        this.openFolder(this.currentFolder);
      });
    },
    uploadSuccess(){
      this.openFolder(this.currentFolder);
    }
  }
}
</script>

<style lang="scss">
  .goods-filefolder{

    .top-wrap{
      height: 60px;
      background: #fff;
      position: fixed;
      // width: calc(100% - 160px);
      top: 50px;
      // left: 160px;
      z-index: 5;
      min-width: 1200px;
      .btn-wrap{
        margin: 15px 20px 0 0;
      }
      .btn{
        margin-left:20px;
      }
      .el-upload{
        &:focus,&:active{
          color:#fff;
        }
      }
    }
    .main-content{
      margin-top: 80px;
      display: flex;
      height: calc(100vh - 160px);
      overflow-y: auto;
      .tree-content{
        margin-right: 20px;
        padding: 16px;
        width: 210px;
        min-height: 600px;
        background: #fff;
        height: calc(100vh - 160px);
        overflow-y: auto;
      }
      .file-content{
        flex: 1;
        height: calc(100vh - 160px);
        overflow-y: auto;
        .folder-name{
          margin-bottom: 15px;
          font-weight: 900;
        }
        .folder-control{
          min-height: 28px;
          .el-checkbox{
            line-height: 28px;
          }
          .folder-ctr-btn{
            display: inline-block;
            margin-left: 20px;
            padding: 6px;
            border:1px solid #ccc;
            border-radius: 4px;
            background: #fff;
            span{
              display: inline-block;
              width: 66px;
              text-align: center;
              cursor: pointer;
            }
            .disable{
              color: #ddd;
            }
            .border-split{
              border-right: 1px solid #ccc;
              border-left: 1px solid #ccc;
            }
          }
          .el-progress{
            width: 250px;
            border-radius: 0;
            margin-top: 5px;
            .el-progress-bar__outer{
              border-radius: 0;
            }
            .el-progress-bar__inner{
              border-radius: 0;
            }
          }
        }
        &.fixed-folder{
          .folder-control{
            position: fixed;
            z-index: 7;
            top: 66px;
            left: 250px;
          }
          .folder-first-list{
            margin-top: 48px;
          }
        }
        .folder-list{
          margin-top: 20px;
          .folder-item{
            display: inline-block;
            margin-right: 20px;
            margin-bottom: 30px;
            vertical-align: top;
            .folder-sign{
              width: 160px;
              height: 160px;
              border-radius: 4px;
              background: #fff;
              text-align: center;
              position: relative;
              .folder-raido{
                position: absolute;
                width: 16px;
                height: 16px;
                border-radius: 8px;
                border:1px solid #ccc;
                top: 8px;
                left: 8px;
                &.active{
                  background: $main-color;
                  border:0;
                }
              }
              .icon{
                margin-top: 48px;
                font-size: 66px;
                color: $main-color;
              }
            }
          }
          .folder-item-name{
            text-align: center;
            margin-top: 10px;
            .folder-name-input{
              display: block;
              width: 100%;
              height: 14px;
              background: transparent;
              text-align: center;
              border:1px solid #ccc;
            }
            div{
              height: 14px;
              width: 160px;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
            }
          }
          .img-item{
            display: inline-block;
            margin-right: 20px;
            margin-bottom: 20px;
            vertical-align: top;
            .file-img-wrap{
              width: 160px;
              height: 160px;
              display: table-cell;
              vertical-align: middle;
              &.active{
                border:2px solid $main-color;
                border-radius: 4px;
              }
            }
            .file-img{
              display: block;
              margin: 0 auto;
              max-width: 100%;
              max-height: 100%;
            }
          }
        }
      }
    }
  }
</style>
