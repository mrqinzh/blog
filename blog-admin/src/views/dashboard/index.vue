<template>
  <div class="dashboard-container">
    <div class="dashboard-text">name: {{ name }}</div>
    <div>
      这是首页
      <login-log-table></login-log-table>
    </div>
  </div>
</template>

<script>
import table from './components/table'
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  components: {
    'login-log-table': table,
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
