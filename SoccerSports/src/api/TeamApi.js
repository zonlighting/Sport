import Api from '@/api/BaseApi';

export function getTeams(){
    return Api.get('/team/getTeams')
}

export function createTeam(teamForm){
    return Api.post('/team/createTeam', teamForm)
}

export function getTeamNoTournament(){
    return Api.get('/team/getTeamNoTournament')
}
export function getDetail(idTeam){
    return Api.get(`/team/detail?idTeam=${idTeam}`)


export function updateMembersInTeam(bodyFormData){
    return Api.post('/team/updateMembersInTeam', bodyFormData)
}

export function getTeamById(teamId){
    return Api.get('/team/' + teamId)
}

export function updateTeam(id, bodyFormData){
    return Api.post(`/team/updateInfo/${id}`, bodyFormData)
}