import { asyncRoutes, constantRoutes } from '@/router'
import { getMenuList } from '@/api/authority/menu'
import Layout from '@/layout'

/**
 * 静态路由懒加载
 * @param view  格式必须为 xxx/xxx 开头不要加斜杠
 * @returns 
 */
export const loadView = (view) => {
  return () => Promise.resolve(require(`@/views/${view}`))
}

/**
 * 把从后端查询的菜单数据拼装成路由格式的数据
 * @param routes
 * @param data 后端返回的菜单数据
 */
export function generaMenu(routes, data) {
  data.forEach(item => {
    const menu = {
      path: item.menuPath, 
      component: item.parentId === 0 ? Layout : loadView(item.componentPath), 
      hidden: item.hidden === 0, // 状态为0的隐藏
      redirect: item.redirect,
      children: [],
      name: item.componentName,
      meta: { title: `${item.menuTitle}`, icon: `${item.icon}` }
    }
    // console.log(menu.component)
    if (item.menuChildren) {
      generaMenu(menu.children, item.menuChildren)
    }
    // console.log(menu);
    routes.push(menu)
  })
  return routes
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    // 拼接静态路由和动态路由
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }) {
    return new Promise(resolve => {
      // 通过token从后端获取用户菜单，并加入全局状态
      getMenuList().then(resp => {
        const menuData = Object.assign([], resp.data)
        const tempAsyncRoutes = Object.assign([], asyncRoutes)
        const accessedRoutes = generaMenu(tempAsyncRoutes, menuData)

        commit('SET_ROUTES', accessedRoutes)
        resolve(accessedRoutes)
      }).catch(error => {
        console.log(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}