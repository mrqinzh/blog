<template>
  <el-dialog
    title='修改'
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="配置名称">
      <label>{{configKeyMap.get(dataForm.configKey)}}</label>
    </el-form-item>
    <el-form-item label="配置值" prop="configValue">
        <el-input v-model="dataForm.configValue" placeholder="配置值"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { keys, configKeyMap, getConfigByKeys, updateConfig } from '@/api/authority/sys-config'

  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: '',
          configKey: '',
          configValue: '',
        },

        configKeyMap: new Map,
      }
    },
    methods: {
      init (configKey) {
        this.configKeyMap = configKeyMap;
        this.dataForm.configKey = configKey;

        this.visible = true;
        this.$nextTick(() => {
            this.$refs['dataForm'].resetFields()
            getConfigByKeys(this.dataForm.configKey).then(resp => {
                this.dataForm = resp.data[0];
            })
        })
      },
      // 表单提交
      dataFormSubmit () {
        let sysConfig = {
            id: this.dataForm.id,
            configKey: this.dataForm.configKey,
            configValue: this.dataForm.configValue,
        }
        updateConfig(sysConfig).then(resp => {
            // console.log(resp);
            if (resp.success) {
                this.$message.success("操作成功");
                this.$emit('refreshDataList');
                this.visible = false;
            }
        })
      }
    }
  }
</script>