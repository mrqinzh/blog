// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'

import router from './router'
import store from './store'

import animate from "animate.css";

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/display.css';

import 'ant-design-vue/dist/antd.css'; // or 'ant-design-vue/dist/antd.less'
import Antd from 'ant-design-vue'// 引入Ant Design Vue组件
Vue.use(Antd) //挂载到vue中

Vue.use(ElementUI)
Vue.config.productionTip = false
window.bus = new Vue()

import VueParticles from 'vue-particles'
Vue.use(VueParticles)


Vue.use(animate)
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  render: h => h(App),
})


