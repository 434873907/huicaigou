<template>
    <div class="invitation-code">
        <div class="list-wrap">
                <div class="search-bar">
                    <div class="search-bar-title">一般筛选：</div>
                    <div class="form-wrap">
                    <div class="form-name">关键字信息</div>
                    <input type="text" style="width:300px;" placeholder="请输入创建人、邀请码信息" v-model="listParams.keyword" class="form-control" @keyup.enter="getList(1)">
                    <div class="form-name">邀请码类型</div>
                    <select class="form-control" v-model="listParams.type" :class="{'unset-select':!listParams.type}">
                        <option value="">请选择邀请码类型</option>
                        <option v-for="(item, i) in codeTypeList" :key="i" :value="item.type">{{item.remark}}</option>
                    </select>
                    <div class="form-name">匹配方式</div>
                    <select class="form-control" v-model="listParams.matchType" :class="{'unset-select':!listParams.type}">
                        <option value="">请选择匹配方式</option>
                        <option v-for="(item,i) in matchList" :key="i" :value="item.type">{{item.remark}}</option>
                    </select>
                    <div class="form-name">状态</div>
                    <select class="form-control" v-model="listParams.status" :class="{'unset-select':!listParams.status}">
                        <option value="">请选择状态</option>
                        <option v-for="(item, i) in statusList" :key="i" :value="item.type">{{item.name}}</option>
                    </select>
                    </div>
                    <button class="btn-fill-main" @click="getList(1)">搜索</button>
                    <button class="btn-fill-main" @click="reset()">清空</button>
                    <button class="btn-main" @click="createdinvitation" >生成邀请码</button>
                </div>
                <table class="table table-text">
                    <thead>
                    <tr>
                        <th class="table-w80">序号</th>
                        <th class="table-w100">邀请码</th>
                        <th class="table-w200">邀请码说明</th>
                        <th class="table-w100">邀请码类型</th>
                        <th class="table-w100">匹配方式</th>
                        <th class="table-w120">可注册用户总数</th>          
                        <th class="table-w100">已注册用户</th>  
                        <th class="table-w80">状态</th>    
                        <th class="table-w170">生成时间</th>    
                        <th class="table-w80">创建人</th>        
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item, i) in tableData" :key="i">
                        <td>{{parseInt(total - (listParams.page-1) * listParams.limit - i) || 0}}</td>
                        <td>
                            {{item.code}}
                        </td>
                        <td class="code-desc">
                            <el-popover
                            ref="popover4"
                            placement="top"
                            width="250"
                            trigger="hover"
                            :content="item.desc"
                            >
                            <span slot="reference">
                                {{item.desc}}
                            </span>
                            </el-popover>

                        </td>
                        <td>{{getCodeTypeName(item.type)}}</td>
                        <td >{{getMathcName(item.matchType)}}</td>
                        <td>
                        {{item.num}}
                        </td>
                        <td>{{item.usedNum}}</td>
                        <td>
                        <span v-show="item.status">启用</span>
                        <span v-show="!item.status">禁用</span>
                        </td>
                        <td>
                            {{item.createTime}}
                        </td>
                        <td class="elits">
                            <el-popover
                            ref="popover4"
                            placement="top"
                            width="250"
                            trigger="hover"
                            :content="item.createUser"
                            >
                            <span slot="reference">
                                {{item.createUser}}
                            </span>
                            </el-popover>
                        </td>
                        <td>
                        <a class="link" @click="goSeeDetail(item)">查看</a>
                        <a class="link" v-show="!item.status" @click="setDisable(item, 1)">启用</a>
                        <a class="link" v-show="item.status" @click="setDisable(item, 0)">禁用</a>
                </td>
                </tr>
                </tbody>
                </table>
                <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
                <noData v-if="total===0"></noData>
            </div>
    </div>
</template>
<script>
import Pagination from '@/components/pagination'
import noData from '@/components/noData'
export default {
    name: 'invitationCode',
    components: {
        Pagination,
        noData
    },
    data () {
        return {
            listParams: {
                type: '',
                matchType: '',
                keyword: '',
                status: '',
                limit: 10,
                page: 1
            },
            levelList: [],
            tableData: [],
            matchList: [],
            codeTypeList: [],
            statusList: [
                {
                    type: 0,
                    name: '禁用'
                },
                {
                    type: 1,
                    name: '启用'
                }
            ],
            total: 0
        }
    },
    created () {
        this.getMatchList()
        this.getCodeTypeList()
        this.getList()
    },
    methods: {
        getList (page) {
            let param = {}
            if (page) {
                this.listParams.page = page
            }
            const {listParams} = this
            this.api.invitationcode.list(listParams).then(r => {
                if (r.success) {
                    this.tableData = r.data && r.data.dataList || []
                    this.total = r.data && r.data.total || 0
                } else {
                    this.$message.info(r.message)
                }
            })
        },
        // 获取匹配列表
        getMatchList () {
            this.api.invitationcode.matchTypeList().then(r => {
                if (r.success) {
                    this.matchList = r.data || []
                }
            })
        },
        // 获取验证码类型列表
        getCodeTypeList () {
            this.api.invitationcode.getCodeTypeList().then(r => {
                if (r.success) {
                    this.codeTypeList = r.data || []
                }
            })
        },
        // 重置， 清空
        reset () {
            this.listParams = {
                type: '',
                matchType: '',
                keyword: '',
                status: '',
                limit: 10,
                page: 1
            }
            this.getList(1)
        },
        // 设置启用、禁用
        setDisable (item, status) {
            const {id} = item
            this.api.invitationcode.changeStatus({id, status}).then(r => {
                if (r.success) {
                    this.$message.success(r.message)
                    setTimeout(() => {
                        this.getList()
                    }, 1000)
                } else {
                    this.$message.info(r.message)
                }
            })
        },
        getCodeTypeName (type) {
            const {codeTypeList} = this
            let remark = ''
            for (let k = 0; k < codeTypeList.length; k++) {
                if (codeTypeList[k].type == type) {
                    remark = codeTypeList[k].remark || ''
                    break
                }
            }
            return remark
        },
        getMathcName (type) {
            const {matchList} = this
            let remark = ''
            for (let k = 0; k < matchList.length; k++) {
                if (matchList[k].type == type) {
                    remark = matchList[k].remark || ''
                    break
                }
            }
            return remark
        },
        goSeeDetail (item) {
            const {id} = item
            localStorage.setItem('currentInvitationId', id)
            this.$router.push({
                path: `/center/user/createdinvitation`,
            })
        },
        createdinvitation () {
            if (localStorage.getItem('currentInvitationId')) {
                localStorage.removeItem('currentInvitationId')
            }
            this.$router.push({
                path: `/center/user/createdinvitation`,
            })
        }
    }
}
</script>
<style lang="scss" scoped>
.invitation-code {
    .search-bar {
        padding-top: 0;
        .search-bar-title {
            padding-top: 10px;
        }
        .form-wrap {
            .form-control {
                margin-top: 10px;
            }
        }
        button {
            margin: 10px 10px 0 0;
        }
    }
    .link {
        margin-right: 10px;
    }
    .code-desc {
        width: 100%;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
}
/deep/.elits {
    overflow: hidden;
    white-space: nowrap;
    width: 100%;
    text-overflow: ellipsis;
}
</style>