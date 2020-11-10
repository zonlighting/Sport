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
export function getDetail(data){
    return Api.get(`/team/detail?idTeam=${data.idTeam}&idTournament=${data.idTournament}`)
}

export function updateMembersInTeam(bodyFormData){
    return Api.post('/team/updateMembersInTeam', bodyFormData)
}

export function getTeamById(teamId){
    return Api.get('/team/' + teamId)
}

export function updateTeam(id, bodyFormData){
    return Api.post(`/team/updateInfo/${id}`, bodyFormData)
}

export function getHistory(data){
    return Api.get(`/team/getHistory?idTour=${data.idTour}&idTeam=${data.idTeam}&idSchedule=${data.idSchedule}`)
}

export function teamMatchs(idTeam){
    return Api.get('/team/teamMatchs/' + idTeam)
}

export function squad(data){
    return Api.get(`/team/squad?idTeam=${data.idTeam}&idTour=${data.idTour}`)
}

export function toursByTeam(idTeam){
    return Api.get(`http://localhost:8090/api/v1/team/toursByTeam/${idTeam}`)
}



