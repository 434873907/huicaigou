<template>
    <div class="agreement-detail">
        <div class="editor-wrap" v-if="!id">
            <div class="form-wrap">
                <div class="form-name">协议名称:</div>
                <el-input v-model="title" placeholder="请输入内容"></el-input>
                <span>(限制25字符)</span>
            </div>
            <editor v-model="content"></editor>
            <div>
                <div class="save-inner">
                    <a href="javascript:;" class="btn-main" @click="saveAgreement()">保存</a>
                    <router-link :to="{ path: 'supplierNotice'}" class="btn-fill-main">返回</router-link>
                </div>
            </div>
        </div>
        <div v-if="!!id" class="contentBox">
            <div v-html="content">

            </div>
        </div>
    </div>
</template>

<script>
    import editor from '@/components/tinymce/tinymce.vue';
    export default {
        components: {editor},
        data() {
            return {
                id: '',
                title:"",
                content:''
            }
        },
        created() {
            this.id = this.$route.query.id || '';
            if(this.id) {
                this.getAgreement();
            }
        },
        methods: {
            getAgreement() {
                this.api.supplier.getAnnouncement({id: this.id}).then((res) => {
                    this.content = res.data.content;
                });
            },
            saveAgreement() {
                let params = {
                    title: this.title,
                    content: this.content
                };
                this.api.supplier.addAnnouncement(params).then((res) => {
                    this.$message.success({message:res.message, showClose:true});
                    this.$router.push({path: 'supplierNotice'});
                });
            }
        }
    }
</script>

<style lang="scss" scoped>
    .agreement-detail{
        // padding-right: 260px;
        background: #ffffff;
        padding-bottom: 50px;
        position: relative;
        .agreement-title{
            color: #333333;
            font-size: 14px;
            font-weight: bold;
            padding-bottom: 10px;
        }

        .editor-wrap{
            padding: 20px;
            background: #fff;
            border-radius: 4px;
            .form-wrap{
                display: flex;
                align-items: center;
                margin-bottom: 20px;
                .form-name{
                    display: inline-block;
                    vertical-align: top;
                    width: 70px;
                    line-height: 30px;
                }
                span{
                    display: inline-block;
                    vertical-align: top;
                    line-height: 30px;
                    font-weight: bold;
                }
                .link{
                    display: inline-block;
                    vertical-align: top;
                    line-height: 30px;
                    margin-right: 15px;
                }
            }
            .form-last{
                padding-bottom: 40px;
            }
        }
    }
    .save-inner{
        margin-top: 20px;
    }
    .contentBox{
        background: white;
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
        box-sizing: border-box;
        padding: 30px;
    }
    .el-input{
        width: auto;
    }
</style>
