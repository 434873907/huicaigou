import { getTeamApplyWorderForceman } from '@/api/teamManage';
import { goChoiceWorkerForceman } from '@/js/appWebviewFunction';
<template>
    <div class="created-invitation">
        <div class="wrap">
            <div class="form-wrap">
                <div class="form-name">当前创建人</div>
                <input type="text"  v-model="nickName" readonly="readonly" class="form-control" disabled="disabled"><span class="warn-msg">(已默认为当前登录管理员姓名)</span>
            </div>
            <div class="form-wrap">
                <div class="form-name">邀请码类型</div>
                <el-radio-group v-model="form.type" :disabled="!!id">
                    <el-radio :label="item.type" v-for="(item, i) in codeTypeList" :key="i">{{item.remark}}</el-radio>
                </el-radio-group>
            </div>
            <div class="form-wrap">
                <div class="form-name" style="vertical-align: top;">匹配方式</div>
                <el-radio-group v-model="form.matchType" :disabled="!!id" class="special">
                    <el-radio :label="item.type" v-for="(item, i) in matchList" :key="i" >{{item.remark}} <span class="warn">{{nameArray[i] || ''}}</span></el-radio>
                </el-radio-group>
            </div>
            <div class="form-wrap" v-if="form.matchType == 2">
                <div class="form-name">可用数量</div>
                <input type="number" :disabled="!!id" v-model="form.availableNum" min="2" max="99" class="form-control" placeholder="请输入可用数量"> <span class="warn-msg">1个邀请码可使用被注册的总量，默认最小为2，最大限制99）</span>
            </div>
            <div class="form-wrap" v-if="!id">
                <div class="form-name">生成数量</div>
                <input type="number" :disabled="!!id" v-model="form.generateNum" :min="1" :max="99" class="form-control" placeholder="请输入生成数量"> <span class="warn-msg">（最小为1，最大限制99）</span>
            </div>
            <div class="form-wrap">
                <div class="form-name special">邀请码说明</div>
                <textarea :disabled="!!id" class="form-control" v-model="form.desc" placeholder="可填写邀请码备注信息（限制15字符）" maxlength="15"></textarea>
                <span class="warn-num">{{form.desc && form.desc.length || 0}}/15</span>
            </div>
            <div class="form-wrap">
                <div class="form-name">邀请码状态</div>
                <el-radio-group v-model="form.status">
                    <el-radio :label="1">启用</el-radio>
                    <el-radio :label="0">禁用</el-radio>
                </el-radio-group>
            </div>
            <button class="btn-main" @click="save">保存</button>
            <button class="btn-fill-main special" @click="$router.go(-1)">取消</button>
        </div>
    </div>
</template>
<script>
export default {
    name: 'createdInvitation',
    data () {
        return {
            form: {
                type: '',
                matchType: '',
                availableNum: 2,
                generateNum: '',
                status: 1,
                desc: ''
            },
            matchList: [],
            codeTypeList: [],
            nickName: '',
            id: '',
            nameArray: ['（1个邀请码只可邀请一个用户注册使用）', '（1个邀请码可邀请多个用户注册使用）']
        }
    },
    created () {
        this.getMatchList()
        this.getCodeTypeList()
        this.nickName = localStorage.getItem('user') && JSON.parse(localStorage.getItem('user')).nickName || ''
        if (localStorage.getItem('currentInvitationId')) {
             let id = localStorage.getItem('currentInvitationId') || ''
             this.id = id
             this.getInfo(id)
        }
    },
    methods: {
         // 获取匹配列表
        getMatchList () {
            this.api.invitationcode.matchTypeList().then(r => {
                if (r.success) {
                    this.matchList = r.data || []
                    this.form.matchType = this.matchList[0] && this.matchList[0].type || 1
                }
            })
        },
        // 获取验证码类型列表
        getCodeTypeList () {
            this.api.invitationcode.getCodeTypeList().then(r => {
                if (r.success) {
                    this.codeTypeList = r.data || []
                    this.form.type = this.codeTypeList[0] && this.codeTypeList[0].type || 1
                }
            })
        },
        // 保存
        save () {
            const {form, form: {type, matchType, availableNum, generateNum, status, desc}, id} = this
            if (!id) {
                if (type == '') {
                    this.$message.error('请选择邀请码类型')
                    return
                }
                if (matchType == '') {
                    this.$message.error('请选择匹配方式')
                    return
                }
                if (availableNum == '') {
                    this.$message.error('请输入邀请码可用数量')
                    return
                }
                if (availableNum > 99 || availableNum < 2) {
                    this.$message.error('可用数量在2-99之间')
                    return
                }
                if (generateNum == '') {
                    this.$message.error('请输入邀请码生成数量')
                    return
                }
                if (generateNum > 99 || generateNum < 1) {
                    this.$message.error('生成数量在1-99之间')
                    return
                }
                if (desc && desc.length > 15) {
                    this.$message.error('备注信息不得超过15字符')
                    return
                }
                this.api.invitationcode.add(form).then(r => {
                    if (r.success) {
                        this.$message.success(r.data)
                        setTimeout(() => {
                            this.$router.replace({
                                path: '/center/user/invitationcode'
                            })
                        }, 1000)
                    } else {
                        this.$message.info(r.message)
                    }
                })
            } else {
                this.api.invitationcode.changeStatus({id, status}).then(r => {
                    if (r.success) {
                        this.$message.success(r.message)
                        setTimeout(() => {
                            this.$router.go(-1)
                        }, 1000)
                    } else {
                        this.$message.info(r.message)
                    }
                })
            }
        },
        getInfo (id) {
            this.api.invitationcode.getDetail({id}).then(r => {
                if (r.success) {
                    this.form = r.data
                    const {desc, createUser, matchType, type, num, status} = r.data
                    this.form = {
                        availableNum: num,
                        desc,
                        matchType,
                        type,
                        status
                    }
                    this.nickName = createUser
                } else {
                    this.$message.info(r.message)
                }
            })
        }
    }
}
</script>
<style lang="scss" scoped>
.created-invitation {
    background: #fff;
    overflow: hidden;
    min-height: 530px;
    .wrap {
        padding: 15px 20px 0;
        background: #fff;
        border-radius: 4px;
        .form-wrap {
            padding-bottom: 20px;
            position: relative;
            select {
                &.form-control {
                    width: 160px;
                }
            }
            .warn-msg {
                margin-left: 15px;
                color: #666;
                font-size: 14px;
            }
            .form-name {
                width: 73px;
                text-align: right;
                &.special {
                    vertical-align: top;
                    position: relative;
                    top: 8px;
                }
            }
            textarea {
                width: 748px;
                height: 78px;
            }
            .warn-num {
                position: absolute;
                top: 72px;
                left: 817px;
                color: #999999;
                font-size: 12px;
            }
            input {
                &.form-control {
                    width: 240px;
                }
            }
            .el-radio-group {
                &.special {
                    .el-radio {
                        height: 20px;
                        display: block;
                        margin-bottom: 20px;
                        margin-left: 0;
                        &:last-child {
                            margin-bottom: 0;
                        }
                        .warn {
                            color: #F7316E;
                            font-size: 14px;
                        }
                    }
                }
            }
        }
        button {
            margin-right: 10px;
            &.special {
                color: #333;
                border-color: rgba(204, 204, 204, 0.6);
            }
        }
    }
}
</style>