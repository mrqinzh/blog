<template>
  <div>
    <el-pagination
      background
      @current-change="handleCurrentChange"
      :current-page="currentPageNum"
      :page-size="pageSize"
      layout="total,slot,prev"
      :total="total"
    >
      <el-button :disabled="firstPageBtnDisabled" class="first-pager" @click="toFirstPage">首页</el-button>
    </el-pagination>
    <el-pagination
      background
      @current-change="handleCurrentChange"
      :current-page="currentPageNum"
      :page-size="pageSize"
      layout="pager,next,slot,jumper"
      :total="total"
    >
      <el-button :disabled="lastPageBtnDisabled" class="last-pager" @click="toLastPage">末页</el-button>
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentPageNum: this.currentPage,
      firstPageBtnDisabled: true,
      lastPageBtnDisabled: false,
      lastPageNum: Math.ceil(this.total / this.pageSize)
    };
  },
  props: {
    currentPage: {
      type: Number,
      default: 1
    },
    pageSize: {
      type: Number,
      default: 10
    },
    total: {
      type: Number,
      default: 0
    }
  },
  watch: {
    total(newVal, oldVal) {
      this.total = newVal;
      this.lastPageNum = Math.ceil(newVal / this.pageSize);
    }
  },
  methods: {
    handleCurrentChange(val) {
      this.firstPageBtnDisabled = val === 1 ? true : false;
      this.lastPageBtnDisabled = val === this.lastPageNum ? true : false;
      this.currentPageNum = val;
      this.$emit("handleCurrentChangeSub", val);
    },
    toFirstPage(val) {
      this.currentPageNum = 1;
      this.handleCurrentChange(1);
    },
    toLastPage(val) {
      this.currentPageNum = this.lastPageNum;
      this.handleCurrentChange(this.lastPageNum);
    }
  },
};
</script>

<style>
.el-pagination {
  float: left;
}
</style>