<template>
  <div class="animate__animated animate__fadeIn">
    <el-container>
      <el-header>
        <el-input :maxlength="25" v-model="article.articleTitle" placeholder="请输入标题..." show-word-limit style="width: 400px;margin-left: 10px"></el-input>

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
        <div style="float: right;position: relative;">
          <el-button type="primary" @click="saveBlog()">保存发布</el-button>
        </div>
      </el-header>

      <el-main>
        <!-- markdown部分 -->
        <div class="markdown_body">
          <mavon-editor 
          ref="md"
          v-model="content"
          @save="saveSubmit"
          @change="change" 
          @imgAdd="uploadImg"
          @imgDel="imgDel"
          :ishljs=true
          style="min-height: 700px" />
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { isNotNullORBlank } from '@/utils/utils'
import { base, postRequest, getRequest, deleteRequest, uploadFileRequest} from '@/utils/api'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import 'mavon-editor/dist/markdown/github-markdown.min.css'
import '@/utils/plugins/hljs'
export default {
  components: {
    mavonEditor, //mavon-editor组件
  },
  data() {
    return {
      imgName: '',
      content: '', // 输入的markdown
      html: '',    // 转成的html
      // 保存表单
      article: {
        // article_id: '',
        articleTitle: '',
        articleType: '',
      },

      dynamicTags: ['java'],
      inputVisible: false,
      inputValue: '',

      aid: '',  // 标志位，判断当前保存操作是 添加 还是修改
    }
  },
  methods: {
    //实时获取转成html的数据
    change(value, render) {    // value => markdown语句   render => html 语句
      this.html = render;
      this.content = value;
//       console.log(this.html);
      //console.log(this.content);
    },
    //真正的保存方法
    saveBlog() {
      if (this.article.articleTitle === "" || this.content === "") {
        this.$message.warning('标题和内容均不能为空哦，不然还有什么保存的意义！ ！！');
        return;
      };
      var url = '';
      if(isNotNullORBlank(this.aid)){
        url = `/article/change/${this.aid}`
        console.log("更新")
      } else {
        url = '/article/add'
        console.log("添加")
      }
      let param = {
        articleTitle: this.article.articleTitle,
        articleContentMd: this.content,
        articleSummary: this.html,
        articleTag: this.dynamicTags.toString(),
        articleType: this.article.articleType
      }
      postRequest(url, param).then(resp => {
        console.log(resp)     
        if (resp.data.success) {
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
      },
      error => {
        this.$message.warning('保存失败');
      });
    },
    // markdown编辑器保存方法
    saveSubmit(value, render) {
      this.saveBlog();
    },
    // 将图片上传到服务器，返回地址替换到md中
    uploadImg (pos, $file) {
      var formdata = new FormData();
      formdata.append('file', $file);
      uploadFileRequest('/file/add', formdata).then(resp => {
        // console.log(resp)
        if (resp.data.success) {
          var url = resp.data.data;
          this.$refs.md.$img2Url(pos, url);
        } else {
          this.$message.error(resp.message)
        }
      }).catch(err => {
          console.log(err)
      })
    },
    // 删除图片
    imgDel(pos){
      var imgPlace = pos[0].slice(37); // 截取文件名
      // console.log(imgPlace);
      deleteRequest('/file/delete/' + imgPlace).then(resp => {
        // console.log(resp)
      })
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
    getUpdatePageInfo(aid) {
      getRequest(`/article/blog/${aid}`).then(resp => {
        // console.log(resp);
        this.content = resp.data.article_md;
        this.articles.article_title = resp.data.article_title;
        this.articles.article_author = resp.data.article_author;
        this.dynamicTags = resp.data.article_tag.split(',');
      });
    }
  },

  mounted() {
    if (isNotNullORBlank(this.$route.query.article_id)){
      this.aid = this.$route.query.article_id;
      this.getUpdatePageInfo(this.$route.query.article_id);
    }
  },
}
</script>

<style>
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
</style>