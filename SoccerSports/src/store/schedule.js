import { create, deleteSchedule, getAll, getById } from "../api/ScheduleApi";

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
    getById(__,idSchedule) {
        return new Promise((resolve, reject) => {
            getById(idSchedule).then(res => {
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