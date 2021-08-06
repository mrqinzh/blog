<template>
  <div id="studypage" class="animate__animated animate__fadeIn" v-loading="loading">
    <el-table
    :data="tableData"
    :border="true"
    stripe
    style="width: 100%">
      <el-table-column
        prop="article.article_title"
        label="文章标题"
        width="150">
      </el-table-column>
      <el-table-column
        prop="comment_content"
        label="评论内容">
      </el-table-column>
      <el-table-column
        prop="comment_time"
        label="评论时间"
        width="180">
      </el-table-column>
      <el-table-column label="操作" width="90">
        <template slot-scope="scope">
            <el-popconfirm
              title="这是你的评论，确定要删除吗？"
              @confirm="tableDelete(scope.$index, scope.row)"
            >
              <el-button
                slot="reference"
                size="mini"
                type="danger"
                >删除</el-button>
            </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px">
      <!-- 分页 -->
      <el-pagination
        background
        layout="prev, pager, next"
        :page-size="pageSize"
        :total="totalCount"
        :current-page="currentPage"
        @current-change="changePageNum">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { logOrNot } from '../../utils/utils'
import { getRequest, deleteRequest} from '../../utils/api'
export default {
  components: {
    
  },
  data() {
    return {
      totalCount: 0,
      currentPage: 1,
      pageSize: 5,

      loading: true,
      tableData: [],
    };
  },
  methods: {
    // 页码变更
    changePageNum(val) {
      this.currentPage = val;
      this.loading = true;
      this.loadMyComments(this.currentPage, this.pageSize);
    },
    // 表格删除时间
    tableDelete(index, row) {
      // console.log(row.id);
      deleteRequest(`/comment/del/${row.id}`).then(resp => {
        // console.log(resp);
        this.tableData.splice(index,1);
        if(resp.data.message === 'success'){
          this.$message.success('删除成功');
          this.loadMyComments(this.currentPage, this.pageSize);
        }
      },
      e => {
        this.$message.info('删除失败');
      });
    },
    // 加载评论信息
    loadMyComments(currentPage, pageSize) {
      if(!logOrNot()){
        this.$message.error('未经授权，无法访问啊。-> -_-');
        return;
      };
      getRequest(`/comment/userComment/${currentPage}/${pageSize}`).then(resp => {
        // console.log(resp);
        this.tableData = resp.data.list;
        this.totalCount = resp.data.count;
        this.loading = false;
      },
      error => {
        // console.log(error);
        this.$message.error('服务器出错了，试试重新登录吧。');
      })
    },
  },
  mounted() {
    // this.loadMyComments(this.currentPage, this.pageSize);
  },

}
</script>

<style>
  
</style>