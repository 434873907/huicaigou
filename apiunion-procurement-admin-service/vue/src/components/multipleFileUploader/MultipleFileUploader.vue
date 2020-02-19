<template>
    <div class="uploadBox">
        <form role="form" enctype="multipart/form-data" @submit.prevent="onSubmit">
            <div class="uploadBoxMain" v-if="!itemsAdded">
                <div class="form-group">
                    <div class="dropArea" @ondragover="onChange" :class="dragging ? 'dropAreaDragging' : ''" @dragenter="dragging=true" @dragend="dragging=false" @dragleave="dragging=false">
                        <input type="file" id="items" name="items[]" required multiple @change="onChange">
                        <p class="help-block">{{dropAreaSecondaryMessage}}</p>
                    </div>
                </div>
            </div>
            <div class="uploadBoxMain" v-else>
                <p class="fileNameMessage">{{fileNameMessage}}</p>
                <ol>
                    <li v-for="(name, index) in itemsNames" :key="index+'name'" class="fileItem">{{name}}</li>
                </ol>
                <!-- <p>{{fileSizeMessage}}</p> -->
                <!-- <ol>
                    <li v-for="(size, index) in itemsSizes" :key="index+'size'">{{size}}</li>
                </ol> -->
                <p class="totalFileMessage">{{totalFileMessage}} {{itemsAdded}}</p>
                <p class="totalUploadSizeMessage">{{totalUploadSizeMessage}} {{itemsTotalSize}}</p>
                <a @click="removeItems" class="link">{{removeFileMessage}}</a>
                <div class="loader" v-if="isLoaderVisible">
                    <div class="loaderImg"></div>
                </div>
            </div>
            <div v-if="false">
                <button type="submit" class="btn btn-primary btn-black btn-round" :disabled="itemsAdded < minItems || itemsAdded > maxItems">
                    {{uploadButtonMessage}}
                </button>
                <button type="button" class="btn btn-default btn-round" @click="removeItems">{{cancelButtonMessage}}</button>
            </div>
            <div class="successMsg" v-if="successMsg !== ''">{{successMsg}}</div>
            <div class="errorMsg" v-if="errorMsg !== ''">{{fileUploadErrorMessage}}:<br>{{errorMsg}} <br>{{retryErrorMessage}}</div>
            
            <div class="progress-box" v-show="importProgress>0">
                <el-progress :text-inside="true" :stroke-width="14" :percentage="importProgress" color="#F06941"></el-progress>
            </div>
            
        </form>
    </div>
</template>

<script>
require('es6-promise').polyfill();
import axios from 'axios';

export default {
    props: {
        postURL: {
            type: String,
            required: true
        },
        minItems: {
            type: Number,
            default: 1
        },
        maxItems: {
            type: Number,
            default: 999999
        },
        method: {
            type: String,
            default: 'post'
        },
        postMeta: {
            type: [String, Array, Object],
            default: ''
        },
        postData: {
            type: [Object],
            default: () => {}
        },
        postHeader:{
          type: [Object],
          default: () => {}
        },
        successMessagePath: {
            type: String,
            required: true
        },
        errorMessagePath: {
            type: String,
            required: true
        },
        dropAreaSecondaryMessage: {
          type: String,
          default: '上传导入模板'
        },
        fileNameMessage: {
          type: String,
          default: '已选择文件:'
        },
        fileSizeMessage: {
          type: String,
          default: 'Sizes'
        },
        totalFileMessage: {
          type: String,
          default: '已选择文件数:'
        },
        totalUploadSizeMessage: {
          type: String,
          default: '已选择文件大小:'
        },
        removeFileMessage: {
          type: String,
          default: '重新选择'
        },
        uploadButtonMessage: {
          type: String,
          default: 'Upload'
        },
        cancelButtonMessage: {
          type: String,
          default: 'Cancel'
        },
        fileUploadErrorMessage: {
          type: String,
          default: 'An error has occurred'
        },
        minFilesErrorMessage: {
          type: String,
          default: 'Minimum files that need to be added to uploader'
        },
        maxFilesErrorMessage:  {
          type: String,
          default: 'Maximum files that can be added to uploader'
        },
        retryErrorMessage: {
          type: String,
          default: 'Please remove the files and try again.'
        },
        httpMethodErrorMessage: {
          type: String,
          default: "请使用'put'或者'post'方式上传文件"
        },
        showHttpMessages: {
          type: Boolean,
          default: true
        }
    },

    /*
     * The component's data.
     */
    data() {
        return {
            dragging: false,
            items: [],
            itemsAdded: '',
            itemsNames: [],
            itemsSizes: [],
            itemsTotalSize: '',
            formData: '',
            uploadList: [],
            successMsg: '',
            errorMsg: '',
            isLoaderVisible: false,
            importProgress: 0
        }
    },

    methods: {
        // http://scratch99.com/web-development/javascript/convert-bytes-to-mb-kb/
        bytesToSize(bytes) {
            const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
            if (bytes === 0) return 'n/a';
            let i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
            if (i === 0) return bytes + ' ' + sizes[i];
            return (bytes / Math.pow(1024, i)).toFixed(2) + ' ' + sizes[i];
        },

        onChange(e) {
            this.successMsg = '';
            this.errorMsg = '';
            this.formData = new FormData();
            let files = e.target.files || e.dataTransfer.files;
            this.itemsAdded = files.length;
            let fileSizes = 0;
            for (let x in files) {
                if (!isNaN(x)) {
                    this.items = e.target.files[x] || e.dataTransfer.files[x];
                    this.itemsNames[x] = files[x].name;
                    this.itemsSizes[x] = this.bytesToSize(files[x].size);
                    fileSizes += files[x].size;
                    this.formData.append('files', this.items);
                    // this.uploadList.push(this.items);
                }
            }
            this.itemsTotalSize = this.bytesToSize(fileSizes);
        },

        removeItems() {
            this.items = '';
            this.itemsAdded = '';
            this.itemsNames = [];
            this.itemsSizes = [];
            this.itemsTotalSize = '';
            this.dragging = false;
        },

        onSubmit() {
            this.isLoaderVisible = true;
            // this.formData = new FormData();
            if ((typeof this.postMeta === 'string' && this.postMeta !== '') ||
                (typeof this.postMeta === 'object' && Object.keys(this.postMeta).length > 0)) {
                this.formData.append('postMeta', this.postMeta);
            }
            
            if(typeof this.postData ==='object' && this.postData !== null && Object.keys(this.postData).length > 0){
              for(var key in this.postData){
                this.formData.append(key, this.postData[key]);
              }
            }
            // this.formData.append('files[]', this.uploadList);
            if (this.method === 'put' || this.method === 'post' ) {
                axios({method: this.method, url: this.postURL, data: this.formData,headers:this.postHeader})
                    .then((response) => {
                        this.console('response:', response);
                        this.isLoaderVisible = false;
                        // Show success message
                        if(this.showHttpMessages) {
                            this.successMsg = response.data.message;
                        }
                        if(response.data.success) {
                            this.successMsg = '';
                        }
                        this.importProgress = 0;
                        if (response.data.success) {
                        let key = response.data.data;
                        let importTimer = setInterval(() => {
                            this.api.progress.rate({data: key}).then((res) => {
                                if(res.success) {
                                    if(res.data.finished) {
                                        this.importProgress = res.data.percent;
                                        clearInterval(importTimer);
                                        this.api.progress.result({data: key}).then((result_res) => {
                                            this.importProgress = 0;
                                            this.$message.success({message:result_res.message, showClose:true});
                                            this.removeItems();
                                            this.$emit('upload-success', result_res);
                                        });
                                    } else {
                                        this.importProgress = res.data.percent;
                                    }
                                } else {
                                    this.$message.info({message:res.message, showClose:true});
                                    this.importProgress = 0;
                                    clearInterval(importTimer);
                                }
                            });
                        }, 1000);
                        } else {
                            this.$message.info(this.successMsg)
                        }
                        // this.removeItems();
                        // this.$emit('upload-success', response);
                    })
                    .catch((error) => {
                        this.isLoaderVisible = false;
                        if(this.showHttpMessages) {
                            this.errorMsg = error.data.message;
                        }
                        this.removeItems();
                        this.$emit('upload-error', error);
                    });
            } else {
                if(this.showHttpMessages) {
                    this.errorMsg = this.httpMethodErrorMessage;
                }
                this.removeItems();
            }
        },
    }
}
</script>

<style lang="scss">
.uploadBox {
    position: relative;
    background: #f8f8f8;
    padding: 10px;
}

.uploadBox h3 {
    padding-top: 1em;
}

.uploadBox .uploadBoxMain {
    position: relative;
}

.uploadBox .uploadBoxMain .fileNameMessage{
    color: #333;
    font-size: 14px;
    font-weight: bold;
    padding-bottom: 5px;
}
.uploadBox .uploadBoxMain .fileItem {
    color: #333;
    font-size: 14px;
    padding-top: 5px;
}
.uploadBox .uploadBoxMain .totalFileMessage {
    padding: 10px 0;
    font-weight: bold;
}
.uploadBox .uploadBoxMain .totalUploadSizeMessage {
    padding-bottom: 10px;
    font-weight: bold;
}

/* Drag and drop */
.uploadBox .dropArea {
    position: relative;
    width: 100px;
    height: 30px;
    border: 1px dashed #F06941;
    text-align: center;
    font-size: 2em;
    border-radius: 15px;
}

.uploadBox .dropArea .help-block {
    font-size: 14px;
    color: #F06941;
    line-height: 30px;
}

.uploadBox .dropArea input {
    position: absolute;
    cursor: pointer;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0;
}
/* End drag and drop */

/* Loader */
.uploadBox .loader {
    position: absolute;
    top: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #fff;
    opacity: 0.9;
}

.uploadBox .loaderImg {
    border: 9px solid #eee;
    border-top: 9px solid #00ADCE;
    border-radius: 50%;
    width: 70px;
    height: 70px;
    animation: spin 1s linear infinite;
}

@keyframes spin {
    0% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(360deg);
    }
}
/* End Loader */

.uploadBox .errorMsg {
    font-size: 14px;
    color: #333;
    margin-top: 10px;
    padding: 10px;
    border: 1px solid $border-color;
    border-radius: 4px;
    background: #fff;
}

.uploadBox .successMsg {
    font-size: 14px;
    color: #333;
    margin-top: 10px;
    padding: 10px;
    border: 1px solid $border-color;
    border-radius: 4px;
    background: #fff;
}

.progress-box{
    padding-top: 10px;;
}

// .el-progress-bar__outer{
//     background-color: #fff;
// }
</style>
