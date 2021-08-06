<template>
  <div id="app">
    <!-- 回到顶部 -->
    <el-backtop>
      <el-tooltip content="回到顶部" placement="left">
        <a-icon type="rocket" />
      </el-tooltip>
    </el-backtop>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app',
}
</script>

<style>
  #app {
    color: rgba(0, 0, 0, 0.65);
    /* position: relative; */
    background-color: #fff;
    font-size: 14px;
    /* line-height: 1.5; */
  }

  /* 侧边滚动栏 */
  ::-webkit-scrollbar {
    width: 10px;
  }
  
  ::-webkit-scrollbar-track {
    --webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
    border-radius: 10px;
  }
  
  ::-webkit-scrollbar-thumb {
    border-radius: 10px;
    background: #e8e8e8;
    --webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.5);
  }
  
  ::-webkit-scrollbar-thumb:hover {
    background: #acb0b1;
    cursor: pointer;
  }

</style>
