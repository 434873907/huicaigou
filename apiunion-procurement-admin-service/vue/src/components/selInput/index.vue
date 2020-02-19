<template>
    <div>
        <div class="inputBox marLeft10">
            <input
                    type="text"
                    class="form-control"
                    v-model="inputVal"
                    maxlength="50"
                    @focus="inputFocus"
                    @input="inputChange"
                    @blur="inputBlur"
                    placeholder=""
            />
            <div class="listBox" v-show="showList">
                <div class="listItem" @click="selItem(item)" v-for="item of list">{{item}}</div>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: "index",
        props:['orgList','index','val'],
        data(){
            return {
                inputVal:this.val,
                showList:false,
                list:this.orgList,
            }
        },
        mounted(){
            this.list = this.orgList;
        },
        methods:{
            inputFocus(){
                this.showList = true
                this.list = this.orgList
                console.log('99999')
            },
            inputChange(){
                this.list = this.orgList.filter((item)=>{
                    return item.indexOf(this.inputVal)>-1
                });
            },
            inputBlur(){
                setTimeout(()=>{
                    this.$emit('modifySkuName',{
                        inputVal:this.inputVal,
                        index:this.index
                    })
                    this.showList = false
                },100)
            },
            selItem(item){
                this.inputVal = item;
                this.showList = false;
                this.$emit('modifySkuName',{
                    inputVal:this.inputVal,
                    index:this.index
                })
            }
        }
    }
</script>

<style scoped>
.inputBox{
    position: relative;
}
    .listBox{
        position: absolute;
        z-index: 221;
        background: #ffffff;
        width: 170px;
        border-radius: 8px;
        box-sizing: border-box;
        padding-bottom: 10px;
    }
    .listItem{
        background: #ffffff;
        cursor: pointer;
        padding: 10px 0 0 10px;
    }
</style>
