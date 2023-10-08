import moment from 'moment';
import account from '@/requests/account';

export default {
  namespaced: true,
  state: {
    info: null,
    status: ''
  },

  getters: {
    getInfo(state) {
      if (!state.info) {
        return;
      }
      const result = {
        ...state.info,
      };
      result.fullName = result.firstName + ' ' + result.lastName;
      result.ages = moment().diff(result.birthDate, 'years');
      return result;
    },
  },

  mutations: {
    setInfo: (s, info) => (s.info = info),
  },

  actions: {
    async apiInfo({ commit }) {
      try {
        const response = await account.get();
        commit('setInfo', response.data);
      } catch(err) {
        localStorage.removeItem('refresh-token');
        localStorage.removeItem('user-token');
        throw err;
      }
    },

    async apiChangeInfo({ commit, dispatch }, user) {
      try {
        const response = await account.edit(user);
        commit('setInfo', response.data);
        dispatch(
          'global/alert/setAlert',
          { status: 'success', text: 'Ваши данные обновлены' },
          { root: true }
        );
      } catch(err) {
        console.dir(err)
        dispatch('global/alert/setAlert',
          { status: 'error', text: `Не удалось обновить данные пользователя: ${err}` },
          { root: true }
        );
      }
    },

    async deleteInfo({ dispatch }) {
      await account.delete();
      dispatch('apiInfo')
    },
    async cancelDelete({ dispatch }) {
      await account.cancelDelete();
      dispatch('apiInfo')
    }
  },
};
