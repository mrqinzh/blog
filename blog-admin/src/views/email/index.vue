<template>
  <div class="email-container">
    <el-form :model="emailForm" ref="emailForm" label-width="100px" class="demo-dynamic">
      <el-form-item
        prop="email"
        label="邮箱"
        :rules="[
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ]"
      >
        <el-input v-model="emailForm.to"></el-input>
      </el-form-item>
      <el-form-item label="邮件内容">
        <el-input type="textarea" v-model="emailForm.content"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('emailForm')">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        emailForm: {
          to: '',
          content: ''
        }
      }
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    }
  }
</script>

<style lang="scss" scoped>
.email-container {
  margin: 30px;
  overflow: hidden;
}

</style>