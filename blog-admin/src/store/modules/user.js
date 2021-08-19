import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  }
}

const actions = {
  login({ commit }, userInfo) {
    const data = { token: "admin" };
    setToken(data.token);
    commit("SET_TOKEN", data.token);
  },
  getInfo({ commit, state }) {
    const data = {
      roles: "admin",
      name: "admin",
      avatar:
        "http://mrqinzh.info:9090/img/avatar.jpg"
    };
    if (data.roles && data.roles.length > 0) {
      // 验证返回的roles是否是一个非空数组
      commit("SET_ROLES", data.roles);
    } else {
      reject("getInfo: roles must be a non-null array !");
    }
    commit("SET_NAME", data.name);
    commit("SET_AVATAR", data.avatar);
  },
  logout({ commit, state }) {
    commit("SET_TOKEN", "");
    commit("SET_ROLES", []);
    removeToken();
  },
  // // user login
  // login({ commit }, userInfo) {
  //   const data = { token: "admin" };
  //   setToken(data.token);
  //   commit("SET_TOKEN", data.token);
  //   const { userName, userPwd } = userInfo
  //   return new Promise((resolve, reject) => {
  //     login({ userName: userName.trim(), userPwd: userPwd }).then(response => {
  //       const { data } = response.data
  //       commit('SET_TOKEN', data.token)
  //       setToken(data.token)
  //       resolve()
  //     }).catch(error => {
  //       reject(error)
  //     })
  //   })
  // },

  // // get user info
  // getInfo({ commit, state }) {
  //   return new Promise((resolve, reject) => {
  //     getInfo(state.token).then(response => {
  //       const { data } = response.data

  //       if (!data) {
  //         return reject('Verification failed, please Login again.')
  //       }

  //       const { name, avatar } = data

  //       commit('SET_NAME', name)
  //       commit('SET_AVATAR', avatar)
  //       resolve(data)
  //     }).catch(error => {
  //       reject(error)
  //     })
  //   })
  // },

  // // user logout
  // logout({ commit, state }) {
  //   return new Promise((resolve, reject) => {
  //     logout(state.token).then(() => {
  //       removeToken() // must remove  token  first
  //       resetRouter()
  //       commit('RESET_STATE')
  //       resolve()
  //     }).catch(error => {
  //       reject(error)
  //     })
  //   })
  // },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

