<template>
    <div class="center-brand">
        <div class="search-bar">
            <div class="form-wrap brandSelect">
                <div class="form-name">品牌名称：</div>
                <input type="text" class="form-control" placeholder="请输入品牌名称" v-model="listParams.brandName" @keyup.enter="getList(1)">
            </div>
            <div class="form-wrap brandSelect">
                <div class="form-name">所属国家：</div>
                <select  class="form-control" v-model="listParams.countryCode" :class="{'unset-select':!listParams.countryCode}">
                    <option v-for="(item, index) in countList" v-bind:value="item.code" :key="index+'code'">{{item.name}}</option>
                </select>            </div>
            <div class="btn-fill-main" @click="getList(1)">搜索</div>
            <div class="btn-main pull-right" @click="addBrand()">新增品牌</div>
        </div>
        <table class="table table-text">
            <thead>
            <tr>
                <th class="table-w90">品牌ID</th>
                <th class="table-w120">品牌中文名</th>
                <th class="table-w120">品牌英文名</th>
                <th class="table-w200">品牌别名</th>
                <th class="table-w170">所属国家</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in listData" :key="index+'list'">
                <td>{{item.id}}</td>
                <td>
                    <el-popover
                            placement="top"
                            width="260"
                            trigger="hover">
                        <span>{{item.nameZh}}</span>
                        <span slot="reference" class="ellipsis">{{item.nameZh}}</span>
                    </el-popover>
                </td>
                <td>
                    <el-popover
                            placement="top"
                            width="260"
                            trigger="hover">
                        <span>{{item.nameEn}}</span>
                        <span class="ellipsis" slot="reference">{{item.nameEn}}</span>
                    </el-popover>
                </td>
                <td>
                    <span v-for="val of item.alias"> {{val}} </span>
                </td>
                <td>
                    {{item.countryName}}
                </td>
                <td>
                    <a class="link" @click="editBrand(item)">编辑</a>
                </td>
            </tr>
            </tbody>
        </table>
        <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
        <noData v-if="total===0"></noData>
        <el-dialog :title="id?'编辑品牌':'新增品牌'" :visible.sync="addShow" width="622px">
            <div class="dialog-form">
                <div class="form-wrap">
                    <div class="form-name form-name-title">(注：品牌中英文名称至少需要填写一个)</div>
                </div>
                <div class="form-wrap">
                    <div class="form-name">品牌中文</div>
                    <input v-model="params.nameZh" class="form-control"/>
                </div>
                <div class="form-wrap">
                    <div class="form-name">品牌英文</div>
                    <input v-model="params.nameEn" class="form-control"/>
                </div>
                <div class="form-wrap">
                    <div class="form-name">品牌别名</div>
                    <textarea v-model="params.alias" class="form-control-text"></textarea>
                </div>
                <div class="form-wrap">
                    <div class="form-name">所属国家</div>
                    <select  class="form-control" v-model="params.countryCode">
                        <option v-for="(item, index) in countList" v-bind:value="item.code" :key="index+'code'">{{item.name}}</option>
                    </select>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
          <button class="btn-org-main" @click="addBrandConfrim">保存</button>
          <button class="btn-fill-grey-main" @click="addShow = false">取消</button>
        </span>
        </el-dialog>


    </div>
</template>
<script>
    import SelectBrand from '@/components/selectBrand/index.vue';
    import Pagination from '@/components/pagination';
    import noData from '@/components/noData';
    export default {
        data() {
            return {
                addShow:false,
                showBrand:false,
                total: -1,
                listParams: {
                    page:1,
                    limit:10,
                    brandName:'',
                    countryCode:''
                },
                countryCodeList:[],
                params:{
                    nameZh:'',
                    nameEn:'',
                    alias:"",
                    countryCode:""
                },
                listData: [],
                id:'',
                countList:[]
            }
        },
        methods:{
            init() {
                this.getList(1);
                this.countryList();
            },
            //获取国家列表
            countryList(){
              this.api.brand.countryList().then((res)=>{
                  this.countList = res.data;
                  this.countList.unshift({
                      name:'全部'
                  })
              })
            },
            //新增品牌
            addBrand(){
                this.id = '';
                this.params ={
                       nameZh:'',
                        nameEn:'',
                        alias:"",
                        countryCode:""
                };
                this.addShow = true;

            },
            //编辑
            editBrand(item){
                this.addShow = true;
                this.id = item.id;
                this.params.nameZh = item.nameZh
                this.params.nameEn = item.nameEn
                this.params.alias = item.alias.join(',')
                this.params.countryCode = item.countryCode
            },
            getList(page) {
                this.listParams.page = page;
                this.api.template.queryBrandLibraryList(this.listParams).then((res) => {
                    this.listData = res.data.dataList;
                    this.total = res.data.total;
                });
            },
            //保存

            addBrandConfrim(){
                if(this.id){
                    this.api.template.updateBrandLibrary({
                        ...this.params,
                        brandId:this.id
                    }).then(res=>{
                        this.getList();
                        this.addShow = false
                    })
                }else {
                    this.api.template.addBrandLibrary({
                        ...this.params
                    }).then(res=>{
                        this.getList();
                        this.addShow = false
                    })
                }
            }
        },
        created() {
            this.init();
        },
        components: { Pagination, noData,SelectBrand}
    }
</script>
<style scoped lang='scss'>
    .center-brand{
        background: $white-color;
        .brandSelect{
            margin-right: 14px;
        }
        .table{
            .link{
                margin-right: 20px;
            }
            .ellipsis{
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;
                max-width: 100%;
            }
        }
        .search-bar{
            .form-name{
                font-weight: 600;
            }
        }
        .letter-wrap{
            display: inline-block;
            margin-right: 40px;
            user-select: none;
            .letter-item{
                display: inline-block;
                padding: 0 5px;
                cursor: pointer;
                &.active{
                    color:$org-color;
                }
            }
        }
        .dialog-form{
            padding-left: 0;
            box-sizing: border-box;
            .form-name{
                vertical-align: top;
                line-height: 30px;
                width: auto;
            }
            .form-name-title{
                color: #999999;
                font-size: 14px;
            }
            .form-control{
                width: 514px;
            }
            .form-control-text{
                width: 508px;
                border-radius: 4px;
                height: 58px;
            }
        }
    }
    .brandBox{
        display: flex;
        align-items: center;
        box-sizing: border-box;
        padding: 0px 60px 20px 60px;
        justify-content: space-between;
    }
    .brandItem{
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;


    }

    .brandImg{
        width: 52px;
        height: 52px;
    }
    .brandText{
        font-size: 14px;
        color: #999999;
        margin-top: 10px;
        white-space:nowrap;/*规定段落中的文本不进行换行*/
        overflow:hidden;/*内容会被修剪，并且其余内容是不可见的。*/
        text-overflow:ellipsis;
        max-width: 110px;
    }
    .remarkBox{
        box-sizing: border-box;
        padding: 0 10px;
        border-top: 1px solid #e5e5e5;
    }
    .remarkItem{
        box-sizing: border-box;
        padding: 20px 30px 0 30px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    .remark{
        font-size: 14px;
        color: #999999;
        margin-top: 10px;
    }
    .arrow{
        width: 52px;
        height: 13px;
        margin-top: 10px;
    }
    .form-wrap{
        display: flex;
        align-items: center;
    }
</style>
