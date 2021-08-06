<template>
  <div id="errorpage">
    <el-col :span="24">
      <i class="layui-icon layui-icon-face-surprised" style="font-size: 300px;color: #393e46"></i>
      <div class="err-text">
        <h1>
          <span class="layui-anim layui-anim-loop layui-anim-">4</span>
          <span class="layui-anim layui-anim-rotate layui-anim-loop">0</span>
          <span class="layui-anim layui-anim-loop layui-anim-">4</span>
        </h1>
      </div>
    </el-col>
  </div>
</template>

<script>
export default {
  data() {
    return {
      
    }
  },
  methods: {
    
  },
  mounted() {
    
  },
}
</script>

<style>
  #errorpage {
    height: 600px;
    text-align: center;
  }
  .err-text {
    width: 30%;;
    margin: 30px auto;

    border-top: 5px solid #409EFF;
  }
  .err-text h1 {
    font-size: 100px;
    line-height: 100px;
    color: #409EFF;
  }
  .err-text h1 span{
    display: inline-block;
  }

</style>