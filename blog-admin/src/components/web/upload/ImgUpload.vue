<template>
  <el-upload
  class="my-uploader"
  :action="baseUrl + '/file/add'"
  :show-file-list="false"
  :data="upLoadData"
  :headers="headers"
  :on-success="upLoadSuccess"
  :before-upload="beforeUpload">
    <img v-if="imageUrl" :src="imageUrl" class="width: 100%;height: 100%;">
    <i v-else class="el-icon-plus my-uploader-icon"></i>
  </el-upload>
</template>

<script>
import store from '@/store';
  export default {
    props: {
      img: '',
    },
    data() {
      return {
        baseUrl: process.env.VUE_APP_BASE_API,

        imageUrl: '',

        upLoadData: {
          file: '',
        },

        headers: {
          token: store.getters.token,
        }
      };
    },
    methods: {
      upLoadSuccess(resp) {
        this.imageUrl = resp.data;
        this.$emit("uploadimg", resp);
        this.$message.success("上传成功");
      },
      beforeUpload(file) {
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isLt2M) {
          this.$message.error('上传图片大小不能超过 2MB!');
        }
        return isLt2M;
      }
    }
  }
</script>


<style>
  .my-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .my-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .my-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
</style>