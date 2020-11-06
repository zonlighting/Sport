import Api from '@/api/BaseApi';

export function getAll() {
    return Api.get('/tournament/getAll')
}
export function deleteTournament(idTournament) {
    return Api.post(`/tournament/delete?idTournament=${idTournament}`);
}
export function editTournament(tournament) {
    return Api.post('/tournament/update', tournament)
}
export function create(tournament) {
    return Api.post('/tournament/create', tournament)
}
export function getById(idTournament) {
    return Api.get(`/tournament/getById?IdTour=${idTournament}`)
}
export function tournamentUpComming() {
    return Api.get('/tournament/tournamentUpComming')
}
export function deleteTeam(data) {
    return Api.post(`tournament/deleteTeam?idTeam=${data.idTeam}&idTournament=${data.idTournament}`)
}
export function addTeam(data) {
    return Api.post(`tournament/addTeam?listTeam=${data.listTeam}&idTournament=${data.idTournament}`)
}
export function tournamentRank(idTournament) {
    return Api.get(`tournament/tournamentRank?idTournament=${idTournament}`)
}
export function rankByTour(idTournament) {
    return Api.get(`tournament/rankInTour/` + idTournament)
}
