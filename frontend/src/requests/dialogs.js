import axios from 'axios';

export default {
  getMessages(id) {
    return axios.get(`/dialogs/messages?companionId=${id}`);
  },

  getDialogs() {
    return axios.get('/dialogs');
  },

  unreadedMessages() {
    return axios.get('/dialogs/unread');
  },

  markReaded(id) {
    return axios.put(`dialogs/${id}`);
  },
};
