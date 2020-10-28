import axios from 'axios';
import store from '@/store';
import { ENV } from '@/config/env.js'

const instance = axios.create({
    baseURL: ENV.BASE_URL
});

instance.interceptors.request.use(request => {
    request.headers.Authorization = store.state.auth != undefined ? store.state.auth.token : '';
    return request;
}, error => {
    return Promise.reject(error);
});

instance.interceptors.response.use(response => {
    return response;
}, error => {
    return Promise.reject(error);
});
export default instance;