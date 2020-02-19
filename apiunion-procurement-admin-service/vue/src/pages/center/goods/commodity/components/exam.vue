<template>
    <div class="examBox">
        <el-dialog
            title="商品审核"
            :before-close="close"
            :visible.sync="dialogVisible">
            <div class="cont">
                <div class="operate">
                    <div class="dot"  :class="{'dotAll':checkAll}" @click="selectAll()"></div>
                    <div class="dotName">全选</div>
                    <div class="operateBtn">
                        <span @click="examOk()" :class="{'active':avtiveOk}">批量通过</span>
                        <span @click="examNo()" :class="{'active':avtiveNo}">批量不通过</span>
                    </div>
                </div>
                 <table class="table">
                    <thead>
                    <tr>
                        <th class="dot" :class="{'active':checkAll}" @click="selectAll()"></th>
                        <th class="table-w160">商品标题</th>
                        <th class="table-w100">商品规格</th>
                        <th class="table-w80">唯妮价</th>
                        <th class="table-w80">当前价</th>
                        <th class="table-w100">发货方式</th>
                        <th class="table-w60">利润率</th>
                        <th class="table-w80">供货商</th>
                        <th class="table-w100">供货商旧价</th>
                        <th class="table-w100">供货商新价</th>
                        <th class="table-w100">供货商库存</th>
                        <th class="table-w100">
                            <el-popover class="ellipsis"
                                placement="top-end"
                                width="200"
                                trigger="hover">
                            <!-- <span>操作</span> -->
                            <div class="alertBox">
                                <div>
                                    <el-switch :value ="true" active-color="#13ce66"></el-switch>
                                    <span>指审核通过</span>
                                </div>
                                <div>
                                    <el-switch :value ="false"></el-switch>
                                    <span>指审核不通过</span>
                                </div>
                            </div>
                            <span class="operation" slot="reference">操作？</span>
                        </el-popover>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item, index) in examTableData" :key="index+'table'" :class="{'selectedTr':item.checked,'noSuccess':item.status}">
                        <td class="dot" :class="{'active':item.checked}" @click="selectItem(item)"></td>
                        <td>
                            <el-popover
                                placement="top"
                                width="150"
                                trigger="hover">
                                <span>{{item.itemName}}</span>
                                <span slot="reference"  class="ellipsis">{{item.itemName}}</span>
                            </el-popover>
                        </td>
                        <td>
                            <el-popover
                                placement="top"
                                width="150"
                                trigger="hover">
                                <span>{{item.sku}}</span>
                                <span slot="reference"  class="ellipsis">{{item.sku}}</span>
                            </el-popover>
                        </td>
                        <td class="priceStyle">魏妮佳</td>
                        <td class="priceStyle">{{item.price}}</td>
                        <td>
                            <el-popover
                                placement="top"
                                width="150"
                                trigger="hover">
                                <span>{{item.channelName}}</span>
                                <span slot="reference" class="ellipsis">{{item.channelName}}</span>
                            </el-popover>
                        </td>
                        <td class="priceStyle">{{item.profitRate}}</td>
                        <td class="transportWays">{{item.supplierName}}</td>
                        <td class="priceStyle">{{item.oldChannelPrice}} </td>
                        <td class="price priceStyle">{{item.channelPrice}}</td>
                        <td>{{item.stock}}</td>
                        <!-- <td>
                        <el-popover class="ellipsis"
                            placement="right"
                            width="260"
                            trigger="hover">
                            <span>{{item.name}}</span>
                            <span slot="reference">{{item.name}}</span>
                        </el-popover>
                        </td> -->
                        <!-- <td>
                        <el-popover class="ellipsis"
                            placement="right"
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
                        </td> -->
                        <!-- <td>{{item.weight}}</td>
                        <td>{{item.quantity}}</td> -->
                        <!-- <td v-show="!userConfig.singleChannel">{{item.channelName}}</td> -->
                        <td>
                        <a href="javascript:;" class="link base-margin-right" @click="examItem(item)"><el-switch v-model="item.status" active-color="#13ce66"></el-switch></a>
                        <!-- <a href="javascript:;" class="link base-margin-right" @click="eidt(item, 1)">编辑</a>
                        <a href="javascript:;" class="link base-margin-right" @click="changeItemType(item, 2)">修改类目</a> -->
                        <!-- <a href="javascript:;" class="link">类目</a> -->
                        </td>
                    </tr>
                    </tbody>
                </table>
                <pagination v-show="total>0" :total="total" :page.sync="listParams.page" layout="total, sizes, prev, pager, next, jumper" :limit.sync="listParams.limit" @pagination="getGoodsList()" />
                <noData v-if="total===0"></noData>
            </div>
            <span slot="footer" class="dialog-footer">
                <button @click="confirm">确 定</button>
                <button @click="handleClose">取 消</button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
import Pagination from '@/components/pagination';
import noData from '@/components/noData';
// import examTableData from '../table.json'
export default {
    props:{
        examData:{
            type:Object
        }
    },
    components:{
        Pagination,noData
    },
    data(){
        return {
            dialogVisible:true,
            data:{
                'f1': [
                        {
                            name:'基础1基础1',
                            active:false
                        }, {
                            name:'基础1基础1',
                            active:false
                        }, {
                            name:'基础基础11',
                            active:false
                        }, {
                            name:'基础基础11',
                            active:false
                        }, {
                            name:'基础基础11',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }],
                'f2': [ {
                            name:'基基础1础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        },],
                'f3': [ {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        }, {
                            name:'基础1',
                            active:false
                        },]
            },
            checkAll: false,
            examTableData:[],
            avtiveOk:false,
            avtiveNo:false,
            total:1,
            listParams: {
                page: 1,
                limit: 20,
            },
            updataParams:{}
        }
    },
    methods: {
        handleClose(){
            this.close()
        },
        confirm(){
            this.api.goods.updatApproveStatus(this.updataParams).then(res=>{
                if(res.success){
                    this.$message.success({message:'更新成功', showClose:true});
                    this.updataParams = {status:'',ids:''}
                    this.close()
                }

            })
        },
        examOk(){
            this.updataParams = {}
            this.avtiveNo = false
            if(this.avtiveOk){
                this.avtiveOk = false
            } else {
                this.avtiveOk = true
            }
            let str = ''
            this.examTableData.forEach(val=>{
                if(val.checked){
                    str+=val.id+','
                    val.status = 1
                }
            })
            this.updataParams.approvedIds = str.substring(0, str.length-1)
        },
        examNo(){
            this.updataParams = {}
            this.avtiveOk = false
            if(this.avtiveNo){
                this.avtiveNo = false
            } else {
                this.avtiveNo = true
            }
            let str = ''
            this.examTableData.forEach(val=>{
                if(val.checked){
                    str+=val.id+','
                    val.status = 0
                }
            })
            this.updataParams.refusedIds = str.substring(0, str.length-1)
        },
        getGoodsList(){
            this.api.goods.approvedItem({id:this.examData.itemId,...this.listParams}).then(res=>{
                res.data.dataList.forEach(val=>{
                    val.checked = false
                })
                this.examTableData = res.data.dataList
                this.total = res.data.total;
            })
        },
        selectAll(){
            this.checkAll = !this.checkAll
            this.examTableData.forEach((item) => {
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
        examItem(item){
            let approvedIdsArr = this.updataParams.approvedIds ? this.updataParams.approvedIds.split(',') : []
            let refusedIdsArr = this.updataParams.refusedIds ? this.updataParams.refusedIds.split(',') : []
            approvedIdsArr.forEach((val,index)=>{
                if(val == item.id && !item.status){
                    approvedIdsArr.splice(index,1)
                }
            })
            if(!approvedIdsArr.includes(item.ids) && item.status){
                approvedIdsArr.push(item.id)
            }
            refusedIdsArr.forEach((val,index)=>{
                if(val == item.id && item.status){
                    refusedIdsArr.splice(index,1)
                }
            })
             if(!refusedIdsArr.includes(item.ids) && !item.status){
                refusedIdsArr.push(item.id)
            }
            this.updataParams.approvedIds = approvedIdsArr.join(',')
            this.updataParams.refusedIds = refusedIdsArr.join(',')

        },
        close(){
            this.$parent.init()
            this.dialogVisible = true;
        }
    },
    created(){
        this.getGoodsList()
    }
}
</script>
<style lang="scss">
    .examBox{
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
                height: 20px;
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
                }
                .operateBtn{
                    display: flex;
                    margin-left:50px;
                    span{
                        border-radius:100px;
                        opacity:0.6;
                        border:1px solid #CCCCCCFF;
                        padding:5px 14px;
                        margin-left:10px;
                        color:#333333FF;
                        &.active{
                            border:1px solid #E1376CFF;
                            color:#E1376CFF;
                        }
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


