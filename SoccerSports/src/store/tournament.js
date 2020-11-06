import { addTeam, create, deleteTeam, deleteTournament, getAll, getById, rankAll, tournamentCheck, tournamentRank, tournamentStatus, tournamentUpComming } from "../api/Tournament";


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
    deleteTeam(__,data) {
        return new Promise((resolve, reject) => {
            deleteTeam(data).then(res => {
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

    }

    
}

export default {
    namespaced: true,
    actions,
}