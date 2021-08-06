<template>
  <div>
    <el-input
    v-model="val"
    maxlength="12"
    prefix-icon="el-icon-search"
    placeholder="请输入内容">
      <el-button @click="Search()" slot="append" icon="el-icon-search">搜索</el-button>
    </el-input>
  </div>
</template>

<script>
export default {
  data() {
    return {
      val: '',
    }
  },
  methods: {
    Search() {
      if(this.val === '') {
        this.$message.warning('啊这，搜了个寂寞 zzz');
        return;
      }
      this.val = '';
      this.$message.success('上当了，就不做功能，诶，我就是玩 => ^_^');
    }
  },
}
</script>

<style>

</style>