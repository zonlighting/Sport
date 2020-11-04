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
export function update(scheduleForm){
    return Api.post("/schedule/update",scheduleForm)
}
export function goalUpdate(data){
    return Api.post(`/schedule/goal`,data)
}
export function edit(data){
    return Api.post('/schedule/edit',data)
}