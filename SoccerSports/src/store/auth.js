import { login, register } from '@/api/AuthApi';

const state = {
    token: localStorage.getItem('token') || '',
    overlay: false,
    checkAccount: false,
    drawer:null
}

const mutations = {
    
    auth_success(state, user) {
        state.token = user.token
    },
    logout(state) {
        state.status = ''
        state.token = ''
    },
    auth_overlay_true(state) {
        state.overlay = true
    },
    auth_overlay_false(state) {
        state.overlay = false
    },
    auth_error(state) {
        state.checkAccount = !state.checkAccount
    },
}

const actions = {
    login({ commit }, user) {
        return new Promise((resolve, reject) => {
            login(user)
                .then(resp => {
                    if (resp.data.code === 9999) {
                        commit('auth_error')
                        setTimeout(function () {
                            commit('auth_error')
                        }, 3000);
                    } else {
                        const user = resp.data.payload
                        localStorage.setItem('token', user.token);
                        commit('auth_success', user);
                        resolve(resp)
                    }
                })
                .catch(err => {
                    commit('auth_error')
                    reject(err)
                })
        })
    },

    register({ commit }, user) {
        return new Promise((resolve, reject) => {
            // commit('auth_request')
            register(user)
                .then(resp => {
                    resolve(resp)
                })
                .catch(err => {
                    commit('auth_error', err)
                    localStorage.removeItem('token')
                    reject(err)
                })
        })
    },

    logout({ commit }) {
        return new Promise((resolve) => {
            commit('logout')
            localStorage.removeItem('token')
            resolve()
        })
    },
}

export default {
    namespaced: true,
    state,
    mutations,
    actions,
}