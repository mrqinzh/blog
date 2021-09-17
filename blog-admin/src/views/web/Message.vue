<template>
  <!-- banner -->
  <div class="message-banner">
    <!-- 弹幕输入框 -->
    <div class="message-container">
      <h1 class="message-title">留言板</h1>
      <div class="animate__animated animate__fadeInUp message-input-wrapper">
        <input
            style="color: #1b1c1d; width: 20%"
            v-model="messageNickname"
            @click="show = true"
            @keyup.enter="addToList"
            placeholder="昵称"
        />
        <input
            style="color: #1b1c1d;"
            v-model="messageContent"
            @click="show = true"
            @keyup.enter="addToList"
            placeholder="说点什么吧"
        />
        <el-button
            round
            type="info"
            class="ml-3 animate__animated animate__fadeInUp"
            @click="addToList"
            v-show="show"
        >
          发送
        </el-button>
      </div>
    </div>
    <!-- 弹幕列表 -->
    <div class="barrage-container">
      <vue-baberrage 
      :barrageList="barrageList"
      :loop="true">
        <template v-slot:default="slotProps">
          <span class="barrage-items">
            <img
                :src="slotProps.item.avatar"
                width="30"
                height="30"
                style="border-radius:50%"
            />
            <span class="ml-2">{{ slotProps.item.nickname }} :</span>
            <span class="ml-2">{{ slotProps.item.content }}</span>
          </span>
        </template>
      </vue-baberrage>
    </div>
  </div>
</template>

<script>
import { list, add } from '@/api/my-message'
import Vue from 'vue'
// import VueParticles from 'vue-particles' 
import { vueBaberrage, MESSAGE_TYPE } from 'vue-baberrage'

// Vue.use(VueParticles)  
Vue.use(vueBaberrage)

export default {
  name: "Message",
  
  data() {
    return {
      show: false,
      messageNickname: "",
      messageContent: "",
      barrageList: [],
      avatar: '',
    }
  },
  methods: {
    addToList() {
      if (this.messageNickname.trim() == ""){
        this.$message.error("昵称不能为空")
        return false;
      }
      if (this.messageContent.trim() == ""){
        this.$message.error("留言不能为空")
        return false;
      }
      //随机赋予头像(本地存放了30个头像)
      this.avatar = "http://mrqinzh.info:9090/img/random-avatars/" + "avatar" +Math.floor((Math.random() * 10) + 1) + ".png"

      var message = {
          avatar: this.avatar,
          nickname: this.messageNickname,
          content: this.messageContent,
          time: Math.floor(Math.random() * (10 - 7)) + 8,
      };
      this.barrageList.push(message);
      this.messageContent = "";

      add(message).then(resp => {
        // console.log(resp)        
      })

    },

    // 加载弹幕消息
    listMessage() {
      list().then(resp => {
        // console.log(resp)
        // this.barrageList = resp.data;
        this.barrageList = JSON.parse(JSON.stringify(resp.data));
      })
    }
  },
  mounted() {
    this.listMessage();
  },
};
</script>

<style scoped>
.message-banner {
  margin: 0;
  padding: 0;
  height: 90vh;
  background-image: url("../../assets/img/hutao.jpg");

  animation: header-effect 1s;
}
.message-title {
  color: #eee;
  animation: title-scale 1s;
}
.message-container {
  position: absolute;
  width: 360px;
  top: 35%;
  left: 0;
  right: 0;
  text-align: center;
  z-index: 5;
  margin: 0 auto;
  color: #fff;
}
.message-input-wrapper {
  display: flex;
  justify-content: center;
  height: 2.5rem;
  margin-top: 2rem;
}
.message-input-wrapper input {
  outline: none;
  width: 70%;
  border-radius: 20px;
  height: 100%;
  padding: 0 1.25rem;
  color: #eee;
  border: #fff 1px solid;
}
.message-input-wrapper input::-webkit-input-placeholder {
  color: #eeee;
}
.message-input-wrapper button {
  outline: none;
  border-radius: 20px;
  height: 100%;
  padding: 0 1.25rem;
  border: #fff 1px solid;
}
.barrage-container {
  position: absolute;
  top: 50px;
  left: 0;
  right: 0;
  bottom: 0;
  height: calc(100% -50px);
  width: 100%;
}
.barrage-items {
  background: rgb(0, 0, 0, 0.7);
  border-radius: 100px;
  color: #fff;
  padding: 5px 10px 5px 5px;
  align-items: center;
  display: flex;
}
.ml-3{
  margin-left: 12px!important;
}

.ml-2{
  margin-left: 8px!important;
}
</style>