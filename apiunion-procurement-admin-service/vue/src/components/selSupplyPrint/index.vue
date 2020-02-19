<template>
    <div>
        <div class="type-content-wrap">
            <div class="type-title">货主筛选： </div>

            <div class="type-content border-bottom">
                <div class="scrollBox" :class="showClose?'scrollBoxHeActive':'scrollBoxHe'">
                    <div class="type-item" v-for="(item, index) in searchSupplierList" :key="index" :class="[idsList.includes(item.id)?'active':'']">
                        <a href="javascript:;" class="ellipsis"  @click="selectItem(item,index)">
                            {{item.supplierName}}{{item.count}}
                        </a>
                    </div>
                </div>

            </div>
            <div>
                <a class="closeText" @click="showCloseBtn">{{showClose?'关闭全部':'展开全部'}}</a>
            </div>
        </div>
    </div>
    </div>
</template>

<script>
    export default {
        name: "selSupplierList",
        data(){
            return {
                searchSupplierList:[],
                idsList:[],
                showClose:false,
            }
        },
        mounted(){
            this.getSupplier()
        },
        methods:{
            getSupplier() {
                this.api.supplier.queryCountBySupplier({status: this.$parent.listParams.status}).then((res) => {
                    this.searchSupplierList = res.data;
                });
            },
            showCloseBtn(){
                this.showClose = !this.showClose
            },
            selectItem(item,index){
                let indexNum = this.idsList.indexOf(item.id);
                if(indexNum>-1){
                    this.idsList.splice(indexNum,1)
                }else {
                    this.idsList.push(item.id)
                }
                console.log(this.idsList)
            }
        }
    }
</script>

<style lang="scss" scoped>
    .type-content-wrap{
        display: flex;
        padding: 10px 0;
        border-top:1px solid $border-color;
        &:first-child{
            border: none;
        }
        .type-title{
            /*width: 90px;*/
            font-weight: 600;
            line-height: 30px;
            margin-right: 10px;
        }
        .type-content{
            display: flex;
            flex: 1;
            flex-wrap:wrap;
            align-content:center;

            .scrollBox{
                display: flex;
                flex: 1;
                flex-wrap:wrap;

                .type-item{
                    /*min-width: 80px;*/
                    line-height: 30px;
                    border-radius: 15px;
                    margin-right: 10px;
                    border-radius: 15px;
                    .ellipsis{
                        /*padding-right: 30px;*/
                        width: auto;
                        max-width: 100%;
                        position: relative;
                        .item-count{
                            position: absolute;
                            right: 5px;
                            top: 0;
                        }
                    }
                    .pr40{
                        /*padding-right: 30px;*/
                    }
                    a{
                        display: inline-block;
                        vertical-align: top;
                        color: #333;
                        height: 28px;
                        line-height: 28px;
                        padding: 0 10px;
                        border-radius: 28px;
                        border: 1px solid #fff;
                    }
                }
                .active{
                    a{
                        border:1px solid rgba($color: $main-color, $alpha: 0.6);
                        color:$main-color;
                    }
                }
            }
            .scrollBoxHe{
                height: 30px;
                overflow: hidden;
            }
            .scrollBoxHeActive{
                max-height: 120px;
                overflow: scroll;
            }



        }
    }
    .closeText{
        line-height: 30px;
    }
</style>
