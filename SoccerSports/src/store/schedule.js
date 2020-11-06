import { create, deleteSchedule, edit, getAll, getById, getByTour, goalUpdate, recentMatch, update } from "../api/ScheduleApi";

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
    create(__,schedule) {
        return new Promise((resolve, reject) => {
            create(schedule).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    deleteSchedule(__,idSchedule) {
        return new Promise((resolve, reject) => {
            deleteSchedule(idSchedule).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    getByTour(__,idTournament) {
        return new Promise((resolve, reject) => {
            getByTour(idTournament).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    getById(__,idSchedule) {
        return new Promise((resolve, reject) => {
            getById(idSchedule).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    update(__,data) {
        return new Promise((resolve, reject) => {
            update(data).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    goalUpdate(__,data) {
        return new Promise((resolve, reject) => {
            goalUpdate(data).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    edit(__,data){
        return new Promise((resolve, reject) => {
            edit(data).then(res => {
                resolve(res);
            }).catch((err) => {
                reject(err);
            })
        })
    },
    
    recentMatch(){
        return new Promise((resolve, reject) => {
            recentMatch().then(res => {
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