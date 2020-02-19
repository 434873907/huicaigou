<template>
    <div class="changeTypeBox">
        <el-dialog
            title="确认商品类目"
            :before-close="close"
            :visible.sync="dialogVisible">
            <div class="cont">
                <div class="firstType typeBox" v-if="firstCateList.length>0">
                    <span class="title">一级类目:</span>
                    <div class="itemBox">
                        <span @click="selectCate(1, item, firstCateList)" :class="{'active-f1':item.active}" class="item" v-for="(item,index) in firstCateList" :key="index">{{item.name}}</span>
                        <span class="item" v-for="item in 7" :key="item + 'f1'"></span>
                    </div>
                </div>
                <div class="secondType typeBox" v-if="secondCateList.length>0">
                    <span class="title">二级类目:</span>
                    <div class="itemBox">
                        <span @click="selectCate(2, item, secondCateList)" :class="{'active-f2':item.active}" class="item" v-for="(item,index) in secondCateList" :key="index">{{item.name}}</span>
                        <span class="item" v-for="item in 7" :key="item + 'f2'"></span>
                    </div>
                </div>
                <div class="thirdType typeBox" v-if="thirdCateList.length>0">
                    <span class="title">三级类目:</span>
                    <div class="itemBox">
                        <span @click="selectCate(3, item, thirdCateList)" class="item" :class="{'active-f3':item.active}" v-for="(item,index) in thirdCateList" :key="index">{{item.name}}</span>
                        <span class="item" v-for="item in 7" :key="item + 'f3'"></span>
                    </div>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
                <button @click="confirm">确 定</button>
                <button @click="close">取 消</button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
    let {log} = console;
export default {
    name:'changeType',
    props:{
        changeTypeData:{
            type:Object,
            cr:String,
        },

    },
    data(){
        return {
            dialogVisible:true,
            firstCateList: [],
            secondCateList: [],
            thirdCateList: [],
            selectedCate: {
                first:'',
                second:'',
                third:''
            },
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
            }
        }
    },
    created(){
         // 获取一级类目列表
      this.getCate(0, '');
    },
    methods: {
        confirm(){
            if(this.changeTypeData.cr=="noAjax"){ //不请求，只返回分类的选择数据
                log(this.selectedCate);
                this.$emit("dataCatgory",this.selectedCate)
                this.close();
                return;
            }


            let itemIds = this.changeTypeData.id
            let categoryId = ''
            if(this.selectedCate.third){
                categoryId = this.selectedCate.third
            } else if(this.selectedCate.second){
                categoryId = this.selectedCate.second
            }else if(this.selectedCate.first){
                categoryId = this.selectedCate.first
            }
            if(!categoryId){
               this.$message.warning({message:'请先选择类目', showClose:true});
               return ;
            }
            this.api.goods.updateGoodsCategory({itemIds:itemIds,categoryId:categoryId}).then(()=>{
                this.$message.success({message:'更新类目成功', showClose:true});
                this.close()
                // this.dialogVisible = false
            })
        },
        getCate(level, id) {
            let pId = id ? id : 0;
            this.api.category.categoryNode({parentId:pId,status:1}).then((res) => {
                res.data.forEach(val=>{
                    val.active = false
                })
                if(level===0){
                    this.firstCateList = res.data;
                    this.secondCateList = [];
                    this.thirdCateList = [];
                } else if (level===1){
                    this.secondCateList = res.data;
                    this.thirdCateList = [];
                }else{
                    this.thirdCateList = res.data;
                }
            });
        },
        selectCate(type, item, list) {
            list.forEach(val=>{
                val.active = false
            })
            item.active = true
            if(type === 1){
                if(this.selectedCate.first !== item.id){
                    this.getCate(1, item.id);
                    this.selectedCate.first = item.id;
                    this.selectedCate.second = '';
                    this.selectedCate.third = '';
                    this.selectedCate.firstName = item.name;
                    this.selectedCate.secondName = '';
                    this.selectedCate.thirdName = '';
                } else {
                    this.selectedCate.first = '';
                    this.selectedCate.second = '';
                    this.selectedCate.third = '';
                    this.secondCateList = [];
                    this.thirdCateList = [];
                }
            } else if (type === 2){
                if(this.selectedCate.second !== item.id){
                    this.getCate(2, item.id);
                    this.selectedCate.second = item.id;
                    this.selectedCate.third = '';
                    this.selectedCate.secondName = item.name;
                    this.selectedCate.thirdName = '';
                } else {
                    this.selectedCate.second = '';
                    this.selectedCate.third = '';
                    this.thirdCateList = [];
                }
            }else{
                if(this.selectedCate.third !== item.id) {
                this.selectedCate.third = item.id;
                this.selectedCate.thirdName = item.name;
                } else {
                this.selectedCate.third = '';
                }
            }
        },
        close(){
            // this.$parent.init()
            this.$parent.closeChangeType()
            this.dialogVisible = true
        },
    }
}
</script>
<style lang="scss">
    .changeTypeBox{
        width:100%;
        height: 100%;
        .el-dialog{
            width:1000px;
        }
        .cont{
            .typeBox{
                display: flex;
                border-bottom: 1px solid #E5E5E5FF;
                .title{
                    // width:72px;
                    margin-top:7px;
                    height:20px;
                    font-size:14px;
                    font-weight:600;
                    color:rgba(51,51,51,1);
                    line-height:20px;
                    white-space: nowrap;
                }
                .itemBox{
                    display: flex;
                    flex-wrap:wrap;
                    justify-content: space-between;
                    margin-bottom:-20px;
                    .item{
                        min-width:92px;
                        // height:20px;
                        font-size:14px;
                        font-weight:400;
                        color:rgba(51,51,51,1);
                        margin:7px 13px;
                        padding:5px 14px;
                        text-align: center;
                        border:1px solid transparent;
                        &.active-f1{
                            border-radius:100px;
                            opacity:0.6;
                            border:1px solid #E1376CFF;
                            color:#E1376CFF;
                        }
                        &.active-f2{
                            border-radius:100px;
                            opacity:0.6;
                            border:1px solid #F06941FF;
                            color:#F06941FF;
                        }
                        &.active-f3{
                            border-radius:100px;
                            opacity:0.6;
                            border:1px solid #3A62E1FF;
                            color:#3A62E1FF;
                        }

                    }
                }
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
</style>


