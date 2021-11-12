<template>
  <!-- banner -->
  <div class="message-container">
    <div class="write-comment animate__animated animate__fadeIn">
      <el-input
        type="textarea"
        placeholder="有什么想说的尽管说吧。^_^"
        maxlength="100"
        show-word-limit
        :rows="5"
        v-model="commentForm.commentContent">
      </el-input>
      <el-input v-model="commentForm.nickname" placeholder="请输入你霸气的昵称。。。>_>" suffix-icon="el-icon-user" style="margin: 10px 0;width: 100%"></el-input>
      <el-button @click="addMessage" type="primary" icon="el-icon-edit" size="small" style="float: right">留言</el-button>
    </div>

    <div class="time-line">
      <a-timeline mode="alternate">
        <a-timeline-item v-for="(item, index) in messageList" :key="index"  :class="index % 2 == 0 ? 'animate__animated animate__fadeInRight' : 'animate__animated animate__fadeInLeft'">
          <img slot="dot" :src="item.avatar" style="width: 20px;height: 20px">
          <span style="font-size: 14px;">{{ item.commentTime }}</span>
          <span style="font-weight: bold ">{{ item.nickname }}</span>
          <br>
          <span style="font-size: 14px">{{ item.commentContent }}</span>
        </a-timeline-item>
      </a-timeline>
    </div>
  </div>
</template>

<script>
import { add, getMessageList } from '@/api/comment'

export default {
  name: "Message",
  data() {
    return {
      messageList: [],

      commentForm: {
        nickname: '',
        commentContent: '',
      }
    }
  },
  methods: {
    loadData() {
      getMessageList().then(resp => {
        // console.log(resp)
        this.messageList = resp.data;
      })
    },
    addMessage() {
      if (this.commentForm.nickname == '') {
        this.$message.warning('你还没有输入昵称哦 => `_`');
        return;
      }
      if (this.commentForm.commentContent == '') {
        this.$message.warning('写点东西了来吧 => `_`');
        return;
      }
      let param = {
        nickname: this.commentForm.nickname,
        commentContent: this.commentForm.commentContent,
        type: 2
      }
      add(param).then(resp => {
        // console.log(resp);
        this.$message.success('留言成功');
        this.loadData();
        this.commentForm.nickname = '';
        this.commentForm.commentContent = '';

      })
    }
  },
  mounted() {
    this.loadData();
  },
};
</script>

<style lang="scss" scoped>
.message-container {
  margin: 30px auto;
  width: 30%;
  .time-line {
    margin-top: 70px;
    span {
      font-size: 17px;
      margin: 0 2px;
    }
  }
}
</style>