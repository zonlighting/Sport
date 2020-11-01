import Api from './BaseApi';

export function getAll(){
    return Api.get("/schedule/getAll")
}
export function create(schedule){
    return Api.post("/schedule/create",schedule)
}