<template>
  <div class="center-brand">
      <div class="search-bar">
        <div class="search-bar-title">品牌筛选：</div>
        <div class="letter-wrap">
          <div v-for="item in letter" :key='item' class="letter-item"
            :class="{active:item==currentLetter}" @click="selectLetter(item)">
            {{item}}
          </div>
        </div>
        <div class="form-wrap brandSelect">
          <div class="form-name">品牌名称：</div>
          <input type="text" class="form-control" placeholder="请输入品牌名称" v-model="listParams.name" @keyup.enter="getList(1)">
        </div>
        <div class="btn-fill-main" @click="getList(1)">搜索</div>
        <div class="btn-main pull-right" @click="editDetail()">新增品牌</div>
      </div>
      <table class="table table-text">
         <thead>
           <tr>
             <th class="table-w90">品牌ID</th>
             <th class="table-w90">品牌LOGO</th>
             <th class="table-w120">品牌中文名</th>
             <th class="table-w120">品牌英文名</th>
             <th class="table-w90">上架商品</th>
             <th class="table-w90">下架商品</th>
             <th class="table-w170">品牌描述</th>
             <th class="table-w80">状态</th>
             <th>操作</th>
           </tr>
         </thead>
         <tbody>
          <tr v-for="(item, index) in listData" :key="index+'list'">
            <td>{{item.id}}</td>
            <td>
              <el-popover
                      placement="top"
                      width="200"
                      trigger="hover" class="imgBoxCont">
                <img :src="item.logoUrl" style="width:100%;height:100%">
                <img slot="reference" :src="item.logoUrl" class="imgBox" style="width:52px;height:52px;object-fit:contain;"/>
              </el-popover>
            </td>
            <td>
              <el-popover
                placement="top"
                width="260"
                trigger="hover">
                <span>{{item.name}}</span>
                <span slot="reference" class="ellipsis">{{item.name}}</span>
              </el-popover>
            </td>
            <td>
              <el-popover
                placement="top"
                width="260"
                trigger="hover">
                <span>{{item.originalName}}</span>
                <span class="ellipsis" slot="reference">{{item.originalName}}</span>
              </el-popover>
            </td>
            <td>{{item.onShelfNum}}</td>
            <td>{{item.offShelfNum}}</td>
            <td>
              <el-popover
                placement="top"
                width="260"
                trigger="hover">
                <span>{{item.desc}}</span>
                <span class="ellipsis" slot="reference">{{item.desc}}</span>
              </el-popover>
            </td>
            <td>
              <span class="active-status" v-show="item.status">启用</span>
              <span class="disable-status" v-show="!item.status">禁用</span>
            </td>
            <td>
              <a class="link" @click="editDetail(item)">编辑</a>
              <a class="link" @click="removeItem(item)">删除</a>
              <a class="link" @click="setStatus(item, 1)" v-show="!item.status">启用</a>
              <a class="link" @click="setStatus(item, 0)" v-show="item.status">禁用</a>
<!--              <a class="link" @click="showBrandModal = true;targetId = item.id">品牌迁移</a>-->
              <a class="link" @click="showBrandModalBtn(item)">品牌迁移</a>
              <a class="link" v-show="item.propStatus==1" @click="updateBrandStatus(item,2)">取消推荐</a>
              <a class="link" v-show="item.propStatus==2" @click="updateBrandStatus(item,1)">推荐</a>
            </td>
          </tr>
          </tbody>
      </table>
      <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
      <noData v-if="total===0"></noData>
      <el-dialog title="新增品牌" :visible.sync="addShow" width="468px">
        <div class="dialog-form">
          <div class="form-wrap">
            <div class="form-name">中文名称</div>
            <input class="form-control"/>
          </div>
          <div class="form-wrap">
            <div class="form-name">英文名称</div>
            <input class="form-control"/>
          </div>
          <div class="form-wrap">
            <div class="form-name">品牌国家</div>
            <input class="form-control"/>
          </div>
          <div class="form-wrap">
            <div class="form-name">品牌状态</div>
            <el-radio v-model="radio" label="1">显示</el-radio>
            <el-radio v-model="radio" label="2">不显示</el-radio>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <button class="btn-org-main" @click="addType()">保存</button>
          <button class="btn-fill-grey-main" @click="addShow = false">取消</button>
        </span>
      </el-dialog>
      <SelectBrand v-if="showBrandModal" ref="showBrandModal" v-on:dialogBrand="dialogBrand" v-on:dialogBrandCancel="dialogBrandCancel" isNext="true" :multiply="false">

      </SelectBrand>
      <el-dialog title="确认品牌迁移" :visible.sync="showBrand" width="440px">
        <div>
          <div class="brandBox">
            <div class="brandItem">
<!--              <div>-->
<!--                <img class="brandImg" src="https://timg.apiunion.com/i/4cf1dfc9cd00ef4242e8070aa01004d2_190X190_5W.jpeg?imageView2/2/w/260/h/260" alt="">-->
<!--              </div>-->
              <div class="brandText">
                {{oldBrand.name}}/{{oldBrand.originalName}}
              </div>
            </div>
            <div>
                <img class="arrow" src="../../../../assets/arrow.png" alt="">
            </div>
            <div class="brandItem">
<!--              <div>-->
<!--                <img class="brandImg" src="https://timg.apiunion.com/i/4cf1dfc9cd00ef4242e8070aa01004d2_190X190_5W.jpeg?imageView2/2/w/260/h/260" alt="">-->
<!--              </div>-->
              <div class="brandText">
                {{newBrand.name}}/{{newBrand.originalName}}
              </div>
            </div>
          </div>

          <div class="remarkBox">
            <div class="remarkItem">
              <div>
                <el-checkbox v-model="checked">同时删除原品牌</el-checkbox>
              </div>
              <div class="remark">
                (备注：迁移后，当前品牌下的商品将归属目标品牌)
              </div>
            </div>
          </div>

        </div>
        <span slot="footer" class="dialog-footer">
          <button class="btn-main closeBtn" style="margin-top: 10px"  @click="confrimBtn">确定</button>
          <button class="btn-fill-grey-main closeBtn" style="margin-top: 10px"  @click="showBrand=false">关闭</button>
        </span>
    </el-dialog>

  </div>
</template>
<script>
import SelectBrand from '@/components/selectBrand/index.vue';
import Pagination from '@/components/pagination';
import noData from '@/components/noData';
export default {
  data() {
    return {
      letter: ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','#'],
      currentLetter:'',
      addShow:false,
      showBrand:false,
      radio:1,
      total: -1,
      checked:true,
      listParams: {
        page:1,
        limit:10,
        name:'',
        letter:''
      },
      listData: [],
      showBrandModal: false,
      targetId:'',
      oldBrand:{},
      newBrand:{}
    }
  },
  methods:{
    init() {
      this.getList(1);
    },
      //推荐品牌
      updateBrandStatus(item,propStatus){
        let messsage = propStatus==1?'确认推荐？':'确认取消推荐？'
          this.$msgbox({
              title: '',
              message: messsage,
              showCancelButton: true,
              confirmButtonText: '确定',
              cancelButtonText: '取消'
          }).then(action => {
              if(action === 'confirm') {
                  this.api.brand.updateBrandStatus({
                      propStatus:propStatus,
                      id:item.id
                  }).then(res=>{
                      this.$message.success({message:res.message, showClose:true});
                      this.getList(1);
                  });
              }
          });

      },
    getList(page) {
      this.listParams.page = page;
      // if(reload){
      //   this.listParams = {
      //     page:1,
      //     limit:10,
      //     name:'',
      //     letter:''
      //   }
      // }
      this.api.brand.list(this.listParams).then((res) => {
        this.listData = res.data.dataList;
        this.total = res.data.total;
      });
    },
    selectLetter(item) {
      if(this.currentLetter === item) {
        this.currentLetter = '';
      } else {
        this.currentLetter = item;
      }
      this.listParams.letter = this.copy(this.currentLetter);
      this.getList(1);
    },
    editDetail(item) {
      let href = '';
      if(item && item.id){
        href = this.$router.resolve({ path: 'brand/detail/' + item.id});
      } else {
        href = this.$router.resolve({ name: '品牌详情' });
      }
      window.open(href.href, '_blank');
    },
    removeItem(item) {
      this.$msgbox({
        title: '',
        message: '确认删除？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.brand.del({id: item.id}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
      });
    },
    setStatus(item, status) {
      this.$msgbox({
        title: '',
        message: status === 1 ? '确认启用？' : '确认禁用？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if(action === 'confirm') {
          this.api.brand.setStatus({id: item.id, status: status}).then((res) => {
            this.$message.success({message:res.message, showClose:true});
            this.getList(1);
          });
        }
      });
    },
    //品牌弹框
    showBrandModalBtn(item){
      this.showBrandModal = true
      this.targetId = item.id
      this.oldBrand = item
    },
    //选择的品牌
    dialogBrand(brands) {
      console.log(brands)
      if(brands.brandList.length===0){
        this.$message.error({message:'请选择需要迁移的品牌', showClose:true});
        return;
      };
      this.newBrand = brands.brandList[0];
        this.checked= true
      this.showBrand = true;
    },
    //确定迁移
    confrimBtn(){
      this.api.brand.moveBrand({targetId: this.newBrand.id, originalIds: this.targetId,needDelete:this.checked?1:0}).then((res) => {
        this.$message.success({message:res.message, showClose:true});
        this.getList(1);
      });
      this.showBrandModal = false;
      this.showBrand = false
    },
    //品牌弹窗关闭
    dialogBrandCancel() {
      this.showBrandModal = false;
    },
  },
  created() {
    this.init();
  },
  components: { Pagination, noData,SelectBrand}
}
</script>
<style scoped lang='scss'>
  .center-brand{
    background: $white-color;
    .brandSelect{
      margin-right: 14px;
    }
    .table{
      .link{
        margin-right: 20px;
      }
      .ellipsis{
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        max-width: 100%;
      }
    }
    .search-bar{
      .form-name{
        font-weight: 600;
      }
    }
    .letter-wrap{
      display: inline-block;
      margin-right: 40px;
      user-select: none;
      .letter-item{
        display: inline-block;
        padding: 0 5px;
        cursor: pointer;
        &.active{
          color:$org-color;
        }
      }
    }
    .dialog-form{
      .form-name{
        width: 60px;
      }
      .form-control{
        width: 300px;
      }
      .form-group{
        width: 380px;
      }
    }
  }
  .brandBox{
    display: flex;
    align-items: center;
    box-sizing: border-box;
    padding: 0px 60px 20px 60px;
    justify-content: space-between;
  }
  .brandItem{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;


  }

  .brandImg{
    width: 52px;
    height: 52px;
  }
  .brandText{
    font-size: 14px;
    color: #999999;
    margin-top: 10px;
  white-space:nowrap;/*规定段落中的文本不进行换行*/
  overflow:hidden;/*内容会被修剪，并且其余内容是不可见的。*/
  text-overflow:ellipsis;
      max-width: 110px;
  }
  .remarkBox{
    box-sizing: border-box;
    padding: 0 10px;
    border-top: 1px solid #e5e5e5;
  }
  .remarkItem{
    box-sizing: border-box;
    padding: 20px 30px 0 30px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .remark{
    font-size: 14px;
    color: #999999;
    margin-top: 10px;
  }
    .arrow{
        width: 52px;
        height: 13px;
        margin-top: 10px;
    }
</style>
