<template>
  <div class="tab-detail">
    <h2 class="title">{{id ? '编辑' : '新增'}}tab</h2>
    <div class="tab-wrap">
      <div class="tab-item">
        <span class="tab-title">标题</span>
        <input type="text" class="form-control" v-model="tabParams.title" />
      </div>
      <div class="tab-item" v-for="(item, index) in tabParams.conditionList" :key="index+'tab'">
        <span class="tab-title">{{index===0 ? '组合条件' : ''}}</span>
        <div class="item-condition">
          <div class="condition-row">
            <span class="condition-title">类目</span>
            <a class="btn-fill-main" @click="selectItemCate(item, index)">选择类目</a>
            <span class="btn-small" v-for="(cate, cate_index) in item.cateList" :key="cate_index+'cate'+index">
              {{cate.name}}<a class="delete-icon" @click="removeItemCate(item, cate_index)">X</a>
            </span>
          </div>
          <div class="condition-row">
            <span class="condition-title">品牌</span>
            <a class="btn-fill-main" @click="selectItemBrand(item, index)">选择品牌</a>
            <span class="btn-small" v-for="(brand, brand_index) in item.brandList" :key="brand_index+'brand'+index">
              {{brand.name}}<a class="delete-icon" @click="removeItemBrand(item, brand_index)">X</a>
            </span>
          </div>
          <div class="condition-row">
            <span class="condition-title">店铺</span>
            <el-select v-model="item.shopIdList" multiple size="small" placeholder="请选择" class="multipleSelect">
              <el-option
                v-for="shop in shopList"
                :key="shop.id"
                :label="shop.name"
                :value="shop.id">
              </el-option>
            </el-select>
          </div>
          <div class="condition-row" v-show="!userConfig.singleChannel">
            <span class="condition-title">渠道</span>
            <el-select v-model="item.channelTypeList" multiple size="small" placeholder="请选择" class="multipleSelect">
              <el-option
                v-for="channel in channelList"
                :key="channel.channelType"
                :label="channel.channelTypeName"
                :value="channel.channelType">
              </el-option>
            </el-select>
          </div>
          <div class="condition-btn">
            <a @click="addConditionItem()" v-show="index===0">添加</a>
            <a class="delete-condition" @click="removeConditionItem(index)" v-show="index>0">删除</a>
          </div>
        </div>
      </div>
      <div class="tab-item">
        <span class="tab-title">起止时间</span>
        <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  size="small" placeholder="请选择"
          v-model="tabParams.startTime" :picker-options="pickerOption"
          ></el-date-picker>
        <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  size="small" placeholder="请选择"
          v-model="tabParams.endTime" :picker-options="pickerOption"
          ></el-date-picker>
      </div>
      <div class="tab-item">
        <span class="tab-title">是否启用</span>
        <el-radio v-model="tabParams.status" :label="1" class="radio">启用</el-radio>
        <el-radio v-model="tabParams.status" :label="0" class="radio">禁用</el-radio>
      </div>
    </div>
    <div class="bot-btn">
      <a href="javascript:;" class="btn-main" @click="saveTab()">保存</a>
      <router-link class="btn-fill-main" :to="{ name: '电脑端'}">返回</router-link>
    </div>
    <selectCategory v-if="showCategoryModal" v-on:dialogCategory="dialogCategory" v-on:dialogCategoryCancel="dialogCategoryCancel" :selected="selectedCateList"></selectCategory>
    <selectBrand v-if="showBrandModal" v-on:dialogBrand="dialogBrand" v-on:dialogBrandCancel="dialogBrandCancel" :selected="selectedBrandList"></selectBrand>
  </div>
</template>

<script>
import selectCategory from '@/components/selectCategory/index.vue';
import selectBrand from '@/components/selectBrand/index.vue';
export default {
  components: { selectCategory, selectBrand },
  data() {
    return {
      id: '',
      showCategoryModal: false,
      showBrandModal: false,
      cateList: [],
      brandList: [],
      shopList: [],
      channelList: [],
      currentCateItem: -1,
      selectedCateList: [],
      currentBrandItem: -1,
      selectedBrandList: [],
      pickerOption: {
        disabledDate(time) {
          return time.getTime() < Date.now()-8.64e7;
        }
      },
      tabParams: {
        id: '',
        title: '',
        startTime: '',
        endTime: '',
        status: 0,
        conditionList: [
          {
            cateList: [],
            brandList: [],
            shopIdList: [],
            channelTypeList: []
          }
        ]
      }
    }
  },
  created() {
    this.id = this.$route.params.id || '';
    this.api.store.allShop({}).then((res) => {
      this.shopList = res.data;
    });
    this.api.goods.channel({}).then((res) => {
      this.channelList = res.data;
    });
    if(this.id) {
      this.getDetail();
    }
  },
  methods: {
    //获取类目信息
    getDetail() {
      this.api.category.thirdCate({limit: 1000}).then((res) => {
        this.cateList = res.data.dataList;
        this.api.brand.letterList({}).then((resBrand) => {
          this.brandList = resBrand.data;
          this.api.system.tabDetail({id: this.id}).then((res) => {
            this.tabParams.id = res.data.id;
            this.tabParams.title = res.data.title;
            this.tabParams.startTime = res.data.startTime;
            this.tabParams.endTime = res.data.endTime;
            this.tabParams.status = res.data.status;
            this.tabParams.conditionList = res.data.conditionList;
            this.tabParams.conditionList.forEach(item => {
              this.$set(item, 'cateList', []);
              this.$set(item, 'brandList', []);
              item.categoryUnitList.forEach(cate => {
                this.cateList.forEach(c => {
                  if(c.id === cate.cateId3) {
                    item.cateList.push({
                      name: c.name,
                      id: c.id
                    });
                  }
                });
              });
              item.brandIdList.forEach(brand => {
                this.brandList.forEach(xb => {
                  if(xb.id === brand) {
                    item.brandList.push(xb);
                  }
                });
              });
            });
          });
        });
      });
    },
    addConditionItem() {
      this.tabParams.conditionList.push({
        cateList: [],
        brandList: [],
        shopIdList: [],
        channelTypeList: []
      });
    },
    removeConditionItem(index) {
      this.tabParams.conditionList.splice(index, 1);
    },
    selectItemCate(item, index) {
      this.currentCateItem = this.copy(index);
      this.selectedCateList = item.cateList || [];
      this.showCategoryModal = true;
    },
    removeItemCate(item, index) {
      item.cateList.splice(index, 1);
    },
    selectItemBrand(item, index) {
      this.currentBrandItem = this.copy(index);
      this.selectedBrandList = item.brandList || [];
      this.showBrandModal = true;
    },
    removeItemBrand(item, index) {
      item.brandList.splice(index, 1);
    },
    dialogCategory(res) {
      if(this.currentCateItem > -1) {
        this.tabParams.conditionList[this.currentCateItem].cateList = res.categoryList;
        this.showCategoryModal = false;
        this.currentCateItem = -1;
      } else {
        this.$message.info({message: '系统错误', showClose: true});
      }
    },
    dialogCategoryCancel() {
      this.showCategoryModal = false;
    },
    dialogBrand(res) {
      if(this.currentBrandItem > -1) {
        this.tabParams.conditionList[this.currentBrandItem].brandList = res.brandList;
        this.showBrandModal = false;
        this.currentBrandItem = -1;
      } else {
        this.$message.info({message: '系统错误', showClose: true});
      }
    },
    dialogBrandCancel() {
      this.showBrandModal = false;
    },
    saveTab() {
      let params = this.copy(this.tabParams);
      let startTime = new Date(params.startTime);
      let endTime = new Date(params.endTime);

      const {title} = params
      if (title == '' || title == undefined) {
        this.$message.info('请填写标题')
        return
      }
      if (!params.startTime) {
        this.$message.info('请选择起止时间')
        return
      }
      if (!params.endTime) {
        this.$message.info('请选择起止时间')
        return
      }
      if(startTime >= endTime) {
        this.$message.info('结束时间应在开始时间之后');
        return 0;
      }
      params.conditionList.forEach(item => {
        item.categoryUnitList = [];
        item.brandIdList = [];
        if(item.cateList && item.cateList.length>0) {
          item.cateList.forEach(cate => {
            item.categoryUnitList.push({
              cateId1: 0,
              cateId2: 0,
              cateId3: cate.id
            });
          });
        }
        if(item.brandList && item.brandList.length>0) {
          item.brandList.forEach(brand => {
            item.brandIdList.push(brand.id);
          });
        }
        delete item.cateList;
        delete item.brandList;
        delete item.empty;
      });
      params.conditionList = JSON.stringify(params.conditionList);
      if(params.id) {
        this.api.system.editTab(params).then((res) => {
          this.$message.success({message: res.message, showClose: true});
          localStorage.setItem('tabDetail', 1)
          this.$router.push({ name: '电脑端' });
        });
      } else {
        this.api.system.addTab(params).then((res) => {
          this.$message.success({message: res.message, showClose: true});
          localStorage.setItem('tabDetail', 1)
          this.$router.push({ name: '电脑端'});
        });
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  .tab-detail{
    .title{
      font-size: 14px;
      color: #333333;
      font-weight: bold;
      padding-bottom: 10px;
    }
    .tab-wrap{
      padding: 20px 20px 5px;
      background: #fff;
      border-radius: 4px;
      .tab-item{
        padding-bottom: 15px;
        .timepicker-control{
          margin-right: 10px;
        }
        .radio{
          line-height: 30px;
        }
        .tab-title{
          display: inline-block;
          vertical-align: top;
          font-size: 14px;
          color: #333333;
          text-align: left;
          width: 78px;
          line-height: 30px;
        }
        .form-control{
          width: 366px;
        }
        .item-condition{
          display: inline-block;
          vertical-align: top;
          width: 800px;
          .condition-row{
            padding-bottom: 15px;
            .condition-title{
              display: inline-block;
              vertical-align: top;
              font-size: 14px;
              color: #333333;
              font-weight: bold;
              padding-right: 10px;
              line-height: 30px;
            }
            .multipleSelect{
              width: 328px;
            }
            .btn-small{
              margin-left: 10px;
              margin-bottom: 10px;
              .delete-icon{
                cursor: pointer;
                padding: 0 2px 0 10px;
              }
            }
          }
          .condition-btn{
            a{
              display: inline-block;
              vertical-align: top;
              width: 42px;
              height: 30px;
              text-align: center;
              line-height: 30px;
              color: #F06941;
              border: 1px dashed #F06941;
              cursor: pointer;
              border-radius: 15px;
            }
            .delete-condition{
              color: #666666;
              border: 1px dashed #999999;
            }
          }
        }
      }
    }
    .bot-btn{
      padding-top: 20px;
      a{
        margin-right: 20px;
      }
    }
  }
</style>

