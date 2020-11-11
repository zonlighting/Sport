import Api from './BaseApi';

export function createMember(formMember) {
    return Api.post('/profiles/createMember', formMember)
}

export function members() {
    return Api.get('/profiles/members');
}

export function updateProfile(id, bodyFormData) {
    return Api.post(`/profiles/update/${id}`, bodyFormData)
}

export function getPlayerById(id){
    return Api.get('/profiles/' + id)
}

export function getTourGoal(data){
    return Api.get(`/profiles/getTourGoal?idTeam=${data}`)
}

export function nextMatch(idPlayer){
    return Api.get(`/team/nextMatch/${idPlayer}`)
}
