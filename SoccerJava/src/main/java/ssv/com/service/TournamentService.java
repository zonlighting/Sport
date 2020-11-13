package ssv.com.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssv.com.dto.RankDto;
import ssv.com.dto.ResponseQuery;
import ssv.com.dto.TeamDetail;
import ssv.com.dto.TournamentForm;
import ssv.com.entity.History;
import ssv.com.entity.Profile;
import ssv.com.entity.Schedule;
import ssv.com.entity.Team;
import ssv.com.entity.Tournament;
import ssv.com.exception.ResourceExistsException;
import ssv.com.file.UploadFile;
import ssv.com.repository.TournamentRepository;

@Service
public class TournamentService {
	@Autowired
	private ModelMapper modelMapper = new ModelMapper();
	@Autowired
	private TournamentRepository tournamentRepository;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private ScheduleService scheduleService;

	public List<Tournament> getAll() {
		return tournamentRepository.getAll();
	}

	public boolean checkName(String nameTournament) {
		for (Tournament tournament : tournamentRepository.getAll()) {
			if (tournament.getNameTournament().toLowerCase().compareTo(nameTournament.toLowerCase()) == 0) {
				return false;
			}
		}
		return true;
	}

	public ResponseQuery<?> create(TournamentForm tournamentForm) {
		Tournament tournament = modelMapper.map(tournamentForm, Tournament.class);
		try {
			if (tournamentForm.getBannerFile() != null) {
				tournament.setBanner(UploadFile.saveVideo(tournamentForm.getBannerFile()));
			}
			tournamentRepository.create(tournament);

			// id tournament new
			int idTournament = tournamentRepository.getIdNew();
			// tạo dữ liệu trong bảng history
			for (Integer listTeam : tournamentForm.getListTeam()) {
				// getByid lấy data team theo id
				teamService.newTournament(listTeam, tournamentRepository.getIdNew());
				for (Profile profile : teamService.getTeamById(listTeam).getProfile()) {

					History history = new History(profile.getId(), listTeam, idTournament);
					historyService.create(history);
				}
			}

			return ResponseQuery.success("Create successful tournament", tournament);
		} catch (IllegalStateException e) {
			tournamentRepository.delete(tournamentRepository.getIdNew());
			return ResponseQuery.faild(e.getMessage(), 888);
		} catch (IOException e) {
			tournamentRepository.delete(tournamentRepository.getIdNew());
			return ResponseQuery.faild(e.getMessage(), 888);
		}

	}

	public ResponseQuery<?> addTeam(int listTeam, int idTournament) {

		if (teamService.getTeamById(listTeam).getIdTour() != 0) {
			return ResponseQuery.faild("Team participated in the tournament", teamService.getTeamById(listTeam));
		}
		teamService.createTournament(listTeam, idTournament);
		for (Profile profile : teamService.getTeamById(listTeam).getProfile()) {
			History history = new History(profile.getId(), listTeam, idTournament);
			historyService.create(history);
		}

		return ResponseQuery.success("Add successful team", getById(idTournament));

	}

	public Tournament getById(int idTournament) {
		return tournamentRepository.getById(idTournament);
	}

	public ResponseQuery<?> deleteTeam(int idTeam, int idTournament) {
		if (teamService.getTeamById(idTeam).getIdTour() == idTournament) {
			teamService.formatTourTeam(idTeam);
			scheduleService.deleteByTeamTour(idTeam, idTournament);
			historyService.deleteTeam(idTeam, idTournament);
			return ResponseQuery.success("Delete successful", tournamentRepository.getById(idTournament));

		} else {
			return ResponseQuery.faild("Team does not exist", 400);

		}
	}

	public ResponseQuery<?> update(TournamentForm tournamentForm) {
		Tournament tournament = modelMapper.map(tournamentForm, Tournament.class);
		if (tournamentForm.getBannerFile()!=null) {
			try {
				tournament.setBanner(UploadFile.saveFile(tournamentForm.getBannerFile()));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		List<Schedule> list=scheduleService.getByTournament(tournament.getIdTournament());
		List<Tournament> listTour=getAll();
		for (Tournament tournament2 : listTour) {
			if(tournament2.getIdTournament()==tournament.getIdTournament()) {
				continue;
			}
			if(tournament2.getNameTournament().toLowerCase()==tournament.getNameTournament().toLowerCase()) {
				return ResponseQuery.faild("Namesake",
						400);
			}

		}
		for (Schedule schedule : list) {
			if(schedule.getTimeStart().toLocalDate().isBefore(tournament.getTimeStart())||schedule.getTimeStart().toLocalDate().isAfter(tournament.getTimeEnd())) {
				return ResponseQuery.faild("Out of match time :"+schedule.getTimeStart(),400);
			}
		}
		tournamentRepository.update(tournament);
		return ResponseQuery.success("Update successful tournament",
				tournamentRepository.getById(tournament.getIdTournament()));
	}

	public ResponseQuery<?> delete(int idTournament) {
		tournamentRepository.delete(idTournament);
		teamService.formatTournament(idTournament);
		scheduleService.deleteByTour(idTournament);
		historyService.delete(idTournament);
		return ResponseQuery.success("Delete successful tournament", 200);
	}

	public List<Tournament> tournamentUpComming() {
		// TODO Auto-generated method stub
		return tournamentRepository.tournamentUpComming();
	}

	public List<TeamDetail> tournamentRank(int idTournament) {
		List<TeamDetail> details = new ArrayList<TeamDetail>();
		List<Team> teams = tournamentRepository.getById(idTournament).getTeam();
		for (Team team : teams) {
			details.add(teamService.getTeamdetail(team.getIdTeam(), idTournament));
		}
		Collections.sort(details, new Comparator<TeamDetail>() {

			@Override
			public int compare(TeamDetail o1, TeamDetail o2) {
				return o2.getPointByTour() - o1.getPointByTour();
			}

		});

		return details;
	}

	public List<Tournament> tournamentStatus(int status) {
		return tournamentRepository.tournamentStatus(status);
	}

	public List<RankDto> rankByTour(int idTournament) {
		List<Schedule> schedules = tournamentRepository.getById(idTournament).getSchedule();
		List<Team> teams = tournamentRepository.getById(idTournament).getTeam();
		List<RankDto> ranks = new ArrayList<RankDto>();
		for (Team team : teams) {
			int win = 0;
			int lose = 0;
			int tie = 0;
			int point;
			int matchPlayed = 0;
			if (schedules.size() > 0) {
				for (Schedule schedule : schedules) {
					if ((team.getIdTeam() == schedule.getIdTeam1() || team.getIdTeam() == schedule.getIdTeam2())
							&& (schedule.getWinner() != 0 || schedule.getAdraw() != 0)) {
						matchPlayed++;
						if (team.getIdTeam() == schedule.getWinner()) {
							win++;
						} else if (team.getIdTeam() != schedule.getWinner() && schedule.getWinner() != 0) {
							lose++;
						} else if (schedule.getAdraw() == 1) {
							tie++;
						}
					}
				}
				point = win * 3 + tie;
				ranks.add(new RankDto(team.getIdTeam(),team.getNameTeam(),team.getLogo(), matchPlayed, win, lose, tie, point));
			} else {
				throw new ResourceExistsException("Tournament don't have any schedule yet!!", 300);
			}

		}
		ranks.sort(new Comparator<RankDto>() {
			@Override
			public int compare(RankDto o1, RankDto o2) {
				if (o1.getPoint() > o2.getPoint()) {
					return -1;
				} else if (o1.getPoint() < o2.getPoint()) {
					return 1;
				}
				return 0;
			}

		});
		return ranks;
	}

	public void checkStatus() {
		LocalDate date = LocalDate.now();
		for (Tournament tournament : tournamentRepository.getAll()) {
			if (tournament.getTimeEnd().isBefore(date)) {
				tournamentRepository.finished(tournament.getIdTournament());
				teamService.formatTournament(tournament.getIdTournament());
			} else if (tournament.getTimeStart().isAfter(date)) {
				continue;
			} else {
				tournamentRepository.ongGame(tournament.getIdTournament());

			}
		}

	}

	public List<TeamDetail> rankAll() {
		List<TeamDetail> details = new ArrayList<TeamDetail>();
		List<Team> teams = teamService.getTeams();
		for (Team team : teams) {
			details.add(teamService.getTeamdetail(team.getIdTeam(), 0));
		}
		Collections.sort(details, new Comparator<TeamDetail>() {

			@Override
			public int compare(TeamDetail o1, TeamDetail o2) {
				return o2.getPointAll() - o1.getPointAll();
			}

		});
		return details;
	}

	public List<Tournament> getAllSchedule() {
		List<Tournament> list = tournamentRepository.getAllSchedule();
		for (Tournament tournament : list) {
			for (Schedule schedule : tournament.getSchedule()) {
				if (schedule.getIdTeam1() > schedule.getIdTeam2()) {
					List<Team> list1 = new ArrayList<Team>();
					list1.add(schedule.getTeam().get(1));
					list1.add(schedule.getTeam().get(0));
					schedule.setTeam(list1);
				}
			}
		}
		return list;

	}

	public List<Tournament> getAllScheduleStatus(int status) {
		// TODO Auto-generated method stub
		return tournamentRepository.getAllScheduleStatus(status);
	}

}
