<template>
  <div class="myfooter">
    <div class="foot-left">
      <img src="@/assets/static/img/beian.png" />
      <a style="line-height:20px;color: white" target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=50023002020179">
        渝公网安备 50023002020179号
      </a>
      <br>
      <a style="color: white;" href="https://beian.miit.gov.cn" target="_blank">渝ICP备2021004455号</a>
    </div>
    <div class="foot-right">
      <span>Copyright - 作者->Mr.QinZH @ 2021-05-06</span><br>
      <span>QQ: 1552589784</span>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <span>
        <a href="https://github.com/mrqinzh"><a-icon type="github" theme="filled" /></a>
        <a style="color: inherit" href="https://account.aliyun.com/login/qr_login.htm" target="_blank"><a-icon type="aliyun" /></a>
      </span>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
  .myfooter {
    position: relative;
    width: 100%;
    margin-top: 100px;
    font-family: 楷体;
    font-size: 18px;
    line-height: 2em;
    color: white;
    background-color: #606266;
    height: 200px;
  }
  .foot-left {
    position: relative;
    display: inline-block;
    width: 50%;
    height: 100%;
    padding: 20px 0 0 20%;
  }
  .foot-right {
    display: inline-block;
  }
</style>