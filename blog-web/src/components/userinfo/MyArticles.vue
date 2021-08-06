<template>
  <div style="position: relative;">
    <div class="animate__animated animate__fadeIn" v-loading="loading" style="height: 570px">
      <div class="myarticle" v-for="(item, index) in myarticles" :key="index">
        <div class="head" style="font-size: 17px;">
          <span style="float: left;margin: 10px 0 0 20px"><h2 @click="show(item.article_id)">{{ item.article_title }}</h2></span>
          <span style="float: right;margin: 10px 20px 0 0"><i class="el-icon-delete" @click="del(index, item.article_id)"></i></span>
          <span style="float: right;margin: 10px 20px 0 0"><i class="el-icon-edit" @click="toUpdate(item.article_id)"></i></span>
        </div>
        <hr>
        <div class="body">
            <i class="el-icon-date"></i>&nbsp;&nbsp;<span>{{ item.article_time }}</span>
            &nbsp;&nbsp;&nbsp;&nbsp;<i class="el-icon-user"></i>&nbsp;&nbsp;<span>{{ item.article_author }}</span>
            &nbsp;&nbsp;&nbsp;&nbsp;<i class="el-icon-price-tag"></i>&nbsp;&nbsp;<span>{{ item.article_tag }}</span>
        </div>
      </div>
      
    </div>
    <div style="margin: 10px">
      <!-- 分页 -->
      <el-pagination
        v-if="totalCount > 5"
        background
        layout="prev, pager, next"
        :page-size="pageSize"
        :total="totalCount"
        :current-page="currentPage"
        @current-change="changePageNum">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { logOrNot} from '../../utils/utils'
import { getRequest, deleteRequest} from '../../utils/api'
export default {
  components: {
    
  },
  data() {
    return {
      totalCount: 0,
      currentPage: 1,
      pageSize: 5,
      myarticles: [],

      loading: true,
    }
  },
  methods: {
    // 页码变更
    changePageNum(val) {
      this.currentPage = val;
      this.loading = true;
      this.loadBlogs(this.currentPage, this.pageSize);
    },
    // 加载博客
    loadBlogs(currentPage, pageSize) {
      if(!logOrNot()){
        this.$message.error('未经授权，无法访问啊。-> -_-');
        return;
      }
      getRequest(`/article/myblog/${currentPage}/${pageSize}`).then(resp => {
        // console.log(resp);
        this.myarticles = resp.data.list;
        this.totalCount = resp.data.count; //获取数据行数
        this.loading = false;
        if (resp.data.msg !== undefined){
          this.$message.error(resp.data.msg);
        };
      },
      error => {
        // console.log(error);
        this.$message.error('服务器出错了，重新试试吧。');
      });
    },
    // 删除文章
    del(index, aid) {
      this.$confirm('真的要删除这篇文章吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$prompt('请输入邮箱来确认身份信息', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
          inputErrorMessage: '邮箱格式不正确'
        }).then(({ value }) => {
          this.$message.success('你的邮箱是: ' + value);
          // 方法
          deleteRequest(`/article/del/${aid}`).then(resp => {
            // console.log(resp);
            this.myarticles.splice(index,1);
            this.$message.success('删除成功');
            this.loadBlogs(this.currentPage, this.pageSize);
          },
          e => {
            this.$message.info('删除失败');
          });
        }).catch(() => {});
      }).catch(() => {});
    },
    // 展示文章
    show(id) {
      let href = this.$router.resolve({path: '/blog', query: {article_id: id}});
      window.open(href.href, '_blank');
    },
    // 跳转修改文章
    toUpdate(aid) {
      this.$prompt('请输入邮箱来确认身份信息', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
        inputErrorMessage: '邮箱格式不正确'
      }).then(({ value }) => {
        // this.$message.success('你的邮箱是: ' + value);
        this.$router.push({path: '/write', query: {article_id: aid}})
      }).catch(() => {});
    }
  },
  mounted() {
    this.loadBlogs(this.currentPage, this.pageSize);
  },
}
</script>

<style>
  .myarticle {
    height: 100px;
    margin: 12px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-radius: 10px
  }
  .body {
    float: left;
    margin: 10px 0 0 20px;
  }
  .myarticle span:hover {
    cursor: pointer;
    color: #318fb5;
    text-decoration: underline;
  }
</style>