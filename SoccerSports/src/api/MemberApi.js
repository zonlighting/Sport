import Api from './BaseApi';

export function createMember(formMember){
    return Api.post('/profiles/createMember', formMember)
}