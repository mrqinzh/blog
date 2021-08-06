<template>
  <div class="order-comment">
    <div style="margin-bottom: 10px;font-size: 15px">最新评论</div>
    <div>
      <ul>
        <li v-for="(item, index) in commentList" :key="index">
          <span style="color: #67C23A">{{ item.comment_time.substring(0, 10) }}</span>
          <span>xx 评论了</span>
          &nbsp;
          <span><a @click="showBlog(item.article_id)">{{ item.article.article_title.substring(0, 10) }}...</a></span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { getRequest } from '@/utils/api'
export default {
  data() {
    return {
      commentList: [],
    }
  },
  methods: {
    getData() {
      getRequest('/comment/list').then(resp => {
        console.log(resp);
        this.commentList = resp.data.body;
      });
    },
    // 展示博客
    showBlog(id) {
      let href = this.$router.resolve({path: '/blog', query: {article_id: id}});
      window.open(href.href, '_blank');
    },
  },
  mounted() {
    this.getData();
  },
}
</script>

<style>
  .order-comment{
    line-height: 1.7em;
    margin: 30px 0;
    padding: 10px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    transition: all 0.4s ease-in;
  }
  .order-comment:hover {
    transform: translate(0, -10px);
  }
</style>