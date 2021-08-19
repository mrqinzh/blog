<template>
  <!-- 头部滚动栏 -->
  <div class="animate__animated animate__fadeInUp notice">
    <i class="layui-icon layui-icon-speaker" style="margin-left: 10px;font-size: 30px;color: #c26565"></i>
    <div class="roll">
      <ul>
        <li v-for="(item, index) in ulList" 
        :key="item.id" 
        :class="!index && play ? 'toUp' : ''">
          <span>{{item.msg}}</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {

  data() {
    return {
      play: false,
      ulList: [
        { msg: '只要学不死，就往死里学。当你牛逼到一定程度时，你就有了话语权。累了看看父母，倦了想想未来。' },
        { msg: '不同的人，即使站在同一个地方，透过各自的人生，看到的风景也有所不同。' },
        { msg: '曾经我以为：孤独是世界上只剩一个人。现在我认为：孤独是自己竟能成为一个世界。' },
      ],
    }
  },

  methods: {
    // 头部公告滚动
    startPlay() {
      let that = this
      that.play = true //开始播放
      setTimeout(() => {
        that.ulList.push(that.ulList[0]) //将第一条数据塞到最后一个
        that.ulList.shift() //删除第一条数据
        that.play = false //暂停播放
      }, 500)
    },
  }, 
  mounted() {
    setInterval(this.startPlay, 4000);
  }
}
</script>

<style>
  .notice {
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    background-image: linear-gradient(-225deg, #FFFEFF 0%, #D7FFFE 100%);
  }
  /* 滚动栏 */
  .roll {
    display: inline-block;
    height: 21px;
    position: relative;
    overflow: hidden;
    margin-left: 10px;
  }
  .roll ul {
    list-style: none;
    overflow: hidden;
    height: 20px;
    padding: 0;
    margin: 0;
  }
  .roll li {
    text-align: left;
    height: 20px;
    line-height: 20px;
  }
  .toUp {
    margin-top: -20px;
    transition: all 1s;
  }

</style>