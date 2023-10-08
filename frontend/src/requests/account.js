import axios from 'axios';

export default {
  // Получение текущего пользователя
  get() {
    return axios.get('users/account/me');
  },
  // Редактирование текущего аккаунта
  edit(data) {
    return axios.put('users/account/me', data);
  },
  // Удаление текущего пользователя
  delete() {
    return axios.delete('users/account/me');
  },
  // Отменить удаление текущего пользователя
  cancelDelete() {
    return axios.post('users/account/me/unmark');
  },
  // Получение пользователя по id
  getUser(id) {
    return axios.get(`users/${id}/account`);
  },
};
