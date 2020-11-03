import { createMember, members, updateProfile } from '@/api/MemberApi'

const state = {

}

const mutations = {}

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
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}