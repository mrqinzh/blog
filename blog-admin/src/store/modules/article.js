const state = {
  content: ''
}

const mutations = {
  SET_CONTENT: (state, content) => {
    state.content = content;
  }
}

const actions = {
  cacheContent: ({ commit }, content) => {
    commit('SET_CONTENT', content);
  }
}

export default {
  state, 
  mutations,
  actions
}