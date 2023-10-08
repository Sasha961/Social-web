import auth from '@/requests/auth';
import requestSettings from '@/requests/settings';

export default {
  namespaced: true,
  state: {
    token: localStorage.getItem('user-token') || '',
    jwt: '',
    status: '',
    pollingToken: null,
    refreshAttempts: 0,
    isCode: true,
    alertText: '',
  },
  getters: {
    apiToken: (sstate) => sstate.token,
    isAuthenticated: (state) => !!state.token,
    authStatus: (state) => {
      return state.status;
    },
    getIsCode: (state) => state.isCode,
  },
  mutations: {
    setJwt: (state) => {
      const cookie = document.cookie.split('; ').reduce((acc, string) => {
        const [key, value] = string.split('=');
        acc[key] = value;
        return acc;
      }, {});

      state.jwt = cookie.jwt || '';
    },
    setAlertText: (state, errorObj) => {
      let alertMessage = '';
      const res = errorObj?.response;
      if (res?.statusText === 'Unauthorized') {
        alertMessage = 'Неверный логин или пароль';
      } else if (res?.statusText === 'Conflict') {
        alertMessage = 'Пользователь с таким email уже зарегестрирован';
      } else if (res?.data === 'Wrong captcha answer') {
        alertMessage = 'Неверный код с картинки'
      } else {
        alertMessage = `Что-то пошло не так: ${ errorObj?.message }`;
      }
      state.alertText = alertMessage;
    },

    setToken: (state, token) => (state.token = token),
    setIsCode: (state, isCode) => (state.isCode = isCode),
    setStatus: (state, status) => (state.status = status),
    addAttempts: (state) => (state.refreshAttempts = state.refreshAttempts + 1),
    resetAttempts: (state) => (state.resetAttmpts = 0),
    setPollingInterval(state, interval) {
      state.pollingToken = interval;
    },
  },
  actions: {
    async register({ dispatch, commit, state }, user) {
      commit('setStatus', 'loading');
      try {
        await auth.register(user);
        commit('setStatus', 'success');
        commit('setIsCode', true)
        dispatch(
          'global/alert/setAlert',
          { status: 'success', text: 'Регистрация прошла успешно' },
          { root: true }
        );
      } catch (err) {
        console.dir(err)
        if (err?.response?.data === 'Wrong captcha answer') {
          commit('setIsCode', false)
        }
        commit('setStatus', err);
        commit('setAlertText', err);

        dispatch(
          'global/alert/setAlert',
          { status: 'error', text: state.alertText },
          { root: true }
        );
      }
      // await dispatch('login', {
      //   email: user.email,
      //   password: user.password1,
      // });
    },
    // pollingToken({ commit, dispatch }) {
    //   const interval = setInterval(() => {
    //     dispatch("refreshToken");
    //   }, 1000 * 600 * 1.5);
    //   commit("setPollingInterval", interval);
    // },
    async refreshToken({ commit, state, dispatch }) {
      if (state.refreshAttempts > 3) {
        console.warn('Cannot refresh token. Logging out...', state.pollingToken);
        dispatch('logout');
        clearInterval(state.pollingToken);
        return;
      }
      const refreshToken = localStorage.getItem('refresh-token');

      try {
        const response = await auth.refreshToken(refreshToken);
        // response.data
        const newAccessToken = response.accessToken;
        const newRefreshToken = response.refreshToken;

        localStorage.setItem('user-token', newAccessToken);
        localStorage.setItem('refresh-token', newRefreshToken);

        document.cookie = `jwt=${newAccessToken}`;
        requestSettings.setDefaultHeader('Authorization', `Bearer ${newAccessToken}`);
        commit('resetAttempts');
      } catch {
        console.warn('Cannot get new access token', state.refreshAttempts);
        commit('addAttempts');
      }
    },

    async login({ commit, dispatch, state }, user) {
      commit('setStatus', 'loading');
      try {
        const response = await auth.login(user);
        console.dir(response)
        const { accessToken, refreshToken } = response.data;

        localStorage.setItem('user-token', accessToken);
        localStorage.setItem('refresh-token', refreshToken);

        document.cookie = `jwt=${accessToken}`;
        requestSettings.setDefaultHeader('Authorization', `Bearer ${accessToken}`);

        commit('setJwt');
        commit('setToken', accessToken);
        commit('setStatus', '');
        commit('profile/info/setInfo', response.data, { root: true });
        dispatch(
          'global/alert/setAlert',
          { status: 'success', text: 'Авторизация прошла успешно' },
          { root: true }
        );

        dispatch('pollingToken');
      } catch (error) {
        commit('setStatus', error);
        console.dir(error);
        commit('setAlertText', error)
        dispatch(
          'global/alert/setAlert',
          { status: 'error', text: state.alertText },
          { root: true }
        );
        localStorage.removeItem('user-token');
        localStorage.removeItem('refresh-token');

        document.cookie = 'jwt=';
        commit('setJwt');
      }
    },

    async logout({ commit, dispatch, state }) {
      await auth.logout();

      commit('setToken', '');
      commit('setStatus', 'logout');

      dispatch(
        'global/alert/setAlert',
        { status: 'success', text: 'Вы вышли из системы' },
        { root: true }
      );

      localStorage.removeItem('user-token');
      localStorage.removeItem('refresh-token');

      document.cookie = 'jwt=';
      clearInterval(state.pollingToken);
      commit('setJwt');
      requestSettings.deleteDefaultHeader('Authorization');
    },
  },
};
