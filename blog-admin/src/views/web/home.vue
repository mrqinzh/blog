<template>
  <div class="web-home">
    <!-- 抽屉 -->
  <el-drawer
    :visible.sync="drawer"
    size="20%"
    direction="ltr">
    <template slot="title">
      <h2>感谢你的访问&nbsp;<a-icon type="smile" /></h2>
    </template>
    <WebInfo></WebInfo>
    <div class="left_drawer">
      <div>
        <span>感谢你的访问</span><br>
        <span>如果可以，交个朋友，一起掉头发</span><br>
        <span>
          <i class="el-icon-caret-bottom"></i>
          &nbsp;
          <i class="el-icon-caret-bottom"></i>
          &nbsp;
          <i class="el-icon-caret-bottom"></i>
        </span>
        <hr>
        <p style="margin: 15px 0;">
          微信 <br>
          <img src="@/assets/img/vx.jpg" style="width: 120px; height: 120px">
        </p>
        <p>
          QQ <br>
          <img src="@/assets/img/qq.jpg" style="width: 120px; height: 120px">
        </p>
      </div>
      <div style="margin-top: 20px">
        <span>网站运行时长：{{ count_time }}</span>
      </div>
    </div>
  </el-drawer>

    <el-row :gutter="20">
      <!-- 左 -->
      <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5" class="hidden-sm-and-down">
        <div>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
      </el-col>

      <!-- 中间 -->
      <el-col :xs="24" :sm="24" :md="14" :lg="14" :xl="14" v-loading="loading">
        <!-- 头部滚动栏 -->
        <Notice></Notice>

        <!-- 中间左侧文章列表部分 -->
        <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="16">
          <div class="animate__animated animate__fadeInLeft">
            <div class="blog-card" v-for="(item, index) in articles" :key="index">
              <div>
                <div class="article-content">
                  <blockquote class="boxchilde">
                    <a><router-link :to="{name: 'Detail', params: {articleId: item.id}}" target="_blank">{{ item.articleTitle }}</router-link></a>
                  </blockquote>
                  <div style="margin-left: 20px;">
                    <a-tag color="#87d068">笔记</a-tag>
                    <i class="el-icon-user"></i>&nbsp;&nbsp;{{item.articleAuthor}}
                    <a-divider type="vertical" />
                    <i class="el-icon-date"></i>&nbsp;&nbsp;{{item.articleUpdateTime}}
                  </div>
                  <div class="summary">
                    &nbsp;&nbsp;&nbsp;&nbsp;<span>{{ item.articleSummary }}。。。</span>
                  </div>
                </div>
                <img :src="item.articleCoverImg" class="artile-cover-img">
              </div>
              <div class="foot">
                <a-icon type="tag" />
                <span v-for="(tag, index) in item.articleTag.split(',')" :key="index">
                  <a>{{tag}}</a>
                  <a-divider type="vertical" v-show="index < item.articleTag.split(',').length-1" />
                </span>
                <span style="float: right;margin: 0 20px 10px 0;">
                    <a-space>
                      <a-icon type="like" @click="$message.success('谢谢你de支持 -> ^_^')" style="color: red"/>
                      <a-icon type="eye" />
                      阅读<span style="color: red">({{ item.articleViews }})</span>
                    </a-space>
                </span>
              </div>
            </div>
            <!-- 分页 -->
            <el-pagination
              v-show="totalCount > 10"
              background
              layout="prev, pager, next"
              :page-size="pageSize"
              :total="totalCount"
              :current-page="currentPage"
              @current-change="changePageNum"
              style="text-align: center;">
            </el-pagination>
          </div>
        </el-col>
        
        <!-- 中间右侧个人信息部分 -->
        <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
          <div class="animate__animated animate__fadeInRight">
            <!-- 搜索文章按钮 -->
            <search-btn style="margin-top: 30px;"></search-btn>
            <!-- 左边个人信息简介 -->
            <div class="user-card">
              <!-- 头像 -->
              <el-avatar :src="user.avatar" :size="100"></el-avatar>  
              <!-- 姓名、座右铭 -->
              <div>
                <!-- <mallki :text="user.name"></mallki> -->
                <span>{{user.name}}</span><br>
                <span style="font-size: 15px;line-height: 2em;color: #909399">{{user.motto}}</span>
              </div>
              <el-button @click="drawer = true" type="primary" round size="medium" style="margin: 10px 0;">
                Click Me
              </el-button>
              <div style="color: #67C23A;font-size: 35px;">
                <el-tooltip class="item" effect="dark" :content="user.vx" placement="bottom-start">
                  <a-icon type="wechat" />
                </el-tooltip>
                <a-divider type="vertical" />
                <el-tooltip class="item" effect="dark" :content="user.tel" placement="bottom-start">
                  <a-icon type="mobile" />
                </el-tooltip>
                <a-divider type="vertical" />
                <el-tooltip class="item" effect="dark" :content="user.qq" placement="bottom-start">
                  <a-icon type="qq" />
                </el-tooltip>
              </div>
            </div>
            <tag></tag>
            <recent-comment></recent-comment>
          </div>
        </el-col>
        
      </el-col>
    </el-row>
  
    <!-- 分割线 -->
    <el-divider content-position="right"><i class="el-icon-wind-power"></i></el-divider>

  </div>
</template>

<script>
import Mallki from '@/components/TextHoverEffect/Mallki'
import Notice from '@/components/web/index/Notice'
import Tag from '@/components/web/index/Tag'
import RecentComment from '@/components/web/index/RecentComment'
import WebInfo from '@/components/web/index/WebInfo'
import SearchBtn from '@/components/web/index/SearchBtn'

import { list } from '@/api/article'

  export default {
    components: {
      SearchBtn,
      'WebInfo': WebInfo,
      'Notice': Notice,
      Tag,
      Mallki,
      RecentComment
    },
    data() {
      return {
        loading: true,
        
        // 用户信息
        user: {
          name: '秦志宏',
          nickname: 'mrqinzh',
          avatar: 'http://mrqinzh.info:9090/img/avatar.jpg',
          vx: 'qzh09010',
          qq: '1552589784',
          tel: '157-3056-7860',
          motto: '别人拥有的，不必羡慕；只要努力，时间都会给你', // 座右铭
        },
        // 分页
        totalCount: 0,
        currentPage: 1,
        pageSize: 10,
        condition: '',
        // 抽屉
        drawer: false,

        // 中间博客文章部分
        articles: [],
        // 开发天数
        start_time: '2021-4-10 14:10:00',
        count_time: '00:00:00',
        start_use_time: '2021-04-17 16:30:00',

        log: false, // 判断是否登录
      }
    },
    methods: {
      // 加载博客
      loadBlogs() {
        list(this.currentPage, this.pageSize, this.condition).then(resp => {
          // console.log(resp);
          resp.data.rows.forEach(e => {
            if (!e.articleCoverImg) {
              e.articleCoverImg = 'http://img.mrqinzh.info/null.jpg'
            }
          });
          this.articles = resp.data.rows;
          this.totalCount = resp.data.totalCount; //获取数据行数
          this.loading = false;
        })
      },
      // 页码变更
      changePageNum(val) {
        this.currentPage = val;
        this.loadBlogs();
      },
      // 计算网页运行时长
      countTime (startTime) {
        if (!startTime) return
        let start_time = new Date(startTime)
        let _this = this
        this.timer = setInterval(() => {
          let millisecond = new Date() - start_time
          let d = Math.floor(millisecond / (24 * 60 * 60 * 1000))
          let h = Math.floor(millisecond % (24 * 60 * 60 * 1000) / (60 * 60 * 1000))
          h = h < 10 ? '0' + h : h
          let min = Math.floor(millisecond % (60 * 60 * 1000) / (60 * 1000))
          min = min < 10 ? '0' + min : min
          let sec = Math.floor(millisecond % (60 * 60 * 1000) % (60 * 1000) / 1000)
          sec = sec < 10 ? '0' + sec : sec
          _this.count_time = d + '天' + h + '小时' + min + '分钟' + sec + '秒'
        }, 1000)
      },

    },
    mounted() {
      this.loadBlogs();
      this.countTime(this.start_time);
    },
    
  }
</script>

<style lang="scss" scoped>
  
  /* 中间博客卡片 */
  .blog-card {
    margin: 30px auto;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease-in;
    background-color: white;
    .article-content {
      display: inline-block;
      width: 70%;
      /* 中间文章内容的title动画 */
      .boxchilde {
        font-size: 20px;
        margin: 10px 0 7px 20px;
        display: inline-block;
        a {
          display: inline-block;
          transition: all 0.4s ease-in;
          &:hover {
            cursor: pointer;
            transform: translate(10px,0);
          }
        }
      }
      .summary {
        margin: 5px 20px;
        font-size: 14px;
        line-height: 1.7em;
        height: 70px;
        width: 90%;
        overflow: hidden;
      }
    }
    .artile-cover-img {
      display: inline-block;
      position: absolute;
      margin: 20px 10px 0 0;
      width: 27%;
      height: 130px;
      border: 1px solid #EBEEF5;
      border-radius: 15px;
    }
    
    .foot {
      padding: 0px 0px 5px 15px;
      font-size: 15px;
      > span {
        margin-left: 5px;
      }
      .ant-divider, .ant-divider-vertical {
        margin: 0 2px;
      }
    }
    &:hover {
      transform: translate(0, -5px);
      box-shadow: 0 2px 12px 0 rgba(189, 102, 197, 0.6);
    }
    a:hover {
      cursor: pointer;
      color: #318fb5;
      text-decoration: underline;
    }
  }

  .user-card {
    text-align: center;
    font-family: STKaiti;
    margin: 30px 0;
    width: 100%;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    background-color: white;
    transition: all 0.3s ease-in;
    &:hover {
      transform: translate(0,-10px);
      box-shadow: 0 2px 12px 0 rgba(189, 102, 197, 0.6);
    }
    > div {
      color: #303133;
      font-size: 20px;
    }
  }

  /* 左侧抽屉 */
  .left_drawer {
    text-align: center;
    margin: 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    font-size: 18px;
    background-image: linear-gradient(-225deg, #FFFEFF 0%, #D7FFFE 100%);
  }

</style>