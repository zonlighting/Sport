import {nextMatch, createMember, members, updateProfile,  getPlayerById ,getTourGoal} from '@/api/MemberApi'

const state = {
    playerProfile: {}
}

const mutations = {
    player_profile(state, player) {
        state.playerProfile = player
    },
}

const actions = {
    createMember(_, formMember) {
        return new Promise((resolve, reject) => {
            createMember(formMember).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },

    members() {
        return new Promise((resolve, reject) => {
            members().then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },


    updateProfile(_, { id, formRequest }) {
        return new Promise((resolve, reject) => {
            updateProfile(id, formRequest).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },

    getPlayerById(_, id) {
        return new Promise((resolve, reject) => {
            getPlayerById(id).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },

    getTourGoal(__,data){
        return new Promise((resolve, reject) => {
            getTourGoal(data).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },

    nextMatch(__,idPlayer){
        return new Promise((resolve, reject) => {
            nextMatch(idPlayer).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}