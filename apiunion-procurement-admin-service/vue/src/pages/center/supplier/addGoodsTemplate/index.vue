<template>
    <div class="goods-detail">

        <h2 class="big-title">同品检测</h2>
        <div class="info-wrap">
            <div class="info-item relative">
                <span class="info-item-title">关键信息</span>
                <input
                        type="text"
                        class="form-control"
                        v-model="keyword"
                        maxlength="50"
                        @input="checkSameTemplateItemDetail"
                        placeholder="请输入商品标题,型号，条形码搜索相似商品"
                        style="width:748px;"
                />
                <span class="limit30">（输入商品相关信息检测是否存在类似的商品）</span>
                <div class="checkListBox">
                    <div class="noList" v-show="searchList.length==0&&keyword">
                        无相关或者相似商品,可直接创建
                    </div>
                    <div v-show="searchList.length>0&&keyword">
                        <div class="checkList" @click="goDetail(item)" v-for="(item,index) of searchList" :key="index" :class="[index%2==0?'back':'']">
                            <div class="checkImgBox">
                                <img v-if="item.imageList[0]" class="checkImg" :src="item.imageList[0].url" alt="">
                            </div>
                            <div class="checkItem">
                                <div class="checkItemName">
                                    {{item.name}}
                                </div>
                                <div class="checkItemBox">
                                    <div class="checkItemObj">
                                        <span class="checkName">品牌：</span><span class="checkVal">{{item.brandName}}</span>
                                    </div>
                                    <div class="checkItemObj">
                                        <span v-if="item.skuList[0].upc" class="checkName">条形码：</span><span class="checkVal">{{item.skuList[0].upc}}</span>
                                    </div>
                                    <div class="checkItemObj">
                                        <span class="checkName">型号：</span><span  class="checkVal">{{item.itemModel}}</span>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

        </div>
        <h2 class="big-title">宝贝参数</h2>
        <div class="info-wrap" ref="mainCON" id="mainCON">
            <h2 class="info-title">基本介绍:</h2>
            <div class="info-item">
                <span class="info-item-title">所属类目</span>
                <el-select :disabled="detailData.status==1" v-model="goodsParams.categoryId" filterable placeholder="请选择商品标签" @change="changeCate">
                    <el-option
                            v-for="item in cateList"
                            :key="item.id"
                            :label="item.cateName"
                            :value="item.id">
                    </el-option>
                </el-select>
                <span class="choic-icon">
                  <span class="sanjiao"></span>
                  <span class="sanjiao bottom"></span>
                </span>
            </div>
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
                <span class="info-item-title">商品标题</span>
                <input
                        type="text"
                        class="form-control"
                        v-model="goodsParams.name"
                        maxlength="50"
                        placeholder="请输入商品名称"
                        style="width:748px;"
                />
                <span class="limit30">（限制50字符）</span>
            </div>
            <div class="info-item">
                <span class="info-item-title">商品型号</span>
                <input
                        type="text"
                        class="form-control"
                        v-model="goodsParams.itemModel"
                        maxlength="30"
                        placeholder="请输入您的商品型号"
                        style="width:748px;"
                />
                <span class="limit30">（限制30字符）</span>
            </div>
            <div class="info-item">
                <span class="info-item-title">商品描述</span>
                <textarea
                        class="form-control desc"
                        @input="limit300()"
                        placeholder="请输入您的商品描述"
                        maxlength="300"
                        v-model="goodsParams.desc"
                ></textarea>
                <span class="limit30">（限制300字符）</span>
                <span class="limit300" style="padding-bottom: 8px">
                  {{descLength}}
                  <span>/300</span>
                </span>
            </div>
        </div>
        <h2 class="big-title">宝贝图片<div class="sugg" style="display:inline-block;font-size: 12px;color: #666;vertical-align:top;margin-left:10px;">尺寸建议为800x800px，大小不超过1M，限制8张图片</div></h2>
        <div class="img-wrap">
            <div class="main-img">
                <img :src="mainImgUrl" v-show="mainImgUrl" />
                <div class="main-img-null" v-show="!mainImgUrl">未设置</div>
                <p>商品主图</p>
            </div>
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
            <div class="sugg" v-if="false">（商品图片尺寸建议800X800px，最多8张图片）</div>

        </div>


        <div class="spec-wrap">
            <h2 class="big-title shuTiao">商品规格:</h2>
            <div class="name-item skuTitle">
                <span>货币</span>
                <select class="form-control" v-model="goodsParams.currency" :disabled="detailData.status==1">
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
                        :disabled="detailData.status==1"
                        class="form-control"
                        v-model="goodsParams.unit"
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
<!--                        <select style="width: 173px"-->
<!--                                class="form-control name-input marLeft10"-->
<!--                                v-model="item.skuName"-->
<!--                                @change="modifySkuName(item.skuName,index)">-->
<!--                            <option-->
<!--                                    v-for="(item, index) in skuNameList"-->
<!--                                    v-bind:value="item"-->
<!--                                    :key="index"-->
<!--                            >{{item}}</option>-->
<!--                        </select>-->
                        <selInput :orgList="skuNameList" v-on:modifySkuName="modifySkuName" :index="index" :val="item.skuName"></selInput>
                        <img style="right: -10px;" src="~assets/goods/remove_spec.png" @click="removeSkuRow(index)" />
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
                                            style="width: 100px"
                                            type="text"
                                            class="form-control name-input"
                                            v-model="value.name"
                                            @focus="diffValue(value.name)"
                                            @blur="maxSkuList(item, index, true,value.name)"
                                    />
                                    <img src="~assets/goods/remove_spec.png" @click="removeSkuValue(item,valueIndex,value.name)" />
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

            <div class="spec-list" style="min-width: 1200px;overflow: auto;">
                <table class="tableBox skuBoxTable">
                    <thead>
                    <tr>
                        <th class="noWrap table-w120">规格</th>
                        <th class="table-w120">条形码</th>
                        <th class="table-w80">重量(kg)</th>
                        <th class="table-w80">
                            日常售价({{currencySymbol}})
                        </th>
                        <th class="table-w80">
                            参考价({{currencySymbol}})
                        </th>
                        <th class="table-w80">件装({{goodsParams.unit}})</th>
                        <th class="noWrap table-w170">操作</th>
                    </tr>
                    </thead>
                    <tbody
                            v-for="(item, index) in goodsParams.skuList"
                            :key="index"
                            :class="{'selectedTr':selectedSKUName===item.displayName}"
                    >
                    <tr>
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
                                    :class="{'form-control-error': item.isMultiple}"
                                    :disabled="item.isMultiple"
                                    @blur="handleUpcBlur(item.upc,item.valueName)"
                                    v-model="item.upc"
                                    style="width:110px;"
                            />
                        </td>
                        <!--重量-->
                        <td>
                            <ApiInput v-model.number="item.weight"  style="width:70px;"/>
                        </td>
                        <td>
                            <input
                                    placeholder
                                    class="form-control"
                                    v-model.number="item.salePrice"

                                    style="width:70px;"
                            />
                        </td>
                        <td>
                            <ApiInput v-model.number="item.settlePrice"  style="width:70px;"/>
                        </td>
                        <!--              数量-->
                        <td>
                            <ApiInput
                                    v-model="item.quantity"
                                    :disabled="!item.isMultiple"
                                    :class="{'form-control-error': !item.isMultiple}"
                                    @focus="diffNum(item.weight,item.quantity)"
                                    @input="handleNum(item,index)"
                                    style="width:66px;"
                            />
                        </td>
                        <td class="noWrap">
                            <a
                                    href="javascript:;"
                                    v-if="!item.isMultiple"
                                    class="link"
                                    @click.stop="addRow(index)"
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

        <div class="editor-wrap">
            <h2 class="big-title shuTiao">图文详情<span style="display:inline-block;font-size: 12px;color: #666;vertical-align:top;margin-left:10px;">尺寸建议为750x任意高度，大小不超过1M</span></h2>
            <editor v-model="goodsParams.richDesc"></editor>
        </div>
        <SelectBrand
                v-if="showBrandModal"
                v-on:dialogBrand="dialogBrand"
                v-on:dialogBrandCancel="dialogBrandCancel"
                :multiply="false"
        ></SelectBrand>
        <div>
            <div class="save-inner marTop20">
                <a href="javascript:;" class="btn-main marRight10" @click="saveGoods(2)">保存</a>
                <a href="javascript:;" class="btn-main marRight10" @click="saveGoods(1)">保存为草稿</a>
                <div class="btn-fill-grey-main" @click="cancel">返回</div>
            </div>
        </div>
    </div>
</template>
<script>
    import selectBrand from "@/components/templateBrand/index.vue";
    import Pagination from "@/components/pagination";
    import editor from "@/components/tinymce/tinymce.vue";
    import noData from "@/components/noData";
    import selInput from '@/components/selInput/index.vue'
    import draggable from 'vuedraggable'
    export default {
        data() {
            return {
                keyword:"",
                skuNameList:[],
                status: 1,
                id: "",
                searchList:[],
                detailLoaded: false,
                showBrandModal:false,
                selectedSKUName:'',
                mainImgUrl: "",
                cateList: [],
                batchWeight: "",
                currencyList: [],
                channelList: [],
                expressTempList: [],
                setSkuNameValues: [],
                goodsImages: [],
                goodsParams: {
                    currency: "",
                    unit: "",
                    unitCanChange: "",
                    richDesc: "",
                    categoryId: "",
                    brandId: "",
                    brandName: "",
                    desc: "",
                    shopId: "",
                    itemModel: "",
                    firstSpecImages: [],
                    delImageIds: [],
                    skuNameValues: [],
                    skuList: [],
                },
                modalSkuValues: [],
                mixedList: [],
                modalParams: {},
                currentModal: [],
                checkAll: false,
                sameGroupTableData: [],
                avtiveOk: false,
                avtiveNo: false,
                total: 1,
                listParams: {
                    page: 1,
                    limit: 10
                },
                sameGroupTableShow: false, //管理同组商品默认关闭
                currencySymbol: "",
                itemDetail: {},
                detailData: {}, //商品详情数据
                isShow: false, //上传图片删除的icon
                unitList: [],
                descLength: 0,
                isNoSku: false, //新增时的无规格单件装
                guivalue:'',//规格值中间值失焦
                newguivalue:'',//规格值中间值失焦
                weightValue:'',//重量中间值失焦
                type:'',
                itemId:"",
                isNewSku:false
            };
        },
        mounted() {

        },
        created() {
            window.onresize =()=>{
                this.autoWidth = document.body.clientWidth;
            };
            this.init();
        },
        methods: {

            init() {
                // 获取商品id
                this.id = this.$route.query.templateItemId || "";
                this.type = this.$route.query.type || "";
                // 获取货币列表
                this.api.common.currency({}).then(res => {
                    this.currencyList = res.data;
                    this.goodsParams.currency = this.currencyList[0].code;
                });
                //获取单位
                this.api.goods.unitList({}).then(res => {
                    this.unitList = res.data;
                });
                // 获取类目
                this.getCate().then(res=>{
                    if(this.id){
                        this.getDetail();
                    }else {
                        this.goodsParams.unit = "件";
                        this.goodsParams.skuList.push({
                            valueName: '',
                            skuValues: '',
                            upc: "",
                            weight: "",
                            salePrice:0,
                            settlePrice:0,
                            quantity: 1,
                            noSku:true
                        })
                    }
                    if(this.type==1){
                        this.itemId = this.$route.query.itemId;
                        this.getDetail();
                    }

                })

            },
            //同名检测
            checkSameTemplateItemDetail(){
              this.api.supplier.checkSameTemplateItemDetail({
                  keyword:this.keyword
              }).then(res=>{
                this.searchList = res.data.dataList;
              })
            },
            //选中类目
            changeCate(){
              console.log(this.goodsParams.categoryId);
              this.cateList.forEach((item)=>{
                  if(item.id==this.goodsParams.categoryId){
                      this.skuNameList = item.cateSkuNames
                  }
              })
                console.log(this.skuNameList)
            },


            //diff规格值
            diffValue(val) {
                this.guivalue = val;
            },

            //获取子类目列表
            getCate() {
                return new Promise((resolve,reject)=>{
                    this.api.template.queryTemplateCategory().then(res=>{
                        this.cateList = res.data;
                        resolve();
                    })
                })
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

            //失去焦点时处理规格明细
            maxSkuList(item, index, create,diffValue) {
                console.log(item)
                if (!item.skuName) {
                    return;
                }
                console.log(this.goodsParams.skuList)
                if(this.goodsParams.skuList.length==1&&this.goodsParams.skuList[0].noSku){
                    this.goodsParams.skuList.splice(0,1)
                }
                console.log(this.goodsParams.skuList)
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
                    if (create) {
                        this.createSKU();
                    }
                }


            },
            //处理规格明细
            createSKU(delItem,itemName) {
                if(this.isNewSku){
                    this.$set(this.goodsParams,'skuList',[])
                }
                if(delItem){
                    let newSkuList = [];
                    this.goodsParams.skuList.forEach((item,index)=>{
                        console.log(item.valueName)
                        console.log(item.valueName.includes(itemName))
                        if(!item.valueName.includes(itemName)){
                            console.log('888')
                            newSkuList.push(item);
                        }
                    });
                    this.$set(this.goodsParams,'skuList',newSkuList)
                    return false
                }
                let list = []; // 计算若干个规格相叠加
                this.goodsParams.skuNameValues.forEach(item => {
                    let arr = [];
                    item.skuValues.forEach(skuVal => {
                        arr.push(skuVal.name || skuVal);
                    });
                    list.push(arr || "");
                });
                let arr = [];
                this.$set(this.goodsParams,'skuList',[])
                this.doExchange(list).forEach((val) => {
                    console.log(val);
                    let skuValues = [];
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
                    let obj = {
                        valueName: relVal,
                        skuValues: skuValues,
                        upc: "",
                        weight: "",
                        salePrice:0,
                        settlePrice:0,
                        quantity: val.quantity ? val.quantity : 1,
                    };
                    let listItem = this.goodsParams.skuList.filter((item)=>{
                        return item.valueName==val;
                    });
                    if(listItem.length==0){
                        arr.push(obj);
                    }
                    console.log(this.newguivalue)
                    // for(let i=0;i<this.goodsParams.skuList.length;i++){
                    //     console.log(this.goodsParams.skuList[i].valueName,val)
                    //     if(this.goodsParams.skuList[i].valueName!=val){
                    //         arr.push(obj);
                    //         return false
                    //     }
                    // }
                    // if(this.goodsParams.skuList.length==0){
                    //     arr.push(obj);
                    // }
                });
                console.log(this.goodsParams.skuList)
                this.goodsParams.skuList =this.goodsParams.skuList.concat(arr)
                this.isNewSku = false;
                console.log(this.goodsParams.skuList)
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
            goDetail(item) {
                let routeData = this.$router.resolve({
                    path: "/center/supplier/addGoodsTemplate/",
                    query: {
                        templateItemId:item.id
                    }
                });
                window.open(routeData.href, '_blank');

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
            //同步条形码
            handleUpcBlur(upc,valueName){
                this.goodsParams.skuList.forEach((item)=>{
                    if(item.valueName==valueName&&item.isMultiple){
                        item.upc = upc
                    }
                })
            },

            //添加多件装
            addRow(index) {
                let obj = this.copy(this.goodsParams.skuList[index]);
                console.log(obj)
                obj.isMultiple = true;
                obj.weight = this.goodsParams.skuList[index].weight * 2;
                obj.id = 0;
                obj.quantity=2;
                obj.skuValues=this.goodsParams.skuList[index].skuValues;
                this.goodsParams.skuList.splice(index + 1, 0, obj);
            },
            //删除多件装
            delRow(index) {
                this.goodsParams.skuList.splice(index, 1);
            },

            //添加规格名
            addSkuRow(skuList) {
                if(this.detailData.status==1){
                    this.$message.info('当前商品已发布，无法添加规格名')
                    return false
                }
                let item = {
                    skuName: "",
                    skuValues: [{ name: "" }]
                };
                this.isNewSku = true;
                this.setSkuNameValues.push(item);
                console.log(this.setSkuNameValues)
            },
            //规格值删除
            removeSkuValue(item, specIndex,itemName) {
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
                console.log(item)
                // this.$set(this.goodsParams,'skuList',[])
                this.createSKU(true,itemName);
            },

            //删除规格名
            removeSkuRow(index) {

                this.setSkuNameValues.splice(index, 1);
                this.goodsParams.skuNameValues.splice(index, 1);
                this.$set(this.goodsParams,'skuList',[])
                this.createSKU();
            },
            //修改规格名
            modifySkuName(val,index) {
                console.log(this.goodsParams.skuNameValues)
                console.log(val)
                if(this.goodsParams.skuNameValues[val.index]){
                    this.goodsParams.skuNameValues[val.index].skuName = val.inputVal
                }
                if(this.setSkuNameValues[val.index]){
                    this.$set(this.setSkuNameValues[val.index],'skuName',val.inputVal)
                }

                let {goodsParams: {skuList}} = this
                if (skuList && skuList.length) {
                    skuList.forEach(r => {
                        if (r.skuValues && r.skuValues.length) {
                            r.skuValues.forEach((rr, i) => {
                                if (i == index) {
                                    rr.skuName = val.inputVal
                                }
                            })
                        }
                    })
                    this.$set(this.goodsParams, 'skuList', skuList)
                }

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
            dialogBrand(res) {
                this.goodsParams.brandId = res.brandList[0].id;
                this.goodsParams.brandName = res.brandList[0].nameZh;
                this.showBrandModal = false;
            },
            dialogBrandCancel() {
                this.showBrandModal = false;
            },
            cancel(){
              this.$router.push({
                  path:"goodsTemplate"
              })
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

            //获取详情
            getDetail() {
                this.detailLoaded = true;
                if(this.type==1){
                    this.api.supplier.supplierItemDetail({
                        itemId:this.itemId
                    }).then(res=>{
                        this.editDetail(res)
                    })
                }else {
                    this.api.template.templateItemDetail({ templateItemId: this.id }).then(res => {
                        this.editDetail(res)
                    });
                }

            },
            //填充详情
            editDetail(res){
                this.detailData = res.data;
                let detail = res.data;
                this.goodsParams.name = detail.name;
                this.goodsParams.brandId = detail.brandId;
                this.goodsParams.brandName = detail.brandName;
                this.goodsParams.desc = detail.desc;
                 this.descLength = detail.desc?detail.desc.length:'';
                this.goodsParams.itemModel = detail.itemModel;
                this.goodsParams.status = detail.status;
                this.goodsParams.richDesc = detail.richDesc;
                this.goodsParams.currency = detail.currency;
                this.goodsParams.unit = detail.unit ? detail.unit : "件";
                this.goodsParams.skuNameValues = detail.skuNameValues;
                this.goodsParams.id = this.id;
                this.goodsParams.categoryId = detail.cateId;
                this.changeCate();
                detail.skuList.forEach(val => {
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
                        val.isMultiple = true;

                    } else {
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

                this.setSkuNameValues = [];


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
                    detail.skuNameValues[0].skuValues.forEach((name, index) => {
                        detail.skuNameValues[0].skuValueImages.forEach(item => {
                            if (name == item.skuValue) {
                                this.goodsParams.firstSpecImages[index] = item.imageUrl;
                            }
                        });
                    });
                }
            },
            saveGoods(status) {
                let params = this.copy(this.goodsParams);
                let flag = true;
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
                    params.skuNameValues=JSON.stringify(skuNameValues)
                    params.skuList = JSON.stringify(params.skuList);
                    params.status = status;
                    let formData = {};
                    for (let index in params) {
                        if (params[index] !== "") {
                            formData[index] = params[index];
                        }
                    }
                    if (this.id) {
                        formData.templateItemId = this.id;
                        this.api.template.updateTemplateItem(formData).then(res => {
                            this.$message.success({ message: res.message, showClose: true });
                            this.$router.push({ path: "goodsTemplate" });
                        });
                    } else {
                        this.api.template.addTemplateItem(formData).then(res => {
                            this.$message.success({ message: res.message, showClose: true });
                            this.$router.push({ path: "goodsTemplate" });
                        });
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
            SelectBrand: selectBrand,
            Pagination: Pagination,
            editor: editor,
            noData: noData,
            draggable:draggable,
            selInput,
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
    .relative{
        position: relative;
    }
    .checkListBox{
        margin-left: 85px;
        width:748px;
        background:rgba(255,255,255,1);
        box-shadow:0px 2px 10px 0px rgba(0,0,0,0.12);
        border-radius:4px;
        position: absolute;
        z-index: 222;
        max-height: 678px;
        overflow: scroll;
    }
    .noList{
        font-size: 14px;
        color: #999999;
        margin-left: 20px;
        height:50px;
        line-height: 50px;
    }

.checkList{
    display: flex;
    align-items: center;
    box-sizing: border-box;
    padding: 20px;
    cursor: pointer;

}
    .checkImgBox{

    }
    .checkImg{
        width: 70px;
        height: 70px;
    }
    .checkItem{
        margin-left: 15px;
    }
    .checkItemBox{
        display: flex;
        justify-content: space-around;
        margin-top: 10px;
    }
    .checkName{
        font-size: 14px;
        color: #333333;
    }
    .checkVal{
        font-size: 14px;
        color: #000;
    }
    .checkItemObj{
        width: 200px;
    }
    .back{
        background:rgba(242,244,247,1);;
    }



</style>
