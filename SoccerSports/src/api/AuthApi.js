import Api from '@/api/BaseApi';

export function login(user) {
    return Api.post('/account/login', user);
}

export function register(register) {
    return Api.post('/account/signup', register);
}

export function autoLogin() {
    return Api.post('/account/autoLogin');
}
export function getAll(){
    return Api.get('/account/getAll')
}
export function forgetEmail(email){
    return Api.post('/account/forget/'+email)
}