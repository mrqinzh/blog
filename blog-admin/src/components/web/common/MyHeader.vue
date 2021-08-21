<template>
  <div class="myhead">
    <el-menu :default-active="activeIndex" mode="horizontal" @select="handleSelect">
      <el-menu-item style="width: 20%;cursor: auto" :disabled="true"></el-menu-item>
      <el-menu-item>
        <template slot="title">
          <img src="@/assets/logo.png" style="width: 150px;">
        </template>
      </el-menu-item>
      <el-menu-item style="width: 30%;cursor: auto" :disabled="true"></el-menu-item>
      <el-menu-item index="/"><i class="el-icon-s-home"></i>首页</el-menu-item>
      <el-menu-item index="/category"><i class="el-icon-menu"></i>分类</el-menu-item>
      <el-menu-item index="/about"><i class="el-icon-share"></i>关于</el-menu-item>
      <!-- <el-menu-item index="/updateinfo"><i class="el-icon-position"></i>更新</el-menu-item> -->
      
      <el-submenu index="" style="float: right;">
        <template slot="title"><i class="el-icon-user"></i></template>
        
        <el-menu-item :index="isLogin ? '/leaveMsg' : '/login'"><i class="el-icon-chat-round"></i>留言板</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLogin: false,
      activeIndex: '1',
    }
  },
  methods: {
    //输出点击的导航栏 号
    handleSelect(key) {
      this.$router.push(key);
    },
  }
}
</script>

<style>

</style>