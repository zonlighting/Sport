package ssv.com.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssv.com.dto.ResponseQuery;
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
			if(teamService.getTeams() != null) {
				return ResponseQuery.success("Connect Success", teamService.getTeams());
			}
			return ResponseQuery.faild("Falied To recive data", null);
		}
		catch (Exception e) {
			return ResponseQuery.faild("Error connect to server", 500);
		}

	}
}
