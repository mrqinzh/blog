<template>
  <!-- banner -->
  <div class="message-container">
    <div class="write-comment">
      <el-input
        type="textarea"
        placeholder="这里是要输入你的评论内容哦。^_^"
        maxlength="100"
        show-word-limit
        :rows="5"
        v-model="commentContent"
        >
      </el-input>
      <el-input v-model="nickname" placeholder="请输入你霸气的昵称。。。>_>" suffix-icon="el-icon-user"></el-input>
      <el-button @click="addComment(0)" type="primary" icon="el-icon-edit" size="small">发表评论</el-button>
    </div>

    <a-timeline mode="alternate">
      <a-timeline-item>Create a services site 2015-09-01</a-timeline-item>
      <a-timeline-item color="green">
        Solve initial network problems 2015-09-01
      </a-timeline-item>
      <a-timeline-item>
        <a-icon slot="dot" type="clock-circle-o" style="font-size: 16px;" />
        Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque
        laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto
        beatae vitae dicta sunt explicabo.
      </a-timeline-item>
      <a-timeline-item color="red">
        Network problems being solved 2015-09-01
      </a-timeline-item>
      <a-timeline-item>Create a services site 2015-09-01</a-timeline-item>
      <a-timeline-item>
        <a-icon slot="dot" type="clock-circle-o" style="font-size: 16px;" />
        Technical testing 2015-09-01
      </a-timeline-item>
    </a-timeline>
  </div>
</template>

<script>
import { list, add } from '@/api/my-message'

export default {
  name: "Message",
  data() {
    return {
      messageList: []
    }
  },
  methods: {
    loadData() {
      list().then(resp => {
        // console.log(resp)
        this.messageList = resp.data;
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
  margin: 0 auto;
  width: 30%;
  .write-comment {
    
  }
}
</style>