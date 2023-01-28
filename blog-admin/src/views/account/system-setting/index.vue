<template>
  <div class="sys-config-container">
    <!-- <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="输入标签名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="findByType(dataForm.key)">搜索</el-button>
      </el-form-item>
    </el-form> -->
    <el-table
      style="margin: 10px 0px;"
      :data="dataList"
      v-loading="dataListLoading">
      <el-table-column
        label="配置名称">
        <template slot-scope="scope">
          <label>{{configKeyMap.get(scope.row.configKey)}}</label>
        </template>
      </el-table-column>
      <el-table-column
        prop="configValue"
        label="值">
      </el-table-column>
      <el-table-column
        fixed="right"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.configKey)">修改</el-button>
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
import { keys, configKeyMap, getSysConfig } from '@/api/authority/sys-config'

import AddOrUpdate from './config-add-or-update'
export default {
  components: {
    AddOrUpdate,
  },
  data() {
    return {
      dataForm: {
        key: ''
      },
      dataList: [],
      configKeyMap,
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
    this.getDataList();
    this.configKeyMap = configKeyMap;
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
      // this.dataListLoading = true
      let pageVo = {
        'currentPage': this.currentPage,
        'pageSize': this.pageSize
      }
      getSysConfig(pageVo).then(resp => {
        this.dataList = resp.data.records;
        this.totalCount = resp.data.total;
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
    addOrUpdateHandle (configKey) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(configKey)
      })
    },
  }
}
</script>

<style lang="scss" scoped>
.sys-config {
  &-container {
    margin: 30px;
  }
}
</style>