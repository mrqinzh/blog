<template>
  <el-dialog
    :title="!dataForm.commentId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="标签名称" prop="tagName">
      <el-input v-model="dataForm.tagName" placeholder="标签名称"></el-input>
    </el-form-item>
    <el-form-item label="标签图" prop="tagName">
      <el-upload
        class="tag-uploader"
        action=""
        :show-file-list="false">
        <img v-if="dataForm.tagImg" :src="dataForm.tagImg" class="tag">
        <i v-else class="el-icon-plus tag-uploader-icon"></i>
      </el-upload>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { getById } from '@/api/tag'
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          tagName: '',
          tagImg: '',
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            getById(id).then(resp => {
              // console.log(resp);
              this.dataForm.tagName = resp.data.tagName;
              this.dataForm.tagImg = resp.data.tagImg;
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        
      }
    }
  }
</script>

<style lang="scss" scoped>

.tag-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  &:hover {
    border-color: #409EFF;
  }
}

.tag-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.tag {
  width: 178px;
  height: 178px;
  display: block;
}

</style>