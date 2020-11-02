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

export function updateMembersInTeam(bodyFormData){
    return Api.post('/team/updateMembersInTeam', bodyFormData)
}