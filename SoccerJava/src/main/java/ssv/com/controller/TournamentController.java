package ssv.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
				tournamentService.create(tournamentForm);
				return ResponseQuery.success("Create Success", 200);
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
	public ResponseQuery<?> addTeam(@RequestParam Integer[] listTeam, @RequestParam int idTournament) {
		if (tournamentService.getById(idTournament).getStatus() == 0) {
			return tournamentService.addTeam(listTeam, idTournament);
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
	@PostMapping(value = "update/{idTournament}")
	public ResponseQuery<?> update(@RequestBody TournamentForm tournamentForm, @PathVariable int idTournament) {
		if (tournamentService.getById(idTournament).getStatus() == 0) {
			return tournamentService.update(tournamentForm, idTournament);
		}
		return ResponseQuery.faild("Tournaments are running or ended", 400);
	}
}
