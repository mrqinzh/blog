import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'
import VueAxios from 'vue-axios'

import Main from '@/views/Main'
import MainPage from '@/views/web/MainPage'
import Articles from '@/views/web/Articles'
import LeaveMessage from '@/views/web/LeaveMessage'
import About from '@/views/web/About'
// ===============================  admin   ===============================

Vue.use(VueAxios,axios)
Vue.use(VueRouter)


//获取原型对象上的push函数
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default new VueRouter({
    routes: [
        { path: '/login', component: () => import('@/views/Login'),},
        {
            path: '/',
            component: Main,
            children: [
                { path: '/', component: MainPage },
                { path: '/articles', component: Articles },
                { path: '/leaveMsg', component: LeaveMessage },
                { path: '/about', component: About, },
                { path: '/userInfo/myComment', component: () => import(/* studyPage */'../components/userinfo/MyComment') },
                { path: '/userInfo/myArticles', component: () => import(/* myArticles */'../components/userinfo/MyArticles') },
                { path: '/blog', name: Articles, component: () => import(/* Articles */'@/views/web/ShowArticles') },
            ]
        },
        { 
            path: '/admin',
            name: 'admin',
            redirect: '/admin/home',
            component: () => import('@/views/admin/Admin'),
            children: [
                { path: '/admin/home', name: '首页', component: () => import('@/views/admin/Index.vue') },
                { path: '/admin/user', name: '用户管理', component: () => import('@/views/admin/system/UserManager.vue') },
                { path: '/admin/blog', name: '文章管理' , component: () => import('@/views/admin/system/BlogManager.vue') },
                { path: '/admin/comment', name: '评论管理', component: () => import('@/views/admin/system/comment.vue') },
                { path: '/admin/article/add', name: '写文章', component: () => import('@/views/admin/system/article/add.vue') },
            ]
        },
        { path: '*', component: () => import(/* Error */'@/views/Error') },
    ]
});
