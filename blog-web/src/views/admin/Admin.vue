<template>
  <div class="admin">
    <el-container class="ctner" :class="{ 'folded' : folded }">
      <el-header class="header">
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
                <i class="el-icon-document"></i>
                <span slot="title">文章管理</span>
              </template>
              <el-menu-item index="/admin/article/add">写文章</el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>

        <el-main class="main">
          <!-- 此处放置el-tabs代码 -->
          <el-tabs
            v-model="activeIndex"
            type="card"
            closable
            v-if="openTab.length"
            @tab-click='tabClick'
            @tab-remove='tabRemove'>
            <el-tab-pane
              :key="index"
              v-for="(item, index) in openTab"
              :label="item.name"
              :name="item.route">
            </el-tab-pane>
          </el-tabs>

          <div class="admin-content">
            <router-view></router-view>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { getRequest } from '@/utils/api'
export default {
  components: {
  },
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
    //tab标签点击时，切换相应的路由
    tabClick(tab){
      console.log("tab",tab);
      console.log('active',this.activeIndex);
      this.$router.push({path: this.activeIndex});
    },
    //移除tab标签
    tabRemove(targetName){
      console.log("tabRemove",targetName);
      //首页不删
      if(targetName == '/admin'){
        return
      }
      this.$store.commit('delete_tabs', targetName);
      if (this.activeIndex === targetName) {
        // 设置当前激活的路由
        if (this.openTab && this.openTab.length >= 1) {
          console.log('=============',this.openTab[this.openTab.length-1].route)
          this.$store.commit('set_active_index', this.openTab[this.openTab.length-1].route);
          this.$router.push({path: this.activeIndex});
        } else {
          this.$router.push({path: '/admin'});
        }
      }
    }
  },

  mounted () {
    // 刷新时以当前路由做为tab加入tabs
    // 当前路由不是首页时，添加首页以及另一页到store里，并设置激活状态
    // 当当前路由是首页时，添加首页到store，并设置激活状态
    // if (this.$route.path !== '/admin' && this.$route.path !== '/admin/home') {
  if (this.$route.path !== '/admin/home') {
      this.$store.commit('add_tabs', {route: '/admin/home', name: '首页'});
      this.$store.commit('add_tabs', {route: this.$route.path , name: this.$route.name});
      this.$store.commit('set_active_index', this.$route.path);
    } else {
      this.$store.commit('add_tabs', {route: '/admin/home', name: '首页'});
      this.$store.commit('set_active_index', '/admin/home');
      this.$router.push('/admin/home');
    }
  },
  computed: {
    openTab () {
      return this.$store.state.openTab;
    },
    activeIndex:{
      get () {
        return this.$store.state.activeIndex;
      },
      set (val) {
        this.$store.commit('set_active_index', val);
      }
    }
  },
  watch:{
    '$route'(to,from){
      //判断路由是否已经打开
      //已经打开的 ，将其置为active
      //未打开的，将其放入队列里
      let flag = false;
      for(let item of this.openTab){
        console.log("item.name",item.name)
        console.log("t0.name",to.name)
        if(item.name === to.name){
          console.log('to.path',to.path);
          this.$store.commit('set_active_index',to.path)
          flag = true;
          break;
        }
      }
      if(!flag){
        console.log('to.path',to.path);
        this.$store.commit('add_tabs', {route: to.path, name: to.name});
        this.$store.commit('set_active_index', to.path);
      }
    }
  }



}
</script>

<style lang="scss">
  .header {
    background-color: #E4E7ED;
    position: fixed;
    width: 100%;
    font-size: 24px;
    line-height: 60px;
    z-index: 1000;
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

<style>
  .el-tabs.el-tabs--border-card {
    box-shadow: none;
    border-bottom: none;
  }
</style>