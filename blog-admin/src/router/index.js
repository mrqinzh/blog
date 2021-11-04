import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/',
    component: () => import('@/views/web/index'),
    children: [
      { path: '/', name: 'home', component: () => import('@/views/web/home') },
      { path: '/article/list', name: 'Article', component: () => import('@/views/web/Article') },
      { path: '/category', name: 'Category', component: () => import('@/views/web/Category') },
      { path: '/about', name: 'About', component: () => import('@/views/web/About') },
      { path: '/detail/:articleId', name: 'Detail', component: () => import('@/views/web/Detail') },
      { path: '/message', name: 'Message', component: () => import('@/views/web/Message_copy') },
    ],
    hidden: true
  },

  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/admin',
    component: Layout,
    redirect: '/admin/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '控制台', icon: 'dashboard', affix: true }
    }]
  },

  // {
  //   path: '/admin/system',
  //   component: Layout,
  //   name: 'System',
  //   meta: { title: '系统管理', icon: 'el-icon-milk-tea' },
  //   redirect: 'noRedirect',
  //   children: [
  //     {
  //       path: 'user',
  //       name: 'User',
  //       component: () => import('@/views/system/user/User'),
  //       meta: { title: '用户管理', icon: 'user' }
  //     },
  //     {
  //       path: 'article',
  //       name: 'Article',
  //       component: () => import('@/views/system/article/index'),
  //       meta: { title: '文章管理', icon: 'el-icon-reading' },
  //       redirect: 'noRedirect',
  //       children: [
  //         { path: 'list', name: 'ArticleList', component: () => import('@/views/system/article/Article'), meta: { title: '文章列表', icon: 'el-icon-data-board' }},
  //         { path: 'add', name: 'ArticleAdd', component: () => import('@/views/system/article/add_copy'), meta: { title: '写文章', icon: 'el-icon-edit' }}
  //       ]
  //     },
  //     {
  //       path: 'comment',
  //       name: 'Comment',
  //       component: () => import('@/views/system/comment/Comment'),
  //       meta: { title: '评论管理', icon: 'el-icon-chat-line-round' }
  //     },
  //     {
  //       path: 'tag',
  //       name: 'Tag',
  //       component: () => import('@/views/system/tag/index'),
  //       meta: { title: '标签管理', icon: 'el-icon-collection-tag' }
  //     }
  //   ]
  // },

  // {
  //   path: '/admin/authority',
  //   component: Layout,
  //   name: 'Authority',
  //   meta: { title: '权限管理', icon: 'el-icon-medal' },
  //   redirect: 'noRedirect',
  //   children: [
  //     {
  //       path: 'menu',
  //       name: 'Menu',
  //       component: () => import('@/views/authority/menu/'),
  //       // component: () => import('@/views/authority/menu/index_copy'),
  //       meta: { title: '菜单管理', icon: 'el-icon-menu' }
  //     },
  //     {
  //       path: 'role',
  //       name: 'Role',
  //       component: () => import('@/views/authority/role'),
  //       meta: { title: '角色管理', icon: 'el-icon-key' }
  //     }
  //   ]
  // },

  {
    path: '/email',
    component: Layout,
    children: [
      {
        path: 'index',
        component: () => import('@/views/email/index'),
        name: 'Email',
        meta: { title: '邮件', icon: 'email', noCache: true }
      }
    ]
  },

  {
    path: '/icon',
    component: Layout,
    redirect: '/icon/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/icons/index'),
        name: 'Icons',
        meta: { title: '图标库', icon: 'icon', noCache: true }
      }
    ]
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  
]

/**
 * 动态路由
 */
export const asyncRoutes = [
  
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
