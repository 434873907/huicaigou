<template>
    <div class="import-upload">
        <multiple-file-uploader
            :postURL="postUrl"
            successMessagePath
            errorMessagePath
            @upload-success="successHandler"
            ref="multipleUpload"
            :post-data="{'type':importLogRadio}"
            dropAreaSecondaryMessage="上传价格文件"
          ></multiple-file-uploader>
          <button class="btn-main" @click="importPrice">导入</button>
    </div>
</template>
<script>
import MultipleFileUploader from "@/components/multipleFileUploader/MultipleFileUploader.vue";
export default {
    name: 'upload',
    components: {
        MultipleFileUploader
    },
    data () {
        return {
            postUrl: '/item/upload_item_price.do',
            importLogRadio: 1
        }
    },
    methods: {
        successHandler (resultMsg) {

            if (resultMsg.success) {
            }
        },
        importPrice() {
            this.$refs.multipleUpload.onSubmit()
        }
    }
}
</script>
<style lang="scss" scoped>
.import-upload {
    background: #fff;
    overflow: hidden;
    button {
        margin: 10px;
    }
}
</style>
