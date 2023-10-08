import axios from 'axios';

export default {
  register(user) {
    return axios.post('/auth/register', user);
  },

  login(user) {
    return axios.post('/auth/login', user);
  },

  refreshToken(token) {
    return axios.post('auth/refresh', { token });
  },

  logout() {
    localStorage.clear();
  },

  captcha() {
    return axios.get('/auth/captcha');
  },

  recoveryPassword(data) {
    return axios.post('/auth/password/recovery/', data);
  },

  setPassword(data, secret) {
    return axios.post(`/auth/password/recovery/${secret}`, data);
  },

  changePassword(data) {
    return axios.put('/auth/password', data);
  },

  changeEmail(data) {
    return axios.put('/auth/email', data);
  },

  requestChangeEmailLink(email) {
    return axios.post('/users/account/me/change-email', { email });
  },

  requestChangePasswordLink(password) {
    return axios.put('/users/account/me/change-password', password);
  },
};
