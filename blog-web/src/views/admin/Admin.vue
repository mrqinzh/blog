<template>
  <div class="admin">
    <el-container class="ctner" :class="{ 'folded' : folded }">
      <el-header class="header">
        <!-- logo -->
        <!-- <span>
          <img src="@/assets/logo.png" alt="" class="logo">
        </span> -->
        <span style="float: right;cursor: pointer;">
          <i class="el-icon-switch-button" @click="lgout()"></i>
        </span>
      </el-header>
      <el-container>
        <el-aside class="left" width="">
          <span @click="folded = !folded" class="btn_fold">
            <i :class=" folded ? 'el-icon-s-unfold' : 'el-icon-s-fold'"></i>
          </span>
          <el-menu
            background-color="rgb(48, 65, 86)"
            text-color="#fff"
            default-active="2"
            @select="handleSelect"
            :collapse="folded">
            <el-menu-item index="/admin">
              <i class="el-icon-coffee-cup"></i>
              <span slot="title">首页</span>
            </el-menu-item>
            <el-submenu index="">
              <template slot="title">
                <i class="el-icon-setting"></i>
                <span slot="title">系统管理</span>
              </template>
              <el-menu-item-group title="分组1">
                <el-menu-item index="/admin/user">用户管理</el-menu-item>
                <el-menu-item index="/admin/blog">文章管理</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group title="分组2">
                <el-menu-item>评论管理</el-menu-item>
                <el-menu-item>分类管理</el-menu-item>
              </el-menu-item-group>
            </el-submenu>

            <el-submenu index="1-4">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span slot="title">页面管理</span>
              </template>
              <el-menu-item index="">导航管理</el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main class="main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { getRequest } from '@/utils/api'
export default {
  data() {
    return {
      folded: false,
    }
  },
  methods: {
    //输出点击的导航栏 号
    handleSelect(key) {
      this.$router.push(key);
    },
    //退出按钮
    lgout() {
      this.$confirm('是否确定退出?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then((action) => {
        // action分别为confirm（确认），cancel（取消），close（关闭）的时候分别触发回调。
        if(action === 'confirm'){
          getRequest('/login/logout').then(resp => {
            this.$message.success(resp.data.body);
            localStorage.removeItem('token');
            this.$router.replace({path:'/login'});
          })
        }
      });
    }
  }
}
</script>

<style>
  .header {
    background-color: #E4E7ED;
    position: fixed;
    width: 100%;
    font-size: 24px;
    line-height: 60px;
  }
  .logo {
    padding: 5px;
    height: 100%;
  }
  .left {
    background-color: rgb(48, 65, 86);
    position: fixed;
    top: 60px;
    left: 0px;
    height: calc(100vh - 60px);
    width: 270px;
    transition: width 0.4s;
    -webkit-transition: width 0.4s;
  }
  .main {
    /* background-color: #f0f2f5; */
    margin: 60px 0px 0px 270px;
    transition: margin-left 0.4s;
    -webkit-transition: margin-left 0.4s;
  }
  .el-menu {
    border-right: 0px;

  }
  .folded .el-menu span{
    display: none;
  }

  .folded .left {
    width: 64px;
  }
  .folded .main {
    margin-left: 64px;
  }
  .btn_fold {
    display: block;
    width: 100%;
    height: 40px;
    line-height: 40px;
    position: absolute;
    bottom: 0px;
    font-size: 24px;
    text-align: center;
    background-color: #E4E7ED;
    cursor: pointer;
  }
</style>