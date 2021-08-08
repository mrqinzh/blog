<template>
  <el-dialog
    :title="!dataForm.commentId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="所属用户编号" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="所属用户编号"></el-input>
    </el-form-item>
    <el-form-item label="评论内容" prop="commentContent">
      <el-input v-model="dataForm.commentContent" placeholder="评论内容"></el-input>
    </el-form-item>
    <el-form-item label="所属电影编号" prop="movieId">
      <el-input v-model="dataForm.movieId" placeholder="所属电影编号"></el-input>
    </el-form-item>
    <el-form-item label="评论时间" prop="commentTime">
      <el-input v-model="dataForm.commentTime" placeholder="评论时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          commentId: 0,
          userId: '',
          commentContent: '',
          movieId: '',
          commentTime: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '所属用户编号不能为空', trigger: 'blur' }
          ],
          commentContent: [
            { required: true, message: '评论内容不能为空', trigger: 'blur' }
          ],
          movieId: [
            { required: true, message: '所属电影编号不能为空', trigger: 'blur' }
          ],
          commentTime: [
            { required: true, message: '评论时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.commentId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.commentId) {
            this.$http({
              url: this.$http.adornUrl(`/movie/comment/info/${this.dataForm.commentId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.comment.userId
                this.dataForm.commentContent = data.comment.commentContent
                this.dataForm.movieId = data.comment.movieId
                this.dataForm.commentTime = data.comment.commentTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/movie/comment/${!this.dataForm.commentId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'commentId': this.dataForm.commentId || undefined,
                'userId': this.dataForm.userId,
                'commentContent': this.dataForm.commentContent,
                'movieId': this.dataForm.movieId,
                'commentTime': this.dataForm.commentTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>