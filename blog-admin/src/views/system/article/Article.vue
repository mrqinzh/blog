<template>
  <div class="article-container">
    文章管理
  </div>
</template>

<script>
export default {
  name: 'Article',
  data() {
    return {
      articles: []
    }
  }

}
</script>

<style lang="scss" scoped>
.article {
  &-container {
    margin: 30px;
  }
}
</style>