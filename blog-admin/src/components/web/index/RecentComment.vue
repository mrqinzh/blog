<template>
  <div class="recent-comment">
    <span style="font-size: 18px;">
      <i class="el-icon-hot-water"></i>
      最新评论
    </span>
    <div class="recent-comment-body">
      <li v-for="(item, index) in commentList" :key="index">
        <a-space>
          <el-avatar size="small" :src="item.avatar"></el-avatar>
          <span style="font-size: 16px;color: #303133">{{ item.nickname }}</span>
          <span>{{ item.commentTime }}</span>
        </a-space>
        <br>
        <p>
          <span>：{{ item.commentContent }}</span>
        </p>
        <a-divider dashed v-show="index < commentList.length-1" />
        <!-- <span v-if="item.articleId">
          {{ item.nickname }}在{{ item.commentTime }} 对 <router-link :to="{name: 'Detail', params: {articleId: item.articleId}}" target="_blank">文章</router-link> 进行了评论。
        </span> -->
      </li>
    </div>
  </div>
</template>

<script>
import { getAllList } from '@/api/comment'
export default {
  data() {
    return {
      commentList: []
    }
  },
  mounted() {
    this.loadData();
  },
  methods: {
    loadData() {
      let param = {
        currentPage: 1,
        pageSize: 5
      }
      getAllList(param).then(resp => {
        // console.log(resp);
        this.commentList = resp.data.rows;
      })
    }
  }
}
</script>

<style lang="scss">
  .recent-comment {
    background-color: white;
    margin: 30px 0px;
    padding: 15px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease-in;
    &:hover {
      transform: translate(0, -10px);
      box-shadow: 0 2px 12px 0 rgba(189, 102, 197, 0.6);
    }
    .recent-comment-body {
      padding: 0 5px;
      margin-top: 5px;
      .ant-divider {
        margin: 0;
      }
      li {
        list-style: none;
      }
    }
    
  }

</style>