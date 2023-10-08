import axios from 'axios';

export default {
  get(page = 0, size = null) {
    return axios.get('notifications', { params: { page, size}});
  },

  getLength() {
    return axios.get('notifications/count');
  },

  changeSettings(data) {
    return axios.put('notifications/settings', data);
  },

  getSettings() {
    return axios.get('notifications/settings');
  },
};
