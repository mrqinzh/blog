<template>
  <div class="role-admin-container">
    <div>
      <a-space>
        <el-input placeholder="角色名" style="width: 200px;"></el-input>
        <el-button icon="el-icon-search" type="primary">搜索</el-button>
        <el-button icon="el-icon-plus" type="success">添加</el-button>
        <el-button icon="el-icon-delete" type="danger">批量删除</el-button>
      </a-space>
    </div>
    <div class="role-admin-tbl">
      <el-table
        style="margin: 10px 0px;"
        :data="roleDataList">
        <el-table-column
          type="selection">
        </el-table-column>
        <el-table-column
          prop="id"
          label="角色id">
        </el-table-column>
        <el-table-column
          prop="roleName"
          label="角色名">
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间">
        </el-table-column>
        <el-table-column
          fixed="right"
          width="150"
          label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
            <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { getRoleList } from '@/api/authority/role'
export default {
  data() {
    return {
      roleDataList: []
    }
  },
  mounted() {
    this.loadData();
  },
  methods: {
    loadData() {
      let param = {
        currentPage: 1,
        pageSize: 10
      }
      getRoleList(param).then(resp => {
        console.log(resp);
        this.roleDataList = resp.data.rows;
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .role-admin-container {
    margin: 30px;
    .role-admin-tbl {
      width: 50%;
    }
  }
</style>