

import { getDetail, getTeams, createTeam, updateMembersInTeam, getTeamById, updateTeam, teamMatchs } from '@/api/TeamApi'
import { getHistory, getTeamNoTournament } from '../api/TeamApi'


const state = {
    currentTab: "",
    teamDetail: "",
    tourId: ""
}

const mutations = {
    current_tab(state, index) {
        state.currentTab = index
    },
    team_detail(state, team) {
        state.teamDetail = team
    },
    tour_id(state, id){
        state.tourId = id
    }
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
    getDetail(__, data) {
        return new Promise((resolve, reject) => {
            getDetail(data).then(res => {
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
    getHistory(__, data) {
        return new Promise((resolve, reject) => {
            getHistory(data).then((res) => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },

    teamMatchs(__, idTeam) {
        return new Promise((resolve, reject) => {
            teamMatchs(idTeam).then((res) => {
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