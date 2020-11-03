package ssv.com.service;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssv.com.dto.ResponseQuery;
import ssv.com.entity.Profile;
import ssv.com.entity.Team;
import ssv.com.exception.ResourceExistsException;
import ssv.com.file.UploadFile;
import ssv.com.form.TeamForm;
import ssv.com.repository.ProfileRepository;
import ssv.com.repository.TeamRepository;
import ssv.com.repository.TournamentRepository;

@Service
public class TeamService {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private ModelMapper modelMapper;

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

	public Team updateTeam(int id, TeamForm teamForm) throws Exception, ResourceExistsException {
		Team oldTeam = teamRepository.getTeamById(id);
		Team teamUpdate = modelMapper.map(teamForm, Team.class);
		if (teamForm.getFile() != null && !teamForm.getFile().getOriginalFilename().isEmpty()) {
			teamUpdate.setLogo(UploadFile.saveFile(teamForm.getFile()));
		} else {
			teamUpdate.setLogo(oldTeam.getLogo());
		}
		teamUpdate.setNameTeam(teamForm.getNameTeam());
		teamUpdate.setCountry(teamForm.getCountry());
		teamUpdate.setDescription(teamForm.getDescription());
		teamRepository.updateTeam(id, teamUpdate);
		return teamUpdate;
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
