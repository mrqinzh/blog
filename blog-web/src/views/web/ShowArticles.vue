<template>
  <div>
    <el-row>
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>阅读</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
        <div style="margin-top: 30px" v-loading="loading" class="animate__animated animate__fadeIn">
          <!-- 文章头区域 -->
          <div style="font-size: 17px;">
            <h1 style="font-size: 40px;text-align: center"><strong>{{article.articleTitle}}</strong></h1>
            
            <div style="margin: 20px;text-align: right;">
              <i class="el-icon-user"></i>&nbsp;&nbsp;<span>{{article.articleAuthor}}</span>&nbsp;&nbsp;&nbsp;&nbsp;
              <i class="el-icon-date"></i>&nbsp;&nbsp;<span>{{article.articleUpdateTime}}</span>
              <el-tag :type="article.articleType === '原创' ? 'success' : 'danger'" style="float: left;">{{ article.articleType}}</el-tag>
            </div>
          </div>
          
          <!-- 文章内容 -->
          <div class="article-content" v-html="content"></div>

          <!-- 底部评论区域 -->
          <div class="page_footer">
            <div>
              <el-input
                type="textarea"
                placeholder="请输入内容"
                maxlength="100"
                show-word-limit
                :rows="6"
                v-model="commentContent"
                >
              </el-input>
              <el-button style="margin: 20px 0 0 80%" @click="submitComment(0)" type="primary" icon="el-icon-edit">发表评论</el-button>
            </div>
            <blockquote style="margin: 30px 0">
              <h1 style="padding: 0 0 0 20px">所有评论</h1>
            </blockquote>
            <div style="line-height: 2em" v-loading="loading2">

              <div v-for="(item, index) in parentComments" :key="index">
                <div style="display: inline-block;">
                  <el-avatar :src="item.user.userAvatar"></el-avatar>
                  <span style="font-size: 19px;margin-left: 20px">{{ item.user.nickname}}</span>
                  <span style="font-size: 17px;margin-left: 50px">评论时间： {{ item.commentTime}}</span>
                </div>
                <el-button style="margin-left: 50px"  @click="flag=index" type="primary" icon="el-icon-edit" size="mini">回复</el-button>
                <p style="margin-left: 60px">
                  <span>{{ item.commentContent}}</span>
                </p>
                <!-- 点击显示回复框 -->
                <div v-show="flag===index" style="margin: 20px">
                  <el-input v-model="replyContent" placeholder="请输入内容"></el-input>
                  <el-button style="margin: 10px 0 0 85%" @click="submitComment(item.id);">确定</el-button>
                </div>
                <blockquote class="child_comment">
                  <div style="margin: 20px" v-for="(child, child_index) in item.comments" :key="child_index">
                    <div style="display: inline-block;">
                      <el-avatar :src="child.user.userAvatar"></el-avatar>
                      <span style="font-size: 16px;margin-left: 20px">{{ child.user.nickname }}</span>
                    </div>
                    <span style="font-size: 13px;margin-left: 50%;">评论时间： {{ child.commentTime }}</span>
                    <p>{{ child.commentContent }}</p>
                  </div>
                </blockquote>
              </div>
              
            </div>

          </div>
        </div>
      </el-col>
    </el-row>
  </div>
  
</template>

<script>
import { logOrNot } from '@/utils/utils'
import { getRequest, postRequest } from '@/utils/api'
import Clipboard from 'clipboard'

// 引入默认样式
import 'highlight.js/styles/darcula.css' // 样式文件
// 引入个性化的vs2015样式
import 'highlight.js/styles/vs2015.css'

const MarkdownIt = require('markdown-it')
const hljs = require('highlight.js')
const md = new MarkdownIt({
  html: true,
  linkify: true,
  typographer: true,
  highlight: function (str, lang) {
    // 当前时间加随机数生成唯一的id标识
    const codeIndex = parseInt(Date.now()) + Math.floor(Math.random() * 10000000)
    // 复制功能主要使用的是 clipboard.js
    let html = `<el-button class="copy-btn" size="mini" data-clipboard-action="copy" data-clipboard-target="#copy${codeIndex}" title="复制"><i class="el-icon-document-copy"></i></el-button>`
    const linesLength = str.split(/\n/).length - 1
    // 生成行号
    let linesNum = '<span aria-hidden="true" class="line-numbers-rows">'
    for (let index = 0; index < linesLength; index++) {
      linesNum = linesNum + '<span></span>'
    }
    linesNum += '</span>'
    if (lang && hljs.getLanguage(lang)) {
      try {
        // highlight.js 高亮代码
        const preCode = hljs.highlight(lang, str, true).value
        html = html + preCode
        if (linesLength) {
          html += '<b class="name">' + lang + '</b>'
        }
        // 将代码包裹在 textarea 中
        return `<pre class="hljs"><code>${html}</code>${linesNum}</pre><textarea style="position: absolute;top: -9999px;left: -9999px;z-index: -9999;" id="copy${codeIndex}">${str.replace(/<\/textarea>/g, '&lt;/textarea>')}</textarea>`
      } catch (error) {
        console.log(error)
      }
    }

    const preCode = md.utils.escapeHtml(str)
    html = html + preCode
    // 将代码包裹在 textarea 中
    return `<pre class="hljs"><code>${html}</code>${linesNum}</pre><textarea style="position: absolute;top: -9999px;left: -9999px;z-index: -9999;" id="copy${codeIndex}">${str.replace(/<\/textarea>/g, '&lt;/textarea>')}</textarea>`
  }
});
export default {
  data() {
    return {
      // 评论内容和回复内容
      commentContent: '',
      replyContent: '',

      // 文章正文部分
      article: {},
      content: '',
      loading: true,
      loading2: false,

      // 评论区域相关信息
      parentComments: [],
      currentArticleId: '',
      flag: '', // 显示回复框的标志位
      isLogin: false,

      clipboard: '', // 添加复制功能
    }
  },
  methods: {
    // 加载文章内容
    loadArticleInfo() {
      this.currentArticleId = this.$route.query.article_id;
      getRequest(`/article/${this.$route.query.article_id}`).then(resp => {
        // console.log(resp);
        this.content = md.render(`${resp.data.data.data.articleContentMd}`);
        this.article = resp.data.data.data;
        this.loading = false;
      });
    },
    // 加载评论内容
    loadComments() {
      getRequest(`/comment/articleId/${this.$route.query.article_id}`).then(resp => {
        // console.log(resp);
        this.parentComments = resp.data.data;
      })
    },
    // 提交评论内容
    submitComment(val) {
      var content = "";
      if(val === 0){
        if(this.commentContent === ''){
          this.$message.warning('你评论了个寂寞 => `_`');
          return;
        }
        content = this.commentContent;
      } else {
        if(this.replyContent === ''){
          this.$message.warning('你评论了个寂寞 => `_`');
          return;
        }
        content = this.replyContent
      }
      
      this.loading2 = true;
      postRequest('/comment/add', {
        comment_content: content,
        comment_time: Date.parse(new Date()),
        article_id: this.currentArticleId,
        parent_id: val
      }).then(resp => {
        // console.log(resp);
        if(resp.data.code === "200"){
          this.loadComments();
          this.commentContent = '',
          this.$message.success('评论成功 => ^_^');
        } else {
          this.$message.warning('服务器出错了 => -_-。');
        }
        this.loading2 = false;
      })
    },
    // 更新浏览量
    updateViews() {
      
    }
    
  },
  mounted() {
    this.isLogin = logOrNot();
    this.loadArticleInfo();
    this.loadComments();

    this.$nextTick(() => {
      this.clipboard = new Clipboard('.copy-btn')
      // 复制成功失败的提示
      this.clipboard.on('success', (e) => {
        this.$message.success('复制成功')
      })
      this.clipboard.on('error', (e) => {
        this.$message.error('复制成功失败')
      })
    });

  },
  destroyed () {
    this.clipboard.destroy()
  }
}
</script>

<style lang="scss">
  .article-content {
    border: 1px solid red;
    padding: 30px;
    font-size: 18px;
    font-family: KaiTi;
    background-color: white;
    h1 {
      color: #F56C6C;
    }
    h2 {
      color: #67C23A;
    }
    h3 {
      color: #E6A23C;
    }
    img {
      width: 100%;
    }
  }
  pre.hljs {
    padding: 12px 2px 12px 40px !important;
    border-radius: 5px !important;
    position: relative;
    font-size: 15px !important;
    line-height: 25px !important;
    overflow: hidden !important;
    code {
      display: block !important;
      margin: 0 10px !important;
      overflow-x: auto !important;
    }
    .line-numbers-rows {
      position: absolute;
      pointer-events: none;
      top: 12px;
      bottom: 12px;
      left: 0;
      font-size: 100%;
      width: 40px;
      text-align: center;
      letter-spacing: -1px;
      border-right: 1px solid rgba(0, 0, 0, .66);
      user-select: none;
      counter-reset: linenumber;
      span {
        pointer-events: none;
        display: block;
        counter-increment: linenumber;
        &:before {
          content: counter(linenumber);
          color: #999;
          display: block;
          text-align: center;
        }
      }
    }
    b.name {
      position: absolute;
      top: 2px;
      right: 100px;
      z-index: 10;
      color: #999;
      pointer-events: none;
    }
    .copy-btn {
      position: absolute;
      top: 2px;
      right: 4px;
      z-index: 10;
      color: black;
      text-align: center;
      cursor: pointer;
      background-color: #fff;
      border: 0;
      border-radius: 2px;
      width: 40px;
    }
  }
</style>