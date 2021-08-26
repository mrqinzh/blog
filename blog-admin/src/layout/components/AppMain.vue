<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
     <!-- <router-view :key="key" />-->
      <keep-alive :include="cachedViews">
        <router-view />
      </keep-alive>
    </transition>
  </section>
</template>

<script>

export default {
  name: 'AppMain',
  data() {
    return {
      userId: this.$store.state.user.userId
    }
  },
  computed: {
    cachedViews() {
      return this.$store.state.tagsView.cachedViews
    },
    /*key() {
      return this.$route.path
    }*/
  },
  methods: {
    initWebSocket: function () {
        this.websock = new WebSocket("ws://localhost:9090/api/websocket/" + this.userId);
        this.websock.onopen = this.websocketonopen;
        this.websock.onerror = this.websocketonerror;
        this.websock.onmessage = this.websocketonmessage;
        this.websock.onclose = this.websocketclose;
    },
    websocketonopen: function () {
        console.log("WebSocket连接成功");
    },
    websocketonerror: function (e) {
        console.log("WebSocket连接发生错误");
    },
    // 接受数据
    websocketonmessage: function (e) {
        console.log(e);
        this.$message.success(e.data)
    },
    // 数据发送
    websocketsend(Data) {
        this.websock.send(Data)
    },
    websocketclose: function (e) {
        console.log("connection closed (" + e + ")");
    }
  },
  mounted() {
    this.initWebSocket();
  }
}
</script>

<style lang="scss" scoped>
.app-main {
  /*50 = navbar  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
}
.fixed-header + .app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 34 */
    min-height: calc(100vh - 84px);
  }

  .fixed-header+.app-main {
    padding-top: 84px;
  }
}
</style>
