package ssv.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssv.com.dto.ResponseQuery;
import ssv.com.entity.Team;
import ssv.com.repository.ProfileRepository;
import ssv.com.repository.TeamRepository;

@Service
public class TeamService {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private ProfileRepository profileRepository;

	public List<Team> getTeams() {
		return teamRepository.getTeams();
	}

	public Long createTeam(Team team) {
		return teamRepository.createTeam(team);
	}

	public boolean checkExistsTeam(Team team) {
		for (Team teamExists : getTeams()) {
			if (teamExists.getNameTeam().equals(team.getNameTeam())) {
				return true;
			}
		}
		return false;
	}

	public Team getById(int idTeam) {
		return teamRepository.getById(idTeam);
	}

	public void updateMembersInTeam(Team team) {
		profileRepository.updateMembersInTeam(team);
	}

//	public ResponseQuery<?> getTeamNoTournament() {
//		List<Team> listTeam=teamRepository.getTeamNoTournament();
//		return ResponseQuery.success("Team no touranment", listTeam);
//
//	}
//	// update tour cho team
//	public void newTournament(Integer idTeam, int idTournament) {
//		 teamRepository.newTournament(idTeam,idTournament);
//
//	}
//	// format lai tour trong team
//	public void formatTournament(int idTournament) {
//		teamRepository.formatTournament(idTournament);
//	}
}
