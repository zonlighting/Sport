import { create, deleteTournament, getAll, getById, tournamentUpComming } from "../api/Tournament";

const actions = {
    getAll() {
        return new Promise((resolve, reject) => {
            getAll().then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    
    tournamentUpComming() {
        return new Promise((resolve, reject) => {
            tournamentUpComming().then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    create(__,tournament) {
        return new Promise((resolve, reject) => {
            create(tournament).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    getById(__,idTournament) {
        return new Promise((resolve, reject) => {
            getById(idTournament).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    deleteTournament(__, idTournament) {
        return new Promise((resolve, reject) => {
            deleteTournament(idTournament).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    }
}

export default {
    namespaced: true,
    actions,
}