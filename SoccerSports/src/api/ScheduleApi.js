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
export function getByTour(idTournament){
    return Api.get(`/schedule/getByTournament?idTournament=${idTournament}`)
}
export function recentMatch(){
    return Api.get("/schedule/recentMatch")
}
export function lastVideo(){
    return Api.get("/schedule/lastVideo")
}
export function lastResults(){
    return Api.get("/schedule/lastResults")
}
export function getByStatus(status){
    return Api.get(`/schedule/getByStatus?status=${status}`)}
export function teamResults(data){
    return Api.get(`/schedule/teamResults?idTeam=${data.idTeam}&idTournament=${data.idTournament}`)
}
