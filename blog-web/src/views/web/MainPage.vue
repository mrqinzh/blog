<template>
  <div>
    <!-- 抽屉 -->
  <el-drawer
    :visible.sync="drawer"
    size="20%"
    :direction="direction">
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
          <img src="@/assets/static/img/vx.jpg" style="width: 150px; height: 150px">
        </p>
        <p style="margin-top: 30px">
          QQ <br>
          <img src="@/assets/static/img/qq.jpg" style="width: 150px; height: 150px">
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
        <div style="box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);background-image: linear-gradient(-225deg, #FFFEFF 0%, #D7FFFE 100%);" class="animate__animated animate__fadeInUp">
          <i class="layui-icon layui-icon-speaker" style="margin-left: 10px;font-size: 30px;color: #c26565"></i>
          <div class="roll">
            <ul>
              <li v-for="(item, index) in ulList" 
              :key="item.id" 
              :class="!index && play ? 'toUp' : ''">
                <span>{{item.msg}}</span>
              </li>
            </ul>
          </div>
        </div>

        <!-- 中间左侧文章列表部分 -->
        <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="17">
          <div class="animate__animated animate__fadeInUp">
            <div style="min-height: 600px">
              <div class="mystory" v-for="(item, index) in articles" :key="index">
                <div style="padding: 15px 0 0 20px;">
                  <blockquote style="border-left: 4px solid #409EFF">
                    <h1 style="padding-left: 10px">
                      <a class="boxchilde" @click="showBlog(item.article_id)">
                        <span>{{item.article_title}}</span>
                      </a>
                    </h1>
                  </blockquote>
                </div>
                <div class="info">
                  <span><a-tag color="#87d068">原创</a-tag></span>
                  <span><i class="el-icon-user"></i>&nbsp;&nbsp;<a>{{item.article_author}}</a></span>
                  &nbsp;&nbsp;&nbsp;&nbsp;<span><i class="el-icon-date"></i>&nbsp;&nbsp;<a>{{item.article_time}}</a></span>
                </div>
                <div class="content">
                  &nbsp;&nbsp;&nbsp;&nbsp;<span>{{ item.article_body }}。。。</span>
                </div>
                <hr>
                <div class="foot">
                  <span v-for="(tag, index) in item.article_tag.split(',')" :key="index">
                    <i class="layui-icon layui-icon-note"></i>&nbsp;&nbsp;<a>{{tag}}</a>&nbsp;&nbsp;
                  </span>
                  <span style="float: right;margin: 0 20px 10px 0;">
                    <a style="color: red">
                      <a-icon type="like" @click="like()"/>
                    </a>
                    <a style="color: red">
                      <a-icon type="heart" @click="like()"/>
                    </a>
                    <a style="color: red">
                      <a-icon type="fire" />
                    </a>
                  </span>
                </div>
                
              </div>
            </div>
            <div style="text-align: center;margin-top: 20px">
              <!-- 分页 -->
              <el-pagination
                v-if="totalCount > 10"
                background
                layout="prev, pager, next"
                :page-size="pageSize"
                :total="totalCount"
                :current-page="currentPage"
                @current-change="changePageNum">
              </el-pagination>
            </div>
          </div>
        </el-col>
        
        <!-- 中间右侧个人信息部分 -->
        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="7">
          <div class="animate__animated animate__fadeInRight">
            <!-- 搜索文章按钮 -->
            <div style="margin-top: 30px">
              <SearchBtn></SearchBtn>
            </div>
            <!-- 左边个人信息简介 -->
            <div class="user-card">
              <!-- 头像 -->
              <div style="padding: 20px;">
                <el-upload
                class="avatar-uploader"
                action="#"
                :show-file-list="false"
                :http-request="uploadSectionFile">
                  <img v-if="imageUrl" :src="imageUrl" class="avatar" alt="@/assets/static/img/touxiang.jpg" title="点击更换头像">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </div>
              <!-- 姓名、座右铭 -->
              <div style="font-family: STKaiti">
                <span style="font-size: 20px;">{{user.name}}</span><br>
                <span style="font-size: 15px;line-height: 2em">{{user.motto}}</span>
              </div>
              <div style="margin-top: 10px">
                <el-button @click="drawer = true" type="primary" round style="margin-left: 16px;" class="hidden-xs-only">
                  点击发现新世界
                </el-button>
              </div>
              <hr>
              <div>
                <span style="letter-spacing: 1em;color: #67C23A">
                  <el-tooltip class="item" effect="dark" :content="user.vx" placement="bottom-start">
                    <i class="layui-icon layui-icon-login-wechat" style="font-size: 35px"></i>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="user.qq" placement="bottom-start">
                    <i class="layui-icon layui-icon-login-qq" style="font-size: 35px"></i>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="user.tel" placement="bottom-start">
                    <i class="layui-icon layui-icon-cellphone" style="font-size: 35px"></i>
                  </el-tooltip>
                </span>
              </div>
            </div>
            <OrderComment></OrderComment>
            <LinkCard></LinkCard>
            <WebInfo></WebInfo>
          </div>
        </el-col>
        
      </el-col>
    </el-row>
  
    <!-- 分割线 -->
    <el-divider content-position="right"><i class="el-icon-wind-power"></i></el-divider>

  </div>
</template>

<script>
import WebInfo from '@/components/partial/WebInfo'
import SearchBtn from '@/components/partial/SearchBtn'
import LinkCard from '@/components/partial/LinkCard';
import OrderComment from '@/components/partial/OrderComment'
import {logOrNot } from '@/utils/utils'
import { postRequest, uploadFileRequest } from '@/utils/api'
  export default {
    components: {
      'LinkCard': LinkCard,
      'SearchBtn': SearchBtn,
      'WebInfo': WebInfo,
      'OrderComment': OrderComment,
    },
    data() {
      return {
        loading: true,
        // 公告
        play: false,
        ulList: [
          { msg: '只要学不死，就往死里学。当你牛逼到一定程度时，你就有了话语权。累了看看父母，倦了想想未来。' },
          { msg: '不同的人，即使站在同一个地方，透过各自的人生，看到的风景也有所不同。' },
          { msg: '曾经我以为：孤独是世界上只剩一个人。现在我认为：孤独是自己竟能成为一个世界。' },
        ],
        // 用户信息
        user: {
          name: '秦志宏',
          vx: 'qzh09010',
          qq: '1552589784',
          tel: '157-3056-7860',
          motto: '别人拥有的，不必羡慕；只要努力，时间都会给你', // 座右铭
        },
        // 分页
        totalCount: 0,
        currentPage: 1,
        pageSize: 10,
        // 抽屉
        drawer: false,
        direction: 'ltr',

        // 中间博客文章部分
        articles: [],
        // 开发天数
        start_time: '2021-4-10 14:10:00',
        count_time: '00:00:00',
        start_use_time: '2021-04-17 16:30:00',

        imageUrl: '@/assets/static/img/touxiang.jpg', // 用户头像url 设置了默认头像

        log: false, // 判断是否登录
      }
    },
    methods: {
      // 加载博客
      loadBlogs(currentPage, pageSize) {
        postRequest('/article/orderArticle', {
          currentPage: currentPage,
          pageSize: pageSize,
        }).then(resp => {
          // console.log(resp);
          this.articles = resp.data.list;
          this.totalCount = resp.data.count; //获取数据行数

          this.loading = false;
        });
      },
      // 展示博客
      showBlog(id) {
        let href = this.$router.resolve({path: '/blog', query: {article_id: id}});
        window.open(href.href, '_blank');
      },
      // 页码变更
      changePageNum(val) {
        this.currentPage = val;
        this.loadBlogs(this.currentPage, this.pageSize);
      },
      // 头部公告滚动
      startPlay() {
        let that = this
        that.play = true //开始播放
        setTimeout(() => {
          that.ulList.push(that.ulList[0]) //将第一条数据塞到最后一个
          that.ulList.shift() //删除第一条数据
          that.play = false //暂停播放
        }, 500)
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
      // 上传头像方法
      uploadSectionFile(params) {
        if(!logOrNot()){
          this.$message.warning('未经授权，无法操作 -> `_`');
          return;
        }
        const file = params.file,
        fileType = file.type,
        isImage = fileType.indexOf("image") != -1,
        isLt2M = file.size / 1024 / 1024 < 2;
        // 这里常规检验，看项目需求而定
        if (!isImage) {
          this.$message.error("只能上传图片格式png、jpg、gif!");
          return;
        };
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
          return;
        };
        var formdata = new FormData();
        formdata.append('file', file);
        uploadFileRequest('/file/headImg', formdata).then(resp => {
          // console.log(resp)
          if(resp.data.message === "success"){
            this.imageUrl = resp.data.body;
            localStorage.setItem("head_img_url",resp.data.body);
          } else {
            this.$message.info(resp.data.message);
          }
        },
        err => {
          this.$message.error('服务器好像出了点小问题');
          this.imageUrl = '@/assets/static/img/touxiang.jpg';
        });
      },

      // 点击文章列表的 图片 方法
      like(){
        this.$message.success('谢谢你de支持 -> ^_^')
      }

    },

    mounted() {
      // console.log(localStorage.getItem("head_img_url"));
      if(localStorage.getItem("head_img_url") != null){
        this.imageUrl = localStorage.getItem("head_img_url");
      } else {
        this.imageUrl = '@/assets/static/img/touxiang.jpg';
      }
      
      setInterval(this.startPlay, 4000);
      // console.log(logOrNot())
      this.loadBlogs(this.currentPage, this.pageSize);
      this.log = logOrNot();

    },
    created () {
      // 调用时机根据需求
      this.countTime(this.start_time)
    }
    
  }
</script>

<style>
  /* 中间博客卡片 */
  .mystory {
    margin: 30px auto;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease-in;
  }
  .mystory:hover {
    transform: translate(0, -5px);
    box-shadow: 0 2px 12px 0 rgba(189, 102, 197, 0.6);
  }
  .info {
    margin: 10px 0 0 20px;
  }
  .content {
    margin: 10px;
    font-size: 17px;
    line-height: 1.7em;
    height: 55px;
    overflow: hidden;
  }
  .foot {
    padding: 0 0 10px 20px;
    font-size: 17px;
  }
  .mystory a:hover {
    cursor: pointer;
    color: #318fb5;
    text-decoration: underline;
  }
  .user-card {
    margin: 30px 0;
    width: 100%;
    min-height: 320px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    background-color: #F2F6FC;
    text-align: center;
    transition: all 0.3s ease-in;
  }
  .user-card:hover {
    transform: translate(0,-10px);
    box-shadow: 0 2px 12px 0 rgba(189, 102, 197, 0.6);
  }

  /* 左侧抽屉 */
  .left_drawer {
    text-align: center;
    margin: 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    font-size: 18px;
    background-image: linear-gradient(-225deg, #FFFEFF 0%, #D7FFFE 100%);
  }

  /* 滚动栏 */
  .roll {
    display: inline-block;
    height: 21px;
    position: relative;
    overflow: hidden;
    margin-left: 10px;
  }
  .roll ul {
    list-style: none;
    overflow: hidden;
    height: 20px;
    padding: 0;
    margin: 0;
  }
  .roll li {
    text-align: left;
    height: 20px;
    line-height: 20px;
  }
  .toUp {
    margin-top: -20px;
    transition: all 1s;
  }

  /* 头像上传的css */
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 50px;
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
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
  }
  .avatar {
    width: 100px;
    height: 100px;
    display: block;
  }

  /* 中间文章内容的title动画 */
  .boxchilde{
    display: inline-block;
    transition: all 0.4s ease-in;
  }
  .boxchilde:hover{
    cursor: pointer;
    transform: translate(20px,0);
  }

</style>