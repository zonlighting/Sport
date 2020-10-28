package ssv.com.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssv.com.entity.Team;
import ssv.com.repository.TeamRepository;

@Service
public class TeamService {

	@Autowired
	private TeamRepository teamRepository;


	public List<Team> getTeams() {
		return teamRepository.getTeams();
	}
}
