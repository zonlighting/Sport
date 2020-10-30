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

