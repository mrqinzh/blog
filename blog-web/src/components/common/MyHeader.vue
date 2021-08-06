<template>
  <div class="myhead">
    <el-menu :default-active="activeIndex" mode="horizontal" @select="handleSelect">
      <el-menu-item style="width: 20%;cursor: auto" :disabled="true"></el-menu-item>
      <el-menu-item>
        <template slot="title">
          <img src="../../assets/logo.png" style="width: 150px;">
        </template>
      </el-menu-item>
      <el-menu-item style="width: 30%;cursor: auto" :disabled="true"></el-menu-item>
      <el-menu-item index="/"><i class="el-icon-s-home"></i>首页</el-menu-item>
      <el-menu-item index="/articles"><i class="el-icon-menu"></i>分类</el-menu-item>
      <!-- <el-menu-item index="/updateinfo"><i class="el-icon-position"></i>更新</el-menu-item> -->

      <el-menu-item @click="lgout" style="float: right;" v-if="this.isLogin">
        <el-tooltip effect="dark" content="退出" placement="bottom">
          <i class="el-icon-switch-button"></i>
        </el-tooltip>
      </el-menu-item>

      <el-menu-item index="/login" style="float: right;" v-else>
        <el-tooltip effect="dark" content="登录" placement="bottom">
          <i class="el-icon-thumb"></i>
        </el-tooltip>
      </el-menu-item>
      
      <el-submenu index="" style="float: right;">
        <template slot="title"><i class="el-icon-user"></i></template>
        <el-menu-item index="/userInfo"><i class="el-icon-house"></i>个人主页</el-menu-item>
        
        <el-menu-item :index="isLogin ? '/leaveMsg' : '/login'"><i class="el-icon-chat-round"></i>留言板</el-menu-item>
        <el-menu-item :index="isLogin ? '/write' : '/login'" class="hidden-xs-only"><i class="el-icon-edit"></i>写博客</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import { logOrNot } from '@/utils/utils'
import { getRequest } from '@/utils/api';
export default {
  data() {
    return {
      isLogin: false,
      activeIndex: '1',
    }
  },
  methods: {
    //退出按钮
    lgout() {
      this.$confirm('是否确定退出?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then((action) => {
        // action分别为confirm（确认），cancel（取消），close（关闭）的时候分别触发回调。
        if(action === 'confirm'){
          getRequest('/logout').then(resp => {
            this.$message.success(resp.data.body);
            localStorage.removeItem('token');
            this.$router.replace({path:'/login'});
          })
        }
      });
    },
    //输出点击的导航栏 号
    handleSelect(key) {
      this.isLogin = logOrNot();
      this.$router.push(key);
    },
  },
  mounted() {
    this.isLogin = logOrNot();
  },
}
</script>

<style>

</style>