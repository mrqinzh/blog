<template>
  <div id="about" class="animate__animated animate__fadeIn">
    <div class="content">
      <el-avatar :src="resultMap.get('DEFAULT_AVATAR_LINK')" :size="100" style="display: inline-block;"></el-avatar>
      <div>
        <div style="margin-top: 10px;">自我简介</div>
        <ul class="info">
          <li><span><a-icon type="user" /> {{ user.name }}</span></li>
          <li><span><a-icon type="man" /> {{ user.sex }}</span></li>
          <li><span><a-icon type="environment" /> {{ user.address }}</span></li>
          <li><span><a-icon type="bug" /> {{ user.job }}</span></li>
          <li><span><a-icon type="message" /> {{ user.msg }}</span></li>
        </ul>
      </div>
    </div>

  </div>
  
</template>

<script>
import { initSystemConfigs } from './common'

export default {
  components: {
    
  },
  data() {
    return {
      user: {
        name: '秦志宏',
        avatar: 'http://mrqinzh.com:9090/img/avatar.jpg',
        sex: '男',
        address: '上海市闵行区',
        job: '00年的Java程序猿',
        msg: '我只想说：yyds!没有其他想说的了。。。字数不够，废话来凑。看看能不能把这行写满，还差一点点就成功了，我们是冠军，yes！阳光、积极、快乐、开心、那我走，。、？',
      },
      resultMap: new Map,
    }
  },
  mounted() {
    this.initConfigs();
  },
  methods: {
    initConfigs() {
      let param = 'DEFAULT_AVATAR_LINK';
      initSystemConfigs(param).then(resp => {
        this.resultMap = resp;
      });
    }
  }
}
</script>

<style lang="scss" scoped>
  #about .content {
    position: relative;
    width: 60%;
    height: 100%;
    margin: 30px auto;
    padding: 20px;
    background-color: white;
    font-size: 18px;
    .info {
      line-height: 2em;
      font-family: 楷体;
    }
  }

</style>