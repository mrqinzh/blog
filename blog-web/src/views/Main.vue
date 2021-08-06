<template>
<div id="pageMain">
  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
    <!-- 页面头部区域 -->
    <MyHeader></MyHeader>
  </el-col>
  
  <!-- 页面主区域 -->
  <el-main style="min-height: 700px">

    <router-view/>
  </el-main>

  <!-- 页面脚步区域 -->
  <MyFooter></MyFooter>
</div>
</template>

<script>
import MyHeader from '@/components/common/MyHeader'
import MyFooter from '@/components/common/MyFooter'
export default {
    components: {
      'MyHeader': MyHeader,
      'MyFooter': MyFooter,
    },
    data() {
      return {
        loading: true,
        msg_data: [],
      };
    },
    methods: {  
      
    },
}
</script>

<style>
  
</style>
