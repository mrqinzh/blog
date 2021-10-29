<template>
  <el-dialog title="添加菜单" :visible.sync="visible" :center="true" :close-on-click-modal="false" :modal-append-to-body="false" :append-to-body="true" class="menuDialog">
    <el-form :model="menuForm" ref="menuForm" :inline="true" class="menuForm">
      <el-form-item label="菜单Title">
        <el-input v-model="menuForm.menuTitle" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="是否隐藏">
        <el-radio-group v-model="menuForm.hidden">
          <el-radio :label="0">隐藏</el-radio>
          <el-radio :label="1">显示</el-radio>
        </el-radio-group>
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
        <el-popover
          placement="bottom-start"
          width="450"
          trigger="click"
          @show="$refs['iconSelect'].reset()"
        >
          <el-input
            slot="reference"
            v-model="menuForm.icon"
            placeholder="请选择菜单图标"
            readonly
            style="cursor: pointer; width: 460px"
          >
            <template slot="prepend">
              <i
                v-if="menuForm.icon && menuForm.icon.includes('el-icon')"
                :class="menuForm.icon"
              />
              <svg-icon v-else :icon-class="menuForm.icon ? menuForm.icon : 'el-icon-search'" />
            </template>
          </el-input>
          <select-icon ref="iconSelect" @selected="selected" />
        </el-popover>
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
      <el-form-item label="上级菜单">
        <treeselect
          v-model="menuForm.parentId"
          :options="menuTree"
          :normalizer="normalizer"
          :show-count="true"
          style="width: 460px"
          placeholder="选择上级菜单"
        />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="menuFormSubmit">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import SelectIcon from '@/components/SelectIcon'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { getMenuList, addMenu, getMenuById, updateMenu } from '@/api/authority/menu'
export default {
  components: {
    SelectIcon,
    Treeselect
  },
  data() {
    return {
      menuTree: [],
      normalizer(node) {
        return {
          id: node.id,
          label: node.menuTitle,
          children: node.menuChildren && node.menuChildren.length > 0 ? node.menuChildren: 0,	 // 自定义下级chidlren字段
        }
      },
      visible: false,
      menuForm: {
        id: '',
        menuTitle: '',
        menuPath: '',
        icon: '',
        hidden: 1,
        cache: false,
        menuSort: '',
        componentName: '',
        componentPath: '',
        parentId: null,
      }
    }
  },
  methods: {
    refreshMenuForm() {
      this.menuForm = {
        id: '',
        menuTitle: '',
        menuPath: '',
        icon: '',
        hidden: 1,
        cache: false,
        menuSort: '',
        componentName: '',
        componentPath: '',
        parentId: null,
      }
    },
    loadMenuList() {
      getMenuList().then(resp => {
        console.log(resp)
        this.menuTree = resp.data;
      })
    },
    init (id) {
      this.menuForm.id = id || 0;
      this.visible = true;
      this.loadMenuList();
      this.$nextTick(() => {
        this.$refs['menuForm'].resetFields();
        if (this.menuForm.id != 0) {
          // Todo axios
          getMenuById(this.menuForm.id).then(resp => {
            console.log(resp)
            this.menuForm = resp.data;
          })
        }
      })
    },
    menuFormSubmit() {
      if (this.menuForm.menuTitle == '') {
        this.$message.error('请输入菜单标题')
        return;
      }
      if (this.menuForm.id != 0) {
        let param = {
          id: this.menuForm.id,
          cache: this.menuForm.cache ? 1 : 0,
          componentName: this.menuForm.componentName,
          componentPath: this.menuForm.componentPath,
          hidden: this.menuForm.hidden,
          icon: this.menuForm.icon,
          menuPath: this.menuForm.menuPath,
          menuTitle: this.menuForm.menuTitle,
          parentId: this.menuForm.parentId,
          menuSort: this.menuForm.menuSort
        }
        updateMenu(param).then(resp => {
          console.log(resp);
          if (resp.success) {
            this.$emit('refreshDataList');
            this.visible = false;
            this.refreshMenuForm();
            this.$message.success("修改成功");
          }
        })
      } else {
        // 添加
        let param = {
          cache: this.menuForm.cache ? 1 : 0,
          componentName: this.menuForm.componentName,
          componentPath: this.menuForm.componentPath,
          hidden: this.menuForm.hidden,
          icon: this.menuForm.icon,
          menuPath: this.menuForm.menuPath,
          menuTitle: this.menuForm.menuTitle,
          parentId: this.menuForm.parentId,
          menuSort: this.menuForm.menuSort
        }
        addMenu(param).then(resp => {
          console.log(resp);
          if (resp.success) {
            this.$emit('refreshDataList');
            this.visible = false;
            this.refreshMenuForm();
            this.$message.success("添加成功");
          }
        })
      }
      
    },
    // 选中图标
    selected(name) {
      this.menuForm.icon = name
    },
  }
}
</script>

<style lang="scss" scoped>
  .menuDialog {
    width: 60%;
    margin: 0 auto;
    overflow: hidden;
  }
  

</style>