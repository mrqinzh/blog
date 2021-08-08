<template>
  <div id="about" class="animate__animated animate__fadeIn">

    <div class="content">
      <div>
        <el-avatar :src="user.avatar" :size="100" style="display: inline-block;"></el-avatar>
        <el-upload
          class="file"
          list-type="picture-card">
          <i class="el-icon-plus"></i>
        </el-upload>
      </div>
      <div>
        <div style="margin-top: 10px;">自我介绍</div>
        <ul class="info">
          <li><span><a-icon type="user" /> {{ user.name }}</span></li>
          <li><span><a-icon type="man" /> {{ user.sex }}</span></li>
          <li><span><a-icon type="environment" /> {{ user.address }} &nbsp;&nbsp;&nbsp;&nbsp;<a-icon type="home" /> 酆都</span></li>
          <li><span><a-icon type="bug" /> {{ user.job }}</span></li>
          <li><span><a-icon type="message" /> {{ user.msg }}</span></li>
        </ul>
      </div>
    </div>

  </div>
  
</template>

<script>
export default {
  data() {
    return {
      user: {
        name: '秦志宏',
        avatar: 'http://localhost:9090/img/avatar.jpg',
        sex: '男',
        address: '重庆市渝北区',
        job: '00年的Java程序猿',
        msg: '我只想说：yyds!没有其他想说的了。。。字数不够，废话来凑。看看能不能把这行写满，还差一点点就成功了，我们是冠军，yes！',
      },
    }
  },
  mounted() {

  },
  
}
</script>

<style lang="scss">
  #about .content {
    width: 60%;
    height: 100%;
    margin: 30px auto;
    padding: 20px;
    background-color: white;
    font-size: 18px;
    .file {
      float: right;
    }
    .info {
      margin-left: 30px;
      line-height: 2em;
      font-family: 楷体;
    }
  }

</style>