<template>
  <div>
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(dataForm.key)">查询</el-button>
        <router-link to="./add" style="margin: 0px 10px;"><el-button type="primary">新增</el-button></router-link>
        <el-button type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
          <el-button type="primary" size="mini" @click="addOrUpdateHandle(scope.row.articleId)">修改</el-button>
          <el-button type="danger" size="mini" @click="deleteHandle(scope.row.articleId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="currentPage"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalCount">
    </el-pagination>
  </div>
</template>

<script>
import { list } from '@/api/article'
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
      dataListLoading: false,
      dataListSelections: [],
    }
  },
  mounted() {
    this.getDataList('');
  },
  methods: {
    // 获取数据列表
    getDataList(condition) {
      this.dataListLoading = true;
      list(this.currentPage, this.pageSize, condition).then(resp => {
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
      this.getDataList('')
    },
    // 当前页
    currentChangeHandle(val) {
      this.currentPage = val
      this.getDataList('')
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val
    },
    // 删除
    deleteHandle(id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.commentId
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/movie/comment/delete'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    }
  }
}
</script>

