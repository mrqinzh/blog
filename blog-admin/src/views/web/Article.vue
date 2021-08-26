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
    <ul class="chooseType animate__animated animate__fadeInLeft">
      <i class="el-icon-s-operation"></i>&nbsp;&nbsp;&nbsp;&nbsp;<span @click="loadBlogs('')" style="cursor: pointer">所有文章</span>
      <li v-for="(item, index) in linkTypes" :key="index" @click="findByType(item.val)">
        {{item.linkName}}
        <span><i class="el-icon-caret-right"></i></span>
      </li>
    </ul>
    
    <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
      <el-col :xs="24" :sm="16" :md="16" :lg="16" :xl="16">
        <!-- 时间线 -->
        <div style="margin: 50px 0px 30px 0px;" v-loading="loading">
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
                  <div class="card-head">
                    <span><a><router-link :to="{path: '/detail', query: {articleId: item.id}}" target="_blank">{{ item.articleTitle }}</router-link></a></span>
                  </div>
                  <div class="card-foot">
                    <a-tag color="#87d068" v-if="item.articleType === '原创'">原创</a-tag>
                    <a-tag color="#f50" v-else>转载</a-tag>
                    <i class="el-icon-s-custom"></i>
                    <span><a style="color: #898d92">{{ item.articleAuthor }}</a></span>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <a-icon type="tags" />
                    <span><a style="color: #898d92">{{ item.articleTag }}</a></span>
                    <span style="float: right;margin: 0px 15px;">
                      <a-icon type="eye" />
                      {{item.articleViews}}
                    </span>
                  </div>
                </div>
              </div>
            </el-timeline-item>
          </el-timeline>
          <!-- 分页 -->
        </div>
        <el-pagination
          style="text-align: center;"
          v-if="totalCount > 10"
          background
          layout="prev, pager, next"
          :page-size="pageSize"
          :total="totalCount"
          :current-page="currentPage"
          @current-change="changePageNum">
        </el-pagination>
      </el-col>
    </el-col>
  </div>
</template>

<script>
import { list } from '@/api/article'
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
      this.loadBlogs('');
    },
    // 加载博客
    loadBlogs(condition) {
      list(this.currentPage, this.pageSize, condition).then( resp => {
        // console.log(resp);
        this.allBlogs = resp.data.rows;
        this.totalCount = resp.data.totalCount; //获取数据行数
        this.loading = false;
      })
    },
    // 根据条件查找文章
    findByType(val) {
      // console.log(val);
      this.currentPage = 1;
      this.pageSize = 10;
      this.loadBlogs(val)
    }
  },
  mounted() {
    this.loadBlogs('');
  },
  
}
</script>

<style lang="scss" scoped>
  /* 卡片 */
  .card {
    box-shadow: 0 2px 20px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    background-color: white;
    .card-head {
      font-size: 20px;
      padding: 10px 0 0 20px;
      a:hover {
        text-decoration: underline;
      }
    }
    .card-foot {
      color: #898d92;
      background-color: rgba(244,246,247,0.2);
      padding: 10px 5px 10px 15px
    }
  }
  
  .chooseType {
    position: fixed;
    top: 60px;
    left: 28%;
    width: 200px;
    font-size: 15px;
    margin: 150px auto;
    line-height: 2em;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-radius: 4px;
    padding: 8px;
    background-color: white;
    li {
      padding: 0px 7px;
      &:hover {
        color: #000;
        cursor: pointer;
      }
      span {
        float: right;
      }
    }
  }

  // element-ui 时间轴
  .el-timeline-item__timestamp.is-top{
    font-size: 17px;
  }

</style>
