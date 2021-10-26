import router, { constantRoutes } from './router'
import store from './store'
import { message } from 'ant-design-vue'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login', '/', '/category', '/about', '/message', '/article/list'] // 白名单，不需要登录信息就可以直接访问

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  // 通过 auth.js 中的 getToken 取得登录时发放的 token 信息
  const hasToken = getToken()

  // 如果取到了令牌则说明用户已经登录
  if (hasToken) {
    // 如果用户访问的还是登录页面，则直接跳转到首页
    if (to.path === '/login') {
      // 如果登录成功，跳转管理员界面
      next({ path: '/admin' })
      NProgress.done()
    } else {
      // const hasRoles = store.getters.roles && store.getters.roles.length > 0
      // if (hasRoles) {
      //   next()
      // } else {

      // }
      const hasGetUserInfo = store.getters.name
      if (hasGetUserInfo) {
        next()
      } else {
        try {
          // 获取用户信息
          await store.dispatch('user/getInfo')

          const accessRoutes = await store.dispatch('permission/generateRoutes')
          router.addRoutes(accessRoutes)
          next({ ...to, replace: true })

        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1 || to.name==='Detail') {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
