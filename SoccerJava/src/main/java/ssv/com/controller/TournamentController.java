package ssv.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssv.com.dto.ResponseQuery;
import ssv.com.entity.Tournament;
import ssv.com.service.TournamentService;

@RestController
@RequestMapping("/api/v1/tournament/")
public class TournamentController {
	@Autowired
	private TournamentService tournamentService;
	
	@PostMapping(value = "create")
	public ResponseQuery<?> createTournament(@RequestBody Tournament tournament){
		
		
		return null;
		
	}
}
