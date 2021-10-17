<template>
  <div class="user-container">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="getDataList()">查询</el-button>
        <el-button icon="el-icon-plus" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
    </el-form>
    <el-table
      style="margin: 10px 0px;"
      :data="dataList"
      v-loading="dataListLoading">
      <el-table-column
        prop="id"
        label="用户编号">
      </el-table-column>
      <el-table-column
        prop="userRealName"
        label="用户姓名">
      </el-table-column>
      <el-table-column
        prop="userNickname"
        label="用户昵称">
      </el-table-column>
      <el-table-column
        ref="userAvatar"
        label="用户头像">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.userAvatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column
        prop="telephone"
        label="联系电话">
      </el-table-column>
      <el-table-column
        prop="userEmail"
        label="用户邮箱">
      </el-table-column>
      <el-table-column
        prop="roleName"
        label="用户角色">
      </el-table-column>
      <el-table-column
        fixed="right"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="danger" size="mini" @click="deleteHandle(scope.row.id)">删除</el-button>
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
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from './user-add-or-update'
import { list } from '@/api/user'

export default {
  name: 'User',
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
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false
    }
  },
  mounted() {
    this.getDataList();
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      list(this.currentPage, this.pageSize, '').then(resp => {
        // console.log(resp);
        this.dataList = resp.data.rows;
        this.totalCount = resp.data.totalCount;
        this.dataListLoading = false;
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
.user {
  &-container {
    margin: 30px;
  }
}
</style>

<style lang="scss">
 .el-input__inner {
   height: 35px;
 }
</style>