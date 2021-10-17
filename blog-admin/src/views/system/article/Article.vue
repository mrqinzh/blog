<template>
  <div>
    <el-form :model="dataForm" :inline="true" class="search-form">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="文章标题或标签"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="findByType(dataForm.key)">搜索</el-button>
        <el-button icon="el-icon-refresh-left" @click="resetList" type="warning">重置</el-button>
      </el-form-item>
      <el-form-item>
        <router-link to="./add"><el-button type="primary" icon="el-icon-plus">新增</el-button></router-link>
      </el-form-item>
    </el-form>
    <el-table
      style="margin: 10px 0px;"
      :data="dataList"
      v-loading="dataListLoading">
      <!-- <el-table-column
        type="selection"
        width="55">
      </el-table-column> -->
      <el-table-column
        prop="articleTitle"
        label="文章题目"
        width="250">
      </el-table-column>
      <el-table-column
        prop="articleAuthor"
        label="作者名"
        width="100">
      </el-table-column>
      <el-table-column
        prop="articleSummary"
        label="文章摘要">
      </el-table-column>
      <el-table-column
        prop="articleUpdateTime"
        label="最后修改时间"
        width="170">
      </el-table-column>
      <el-table-column
        prop="articleCreateTime"
        label="发布时间"
        width="170">
      </el-table-column>
      <el-table-column
        prop="articleType"
        label="是否原创"
        width="100">
      </el-table-column>
      <el-table-column
        prop="articleTag"
        label="文章标签"
        width="150">
      </el-table-column>
      <el-table-column
        prop="articleViews"
        label="浏览量"
        width="70">
      </el-table-column>
      <el-table-column
        fixed="right"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="update(scope.row.id)">编辑</el-button>
          <el-button type="text" size="mini" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="currentPage"
      :page-sizes="[10, 20, 50]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalCount">
    </el-pagination>
  </div>
</template>

<script>
import { list, del } from '@/api/article'
export default {
  data() {
    return {
      coverImgDialog: false,
      dataForm: {
        key: ''
      },
      dataList: [],

      currentPage: 1,
      pageSize: 10,
      totalCount: 0,
      condition: '',

      dataListLoading: false,
      dataListSelections: [],
    }
  },
  mounted() {
    this.getDataList();
  },
  methods: {
    // 根据条件查找文章
    findByType(val) {
      // console.log(val);
      this.currentPage = 1;
      this.pageSize = 10;
      this.condition = val;
      this.getDataList()
    },
    resetList() {
      this.dataForm.key = '';
      this.currentPage = 1; 
      this.pageSize = 10;
      this.condition = '';
      this.getDataList();
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      list(this.currentPage, this.pageSize, this.condition).then(resp => {
        // console.log(resp);
        this.dataList = resp.data.rows;
        this.dataList.forEach(e => {
          e.articleTitle = e.articleTitle.substring(0, 20);
          e.articleSummary = e.articleSummary.substring(0, 35) + '......';
        })
        this.totalCount = resp.data.totalCount;
        this.dataListLoading = false;
      })
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.currentPage = val
      this.getDataList()
    },
    // 删除
    deleteHandle(id) {
      this.$confirm(`确定对文章[id=${id}]进行[删除]操作?`, '提示', {
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
          // Todo 这里可以对邮箱信息进行验证
          this.$message.success('你的邮箱是: ' + value);
          del(id).then(resp => {
            // console.log(resp);
            if (resp.success) {
              this.$message.success(resp.msg);
              this.getDataList('');
            }
          })
        }).catch(() => {})
      }).catch(() => {})
    },
    update(id) {
      this.$router.push({name: 'ArticleAdd', params: {articleId: id}})
    }
  }
}
</script>