<template>
  <el-dialog
    :title="!dataForm.commentId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="姓名" prop="userRealName">
      <el-input v-model="dataForm.userRealName" placeholder="姓名"></el-input>
    </el-form-item>
    <el-form-item label="昵称" prop="userNickname">
      <el-input v-model="dataForm.userNickname" placeholder="昵称"></el-input>
    </el-form-item>
    <el-form-item label="电话号码" prop="telephone">
      <el-input v-model="dataForm.telephone" placeholder="电话号码"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="userEmail">
      <el-input v-model="dataForm.userEmail" placeholder="邮箱"></el-input>
    </el-form-item>
    <el-form-item label="角色" prop="roleName">
      <el-input v-model="dataForm.roleName" placeholder="角色"></el-input>
    </el-form-item>
    
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import ImgUpload from '@/components/web/upload/ImgUpload';
import { getUserById } from '@/api/user';
export default {
  components: {
    ImgUpload
  },
  data () {
    return {
      visible: false,
      dataForm: {
        id: '',
        userRealName: '',
        userNickname: '',
        telephone: '',
        userEmail: '',
        roleName: '',
        userPwd: '',
      },
    }
  },
  methods: {
    // 文件上传
    avatarUpload(resp) {
      this.dataForm.userAvatar = resp.data;
    },
    init (id) {
      this.dataForm.id = id || 0
      console.log(this.dataForm.id)
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id != 0) {
          // Todo axios
          getUserById(this.dataForm.id).then(resp => {
            console.log(resp);
            this.dataForm = resp.data;
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // Todo axios
        }
      })
    }
  }
}
</script>


<style lang="scss" scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
