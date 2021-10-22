<template>
  <el-dialog title="添加菜单" :visible.sync="visible" :center="true" :close-on-click-modal="false" :modal-append-to-body="false" :append-to-body="true" class="menuDialog">
    <el-form :model="menuForm" ref="menuForm" :inline="true" class="menuForm">
      <el-form-item label="菜单Title">
        <el-input v-model="menuForm.menuTitle" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="菜单可见">
        <el-switch
          v-model="menuForm.hidden"
          active-color="#13ce66"
          inactive-color="#ff4949">
        </el-switch>
      </el-form-item>
      <el-form-item label="菜单路径">
        <el-input v-model="menuForm.menuPath" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="是否缓存">
        <el-switch
          v-model="menuForm.cache"
          active-color="#13ce66"
          inactive-color="#ff4949">
        </el-switch>
      </el-form-item>
      <el-form-item label="菜单图标">
        <el-select v-model="menuForm.icon" style="width: 177px;">
        </el-select>
      </el-form-item>
      <el-form-item label="菜单排序">
        <el-input-number v-model="menuForm.menuSort" :min="1" :max="10"></el-input-number>
      </el-form-item>
      <el-form-item label="组件名称">
        <el-input v-model="menuForm.componentName"></el-input>
      </el-form-item>
      <el-form-item label="组件路径">
        <el-input v-model="menuForm.componentPath"></el-input>
      </el-form-item>
      <el-form-item label="上级目录">
        <el-popover
          placement="bottom"
          width="200"
          trigger="click"
          v-model="parentDirVisible">
          <el-tree :data="data" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
          <el-input slot="reference" v-model="menuForm.parentDir" clearable style="width: 177px;"></el-input>
        </el-popover>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="visible = false">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      data: [{
        id: 1,
        label: '一级 1',
        children: [{
          id: 4,
          label: '二级 1-1',
          children: [{
            id: 9,
            label: '三级 1-1-1'
          }]
        }]
      }, {
        id: 2,
        label: '一级 2',
        children: [{
          id: 5,
          label: '二级 2-1'
        }]
      }],
      defaultProps: {
        children: 'children',
        label: 'label'
      },


      parentDirVisible: false,
      visible: false,
      menuForm: {
        id: '',
        menuTitle: '',
        menuPath: '',
        icon: '',
        hidden: false,
        cache: true,
        menuSort: '',
        componentName: '',
        componentPath: '',
        parentDir: '',
      }
    }
  },
  methods: {
    handleNodeClick(data) {
      this.parentDirVisible = false;
      this.menuForm.parentDir = data.label;
      console.log(data);
    },
    init (id) {
      this.menuForm.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs['menuForm'].resetFields();
        if (this.menuForm.id != 0) {
          // Todo axios
        }
      })
    },
  }
}
</script>

<style lang="scss" scoped>
  .menuDialog {
    width: 60%;
    margin: 0 auto;
    overflow: hidden;
    .menuForm {
      
    }
  }
  

</style>