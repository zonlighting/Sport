import Api from '@api/BaseApi';

export function getTeams(){
    return Api.get('/team/getAll')
}