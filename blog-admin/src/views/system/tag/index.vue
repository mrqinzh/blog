<template>
  <div class="tag-container">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="findByType(dataForm.key)">查询</el-button>
        <el-button type="primary" @click="addOrUpdateHandle()">新增</el-button>
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
        prop="id"
        header-align="center"
        align="center"
        label="标签编号"
        width="250">
      </el-table-column>
      <el-table-column
        prop="tagName"
        header-align="center"
        align="center"
        label="标签名称">
      </el-table-column>
      <el-table-column
        prop="tagImg"
        header-align="center"
        align="center"
        label="标签图">
        <template slot-scope="scope">
          <el-image :src="scope.row.tagImg" style="width: 50px;"></el-image>
        </template>
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
      :total="totalCount"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList()"></add-or-update>
  </div>
</template>

<script>
import { page } from '@/api/tag'

import AddOrUpdate from './tag-add-or-update'
export default {
  components: {
    AddOrUpdate
  },
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

      addOrUpdateVisible: false
    
    }
  },
  mounted() {
    this.getDataList()
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
      this.dataListLoading = true
      page(this.currentPage, this.pageSize, this.condition).then(resp => {
        // console.log(resp);
        this.dataList = resp.data.rows;
        this.totalCount = resp.data.totalCount;
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.currentPage = 1
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

    // 新增 / 修改
    addOrUpdateHandle (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
  }
}
</script>

<style lang="scss" scoped>
.tag {
  &-container {
    margin: 30px;
  }
}
</style>