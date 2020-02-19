<template>
    <div class="adminGroup-box">
        <el-dialog
            title="管理同组商品"
            :visible.sync="dialogVisible"
            :before-close="close"
            width="1000px">
            <div class="cont">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane :label="labelName.firstLabel" name="first"></el-tab-pane>
                    <el-tab-pane :label="labelName.secondLabel" name="second"></el-tab-pane>
                </el-tabs>
                <div class="operate">
                    <div class="dot"  :class="{'dotAll':checkAll}" @click="selectAll()"></div>
                    <div class="dotName">全选</div>
                    <div class="operateBtn">
                        <span @click="groupDete()" v-if="activeName == 'first'" :class="{'active':avtiveOk}">批量剔除</span>
                        <span @click="groupAdd()" v-else :class="{'active':avtiveOk}">批量添加</span>
                    </div>
                    <div class="serachBox" v-if="activeName == 'second'">
                        <span class="title">关键字</span>
                        <el-input v-model.trim="input" placeholder="请输入商品名称、商品ID、条形码" @keyup.enter.native="search"></el-input>
                        <span class="searchBtn" @click="search">搜索</span>
                    </div>
                </div>
                 <table class="table">
                    <thead>
                    <tr>
                        <th class="dot" :class="{'active':checkAll}" @click="selectAll()"></th>
                        <th class="table-w100">宝贝ID</th>
                        <th class="table-w160">主图</th>
                        <th class="table-w160">商品名称</th>
                        <th class="table-w100">品牌</th>
                        <th class="table-w100">型号</th>
                        <th class="table-w80">价格</th>
                        <th class="table-w100">发货方式</th>
                        <th class="table-w100">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item, index) in sameGroupTableData" :key="index+'table'" :class="{'selectedTr':item.checked,'noSuccess':item.status}">
                        <td class="dot" :class="{'active':item.checked}" @click="selectItem(item)"></td>
                        <td>{{item.id}}</td>
                        <td>
                            <el-popover
                                placement="top"
                                width="260"
                                trigger="hover">
                                <img style="width:100%;height:100%" :src="item.mainImageUrl">
                                <img slot="reference" :src="item.mainImageUrl" />
                            </el-popover>
                        </td>
                        <td>
                            <el-popover 
                                placement="top"
                                width="260"
                                trigger="hover">
                                <span>{{item.name}}</span>
                                <span class="ellipsis goodsName" @click="goDetail(item)" slot="reference">{{item.name}}</span>
                            </el-popover>
                        </td>
                        <td>{{item.brandName}}</td>
                        <td>
                            <el-popover class="ellipsis"
                                placement="top"
                                width="260"
                                trigger="hover">
                                <span>
                                <span v-for="(sku, sku_index) in item.skuList" :key="sku_index+'sku'">
                                    {{sku}}
                                    <span v-show="sku_index != item.skuList.length-1">、</span>
                                </span>
                                </span>
                                <span slot="reference">
                                <span v-for="(sku, sku_index) in item.skuList" :key="sku_index+'sku'">
                                    {{sku}}
                                    <span v-show="sku_index != item.skuList.length-1">、</span>
                                </span>
                                </span>
                            </el-popover>
                        </td>
                        <td><span class="itemPrice">{{item.price}}</span></td>
                        <td>
                             <el-popover class="ellipsis"
                                placement="top"
                                width="260"
                                trigger="hover">
                                <span>
                                <span v-for="(sku, channels_index) in item.channels" :key="channels_index+'sku'">
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
                        <a href="javascript:;" class="link base-margin-right" v-if="activeName == 'first'&&item.id !== adminGoodsData.id" @click="deteItem(item)">剔除</a>
                        <a href="javascript:;" v-if="activeName == 'second'&&item.id !== adminGoodsData.id" class="link" @click="addItem(item)">添加</a>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <pagination v-show="total>0" :total="total" :page.sync="listParams.page" layout="total, sizes, prev, pager, next, jumper" :limit.sync="listParams.limit" @pagination="getGoodsList()" />
                <noData v-if="total===0"></noData>
            </div>
            <span slot="footer" class="dialog-footer">
                <button @click="close">确 定</button>
                <button @click="close">取 消</button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
import Pagination from '@/components/pagination';
import noData from '@/components/noData';
export default {
    props:{
        adminGoodsData:{
            type:Object
        },
        page:{
            type:Object
        }
    },
    components:{
        Pagination,noData
    },
    data(){
        return {
            dialogVisible:true,
            checkAll: false,
            sameGroupTableData:[],
            avtiveOk:false,
            avtiveNo:false,
            total:1,
            listParams: {
                page: 1,
                limit: 10,
            },
            activeName:'first',
            labelName:{firstLabel:'管理同组商品',secondLabel:'增加同组商品'},
            input:'',
            groupSum:0
        }
    },
    methods: {
        handleClose(){
            
        },
        close(){
            this.dialogVisible = true;
            this.$parent.init(this.page.page);
        },
        confirm(){

        },
        goDetail(item){
            let path = `commodity/detail/${item.id}/1`
            window.open(path, '_blank');
        },
        //批量剔除
        groupDete(){
            let itemIds = []
            this.sameGroupTableData.forEach(val=>{
                if(val.checked){
                    itemIds.push(val.id)
                }
            })
            if(itemIds.length){
                if(this.avtiveOk){
                    this.avtiveOk = false
                } else {
                    this.avtiveOk = true
                }
                this.api.goods.removeGoodsFromGroup({itemIds:JSON.stringify(itemIds)}).then(()=>{
                    this.$message.success({message:'更新成功', showClose:true});
                    this.getGoodsList()
                })
            }else {
                this.$message.warning({message:'请选择商品', showClose:true});
            }
        },
        //批量添加
        groupAdd(){
            let groupIds = []
            let itemIds = []
            this.sameGroupTableData.forEach(val=>{
                if(val.checked){
                    val.groupId ? groupIds.push(val.groupId) : itemIds.push(val.id)
                }
            })
            if(groupIds.length || itemIds.length){
                if(this.avtiveOk){
                    this.avtiveOk = false
                } else {
                    this.avtiveOk = true
                }
                this.api.goods.mergeGoodsGroup({itemIds:JSON.stringify(itemIds),groupIds:JSON.stringify(groupIds)}).then(()=>{
                    this.$message.success({message:'更新成功', showClose:true});
                    this.getGoodsList()
                })
            }else {
                this.$message.warning({message:'请选择商品', showClose:true});
            }
        },
        //获取列表
        getGoodsList(){
            this.groupNum();
            this.api.goods.groupList(this.listParams).then((res) => {
                res.data.dataList.forEach(val=>{
                    val.checked = false
                })
                this.sameGroupTableData = res.data.dataList
                this.total = res.data.total;
                this.checkAll = false
            }).catch(()=>{
                this.total = 0;
                this.checkAll = false
                this.sameGroupTableData = []
            });
        },
        //全选
        selectAll(){
            this.checkAll = !this.checkAll
            this.sameGroupTableData.forEach((item) => {
                if(this.checkAll) {
                item.checked = true;
                } else {
                item.checked = false;
                }
            });
            
        },
        selectItem(item){
            item.checked ? item.checked = false : item.checked = true
            if(!item.checked){
                this.checkAll = false
            }
        },
        //单个商品剔除
        deteItem(item){
            this.api.goods.removeGoodsFromGroup({itemIds:JSON.stringify([item.id])}).then(()=>{
                this.$message.success({message:'剔除成功', showClose:true});
                this.getGoodsList()
            })
        },
        //单个商品添加
        addItem(item){
            let itemIds = []
            let groupIds = []
            if(this.adminGoodsData.groupId){
                groupIds = [this.adminGoodsData.groupId]
            }else{
                itemIds = [this.adminGoodsData.id]
            }
            item.groupId ? groupIds.push(item.groupId) : itemIds.push(item.id)
            this.api.goods.mergeGoodsGroup({itemIds:JSON.stringify(itemIds),groupIds:JSON.stringify(groupIds)}).then((res)=>{
                this.$message.success({message:'添加成功', showClose:true});
                this.getGoodsList();
                this.adminGoodsData.groupId = res.data ? res.data : 0
            })
        },
        handleClick(tab){
            this.listParams.keyword ? delete this.listParams.keyword : ''
            if(tab.name == 'first'){
                this.listParams.groupId = this.adminGoodsData.groupId
                this.listParams.itemId = this.adminGoodsData.id
                this.listParams.exceptGroupId ? delete  this.listParams.exceptGroupId : ''
                this.listParams.page = 1;
            } else {
                delete this.listParams.groupId
                this.listParams.exceptGroupId = this.adminGoodsData.groupId
                this.listParams.exceptItemId = this.adminGoodsData.itemId
                this.listParams.itemId ? delete this.listParams.itemId : ''
                this.listParams.page = 1;
            }
            this.getGoodsList()
        },
        search(){
            this.listParams = {isGift:-1,page: 1,limit: 10,statusList:JSON.stringify([this.adminGoodsData.status]),keyword:this.input}
            this.getGoodsList()
        },
        groupNum(){
                let groupIds = []
                let itemIds = []
                this.adminGoodsData.groupId ? groupIds.push(this.adminGoodsData.groupId) : itemIds.push(this.adminGoodsData.id)
                this.api.goods.goodsGroupNum({groupIds:JSON.stringify(groupIds),itemIds:JSON.stringify(itemIds)}).then(res=>{
                    this.groupSum = res.data
                    if(this.groupSum){
                        this.labelName.firstLabel = `管理同组商品(${this.groupSum})`
                    }
                })
            },
    },
    created(){
        this.listParams = {isGift:-1,page: 1,limit: 10,statusList:JSON.stringify(['1','2']),groupId:this.adminGoodsData.groupId,itemId:this.adminGoodsData.id}
        this.getGoodsList()
    }
}
</script>
<style lang="scss">
    .adminGroup-box{
        width:100%;
        height: 100%;
        .el-dialog{
            width:1200px;
        }
        .cont{
            .dotAll{
                background: #E1376C;
                border: 1px solid #E1376C;
            }
            .operate{
                width:100%;
                display: flex;
                height: 30px;
                align-items: center;
                .dot{
                    display: inline-block;
                    width: 14px;
                    height: 14px;
                    border:1px solid  #ddd;
                    border-radius: 7px;
                    margin-left:20px;
                    margin-right:25px;
                    &.active{
                        background: #E1376C;
                        border: 1px solid #E1376C;
                    }
                }
                .dotName{
                    display: inline-block;
                    font-size:14px;
                    font-weight:600;
                    // margin-left:13px;
                }
                .operateBtn{
                    display: flex;
                    // margin-left:50px;
                    span{
                        border-radius:100px;
                        opacity:0.6;
                        border:1px solid #CCCCCCFF;
                        padding:5px 14px;
                        margin-left:10px;
                        color:#333333FF;
                        &.active{
                            border:1px solid #E1376C;
                            color:#E1376C;
                        }
                    }
                }
                .serachBox{
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    margin-left:363px;
                    .title{
                        font-size:14px;
                        color:#333333;
                        width:72px;
                    }
                    .el-input {
                        margin:0 20px 0 10px;
                        .el-input__inner{
                            height: 30px;
                            line-height: 30px;
                        }
                    }
                    .searchBtn{
                        width:109px;
                        border-radius:100px;
                        opacity:0.6;
                        border:1px solid rgba(225,55,108,1);
                        font-size:14px;
                        color:#E1376C;
                        padding:5px 29px;
                    }
                }
            }
            .operation {
                    color: #666;
                    font-weight: bold;
            }
            .pagination-container{
                text-align: right;
            }
            .noSuccess{
                td{
                    color:#999
                }
            }
            .priceStyle{
                color:#E1376C
            }
            .el-tabs__item.is-active{
                color:#E1376C;
                font-weight: 600;
            }
            .el-tabs__active-bar{
                background-color:#E1376C;
            }
            .el-tabs__item:hover{
                color:#E1376C;
            }
            .goodsName{
                color:#3A62E1;
                cursor: pointer;
            }
            .itemPrice{
                color:#E1376C;
            }
            .transportStyle{
                color:#F06941
            }
        }
        .dialog-footer{
            button{
                width:86px;
                height:30px;
                border-radius:100px;
                border:0;
                background:#fff;
            }
            button:nth-of-type(1){
                background:rgba(225,55,108,1);
                color:#fff;
            }
             button:nth-of-type(2){
                border:1px solid #999999FF
            }
        }
    }
    .alertBox{
        div{
            margin:5px 0;
            span{
                margin-left:10px;
            }
        }
    }
</style>


