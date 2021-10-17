const state = {
  content: ''
}

const mutations = {
  SET_CONTENT: (state, content) => {
    state.content = content;
  }
}

const actions = {
  setContent: ({ commit }, content) => {
    commit('SET_CONTENT', content);
  }
}

export default {
  namespaced: true,
  state, 
  mutations,
  actions
}