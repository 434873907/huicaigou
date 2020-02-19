<template>
    <div class="send-coupon">
            <h2 class="coupon-title">发放优惠券</h2>
            <div class="coupon-box">
                <div class="coupon">
                    <span class="title">当前优惠劵：{{name}}</span>
                </div>
                <div class="coupon choice-select">
                    <span class="title">请加入用户：</span>
                    <el-radio-group v-model="choiceType" @change="choiceSelect">
                        <el-radio :label="0">全部用户</el-radio>
                        <el-radio :label="1">部分用户</el-radio>
                    </el-radio-group>
                </div>
            </div>
            <div class="coupon-titles" v-if="choiceType == 1"><span class="num">已选用户（{{saveChoiceList.length}}）</span></div>
            <div class="choice-user" v-if="choiceType == 1">
                    <div class="taps">
                        <span class="num" v-if="false">选择用户</span>
                    </div>
                    <div class="search-bar">
                        <span class="search-bar-title">一般筛选:</span>
                        <span class="normal-title">用户信息：</span>
                        <input type="text" class="form-control" placeholder="请输入用户名" v-model="listParams.name" />
                        <a href="javascript:;" class="btn-fill-main" @click="getList()">搜索</a>
                        <a href="javascript:;" class="btn-fill-main" v-if="false">批量加入</a>
                    </div>
                    <table class="table table-text">
                        <thead>
                            <tr>
                            <th class="dot" :class="{'active':checkAll}" @click="selectAll()"></th>
                            <th class="table-w180">用户昵称</th>
                            <th class="table-w120">手机号</th>
                            <th class="table-w100">会员类型</th>
                            <th class="table-w180">注册时间</th>
                            <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(item, i) in tableData" :key="i" @click="selectItem(item, i)">
                            <td class="dot" :class="{'active':item.checked}" ></td>
                            <td>
                               {{item.userName}}
                            </td>
                            <td>{{item.userPhone}}</td>
                            <td>{{item.userType}}</td>
                            <td>{{item.registerTime}}</td>
                            <td>
                            </td>
                            </tr>
                        </tbody>
                        </table>
                        <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList()" />
                        <noData v-if="total===0"></noData>

            </div>
             <div class="control-box">
                            <a href="javascript:;" class="btn-fill-main" @click="cancel">取消</a>
                            <a href="javascript:;" class="btn-fill-main btn-main" @click="confirm">确定</a>
              </div>
             <el-dialog title="发劵:" :visible.sync="showSendModal" width="468px" :close-on-click-modal="false">
                <div class="dialog-form export-dialog">
                    <el-progress
                    :text-inside="true"
                    :stroke-width="14"
                    :percentage="sendProgress"
                    color="#F06941"
                    ></el-progress>
                </div>
                <span slot="footer" class="dialog-footer">
                    <button class="btn-fill-grey-main" @click="showSendModal=false">取消</button>
                    <button  class="btn-main"  v-no-more-click @click="sendCoupon" v-show="!sendKey" >开始发劵</button>
                    <a  href="javascript:;" class="btn-main" @click="showSendModal = false, downloadOrder()" v-show="sendKey">点击下载</a>
                    <a href="javascript:;" class="btn-main" v-if="false">导出失败列表</a>
                </span>
            </el-dialog>
    </div>
</template>
<script>
import Pagination from '@/components/pagination';
import noData from '@/components/noData';
export default {
    name: 'sendCoupon',
    components: {
        Pagination,
        noData
    },
    data () {
        return {
            choiceType: 1,
            tableData: [
            ],
            total: 1,
            checkAll: false,
            listParams: {
                page: 1,
                limit: 10,
                name: '',
                auditStatusList: [1]
            },
            name: '',
            id: '',
            showSendModal: false,
            sendProgress: 0,
            sendKey: '',
            saveChoiceList: []
        }
    },
    computed: {
        choiceList () {
            const {tableData} = this
            let list = tableData.filter(r=> {
               return  r.checked == true
            }) || []
            return list || []
        }
    },
    created () {
        this.id = this.$route.params.id || ''
        this.name = this.$route.params.name || ''
        this.getList()
    },
    methods: {
        choiceSelect (val) {
            this.choiceType = val
        },
        getList () {
            const {listParams} = this
            this.checkAll = false
            this.api.consumer.user(listParams).then(r => {
                if (r.success) {
                    let list = r.data && r.data.dataList || []
                    list = list.map(rr => {
                        rr.checked = false
                        return rr
                    }) || []
                    this.tableData = list || []
                    this.doChangeDateList()
                    this.total = r.data.total || 0
                } else {
                    this.toast(r.message)
                }
            })
        },
        selectAll () {
            this.checkAll = !this.checkAll
            let {tableData} = this
            tableData = tableData.map(r => {
                if (this.checkAll ) {
                   r.checked = true
                } else {
                    r.checked = false
                }
                return r
            }) || []
            this.tableData = tableData
            this.doControlAll(this.checkAll)
        },
        // 全选操作
        doControlAll (status = true) {
            let {saveChoiceList, tableData} = this
            if (status) {
                for (let k=0; k< tableData.length; k++) {
                    let status = false
                    for (let i=0; i < saveChoiceList.length; i++) {
                        if (saveChoiceList[i].userId == tableData[k].userId) {
                             status = true
                        }
                    }
                    if (!status) saveChoiceList.push(tableData[k])
                }
                this.saveChoiceList = saveChoiceList
            } else {
                let arr = []
                for (let k=0; k< saveChoiceList.length; k++) {
                    let status = false
                    for (let i=0; i < tableData.length; i++) {
                        if (tableData[i].userId == saveChoiceList[k].userId) {
                             status = true
                        }
                    }
                    if (!status) arr.push(saveChoiceList[k])
                }
                this.saveChoiceList = arr
            }
        },
        // 对翻页加载的新数据进行处理
        doChangeDateList () {
            let {saveChoiceList, tableData} = this
            if (saveChoiceList.length) {
                for (let k = 0; k< tableData.length ; k++) {
                    let status = false
                    for (let i=0; i < saveChoiceList.length; i++) {
                        if (saveChoiceList[i].userId ==  tableData[k].userId) {
                            status = true
                        }
                    }
                    if (status) {
                        tableData[k].checked = true
                    } else {
                        tableData[k].checked = false
                    }
                }
                this.tableData = tableData
            } else {
                this.tableData = tableData
            }
        },
        selectItem (item, i) {
            item.checked = !item.checked || false
            this.$set(this.tableData, i, item)
            let {saveChoiceList} = this
            let arr = []
            if (saveChoiceList.length) {
                let status = false
                for (let k = 0; k<saveChoiceList.length; k++) {
                    if (saveChoiceList[k].userId == item.userId) {
                        status = true
                    } else {
                        arr.push(saveChoiceList[k])
                    }
                }
                if (!status) {
                    arr.push(item)
                }
            } else {
                if (item.checked) {
                    arr.push(item)
                }
            }
            this.saveChoiceList = arr
        },
        cancel () {
            this.$router.go(-1)
        },
        confirm () {
            const {choiceType, saveChoiceList} = this
            if (choiceType == 0) {
                this.showSendModal = true
            } else {
                if (saveChoiceList.length == 0) {
                    this.$message.error('请选择用户')
                    return
                } else {
                    this.showSendModal = true
                }
            }
        },
        sendCoupon () {
            const {choiceType, saveChoiceList, id} = this
            let param = {}
            if (choiceType == 0) {
                param = {
                    couponConfigId: id,
                    selectType: 1
                }
            } else {
                let arr = saveChoiceList.map(r => {
                    let item = r.userId
                    return item
                }) || []
                param = {
                    couponConfigId: id,
                    selectType: 2,
                    userIds: String(arr)
                }
            }
            this.api.coupon.sendCoupon(param).then(res => {
                if (res.success) {
                    let key = res.data
                    let exportTimer = setInterval(() => {
                    this.api.progress.rate({data: key}).then((res) => {
                        if(res.success) {
                            if(res.data.finished) {
                                this.sendProgress = res.data.percent;
                                this.sendKey = key;
                                clearInterval(exportTimer);
                            } else {
                                this.sendProgress = res.data.percent;
                            }
                        } else {
                            this.$message.info({message:res.message, showClose:true});
                            this.sendProgress = 0;
                            clearInterval(exportTimer);
                        }
                    });
                    }, 1000);
                } else {
                    this.$message.info(res.message)
                }
            })
        },
        downloadOrder() {
            window.open('/common/progress_result.do?data='+this.sendKey, '_blank');
        }
    },
    watch: {
        'showSendModal' (val) {
            if (!val) {
                this.sendKey = ''
                this.sendProgress = 0
            }
        }
    }
}
</script>
<style lang="scss" scoped>
.send-coupon {
    height: 100%;
    .coupon-title {
        padding-bottom: 10px;
        color: #333;
        font-weight: bold;
    }
    .coupon-box {
        background: #fff;
        padding: 20px;
        .coupon {
            height: 40px;
            line-height: 40px;
        }
    }
    .choice-user {
        padding: 20px;
        background: #fff;
        .search-bar{
            span{
            display: inline-block;
            vertical-align: top;
            line-height: 30px;
            }
            .normal-title{
            color: #333333;
            font-size: 14px;
            padding-right: 10px;
            }
            .form-control{
            margin-right: 20px;
            }
            .btn-fill-main{
            margin-right: 10px;
            }
        }
    }
    .control-box {
        text-align: center;
        background: #fff;
        padding: 20px 0;
        .btn-main {
            background: #E1376C;
            color: #fff;
            margin-left: 30px;
        }
    }
}
.warn-msg {
    float: right;
    padding-top: 10px;
}
.coupon-titles {
    margin: 10px 0;
}
tbody {
    tr {
        cursor: pointer;
    }
}
</style>
