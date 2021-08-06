<template>
  <div id="userInfoPage" style="text-align: center;">
    <div class="layui-row">
      <!-- 左 -->
      <el-col :xs="6" :sm="6" :md="6" :lg="6" :xl="6" class="hidden-xs-only">
        <div class="grid-demo grid-demo-bg1">
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>个人信息</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
      </el-col>

      <!-- 中 -->
      <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12">
        <!-- 卡片 -->
        <el-card style="height: 130px;width: 100%;border-radius:15px;" class="animate__animated animate__fadeIn">
          <!-- 卡片中的头像 -->
          <div class="block" style="float: left;margin-top: 17px;">
            <el-avatar :size="50" :src="imageUrl"></el-avatar> 
          </div>
          <div style="float: left;margin-left: 20px;margin-top: 17px;">
            <div style="float: left;">
              <el-link type="primary" @click="userinfo_goto(0)">Mr.QinZH</el-link>
              &nbsp;&nbsp;&nbsp;<span><i class="el-icon-male"></i></span>
            </div>
            <div style="padding: 20px 45px 20px 0;margin-top: 13px">
              <i class="el-icon-map-location"></i>&nbsp;&nbsp;重庆市
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <i class="el-icon-school"></i>&nbsp;&nbsp;重庆三峡学院
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <i class="el-icon-monitor"></i>&nbsp;&nbsp;信息与计算科学
            </div>
          </div>
          <div style="float: right;margin-top: 17px;">
            <el-button type="success" @click="daka">打卡</el-button>
          </div>
        </el-card>

        <!-- 中间左侧 -->
        <el-col :xs="4" :sm="4" :md="4" :lg="4" :xl="4">
          <div class="animate__animated animate__fadeInUp">
            <ul class="layui-nav layui-nav-tree" id="leftnav">
              <li class="layui-nav-item" v-for="(userinfo_nav, index) in userinfo_navs" :key="index" @click="userinfo_goto(index)">
                <span class="allLi">
                  <i :class="userinfo_nav.left_icon"></i>
                  {{userinfo_nav.nav_title}}
                  <i class="el-icon-arrow-right" style="float: right;margin-top: 14px;margin-right: 5px"></i>
                </span>
              </li>
            </ul>
          </div>
        </el-col>
        <!-- 中间右侧部分 -->
        <el-col :xs="18" :sm="18" :md="18" :lg="18" :xl="18">
          <div class="zRight">
            <router-view></router-view>
          </div>
        </el-col>

      </el-col>
    </div>

  </div>
</template>

<script>
export default {
  data () {
    return {
      imageUrl: '',
      userinfo_navs: [
        {
          nav_title: '首页',
          left_icon: 'layui-icon layui-icon-home',
          to_where: '/userinfo'
        },
        {
          nav_title: '发布文章',
          left_icon: 'el-icon-edit',
          to_where: '/userInfo/myArticles'
        },
        // {
        //   nav_title: '我的评论',
        //   left_icon: 'el-icon-chat-dot-round',
        //   to_where: '/userInfo/myComment'
        // },
      ],
    }
  },
  methods: {
    // 打卡
    daka() {
      const h = this.$createElement;
      this.$notify({
        type: 'success',
        title: '打卡成功',
        duration: 3000,
        message: h('b', { style: 'color: teal'}, '今天又是充满希望的一天！'),
      });
    },
    //控制nav跳转的方法
    userinfo_goto(index) {
      this.$router.push(this.userinfo_navs[index].to_where);
    }
  },
  mounted() {
    if(localStorage.getItem("head_img_url") != null){
      this.imageUrl = localStorage.getItem("head_img_url");
    } else {
      this.imageUrl = '../../static/img/touxiang.jpg';
    }
  },
}
</script>

<style>
  .zRight {
    width: 100%;
    height: 630px;
    margin: 20px;
    background: #fff;
    border: 1px solid #EBEEF5;
    border-radius:15px;
  }

  #leftnav {
    width: 100%;
    margin-top: 20px;
    border: 1px solid #EBEEF5;
    background: #fff;
  }

  .layui-nav-item {
    color: #071a52;
    cursor: pointer;
  }

  .allLi {
    padding-left: 5px;
  }

  .layui-nav-item:hover {
    background: #ebfffa;
  }
  .layui-nav-item:active {
    color: #1fab89;
    background: #a1eafb;
  }
</style>