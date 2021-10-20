<template>
  <div class="web-category-container">
    <div class="web-category-header">
      category header ...
    </div>
    <a-divider />
    <div class="web-category-body">
      <!-- category content ... -->
      <el-timeline>
        <el-timeline-item placement="top" size="large">
          <div style="height: 50px;"></div>
        </el-timeline-item>
        <el-timeline-item timestamp="2018-4-12" placement="top" size="large">
          <p>更新 Github 模板</p>
          <p>王小虎 提交于 2018/4/12 20:46</p>
          <p>王小虎 提交于 2018/4/12 20:46</p>
          <p>王小虎 提交于 2018/4/12 20:46</p>
          <p>王小虎 提交于 2018/4/12 20:46</p>
        </el-timeline-item>
      </el-timeline>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style lang="scss" scoped>
  .web-category-container {
    width: 50%;
    margin: 30px auto;
    padding: 15px;
    border: 1px solid #EBEEF5;
    border-radius: 10px;
    .web-category-header {
      font-size: 25px;
    }
    .ant-divider {
      margin: 5px 0 20px 0;
    }
    .web-category-body {
      .el-timeline-item__timestamp {
        font-size: 20px;
      }
    }
  }
</style>

<style lang="scss">

  .web-category-body {
    .el-timeline-item__timestamp {
      font-size: 20px;
    }
  }

</style>