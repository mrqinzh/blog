<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4">
        <el-input prefix-icon="el-icon-search" placeholder="请输入内容"></el-input>
        <div style="margin-top: 10px">
          <el-tree
            :data="data"
            accordion>
          </el-tree>
        </div>
      </el-col>

      <el-col :span="20">
        <el-col :span="4">
          <el-input placeholder="请输入内容" v-model="condition"></el-input>
        </el-col>
        <el-col :span="4">
          <el-button icon="el-icon-search">搜索</el-button>
          <el-button icon="el-icon-refresh-left">重置</el-button>
        </el-col>
        <div>
          <el-table
            :data="userList"
            style="width: 100%">
            <el-table-column
            type="selection"
            width="55">
            </el-table-column>
            <el-table-column
              prop="uid"
              label="账号"
              width="100">
            </el-table-column>
            <el-table-column
              prop="name"
              label="姓名"
              width="180">
            </el-table-column>
            <el-table-column
              prop="uname"
              label="昵称"
              width="180">
            </el-table-column>
            <el-table-column
              prop="sex"
              label="性别"
              width="100">
            </el-table-column>
            <el-table-column
              prop="birth"
              label="生日"
              width="180">
            </el-table-column>
            <el-table-column
              prop="tel"
              label="联系方式"
              width="180">
            </el-table-column>
            <el-table-column
              prop="email"
              label="电子邮箱"
              width="180">
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                <el-button
                  size="mini"
                  type="danger"
                  @click="handleDelete(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            style="margin: 20px auto"
            background
            layout="prev, pager, next"
            :page-size="page.pageSize"
            :total="page.totalCount"
            :current-page="page.currentPage"
            @current-change="changePageNum">
          </el-pagination>
        </div>

      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      condition: '',
      page: {pageSize: 10, totalCount: 0, currentPage: 1},
      userList: [
        {uid: 1, uname: 'mrqinzh', name: '秦志宏', sex: '男', birth: '2000-09-28', tel: '15730567860', email: '1552589784@qq.com'},
        {uid: 2, uname: 'mrqinzh', name: '秦志宏', sex: '男', birth: '2000-09-28', tel: '15730567860', email: '1552589784@qq.com'},
        ],
      data: [{
        label: '一级 1',
        children: [{
          label: '二级 1-1',
          children: [{
            label: '三级 1-1-1'
          }]
        }]
      }, {
        label: '一级 2',
        children: [{
          label: '二级 2-1',
        }]
      }],
    }
  },
  methods: {
    changePageNum(){
      console.log('换页');
    },
    // 删除记录触发事件
    handleDelete(index, row){   
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then((aciton) => {
        // action分别为confirm（确认），cancel（取消），close（关闭）的时候分别触发回调。
        if(aciton === 'confirm'){
          this.$message.success('删除成功!');
          this.userList.splice(index,1);    
        }
      }).catch(() => {

      });
    },
    // 点击编辑触发事件
    handleEdit(index, row){
      console.log(index);
      //this.form.name = this.tableData[index].name;
    },
  },
}
</script>

<style>

</style>