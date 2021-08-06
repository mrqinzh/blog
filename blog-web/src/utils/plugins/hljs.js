import Vue from 'vue'
import highlight from 'highlight.js';
import 'highlight.js/styles/googlecode.css' // 样式文件

//封装成一个指令
Vue.directive('highlight', (el) => {
  let blocks = el.querySelectorAll('pre code')
  blocks.forEach((block) => {
    highlight.highlightBlock(block)
  })
});
