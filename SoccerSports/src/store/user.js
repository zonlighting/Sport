import { autoLogin } from '@/api/AuthApi';

const state = {
    userInfo: null,
    isProfile: false,
    isAdminProfile: false
}

const mutations = {
    user_info(state, user) {
        state.userInfo = user
    },
    user_profile(state) {
        state.isProfile = !state.isProfile
    },
    admin_profile(state) {
        state.isAdminProfile = !state.isAdminProfile
    }
}

const actions = {
    autoLogin({ commit },) {
        return new Promise((resolve, reject) => {
            autoLogin().then((res) => {
                commit('user_info', res.data.payload)
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}