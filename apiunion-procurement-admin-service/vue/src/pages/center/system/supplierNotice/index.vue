<template>
    <div class="center-system-notice">
        <div class="search-bar">
            <div class="search-bar-title">一般筛选：</div>
            <input type="text" placeholder="请输入公告信息进行搜索" class="form-control" v-model="listParams.keyword" @keyup.enter="getList(1)"/>
            <span class="text text-time">发布时间</span>
            <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择" v-model="listParams.beginTime"></el-date-picker>
            <span class="time-mid">－</span>
            <el-date-picker class="timepicker-control" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="请选择" v-model="listParams.endTime"></el-date-picker>
            <a class="btn-fill-main" @click="getList(1)">搜索</a>
            <router-link :to="{ name: '新增公告'}" class="btn-main">新增公告</router-link>
        </div>
        <table class="table table-text">
            <thead>
            <tr>
                <th class="table-w80">序号</th>
                <th class="table-w200">标题</th>
                <th class="table-w300">内容</th>
                <th class="table-w170">发布时间</th>
                <th class="table-w100">发布人</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item,index) in tableData" :key="item.id">
                <td>{{index+1}}</td>
                <td>
                    <el-popover
                            placement="top"
                            width="260"
                            trigger="hover">
                        <span>{{item.title}}</span>
                        <span class="ellipsis" slot="reference">{{item.title}}</span>
                    </el-popover>
                </td>
                <td>
                    <el-popover
                            placement="top"
                            width="260"
                            trigger="hover">
                        <span>{{item.content}}</span>
                        <span class="ellipsis" slot="reference">{{item.content}}</span>
                    </el-popover>
                </td>
                <td>{{item.createTime}}</td>
                <td>
                    {{item.creator}}
                </td>
                <td>
                    <a class="link" @click="editNotice(item)">查看详情</a>
                    <a class="link" @click="delItem(item)">删除</a>
                </td>
            </tr>
            </tbody>
        </table>
        <pagination v-show="total>0" :total="total" :page.sync="listParams.page" :limit.sync="listParams.limit" @pagination="getList(listParams.page)" />
        <noData v-if="total===0"></noData>
    </div>
</template>
<script>
    import Pagination from '@/components/pagination';
    import noData from '@/components/noData';
    export default {
        data() {
            return {
                total: -1,
                typeList: [],
                listParams: {
                    page: 1,
                    limit: 10,
                    keyword: '',
                    beginTime: '',
                    endTime: ''
                },
                tableData: []
            }
        },
        created() {
            this.getList(1);
        },
        methods: {
            getList(page) {
                this.listParams.page = page;
                this.api.supplier.queryAnnouncementList(this.listParams).then((res) => {
                    this.tableData = res.data.dataList;
                    this.total = res.data.total;
                });
            },
            editNotice(item) {
                this.$router.push({
                    path:"supplierNoticeDetail",
                    query:{
                        id:item.id,
                        name:'查看公告'
                    },
                })
            },
            delItem(item) {
                this.$msgbox({
                    title: '',
                    message: '确认删除？',
                    showCancelButton: true,
                    confirmButtonText: '确定',
                    cancelButtonText: '取消'
                }).then(action => {
                    if(action === 'confirm') {
                        this.api.supplier.deleteAnnouncement({id: item.id}).then((res) => {
                            this.$message.success({message:res.message, showClose:true});
                            this.getList(1);
                        });
                    }
                });
            }
        },
        components: {
            'Pagination': Pagination,
            'noData': noData
        }
    }
</script>
<style lang='scss'>
    .center-system-notice{
        background: #fff;
        .search-bar{
            .text{
                margin-right: 10px;
            }
            .text-time{
                margin-left: 20px;
            }
            .time-mid{
                padding: 0 10px;
            }
            .form-control{
                width: 160px;
            }
            .el-input, .el-input__inner{
                height: 30px;
                // width: 160px;
            }
            .el-input__icon{
                line-height: 30px;
            }
            .btn-fill-main, .btn-main{
                margin-left: 20px;
            }
        }
        .table{
            .link{
                margin-right: 10px;
            }
        }
    }
</style>
