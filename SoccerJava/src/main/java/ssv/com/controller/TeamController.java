package ssv.com.controller;

import org.modelmapper.ModelMapper;
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
import ssv.com.entity.Profile;
import ssv.com.entity.Team;
import ssv.com.file.UploadFile;
import ssv.com.form.TeamForm;
import ssv.com.service.TeamService;

@RestController
@RequestMapping("/api/v1/team/")
public class TeamController {
	ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private TeamService teamService;

	@GetMapping(value = "getTeams")
	public ResponseQuery<?> getTeams() {

		if (teamService.getTeams() != null) {
			return ResponseQuery.success("Connect Success", teamService.getTeams());
		}
		return ResponseQuery.faild("Falied To recive data", null);

	}

	@PostMapping(value = "createTeam")
	public ResponseQuery<?> createTeam(@ModelAttribute TeamForm teamForm) {
		String path = "";
		try {
			path = UploadFile.saveFile(teamForm.getFile());
			Team team = modelMapper.map(teamForm, Team.class);
			team.setLogo(path);
			if (!teamService.checkExistsTeam(team)) {
				teamService.createTeam(team);
				return ResponseQuery.success("Success", teamForm);
			}
			return ResponseQuery.faild("Team has already exists", 409);
		} catch (Exception e) {
			return ResponseQuery.faild("Create team fail", e);
		}
	}

	// Lấy data theo id
	@GetMapping(value = "{idTeam}")
	public ResponseQuery<?> getById(@PathVariable int idTeam) {
		if (teamService.getTeamById(idTeam) != null) {
			return ResponseQuery.success("Success", teamService.getTeamById(idTeam));
		}
		return ResponseQuery.faild("Team can't found", null);
	}

	@PostMapping(value = "updateMembersInTeam")
	public ResponseQuery<?> update(@RequestBody Team team) {
		teamService.updateMembersInTeam(team);
		return ResponseQuery.success("Update Success", team);
	}

	//thông tin các giải đấu về team
	@GetMapping(value="detail")
	public ResponseQuery<?> getTeamdetail(@RequestParam int idTeam,@RequestParam int idTournament){
		return ResponseQuery.success("Detail Team", teamService.getTeamdetail(idTeam,idTournament));

	}

	@PostMapping(value = "updateInfo/{id}")
	public ResponseQuery<?> updateTeamInfo(@PathVariable(value = "id") int id, @ModelAttribute TeamForm teamForm) {
		try {
			return ResponseQuery.success("Upadate Success", teamService.updateTeam(id, teamForm));
		} catch (Exception e) {
			return ResponseQuery.faild("Failed To Update", e);
		}
	}

	// lấy các team chưa có tham gia giải nào
	@GetMapping(value = "getTeamNoTournament")
	public ResponseQuery<?> getTeamNoTournament() {
		return teamService.getTeamNoTournament();
	}
	//Team theo tour và member của lịch sử đấu
	@GetMapping(value="getHistory")
	public ResponseQuery<?> getHistory(@RequestParam int idTour,@RequestParam int idTeam,@RequestParam int idSchedule){
		return teamService.getHistory(idTour,idTeam,idSchedule);

	}

}
