import axios from 'axios';

export default {
  // api(data) {
  //   return axios.post('/storage?type=IMAGE', data, {
  //     headers: { 'Content-Type': 'multipart/form-data' },
  //   });
  // },
  api(data) {
    return axios.post('/users/account/me/addAvatar?type=IMAGE', data, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
  },

  // postPhoto(data) {
  //   return axios.post('/post/storagePostPhoto?type=IMAGE', data, {
  //     headers: { 'Content-Type': 'multipart/form-data' },
  //   });
  // },
  postPhoto(data) {
    return axios.put('/users/account/me?type=IMAGE', data, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
  },
};
