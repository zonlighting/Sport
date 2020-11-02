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

@Service
public class TeamService {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ScheduleRepository scheduleRepository;

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

}
