import Vue from 'vue'

import '@/icons' // icon
import '@/permission' // permission control

import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import '@/styles/index.scss' // global css

import animated from 'animate.css' // animate.css
Vue.use(animated)

import 'ant-design-vue/dist/antd.css'; // or 'ant-design-vue/dist/antd.less'
import Antd from 'ant-design-vue';
Vue.use(Antd);

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI, { size: 'mini', zIndex: 3000 })
// 使用ant-design的消息message组件覆盖elementui的
import { message } from 'ant-design-vue';
Vue.prototype.$message = message;


import App from './App'
import store from './store'
import router from './router'


Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
