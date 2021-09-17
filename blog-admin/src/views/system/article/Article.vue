<template>
  <div>
    <el-form :inline="true" :model="dataForm">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="findByType(dataForm.key)">查询</el-button>
        <router-link to="./add" style="margin: 0px 10px;"><el-button type="primary">新增</el-button></router-link>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="articleTitle"
        header-align="center"
        align="center"
        label="文章题目"
        width="250">
      </el-table-column>
      <el-table-column
        prop="articleAuthor"
        header-align="center"
        align="center"
        label="作者名"
        width="100">
      </el-table-column>
      <el-table-column
        prop="articleSummary"
        header-align="center"
        align="center"
        label="文章内容">
      </el-table-column>
      <el-table-column
        prop="articleUpdateTime"
        header-align="center"
        align="center"
        label="最后修改时间"
        width="170">
      </el-table-column>
      <el-table-column
        prop="articleCreateTime"
        header-align="center"
        align="center"
        label="发布时间"
        width="170">
      </el-table-column>
      <el-table-column
        prop="articleTag"
        header-align="center"
        align="center"
        label="发布时间"
        width="150">
      </el-table-column>
      <el-table-column
        prop="articleViews"
        header-align="center"
        align="center"
        label="浏览量"
        width="70">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="update(scope.row.id)">编辑</el-button>
          <el-button type="danger" size="mini" @click="deleteHandle(scope.row.id)">删除</el-button>
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
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      list(this.currentPage, this.pageSize, this.condition).then(resp => {
        // console.log(resp);
        this.dataList = resp.data.rows;
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
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val
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

