<template>
  <div class="select-category">
    <el-dialog title="类目选择:" :visible.sync="showCategory" width="815px" :before-close="cancelCategory">
      <div class="selected-cate">
        <span class="title">已选类目</span>
        <span class="item" v-for="(item, index) in selectedCategoryList" :key="item.id">
          {{item.name}}
          <span class="remove-select" @click="removeSelected(index)">X</span>
        </span>
      </div>
      <div class="dialog-form">
        <div class="left-bar">
          <div class="first-item" v-for="item in cateTree" :key="item.id" @click.stop="selectFirst(item)" :class="{'active':currentBar.id===item.id}">
            {{item.name}}
            <!-- <a class="link" v-show="!item.checked" @click.stop="selectAll(1, item, true)">全选</a>
            <a class="link" v-show="item.checked" @click.stop="selectAll(1, item, false)">反选</a> -->
          </div>
          <div class="selectAll" @click.stop="allSelect()">
            <div class="dot" :class="{'active':firstAllSelect}"></div>
            <span>全选</span>
        </div>
        </div>
        <div class="right-content">
          <div class="second-item" v-for="item in currentBar.children" :key="item.id">
            <div class="second-item-title">
              <h2>{{item.name}}</h2>
              <div class="selectAll" v-show="!item.checked" @click.stop="selectAll(2, item, true)">
                <div class="dot" :class="{'active':item.checked}"></div>
                <span>全选</span>
              </div>
              <div class="selectAll" v-show="item.checked" @click.stop="selectAll(2, item, false)">
                <div class="dot" :class="{'active':item.checked}"></div>
                <span>全选</span>
              </div>
            </div>
            <div class="third-content">
              <span class="third-item" v-for="cell in item.children" :key="cell.id" @click="selectThird(cell,item.parentId)">{{cell.name}}</span>
            </div>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="exportCategory()">确定</button>
        <button class="btn-fill-grey-main" @click="cancelCategory()">取消</button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: {
    multiply: {   // 类目多选:true, 类目单选:false, 默认多选
      default: true
    },
    selected: {   // 打开模态框前已选择的类目
      type: Array,
      default () {
        return []
      }
    }
  },
  data() {
    return {
      showCategory: true,
      cateTree: [],
      currentBar: {},
      selectedCategoryList: [],
      firstAllSelect:false
    }
  },
  created() {
    if(this.selected.length>0) {
      this.selectedCategoryList = this.copy(this.selected);
    }
    this.getCateTree();
  },
  methods: {
    getCateTree() {
      this.api.category.tree({}).then((res) => {
        this.cateTree = res.data;
        this.cateTree.forEach(first => {
          this.$set(first, 'checked', false);
          if(first.children && first.children.length>0) {
            first.children.forEach(second => {
              this.$set(second, 'checked', false);
              if(second.children && second.children.length>0) {
                second.children.forEach(third => {
                  this.$set(third, 'checked', false);
                });
              }
            });
          }
        });
        this.currentBar = this.cateTree[0];
        this.console('cate:', this.cateTree);
      });
    },
    selectAll(level, item, checked) {
      if(level === 1) {
        item.checked = checked;
        if(checked === true) {
          if(item.children && item.children.length>0) {
            item.children.forEach(second => {
              second.checked = true;
              if(second.children && second.children.length>0) {
                second.children.forEach(third => {
                  this.selectThird(third,second.parentId);
                });
              }
            });
          }
        } else {
          let select = [];
          if(item.children && item.children.length>0) {
            item.children.forEach(second => {
              second.checked = false;
              if(second.children && second.children.length>0) {
                second.children.forEach(third => {
                  select.push(third.id);
                });
              }
            });
          }
          if(this.selectedCategoryList.length>0 && select.length>0) {
            let target = [];
            this.selectedCategoryList.forEach(item => {
              if(select.indexOf(item.id) === -1) {
                target.push(item);
              }
            });
            this.selectedCategoryList = target;
          }
        }
      } else {
        item.checked = checked;
        if(checked === true) {
          if(item.children && item.children.length>0) {
            item.children.forEach(third => {
              this.selectThird(third,item.parentId);
            });
          }
        } else {
          let select = [];
          if(item.children && item.children.length>0) {
            item.children.forEach(third => {
              select.push(third.id);
            });
          }
          if(this.selectedCategoryList.length>0 && select.length>0) {
            let target = [];
            this.selectedCategoryList.forEach(item => {
              if(select.indexOf(item.id) === -1) {
                target.push(item);
              }
            });
            this.selectedCategoryList = target;
          }
        }
      }
    },
    allSelect(){
      this.firstAllSelect = !this.firstAllSelect
      if(this.firstAllSelect){
        this.cateTree.forEach(first=>{
          this.selectAll(1,first,true)
        })
      }else{
        this.selectedCategoryList = []
      }
      
    },
    selectFirst(item) {
      this.currentBar = item;
    },
    selectThird(item,firstId) {
      item.firstId = firstId;
      if(this.multiply) {
        let exist = false;
        this.selectedCategoryList.forEach(third => {
          if(third.id === item.id) {
            exist = true;
          }
        });
        if(!exist) {
          this.selectedCategoryList.push(item);
        }
      } else {
        if(this.selectedCategoryList.length !== 0) {
          this.selectedCategoryList = [];
        }
        this.selectedCategoryList.push(item);
      }
    },
    removeSelected(index) {
      this.selectedCategoryList.splice(index, 1);
    },
    exportCategory() {
      if(this.selectedCategoryList.length === 0) {
        this.$message.info({message:'请选择至少一个类目', showClose:true});
        return
      }
      this.$emit('dialogCategory', { categoryList: this.selectedCategoryList });
    },
    cancelCategory() {
      this.showCategory = true;
      this.$emit('dialogCategoryCancel');
    }
  }
}
</script>

<style lang="scss" scoped>
  .select-category{
    .selected-cate{
      padding: 10px 20px 0;
      border-bottom: 1px solid $border-color;
      span{
        display: inline-block;
        vertical-align: top;
        font-size: 12px;
      }
      .title{
        font-size: 14px;
        font-weight: bold;
        line-height: 22px;
        margin-bottom: 10px;
      }
      .item{
        height: 22px;
        line-height: 22px;
        padding: 0 8px;
        background: $main-color;
        color: #fff;
        border-radius: 22px;
        margin-left: 10px;
        margin-bottom: 10px;
        .remove-select{
          padding: 0 4px;
          cursor: pointer;
        }
      }
    }
    .dialog-form{
      height: 380px;
      overflow: auto;
      border-bottom: 1px solid $border-color;
      margin-bottom: 10px;
      .selectAll{
          padding-left: 30px;
          height:40px;
          line-height:40px;
          .dot{
            width:12px;
            height:12px;
            display:inline-block;
            border-radius:50%;
            border:1px solid #979797;
            margin-right:4px;
            line-height:40px;
            &.active{
              background:#f00;
            }
          }
          span{
            display:inline-block;
            font-size:14px;
            line-height:40px;
            height:40px;
          }
        }
      .left-bar{
        display: inline-block;
        vertical-align: top;
        height: 100%;
        width: 156px;
        overflow: auto;
        padding: 10px 0;
        border-right: 1px solid $border-color;
        .first-item{
          font-size: 14px;
          color: #666;
          padding-left: 30px;
          line-height: 40px;
          cursor: pointer;
          &:hover{
            color: $main-color;
          }
          .link{
            font-size: 12px;
            padding-left: 5px;
          }
        }
        .active{
          color: $main-color;
        }
      }
      .right-content{
        display: inline-block;
        vertical-align: top;
        height: 100%;
        width: calc(100% - 156px);
        overflow: auto;
        .second-item{
          .second-item-title{
            line-height: 40px;
            padding:0 20px;
            color: #333;
            font-size: 14px;
            font-weight: bold;
            border-bottom: 1px solid $border-color;
            height:40px;
            display:flex;
            justify-content:space-between;
            .link{
              font-size: 12px;
              margin-left: 10px;
            }
          }
          .third-content{
            padding: 8px 10px;
            .third-item{
              display: inline-block;
              vertical-align: top;
              width: 20%;
              line-height: 30px;
              padding: 0 10px;
              cursor: pointer;
              &:hover{
                color: #F06941;
              }
            }
          }
        }
      }
    }
  }
</style>

