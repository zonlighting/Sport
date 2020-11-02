package ssv.com.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ssv.com.dto.ResponseQuery;
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
		try {
			if (teamService.getTeams() != null) {
				return ResponseQuery.success("Connect Success", teamService.getTeams());
			}
			return ResponseQuery.faild("Falied To recive data", null);
		} catch (Exception e) {
			return ResponseQuery.faild("Error connect to server", 500);
		}
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
	//Lấy data theo id
	@GetMapping(value = "getById")
	public ResponseQuery<?> getById(@RequestParam int idTeam){
		return ResponseQuery.success("Success", teamService.getById(idTeam));
	}

	@PostMapping(value = "updateMembersInTeam")
	public ResponseQuery<?> update(@RequestBody Team team) {
		teamService.updateMembersInTeam(team);
		return ResponseQuery.success("Update Success", team);
	}

	//lấy các team chưa có tham gia giải nào
//	@GetMapping(value = "getTeamNoTournament")
//	public ResponseQuery<?> getTeamNoTournament(){
//		return teamService.getTeamNoTournament();
//	}

}
