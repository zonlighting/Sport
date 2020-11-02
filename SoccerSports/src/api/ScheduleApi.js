import Api from './BaseApi';

export function getAll(){
    return Api.get("/schedule/getAll")
}
export function create(schedule){
    return Api.post("/schedule/create",schedule)
}
export function deleteSchedule(idSchedule){
    return Api.post(`/schedule/deleteSchedule?idSchedule=${idSchedule}`)
}
export function getById(idSchedule){
    return Api.get(`/schedule/getById?idSchedule=${idSchedule}`)
}