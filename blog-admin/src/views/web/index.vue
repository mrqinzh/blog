<template>
  <div>
    <!-- 页面头部区域 -->
    <el-header>
      <MyHeader></MyHeader>
    </el-header>
    
    
    <!-- 页面主区域 -->
    <el-main class="web-main">
      <router-view />
    </el-main>

    <!-- 页面脚步区域 -->
    <el-footer>
      <MyFooter></MyFooter>
    </el-footer>
  </div>
</template>

<script>
import MyHeader from '@/components/web/common/MyHeader'
import MyFooter from '@/components/web/common/MyFooter'
export default {
    components: {
      'MyHeader': MyHeader,
      'MyFooter': MyFooter,
    }
}
</script>

<style lang="scss">
  .el-header {
    padding: 0;
  }
  .el-footer {
    padding: 0;
  }
</style>

<style lang="scss" scoped>
  .web-main {
    min-height: 700px;
  }
</style>
