<template>
  <div>
    <!-- 抽屉 -->
  <el-drawer
    :visible.sync="drawer"
    size="20%"
    direction="ltr">
    <template slot="title">
      <h2>感谢你的访问&nbsp;&nbsp;<a-icon type="smile" /></h2>
    </template>
    <div class="left_drawer">
      <div>
        <p>感谢你的访问</p>
        <p>如果可以，交个朋友，一起掉头发</p>
        <p>
          <i class="el-icon-caret-bottom"></i>
          &nbsp;
          <i class="el-icon-caret-bottom"></i>
          &nbsp;
          <i class="el-icon-caret-bottom"></i>
        </p>
        <hr>
        <p style="margin-top: 30px">
          微信 <br>
          <img src="@/assets/img/vx.jpg" style="width: 150px; height: 150px">
        </p>
        <p style="margin-top: 30px">
          QQ <br>
          <img src="@/assets/img/qq.jpg" style="width: 150px; height: 150px">
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
        <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="16">
          <div class="animate__animated animate__fadeInLeft">
            <div class="blog-card" v-for="(item, index) in articles" :key="index">
              <blockquote class="boxchilde">
                <a><router-link :to="{name: 'Detail', params: {articleId: item.id}}" target="_blank">{{ item.articleTitle }}</router-link></a>
              </blockquote>
              <div style="margin-left: 20px;">
                  <a-tag color="#87d068" v-if="item.articleType === '原创'">原创</a-tag>
                  <a-tag color="#f50" v-else>转载</a-tag>
                  <i class="el-icon-user"></i>&nbsp;&nbsp;<a>{{item.articleAuthor}}</a>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <i class="el-icon-date"></i>&nbsp;&nbsp;<a>{{item.articleUpdateTime}}</a>
              </div>
              <div class="content">
                &nbsp;&nbsp;&nbsp;&nbsp;<span>{{ item.articleSummary }}。。。</span>
              </div>
              <div class="foot">
                <span v-for="(tag, index) in item.articleTag.split(',')" :key="index">
                  <a-icon type="tag" />&nbsp;&nbsp;&nbsp;&nbsp;<a>{{tag}}</a>&nbsp;&nbsp;&nbsp;&nbsp;
                </span>
                <span style="float: right;margin: 0 20px 10px 0;color: red;">
                    <a-icon type="like" @click="$message.success('谢谢你de支持 -> ^_^')"/>
                    <a-icon type="eye" />
                    <span>{{ item.articleViews }}</span>
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
              style="text-align: center;margin-top: 20px">
            </el-pagination>
          </div>
        </el-col>
        
        <!-- 中间右侧个人信息部分 -->
        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="8">
          <div class="animate__animated animate__fadeInRight">
            <!-- 搜索文章按钮 -->
            <SearchBtn style="margin-top: 30px;"></SearchBtn>
            <!-- 左边个人信息简介 -->
            <div class="user-card">
              <!-- 头像 -->
              <el-avatar class="avatar" :src="user.avatar" :size="100"></el-avatar>  
              <!-- 姓名、座右铭 -->
              <div style="font-size: 20px;">
                <span>{{user.name}}</span><br>
                <span style="font-size: 15px;line-height: 2em">{{user.motto}}</span>
              </div>
              <el-button @click="drawer = true" type="primary" round size="medium" style="margin: 10px 0;">
                Click Me
              </el-button>
              <div style="color: #67C23A;font-size: 35px;">
                <el-tooltip class="item" effect="dark" :content="user.vx" placement="bottom-start">
                  <a-icon type="wechat" />
                </el-tooltip>
                <el-tooltip class="item" effect="dark" :content="user.qq" placement="bottom-start">
                  <a-icon type="qq" />
                </el-tooltip>
                <el-tooltip class="item" effect="dark" :content="user.tel" placement="bottom-start">
                  <a-icon type="mobile" />
                </el-tooltip>
              </div>
            </div>
            <!-- <LinkCard></LinkCard> -->
            <!-- <WebInfo></WebInfo> -->
            <Tag></Tag>
          </div>
        </el-col>
        
      </el-col>
    </el-row>
  
    <!-- 分割线 -->
    <el-divider content-position="right"><i class="el-icon-wind-power"></i></el-divider>

  </div>
</template>

<script>
import Notice from '@/components/web/index/Notice'
import Tag from '@/components/web/index/Tag'
import LinkCard from '@/components/web/index/LinkCard';
import WebInfo from '@/components/web/index/WebInfo'
import SearchBtn from '@/components/web/index/SearchBtn'

import { list } from '@/api/article'
  export default {
    components: {
      'LinkCard': LinkCard,
      'SearchBtn': SearchBtn,
      'WebInfo': WebInfo,
      'Notice': Notice,
      'Tag': Tag,
    },
    data() {
      return {
        loading: true,
        
        // 用户信息
        user: {
          name: '秦志宏',
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
    .content {
      margin: 5px 20px;
      font-size: 14px;
      line-height: 1.7em;
      height: 50px;
      overflow: hidden;
    }
    .foot {
      padding: 0px 0px 5px 15px;
      font-size: 15px;
      span {
        margin-right: 5px;
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
    .avatar {
      width: 100%;
      height: 100%;
      transition: all 0.3s linear;
      &:hover {
        transform: scale(1.1, 1.1);
        filter: contrast(120%);
      }
    }
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
  }

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

  /* 左侧抽屉 */
  .left_drawer {
    text-align: center;
    margin: 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    font-size: 18px;
    background-image: linear-gradient(-225deg, #FFFEFF 0%, #D7FFFE 100%);
  }

</style>