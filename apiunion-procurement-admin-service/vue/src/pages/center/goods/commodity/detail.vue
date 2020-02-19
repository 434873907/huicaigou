<template>
  <div class="goods-detail">
    <div class="operateBox">
        <h2 class="big-title">操作</h2>
        <div class="save-inner">
            <a href="javascript:;" class="btn-main" @click="saveGoods()">保存</a>
            <router-link :to="{ name: '商品管理'}" class="btn-fill-main">返回</router-link>
        </div>
    </div>
    <!-- <div class="save-box">
      <a href="javascript:;" class="btn-main" @click="saveGoods()">保存</a>
      <router-link :to="{ name: '商品管理'}" class="btn-fill-main">返回</router-link>
    </div> -->
    <h2 class="big-title" v-show="firstCateList.length>0">类目选择</h2>
    <div class="type-wrap" v-show="firstCateList.length>0">
      <div class="type-content-wrap" :class="{'mb15':secondCateList.length>0}">
        <div class="type-title">一级类目：</div>
        <div class="type-content border-bottom">
          <div
            class="type-item"
            v-for="(item, index) in firstCateList"
            :key="index+'first'"
            :class="{'active':selectedCate.first===item.id}"
          >
            <!-- <a href="javascript:;" @click="selectCate(1, item)">{{item.name}}({{item.count}})</a> -->
            <a
              href="javascript:;"
              class="ellipsis"
              :class="{'pr40':item.count>99}"
              @click="selectCate(1, item)"
            >
              {{item.name}}
              <span class="item-count">({{item.count}})</span>
              <!-- <span class="item-count" v-show="item.count>99">(99+)</span> -->
            </a>
          </div>
        </div>
      </div>
      <div
        class="type-content-wrap"
        :class="{'mb15':thirdCateList.length>0}"
        v-show="secondCateList.length>0"
      >
        <div class="type-title">二级类目：</div>
        <div class="type-content">
          <div
            class="type-item"
            v-for="(item, index) in secondCateList"
            :key="index+'second'"
            :class="{'active':selectedCate.second===item.id}"
          >
            <!-- <a href="javascript:;" @click="selectCate(2, item)">{{item.name}}({{item.count}})</a> -->
            <a
              href="javascript:;"
              class="ellipsis a1"
              :class="{'pr40':item.count>99}"
              @click="selectCate(2, item)"
            >
              {{item.name}}
              <span class="item-count">({{item.count}})</span>
              <!-- <span class="item-count" v-show="item.count>99">(99+)</span> -->
            </a>
          </div>
        </div>
      </div>
      <div class="type-content-wrap mb15" v-show="thirdCateList.length>0">
        <div class="type-title">三级类目：</div>
        <div class="type-content">
          <div
            class="type-item"
            v-for="(item, index) in thirdCateList"
            :key="index+'third'"
            :class="{'active':selectedCate.third===item.id}"
          >
            <!-- <a href="javascript:;" @click="selectCate(3, item)">{{item.name}}({{item.count}})</a> -->
            <a
              href="javascript:;"
              class="ellipsis a2"
              :class="{'pr40':item.count>99}"
              @click="selectCate(3, item)"
            >
              {{item.name}}
              <span class="item-count">({{item.count}})</span>
              <!-- <span class="item-count" v-show="item.count>99">(99+)</span> -->
            </a>
          </div>
        </div>
      </div>
    </div>
    <h2 class="big-title">宝贝图片<div class="sugg" style="display:inline-block;font-size: 12px;color: #666;vertical-align:top;margin-left:10px;">尺寸建议为800x800px，大小不超过1M，限制8张图片</div></h2>
    <div class="img-wrap">
      <div class="main-img">
        <img :src="mainImgUrl" v-show="mainImgUrl" />
        <div class="main-img-null" v-show="!mainImgUrl">未设置</div>
        <p>商品主图</p>
      </div>
<!--      <p>{{goodsImages}}</p>-->

      <div style="display: inline-block">
        <draggable v-model="goodsImages" >
          <transition-group tag="div" class="drop-wrapper">
            <div class="img-item" v-for="(item, index) in goodsImages" :key="index+'img'">
              <img :src="item.url" />
              <div class="set-main">
                <a class="btn-fill-org-main" @click="setMainImg(item)">设为主图</a>
              </div>
              <img src="~assets/goods/remove_spec.png" class="remove-img" @click="removeImg(item, index)" />
            </div>
          </transition-group>
        </draggable>
      </div>


      <a href="javascript:;" class="addGoodsImg" v-show="goodsImages.length<=7">
        <upload uploadType="2" v-on:uploadSuccess="uploadSuccess">
          <img src="~/assets/goods/addGoodsImg.png" />
        </upload>
      </a>
      <!-- <el-popover v-show="goodsImages.length>4"
                placement="right"
                width="260"
                trigger="hover">
                <p>最多选择5张图片</p>
                <div class="noMoreGoodsImg" slot="reference">
                    <img src="~/assets/goods/addGoodsImg.png"/>
                </div>
      </el-popover>-->
      <!-- <div class="sugg">（商品图片尺寸建议800X800px，最多8张图片）</div> -->
        <div class="sugg" v-if="false">（商品图片尺寸建议800X800px，最多8张图片）</div>

    </div>
    <h2 class="big-title">宝贝参数</h2>
    <div class="info-wrap" ref="mainCON" id="mainCON">
      <h2 class="info-title">基本介绍:</h2>
      <div class="info-item">
        <span class="info-item-title">所属品牌</span>
        <input
          type="text"
          class="form-control"
          @focus="showBrandModal=true"
          v-model="goodsParams.brandName"
          placeholder="请选择商品品牌"
          style="width:748px;cursor: pointer"
        />
        <span class="choic-icon">
          <span class="sanjiao"></span>
          <span class="sanjiao bottom"></span>
        </span>
      </div>
      <div class="info-item">
        <span class="info-item-title">商品店铺</span>
        <input
          type="text"
          class="form-control"
          @focus="showShopModal=true"
          v-model="goodsParams.shopName"
          placeholder="请选择商品店铺"
          style="width:748px;cursor: pointer"
        />
         <span class="choic-icon">
          <span class="sanjiao"></span>
          <span class="sanjiao bottom"></span>
        </span>
      </div>
      <div class="info-item">
<!--        <el-form-item label="发货方式" prop="sourceItemAttr.channelTypes" >-->
<!--          <el-select v-model="goodsParams.nameLabels" class="select-multiple" multiple size="mini" placeholder="">-->
<!--            <el-option v-for="item in labelList"-->
<!--                       :key="item.id"-->
<!--                       :label="item.name"-->
<!--                       :value="item.id">-->
<!--            </el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
        <span class="info-item-title">商品标签</span>
        <el-select v-model="goodsParams.nameLabels" filterable placeholder="请选择商品标签">
          <el-option
                  v-for="item in labelList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
          </el-option>
        </el-select>
        <span class="choic-icon">
          <span class="sanjiao"></span>
          <span class="sanjiao bottom"></span>
        </span>
        <span class="limit30">（选择标签后,用户端商品页会展示该标签）</span>
      </div>
      <div class="info-item">
        <span class="info-item-title">商品名称</span>
        <input
          type="text"
          class="form-control"
          v-model="goodsParams.name"
          maxlength="50"
          placeholder="请输入商品名称"
          style="width:748px;"
        />
        <span class="limit30">（限制50字符）</span>
        <!-- <span class="info-item-title">品牌名称</span>
                <el-select class="el-form-control" v-model="goodsParams.brandName" filterable remote :remote-method="remoteMethod" @change="chooseBrand()">
                    <el-option
                        v-for="item in searchBrandList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.name">
                    </el-option>
        </el-select>-->
      </div>
      <!-- <div class="info-item">
                <span class="info-item-title">温馨提示</span>
                <input type="text" class="form-control" v-model="goodsParams.info"/>
      </div>-->
      <div class="info-item">
        <span class="info-item-title">商品型号</span>
        <input
          type="text"
          class="form-control"
          v-model="goodsParams.modelNo"
          maxlength="30"
          placeholder="请输入您的商品型号"
          style="width:748px;"
        />
        <span class="limit30">（限制30字符）</span>
      </div>
      <div class="info-item">
        <span class="info-item-title">宝贝描述</span>
        <textarea
          class="form-control desc"
          @input="limit300()"
          placeholder="请输入您的宝贝描述"
          maxlength="300"
          v-model="goodsParams.desc"
        ></textarea>
        <span class="limit30">（限制300字符）</span>
        <span class="limit300" style="padding-bottom: 8px">
          {{descLength}}
          <span>/300</span>
        </span>
      </div>
      <div class="info-item-box">
        <div class="info-item">
          <span class="info-item-title" >上架状态</span>
          <el-radio class="goods-status"  v-model="goodsParams.status" v-if="false" :label="0">待审核</el-radio>
          <el-radio class="goods-status" style="padding-left: 10px" v-model="goodsParams.status" :label="1">上架</el-radio>
          <el-radio class="goods-status" v-model="goodsParams.status" :label="2">下架</el-radio>
          <span class="line">|</span>
        </div>
        <div class="info-item">
          <span class="info-item-title" style="width: 86px;padding-right: 20px">设为赠品</span>
          <el-radio class="goods-status" v-model="goodsParams.gift" :label="1">是</el-radio>
          <el-radio class="goods-status" v-model="goodsParams.gift" :label="0">否</el-radio>
          <span class="line">|</span>
        </div>
        <div class="info-item">
          <span class="info-item-title" style="width: 86px;padding-right: 20px">预售商品</span>
          <el-radio class="goods-status" v-model="goodsParams.presell" :label="1">是</el-radio>
          <el-radio class="goods-status" v-model="goodsParams.presell" :label="0">否</el-radio>
        </div>
      </div>
    </div>

    <div class="spec-wrap">
      <h2 class="big-title shuTiao">宝贝规格:</h2>
      <div class="name-item skuTitle">
        <span>货币</span>
        <select class="form-control" v-model="goodsParams.currency">
          {{goodsParams.currency}}
          <option
            v-for="(item, index) in currencyList"
            v-bind:value="item.code"
            :key="index+'currency'"
          >{{item.symbol}} {{item.name}} <span v-if="item.rate">({{item.rate}})</span></option>
        </select>
      </div>
      <div class="name-item skuTitle">
        <span>商品单位</span>
        <select
          class="form-control"
          v-model="goodsParams.unit"
          :disabled="goodsParams.unitCanChange === 0"
        >
          {{goodsParams.unit}}
          <option
            v-for="(item, index) in unitList"
            v-bind:value="item.unit"
            :key="index"
          >{{item.unit}}</option>
        </select>
      </div>
      <div class="name-item title-item">
        <span>商品规格</span>
        <div class="addBtn" @click="addSkuRow(goodsParams.skuList)">添加规格名</div>
        <span class="lookDemo" slot="reference">
          查看示例
          <svg class="icon" aria-hidden="true" style="color:#999999;">
            <use xlink:href="#icon-help-fill" />
          </svg>
          <img src="~assets/goods/lookDemo.png" class="lookDemoimg" @click="removeImg(item, index)" />
        </span>
      </div>

      <div
        class="name-item skuItemBox"
        v-for="(item, index) in setSkuNameValues"
        :key="index + 'sku'"
      >
        <div class="skuNameBox">
          <h2 style="width: 76px">规格名</h2>
          <div class="skuNameInput">
            <input
              type="text"
              class="form-control name-input"
              placeholder="如:size"
              v-model="item.skuName"
              @blur="modifySkuName(item.skuName,index)"
            />
            <img src="~assets/goods/remove_spec.png" @click="removeSkuRow(index)" />
          </div>
        </div>
        <div class="skuValueBox">
          <h2 style="width: 76px">规格值</h2>
          <div class="skuValueContBox-cont">
            <div class="skuValueContBox">
              <div
                class="skuValueCont"
                v-for="(value, valueIndex) in item.skuValues"
                :key="valueIndex"
              >
<!--     值input和删除     -->
                <div class="skuValueInput">
                  <input
                    type="text"
                    class="form-control name-input"
                    v-model="value.name"
                    @focus="diffValue(value.name)"
                    @blur="maxSkuList(item, index, true,value.name)"
                  />
                  <img src="~assets/goods/remove_spec.png" @click="removeSkuValue(item,valueIndex)" />
                </div>
<!--                  添加图片 -->
                <div class="sku-img" v-if="index===0">
                  <upload
                    uploadType="2"
                    v-on:uploadSuccess="uploadSKUSuccess"
                    :index="valueIndex"
                    v-show="!goodsParams.firstSpecImages[valueIndex]"
                  >
                    <a class="btn-fill-org-main">添加图片</a>
                  </upload>
                  <img
                    :src="goodsParams.firstSpecImages[valueIndex]"
                    class="imageUrl"
                    v-show="goodsParams.firstSpecImages[valueIndex]"
                  />
                  <upload
                    uploadType="2"
                    v-on:uploadSuccess="uploadSKUSuccess"
                    :index="valueIndex"
                    v-show="goodsParams.firstSpecImages[valueIndex]"
                  >
                    <a class="link">更换</a>
                  </upload>
                  <a
                    class="link del-spec-img"
                    v-show="goodsParams.firstSpecImages[valueIndex]"
                    @click="removeSpecImg(valueIndex)"
                  >删除</a>
                </div>
              </div>
              <a class="addSkuBtn" @click="addSkuValue(item)">添加规格值</a>
            </div>
          </div>
        </div>
      </div>
      <div>
        <a class="btn-fill-main" @click="openMixModal()" v-show="false">添加组合装</a>
        <span class="set-sku-weight" v-show="false">
          <span class="text">添加重量</span>
          <ApiInput v-model="batchWeight" />
          <span class="kg">KG</span>
          <a href="javascript:;" class="btn-main" @click="batchSetWeight">添加</a>
        </span>
      </div>

      <div class="spec-list" style="min-width: 1200px;overflow: auto;">
          <div class="dispatchBox">
              <div class="settingBox">批量设置</div>
              <div class="printBox" @click="setPrintMask(1)">售价</div>
              <div class="printBox" @click="setPrintMask(2)">企业价</div>
              <div class="printBox" @click="setPrintMask(3)">原价</div>
          </div>
        <table class="tableBox skuBoxTable" :class="{autoWidth:autoWidth<=1280}">
          <thead>
            <tr>
              <th class="dot" :class="{'active':checkAllPtint}" @click="selectAllPrint()"></th>
              <th class="noWrap table-w120">规格</th>
              <!-- <th>UPC</th> -->
              <th class="table-w120">条形码</th>
              <th class="table-w80">重量(kg)</th>
              <th class="table-w130">
                发货方式
                <!-- <span class="mustChoose">*</span> -->
              </th>
              <th class="table-w130">
                运费模板
                <!-- <span class="mustChoose">*</span> -->
              </th>
              <th class="table-w80">
                售价({{currencySymbol}})
                <!-- <span class="mustChoose">*</span> -->
              </th>
              <th class="table-w80">
                企业价({{currencySymbol}})
                <!-- <span class="mustChoose">*</span> -->
              </th>
              <th class="table-w80">
                原价({{currencySymbol}})
                <!-- <span class="mustChoose">*</span> -->
              </th>
              <th class="table-w80">数量({{goodsParams.unit}})</th>
              <th class="table-w100">商品编码</th>
              <th class="table-w80">总库存</th>
              <th class="noWrap table-w60" v-if="userConfig.hasWeiNiPrice&&id!=1&&id!=''">唯妮价</th>
              <th class="noWrap table-w170">操作</th>
            </tr>
          </thead>
          <tbody
            v-for="(item, index) in goodsParams.skuList"
            :key="index"
            :class="{'selectedTr':selectedSKUName===item.displayName}"
          >
            <tr v-for="(skuVal,skuValIndex) in item.skuChannels" :key="skuValIndex">
                <td class="dot" :class="{'active':skuVal.checked}" @click="selectItemPrint(skuVal,index,skuValIndex)"></td>
                <td class="noWrap sizeValue elips">
<!--                分别对应多件装和规格名-->
                <el-popover
                        placement="top-start"
                        width="200"
                        trigger="hover"
                        :content="item.valueName ? `${item.valueName}-${item.quantity}${goodsParams.unit}装` : `${item.quantity}${goodsParams.unit}装`">
                  <div style="display: inline-block" slot="reference">
                    <span v-if="item.valueName">{{item.valueName}}-</span>
                    <span v-if="item.quantity">{{item.quantity}}</span>{{goodsParams.unit}}装
                  </div>
                </el-popover>


              </td>
              <td>
                <input
                  type="text"
                  class="form-control"
                  :class="{'form-control-error': status == 0}"
                  :disabled="skuVal.hasSyncSupplier==1"
                  v-model="item.upc"
                  style="width:110px;"
                />
              </td>
<!--重量-->
              <td>
                <ApiInput v-model="item.weight" :disabled="skuVal.hasSyncSupplier==1" style="width:70px;"/>
              </td>

              <td>
                <select
                  class="form-control noWrap"
                  v-model="skuVal.channelType"
                  :disabled="skuVal.hasSyncSupplier==1"
                  :class="{'form-control-error': skuVal.hasSyncSupplier}"
                  style="width:120px;"
                >
                  <option selected disabled value>请选择发货方式</option>
                  <option
                    v-for="(optionItem,index) in channelList"
                    v-bind:value="optionItem.channelType"
                    :key="optionItem.channelType + index"
                  >{{optionItem.channelName||optionItem.channelTypeName}}</option>
                </select>
              </td>
              <td>
                <select
                  class="form-control"
                  v-model="skuVal.expressFeeTemplateId"
                  :class="{'unset-select':!skuVal.expressFeeTemplateId}"
                  style="width:120px;"
                >
                  <option selected disabled value>请选择运费模板</option>
                  <option
                    v-for="temp in expressTempList"
                    :key="temp.id"
                    :value="temp.id"
                  >{{temp.templateName}}</option>
                </select>
              </td>
              <td>
                <input
                  placeholder
                  class="form-control"
                  v-model="skuVal.price"

                  style="width:70px;"
                />
              </td>
              <td>
                <ApiInput v-model="skuVal.enterprisePrice" @blur="handlePriceBlur(skuVal)" style="width:70px;"/>
              </td>
              <td>
                <ApiInput v-model="skuVal.originalPrice" style="width:70px;"/>
              </td>
<!--              数量-->
              <td>
                <ApiInput :disabled="true" v-if="!item.isMultiple" v-model="item.quantity" style="width:66px;"/>
                <ApiInput
                  v-if="item.isMultiple"
                  v-model="item.quantity"
                  @focus="diffNum(item.weight,item.quantity)"
                  @input="handleNum(item,index)"
                  style="width:66px;"
                />
              </td>
              <td class="elips">{{skuVal.itemCode}}</td>
              <td>
                <ApiInput style="width:60px;" :disabled="true" v-model="skuVal.totalStock" />
              </td>
              <td class="weini noWrap" v-if="userConfig.hasWeiNiPrice&&id!=1&&id!=''">
                <span v-if="skuVal.refPrice">{{skuVal.refPrice.weiniPrice}}</span>
              </td>
              <td class="noWrap">
                  <a href="javascript:;" class="link" :class="{'select':skuVal.show}" @click.stop="btnSetSupplier(skuVal,index,skuValIndex,item)">供货商设置</a>
                  <a
                          href="javascript:;"
                          v-if="!item.isMultiple"
                          class="link"
                          @click.stop="addRow(index,skuVal)"
                          style="margin-left: 10px;"
                  >添加多件装</a>
                  <a
                          href="javascript:;"
                          v-if="item.isMultiple"
                          class="del link"
                          @click.stop="delRow(index)"
                  >删除多件装</a>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="supportBox" v-if="selectedSKUName">
      <h2 class="big-title">供货商设置</h2>
      <table class="tableBox table">
        <thead>
          <tr>
            <th class="table-w140">供货商名称</th>
            <th class="table-w140">供应商旧价({{currencySymbol}})</th>
            <th class="table-w140">供应商新价({{currencySymbol}})</th>
            <th v-if="id!=1" class="table-w40"></th>
            <th class="table-w140">供应商库存</th>
            <th class="table-w140">
              <el-popover class="ellipsis" placement="top-end" width="200" trigger="hover">
                <div class="alertBox">
                 利润率=(企业价-供货价)/供货价*100%
                </div>
                <span class="operation" slot="reference" style="color:#262626;">
                  利润率
                  <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-help-fill" />
                  </svg>
                </span>
              </el-popover>
            </th>
            <th class="operateTh table-w140">
              <el-popover class="ellipsis" placement="top-end" width="200" trigger="hover">
                <div class="alertBox">
                  开启后该供货商商品将会正常销售，否则不销售
<!--                  <div class="itemCont">-->
<!--                    <el-switch :value="true" active-color="#13ce66"></el-switch>-->
<!--                    <span>指启用</span>-->
<!--                  </div>-->
<!--                  <div class="itemCont">-->
<!--                    <el-switch :value="false"></el-switch>-->
<!--                    <span>指禁用</span>-->
<!--                  </div>-->
                </div>
                <span class="operation" slot="reference">
                  销售状态
                  <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-help-fill" />
                  </svg>
                </span>
              </el-popover>
            </th>
            <th class="operateTh" v-show="id!= 1&&id!== ''">
              <el-popover class="ellipsis" placement="top-end" width="200" trigger="hover">
                <div class="alertBox">
                  开启后该供货商商品将保留在商城，否则不保留
<!--                  <div class="itemCont">-->
<!--                    <el-switch :value="true" active-color="#13ce66"></el-switch>-->
<!--                    <span>指启用</span>-->
<!--                  </div>-->
<!--                  <div class="itemCont">-->
<!--                    <el-switch :value="false"></el-switch>-->
<!--                    <span>指禁用</span>-->
<!--                  </div>-->
                </div>
                <span class="operation" slot="reference">
                  审核开关
                  <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-help-fill" />
                  </svg>
                </span>
              </el-popover>
            </th>
            <th>变更记录</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in goodsParams.supplierStocks" :key="index+'channel'">
            <td>
              <el-popover placement="top" width="120" trigger="hover">
                <span>{{item.supplierName}}</span>
                <span class="ellipsis" slot="reference">{{item.supplierName}}</span>
              </el-popover>
            </td>
            <td>
              <input
                placeholder
                class="form-control"
                :disabled="true"
                v-model="item.approvedChannelPrice"
              />
            </td>
            <td>
              <input
                placeholder
                class="form-control"
                :disabled="Boolean(item.thirdAttr && item.thirdAttr.authId)"
                v-model="item.channelPrice"
                @blur="handleBlur(item)"
              />
            </td>
            <td v-if="id!=1">
              <img src="~assets/goods/rise.png" v-if="item.channelPrice>item.approvedChannelPrice" />
              <img src="~assets/goods/fall.png" v-if="item.channelPrice<item.approvedChannelPrice" />
            </td>
            <td>
              <input
                placeholder
                class="form-control"
                :disabled="Boolean(item.thirdAttr && item.thirdAttr.authId)"
                v-model="item.stock"
              />
            </td>
            <td>
              <span
                      v-if="item.isRed" style="color:#F56C6C;">{{item.profitRate}}</span>
              <span v-if="!item.isRed">{{item.profitRate}}</span>
            </td>
            <td class="switchOperate">
              <a href="javascript:;" class="link">
                <el-switch
                  v-model="item.status"
                  :active-value="1"
                  :inactive-value="0"
                  active-color="#13ce66"
                ></el-switch>
              </a>
            </td>
            <td class="switchOperate" v-show="id!= 1&&id!== ''">
              <a href="javascript:;" class="link">
                <el-switch
                  v-model="item.approvedStatus"
                  :active-value="1"
                  :inactive-value="0"
                  :disabled="Boolean(item.isLock)"
                  active-color="#13ce66"
                ></el-switch>
              </a>
            </td>
            <td   style="line-height: 48px;" >
              <span v-if="item.changes&&item.changes.length>0" @click="log(goodsParams,item)" class="link">查看记录</span>
              <span v-else>无记录</span>
            </td>
            <td></td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="sameGroupBox" v-if="userConfig.hasWeiNiPrice&&id!=1&&id!=''">
      <div class="top" :class="{topline:sameGroupTableShow && groupData.groupNum}">
        <h2 class="big-title shuTiao title">同组宝贝:</h2>
        <div class="boxInFo">
          <div class="totalInfo">
            <span v-if="groupData.groupNum">同组商品共计{{groupData.groupNum}}个(含当前商品)</span>
            <span v-else>暂无同组宝贝</span>
          </div>
          <div class="operateBtn" @click="sameGroupTableVisible">
            <span v-if="groupData.groupNum" style="cursor: pointer;">管理同组商品</span>
            <span v-else>添加同组商品</span>
          </div>
        </div>
      </div>
      <div class="cont" v-if="sameGroupTableShow && groupData.groupNum">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane :label="labelName.firstLabel" name="first"></el-tab-pane>
          <el-tab-pane :label="labelName.secondLabel" name="second"></el-tab-pane>
        </el-tabs>
        <div class="operate">
          <div class="left">
            <div class="dot" :class="{'dotAll':checkAll}" @click="selectAll()"></div>
            <div class="dotName">全选</div>
            <div class="operateBtn">
              <span
                @click="groupDete()"
                v-if="activeName == 'first'"
                :class="{'active':avtiveOk}"
              >批量剔除</span>
              <span @click="groupAdd()" v-else :class="{'active':avtiveOk}">批量添加</span>
            </div>
          </div>
          <div class="serachBox" v-if="activeName == 'second'">
            <span class="title">关键字</span>
            <el-input v-model="input" placeholder="请输入商品名称、商品ID、条形码"></el-input>
            <span class="searchBtn" @click="search">搜索</span>
          </div>
        </div>
        <table class="table">
          <thead>
            <tr>
              <th class="dot" :class="{'active':checkAll}" @click="selectAll()"></th>
              <th class="table-w100">宝贝ID</th>
              <th class="table-w60">主图</th>
              <th class="table-w160">商品名称</th>
              <th class="table-w100">品牌</th>
              <th class="table-w100">型号</th>
              <th class="table-w80">价格</th>
              <th class="table-w100">发货方式</th>
              <th class="table-w100">操作</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(item, index) in sameGroupTableData"
              :key="index+'table'"
              :class="{'selectedTr':item.checked,'noSuccess':item.status}"
            >
              <td class="dot" :class="{'active':item.checked}" @click="selectItem(item)"></td>
              <td>{{item.id}}</td>
              <td>
                <el-popover placement="top" width="260" trigger="hover" class="imgBoxCont">
                  <img style="width:100%;height:100%" :src="item.mainImageUrl" />
                  <div slot="reference">
                    <img class="imgBox" :src="item.mainImageUrl" />
                  </div>
                </el-popover>
              </td>
              <td>
                <el-popover placement="top" width="260" trigger="hover">
                  <span>{{item.name}}</span>
                  <span
                    class="ellipsis goodsName"
                    @click="goDetail(item)"
                    slot="reference"
                  >{{item.name}}</span>
                </el-popover>
              </td>
              <td>{{item.brandName}}</td>
              <td>
                <el-popover placement="top" width="260" trigger="hover">
                  <span>
                    <span v-for="(sku, sku_index) in item.skuList" :key="sku_index+'sku'">
                      {{sku}}
                      <span v-show="sku_index != item.skuList.length-1">、</span>
                    </span>
                  </span>
                  <span slot="reference" class="ellipsis">
                    <span v-for="(sku, sku_index) in item.skuList" :key="sku_index+'sku'">
                      {{sku}}
                      <span v-show="sku_index != item.skuList.length-1">、</span>
                    </span>
                  </span>
                </el-popover>
              </td>
              <td>
                <span class="itemPrice">{{item.price}}</span>
              </td>
              <td>
                <el-popover class="ellipsis" placement="top" width="260" trigger="hover">
                  <span>
                    <span
                      v-for="(sku, channels_index) in item.channels"
                      :key="channels_index+'sku'"
                    >
                      {{sku}}
                      <span v-show="channels_index != item.channels.length-1">、</span>
                    </span>
                  </span>
                  <span slot="reference" class="transportStyle">
                    <span v-for="(sku, sku_index) in item.channels" :key="sku_index+'sku'">
                      {{sku}}
                      <span v-show="sku_index != item.channels.length-1">、</span>
                    </span>
                  </span>
                </el-popover>
              </td>
              <td>
                <a
                  href="javascript:;"
                  class="link base-margin-right"
                  v-if="activeName == 'first'&&item.id !== adminGoodsData.id"
                  @click="deteItem(item)"
                >剔除</a>
                <a
                  href="javascript:;"
                  v-if="activeName == 'second'&&item.id !== adminGoodsData.id"
                  class="link"
                  @click="addItem(item)"
                >添加</a>
              </td>
            </tr>
          </tbody>
        </table>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="listParams.page"
          layout="total, sizes, prev, pager, next, jumper"
          :limit.sync="listParams.limit"
          @pagination="getGoodsList()"
        />
        <noData v-if="total===0"></noData>
      </div>
    </div>


    <div class="editor-wrap">
      <h2 class="big-title shuTiao">图文详情<span style="display:inline-block;font-size: 12px;color: #666;vertical-align:top;margin-left:10px;">尺寸建议为750x任意高度，大小不超过1M</span></h2>
      <editor v-model="goodsParams.richDesc" :isGoods="true"></editor>
    </div>
    <div class="save-box" v-if="false">
        <h2 class="title">操作</h2>
        <div class="save-inner">
            <a href="javascript:;" class="btn-main" @click="saveGoods()">保存</a>
            <router-link :to="{ name: '商品管理'}" class="btn-fill-main">返回</router-link>
        </div>
    </div>
    <selectShop
      v-if="showShopModal"
      v-on:dialogShop="dialogShop"
      v-on:dialogShopCancel="dialogShopCancel"
      :multiply="false"
    ></selectShop>
    <SelectBrand
      v-if="showBrandModal"
      v-on:dialogBrand="dialogBrand"
      v-on:dialogBrandCancel="dialogBrandCancel"
      :multiply="false"
    ></SelectBrand>
    <SelectBrand
            v-if="showLabelModal"
            v-on:dialogBrand="dialogBrand"
            v-on:dialogBrandCancel="dialogBrandCancel"
            :multiply="false"
    ></SelectBrand>
    <el-dialog title="添加组合装:" :visible.sync="addShow" width="840px">
      <div class="dialog-form">
        <div class="modal-item" v-for="(item, index) in modalSkuValues" :key="index+'sku'">
          <span>规格{{index+1}}可选</span>
          <div class="item-content">
            <span
              class="spec-item"
              v-for="(val, val_index) in item.skuValues"
              :key="val_index+'val'"
              @click="selectMixItem(item, val)"
              :class="{'spec_active':item.activeSpec===val}"
            >{{val}}</span>
          </div>
        </div>
        <div class="modal-item current-mix-box">
          <span>当前已选</span>
          <div class="item-content" v-show="modalSkuValues.length>0">
            <span class="current-mix" v-for="(item, index) in modalSkuValues" :key="index+'cur'">
              {{item.activeSpec}}
              <span v-show="index != modalSkuValues.length-1">+</span>
            </span>
            <a href="javascript:;" class="btn-fill-main" @click="appendMix()">添加</a>
          </div>
        </div>
        <div class="set-current">
          <input
            type="text"
            class="form-control set-name"
            placeholder="请输入组合名称"
            v-model="modalParams.groupSkuValue"
          />
          <span class="colon" v-show="mixedList.length>0">:</span>
          <div class="conected-spec" v-for="(item, index) in mixedList" :key="index+'mix'">
            <span class="conected-spec-name">
              {{item.mixedName}}
              <img src="~assets/goods/remove_mix_spec.png" />
            </span>
            <span class="conected-spec-count">
              <span class="count">{{item.quantity}}</span>
              <span class="set-count">
                <span class="add-count" @click="setQuantity(1, item)"></span>
                <span class="reduce-count" @click="setQuantity(2, item)"></span>
              </span>
            </span>
            <span class="colon" v-if="index<mixedList.length-1">
              <img src="~assets/goods/connect.png" />
            </span>
          </div>
        </div>
        <div class="modal-item modal-item-bot">
          <span>重量</span>
          <ApiInput placeholder="请输入组合重量" v-model="modalParams.weight" />
        </div>
        <div class="modal-item modal-item-bot">
          <span>原价</span>
          <ApiInput placeholder="请输入原价" v-model="modalParams.originalPrice" />
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="saveModal()">保存</button>
        <button class="btn-fill-grey-main" @click="addShow = false">取消</button>
      </span>
    </el-dialog>

    <el-dialog title="渠道设置:" :visible.sync="showChannelModal" width="840px">
      <div class="dialog-form channel-dialog">
        <div class="search-item selected－brand">
          <span class="title">已选渠道：</span>
          <span
            class="selected-item"
            v-for="(item, index) in selectedChannelList"
            :key="index+'selected'"
          >
            {{item.channelTypeName}}
            <a href="javascript:;" @click="removeChannel(index)">X</a>
          </span>
        </div>
        <div class="search-item selected-box">
          <span class="brand-item" v-for="item in channelList" :key="item.channelType">
            <a class="link" @click="selectChannel(item)">{{item.channelTypeName}}</a>
          </span>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="saveChannelModal()">保存</button>
        <button class="btn-fill-grey-main" @click="showChannelModal = false">取消</button>
      </span>
    </el-dialog>

    <el-dialog title="供货商变更历史记录" :visible.sync="logTar" width="600px" >
      <div class="tableLog">
        <table>
          <tr>
            <th>变更时间</th> <th>变更内容</th> <th>变更类型</th>
          </tr>
          <tr v-for="(v,i) in logData" :key="i">

            <td >{{v.createTime}}</td> <td>{{v | content }}</td> <td>{{v | typeName}}</td>
          </tr>
<!--    1, 供价变化 3, 下架商品 2, 重量调整      -->

        </table>
      </div>
      <div class="log-more" v-if="logDataLen>30">
        只显示最近30条记录
      </div>
    </el-dialog>
      <el-dialog style="margin-top: 150px" :title="setPrintIndex==1?'批量设置售价':setPrintIndex==2?'批量设置企业价':'批量设置原价'" :visible.sync="setPrint" width="340px" >
          <div class="setPrintBox">
             <div class="setPrintItem">
                 <span class="moneyIcon">￥</span> <input class="moneyInput" type="number"  v-model="setPrintValue" placeholder="请输入商品价格"></input>
             </div>
              <div class="tipsTitle">
                  注意：仅可输入数字，最多2位小数
              </div>
          </div>
          <span slot="footer" class="dialog-footer">
            <button class="btn-org-main" @click="setPrintBtn()">保存</button>
            <button class="btn-fill-grey-main" @click="setPrint = false">取消</button>
          </span>
      </el-dialog>
  </div>
</template>
<script>
import selectShop from "@/components/selectShop/index.vue";
import selectBrand from "@/components/selectBrand/index.vue";
import editor from "@/components/tinymce/tinymce.vue";
import Pagination from "@/components/pagination";
import noData from "@/components/noData";
import draggable from 'vuedraggable'
// let {log}=console; //解构赋值一例，
export default {
  data() {
    return {
      setPrint:false,
      setPrintValue:'',
      setPrintIndex:1,
      options:[],//商品标签列表
      logTar:false,
      logData:[],
      logDataLen:0,
      isExam: false, //供货商信息是否可审核  现在可去掉
      status: 1,
      id: "",
      isShelves: true,
      showShopModal: false, // 选择店铺弹窗
      showBrandModal: false, // 选择品牌弹窗
      showLabelModal:false,//标签弹窗
      addShow: false, // 组合装弹窗
      showChannelModal: false, // 渠道设置弹窗
      detailLoaded: false,
      selectedChannelList: [],
      selectedSKUName: "",
      selectedSKUItem: {},
      categoryTree: [],
      mainImgUrl: "",
      firstCateList: [],
      secondCateList: [],
      thirdCateList: [],
      searchBrandList: [],
      searchShopList: [],
      selectedCate: {
        first: "",
        second: "",
        third: ""
      },
      batchWeight: "",
      currencyList: [],
      channelList: [],
      expressTempList: [],
      setSkuNameValues: [],
      goodsImages: [],
      goodsParams: {
        status: 1,
        gift: 0,
        presell: 0,
        currency: "",
        unit: "",
        unitCanChange: "",
        richDesc: "",
        categoryId: "",
        brandId: "",
        brandName: "",
        desc: "",
        shopId: "",
        shopName: "",
        modelNo: "",
        firstSpecImages: [],
        delImageIds: [],
        skuNameValues: [
          // {skuName:'',skuValues:'',skuValueImages:[]}
        ],
        skuList: [],
        supplierStocks: [],
        nameLabels:''
      },
      modalSkuValues: [],
      mixedList: [],
      modalParams: {},
      currentModal: [],
      supplierList: [],
      supplierList1: [],//新增时的供应商
      skuTable: {
        skuChannels: []
      },
      checkAll: false,
      sameGroupTableData: [],
      avtiveOk: false,
      avtiveNo: false,
      total: 1,
      listParams: {
        page: 1,
        limit: 10
      },
      activeName: "first",
      labelName: { firstLabel: "管理同组商品", secondLabel: "增加同组商品" },
      input: "",
      adminGoodsData: {},
      groupData: {
        groupNum: 0
      },
      sameGroupTableShow: false, //管理同组商品默认关闭
      currencySymbol: "",
      itemDetail: {},
      detailData: {}, //商品详情数据
      isRed: false, //利润率低于2.2红色显示
      isShow: false, //上传图片删除的icon
      unitList: [
        // {id:1,unit:'件'},
        // {id:2,unit:'套'},
        // {id:3,unit:'盒'}
      ],
      descLength: 0,
      isNoSku: false, //新增时的无规格单件装
      guivalue:'',//规格值中间值失焦
      newguivalue:'',//规格值中间值失焦
      weightValue:'',//重量中间值失焦
      autoWidth:'',
      labelList:[],
      checkAllPtint:false,
    };
  },
  mounted() {
    // this.$refs.operateBo.style.width = this.$refs.mainCON.clientWidth+'px';
    // this.autoWidth = this.$refs.mainCON.clientWidth+'px';
  },
  created() {
    this.status = this.$route.params.status;
    window.onresize =()=>{
      // this.autoWidth = this.$refs.mainCON.clientWidth+'px';
      this.autoWidth = document.body.clientWidth;
    }
    if (this.status == 1 || this.status == 2) {
      this.isExam = true;
    } else {
      this.isExam = false;
    }
    this.getNewSuppList();//新增商品或规格时获取供应商列表
    // this.$nextTick(()=>{


    // })
    this.init();
    //获取标签列表
    this.labelListByType();
  },
  filters:{
    content:function (cin) {
      // 1, 供价变化 3, 下架商品 2, 重量调整
      if(!cin.type){
        return "error"
      }
      if(cin.type==1){
        return `调整供货价由"￥${cin.originalValue}" 为 "￥${cin.value}"`;
      }else if(cin.type==3){
        return `将该商品进行下架`
      }else if(cin.type==2){
        return `调整重量由"${cin.originalValue}kg" 为 "${cin.value}kg";`
      }
    },
    typeName(cin){
      if(!cin.type){
        return "error"
      }
      if(cin.type==1){
        return `调整供价`;
      }else if(cin.type==3){
        return `下架商品`;
      }else if(cin.type==2){
        return `调整重量`;
      }
    },
    testBack(){
    // <span v-if="item.valueName">{{item.valueName}}-</span>
      //   <span v-if="item.quantity">{{item.quantity}}</span>{{goodsParams.unit}}装
      // let str='';
      // if(cin.valueName){ str+= cin.valueName + '-'}
      // if(cin.quantity){ str+= cin.quantity + '-'}
    }
  },
  methods: {
      //设置价格按钮
      setPrintMask(num){
          this.setPrintValue= '';

          var numFlag = 0;
          var skuList = this.goodsParams.skuList;
          skuList.map((itemBox) => {
              itemBox.skuChannels.map((val)=>{
                  if(val.checked){
                      numFlag+=1;
                  }

              })
          });
          if(numFlag>0){
              this.setPrintIndex = num;
              this.setPrint = true;
          }else {
              this.$message.info('请先选择规格')
          }

      },
      //循环是否有选中项
      selItemCheckout(){
          return new Promise((resolve,reject)=>{
              var skuList = this.goodsParams.skuList;
              skuList.map((itemBox) => {
                  itemBox.skuChannels.map((val)=>{
                      if(val.checked) {
                          this.flag = true
                          resolve(true);
                      }
                  })

              });
          })
      },
      //确认设置价格设置价格
      setPrintBtn(){
          if(!this.setPrintValue){
              this.$message.info('请输入价格')
              return false
          }
          var skuList = this.goodsParams.skuList;
          skuList.map((itemBox) => {
              itemBox.skuChannels.map((val)=>{
                  if(val.checked){
                      if(this.setPrintIndex==1){
                          val.price = this.setPrintValue
                      }else if(this.setPrintIndex==2){
                          val.enterprisePrice = this.setPrintValue
                      }else if(this.setPrintIndex==3){
                          val.originalPrice = this.setPrintValue
                      }

                  }

              })
          });
          this.setPrint = false
      },
      selectItemPrint(item,index,skuValIndex) {
          this.$set(this.goodsParams.skuList[index].skuChannels[skuValIndex],'checked',!item.checked)
          var skuList = this.goodsParams.skuList;
          this.checkAllPtint = true;
          skuList.forEach((itemBox) => {
              itemBox.skuChannels.forEach((val)=>{
                  if(!val.checked) {
                      this.checkAllPtint = false;
                  }
              })

          });
      },
      selectAllPrint() {
          this.checkAllPtint = !this.checkAllPtint;
          var skuList = this.goodsParams.skuList;
          skuList.forEach((itemBox) => {
              itemBox.skuChannels.forEach((val)=>{
                  if(this.checkAllPtint) {
                      val.checked = true;
                  } else {
                      val.checked = false;
                  }
              })

          });
      },

    //获取标签列表
    labelListByType(){
      this.api.goods.labelListByType({
        labelType:1
      }).then(res=>{
        this.labelList = res.data;
          this.labelList.unshift({
              name:'请选择商品标签'
          })
      })
    },
    init() {
      // 获取商品id
      this.id = this.$route.params.id || "";
      // 获取货币列表
      this.api.common.currency({}).then(res => {
        this.currencyList = res.data;
        this.goodsParams.currency = this.currencyList[0].code;
      });
      // 获取所有类目
      this.api.category.tree({ hasCount: true }).then(res => {
        this.categoryTree = res.data;
      });
      //获取单位
      this.api.goods.unitList({}).then(res => {
        this.unitList = res.data;
        // if(this.id){
        //     // 获取商品详情
        //     this.getDetail();
        // } else {
        //     this.goodsParams.unit = '件';
        // }
      });
      // 获取渠道列表
      this.api.goods.channel({}).then(res => {
        this.channelList = res.data;
        if (this.id && this.id != 1) {
          // 获取商品详情
          this.getDetail();
          this.getSuppList();//获取供应商列表
        } else {
          this.goodsParams.unit = "件";
        }
      });

      // 获取一级类目
      this.getCate(0, "");
      if (!this.id) {
        this.remoteMethod("");
        this.remoteShopMethod("");
      }

      // 获取运费模板列表
      this.api.feetemplate.allTemp({}).then(res => {
        this.expressTempList = res.data;
      });
      //获取同组商品信息
      this.adminGoodsData = JSON.parse(localStorage.getItem("groupGoodsInfo"));
      this.groupNum();
      this.listParams = {
        isGift: -1,
        page: 1,
        limit: 10,
        statusList: [1, 2],
        itemId: this.adminGoodsData.id
      };

      this.adminGoodsData.groupId ? (this.listParams.groupId = this.adminGoodsData.groupId): "";
      this.getGoodsList();

    },
    log(item){
      if(item.supplierStocks[0].changes.length==0){
        this.$message.warning({
          message: "无变更数据！",
          showClose: true
        });
      }else{
        this.logTar=true;
        this.logDataLen=item.supplierStocks[0].changes.length;
        if(this.logDataLen>30){
          this.logData=item.supplierStocks[0].changes.splice(0,30);
        }else{
          this.logData=item.supplierStocks[0].changes;
        }
      }
      //  出一个弹窗在这里
    },
    handlePriceBlur(skuval) {
      if (skuval.enterprisePrice) {
        skuval.supplierStocks.forEach(val => {
          if (val.channelPrice) {
            let num = (skuval.enterprisePrice - val.channelPrice) / val.channelPrice;
            val.profitRate = Math.round(num * 10000) / 100 + "%";
            if (Math.round(num * 10000) / 100 < 2.5) {
              val.isRed = true
            } else {
              val.isRed = false
            }
          }
        });
      }
    },
    handleBlur(item) {
      if (!this.itemDetail.price || !item.channelPrice) {
        return;
      }
      let num = (this.itemDetail.enterprisePrice - item.channelPrice) / item.channelPrice;
      item.profitRate = Math.round(num * 10000) / 100 + "%";
      if (Math.round(num * 10000) / 100 < 2.5) {
          item.isRed = true
        } else {
          item.isRed = false
        }
    },
    //diff规格值
    diffValue(val) {
      this.guivalue = val;
    },
    //新增商品获取供应商列表
    getNewSuppList() {
      this.api.supplier.newList({ channelType: 0 }).then(res => {
        res.data.forEach(item => {
          item.approvedChannelPrice = '';
          item.approvedStatus = 1;
          item.channelPrice = '';
          // item.profitRate = item.profitRate;
          item.status = 1;
          item.stock = '';
          item.supplierId = item.id;
          item.supplierName = item.supplierName;
        });
        this.supplierList1 = res.data;
        if (this.id == 1||this.id==="") {
          this.isNoSku = true;
          //无规格单件装初始化
          let obj = {
            upc: "",
            skuValues: [
                {
                skuName: "",
                skuValue: ""
                }
            ],
            originalPrice: "",
            weight: "",
            quantity: 1,
            valueName: "",
            transportWays: [],

            skuChannels: [
                {
                channelName: "",
                channelPrice: "",
                channelType: "",
                enterprisePrice: "",
                expressFeeTemplateId: "",
                hasSyncSupplier: false,
                itemCode: "",
                originalPrice: "",
                price: "",
                supplierStocks: this.supplierList1,
                totalStock: 0
                }
            ]
          };
          this.goodsParams.skuList.push(obj);
          console.log(this.goodsParams.skuList)
        } else {
            this.isNoSku = false;
        }
        // this.goodsParams.skuList[0].skuChannels[0].supplierStocks = res.data;
      });
    },
    //获取供应商列表
    getSuppList() {
      this.api.supplier.list({ limit: 99999 }).then(res => {
        res.data.dataList.forEach(item => {
          if (item.status) {
            this.supplierList.push(
              Object.assign(item, {
                channelPrice: "",
                approvedChannelPrice: "",
                stock: "",
                supplierId: item.id,
                newAdd: true
              })
            );
          }
        });
      });
    },
    //获取子类目列表
    getCate(level, id) {
      let pId = id ? id : 0;
      this.api.category.categoryNode({ parentId: pId }).then(res => {
        if (level === 0) {
          this.firstCateList = res.data;
          this.secondCateList = [];
          this.thirdCateList = [];
        } else if (level === 1) {
          this.secondCateList = res.data;
          this.thirdCateList = [];
        } else {
          this.thirdCateList = res.data;
        }
      });
    },
    removeSkuChannel(sku, item) {
      this.$msgbox({
        title: "",
        message: "确认删除该渠道？",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(action => {
        if (action === "confirm") {
          let potision = "";
          sku.skuChannels.forEach((i, index) => {
            if (i.channelType === item.channelType) {
              potision = index;
            }
          });
          if (potision) {
            sku.skuChannels.splice(potision, 1);
          }
        }
      });
    },
    uploadSuccess(res) {
      this.goodsImages.push({
        url: res.data,
        type: 0,
        showBtn: false
      });
    },
    uploadSKUSuccess(res) {
      this.$set(this.goodsParams.firstSpecImages, res.index, res.data);
    },
    removeSpecImg(index) {
      this.$set(this.goodsParams.firstSpecImages, index, "");
    },
    //选择类目
    selectCate(type, item) {
      if (type === 1) {
        if (this.selectedCate.first !== item.id) {
          this.getCate(1, item.id);
          this.selectedCate.first = item.id;
          this.selectedCate.second = "";
          this.selectedCate.third = "";
        }
      } else if (type === 2) {
        if (this.selectedCate.second !== item.id) {
          this.getCate(2, item.id);
          this.selectedCate.second = item.id;
          this.selectedCate.third = "";
        }
      } else {
        this.selectedCate.third = item.id;
      }
    },
    //失去焦点时处理规格明细
    maxSkuList(item, index, create,diffValue) {
      if (!item.skuName) {
        return;
      }
      //比较规格值是否改变
      if(this.guivalue!==diffValue){
        this.newguivalue = diffValue;
        let targetList = [];
        item.skuValues.forEach(item => {
          if (targetList.indexOf(item.name) === -1 && item.name != "") {
            targetList.push(item.name);
          }
        });
        this.$set(this.goodsParams.skuNameValues, index, {
          skuName: item.skuName,
          skuValues: targetList
        });
        //新增的规格值
        // let arr=[];
        // this.goodsParams.skuNameValues.forEach((item,index1)=>{
        //   if(index!==index1){
        //     item.forEach((item1)=>{
        //       this.goodsParams.skuList.push(obj);
        //     })
        //   }


        // })
        // return;
        if (create) {
          this.createSKU();
        }
      }


    },
    //处理规格明细
    createSKU() {
      let list = []; // 计算若干个规格相叠加
      this.goodsParams.skuNameValues.forEach(item => {
        let arr = [];
        item.skuValues.forEach(skuVal => {
          arr.push(skuVal.name || skuVal);
        });
        list.push(arr || "");
      });
      let tableArr = [];
      let arr = [];
      this.doExchange(list).forEach((val) => {
        let skuValues = []; // [{skuValue:val,skuName:''}]
        let relVal = val.name ? val.name : val;
        let values = relVal.split("*");
        this.goodsParams.skuNameValues.forEach((sku, index) => {
          values.forEach(valItem => {
            if (sku.skuValues.includes(valItem)) {
              let valObj = {
                skuName: sku.skuName,
                skuValue: valItem
              };
              if (
                index == 0 &&
                this.goodsParams.firstSpecImages[sku.skuValues.indexOf(valItem)]
              ) {
                valObj.imageUrl = this.goodsParams.firstSpecImages[
                  sku.skuValues.indexOf(valItem)
                ];
              }
              skuValues.push(valObj);
            }
          });
        });
        // let supplierList = JSON.parse(JSON.stringify(this.supplierList));
        let obj = {
          valueName: relVal,
          skuValues: skuValues,
          upc: "",
          weight: "",
          transportWays: this.channelList,
          quantity: val.quantity ? val.quantity : 1,
          skuChannels: [
            {
              addSku: true,
              channelType: "",
              expressFeeTemplateId: "",
              price: "",
              enterprisePrice: "",
              originalPrice: "",
              totalStock: 0,
              refPrice: { weiniPrice: "" },
              supplierStocks: this.supplierList1
            }
          ]
        };
        // let i = 0;
        this.goodsParams.skuList.forEach((data) => {
          if(data.valueName.indexOf(this.newguivalue)!=-1){
            // arr.push(data);
            // obj = {...data};
          }
          if (data.valueName === relVal&&data.valueName.indexOf(this.newguivalue)!=-1) {
            arr.push(data);
            obj = {...data};
            // obj = data;

            // tableArr.push(data);
          } else {
            // obj = data;
            // arr.push(index);
            // this.goodsParams.skuList.splice(index, 1);
          }
          // tableArr.push(obj);
        });
        tableArr.push(obj);
      });
      // this.goodsParams.skuList = arr;
      this.goodsParams.skuList = [...tableArr];
      if(this.goodsParams.skuList.length<arr.length&&this.guivalue===''){
        this.goodsParams.skuList = arr;
      }

    },
    //数组拿到重复值
    arrGetRe(arr) {
      var b = [],
        c = [];
      for (var i = 0; i < arr.length; i++) {
        if (c.indexOf(arr[i]) === -1) {
          c.push(arr[i]);
        } else {
          b.push(arr[i]);
        }
      }
      return b;
    },
    doExchange(arr) {
      let len = arr.length;
      // 当数组大于等于2个的时候
      if (len >= 2) {
        // 第一个数组的长度
        let len1 = arr[0].length;
        // 第二个数组的长度
        let len2 = arr[1].length;
        // 2个数组产生的组合数
        let lenBoth = len1 * len2;
        //  申明一个新数组,做数据暂存
        let items = new Array(lenBoth);
        // 申明新数组的索引
        let index = 0;
        // 2层嵌套循环,将组合放到新数组中
        for (let i = 0; i < len1; i++) {
          for (let j = 0; j < len2; j++) {
            items[index] = arr[0][i] + "*" + arr[1][j];
            index++;
          }
        }
        // 将新组合的数组并到原数组中
        let newArr = new Array(len - 1);
        for (let i = 2; i < arr.length; i++) {
          newArr[i - 1] = arr[i];
        }
        newArr[0] = items;
        // 执行回调
        return this.doExchange(newArr);
      } else if (len == 1) {
        return arr[0];
      } else {
        return arr;
      }
    },
    addSkuValue(item) {
      item.skuValues.push({ name: "" });
    },
    selectSKU(item) {
      if (this.selectedSKUName === item.displayName) {
        this.selectedSKUName = "";
      } else {
        this.selectedSKUName = item.displayName;
      }
    },
    chooseBrand() {
      this.searchBrandList.forEach(item => {
        if (item.name === this.goodsParams.brandName) {
          this.goodsParams.brandId = item.id;
        }
      });
    },
    chooseShop() {
      this.searchShopList.forEach(item => {
        if (item.name === this.goodsParams.shopName) {
          this.goodsParams.shopId = item.id;
        }
      });
    },
    //删除多件装
    delRow(index) {
      this.goodsParams.skuList.splice(index, 1);
    },
    //添加多件装
    addRow(index,item) {
        let obj = this.copy(this.goodsParams.skuList[index]);
        let item_new=this.copy(item);
        if(this.goodsParams.skuList[index].skuChannels.length>0){
            obj.skuChannels=[item_new];
        }

      obj.isMultiple = true;
      obj.quantity = 2;
      obj.weight = this.goodsParams.skuList[index].weight * 2;
      // obj.show = false;
      obj.skuChannels[0].show = false;
      obj.skuChannels[0].supplierStocks = this.supplierList1;
      obj.id = "";
      obj.groupSkuValue = this.goodsParams.skuList[index].displaySkuValue;
      obj.quantity=2;
      obj.skuValues=this.goodsParams.skuList[index].skuValues;
      this.goodsParams.skuList.splice(index + 1, 0, obj);
    },
    //添加规格名
    addSkuRow(skuList) {
          console.log(skuList)

      if(skuList.length>0&&skuList[0].skuChannels.length>0&&skuList[0].skuChannels[0].supplierStocks.length>0&&skuList[0].skuChannels[0].supplierStocks[0].thirdAttr){
        this.$message.info('因当前商品是接口商品，暂无法直接添加规格，若要添加请前往上级供货端进行修改或添加');
        return false
      }
      let item = {
        skuName: "",
        skuValues: [{ name: "" }]
      };
      this.setSkuNameValues.push(item);
        console.log(this.setSkuNameValues)

    },
    //规格值删除
    removeSkuValue(item, specIndex) {
      item.skuValues.splice(specIndex, 1);
      let removeSkuFlag = false;
      this.goodsParams.skuNameValues.forEach(val => {
        if (val.skuName == item.skuName) {
          let targetList = [];
          item.skuValues.forEach(item => {
            if (targetList.indexOf(item.name) === -1 && item.name != "") {
              targetList.push(item.name);
            }
          });
          val.skuValues = targetList;
        }
        if (!val.skuValues.length) {
          removeSkuFlag = true;
        }
      });
      if (removeSkuFlag) {
        this.goodsParams.skuNameValues.splice(specIndex + 1, 1);
      }
      this.createSKU();
    },
    //数组判断是否重复
    unARR(val, arr) {
      let boolean = false;
      arr.forEach(data => {
        if (val.indexOf(data) != -1) {
          boolean = true;
        }
      });
      return boolean;
    },
    //删除规格名
    removeSkuRow(index) {

      this.setSkuNameValues.splice(index, 1);
      this.goodsParams.skuNameValues.splice(index, 1);
      this.createSKU();
    },
    //修改规格名
    modifySkuName(val,index) {

      this.goodsParams.skuNameValues[index].skuName = val
      let {goodsParams: {skuList}} = this
      if (skuList && skuList.length) {
        skuList.forEach(r => {
            if (r.skuValues && r.skuValues.length) {
              r.skuValues.forEach((rr, i) => {
                  if (i == index) {
                    rr.skuName = val
                  }
              })
            }
        })
        this.$set(this.goodsParams, 'skuList', skuList)
      }
      // this.createSKU();
    },
    removeImg(item, index) {
      if (this.mainImgUrl === item.url) {
        this.mainImgUrl = "";
      }
      if (this.id && item.id) {
        this.goodsParams.delImageIds.push(item.id);
      }
      this.goodsImages.splice(index, 1);
    },
    openMixModal() {
      if (this.goodsParams.skuNameValues.length === 0) {
        this.$message.warning({
          message: "请添加至少一个规格",
          showClose: true
        });
        return;
      }
      this.modalSkuValues = [];
      this.modalSkuValues = this.copy(this.goodsParams.skuNameValues);
      this.modalSkuValues.forEach(item => {
        this.$set(item, "currentSpec", {
          skuName: "",
          skuValue: ""
        });
        this.$set(item, "activeSpec", "");
      });
      this.mixedList = [];
      this.modalParams = {};
      this.addShow = true;
    },
    selectMixItem(item, val) {
      item.currentSpec = {
        skuName: item.skuName,
        skuValue: val
      };
      item.activeSpec = val;
    },
    batchSetWeight() {
      if (this.goodsParams.skuList.length === 0) {
        this.$message.info({ message: "请先新增一个规格", showClose: true });
      } else if (!this.batchWeight) {
        this.$message.info({
          message: "请输入批量更改的重量",
          showClose: true
        });
      } else {
        this.goodsParams.skuList.forEach(item => {
          item.weight = this.batchWeight;
        });
      }
    },
    appendMix() {
      let targetValues = [];
      let mixName = "";
      this.modalSkuValues.forEach(item => {
        targetValues.push(item.currentSpec);
        mixName += item.activeSpec + "+";
      });
      mixName = mixName.substring(0, mixName.length - 1);
      let item = {
        mixedName: mixName,
        skuValues: targetValues,
        quantity: 1
      };
      this.mixedList.push(item);
    },
    setMainImg(item) {
      this.goodsImages.forEach(img => {
        img.type = 0;
      });
      item.type = 1;
      this.mainImgUrl = item.url;
    },
    setQuantity(type, item) {
      if (type === 1) {
        item.quantity += 1;
      } else {
        if (item.quantity === 1) {
          this.$message.warning({ message: "数量不能少于1", showClose: true });
          return;
        } else {
          item.quantity -= 1;
        }
      }
    },
    dialogShop(res) {
      this.goodsParams.shopId = res.shopList[0].id;
      this.goodsParams.shopName = res.shopList[0].name;
      this.showShopModal = false;
    },
    dialogShopCancel() {
      this.showShopModal = false;
    },
    dialogBrand(res) {
      this.goodsParams.brandId = res.brandList[0].id;
      this.goodsParams.brandName = res.brandList[0].name;
      this.showBrandModal = false;
    },
    dialogBrandCancel() {
      this.showBrandModal = false;
    },
    //获取详情
    getDetail() {
      this.detailLoaded = true;
      let fromPage = 0;
      if (this.status == 1 || this.status == 2) {
        fromPage = 0;
      } else {
        fromPage = 1;
      }

      this.api.goods.detail({ id: this.id, fromPage: fromPage }).then(res => {
        this.detailData = res.data;
        let detail = res.data;
        this.goodsParams.name = detail.name;
        this.goodsParams.gift = detail.gift;
        this.goodsParams.presell = detail.presell;
        this.goodsParams.brandId = detail.brandId;
        this.goodsParams.brandName = detail.brandName;
        this.goodsParams.shopId = detail.shopId;
        this.goodsParams.shopName = detail.shopName;
        this.goodsParams.desc = detail.desc;
        this.descLength = detail.desc.length;
        this.goodsParams.modelNo = detail.modelNo;
        // this.goodsParams.info = detail.info;
        this.goodsParams.status = detail.status;
       // let str = 'detail.richDesc'
       // if (detail.richDesc) {
       //   if (!/<img style="/ig.test(detail.richDesc)) {
       //      str = detail.richDesc.replace(/<img/ig, '<img style="display:block;"')
       //   }
       // }
       // this.goodsParams.richDesc = str
        this.goodsParams.richDesc = detail.richDesc;
        this.goodsParams.currency = detail.currency;
        this.goodsParams.unit = detail.unit ? detail.unit : "件";
        this.goodsParams.unitCanChange = detail.unitCanChange;
        this.goodsParams.firstSpecImages = detail.skuNameValues[0];
        this.goodsParams.skuNameValues = detail.skuNameValues;
        this.goodsParams.nameLabels = detail.nameLabels.length>0?detail.nameLabels[0]:"";
        this.goodsParams.id = this.id;
        detail.skuList.forEach(val => {
//组合规格值处理
          let name = "";
          if (val.quantity>1) {
            if(val.skuValues.length>0){
              val.skuValues.forEach((v,i)=>{
                if(i>0){
                  name+="*"+v.skuValue;
                }else{
                  name+=v.skuValue;
                }
              })
            }
            val.valueName=name;
            // val.quantity = val.skuGroup[0].quantity;
            val.isMultiple = true;
            // console.log(val)
            // val.skuGroup[0].skuValues.forEach(value => {
            //   if (value.skuValue) {
            //     name += `${value.skuValue}*`;
            //   }
            // });

            // val.valueName = '多件装';
            // console.log(val.valueName)
          } else {
            // val.valueName+='-';
            val.quantity = 1;
            val.isMultiple = false;
            val.skuValues.forEach(value => {
            if (value.skuValue) {
              name += `${value.skuValue}*`;
            }
            });
            val.valueName = name.substring(0, name.length - 1);
          }
        });
        this.goodsParams.skuList = detail.skuList;
        //每个规格添加发货方式数组
        this.goodsParams.skuList.forEach(val => {
          let skuChannels = [];
          val.skuChannels.forEach(item => {
            item.totalStock = item.totalStock || '0'
            skuChannels.push(item);
          });
          val.transportWays = skuChannels;
        });
        this.setSkuNameValues = [];
        // 获取类目信息并展示
        // setTimeout(() => {
        //     this.categoryTree.forEach(first => {
        //         if(first.id === detail.cateId1) {
        //             this.selectedCate.first = detail.cateId1;
        //             this.secondCateList = first.children || [];
        //             if(this.secondCateList && this.secondCateList.length>0) {
        //                 this.secondCateList.forEach(second => {
        //                     if(second.id === detail.cateId2) {
        //                         this.selectedCate.second = second.id;
        //                         this.thirdCateList = second.children;
        //                         this.thirdCateList.forEach(third => {
        //                             if(third.id === detail.cateId3) {
        //                                 this.selectedCate.third = third.id;
        //                             }
        //                         });
        //                     }
        //                 });
        //             }
        //         }
        //     });
        // });
        this.categoryTree.forEach(first => {
          if (first.id === detail.cateId1) {
            this.selectedCate.first = detail.cateId1;
            this.secondCateList = first.children || [];
            if (this.secondCateList && this.secondCateList.length > 0) {
              this.secondCateList.forEach(second => {
                if (second.id === detail.cateId2) {
                  this.selectedCate.second = second.id;
                  this.thirdCateList = second.children;
                  this.thirdCateList.forEach(third => {
                    if (third.id === detail.cateId3) {
                      this.selectedCate.third = third.id;
                    }
                  });
                }
              });
            }
          }
        });
        if (detail.imageList) {
          detail.imageList.forEach(item => {
            item.showBtn = false;
            if (item.type === 1) {
              this.mainImgUrl = item.url;
            }
          });
        } else {
          this.mainImgUrl = "";
        }

        this.goodsImages = detail.imageList || [];
        detail.skuNameValues.forEach(item => {
          let skuValues = [];
          if (item.skuValues && item.skuValues.length) {
            item.skuValues.forEach(value => {
              skuValues.push({
                name: value
              });
            });
          }
          this.setSkuNameValues.push({
            skuName: item.skuName,
            skuValues: skuValues
          });
        });
        this.goodsParams.firstSpecImages = [];
        if (
          detail.skuNameValues && detail.skuNameValues.length>1 &&
          detail.skuNameValues[0].skuValueImages &&
          detail.skuNameValues[0].skuValues
        ) {
          this.goodsParams.firstSpecImages.length =
            detail.skuNameValues[0].skuValues.length;
          // if(detail.skuNameValues[0].skuValueImages && detail.skuNameValues[0].skuValues) {
          detail.skuNameValues[0].skuValues.forEach((name, index) => {
            detail.skuNameValues[0].skuValueImages.forEach(item => {
              if (name == item.skuValue) {
                this.goodsParams.firstSpecImages[index] = item.imageUrl;
              }
            });
          });
        }
      });
    },
    saveGoods() {
      this.goodsParams.categoryId =
        this.selectedCate.third ||
        this.selectedCate.second ||
        this.selectedCate.first ||
        "";
      let params = this.copy(this.goodsParams);
      params.skuList.forEach(item => {
        item.skuChannels.forEach(sku => {
          sku.supplierStocks.forEach(sup => {
            delete sup.supplierName;
            delete sup.expressFeeTemplates;
          });
        });
  // 保存时的多件装处理
        if (item.skuGroup) {
          let str = "";
          if (params.skuNameValues.length) {
            item.skuGroup[0].skuValues.forEach((itm) => {
              if (itm.skuName) {
                str += itm.skuName + ":" + itm.skuValue + ",";
              } else {
                str += ",";
              }
            });
            str = str.substring(0, str.length - 1);
          }

          item.groupSkuValue = str + "*" + item.skuGroup[0].quantity;
        }
      });
      let flag = true;
      params.skuList.forEach(val => {
        if (val.valueName) {
          val.skuChannels.forEach(() => {
          });
        }
      });
      if (!flag) {
        return;
      }
      let firstSpecImages = [];
      params.firstSpecImages.forEach((item, index) => {
        firstSpecImages.push({
          skuValue: params.skuNameValues[0].skuValues[index],
          imageUrl: item || ""
        });
      });
      if(params.skuNameValues.length) {
        if(params.skuNameValues[0].skuName){
          params.skuNameValues[0].skuValueImages = [];
          firstSpecImages.forEach(item => {
              if (item.skuValue) {
              params.skuNameValues[0].skuValueImages.push(item);
              }
          });
        } else {
          //无规格时传空数组
          params.skuNameValues = [];
          params.skuList.forEach((item)=>{
            if(item.groupSkuValue){
              item.skuGroup[0].skuValues = [];
            } else {
              item.skuValues = [];
            }
          })
        }
      }else {
        //无规格时传空数组
        params.skuNameValues = [];
        params.skuList.forEach((item)=>{
          if(item.groupSkuValue){
            item.skuGroup[0].skuValues = [];
          } else {
            item.skuValues = [];
          }
        })
      }


      delete params.firstSpecImages;
      if (this.status == 1 || this.status == 2) {
        params.fromPage = 0;
      } else {
        params.fromPage = 1;
      }
      let imglist = JSON.parse(JSON.stringify(this.goodsImages))
      let count = 1
      imglist = imglist.map(r => {
        if (r.type ==1) {
            r.sort = 0
        } else {
          r.sort = count
          count ++
        }
        return r
      }) || []
      params.addImages = JSON.stringify(imglist);
      params.skuNameValues = JSON.stringify(params.skuNameValues);
      let supplierFlag = false;
      params.skuList.forEach(skuVal => {
        skuVal.skuChannels.forEach(suppliers => {
          suppliers.supplierStocks.forEach(supplier => {
            if (supplier.approvedStatus == 0) {
              supplierFlag = true;
              // this.$message.success({message:res.message, showClose:true});
            }
          });
        });
      });
      //销售状态关闭时未填库存的统一变成-1
      params.skuList.forEach((item)=>{
        item.skuChannels.forEach((item1)=>{
          item1.supplierStocks.forEach((item2)=>{
            if(item2.status==1){
              if(!item2.stock) {
                item2.stock = -1;
              }
            }
          })
        })
      })

      //搞清楚这个东东是做什么的
      if (supplierFlag) {
        this.$msgbox({
          type: "warning",
          title: "友情提示",
          message:
            "您当前存在审核不通过的商品规格或供应商，是否继续保存？（保存后商品将不含有审核不通过的规格数据）",
          showClose: true,
          showCancelButton: true,
          cancelButtonText: "关闭",
          confirmButtonText: "我已知晓，继续保存",
          center: true,
          beforeClose: (action, instance, done) => {
            if (action === "confirm") {
              supplierFlag = false;
              done();
              params.skuList.forEach(skuVal => {
                skuVal.skuChannels.forEach(suppliers => {
                  suppliers.supplierStocks.forEach(supplier => {
                    if (supplier.approvedStatus == 0) {
                      supplier.approvedStatus = 2;
                      // this.$message.success({message:res.message, showClose:true});
                    }
                  });
                });
              });
              params.skuList = JSON.stringify(params.skuList);
              let formData = {};
              for (let index in params) {
                if (params[index] !== "") {
                  formData[index] = params[index];
                }
              }
              if (this.id && this.id != 1) {
                formData.id = this.id;
                if (params.delImageIds.length) {
                  formData.delImageIds = JSON.stringify(params.delImageIds);
                }
                this.api.goods.update(formData).then(res => {
                  this.$message.success({
                    message: res.message,
                    showClose: true
                  });
                  this.$router.push({ name: "商品管理" });
                });
              } else {
                this.api.goods.add(formData).then(res => {
                  this.$message.success({
                    message: res.message,
                    showClose: true
                  });
                  this.$router.push({ name: "商品管理" });
                });
              }
            } else if (action === "cancel") {
              supplierFlag = true;
              done();
            }
          }
        });
      } else {

        //规格列表加图片，用于详情的修改
        var skuNameValues=JSON.parse(JSON.stringify(this.setSkuNameValues));
        var imgArr=[];
        if(skuNameValues && skuNameValues.length>0){
          //
          this.goodsParams.firstSpecImages.forEach((v,i)=>{
            if(v){
              imgArr.push({
                skuValue:this.setSkuNameValues[0].skuValues[i].name,
                imageUrl:v,
              })
            }
            //
          })
          skuNameValues[0].skuValueImages=imgArr;
        }


        skuNameValues.forEach((v,i)=>{
          var arr=[];
          v.skuValues.forEach((v1)=>{
            arr.push(v1.name);
          })
          skuNameValues[i].skuValues=arr;
        })
        // console.log(skuNameValues);
        // return;
        params.skuNameValues=JSON.stringify(skuNameValues)
        // console.log(params.skuList);
        params.skuList = JSON.stringify(params.skuList);
        let formData = {};
        for (let index in params) {
          if (params[index] !== "") {
            formData[index] = params[index];
          }
        }
        // console.log("是否是编辑：")
        // console.log(this.id)
        // console.log(formData);
        // return;
        if (this.id&&this.id!=1) {
          formData.id = this.id;
          if (params.delImageIds.length) {
            formData.delImageIds = JSON.stringify(params.delImageIds);
          }
          this.api.goods.update(formData).then(res => {
            this.$message.success({ message: res.message, showClose: true });
            this.$router.push({ name: "商品管理" });
          });
        } else {
          this.api.goods.add(formData).then(res => {
            this.$message.success({ message: res.message, showClose: true });
            this.$router.push({ name: "商品管理" });
          });
        }
      }

    },
    //多件装数量限制
    handleNum(val) {
      if (val.quantity > 100) {
        val.quantity = 100;
        // val.weight = val.skuGroup[0].quantity*this.goo
        this.$message.warning({
          message: "多件装的数量不能大于100",
          showClose: true
        });
      }
      if(this.weightValue!==val.quantity) {
        val.weight = this.weightValue*val.quantity;
      }
    },
    //多件装数量计算重量
    diffNum(weight,num) {
      this.weightValue = weight/num;
    },
    //供货商设置按钮
    btnSetSupplier(skuVal, index,skuValIndex) {
      // console.log(this.goodsParams.skuList[index].skuChannels)
      // console.log(this.goodsParams.skuList[index].skuChannels[skuValIndex])
      // console.log(this.goodsParams.supplierStocks)
      this.selectedSKUName = false;
      //供货商选中效果
      this.goodsParams.skuList.forEach((item)=>{
        item.skuChannels.forEach((item1)=>{
          item1.show = false;
        })
      })
      this.goodsParams.skuList[index].skuChannels[skuValIndex].show = true;

      let arr = [];
      skuVal.supplierStocks.forEach(item => {
        let bool, isLock;
        if (item.profitRate) {
          if (Number(item.profitRate.split("%")[0]) < 2.2) {
            bool = true; //供应商对应的利润率小于2.2%时，利润率对应的数值为红色
          } else {
            bool = false;
          }
        }
        if (item.approvedStatus) {
          if (item.profitRate === 0) {
            isLock = false; //供应商对应的利润率小于2.2%时，利润率对应的数值为红色
          } else {
            isLock = true;
          }
        }
        // console.log(item);

        let obj = {
          approvedChannelPrice: item.approvedChannelPrice,
          approvedStatus: item.approvedStatus ? item.approvedStatus : 0,
          channelPrice: item.channelPrice,
          offlineType: item.offlineType,
          profitRate: item.profitRate,
          status: item.status,
          stock: item.stock,
          supplierId: item.supplierId,
          supplierName: item.supplierName,
          thirdAttr: item.thirdAttr ? item.thirdAttr : [],
          isRed: bool,
          isLock: isLock,
          changes:item.changes
        };
        arr.push(obj);
      });

      skuVal.supplierStocks = arr;
      this.goodsParams.supplierStocks = skuVal.supplierStocks;
      this.itemDetail = skuVal;
      this.selectedSKUName = true;

    },
    btnSelectSKU(item) {
      this.selectedSKUName = "";
      this.selectedChannelList = [];
      this.selectedSKUItem = item;
      if (item.skuChannels && item.skuChannels.length > 0) {
        item.skuChannels.forEach(channel => {
          this.selectedChannelList.push(channel);
        });
      }
      this.showChannelModal = true;
    },
    btnSetPrice(item) {
      if (this.selectedSKUName === item.displayName) {
        this.selectedSKUName = "";
      } else {
        this.selectedSKUName = item.displayName;
      }

      if (item.skuChannels && item.skuChannels.length > 0) {
        return;
      }
      this.$set(item, "skuChannels", this.copy(this.channelList));
    },
    removeChannel(index) {
      this.selectedChannelList.splice(index, 1);
    },
    selectChannel(item) {
      if (this.selectedChannelList.length > 0) {
        let exist = false;
        this.selectedChannelList.forEach(channel => {
          if (channel.channelType === item.channelType) {
            exist = true;
          }
        });
        if (!exist) {
          this.selectedChannelList.push(item);
        }
      } else {
        this.selectedChannelList.push(item);
      }
    },
    saveChannelModal() {
      this.selectedSKUItem.skuChannels = this.copy(this.selectedChannelList);
      this.selectedSKUName = this.copy(this.selectedSKUItem.displayName);
      this.showChannelModal = false;
    },
    saveModal() {
      let displayName = "［" + this.modalParams.groupSkuValue + "］";
      this.mixedList.forEach(item => {
        displayName += "(" + item.mixedName + ")*" + item.quantity + "+";
      });
      displayName = displayName.substring(0, displayName.length - 1);
      let mixItem = {
        displayName: displayName,
        originalPrice: this.modalParams.originalPrice,
        groupSkuValue: this.modalParams.groupSkuValue,
        weight: this.modalParams.weight,
        skuGroup: this.mixedList,
        skuChannels: this.copy(this.channelList)
      };
      this.goodsParams.skuList.push(mixItem);
      this.addShow = false;
    },
    remoteMethod(query) {
      this.api.brand.list({ name: query }).then(res => {
        this.searchBrandList = res.data.dataList;
      });
    },
    remoteShopMethod(query) {
      this.api.store.allShop({ name: query }).then(res => {
        this.searchShopList = res.data;
      });
    },
    sameGroupTableVisible() {
      this.sameGroupTableShow = !this.sameGroupTableShow;
      if (this.groupData.groupNum) {
        this.activeName = "first";
        this.labelName.firstLabel = `管理同组商品(${this.groupData.groupNum})`;
      } else {
        this.activeName = "second";
      }
    },
    groupNum() {
      let groupIds = [];
      let itemIds = [];
      // this.adminGoodsData.groupId ? groupIds.push(this.adminGoodsData.groupId) : groupIds = [];
      // this.adminGoodsData.id ? itemIds.push(this.adminGoodsData.id) : itemIds = [];
      this.adminGoodsData.groupId
        ? groupIds.push(this.adminGoodsData.groupId)
        : itemIds.push(this.adminGoodsData.id);
      this.api.goods
        .goodsGroupNum({
          groupIds: JSON.stringify(groupIds),
          itemIds: JSON.stringify(itemIds)
        })
        .then(res => {
          this.groupData.groupNum = res.data;
          if (this.groupData.groupNum) {
            this.labelName.firstLabel = `管理同组商品(${this.groupData.groupNum})`;
          }
        });
    },
    //限制300字符
    limit300() {
      this.descLength = this.goodsParams.desc.length;
      if (this.descLength > 300) {
        this.$message.warning({
          message: "宝贝描述应不多于300字符",
          showClose: true
        });
        this.goodsParams.desc = this.goodsParams.desc.substring(0, 300);
      }
    },
    goDetail(item) {
      let path = `/center/goods/commodity/detail/${item.id}/1`;
      window.open(path, "_blank");
    },
    groupDete() {
      let itemIds = [];
      this.sameGroupTableData.forEach(val => {
        if (val.checked) {
          itemIds.push(val.id);
        }
      });
      if (itemIds.length) {
        if (this.avtiveOk) {
          this.avtiveOk = false;
        } else {
          this.avtiveOk = true;
        }
        this.api.goods
          .removeGoodsFromGroup({ itemIds: JSON.stringify(itemIds) })
          .then(() => {
            this.$message.success({ message: "更新成功", showClose: true });
            this.getGoodsList();
          });
      } else {
        this.$message.warning({ message: "请选择商品", showClose: true });
      }
    },
    groupAdd() {
      let groupIds = [];
      let itemIds = [];
      this.sameGroupTableData.forEach(val => {
        if (val.checked) {
          val.groupId ? groupIds.push(val.groupId) : itemIds.push(val.id);
        }
      });
      if (groupIds.length || itemIds.length) {
        if (this.avtiveOk) {
          this.avtiveOk = false;
        } else {
          this.avtiveOk = true;
        }
        this.api.goods
          .mergeGoodsGroup({
            itemIds: JSON.stringify(itemIds),
            groupIds: JSON.stringify(groupIds)
          })
          .then(() => {
            this.$message.success({ message: "更新成功", showClose: true });
            this.getGoodsList();
          });
      } else {
        this.$message.warning({ message: "请选择商品", showClose: true });
      }
    },
    //同组商品
    getGoodsList() {
      this.api.goods
        .groupList(this.listParams)
        .then(res => {
          res.data.dataList.forEach(val => {
            val.checked = false;
          });
          this.sameGroupTableData = res.data.dataList;
          this.total = res.data.total;
          this.checkAll = false;
        })
        .catch(() => {
          this.total = 0;
          this.checkAll = false;
          this.sameGroupTableData = [];
        });
      this.groupNum();
    },
    selectAll() {
      this.checkAll = !this.checkAll;
      this.sameGroupTableData.forEach(item => {
        if (this.checkAll) {
          item.checked = true;
        } else {
          item.checked = false;
        }
      });
    },
    selectItem(item) {
      item.checked ? (item.checked = false) : (item.checked = true);
      if (!item.checked) {
        this.checkAll = false;
      }
    },
    deteItem(item) {
      this.api.goods
        .removeGoodsFromGroup({ itemIds: JSON.stringify([item.id]) })
        .then(() => {
          this.$message.success({ message: "剔除成功", showClose: true });
          this.getGoodsList();
        });
    },
    //单个添加
    addItem(item) {
      let itemIds = [];
      let groupIds = [];
      if (this.adminGoodsData.groupId) {
        groupIds.push(this.adminGoodsData.groupId);
      } else {
        itemIds.push(this.adminGoodsData.id);
      }
      item.groupId ? groupIds.push(item.groupId) : itemIds.push(item.id);
      this.api.goods
        .mergeGoodsGroup({
          itemIds: JSON.stringify(itemIds),
          groupIds: JSON.stringify(groupIds)
        })
        .then(res => {
          this.$message.success({ message: "添加成功", showClose: true });
          this.getGoodsList();
          this.adminGoodsData.groupId = res.data ? res.data : 0;
        });
    },
    //tab事件
    handleClick(tab) {
      this.listParams.keyword ? delete this.listParams.keyword : "";
      this.listParams.page = 1;
      // if(tab.name == 'first'){
      //     if(this.adminGoodsData.groupId){
      //         this.listParams.groupId = this.adminGoodsData.groupId
      //     }else{
      //         this.listParams.groupId ? delete this.listParams.groupId : ''
      //     }
      //     if(this.adminGoodsData.id){
      //         this.listParams.itemId = this.adminGoodsData.id
      //     }else{
      //         this.listParams.itemId ? delete this.listParams.itemId : ''
      //     }
      //     // this.listParams.groupId = this.adminGoodsData.groupId
      //     // this.listParams.itemId = this.adminGoodsData.id
      //     delete this.listParams.groupId;
      //     delete this.listParams.exceptItemId;
      //     this.listParams.exceptGroupId ? delete  this.listParams.exceptGroupId : ''
      // } else {

      //     if(this.adminGoodsData.groupId){
      //         this.listParams.exceptGroupId = this.adminGoodsData.groupId
      //     }else{
      //         this.listParams.exceptGroupId ? delete this.listParams.exceptGroupId : ''
      //     }
      //     if(this.adminGoodsData.itemId){
      //         this.listParams.exceptItemId = this.adminGoodsData.itemId
      //     }else{
      //         this.listParams.exceptItemId ? delete this.listParams.exceptItemId : ''
      //     }
      //     this.listParams.itemId ? delete this.listParams.itemId : ''
      // }
      if (tab.name == "first") {
        this.listParams.groupId = this.adminGoodsData.groupId;
        this.listParams.itemId = this.adminGoodsData.id;
        this.listParams.exceptGroupId
          ? delete this.listParams.exceptGroupId
          : "";
        this.listParams.page = 1;
        delete this.listParams.exceptItemId;
      } else {
        delete this.listParams.groupId;
        this.listParams.exceptGroupId = this.adminGoodsData.groupId;
        this.listParams.exceptItemId = this.adminGoodsData.id;
        this.listParams.itemId ? delete this.listParams.itemId : "";
        this.listParams.page = 1;
      }
      this.getGoodsList();
    },
    search() {
      this.listParams = {
        isGift: -1,
        page: 1,
        limit: 10,
        statusList: JSON.stringify(["1", "2"]),
        keyword: this.input
      };
      this.getGoodsList();
    }
  },
  watch: {
    "goodsParams.currency"() {
      this.currencyList.forEach(val => {
        if (val.code == this.goodsParams.currency) {
          this.currencySymbol = val.symbol;
        }
      });
    },
  },
  components: {
    editor: editor,
    selectShop: selectShop,
    SelectBrand: selectBrand,
    Pagination: Pagination,
    noData: noData,
    draggable:draggable,
  }
};
</script>
<style lang='scss' scoped>
.goods-detail {
  // padding-right: 260px;
  padding-bottom: 50px;
  position: relative;
  .operateBox {
    display: block !important;
  }
  .form-control-error {
    cursor: not-allowed;
    background: #e5e5e5;
  }
  .api-input-control {
    input:disabled {
      cursor: not-allowed;
      background: rgb(235, 235, 228);
    }
  }
  .save-box{
      position: fixed;
      top: 55px;
      right: 15px;
      // width:245px;
      .title{
          font-size: 14px;
          font-weight: bold;
          color: #333333;
          padding: 10px 0;
      }
      .save-inner{
          background: #fff;
          padding: 20px 30px;
          border-radius: 4px;
          .btn-main{
              margin-right: 20px;
          }
      }
  }
  .big-title {
    color: #333333;
    font-size: 14px;
    font-weight: bold;
    padding-bottom: 10px;
  }
  .shuTiao {
    position: relative;
    padding-left: 8px;
    &::before {
      content: "";
      position: absolute;
      width: 3px;
      height: 13px;
      background: $org-color;
      left: 0;
      top: 0;
    }
  }
  .type-wrap {
    margin-bottom: 20px;
    padding: 10px 0 10px 20px;
    background: $white-color;
    box-shadow: 0 2px 3px $border-color;
    border-radius: 4px;
    .mb15 {
      margin-bottom: 15px;
    }
    .type-content-wrap {
      display: flex;
      &:last-child {
        margin-bottom: 0;
        padding-bottom: 0;
        border-bottom: 0;
      }
      .type-title {
        width: 76px;
        font-weight: 600;
        line-height: 30px;
      }
      .type-content {
        display: flex;
        flex-wrap: wrap;
        align-content: center;
        flex: 1;
        .type-item {
          min-width: 80px;
          line-height: 30px;
          border-radius: 15px;
          margin-right: 10px;
          .ellipsis {
            padding-right: 30px;
            width: auto;
            max-width: 100%;
            position: relative;
            .item-count {
              position: absolute;
              /*right: 5px;*/
              top: 0;
            }
          }
          .pr40 {
            padding-right: 40px;
          }
          a {
            display: inline-block;
            vertical-align: top;
            color: #333;
            height: 28px;
            line-height: 28px;
            padding: 0 8px;
            border-radius: 28px;
            border: 1px solid #fff;
          }
        }
        .active {
          a {
            border: 1px solid rgba($color: $main-color, $alpha: 0.6);
            color: $main-color;
          }
          .a1 {
            border: 1px solid rgba($color: #f06941, $alpha: 0.6);
            color: #f06941;
          }
          .a2 {
            border: 1px solid rgba($color: #3a62e1, $alpha: 0.6);
            color: #3a62e1;
          }
        }
      }
    }
  }
  .img-wrap {
    margin-bottom: 20px;
    padding: 20px;
    background: $white-color;
    box-shadow: 0 2px 3px $border-color;
    border-radius: 4px;
    position: relative;
    .main-img {
      display: inline-block;
      vertical-align: top;
      padding-right: 30px;
      width: 130px;
      height: 130px;
      margin-right: 30px;
      position: relative;
      &::after {
        content: "";
        position: absolute;
        width: 1px;
        height: 100px;
        right: 0;
        top: 0;
        background: $border-color;
      }
      img {
        height: 100px;
        width: 100px;
      }
      p {
        width: 100px;
        text-align: center;
        color: #333333;
        font-size: 12px;
        padding-top: 13px;
        left: 0;
        bottom: -30px;
      }
      .main-img-null {
        line-height: 100px;
        text-align: center;
      }
    }
    .img-item {
      display: inline-block;
      vertical-align: top;
      width: 100px;
      height: 132px;
      margin-right: 20px;
      position: relative;
      &:hover {
        .set-main {
          display: inline-block;
        }
        .remove-img {
          display: block;
          position: absolute;
          width: 20px;
          height: 20px;
          border-radius: 20px;
          right: -10px;
          top: -10px;
          cursor: pointer;
          border: none;
        }
      }
      img {
        height: 100px;
        width: 100px;
      }
      .remove-img {
        display: none;
        position: absolute;
        width: 20px;
        height: 20px;
        border-radius: 20px;
        right: -10px;
        top: -10px;
        cursor: pointer;
        border: none;
      }
      .set-main {
        display: none;
        width: 100px;
        text-align: center;
        .btn-fill-org-main {
          display: inline-block;
          height: 22px;
          line-height: 22px;
          margin-top: 10px;
          cursor: pointer;
          position: relative;
          z-index: 100;
        }
      }
    }
    .img-item:hover {
      img {
        border: 1px solid #f06941;
      }
    }
    .addGoodsImg {
      display: inline-block;
      vertical-align: top;
      width: 100px;
      height: 100px;
      text-align: center;
      border: 1px dashed $border-color;
      position:relative;
      cursor: pointer;
      .upload-container {
        height: 100%;
        .image-uploader {
          height: 100%;
          .el-upload {
            width: 100%;
            height: 100%;
          }
        }
      }
      img {
        margin-top: 40px;
      }
      .sugg {
        position: absolute;
        left: 100px;
        top: 43px;
        width: 300px;
        height: 17px;
        font-size: 12px;
        font-family: PingFangSC;
        font-weight: 400;
        color: rgba(153, 153, 153, 1);
        line-height: 17px;
        filter:Alpha(opacity=50);
      }
    }
    .noMoreGoodsImg {
      display: inline-block;
      vertical-align: top;
      width: 100px;
      height: 100px;
      text-align: center;
      border: 1px dashed $border-color;
      background: #efefef;
      img {
        margin-top: 40px;
      }
    }
    .sugg {
      position: absolute;
      left: 382px;
      top: 126px;
      width: 300px;
      height: 17px;
      font-size: 12px;
      font-family: PingFangSC;
      font-weight: 400;
      color: rgba(153, 153, 153, 1);
      line-height: 17px;
      filter:Alpha(opacity=50);
      opacity: 0.5;
    }
  }
  .info-wrap {
    margin-bottom: 20px;
    padding: 20px;
    background: $white-color;
    box-shadow: 0 2px 3px $border-color;
    border-radius: 4px;
    padding-bottom: 0;
    .info-title {
      color: #333333;
      font-size: 14px;
      padding-left: 8px;
      font-weight: bold;
      padding-bottom: 15px;
      position: relative;
      &::before {
        content: "";
        position: absolute;
        width: 3px;
        height: 13px;
        background: $org-color;
        left: 0;
        top: 0;
      }
    }
    .info-item-box {
      display: flex;
    }
    .info-item {
      padding-bottom: 15px;
      position: relative;
      margin-right: 40px;

      .info-item-title {
        display: inline-block;
        vertical-align: top;
        width: 85px;
        text-align: right;
        color: #333333;
        font-size: 14px;
        padding-right: 10px;
        line-height: 30px;
      }
      .limit30 {
        width: 89px;
        height: 30px;
        font-size: 14px;
        font-family: PingFangSC;
        font-weight: 400;
        color: #666;
        line-height: 30px;
        margin-left: 4px;
      }
      .limit300 {
        position: absolute;
        width: 60px;
        text-align: right;
        left: 756px;
        bottom: 17px;
        height: 17px;
        font-size: 12px;
        font-family: PingFangSC;
        font-weight: 400;
        color: rgba(153, 153, 153, 1);
        background: #fff;
        /*line-height: 17px;*/
      }
      .line {
        padding-left: 40px;
        color: #ddd;
      }
      .active {
        font-weight: 600;
      }
      .el-radio__label {
        font-weight: 600;
      }
      .el-radio + .el-radio {
        margin-left: 20px;
      }
      .selected-modal {
        display: inline-block;
        vertical-align: top;
        line-height: 30px;
        padding-right: 15px;
        font-weight: bold;
      }
      .form-control {
        display: inline-block;
        vertical-align: top;
        width: 320px;
      }
      .el-form-control {
        height: 30px;
        width: 320px;
        .el-input {
          height: 30px;
          color: #000;
          .el-input__inner {
            height: 30px;
            color: #000;
          }
        }
      }
      .desc {
        width: 722px;
        height: 78px;
        resize: none;
        padding-bottom: 20px;
      }
      .shelves-name {
        width: 38px;
        padding-right: 0;
        margin-right: 20px;
        cursor: pointer;
      }
      .shelves {
        display: inline-block;
        vertical-align: top;
        width: 12px;
        height: 12px;
        margin-top: 7px;
        background: $org-color;
        border-radius: 12px;
        cursor: pointer;
      }
      .un-shelves {
        display: inline-block;
        vertical-align: top;
        width: 12px;
        height: 12px;
        margin-top: 7px;
        background: #fff;
        border: 1px solid #979797;
        border-radius: 12px;
        cursor: pointer;
      }
      .goods-status {
        line-height: 30px;
      }
      .brand-box {
        position: absolute;
        background: #fff;
        padding: 5px;
        border-radius: 4px;
        box-shadow: 0 1px 3px $border-color;
        z-index: 5;
        left: 535px;
        top: 35px;
        width: 300px;
        max-height: 200px;
        overflow: auto;
        .brand-item {
          padding: 5px;
          font-size: 14px;
          line-height: 16px;
          cursor: pointer;
          &:hover {
            background: #f7f7f7;
          }
        }
      }
      .el-form-control {
        height: 30px;
        width: 200px;
        margin-left: 20px;
        .el-input {
          height: 30px;
          .el-input__inner {
            height: 30px;
          }
        }
      }
    }
  }
  .spec-wrap {
    margin-bottom: 20px;
    padding: 20px 20px;
    background: $white-color;
    box-shadow: 0 2px 3px $border-color;
    border-radius: 4px;

    .sizeValue {
      width: 100%;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    .skuItemBox {
      border-bottom: 1px solid #e5e5e5;
    }
    .skuTitle {
      padding-bottom: 20px;
    }
    .name-item {
      .skuNameBox {
        margin-top: 21px;
        margin-bottom: 7px;
        display: flex;
        align-items: center;
        h2 {
          font-weight: 400;
          font-size: 14px;
          width:66px;
          text-align: right;
          // line-height: 30px;
        }
        .skuNameInput {
          position: relative;
        }
        input {
          margin-left: 10px;
        }
        img {
          position: absolute;
          top: -8px;
          right: 10px;
        }
      }
      .skuValueBox {
        margin-top: 21px;
        margin-bottom: 7px;
        display: flex;
        align-items: flex-start;
        // display:flex;
        // align-items:center;
        // flex-wrap:wrap;
        h2 {
          // width:50px;
          font-weight: 400;
          font-size: 14px;
          margin-bottom: 15px;
          line-height: 30px;
          height: 30px;
          display: inline-block;
          width:66px;
          text-align: right;
          word-break: keep-all;
        }
        .skuValueContBox-cont {
          display: inline-block;
        }
        .skuValueContBox {
          display: flex;
          align-items: center;
          flex-wrap: wrap;
          .addSkuBtn {
            background: rgba(225, 55, 108, 1);
            border-radius: 100px;
            padding: 5px 14px;
            margin-left: 50px;
            font-size: 14px;
            color: #fff;
            margin-bottom: 15px;
            cursor: pointer;
          }
        }
        .skuValueCont {
          display: flex;
          align-items: center;
          margin-bottom: 15px;
          .skuValueInput {
            position: relative;
            input {
              margin-left: 10px;
              // width:100px;
            }
            img {
              position: absolute;
              top: -8px;
              right: 10px;
            }
          }
          // .sku-img{
          //     display:flex;
          //     align-items:center;
          // }
          .sku-img {
            display: inline-block;
            vertical-align: top;
            .imageUrl {
              display: inline-block;
              vertical-align: top;
              width: 30px;
              height: 30px;
              border-radius: 2px;
            }
            .link {
              margin-left: 10px;
              line-height: 30px;
            }
            .del-spec-img {
              color: $main-color;
            }
            .upload-container {
              display: inline-block;
              vertical-align: top;
            }
          }
        }
      }
      span {
        display: inline-block;
        vertical-align: top;
        width: 76px;
        text-align: right;
        color: #333333;
        font-size: 14px;
        line-height: 30px;
        margin-right: 12px;
      }
      .addBtn {
        margin-top: 2px;
        display: inline-block;
        padding: 5px 14px;
        font-size: 14px;
        color: #fff;
        border-radius: 100px;
        background: #e1376cff;
        cursor: pointer;
      }
      .link {
        line-height: 30px;
        padding-left: 8px;
      }
      .form-control {
        display: inline-block;
        vertical-align: top;
      }
      .name-input {
        // width: 300px;
        margin-right: 20px;
      }
      .btn-main {
        margin-right: 20px;
      }
      .btn-fill-main {
        height: 30px;
        cursor: pointer;
      }
    }
    .title-item {
      border-bottom: 1px solid #e5e5e5ff;
      padding-bottom: 20px;
      .lookDemo {
        width: 70px;
        height: 24px;
        font-size: 12px;
        font-family: PingFangSC;
        font-weight: 400;
        color: rgba(58, 98, 225, 1);
        line-height: 24px;
        margin-left: 10px;
        position: relative;
        vertical-align: middle;
        &:hover .lookDemoimg {
          display: block;
        }
        .lookDemoimg {
          display: none;
          position: absolute;
          left: 100px;
          top: -50px;
          z-index: 99;
        }
      }
    }
    .value-item {
      padding-bottom: 20px;
      span {
        display: inline-block;
        vertical-align: top;
        width: 78px;
        text-align: left;
        color: #333333;
        font-size: 14px;
        line-height: 30px;
      }
      .value-box {
        display: inline-block;
        vertical-align: top;
        white-space: normal;
        width: 730px;
        .value-item-spec {
          width: unset;
          margin-right: 15px;
          .spec {
            width: unset;
            height: 30px;
            line-height: 30px;
            color: #333333;
            border: 1px dashed $border-color;
            padding: 0 20px;
            margin-right: 10px;
            position: relative;
            img {
              position: absolute;
              width: 20px;
              height: 20px;
              border-radius: 20px;
              right: -10px;
              top: -10px;
              cursor: pointer;
            }
          }
        }
        .sku-img {
          display: inline-block;
          vertical-align: top;
          .imageUrl {
            display: inline-block;
            vertical-align: top;
            width: 30px;
            height: 30px;
            border-radius: 2px;
          }
          .link {
            margin-left: 10px;
            line-height: 30px;
          }
          .del-spec-img {
            color: $main-color;
          }
          .upload-container {
            display: inline-block;
            vertical-align: top;
          }
        }
      }
    }
    .set-sku-weight {
      position: relative;
      .text {
        // margin-left: 592px;
        margin-left: 692px;
        margin-right: 10px;
        font-size: 14px;
        color: #333;
        font-weight: bold;
      }
      .form-control {
        width: 80px;
        padding-right: 35px;
      }
      .kg {
        position: absolute;
        right: 80px;
        top: 2px;
      }
      .btn-main {
        min-width: 60px;
        width: 60px;
        margin-left: 10px;
      }
    }
  }
  .sameGroupBox {
    padding: 10px 20px 0px;
    background: #fff;
    margin-bottom: 20px;
    .top {
      padding-bottom: 5px;
    }
    .topline{
      border-bottom: 1px solid #e5e5e5;
    }
    .title {
      display: inline-block;
    }
    .boxInFo {
      display: inline-block;
      .totalInfo {
        display: inline-block;
        margin: 0 20px 0 10px;
        font-size: 14px;
        line-height: 20px;
        color: #333;
      }
      .operateBtn {
        display: inline-block;
        border-radius: 100px;
        color: #fff;
        font-size: 14px;
        background: #e1376c;
        padding: 5px 15px;
      }
    }
    .cont {
      .dotAll {
        background: #e1376c;
        border: 1px solid #e1376c;
      }
      .operate {
        width: 100%;
        display: flex;
        height: 30px;
        align-items: center;
        justify-content: space-between;
        .left {
          display: flex;
          align-items: center;
        }
        .dot {
          display: inline-block;
          width: 14px;
          height: 14px;
          border: 1px solid #ddd;
          border-radius: 7px;
          margin-left: 20px;
          margin-right: 32px;
          &.active {
            background: #e1376c;
            border: 1px solid #e1376c;
          }
        }
        .dotName {
          display: inline-block;
          font-size: 14px;
          font-weight: 600;
          margin-left: 17px;
        }
        .operateBtn {
          display: flex;
          // margin-left:50px;
          span {
            border-radius: 100px;
            opacity: 0.6;
            border: 1px solid #ccccccff;
            padding: 5px 14px;
            margin-left: 10px;
            color: #333333ff;
            &.active {
              border: 1px solid #e1376c;
              color: #e1376c;
            }
          }
        }
        .serachBox {
          display: flex;
          align-items: center;
          justify-content: center;
          .title {
            font-size: 14px;
            color: #333333;
          }
          .el-input {
            margin: 0 20px 0 10px;
            width: 240px;
            .el-input__inner {
              height: 30px;
              line-height: 30px;
            }
          }
          .searchBtn {
            border-radius: 100px;
            opacity: 0.6;
            border: 1px solid rgba(225, 55, 108, 1);
            font-size: 14px;
            color: #e1376c;
            padding: 5px 29px;
          }
        }
      }
      .operation {
        color: #666;
        font-weight: bold;
      }
      .pagination-container {
        text-align: right;
      }
      .noSuccess {
        td {
          color: #999;
        }
      }
      .priceStyle {
        color: #e1376c;
        font-weight: 500;
      }
      .el-tabs__item.is-active {
        color: #e1376c;
        font-weight: 600;
      }
      .el-tabs__active-bar {
        background-color: #e1376c;
      }
      .el-tabs__item:hover {
        color: #e1376c;
      }
      .goodsName {
        color: #3a62e1;
        cursor: pointer;
      }
      .itemPrice {
        color: #e1376c;
      }
      .transportStyle {
        color: #f06941;
      }
    }
    // .imgBox{
    //     width:52px;
    //     background:#D8D8D8;
    // }
    // .table td{
    //     padding:10px 20px 10px 0;
    // }
    .productName {
      width: 180px;
      max-height: 40px;
      margin: 16px 0px 16px 0px;
      line-height: 20px;
      word-break: break-all;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
      overflow: hidden;
    }
    // .table tr td:nth-of-type(2){
    //     padding-left:0;
    // }
  }
  .supportBox {
    padding: 10px 20px 25px;
    background: #fff;
    margin-bottom: 20px;
  }
  .tableBox {
    // width: 1200px;
    background: #fff;
    border-collapse: collapse;
    border-spacing: 0;
    border-radius: 4px;
    overflow: hidden;
    box-shadow: 0 0 0 1px $border-color;
    thead {
      background: #ebeffc;
    }
    tr {
      .mustChoose {
        color: #f00;
      }
      .widthSize {
        width: 45px;
      }
      .noWrap {
        white-space: nowrap;
        .link {
          padding: 8px 3px;
          border: 1px solid transparent;
        }
        .select {
          border-radius:4px;
          border:1px solid rgba(58,98,225,1);
        }
      }
      .weini {
        color: #e1376c;
      }
      .operateTh {
        // width:340px;
        // display: flex;
        // align-items: center;
        // justify-content: flex-end;
        .ellipsis {
          text-align: left;
          span {
            line-height: 50px;
            color: #666;
            font-weight: bold;
          }
        }
      }
      th,
      td {
        text-align: left;
        padding: 0px 15px 0px 0;
        // line-height: 40px;
        // padding: 0 10px;
        // white-space: nowrap;
        select {
          min-width: 120px;
        }
        .form-control {
          width: 100%;
        }
        .del {
          margin-left: 10px;
          color: $main-color;
        }
      }
      .switchOperate {
        text-align: left;
      }
    }
    .selectedTr {
      background: #f7f7f7;
    }
  }
  .editor-wrap {
    background: #fff;
    padding: 20px 20px;
  }
  .channel-wrap {
    margin-bottom: 20px;
    .channel-item {
      display: inline-block;
      vertical-align: top;
      background: #fff;
      border-radius: 4px;
      width: 520px;
      overflow: hidden;
      margin: 0 15px 15px 0;
      &:nth-child(4n) {
        margin-right: 0;
      }
      &:nth-child(4n-3) {
        .channel-title {
          background: #e54f7f;
        }
      }
      &:nth-child(4n-2) {
        .channel-title {
          background: #a855e5;
        }
      }
      &:nth-child(4n-1) {
        .channel-title {
          background: #ea6157;
        }
      }
      &:nth-child(4n) {
        .channel-title {
          background: #517adc;
        }
      }
      .channel-title {
        height: 40px;
        padding-left: 20px;
        span {
          display: inline-block;
          line-height: 40px;
          color: #fff;
          font-size: 16px;
          font-weight: bold;
        }
        .link {
          font-size: 14px;
          float: right;
          color: #fff;
          border-color: #fff;
          margin: 7px 10px 0 0;
          height: 26px;
          line-height: 26px;
        }
      }
      .price {
        padding-left: 20px;
        padding-top: 15px;
        span {
          display: inline-block;
          vertical-align: top;
          width: 70px;
          line-height: 30px;
          color: #333333;
          font-size: 14px;
          font-weight: bold;
        }
        .form-control {
          width: 200px;
        }
      }
      .supplier {
        padding: 20px 0 30px;
        p {
          font-size: 14px;
          color: #333333;
          font-weight: bold;
          padding-left: 20px;
        }
        .supplier-item {
          padding: 20px 20px 0;
          white-space: normal;
          word-break: break-all;
          span {
            color: #333333;
            font-size: 14px;
            margin-left: 10px;
          }
          .supplier-price {
            color: $main-color;
            padding: 0 20px 0 8px;
          }
          .supplier-input {
            width: 100px;
            margin-left: 10px;
          }
          .supplier-select {
            min-width: 100px;
            width: 100px;
            margin-left: 10px;
          }
        }
      }
    }
  }
  .dialog-form {
    .modal-item {
      padding-bottom: 15px;
      span {
        display: inline-block;
        vertical-align: top;
        width: 78px;
        font-size: 14px;
        color: #333333;
        font-weight: bold;
        line-height: 30px;
      }
      .item-content {
        display: inline-block;
        vertical-align: top;
        width: 410px;
        white-space: normal;
        word-break: break-all;
        .spec-item {
          display: inline-block;
          vertical-align: top;
          font-weight: normal;
          width: auto;
          height: 30px;
          padding: 0 30px;
          border: 1px dashed $border-color;
          line-height: 30px;
          border-radius: 4px;
          margin-right: 15px;
          cursor: pointer;
        }
        .spec_active {
          border-color: #e1376c;
          color: #e1376c;
        }
        .current-mix {
          font-weight: normal;
          width: unset;
          span {
            width: unset;
            padding-right: 5px;
          }
        }
        .btn-fill-main {
          height: 20px;
          line-height: 20px;
          padding: 0 8px;
          min-width: auto;
          margin-top: 4px;
          margin-left: 10px;
        }
      }
    }
    .current-mix-box {
      padding-bottom: 0;
    }
    .modal-item-bot {
      padding-top: 15px;
      padding-bottom: 0;
    }
    .set-current {
      .set-name {
        width: 122px;
        border: 1px solid #dddddd;
        padding-left: 10px;
        margin-top: 15px;
      }
      .colon {
        line-height: 30px;
        padding: 0 10px;
        vertical-align: bottom;
      }
      span {
        display: inline-block;
        vertical-align: top;
        line-height: 30px;
        color: #333;
      }
      .conected-spec {
        display: inline-block;
        vertical-align: top;
        margin-top: 15px;
        .conected-spec-name {
          position: relative;
          padding: 0 30px;
          border: 1px dashed #999999;
          border-radius: 4px;
          margin-right: 10px;
          img {
            position: absolute;
            right: -10px;
            top: -10px;
            width: 20px;
            height: 20px;
          }
        }
        .conected-spec-count {
          border: 1px solid #dddddd;
          border-radius: 4px;
          .count {
            padding: 0 10px;
          }
          .set-count {
            width: 20px;
            height: 30px;
            background: #dddddd;
            .add-count {
              display: block;
              height: 13px;
              background: url("~assets/goods/add.png") no-repeat center bottom;
              margin-bottom: 3px;
              cursor: pointer;
            }
            .reduce-count {
              display: block;
              height: 13px;
              background: url("~assets/goods/reduce.png") no-repeat center top;
              cursor: pointer;
            }
          }
        }
      }
    }
  }
  .channel-dialog {
    .search-item {
      padding-bottom: 10px;
      span {
        display: inline-block;
        vertical-align: top;
        line-height: 30px;
      }
      .title {
        color: #333;
        font-weight: bold;
        font-size: 14px;
        width: 82px;
      }
      .form-control {
        width: 670px;
      }
      .letter {
        cursor: pointer;
        margin-right: 14px;
        &.active {
          color: #f06941;
        }
      }
      .selected-item {
        margin-top: 4px;
        height: 22px;
        line-height: 22px;
        padding: 0 20px 0 8px;
        border-radius: 22px;
        background: $main-color;
        color: #fff;
        margin-right: 10px;
        font-size: 12px;
        max-width: 200px;
        overflow: hidden;
        text-overflow: ellipsis;
        position: relative;
        a {
          position: absolute;
          color: #fff;
          font-size: 12px;
          top: 0;
          right: 10px;
        }
      }
    }
    .selected－brand {
      padding-top: 10px;
      padding-bottom: 20px;
    }
    .selected-box {
      max-height: 200px;
      overflow: auto;
      .brand-item {
        display: inline-block;
        vertical-align: top;
        width: 20%;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        .link {
          display: inline-block;
          vertical-align: top;
          line-height: 30px;
          color: #333;
          border-radius: 30px;
          padding: 0 8px;
          border: 1px solid #fff;
          max-width: 100%;
          &:hover {
            color: $main-color;
            border: 1px solid $main-color;
          }
        }
      }
    }
  }
}
.alertBox {
  .itemCont {
    span {
      margin-left: 10px;
    }
  }
  .itemCont:nth-of-type(1) {
    margin-bottom: 16px;
  }
}
</style>
<style lang="scss" scoped>
.spec-list {
  .autoWidth {
    // width: auto !important;
  }
  .spec-list{
    min-width: 1200px;
    overflow: auto;
  }
  .skuBoxTable {
    width: 100%;
    min-width: 1360px;
    table-layout:fixed;
    overflow-x: scroll;
    border-collapse: separate;
    text-align: left;
    thead {
      th {
        color: #666;
        font-weight: bold;
        height: 50px;
        // border-bottom:1px solid $border-color;
      }
      .table-w40 {
        width: 40px;
      }
      .table-w60 {
        width: 60px;
      }
      .table-w80 {
        width: 80px;
      }
      .table-w90 {
        width: 90px;
      }
      .table-w100 {
        width: 100px;
      }
      .table-w110 {
        width: 110px;
      }
      .table-w120 {
        width: 120px;
      }
      .table-w130 {
        width: 130px;
      }
      .table-w140 {
        width: 140px;
      }
      .table-w150 {
        width: 150px;
      }
      .table-w160 {
        width: 160px;
      }
      .table-w170 {
        width: 170px;
      }
      .table-w180 {
        width: 180px;
      }
      .table-w200 {
        width: 200px;
      }
      .table-w220 {
        width: 220px;
      }
      .table-w240 {
        width: 240px;
      }
      .table-w330 {
        width: 330px;
      }
      .table-w340 {
        width: 340px;
      }
      .table-w360 {
        width: 360px;
      }
      .table-w440 {
        width: 440px;
      }
      tr {
        margin-left: 20px;
        border-bottom: 1px solid $border-color;
        th:first-child {
          padding-left: 20px;
          position: relative;
          border-bottom: none;
          &::before {
            content: "";
            position: absolute;
            width: 100%;
            height: 0;
            background: $border-color;
            left: 20px;
            bottom: 0;
          }
        }
      }
    }
    tbody {
      &:nth-of-type(odd) {
        tr {
          &:nth-child(odd) {
            background: #f2f4f7;
          }
        }
      }
      tr {
        background: #fff;
        // &:nth-child(odd){
        //     background: #F2F4F7;
        // }
        td:first-child {
          padding-left: 20px;
        }
      }
      .selectedTr {
        background: rgba($color: $org-color, $alpha: 0.1) !important;
      }
    }
    th,
    td {
      font-size: 14px;
      padding: 16px 15px 12px 0;
      vertical-align: middle;
      img {
        max-width: 54px;
        max-height: 54px;
      }
    }
    td {
      height: 48px;
      padding: 10px 20px 10px 0;
      border-bottom: 1px solid #e5e5e5;
      .imgBoxCont {
        display: inline-block;
        width: 52px;
        height: 52px;
        background: #eee;
        text-align: center;
        vertical-align: middle;
      }
      .imgBox {
        width: 52px;
      }
      .active-status {
        color: $org-color;
      }
      .disable-status {
        color: #999;
      }
      .during-status {
        color: $main-color;
      }
    }
    .price-status {
      color: $main-color;
      font-weight: bold;
    }
    .dot {
      width: 60px;
      position: relative;
      &::after {
        content: " ";
        width: 12px;
        height: 12px;
        border: 1px solid #ddd;
        border-radius: 7px;
        position: absolute;
        left: 20px;
        top: 50%;
        margin-top: -7px;
      }
      &.active::after {
        background: $main-color;
        border: 1px solid $main-color;
      }
    }
  }
  .table {
    tbody {
      tr {
        &:nth-child() {
          background: #fff;
        }
      }
      &:nth-of-type(odd) {
        tr {
          &:nth-child(even) {
            background: #f2f4f7;
          }
        }
      }
    }
  }
}
  .tableLog{
    margin:-20px -20px 0 -20px;
    max-height: 300px;
    overflow: auto;
    table{
      width:100%;
      text-align: left;
    }
    tr{
      th,td{

        font-size: 14px;
        color:#666;
        height: 40px;
        line-height: 40px;

        &:nth-child(1){
          padding-left: 20px;
        }
      }
      th{
        font-weight: bold;
        border-bottom: 1px solid #eaeaea;
      }
      &:nth-child(2n){
        background:rgba(242,244,247,1);
      }

    }

  }
  .log-more{
    height: 40px;
    line-height: 40px;
    font-size: 14px;
    color:#999;
    text-align: center;
  }
  /deep/.choic-icon {
    position: absolute;
    left: 816px;
    width: 5px;
    height: 100%;
    justify-content: center;
    top: 17px;
    .sanjiao {
       border: 4px solid transparent;
       border-bottom-color: #999;
       position: absolute;
       top: -12px;
       left: 0;
    }
    .bottom {
        border-bottom-color:transparent;
        border-top-color: #999;
        position: absolute;
        left: 0;
        top: 0;
    }
  }
  /deep/.elips {
    width: 100%;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
  }
  /deep/#tinymce {
    p {
      img {
        display: block;
      }
    }
  }
</style>
<style scoped>
    /deep/ .el-input{
    height: 30px;
    width: 748px;
  }
    /deep/ .el-input__inner{
    height: 30px;
    line-height: 30px;
    color: #000;
  }
    /deep/ .el-input__suffix{
    display: none;
  }
    /deep/ input::-webkit-input-placeholder {
    color: #c0c4cc;
  }
    /deep/ textarea::-webkit-input-placeholder {
    color: #c0c4cc;
  }
    /deep/ input::-moz-input-placeholder {
    color: #c0c4cc;
  }
  input::-ms-input-placeholder {
    color: #c0c4cc;
  }
  textarea::-moz-input-placeholder {
    color:#c0c4cc;
  }
  textarea::-ms-input-placeholder {
    color:#c0c4cc;
  }
    .dispatchBox{
        display: flex;
        align-items: center;
        box-sizing: border-box;
        padding: 15px 0 15px 0;
    }
    .settingBox{
        color: #333333;
        font-size: 14px;
        /*width: 66px;*/
        text-align: right;
        margin-right: 12px;
        padding-left: 20px;
    }
    .printBox{
        width: 86px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        border:1px solid rgba(225,55,108,1);
        color: #E1376C;
        border-radius: 30px;
        margin-right: 10px;
        cursor: pointer;
    }
    .setPrintBox{
        box-sizing: border-box;
        /*padding: 20px;*/
    }
    .setPrintItem{
        display: flex;
        justify-content: center;
    }
    .moneyIcon{
        display: inline-block;
        width: 35px;
        height: 30px;
        text-align: center;
        line-height: 30px;
        background:rgba(245,245,245,1);
        border-radius:4px 0px 0px 4px;
        border:1px solid rgba(221,221,221,1);
        border-right: none;
    }
    .moneyInput{
        width:265px;
        height:30px;
        border-radius:0 4px 4px 0;
        border:1px solid rgba(221,221,221,1);
        border-left: none;
    }
    .tipsTitle{
        color: #999999;
        font-size: 14px;
        margin-top: 10px;
    }




</style>
