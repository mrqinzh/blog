<template>
  <div class="tag-card">
    <div class="card-title">
      <blockquote>
        <span style="margin-left: 5px;font-size: 18px;">标签库</span>
      </blockquote>
    </div>
    <div class="card-body">
      <span v-for="(tag, index) in tags" :key="index">
        <a-tag :color="index%2===0 ? index%3===0 ? 'blue' : 'purple' : 'green'">{{ tag.tagName }}</a-tag>
        <a-divider type="vertical" />
      </span>
    </div>
  </div>
</template>

<script>
import { list } from '@/api/tag'
export default {
  data() {
    return {
      tags: [],
    }
  },
  methods: {
    loadData() {
      list().then(resp => {
        // console.log(resp);
        this.tags = resp.data;
      });
    }
  },
  mounted() {
    this.loadData();
  }
}
</script>

<style lang="scss">
 .tag-card {
   background-color: white;
   margin: 30px 0px;
   padding: 15px;
   box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
   transition: all 0.3s ease-in;
   .card-body {
     margin: 10px 0px;
     span {
       margin: 5px 0px;
     }
   }
   &:hover {
      transform: translate(0, -10px);
      box-shadow: 0 2px 12px 0 rgba(189, 102, 197, 0.6);
   }
 }
</style>