import axios from 'axios';
import createQuery from '@/utils/createQuery';

export default {
  getById(id, params) {
    const query = createQuery(params);
    const queryString = !query.length ? '' : `&${query.join('&')}`;
    return axios.get(`post/post?accountIds=1${queryString}&sort=time,desc&isDeleted=false`);
  },

  async getFeeds(paramsObj) {
    console.log(paramsObj)
    try {
      const queryParams = {
        sort: 'time,desc',
        isDeleted: false,
        page: '1',
      };
      const allParams = { ...queryParams }
      const res = await axios.get(`post/post`, allParams);
      console.dir(res)
      return res
    } catch(err) {
      console.dir(err)
    }
  },

  get(params) {
    const query = createQuery(params);
    const queryString = !query.length ? '' : `&${query.join('&')}`;
    return axios.get(`post?accountIds=1${queryString}&sort=time,desc&isDeleted=false`);
  },

  // push(data, isPUT = true, id = '', query = '') {
  //   // push(data, isPUT = true, query = '') {
  //   return isPUT ? axios.put(`posts/post${id}${query}`, data) : axios.post(`post${query}`, data);
  //   // return isPUT ? axios.put(`post/${query}`, data) : axios.post(`post${query}`, data);
  // },
  async push(data, isPUT = true, id = '', query = '') {
    try {
      let res =null
      console.dir(data)
      if (isPUT) res = await axios.put(`post/post${id}${query}`, data)
      if (!isPUT) res = await axios.post(`post${query}`, data);
      console.dir(res)
      return res
    } catch(err) {
      console.dir(err)
    }
    // push(data, isPUT = true, query = '') {
    // return isPUT ? axios.put(`posts/post${id}${query}`, data) : axios.post(`post${query}`, data);
    // return isPUT ? axios.put(`post/${query}`, data) : axios.post(`post${query}`, data);
  },

  delete(id) {
    return axios.delete(`post/post/${id}`);
  },

};

