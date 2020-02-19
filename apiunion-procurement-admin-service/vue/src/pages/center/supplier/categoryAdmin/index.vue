<template>
    <div class="center-first-category">
        <div class="search-bar">
            <div class="btn-main" @click="openAddModal">新增类目</div>
        </div>
        <table class="table">
            <thead>
            <tr>
                <th class="table-w120">类目名称</th>
                <th class="table-w380">类目描述</th>
                <th class="table-w120">类目类目</th>
                <th class="table-w240">类目属性</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in listData" :key="index+'list'">
                <td>{{item.cateName}}</td>
                <td>{{item.cateDesc}}</td>
                <td>{{item.type==1?'标品商品':item.type==2?'非标品商品':''}}</td>
                <td>
                    <span v-for="item of item.cateSkuNames"> {{item}} </span>
                </td>
                <td>
                    <a class="link" @click="openAddModal(item)">编辑</a>
                </td>
            </tr>
            </tbody>
        </table>
        <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
        <noData v-if="total===0"></noData>
        <el-dialog :title="modalParams.id?'编辑类目':'新增类目'" :visible.sync="addShow" width="622px">
            <div class="dialog-form">
                <div class="form-wrap">
                    <div class="form-name">类目名称</div>
                    <input class="form-control" :disabled="modalParams.id" v-model="modalParams.cateName" maxlength="10" placeholder="请输入类目名称，限制8个字符">
                </div>
                <div class="form-wrap">
                    <div class="form-name">类目描述</div>
                    <textarea class="form-control" style="width: 488px" v-model="modalParams.cateDesc"  placeholder="多个别名是用英文','分隔"></textarea>
                </div>
                <div class="form-wrap" style="display: flex;align-items: center">
                    <div class="form-name">上架状态</div>
                    <el-radio-group v-model="modalParams.type" :disabled="modalParams.id">
                        <el-radio :label="1">标品商品</el-radio>
                        <el-radio :label="2">非标品商品</el-radio>
                    </el-radio-group>
                </div>
                <div class="form-wrap">
                    <div class="form-name">类目属性</div>
                    <input class="form-control" style="width: 418px" v-model="cateSkuNamesItem" maxlength="10" placeholder="请输入类目名称，限制8个字符">
                    <div class="addDescBtn1 marLeft10" @click="addCateSkuNamesItem">确定</div>
                </div>
                <div class="form-wrap">
                    <div class="form-name" style="display: none">类目属性</div>
                    <div class="addDescBtnBox">
                        <div v-for="(item,index) of cateSkuNames" @click="delItemBtn(index)" class="addDescBtn marRight10">{{item}} X </div>
                    </div>
                </div>


            </div>
            <span slot="footer" class="dialog-footer">
        <button class="btn-org-main" @click="saveCate()">确定</button>
        <button class="btn-fill-grey-main" @click="addShow = false">取消</button>
      </span>
        </el-dialog>
    </div>
</template>
<script>
    import Pagination from '@/components/pagination';
    import noData from '@/components/noData';
    export default {
        data() {
            return {
                addShow:false,
                total: -1,
                cateSkuNamesItem:'',
                listParams: {
                    page:1,
                    limit:10
                },
                modalParams: {
                    cateName: '',
                    cateDesc: '',
                    type:1
                },
                cateSkuNames:[],
                listData: []
            }
        },
        created() {
            this.init();
        },
        methods: {
            init() {
                this.getList(1);
            },
            //删除
            delItemBtn(index){
                this.cateSkuNames.splice(index,1)
            },
            //添加属性
            addCateSkuNamesItem(){
              this.cateSkuNames.push(this.cateSkuNamesItem);
                this.cateSkuNamesItem = [];
            },
            getList(page) {
                this.listParams.page = page;
                this.api.template.queryTemplateCategoryList(this.listParams).then((res) => {
                    this.listData = res.data.dataList;
                    this.total = res.data.total;
                });
            },
            openAddModal(item) {
                this.modalParams.parentId = 0;
                this.modalParams.depth = 1;
                if(item.id){
                    this.modalParams.id = item.id;
                    this.modalParams.cateDesc = item.cateDesc || '';
                    this.modalParams.cateName = item.cateName || '';
                    this.modalParams.type = item.type || '';
                    this.cateSkuNames = item.cateSkuNames || [];
                } else {
                    this.cateSkuNames = [];
                    this.modalParams = {
                        cateName: '',
                        cateDesc: '',
                    };
                }
                this.addShow = true;
            },
            saveCate() {
                if(!this.modalParams.cateName) {
                    this.$message.info({message:'请输入类目名称', showClose:true});
                    return
                }
                if(this.modalParams.id){
                    this.api.template.updateTemplateCategory({
                        ...this.modalParams,
                        cateSkuNames:this.cateSkuNames
                    }).then((res) => {
                        this.$message.success({message:res.message, showClose:true});
                        this.addShow = false;
                        this.getList(1);
                    });
                } else {
                    this.api.template.addTemplateCategory({
                        ...this.modalParams,
                        cateSkuNames:this.cateSkuNames
                    }).then((res) => {
                        this.$message.success({message:res.message, showClose:true});
                        this.addShow = false;
                        this.getList(1);
                    });
                }
            },
            uploadSuccess(res) {
                this.modalParams.logoUrl = res.data;
            }
        },
        components: { Pagination, noData }
    }
</script>
<style scoped lang='scss'>
    .center-first-category{
        background: $white-color;
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
        .dialog-form{
            /*padding-left:10px;*/
            padding-left: 0;
            box-sizing: border-box;
            .form-name{
                vertical-align: top;
                line-height: 30px;
                width: auto;
            }
            .upload-wrap{
                .cateImg{
                    max-width: 100px;
                    max-height: 100px;
                }
                .addCateImg{
                    display: inline-block;
                    vertical-align: top;
                    line-height: 30px;
                    margin-left: 10px;
                }

            }
            .form-control{
                width: 514px;
            }
        }
        .btn-org-main{
            background: #E1376C;
        }
    }
    .addDescBtnBox{
        margin-left: 66px;
    }
    .addDescBtn{
        display: inline-block;
        height:22px;
        background:rgba(225,55,108,1);
        border-radius:100px;
        color: #ffffff;
        box-sizing: border-box;
        line-height: 22px;
        cursor: pointer;
        padding: 0 5px;
        text-align: center;
    }
    .addDescBtn1{
        width:84px;
        height:30px;
        border-radius:100px;
        line-height: 30px;
        text-align: center;
        color: #E1376C;
        display: inline-block;
        cursor: pointer;
        border:1px solid rgba(225,55,108,1);
    }

</style>
