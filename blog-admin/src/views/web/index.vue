<template>
  <div id="pageMain">
    <!-- 页面头部区域 -->
    <MyHeader></MyHeader>
    
    <!-- 页面主区域 -->
    <el-main style="min-height: 700px">
      <router-view />
    </el-main>

    <!-- 页面脚步区域 -->
    <MyFooter></MyFooter>
  </div>
</template>

<script>
import MyHeader from '@/components/web/common/MyHeader'
import MyFooter from '@/components/web/common/MyFooter'
export default {
    components: {
      'MyHeader': MyHeader,
      'MyFooter': MyFooter,
    },
    data() {
      return {
      };
    },
    methods: {  
      
    },
}
</script>

<style>
  
</style>
