<template>
  <div class="email-container">
    <el-form :model="emailForm" ref="emailForm" label-width="100px" class="email-form">
      <el-form-item label="邮件主题" prop="emailTitle" :rules="[{ required: true, message: '邮件主题不能为空', trigger: 'blur' }]">
        <el-input v-model="emailForm.emailTitle"></el-input>
      </el-form-item>
      <el-form-item
        prop="to"
        label="收件人"
        :rules="[
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ]"
      >
        <el-input v-model="emailForm.to"></el-input>
      </el-form-item>
      
      <el-form-item label="邮件内容">
        <el-input type="textarea" v-model="emailForm.emailContent"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('emailForm')">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { simpleEmail } from '@/api/email'
export default {
  data() {
    return {
      emailForm: {
        emailTitle: '',
        to: '',
        emailContent: ''
      }
    }
  },
  methods: {
    submitForm(formName) {
      if (this.emailForm.emailContent == '') {
        this.$message.warning('请填写邮件内容');
        return;
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!');
          simpleEmail(this.emailForm).then(resp => {
            // console.log(resp);
            if (resp.success) {
              this.$message.success('邮件发送成功了');
              this.resetForm();
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm() {
      this.$refs['emailForm'].resetFields();
      this.emailForm.emailTitle = '';
      this.emailForm.to = '';
      this.emailForm.emailContent = '';
    },
  }
}
</script>

<style lang="scss" scoped>
.email-container {
  margin: 30px;
  overflow: hidden;
  .email-form {
    padding: 40px;
    width: 600px;
    border: 1px rgba(0, 0, 0, 0.1) solid;
    border-radius: 2px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
  }
}

</style>