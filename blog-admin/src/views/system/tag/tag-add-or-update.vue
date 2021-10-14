<template>
  <el-dialog
    :title="!dataForm.commentId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="标签名称" prop="tagName">
      <el-input v-model="dataForm.tagName" placeholder="标签名称"></el-input>
    </el-form-item>
    <el-form-item label="标签图" prop="tagName">
      <img-upload :img="dataForm.tagImg" @uploadimg="tagImgUpload" />
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { getById } from '@/api/tag'
import ImgUpload from '@/components/web/upload/ImgUpload.vue'
  export default {
  components: { ImgUpload },
    data () {
      return {
        visible: false,
        dataForm: {
          tagName: '',
          tagImg: '',
        }
      }
    },
    methods: {
      tagImgUpload(resp) {
        this.dataForm.tagImg = resp.data;
      },
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            getById(id).then(resp => {
              // console.log(resp);
              this.dataForm.tagName = resp.data.tagName;
              this.dataForm.tagImg = resp.data.tagImg;
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        
      }
    }
  }
</script>

<style lang="scss" scoped>

</style>