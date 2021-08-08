<template>
  <div>
    <el-row>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <!-- 登录Form -->
        <div class="login-box" v-loading="loading"> <!-- style="align: center" -->
          <el-form :label-position="labelPosition" 
          label-width="80px" 
          :model="loginForm"
          ref="loginForm"
          class="loginform">
            <div>
              <el-form-item>
                <el-link type="primary" :underline="false" style="float: right;margin-top: 20px">入口</el-link>
              </el-form-item>
              <el-form-item label="账号：" prop="uid">
                <el-input type="text" prefix-icon="el-icon-user" v-model="loginForm.uid" placeholder="电话/邮箱/账号"></el-input>
              </el-form-item>
              <el-form-item label="密码：" prop="password">
                <el-input type="password" prefix-icon="el-icon-key" v-model="loginForm.password"></el-input>
              </el-form-item>
            </div>
            <div style="float: right;">
              <el-checkbox v-model="loginForm.remember">记住我</el-checkbox>
            </div>
            <el-form-item>
              <el-button round type="primary" 
              @click="loginSubmit()"
              style="margin-top: 10px;width: 80px">
                Login
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
    </el-row>
    
  </div>
</template>

<script>
import { getCookie, setCookie } from '@/utils/js/cookie.js'
import { postRequest } from '../utils/api'
const Base64 = require('js-base64').Base64
export default {
  data() {
    return {
      loading: false,
      labelPosition: 'right',
      // 登录表单
      loginForm: {
          uid: '',
          password: '',
          remember: false
      },
    }
  },
  methods: {
    // 登录提交方法
    loginSubmit() {
      if(this.loginForm.uid === ""){
        this.$message.error('账号不能为空');
        return
      };
      if(this.loginForm.password === ""){
        this.$message.error('密码不能为空');
        return
      };
      this.loading = true;
      let param = {
        uid: this.loginForm.uid,
        password: this.loginForm.password,
      };
      postRequest('/login',param).then(resp => {
        console.log(resp);
        if (resp.data.success) {
          this.$message.success('登录成功');
          this.setUserInfo(); // 记住我功能实现
          localStorage.setItem("token",resp.data.data);
          this.$router.replace({path: "/"}) // , query: {userid: this.loginForm.uid}
        } else {
          this.$message.error(resp.data.data);
          this.loading = false;
        };
      },
      error => {
        //console.log(error);
        this.$message.error('服务器错误');
        this.loading = false;
      });
    },
    // 储存表单信息  记住我功能
    setUserInfo () {
      // 判断用户是否勾选记住密码，如果勾选，向cookie中储存登录信息，
      // 如果没有勾选，储存的信息为空
      if (this.loginForm.remember) {
        setCookie('userName', this.loginForm.uid)
        // base64加密密码
        const passWord = Base64.encode(this.loginForm.password)
        setCookie('passWord', passWord)
      } else {
        setCookie('userName', '')
        setCookie('passWord', '')
      }
    },
  },
  mounted() {
    // 在页面加载时从cookie获取登录信息
    const userName = getCookie('userName')
    const passWord = Base64.decode(getCookie('passWord'))
    // 如果存在赋值给表单，并且将记住密码勾选
    if (userName) {
      this.loginForm.uid = userName
      this.loginForm.password = passWord
      this.loginForm.remember = true
    }
  },
}
</script>

<style lang="scss">
  .login-box {
    width: 500px;
    height: 300px;
    text-align: center;
    margin: 0 auto;
    margin-top: 180px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
  }
  .loginform {
    margin: 30px 50px 0 10px;
  }
  
</style>