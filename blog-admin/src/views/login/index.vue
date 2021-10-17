<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" class="login-form">

      <div class="title-container">
        <h3 class="title">mrqinzh's admin</h3>
      </div>

      <el-form-item prop="userName" :rules="[{ required: true, message: '账号不能为空', trigger: 'blur' }]">
        <el-input prefix-icon="el-icon-user" v-model="loginForm.userName" placeholder="账号" clearable></el-input>
      </el-form-item>

      <el-form-item prop="userPwd" :rules="[{ required: true, message: '密码不能为空', trigger: 'blur' }]">
        <el-input
          prefix-icon="el-icon-lock"
          v-model="loginForm.userPwd"
          type="password"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
          clearable>
        </el-input>
      </el-form-item>

      <el-button :loading="loading" type="primary" style="width: 100%;margin-top: 5px;" @click.native.prevent="handleLogin">Enter</el-button>

    </el-form>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        userName: '',
        userPwd: ''
      },
      loading: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || '/admin' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.login-container {
  min-height: 100%;
  width: 100%;
  background-size: 100% 100%;
  background-image: url('../../assets/img/bgimg.jpg');
  overflow: hidden;

  .login-form {
    position: relative;
    width: 380px;
    max-width: 100%;
    padding: 15px 35px;
    margin: 200px auto;
    overflow: hidden;
    border: 1px solid #DCDFE6;
    border-radius: 5px;
    background-color: #fff;
  }

  .title-container {
    position: relative;
    .title {
      font-size: 26px;
      color: #606266;
      margin: 0px auto 20px auto;
      text-align: center;
      font-weight: bold;
    }
  }
}
</style>


<style lang="scss">
.el-input__prefix {
  .el-input__icon {
    color: #303133;
  }
  .el-input__inner {
    background-color: #fff;
  }
}
</style>
