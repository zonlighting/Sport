import Api from './BaseApi';

export function createMember(formMember){
    return Api.post('/profiles/createMember', formMember)
}

export function members(){
    return Api.get('/profiles/members');
}
