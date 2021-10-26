<template>
  <div class="menu-admin-manage">
    <div>
      <el-input v-model="searchCondition.menuTitle" placeholder="菜单标题" style="width: 10%;"></el-input>
      <el-date-picker
        style="margin: 0 20px;"
        v-model="searchCondition.time"
        type="daterange"
        align="right"
        unlink-panels
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期">
      </el-date-picker>
      <el-button type="success" icon="el-icon-search">搜索</el-button>
      <el-button type="warning" icon="el-icon-refresh-left">重置</el-button>
      <br>
      <div style="margin: 10px 0;">
        <el-button type="primary" icon="el-icon-plus" @click="addOrUpdateHandle('')">添加</el-button>
        <el-button type="success" icon="el-icon-edit">修改</el-button>
      </div>
    </div>
    <div>
      <el-table
        ref="tableRef"
        :data="menuListData"
        style="width: 100%"
        row-key="id"
        :tree-props="{ children: 'menuChildren', hasChildren: 'menuChildren.length > 0' }"
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop="menuTitle"
          label="菜单名称"
          width="180">
        </el-table-column>
        <el-table-column
          label="图标"
          width="100">
          <template slot-scope="scope">
            <i :class="`el-icon ${scope.row.icon}`"></i>
          </template>
        </el-table-column>
        <el-table-column
          prop="menuPath"
          label="路径"
          width="180">
        </el-table-column>
        <el-table-column
          prop="menuSort"
          label="排序等级"
          width="180">
        </el-table-column>
        <el-table-column
          label="缓存"
          width="180">
          <template slot-scope="scope">
            {{ scope.row.cache == 0 ? '是' : '否' }}
          </template>
        </el-table-column>
        <el-table-column
          label="隐藏"
          width="180">
          <template slot-scope="scope">
            {{ scope.row.hidden == 0 ? '是' : '否' }}
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间">
        </el-table-column>
        <el-table-column
          fixed="right"
          align="center"
          width="150"
          label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="addOrUpdateHandle(scope.row.id)">编辑</el-button>
            <el-button type="text" size="mini" @click="deleteHandle(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <menu-add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="loadData"></menu-add-or-update>
  </div>
</template>

<script>
import { getMenuList } from '@/api/authority/menu'
import MenuAddOrUpdate from './MenuAddOrUpdate'
export default {
  components: {
    MenuAddOrUpdate
  },
  data() {
    return {
      addOrUpdateVisible: false,
      searchCondition: {
        menuTitle: '',
        time: '',
      },
      menuListData: [],

      multipleSelection: [],
    }
  },
  mounted() {
    this.loadData();
  },
  methods: {
    addOrUpdateHandle (id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      })
    },
    loadData() {
      getMenuList().then(resp => {
        console.log(resp);
        this.menuListData = resp.data;
      })
    },

    // 表格选择
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
  }
}
</script>

<style lang="scss" scoped>
 .menu-admin-manage {
   margin: 30px;
 }
</style>

<style lang="scss">
  
</style>