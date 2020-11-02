import { getTeams, createTeam, updateMembersInTeam } from '@/api/TeamApi'
import { getTeamNoTournament } from '../api/TeamApi'


const state = {

}

const mutations = {

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

    createTeam(_,teamForm) {
        return new Promise((resolve, reject) => {
            createTeam(teamForm).then((res) => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    getTeamNoTournament(){
        return new Promise((resolve, reject) => {
            getTeamNoTournament().then(res => {
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
}

export default {
    namespaced: true,
    actions,
    state,
    mutations
}