const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  userId: state => state.user.userId,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  // ===
  articleContent: state => state.article.content,

  //添加roles
  roles: state => state.user.roles,
  //动态路由
  permission_routes: state => state.permission.routes,
}
export default getters
