<template>
  <div>
    <span @click="folded = !folded" class="btn_fold">
      <i :class=" folded ? 'el-icon-s-unfold' : 'el-icon-s-fold'"></i>
    </span>
    <el-menu
      background-color="rgb(48, 65, 86)"
      text-color="#fff"
      default-active="1"
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
          <el-menu-item index="/admin/comment">评论管理</el-menu-item>
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

      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">文章管理</span>
        </template>
        <el-menu-item index="/admin/article/add">写文章</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
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
    },
  }
}
</script>

<style>
  .el-menu span{
    display: none;
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