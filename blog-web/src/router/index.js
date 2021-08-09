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
            component: () => import('@/views/admin/Admin'),
            children: [
                { path: '/admin', name: 'adminHome', component: () => import('@/views/admin/Index.vue') },
                { path: '/admin/user', name: 'adminUser', component: () => import('@/views/admin/system/UserManager.vue') },
                { path: '/admin/blog', name: 'adminBlog' , component: () => require('@/views/admin/system/BlogManager.vue') },
                { path: '/admin/comment', name: 'adminComment', component: () => import('@/views/admin/system/comment.vue') },
                { path: '/admin/article/add', name: 'adminAdd', component: () => import('@/views/admin/system/article/add.vue') },
            ]
        },
        { path: '*', component: () => import(/* Error */'@/views/Error') },
    ]
});
