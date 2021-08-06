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
            <h1 style="font-size: 40px;text-align: center"><strong>{{article.article_title}}</strong></h1>
            <div style="margin: 20px;text-align: right;">
              <i class="el-icon-user"></i>&nbsp;&nbsp;<span>{{article.article_author}}</span>&nbsp;&nbsp;&nbsp;&nbsp;
              <i class="el-icon-date"></i>&nbsp;&nbsp;<span>{{article.article_time}}</span>
            </div>
          </div>
          
          <!-- 文章内容 -->
          <div class="article">            
            <div v-highlight v-html="content" class="markdown-body" style="margin: 20px;">
            </div>
          </div>
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
                  <el-avatar :src="item.user.head_img"></el-avatar>
                  <span style="font-size: 19px;margin-left: 20px">{{ item.user.uname}}</span>
                  <span style="font-size: 17px;margin-left: 50px">评论时间： {{ item.comment_time}}</span>
                </div>
                <el-button style="margin-left: 50px"  @click="flag=index" type="primary" icon="el-icon-edit" size="mini">回复</el-button>
                <p style="margin-left: 60px">
                  <span>{{ item.comment_content}}</span>
                </p>
                <!-- 点击显示回复框 -->
                <div v-show="flag===index" style="margin: 20px">
                  <el-input v-model="replyContent" placeholder="请输入内容"></el-input>
                  <el-button style="margin: 10px 0 0 85%" @click="submitComment(item.id);">确定</el-button>
                </div>
                <blockquote class="child_comment">
                  <div style="margin: 20px" v-for="(child, child_index) in item.comments" :key="child_index">
                    <div style="display: inline-block;">
                      <el-avatar :src="child.user.head_img"></el-avatar>
                      <span style="font-size: 16px;margin-left: 20px">{{ child.user.uname }}</span>
                    </div>
                    <span style="font-size: 13px;margin-left: 50%;">评论时间： {{ child.comment_time }}</span>
                    <p>{{ child.comment_content }}</p>
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
import 'mavon-editor/dist/css/index.css'
import 'mavon-editor/dist/markdown/github-markdown.min.css'
import '@/utils/plugins/hljs'
import { getRequest, postRequest } from '@/utils/api'
import marked from 'marked'   //引入

export default {
  
  data() {
    return {

      // 评论内容和回复内容
      commentContent: '',
      replyContent: '',

      // 文章正文部分
      article: {
        article_title: '',
        article_author: '',
        article_time: '',
      },
      content: '',
      loading: true,
      loading2: false,


      // 评论区域相关信息
      parentComments: [],
      currentArticleId: '',

      flag: '', // 显示回复框的标志位

      isLogin: false,
    }
  },
  methods: {
    // 加载文章内容
    loadArticleInfo() {
      this.currentArticleId = this.$route.query.article_id;
      getRequest(`/article/blog/${this.$route.query.article_id}`).then(resp => {
        // console.log(resp);
        this.content = marked(resp.data.article_md)
        this.article.article_title = resp.data.article_title;
        this.article.article_author = resp.data.article_author;
        this.article.article_time = resp.data.article_time;
        this.loading = false;
      });
    },
    // 加载评论内容
    loadComments() {
      getRequest(`/comment/${this.$route.query.article_id}`).then(resp => {
        console.log(resp);
        this.parentComments = resp.data.body;
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
    
  },
  mounted() {
    this.isLogin = logOrNot()
    this.loadComments();
  },
  created() {
    this.loadArticleInfo();
  },
}
</script>

<style>
  .article {
    border: 1px solid #eeeeee;
    border-radius: 20px;
  }
  .page_footer {
    margin-top: 100px;
  }

  blockquote {
    border-left: 3px dashed red;
  }
  .child_comment {
    border-left: 2px solid green;
    margin-left: 10%;
    background-color: #F2F6FC;
  }


  /* markdown内容样式 */
  .article pre {
    line-height: 2em;
    background-color: #F1F6F9;
				
  }
  .article blockquote {
    border-left: 4px solid #409EFF;
    color: #409EFF;
  }
  .article pre code {
    font-size: 15px;
  }
  .markdown-body img:hover {
    cursor: pointer;
  }
</style>