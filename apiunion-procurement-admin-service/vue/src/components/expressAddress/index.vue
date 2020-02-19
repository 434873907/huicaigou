<template>
  <div class="express-address">
    <el-dialog title="收货地区:" :visible.sync="showAddress" width="640px" :before-close="cancelAddress">
      <div class="dialog-form selected-inner" v-show="selectedAddress.length>0">
        <span class="title">当前已选:</span>
        <span class="selected-item" v-for="(item, index) in selectedAddress" :key="index+'city'">
          {{item}}
          <span v-show="index !== selectedAddress.length-1">、</span>
        </span>
      </div>
      <div class="dialog-form border-bottom">
        <div class="area-item" v-for="item in area" :key="item.id">
          <div class="area-name">{{item.areaName}}:</div>
          <div class="area-content">
            <div class="content-second">
              <span class="province" 
                    v-for="province in item.children" 
                    :key="province.id" 
                    :class="{'active':item.currentProvince.id===province.id}"
                    @click="selectProvince(item, province)">
                {{province.areaName}}
              </span>
            </div>
            <div class="content-third" v-show="item.currentProvince.children.length>0">
              <p>
                <el-checkbox v-model="item.currentProvince.checkAll" @change="selectAll(item)">全选</el-checkbox>
              </p>
              <el-checkbox v-for="city in item.currentProvince.children" :key="city.id" 
              v-model="city.checked" @change="selectCity(item, city)">
              {{city.areaName}}</el-checkbox>
            </div>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="exportAddress()">保存</button>
        <button class="btn-fill-grey-main" @click="cancelAddress()">取消</button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'expressAddress',
  data() {
    return {
      showAddress: true,
      area: [],
      selectedAddress: []
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      // 获取地区数据
      this.getArea(); 
    },
    getArea() {
      this.api.common.expressAddress({}).then((res) => {
        res.data.forEach(area => {
          area.currentProvince = {
            children: []
          };
          area.children.forEach(province => {
            province.checkAll = false;
            province.children.forEach(city => {
              city.checked = false;
            });
          });
        });
        this.area = res.data;
      });
    },
    selectProvince(item, province) {
      if(item.currentProvince.id === province.id) {
        item.currentProvince = {
          children: []
        };
      } else {
        item.currentProvince = province;
      }
    },
    selectAll(item) {
      item.currentProvince.children.forEach(city => {
        city.checked = item.currentProvince.checkAll;
        let fullName = item.areaName + '|' + item.currentProvince.areaName + '|' + city.areaName;
        if(city.checked) {
          this.selectedAddress.push(fullName);
        } else {
          if(this.selectedAddress.indexOf(fullName) !== -1) {
            this.selectedAddress.splice(this.selectedAddress.indexOf(fullName), 1);
          }
        }
      });
    },
    selectCity(item, city) {
      let fullName = item.areaName + '|' + item.currentProvince.areaName + '|' + city.areaName;
      if(city.checked) {
        this.selectedAddress.push(fullName);
      } else {
        if(this.selectedAddress.indexOf(fullName) !== -1) {
          this.selectedAddress.splice(this.selectedAddress.indexOf(fullName), 1);
        }
      }
      item.currentProvince.checkAll = true;
      item.currentProvince.children.forEach(city => {
        if(!city.checked) {
          item.currentProvince.checkAll = false;
        }
      });
    },
    exportAddress() {
      this.$emit('dialogExpressAddress', { address: this.selectedAddress });
    },
    cancelAddress() {
      this.showAddress = true;
      this.$emit('dialogExpressAddressCancel');
    }
  }
}
</script>

<style lang='scss'>
  .express-address{
    .el-dialog__body{
      padding-top: 10px;
    }
    .selected-inner{
      padding: 0 0 8px;
      margin-bottom: 10px;
      border-bottom: 1px solid $border-color;
      span{
        display: inline-block;
        vertical-align: top;
        line-height: 30px;
      }
      .title{
        font-weight: bold;
        width: 72px;
      }
      .selected-item{
        
      }
    }
    .dialog-form{
      .area-item{
        padding-bottom: 15px;
        .area-name{
          display: inline-block;
          vertical-align: top;
          width: 54px;
          line-height: 30px;
          font-size: 14px;
          font-weight: bold;
        }
        .area-content{
          display: inline-block;
          vertical-align: top;
          width: 500px;
          .el-checkbox{
            line-height: 30px;
            margin: 0 20px 0 0;
          }
          .content-second{
            .province{
              display: inline-block;
              vertical-align: top;
              line-height: 30px;
              margin-right: 20px;
              cursor: pointer;
            }
            .active{
              color: $main-color;
            }
          }
          .content-third{
            border: 1px solid $border-color;
            border-radius: 4px;
            padding: 10px;
          }
        }
      }
    }
  }
</style>