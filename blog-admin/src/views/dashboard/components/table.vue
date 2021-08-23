<template>
  <div class="comment-container">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
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
        prop="userId"
        header-align="center"
        align="center"
        label="用户编号">
      </el-table-column>
      <el-table-column
        prop="token"
        header-align="center"
        align="center"
        label="用户token">
      </el-table-column>
      <el-table-column
        prop="ip"
        header-align="center"
        align="center"
        label="登录ip">
      </el-table-column>
      <el-table-column
        prop="loginTime"
        header-align="center"
        align="center"
        label="登录时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
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
import { list } from '@/api/log'

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
    this.getDataList('')
  },
  methods: {
    // 获取数据列表
    getDataList(condition) {
      this.dataListLoading = true
      list(this.currentPage, this.pageSize, condition).then(resp => {
        console.log(resp);
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
      this.pageIndex = val
      this.getDataList('')
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val
    },
  }
}
</script>

<style lang="scss" scoped>
.comment {
  &-container {
    margin: 30px;
  }

}
</style>