import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'
import VueAxios from 'vue-axios'

import Main from '@/views/Main'
import MainPage from '@/views/web/MainPage'
import Articles from '@/views/web/Articles'
import WriteArticle from '@/views/web/WriteArticle'
import LeaveMessage from '@/views/web/LeaveMessage'
import UserInfo from '@/views/web/UserInfo'
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
                { path: '/write', component: WriteArticle },
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
            component: () => import('@/views/admin/Admin'),
            children: [
                { path: '/admin', component: () => import('@/views/admin/Index') },
                { path: '/admin/user', component: () => import('@/views/admin/system/UserManager') },
                { path: '/admin/blog', component: () => import('@/views/admin/system/BlogManager') },
                { path: '/admin/comment', component: () => import('@/views/admin/system/comment') },
            ]
        },
        { path: '*', component: () => import(/* Error */'@/views/Error') },
    ]
});
