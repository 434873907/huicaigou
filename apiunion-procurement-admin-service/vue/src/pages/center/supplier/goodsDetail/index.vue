<template>
    <div class="wrapBox">
        <div>
            <div class="titleMar">
                商品类型
            </div>
            <div class="containBox">
                <div v-if="goodsDetail.fromTemplate">
                    <span>该商品是供货商使用的模板的商品</span> <span class="clickText marLeft20" @click="lookTemplate">查看该商品模板>></span>
                </div>
                <div v-if="!goodsDetail.fromTemplate">
                    <span>该商品是供货商自建的商品</span> <span class="clickText marLeft20" @click="addTemplate" >添加该商品至商品模板>></span>
                </div>
            </div>
            <div class="titleMar">
                基本信息
            </div>
            <div class="containBox">
                <div class="goodsDetailBox">
                    <div class="goodsImgBox">
                        <div class="imgBox" >
                            <img :src="activeimg" alt="">
                        </div>
                        <div class="smallImgBox">
                            <div class="arrowLeft marRight15" @click="scrollItem('left')">
                                <
                            </div>
                            <div class="centerBox">
                                <div class="centerImgBox" ref="previewImg">
                                    <div class="centerImg"  @mouseover="tabImg(item.url)" :class="[activeimg==item.url?'activeBor':'']" v-for="item of imgList">
                                        <img :src="item.url" alt="">
                                    </div>
                                </div>
                            </div>

                            <div class="arrowLeft"  @click="scrollItem('right')">
                                >
                            </div>
                        </div>
                    </div>
                    <div class="marLeft20">
                        <div class="f18 marBottom10">
                            {{goodsDetail.name}}
                        </div>
                        <div class="f16 marBottom10">
                            {{goodsDetail.desc}}
                        </div>
                        <div class="goodsItemBox">
                            <div class="marBottom15">
                                <span class="f14">所属分类</span> <span class="f14Col"> {{goodsDetail.cateName1}}{{goodsDetail.cateName2}}{{goodsDetail.cateName3}}</span>
                            </div>
                            <div class="marBottom15">
                                <span class="f14">所属品牌</span> <span class="f14Col"> {{goodsDetail.brandName}}</span>
                            </div>
                            <div class="marBottom15">
                                <span class="f14">商品型号</span> <span class="f14Col"> {{goodsDetail.itemModel}}</span>
                            </div>
                            <div class="marBottom15">
                                <span class="f14">商品单位</span> <span class="f14Col"> {{goodsDetail.unit}}</span>
                            </div>
                            <div class="marBottom15">
                                <span class="f14">货币单位</span> <span class="f14Col"> {{goodsDetail.currency}}</span>
                            </div>
                            <div class="marBottom15">
                                <span class="f14">商品描述</span> <span class="f14Col"> {{goodsDetail.desc}}</span>
                            </div>
                            <div class="marBottom15">
                                <span class="f14">商品状态</span> <span class="f14Col"> {{goodsDetail.status==1?'上架':'下架'}}</span>
                            </div>
                            <div class="marBottom15">
                                <span class="f14">所属货主</span> <span class="f14Col"> {{goodsDetail.supplierName}}</span>
                            </div>
                            <div class="marBottom15">
                                <span class="f14">更新时间</span> <span class="f14Col"> {{goodsDetail.modifyTime}}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="titleMar">
                商品规格信息
            </div>
            <div class="tableBox">
                <table class="table"  ref="table">
                    <thead>
                    <tr>
                        <th class="table-w200">颜色+尺寸</th>
                        <th class="table-w200">条形码/货号</th>
                        <th class="table-w120">重量(KG)</th>
                        <th class="table-w100">件装</th>
                        <th class="table-w100">日常售价</th>
                        <th class="table-w140" >发货方式</th>
                        <th class="table-w80">当前供货价</th>
                        <th class="table-w160">当前库存</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item, index) in tableData" :key="index+'table'" :class="{'selectedTr':item.checked}">
                        <td>
                            <span v-for="val of item.skuValues">{{val.skuName}}+{{val.skuValue}}</span>
                        </td>
                        <td>{{item.upc}}</td>
                        <td> {{item.weight}} </td>
                        <td> {{item.quantity}} </td>
                        <td>{{item.salePrice}}</td>
                        <td>{{item.channelName}}</td>
                        <td>{{item.supplierPrice}}</td>
                        <td>{{item.totalStock}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="titleMar">
                商品图文信息
            </div>
            <div class="goodsDetailImgBox">
                <div class="goodsDetailImg">
                    <div v-html="goodsDetail.richDesc">

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "index",
        data(){
            return {
                activeimg:'',
                imgList:[],
                tableData:[],
                itemId:'',
                goodsDetail:{},
            }
        },
        mounted() {
            this.itemId = this.$route.query.itemId;
            if(this.itemId){
                this.getDetail();
            }
        },
        methods:{
            //获取详情
            getDetail(){
                this.api.supplier.supplierItemDetail({
                    itemId:this.itemId
                }).then(res=>{
                    this.goodsDetail = res.data;
                    this.imgList = res.data.imageList;
                    if(res.data.imageList.length>0){
                        this.activeimg = res.data.imageList[0].url;
                    }
                    this.tableData = res.data.skuList
                })
            },
            //查看商品模板
            lookTemplate(){
              this.$router.push({
                  path:"addGoodsTemplate",
                  query:{
                      templateItemId:this.goodsDetail.templateItemId
                  }
              })
            },
            //添加新模板
            addTemplate(){
                this.$router.push({
                    path:"addGoodsTemplate",
                    query:{
                        itemId:this.itemId,
                        type:1
                    }
                })
            },
            tabImg(url){
                this.activeimg = url;
            },
            scrollItem(direction){
                console.log(direction)
                let len=Math.ceil(this.imgList.length / 4);
                let startLength = 1;
                if(direction == 'right'){
                    startLength<len?startLength++:startLength=len;
                }else{
                    startLength>len?startLength--:startLength=1;
                }
                if(startLength>1){
                    console.log((- 320 * (startLength-1)  +(4*len - this.imgList.length)*80))
                    this.$refs.previewImg.style.left = (- 320 * (startLength-1)+2 +(4*len - this.imgList.length)*80) +"px";
                } else {
                    this.$refs.previewImg.style.left = (- 320 * (startLength-1)) +"px";
                }
            }
        }
    }
</script>

<style scoped lang="scss">
.containBox{
    background: #ffffff;
    box-sizing: border-box;
    padding: 20px;
}
    .goodsDetailBox{
        display: flex;
    }
    .goodsImgBox{
        /*overflow: hidden;*/
        width: 400px;
    }
    .imgBox{
        border: 1px solid rgba(221,221,221,1);
        width: 398px;
        height: 398px;
        img {
            width: 398px;
            height: 398px;
        }
    }
    .smallImgBox{
        display: flex;
        margin-top: 20px;
        width: 400px;
        overflow: hidden;
    }
    .arrowLeft{
        width:35px;
        height:64px;
        background:rgba(255,255,255,1);
        border-radius:2px;
        border:1px solid rgba(221,221,221,1);
        text-align: center;
        line-height: 64px;
        cursor: pointer;
        position: relative;
        z-index: 6;
    }
    .centerBox{
        width: 320px;
        overflow: hidden;
        height: 64px;
    }
    .centerImgBox{
        /*width: 320px;*/
        height: 64px;
        position: relative;
        display: flex;
    }
    .centerImg{
        margin-right: 15px;
        cursor: pointer;
        height: 64px;
        img {
            width: 64px;
            height: 62px;
            display: block;
        }
    }
    .activeBor{
        border: 1px solid #E1376C;
    }
    .f18{
        font-weight: 600;
        color: #333333;
        font-size: 18px;
    }
    .f16{
        font-size: 16px;
        color: #666666;
        line-height: 1.4;
    }
    .goodsItemBox{
        background:rgba(245,245,245,1);
        border-radius:2px;
        border-bottom: 1px dashed #cccccc;
        border-top: 1px dashed #cccccc;
        box-sizing: border-box;
        padding: 20px 20px 5px 20px;
    }
    .f14{
        font-size: 14px;
        color: #666666;
    }
    .f14Col{
        font-size: 14px;
        color: #333333;
        margin-left: 20px;
    }
    .tableBox{
        background: #ffffff;
    }
    .goodsDetailImgBox{
        background: #ffffff;
        box-sizing: border-box;
        padding: 20px;
    }
    .goodsDetailImg{
        box-sizing: border-box;
        padding: 20px;
        border:1px solid #dddddd;
    }
</style>
