import storage from '@/requests/storage';

export default {
  namespaced: true,
  state: {
    storage: null,
  },
  getters: {
    getStorage: (s) => s.storage,
  },
  mutations: {
    setStorage: (s, value) => {
      s.storage = value;
    },
  },
  actions: {
    async apiStorage({ commit, dispatch }, file) {

      try {
        const formData = new FormData();
        formData.append('file', file);
        const response = await storage.api(formData);
        commit('setStorage', response);
        return response.data;
      } catch(err) {
        console.dir(err)
        dispatch('global/alert/setAlert',
        { status: 'error', text: `Не удалось обновить аватарку: ${err}` },
        { root: true }
      );
      }
    },
  },
};
