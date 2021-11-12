<template>
  <div>
    <el-row>
      <el-col :span="7">
        <div class="grid-content bg-purple">
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>阅读</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :md="10" :lg="10" :xl="10">
        <div v-loading="loading" class="animate__animated animate__fadeIn">
          <!-- 文章头区域 -->
          <div style="font-size: 15px;">
            <h1 style="text-align: center;font-size: 20px;">{{article.articleTitle}}</h1>
            
            <div style="margin-top: 20px;text-align: right;">
              <a-space>
                  <a-tag color="#87d068"> 笔记 </a-tag>
                  <i class="el-icon-user"></i>&nbsp;&nbsp;<span>{{article.articleAuthor}}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                  <i class="el-icon-date"></i>&nbsp;&nbsp;<span>{{article.articleUpdateTime}}</span>
              </a-space>
            </div>
          </div>
          
          <!-- 文章内容 -->
          <div class="article-main-content" v-html="content"></div>

          <!-- 底部评论区域 -->
          <div class="page-comment">
            <div class="write-comment">
              <el-input
                type="textarea"
                placeholder="这里是要输入你的评论内容哦。^_^"
                maxlength="100"
                show-word-limit
                :rows="5"
                v-model="commentContent"
                >
              </el-input>
              <el-input v-model="nickname" placeholder="请输入你霸气的昵称。。。>_>" suffix-icon="el-icon-user"></el-input>
              <el-button @click="addComment(0)" type="primary" icon="el-icon-edit" size="small">发表评论</el-button>
            </div>
            <blockquote>
              <h2 style="font-family: 楷体">所有评论</h2>
            </blockquote>
            <div v-for="(item, index) in comments" :key="index">
              <a-space size="large">
                <el-avatar :src="item.avatar"></el-avatar>
                <span class="name-text">{{ item.nickname}}</span>
                <span style="font-size: 12px;color: #909399">{{ item.commentTime}}</span>
              </a-space>
              
              <p style="color: #303133">
                <i class="el-icon-chat-dot-round"></i>：{{ item.commentContent}}. . .
                <br>
                <el-link @click="flag=index" type="primary" icon="el-icon-edit" :underline="false">回复</el-link>
              </p>

              <!-- 点击显示回复框 -->
              <div v-show="flag===index" style="margin: 20px">
                <el-input v-model="replyContent" placeholder="想想你要回复些啥。^_^"></el-input>
                <el-button style="margin: 10px 0 0 85%" @click="addComment(item.id);">确定</el-button>
              </div>

              <!-- 子评论区域 -->
              <div class="child-comment">
                <blockquote>
                  <div style="margin: 20px" v-for="(child, child_index) in item.comments" :key="child_index">
                    <a-space size="large">
                      <el-avatar :src="child.avatar"></el-avatar>
                      <span class="name-text">{{ child.nickname }}</span>
                      <span style="font-size: 12px;">{{ child.commentTime }}</span>
                    </a-space>
                    <p>
                      <i class="el-icon-chat-dot-round"></i>：{{ child.commentContent }}
                    </p>
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
import { getById } from '@/api/article'
import { getByArticleIdOrUserId, add } from '@/api/comment'
import Clipboard from 'clipboard'
// 引入默认样式
import 'highlight.js/styles/atelier-cave-dark.css' // 样式文件

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
  name: 'Detail',
  data() {
    return {
      // 文章正文部分
      article: {},
      content: '',
      loading: true,

      // 评论区域相关信息
      nickname: '',
      comments: [],
      currentArticleId: '',
      // 评论内容和回复内容
      commentContent: '',
      replyContent: '',

      flag: '', // 显示回复框的标志位

      clipboard: '', // 添加复制功能
    }
  },
  methods: {
    // 加载文章内容
    loadArticleInfo() {
      getById(this.currentArticleId).then(resp => {
        // console.log(resp);
        this.content = md.render(resp.data.articleContentMd);
        this.article = resp.data;
        this.loading = false;
      })
    },
    // 加载评论内容
    loadComments() {
      getByArticleIdOrUserId('articleId', this.currentArticleId).then(resp => {
        // console.log(resp);
        this.comments = resp.data;
      })
    },
    // 提交评论内容
    addComment(val) {
      if (this.nickname === '') {
        this.$message.error('你还没有输入昵称哦 => `_`');
        return;
      }
      var content = "";
      if(val === 0){
        if(this.commentContent === ''){
          this.$message.warning('你评论了个寂寞 => `_`');
          return;
        }
        content = this.commentContent;
      } else {
        if(this.replyContent === ''){
          this.$message.warning('你回复了个寂寞 => `_`');
          return;
        }
        content = this.replyContent
      }
      let params = {
        nickname: this.nickname,
        parentId: val,
        commentContent: content,
        articleId: this.currentArticleId,
        type: 1,
      };
      add(params).then(resp => {
        // console.log(resp);
        if(resp.success) {
          this.loadComments();
          this.$message.success('评论成功。');
          this.flag = '';
        }
      })
    }
    
  },
  mounted() {
    this.currentArticleId = this.$route.params.articleId;
    this.loadArticleInfo();
    this.loadComments();
    this.$notify({type: 'success', title: '善意的提醒', message: '在文章的下方可以进行评论哦。^_^'})
    // 添加代码块复制方法
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
    this.clipboard.destroy();
  }
}
</script>

<style lang="scss">
  // 底部评论区域
  .page-comment {
    margin: 30px;
    line-height: 1.8em;
    .write-comment {
      margin: 0 auto;
      width: 70%;
      input {
        margin: 10px 0;
      }
      button {
        margin-left: 80%;
      }
    }
    .child-comment {
      margin-left: 10%;
      blockquote {
        border-left: 2px solid #409EFF;
        // background-color: #F2F6FC;
      }
      
    }
    .name-text{
      font-size: 17px;
      margin: 0 0 0 -10px;
      color: #303133;
    }
  }

  .article-main-content {
    // border: 1px solid red;
    padding: 30px;
    font-size: 16px;
    // font-family: KaiTi;
    color: #303133;
    background-color: white;
    h1 {
      color: #F56C6C;
    }
    h2 {
      color: #E6A23C;
    }
    h3 {
      color: #67C23A;
    }
    h4 {
      color: #1EE0EE;
    }
    h5 {
      color: #E41DD3;
    }
    h6 {
      color: #145FEA;
    }
    img {
      width: 100%;
    }
    ul li, ol li {
      list-style: disc;
      margin-left: 15px;
    }
    li p.first {
      display: inline-block;
    }
    ul,
    ol {
        padding-left: 30px;
    }
    ul:first-child,
    ol:first-child {
        margin-top: 0;
    }
    ul:last-child,
    ol:last-child {
        margin-bottom: 0;
    }
    table {
      padding: 0;
      word-break: initial;
    }
    table tr {
      border: 1px solid #dfe2e5;
      margin: 0;
      padding: 0;
    }
    table tr:nth-child(2n),
    thead {
      background-color: #f8f8f8;
    }
    table th {
      font-weight: bold;
      border: 1px solid #dfe2e5;
      border-bottom: 0;
      margin: 0;
      padding: 6px 13px;
    }
    table td {
      border: 1px solid #dfe2e5;
      margin: 0;
      padding: 6px 13px;
    }
    table th:first-child,
    table td:first-child {
      margin-top: 0;
    }
    table th:last-child,
    table td:last-child {
      margin-bottom: 0;
    }
    

  }
  pre.hljs {
    padding: 12px 2px 12px 40px !important;
    border: 1px solid;
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