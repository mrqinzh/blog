<template>
  <div class="animate__animated animate__fadeIn">
    <div>
      <div>
        <el-input :maxlength="30" v-model="article.articleTitle" placeholder="请输入标题..." show-word-limit style="width: 400px;"></el-input>

        <span style="margin-left: 10px;font-size: 17px">选择文章标签：</span>
        <el-tag
          :key="tag"
          v-for="tag in dynamicTags"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)" style="margin-left: 10px">
          {{tag}}
        </el-tag>
        <el-input
          class="input-new-tag"
          v-if="inputVisible"
          v-model="inputValue"
          ref="saveTagInput"
          size="small"
          @keyup.enter.native="handleInputConfirm"
          @blur="handleInputConfirm">
        </el-input>
        <el-button v-else class="button-new-tag" type="primary" size="small" @click="showInput">+Tag</el-button>

        <!-- 选择文章类型、转载/原创 -->
        <span style="margin-left: 10px;font-size: 17px">选择文章类型：</span>
        <el-select v-model="article.articleType" placeholder="请选择">
          <el-option value="原创">原创</el-option>
          <el-option value="转载">转载</el-option>
        </el-select>
        <div style="float: right;">
          <el-button type="primary" @click="saveBlog()">保存发布</el-button>
          <!-- <el-button type="primary" @click="showArticleForm = true">保存发布</el-button> -->
        </div>
      </div>

      <!-- markdown部分 -->
      <div class="markdown_body">
        <mavon-editor 
        ref="md"
        v-model="content"
        @save="saveBlog"
        @change="change" 
        @imgAdd="uploadImg"
        @imgDel="imgDel"
        :ishljs=true
        style="min-height: 700px" />
      </div>


      <el-dialog title="文章发布" :visible.sync="showArticleForm">
        <el-form :model="articleForm">
          <el-form-item label="文章标签" label-width="120px">
            <el-select v-model="articleForm.articleTag" multiple placeholder="请选择文章标签">
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
          <!-- <el-form-item label="文章封面图" label-width="120px">
            <el-upload
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="articleForm.articleCoverImg" :src="articleForm.articleCoverImg" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item> -->
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="showArticleForm = false">取 消</el-button>
          <el-button type="primary" @click="showArticleForm = false">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
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
    mavonEditor, //mavon-editor组件
  },
  data() {
    return {
      content: '', // 输入的markdown
      html: '',    // 转成的html
      // 保存表单
      article: {
        articleTitle: '',
        articleType: '',
      },
      dynamicTags: ['java'],
      inputVisible: false,
      inputValue: '',

      // 新定义
      tagList: [],
      showArticleForm: false,
      articleForm: {
        articleTag: [],
        articleType: '原创',
        articleCoverImg: ''
      },

      aid: '',  // 标志位，判断当前保存操作是 添加 还是修改
    }
  },
  methods: {
    //实时获取转成html的数据
    change(value, render) {    // value => markdown语句   render => html 语句
      this.html = render;
      this.content = value;
      // console.log(this.html);
      // console.log(this.content);

      this.$store.commit('SET_CONTENT', value);
    },
    //真正的保存方法
    saveBlog() {
      if (this.article.articleTitle === "" || this.content === "") {
        this.$message.warning('标题和内容均不能为空哦，不然还有什么保存的意义！ ！！');
        return;
      }
      if (this.aid) {
        let param = {
          id: this.aid,
          articleTitle: this.article.articleTitle,
          articleContentMd: this.content,
          articleSummary: this.html,
          articleTag: this.dynamicTags.toString(),
          articleType: this.article.articleType
        }
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
          } else {
            this.$message.warning('保存失败');
          }
        })
      } else {
        let param = {
          articleTitle: this.article.articleTitle,
          articleContentMd: this.content,
          articleSummary: this.html,
          articleTag: this.dynamicTags.toString(),
          articleType: this.article.articleType
        }
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
          } else {
            this.$message.warning('保存失败');
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
    // 添加标签相关方法
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },
    showInput() {
      let tags = this.dynamicTags;
      if (tags.length > 3) {
        this.$message.warning('最多添加四个标签。。');
        return 
      }
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },
    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.dynamicTags.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = '';
    },

    // 获取要更改的文章信息
    initUpdateData() {
      getById(this.aid).then(resp => {
        // console.log(resp);
        this.article.articleTitle = resp.data.articleTitle;
        this.article.articleType = resp.data.articleType;
        this.dynamicTags = resp.data.articleTag.split(",");
        this.content = resp.data.articleContentMd;
      })
    },
    initTags() {
      list().then(resp => {
        this.tagList = resp.data;
      })
    }
  },
  mounted() {
    this.aid = this.$route.params.articleId;
    this.initTags();
    if (this.aid) {
      this.initUpdateData();
    } else {
      this.content = this.$store.state.article.content;
      console.log(this.content);
    }

  },

  destroyed() {
    // this.$store.commit('SET_CONTENT', '');
  }
}
</script>

<style>
  .markdown_body {
    margin: 20px 0; 
  }
  .el-tag + .el-tag {
    position: relative;
    margin-left: 10px;
  }
  .button-new-tag {
    position: relative;
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    position: relative;
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  } 



  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>