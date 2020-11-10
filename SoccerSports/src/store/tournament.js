
import {rankByTour, addTeam, create, deleteTeam, deleteTournament, getAll, getById, rankAll, tournamentCheck, tournamentRank, tournamentStatus, tournamentUpComming, getAllSchedule, getAllScheduleStatus, update } from "../api/Tournament";


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
    deleteTeam(__, data) {
        return new Promise((resolve, reject) => {
            deleteTeam(data).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    create(__, tournament) {
        return new Promise((resolve, reject) => {
            create(tournament).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    getById(__, idTournament) {
        return new Promise((resolve, reject) => {
            getById(idTournament).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    tournamentCheck() {
        return new Promise((resolve, reject) => {
            tournamentCheck().then(res => {
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
    },
    addTeam(__, data) {
        return new Promise((resolve, reject) => {
            addTeam(data).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    tournamentRank(__, data) {
        return new Promise((resolve, reject) => {
            tournamentRank(data).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },

    rankByTour(_, id) {
        return new Promise((resolve, reject) => {
            rankByTour(id).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },

    rankAll() {
        return new Promise((resolve, reject) => {
            rankAll().then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    tournamentStatus(__,status){
        return new Promise((resolve, reject) => {
            tournamentStatus(status).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    getAllSchedule(){
        return new Promise((resolve, reject) => {
            getAllSchedule().then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    getAllScheduleStatus(__,id){
        return new Promise((resolve, reject) => {
            getAllScheduleStatus(id).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    update(__,form){
        return new Promise((resolve, reject) => {
            update(form).then(res => {
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
}