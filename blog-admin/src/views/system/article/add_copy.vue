<template>
  <div class="animate__animated animate__fadeIn">
    <div>
      文章标题：<el-input :maxlength="40" v-model="articleForm.articleTitle" placeholder="请输入标题..." show-word-limit style="width: 500px;"></el-input>

      <div style="float: right;">
        <el-button type="primary" @click="articleForm.articleTitle != '' && articleForm.articleContentMd != '' ? showArticleForm = true : $message.warning('标题和内容均不能为空哦，不然还有什么保存的意义！ ！！')">保存发布</el-button>
      </div>
    </div>

    <!-- markdown部分 -->
    <div class="markdown_body">
      <mavon-editor 
      ref="md"
      v-model="articleForm.articleContentMd"
      @save="saveBlog"
      @change="change" 
      @imgAdd="uploadImg"
      @imgDel="imgDel"
      :ishljs=true
      style="min-height: 700px" />
    </div>

    <el-dialog title="文章发布" :visible.sync="showArticleForm">
      <el-form :model="articleForm">
        <el-form-item label="文章标签" label-width="120px" >
          <el-select v-model="articleForm.articleTag" multiple :multiple-limit="4" placeholder="请选择文章标签">
            <el-option
              v-for="(item, index) in tagList"
              :key="index"
              :label="item.tagName"
              :value="item.tagName">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="文章类型" label-width="120px">
          <el-radio-group v-model="articleForm.articleType">
            <el-radio label="原创"></el-radio>
            <el-radio label="转载"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="文章封面图" label-width="120px">
          <!-- 图片上传组件 -->
          <img-upload :img="articleForm.articleCoverImg" @uploadimg="coverImgUpload"></img-upload>

          <el-alert
            title="如果不上传图片的话，系统会随机再你选择的标签中，生成一张对应的图片哦。^_^"
            type="success"
            :closable="false">
          </el-alert>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showArticleForm = false">取 消</el-button>
        <el-button type="primary" @click="saveBlog">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import ImgUpload from '@/components/web/upload/ImgUpload'

import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import 'mavon-editor/dist/markdown/github-markdown.min.css'
import '@/utils/hljs'

import { list } from '@/api/tag'
import { uploadFileRequest } from '@/api/file'
import { add, getById, update } from '@/api/article'

export default {
  name: 'ArticleAdd',
  components: {
    ImgUpload,
    mavonEditor //mavon-editor组件
  },
  data() {
    return {
      // 新定义
      articleHtml: '',
      tagList: [],
      showArticleForm: false,
      articleForm: {
        id: '',
        articleTitle: '',
        articleSummary: '',
        articleContentMd: '',
        articleTag: [],
        articleType: '原创',
        articleCoverImg: ''
      },

    }
  },
  methods: {
    //实时获取转成html的数据
    change(value, render) {    // value => markdown语句   render => html 语句
      this.articleForm.articleContentMd = value;
      this.articleHtml = render;
      this.$store.commit('SET_CONTENT', value);
    },
    //真正的保存方法
    saveBlog() {
      let param = {
        id: this.articleForm.id,
        articleTitle: this.articleForm.articleTitle,
        articleSummary: this.articleHtml.substring(0, 200),
        articleCoverImg: this.articleForm.articleCoverImg,
        articleContentMd: this.articleForm.articleContentMd,
        articleTag: this.articleForm.articleTag.toString(),
        articleType: this.articleForm.articleType
      }
      
      if (this.articleForm.id) {
        console.log('update');
        update(param).then(resp => {
          // console.log(resp);
          if (resp.success) {
            this.$message.success('恭喜恭喜，保存成功了。 => ^_^');
            this.$notify({
              title: '通知',
              dangerouslyUseHTMLString: true,
              duration: 0,
              message: '<strong>更新成功了，快去看看把<a href="/" target="_blank">点击前往</a></strong>'
            });
            this.showArticleForm = false;
          } else {
            this.$message.error('保存失败');
          }
        })
      } else {
        console.log('add')
        add(param).then(resp => {
          // console.log(resp);
          if (resp.success) {
            this.$message.success('恭喜恭喜，保存成功了。 => ^_^');
            this.$notify({
              title: '通知',
              dangerouslyUseHTMLString: true,
              duration: 0,
              message: '<strong>有新文章添加了，快去看看把<a href="/" target="_blank">点击前往</a></strong>'
            });
            this.showArticleForm = false;
          } else {
            this.$message.error('保存失败');
          }
        })
      }
    },

    // 将图片上传到服务器，返回地址替换到md中
    uploadImg (pos, $file) {
      var formdata = new FormData();
      formdata.append('file', $file);
      uploadFileRequest(formdata).then(resp => {
        // console.log(resp)
        if (resp.success) {
          this.$refs.md.$img2Url(pos, resp.data);
        }
      })
    },
    // 删除图片
    imgDel(pos){
      var imgPlace = pos[0].slice(37); // 截取文件名
      console.log(imgPlace);
    }, 

    // 获取要更改的文章信息
    initUpdateData() {
      getById(this.articleForm.id).then(resp => {
        // console.log(resp);
        // this.articleForm = resp.data;
        this.articleForm.articleTitle = resp.data.articleTitle;
        this.articleForm.articleContentMd = resp.data.articleContentMd;
        this.articleForm.articleTag = resp.data.articleTag.split(",");
        this.articleForm.articleType = resp.data.articleType;
        this.articleForm.articleCoverImg = resp.data.articleCoverImg;
      })
    },
    initTags() {
      list().then(resp => {
        this.tagList = resp.data;
      })
    },

    // 上传封面图方法
    coverImgUpload(resp) {
      this.articleForm.articleCoverImg = resp.data;
    }
  },
  mounted() {
    this.articleForm.id = this.$route.params.articleId;
    this.initTags();
    if (this.articleForm.id) {
      this.initUpdateData();
    } else {
      this.content = this.$store.state.article.content;
      // console.log(this.content);
    }

  },

}
</script>

<style>
  .markdown_body {
    margin: 20px 0; 
  }
</style>