<template>
  <div class="menu-admin-manage">
    菜单管理
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
      <span style="margin: 20px 0;">
        <el-button type="primary" icon="el-icon-plus" @click="addOrUpdateHandle">添加</el-button>
        <el-button type="success" icon="el-icon-edit">修改</el-button>
      </span>
    </div>
    <div>
      <el-table
        :data="menuListData"
        style="width: 100%">
        <el-table-column
          prop="date"
          label="日期"
          width="180">
        </el-table-column>
      </el-table>
    </div>

    <menu-add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="loadData"></menu-add-or-update>
  </div>
</template>

<script>
import { menuList } from '@/api/authority/menu'
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
      menuListData: []
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
      menuList().then(resp => {
        console.log(resp);
        this.menuListData = resp.data;
      })
    }
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