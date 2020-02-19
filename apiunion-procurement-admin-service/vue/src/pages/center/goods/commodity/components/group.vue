<template>
    <div class="group-box">
         <el-dialog
            title="设为同组商品"
            :before-close="close"
            :visible.sync="dialogVisible"
            width="468px">
            <div class="cont">
                您当前正在将<span>{{num}}</span>个商品设为同一分组商品,设置后,该分组商品总量将达到总商品数量将达到<span>{{groupNum}}</span>个，请确认是否设置？
            </div>
            <span slot="footer" class="dialog-footer">
                <button class="confirm" @click="confirm">确 定</button>
                <button class="cancle" @click="close">取 消</button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
export default {
    props:{
        'groupData':{
            type:Object,
            default:{num:0}
        }
    },
    data(){
        return {
            dialogVisible:true,
            num :0,
            groupNum:0
        }
    },
    created(){
        this.num = this.groupData.num
        this.groupNum = this.groupData.groupNum
    },
    methods:{
        confirm(){
            this.api.goods.mergeGoodsGroup({groupIds:this.groupData.groupIds,itemIds:this.groupData.itemIds}).then(()=>{
               this.close()
            })
        },
        close(){
            this.$parent.init();
            this.dialogVisible = true;
        },
    }
}
</script>
<style lang="scss">
    .group-box{
        .cont{
            // width:376px;
            // height:84px;
            font-size:16px;
            font-weight:400;
            color:#333333;
            line-height:22px;
            text-align: center;
            font-family:PingFangSC-Regular;
            span{
                font-size:20px;
                margin:0 4px;
            }
        }
        .dialog-footer{
            button{
                border:0;
                border-radius:100px;
                padding:5px 28px;
                font-size:14px;
                cursor: pointer;
            }
            .confirm{
                background: #E1376C;
                color:#fff;
            }
            .cancle{
                border:1px solid #999;
                color:#333;
                background:#fff;
            }
        }
    }
</style>


