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
        <div>
          <!-- 注册Form -->
          <el-dialog title="注册账号" :visible.sync="openRegisterForm" :before-close="quitReg" v-loading="loading">
            <el-form :model="registerForm" ref="registerForm" :rules="passRule" status-icon label-position="right" label-width="100px">
              <el-form-item label="用户昵称">
                <el-input v-model="registerForm.uname" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="登录邮箱" prop="email"
              :rules="[
              { required: true, message: '请输入邮箱地址', trigger: 'blur' },
              { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }]">
                <el-input v-model="registerForm.email" autocomplete="off">
                </el-input>
              </el-form-item>
              <el-form-item label="电话" prop="tel" 
              :rules="[
                { required: true, message: '电话号码不能为空'},
                { type: 'number', message: '输入错误字符'}]">
                <el-input v-model.number="registerForm.tel" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input type="password" v-model="registerForm.password" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="checkPass">
                <el-input type="password" v-model="registerForm.checkPass" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="openRegisterForm = false">取 消</el-button>
              <el-button type="primary" @click="submitForm('registerForm');">确 定</el-button>
            </div>
          </el-dialog>
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
    // 密码验证
    var validatePass = (rule, value, callback) => {
        if (value === '') {
            callback(new Error('请输入密码'));
        } else {
        if (this.registerForm.checkPass !== '') {
            this.$refs.registerForm.validateField('checkPass');
        }
        callback();
        }
    };
    var validatePass2 = (rule, value, callback) => {
        if (value === '') {
            callback(new Error('请再次输入密码'));
        } else if (value !== this.registerForm.password) {
            callback(new Error('两次输入密码不一致!'));
        } else {
            callback();
        }
    };
    return {
      openRegisterForm: false,
      loading: false,
      labelPosition: 'right',
      // 登录表单
      loginForm: {
          uid: '',
          password: '',
          remember: false
      },
      // 注册表单
      registerForm: {
        uname: '',
        email: '',
        tel: '',
        password: '',
        checkPass: '',
      },
      // 注册表单密码验证规则
      passRule: {
        password: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],
      },
    }
  },
  methods: {
    // 表单验证方法
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        this.loading = true;
        if (valid) {
          //console.log('submit!');
          let param = {
            uname: this.registerForm.uname,
            email: this.registerForm.email,
            tel: this.registerForm.tel,
            password: this.registerForm.checkPass,
          }
          postRequest('/login/reg',param).then(resp => {
            // console.log(resp);
            const h = this.$createElement;
            if (resp.data === "success") {
              this.$notify({
                type: 'success',
                title: '消息提示',
                duration: 2000,
                message: h('b', { style: 'color: teal'}, '成功注册，可以登录了哦'),
              });
              this.loading = false;
              this.openRegisterForm = false;
            }
          },
          error => {
            //console.log(error)
            this.$message({
              type: 'error',
              message: '注册失败了',
            });
            this.loading = false;
          })
        } else {
          this.openRegisterForm = true;
          this.loading = false;
          return false;
        }
      });
    },
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
      postRequest('/login/isLogin',param).then(resp => {
        // console.log(resp);
        if (resp.data.message === "success") {
          this.$message.success('登录成功');
          this.setUserInfo(); // 记住我功能实现
          localStorage.setItem("token",resp.data.body);
          this.$router.replace({path: "/"}) // , query: {userid: this.loginForm.uid}
        } else {
          this.$message.error(resp.data.message);
          this.loading = false;
        };
      },
      error => {
        //console.log(error);
        this.$message.error('服务器错误');
        this.loading = false;
      });
    },
    // 关闭注册表单方法
    quitReg() {
      this.$confirm('蒸的要取消注册了吗?', '提示',{
        iconClass: 'layui-icon layui-icon-face-cry',
      })
      .then(_ => {
        this.openRegisterForm = false;
      })
      .catch(_ => {});
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

<style>
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