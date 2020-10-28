const state = {

}

const mutations = {

}

const actions = {
    getTeams(){
        return new Promise((resolve, reject) => {
            getTeams().then(res => {
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