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
import { getById, add, update } from '@/api/tag'
import ImgUpload from '@/components/web/upload/ImgUpload.vue'
  export default {
  components: { ImgUpload },
    data () {
      return {
        visible: false,
        dataForm: {
          id: '',
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
            getById(this.dataForm.id).then(resp => {
              // console.log(resp);
              this.dataForm = resp.data;
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        if (this.dataForm.tagImg == '') {
          this.$message.warning("标签图，必须上传");
          return;
        }
        
        if (this.dataForm.id) {
          let param = {
            id: this.dataForm.id,
            tagName: this.dataForm.tagName,
            tagImg: this.dataForm.tagImg,
          }
          update(param).then(resp => {
            // console.log(resp);
            if (resp.success) {
              this.$message.success("操作成功");
            }
          })
        } else {
          let param = {
            tagName: this.dataForm.tagName,
            tagImg: this.dataForm.tagImg,
          }
          add(param).then(resp => {
            // console.log(resp);
            if (resp.success) {
              this.$message.success("操作成功");
            }
          })
        }
      }
    }
  }
</script>

<style lang="scss" scoped>

</style>