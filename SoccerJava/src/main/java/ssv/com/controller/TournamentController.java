package ssv.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ssv.com.dto.ResponseQuery;
import ssv.com.dto.TournamentForm;
import ssv.com.service.TournamentService;

@RestController
@RequestMapping("/api/v1/tournament/")
public class TournamentController {

	@Autowired
	private TournamentService tournamentService;

	// tạo giải đấu
	@PostMapping(value = "create")
	public ResponseQuery<?> createTournament(@ModelAttribute TournamentForm tournamentForm) {
		if (tournamentForm.getTimeEnd().isAfter(tournamentForm.getTimeStart())) {
			if (tournamentService.checkName(tournamentForm.getNameTournament())) {

				return tournamentService.create(tournamentForm);
			}
			return ResponseQuery.faild("Tournaments of the same name", 400);
		}
		return ResponseQuery.faild("Invalid time", 400);

	}

	// hiển thị tất cả giải đấu
	@GetMapping(value = "getAll")
	public ResponseQuery<?> getAll() {
		return ResponseQuery.success("Connect Success", tournamentService.getAll());
	}

	// Hiển thị giải đấu theo id
	@GetMapping(value = "getById")
	public ResponseQuery<?> getById(@RequestParam int IdTour) {
		return ResponseQuery.success("Connect Success", tournamentService.getById(IdTour));
	}

	// thêm team vào giải đấu
	@PostMapping(value = "addTeam")
	public ResponseQuery<?> addTeam(@RequestParam int listTeam, @RequestParam int idTournament) {
		if (tournamentService.getById(idTournament).getStatus() == 0) {
			return tournamentService.addTeam(listTeam, idTournament);
		}
		return ResponseQuery.faild("Tournaments are running or ended", 400);
	}

	// Xóa giải đấu
	@PostMapping(value = "delete")
	public ResponseQuery<?> delete(@RequestParam int idTournament) {
		if (tournamentService.getById(idTournament) == null) {
			return ResponseQuery.faild("Tournaments does not exist", 400);
		}
		if (tournamentService.getById(idTournament).getStatus() == 0) {
			return tournamentService.delete(idTournament);
		}
		return ResponseQuery.faild("Tournaments are running or ended", 400);
	}

	// Xóa team trong giải
	@PostMapping(value = "deleteTeam")
	public ResponseQuery<?> deleteTeam(@RequestParam int idTeam, @RequestParam int idTournament) {
		if (tournamentService.getById(idTournament).getStatus() == 0) {
			return tournamentService.deleteTeam(idTeam, idTournament);
		}
		return ResponseQuery.faild("Tournaments are running or ended", 400);
	}

	// Sửa tổng quát giải đấu
	@PostMapping(value = "update")
	public ResponseQuery<?> update(@RequestBody TournamentForm tournamentForm) {
		if (tournamentService.getById(tournamentForm.getIdTournament()).getStatus() == 0) {
			return tournamentService.update(tournamentForm);

		}
		return ResponseQuery.faild("Tournaments are running or ended", 400);
	}

	// danh sach tournament chưa đến ngày kết thúc
	@GetMapping(value = "tournamentUpComming")
	public ResponseQuery<?> tournamentUpComming() {
		return ResponseQuery.success("List Tournament Up Comming", tournamentService.tournamentUpComming());
	}

	// Danh sách xếp hạng trong giải
	@GetMapping(value = "tournamentRank")
	public ResponseQuery<?> tournamentRank(@RequestParam int idTournament) {
		return ResponseQuery.success("Connect", tournamentService.tournamentRank(idTournament));
	}

	//Hiển thị giải theo trạng thái
	@GetMapping(value="tournamentStatus")
	public ResponseQuery<?> tournamentStatus(@RequestParam int status){
		if(tournamentService.tournamentStatus(status)==null) {
			return ResponseQuery.faild("No Tournaments", 400);
		}
		else {
			return ResponseQuery.success("Connet", tournamentService.tournamentStatus(status));
		}

	}
	@GetMapping(value="rankInTour")
	public ResponseQuery<?> rankByTour(@RequestParam int id){
		return ResponseQuery.success("Connect",tournamentService.rankByTour(id));
	}
	@PostMapping(value="checkStatus")
	public void checkStatus(){
		tournamentService.checkStatus();
	}
}
