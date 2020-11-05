

import { getDetail, getTeams, createTeam, updateMembersInTeam, getTeamById, updateTeam } from '@/api/TeamApi'
import { getTeamNoTournament } from '../api/TeamApi'


const state = {
    currentTab: ""
}

const mutations = {
    current_tab(state, index) {
        console.log("index")
        console.log(index)
        state.currentTab = index
    },
}

const actions = {
    getTeams() {
        return new Promise((resolve, reject) => {
            getTeams().then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },

    createTeam(_, teamForm) {
        return new Promise((resolve, reject) => {
            createTeam(teamForm).then((res) => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    getTeamNoTournament() {
        return new Promise((resolve, reject) => {
            getTeamNoTournament().then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    getDetail(__, idTeam) {
        return new Promise((resolve, reject) => {
            getDetail(idTeam).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },


    updateMembersInTeam(_, data) {
        return new Promise((resolve, reject) => {
            updateMembersInTeam(data).then((res) => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },

    getTeamById(_, teamId) {
        return new Promise((resolve, reject) => {
            getTeamById(teamId).then((res) => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },

    updateTeam(_, { id, formRequest }) {
        return new Promise((resolve, reject) => {
            updateTeam(id, formRequest).then((res) => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
}

export default {
    namespaced: true,
    actions,
    state,
    mutations
}