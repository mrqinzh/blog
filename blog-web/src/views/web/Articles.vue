<template>
  <div>
    <div class="hidden-sm-and-down">
      <el-col :xs="24" :sm="24" :md="10" :lg="10" :xl="10">
        <div>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>分类</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
      </el-col>
    </div>

    <!-- 文章分类 -->
    <div class="hidden-sm-and-down">
      <div class="chooseType">
        <div class="animate__animated animate__fadeInLeft">
          <div class="categories_title">
            <i class="el-icon-s-operation"></i>&nbsp;&nbsp;&nbsp;<span @click="loadBlogs(currentPage, pageSize, '')" style="cursor: pointer">所有文章</span>
          </div>
          <ul>
            <li style="padding: 5px;" v-for="(item, index) in linkTypes" :key="index" @click="findByType(item.val)">
              <span type="primary">{{item.linkName}}</span>
              <span style="float: right;"><i class="el-icon-caret-right"></i></span>
            </li>
          </ul>
        </div>
      </div>
    </div>
    
    <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
      
      <div >
        <el-col :xs="24" :sm="16" :md="16" :lg="16" :xl="16">
          <!-- 时间线 -->
          <div style="margin-top: 50px;" v-loading="loading">
            <el-timeline style="min-height: 720px">
              <el-timeline-item 
              class="animate__animated animate__fadeIn"
              size="large"
              :color="'#409EFF'">
                <div style="height: 80px;font-size: 17px">
                  # 当前共有 <span style="font-size: 21px">{{totalCount}}</span> 篇文章  <a-icon type="smile" />
                </div>
              </el-timeline-item>
              <el-timeline-item
              :timestamp="allBlogs[index].articleUpdateTime" 
              placement="top" 
              size="large"
              :color="'#409EFF'"
              v-for="(item, index) in allBlogs" :key="index">
                <div class="animate__animated animate__fadeInRight">
                  <div class="card">
                    <i class="el-icon-caret-left" style="float: left;margin: 10px 0 0 -15px;font-size: 20px;color: #dedede;"></i>
                    <div class="card_head">
                      <span><a  @click="showBlog(item.id)">{{ item.articleTitle }}</a></span>
                    </div>
                    <hr>
                    <div class="card_foot">
                      <a-tag color="#87d068" v-if="item.articleType === '原创'">原创</a-tag>
                      <a-tag color="#f50" v-else>转载</a-tag>
                      <i class="el-icon-s-custom"></i>
                      <span><a style="color: #898d92">{{ item.articleAuthor }}</a></span>
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <i class="el-icon-price-tag"></i>
                      <span><a style="color: #898d92">{{ item.articleTag }}</a></span>
                    </div>
                  </div>
                </div>
              </el-timeline-item>
            </el-timeline>
            <!-- 分页 -->
            <div style="text-align: center;margin: 0 0 20px 0;position: relative;">
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
      </div>
    </el-col>
  </div>
</template>

<script>
import { postRequest, getRequest } from '@/utils/api'
export default {
  data(){
    return {
      totalCount: 0,
      currentPage: 1,
      pageSize: 10,
      // 文章信息
      allBlogs: [],
      // 标签信息
      linkTypes: [
        {
          linkName: 'Java',
          val: 'java'
        },
        {
          linkName: '数据库',
          val: 'sql'
        },
        {
          linkName: 'html',
          val: 'html'
        },
        {
          linkName: 'Spring',
          val: 'spring'
        },
        {
          linkName: 'Redis',
          val: 'redis'
        },
        {
          linkName: '算法',
          val: '算法'
        },
        {
          linkName: 'Linux',
          val: 'linux'
        },
      ],
      loading: true,
    }
  },
  methods: {
    // 页码变更
    changePageNum(val) {
      this.currentPage = val;
      this.loadBlogs(this.currentPage, this.pageSize, '');
    },
    // 加载博客
    loadBlogs(currentPage, pageSize, condition) {
      let url = `/article/list?currentPage=${currentPage}&pageSize=${pageSize}&condition=${condition}`
      getRequest(url).then(resp => {
        // console.log(resp);
        this.allBlogs = resp.data.rows;
        this.totalCount = resp.data.total; //获取数据行数
        this.loading = false;
      })
    },
    // 展示博客
    showBlog(id) {
      let href = this.$router.resolve({path: '/blog', query: {article_id: id}});
      window.open(href.href, '_blank');
    },
    // 根据条件查找文章
    findByType(val) {
      // console.log(val);
      this.currentPage = 1;
      this.pageSize = 10;
      this.loadBlogs(this.currentPage, this.pageSize, val)
    }
  },
  mounted() {
    this.loadBlogs(this.currentPage, this.pageSize, '');
  },
  
}
</script>

<style>
  /* 卡片 */
  .card {
    height: 100px;
    box-shadow: 0 2px 20px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    background-color: white;
  }
  .card_head {
    font-size: 20px;
    padding: 15px 0 0 20px;
  }
  .card span a:hover {
    text-decoration: underline;
  }
  .card_foot {
    color: #898d92;
    background-color: rgba(244,246,247,0.2);
    padding: 10px 5px 5px 15px
  }

  .chooseType {
    position: fixed;
    top: 60px;
    left: 28%;
    width: 200px;
    font-size: 14px;
    margin: 150px auto;
    line-height: 2em;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-radius: 4px;
    padding: 10px;
    background-color: white;
  }

  .chooseType div li:hover {
    cursor: pointer;
    color: #3EC1D3;
    background: #eeeeee;
  }

  .el-timeline-item__timestamp.is-top{
    font-size: 17px;
  }

</style>
