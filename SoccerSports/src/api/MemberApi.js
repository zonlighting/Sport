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

