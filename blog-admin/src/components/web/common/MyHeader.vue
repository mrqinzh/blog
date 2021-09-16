<template>
  <div class="myhead">
    <el-menu  mode="horizontal" @select="handleSelect">
      <el-menu-item style="width: 20%;cursor: auto" :disabled="true"></el-menu-item>
      <el-menu-item>
        <template slot="title">
          <img src="@/assets/logo.png" style="width: 150px;">
        </template>
      </el-menu-item>
      <el-menu-item style="width: 30%;cursor: auto" :disabled="true"></el-menu-item>
      <el-menu-item index="/"><i class="el-icon-s-home"></i>首页</el-menu-item>
      <el-menu-item index="/category"><i class="el-icon-menu"></i>分类</el-menu-item>
      <el-menu-item index="/message"><i class="el-icon-chat-dot-round"></i>留言</el-menu-item>
      <el-menu-item index="/about"><i class="el-icon-share"></i>关于</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLogin: false,
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