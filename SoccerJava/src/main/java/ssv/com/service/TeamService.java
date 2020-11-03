package ssv.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssv.com.dto.ResponseQuery;
import ssv.com.dto.TeamDetail;
import ssv.com.entity.Team;
import ssv.com.repository.ProfileRepository;
import ssv.com.repository.ScheduleRepository;
import ssv.com.repository.TeamRepository;
import ssv.com.repository.TournamentRepository;

@Service
public class TeamService {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private TournamentRepository tournamentRepository;

	public List<Team> getTeams() {
		return teamRepository.getTeams();
	}

	public Long createTeam(Team team) {
		return teamRepository.createTeam(team);
	}

	public boolean checkExistsTeam(Team team) {
		for (Team teamExists : getTeams()) {
			if (teamExists.getNameTeam().equalsIgnoreCase(team.getNameTeam())) {
				return true;
			}
		}
		return false;
	}

	public Team getTeamById(int idTeam) {
		Team team = teamRepository.getTeamById(idTeam);
		if (team.getIdTour() != 0) {
			team.setTourName(tournamentRepository.getById(team.getIdTour()).getNameTournament());
		}
		return team;
	}

	public void updateMembersInTeam(Team team) {
		profileRepository.updateMembersInTeam(team);
	}

<<<<<<< HEAD
	public ResponseQuery<?> getTeamNoTournament() {
		List<Team> listTeam=teamRepository.getTeamNoTournament();
		return ResponseQuery.success("Team no touranment", listTeam);

	}
	// update tour cho team
	public void newTournament(Integer idTeam, int idTournament) {
		 teamRepository.newTournament(idTeam,idTournament);
		
	}
	// format lai tour trong team
	public void formatTournament(int idTournament) {
		teamRepository.formatTournament(idTournament);
	}

	public ResponseQuery<?> getTeamdetail(int idTeam) {
		TeamDetail detail=new TeamDetail();
		detail.setTotalMatch(scheduleRepository.teamTotalMatch(idTeam));
		detail.setTotalWin(scheduleRepository.teamTotalWin(idTeam));
		if(teamRepository.getById(idTeam).getIdTour()==0) {
			detail.setTotalMatchByTour((Integer) null);
			detail.setTotalWinByTour((Integer) null);
		}
		else {
			int idTour=teamRepository.getById(idTeam).getIdTour();
			detail.setTotalMatchByTour(scheduleRepository.teamTotalMatchByTour(idTeam,idTour));
			detail.setTotalWinByTour(scheduleRepository.teamTotalWinByTour(idTeam,idTour));
			detail.setTotalAdrawByTour(scheduleRepository.teamTotalAdrawByTour(idTeam,idTour));
			int point =detail.getTotalWinByTour()*3+detail.getTotalAdrawByTour()*1;
			detail.setPointByTour(point);
		}
		return ResponseQuery.success("Detail Team", detail);
	}

=======

	public void updateTeam(int id, Team team) {
		teamRepository.updateTeam(id, team);
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
>>>>>>> f95b5fbe50dbb9a361ff81ec303555a8203cd959
}
