import Api from '@/api/BaseApi';

export function getTeams(){
    return Api.get('/team/getTeams')
}

export function createTeam(teamForm){
    return Api.post('/team/createTeam', teamForm)
}