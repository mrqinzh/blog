<template>
  <div>
    <el-row :gutter="10">
      <el-col :xs="6" :sm="6" :md="6" :lg="6" :xl="6">
        <div class="hidden-xs-only">
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>留言</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
        <div class="animate__animated animate__fadeInUp">
          <!-- 输入留言内容 -->
          <div>
            <el-input
              type="textarea"
              placeholder="请输入内容"
              v-model="msgContent"
              maxlength="100"
              show-word-limit
              :rows="6"
              v-loading="loading"
              >
            </el-input>
            <el-button type="primary" style="float: right;margin: 20px 0" @click="addMsg()">发表</el-button>
          </div>
          <hr>
          <!-- 留言信息 -->
          <div style="min-height: 500px">
            <div style="font-size: 17px;margin-top: 20px" v-for="(item, index) in user_comments" :key="index">
              <p>
                <el-avatar :src="item.user.head_img"></el-avatar>&nbsp;&nbsp;
                <span>{{item.user.uname}}</span>
                <span style="float: right;">时间：{{item.msg_time}}</span>
              </p>
              <p style="margin: 10px 45px"><i class="el-icon-chat-line-round"></i> ==> {{item.msg_comment}}</p>
            </div>
          </div>
          <!-- 分页 -->
          <div style="text-align: center;margin: 0 0 20px 0">
            <el-pagination
              v-if="totalCount > 10"
              background
              layout="total, prev, pager, next"
              :page-size="pageSize"
              :total="totalCount"
              :current-page="currentPage"
              @current-change="changePageNum">
            </el-pagination>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { postRequest, getRequest } from '@/utils/api'
export default {
  data() {
    return {
      loading: false,
      msgContent: '',
      user_comments: [],
      // 分页参数
      totalCount: 0,
      currentPage: 1,
      pageSize: 10,
    }
  },
  methods: {
    // 加载所有留言信息
    loadMsgs(currentPage, pageSize) {
      getRequest(`/comment/all/${currentPage}/${pageSize}`).then(resp => {
        // console.log(resp);
        this.user_comments = resp.data.list;
        this.totalCount = resp.data.count; //获取数据行数
      },
      error => {
        this.$message.error('留言加载失败了，服务器好像出了点问题')
      })
    },
    // 页码变更
    changePageNum(val) {
      this.currentPage = val;
      this.loadMsgs(this.currentPage, this.pageSize);
    },
    // 新增留言
    addMsg() {
      if (this.msgContent === ""){
        this.$message.warning("内容不能为空啊");
        return;
      };
      this.loading = true;
      postRequest('/comment/addMsg',{
        msg_comment: this.msgContent,
        msg_time: Date.parse(new Date())
      }).then(resp => {
        // console.log(resp);
        this.$message.success(resp.data.message);
        this.loadMsgs(this.currentPage,this.pageSize);
        this.loading = false;
      });
      this.msgContent = "";
      this.loading = false;
    },
  },
  mounted() {
    this.loadMsgs(this.currentPage,this.pageSize);
  },
}
</script>

<style>

</style>