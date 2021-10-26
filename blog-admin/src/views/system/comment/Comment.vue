<template>
  <div class="comment-container">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="searchForm.nickname" placeholder="昵称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.ip" placeholder="ip地址" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchForm.type" placeholder="评论/留言" clearable>
          <el-option value="1" label="评论"></el-option>
          <el-option value="2" label="留言"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchForm.time"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd HH:mm:ss"
          align="right">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="search()">搜索</el-button>
        <el-button icon="el-icon-refresh-left" @click="resetSearch" type="info">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table
      style="margin: 10px 0px;"
      :data="dataList"
      v-loading="dataListLoading">
      <el-table-column
        prop="id"
        label="评论编号">
      </el-table-column>
      <el-table-column
        ref="avatar"
        label="用户头像">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column
        prop="nickname"
        label="用户昵称">
      </el-table-column>
      <el-table-column
        prop="ip"
        label="用户ip">
      </el-table-column>
      <el-table-column
        prop="commentContent"
        label="评论内容">
      </el-table-column>
      <el-table-column
        prop="articleId"
        label="所属文章编号">
      </el-table-column>
      <el-table-column
        prop="commentTime"
        label="评论时间">
      </el-table-column>
      <el-table-column
        prop="type"
        label="评论/留言">
      </el-table-column>
      <el-table-column
        fixed="right"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="deleteHandle(scope.row.id)">删除</el-button>
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
  </div>
</template>

<script>
import { getAllList } from '@/api/comment';
export default {
  data() {
    return {
      searchForm: {
        nickname: '',
        ip: '',
        type: '',
        time: '',
      },
      dataList: [],
      currentPage: 1,
      pageSize: 10,
      totalCount: 0,
      dataListLoading: false,

    }
  },
  mounted() {
    this.getDataList()
  },
  methods: {
    resetSearch() {
      this.searchForm = {
        nickname: '',
        ip: '',
        type: '',
        time: '',
      }
      this.getDataList();
    },
    search() {
      this.currentPage = 1;
      this.pageSize = 10;
      this.getDataList();
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      let param = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
        nickname: this.searchForm.nickname,
        ip: this.searchForm.ip,
        type: this.searchForm.type,
        startTime: this.searchForm.time[0],
        endTime: this.searchForm.time[1],
      }
      // console.log(param);
      getAllList(param).then(resp => {
        // console.log(resp)
        this.dataList = resp.data.rows;
        this.dataList.forEach(e => {
          e.type = e.type == 1 ? '评论' : '留言'
        })
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
    // 删除
    deleteHandle(id) {
      this.$confirm(`确定对[id=${id}]进行删除操作？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // Todo axios
      })
    }
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