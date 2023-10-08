import auth from '@/requests/auth';

export default {
  namespaced: true,
  state: {
    imgBase64Code: '',
  },
  getters: {
    getCaptcha: (state) => {
      return state.imgBase64Code;
    },
  },
  mutations: {
    setCaptcha: (state, value) => {
      state.imgBase64Code = value;
    },
  },
  actions: {
    async fetchCaptcha({ commit }) {
      const res = await auth.captcha();
      commit('setCaptcha', res.data);
    },
  },
};
