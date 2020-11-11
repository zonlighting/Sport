package ssv.com.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssv.com.dto.MonthYearDto;
import ssv.com.dto.ResponseQuery;
import ssv.com.dto.SquadDto;
import ssv.com.dto.TeamDetail;
import ssv.com.dto.TeamScheduleDto;
import ssv.com.entity.History;
import ssv.com.entity.Profile;
import ssv.com.entity.Schedule;
import ssv.com.entity.Team;
import ssv.com.entity.Tournament;
import ssv.com.exception.ResourceExistsException;
import ssv.com.file.UploadFile;
import ssv.com.form.TeamForm;
import ssv.com.repository.HistoryRepository;
import ssv.com.repository.ProfileRepository;
import ssv.com.repository.ScheduleRepository;
import ssv.com.repository.TeamRepository;
import ssv.com.repository.TournamentRepository;

@Service
public class TeamService {
	@Autowired
	private HistoryRepository historyRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<Team> getTeams() {
		List<Team> teams = teamRepository.getTeams();
		for (Team team : teams) {
			List<Schedule> schedules = scheduleRepository.getAll();
			int totalMacth = 0;
			int totalWin = 0;
			for (Schedule schedule : schedules) {
				if ((schedule.getIdTeam1() == team.getIdTeam() || schedule.getIdTeam2() == team.getIdTeam())
						&& schedule.getStatus() == 2) {
					totalMacth++;
					if (schedule.getWinner() == team.getIdTeam()) {
						totalWin++;
					}
				}
			}
			if (totalMacth != 0) {
				team.setTotalmatch(totalMacth);
				team.setTotalwin(totalWin);
				team.setRate(((float) totalWin / totalMacth) * 100);
			} else {
				team.setTotalmatch(0);
				team.setTotalwin(0);
				team.setRate(0);
			}
		}
		return teams;
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
			List<Schedule> schedules = scheduleRepository.getAll();
			team.setTourName(tournamentRepository.getById(team.getIdTour()).getNameTournament());
			int totalMacth = 0;
			int totalWin = 0;
			for (Schedule schedule : schedules) {
				if ((schedule.getIdTeam1() == team.getIdTeam() || schedule.getIdTeam2() == team.getIdTeam())
						&& schedule.getStatus() == 2) {
					totalMacth++;
					if (schedule.getWinner() == team.getIdTeam()) {
						totalWin++;
					}
				}
			}
			if (totalMacth != 0) {
				team.setTotalmatch(totalMacth);
				team.setTotalwin(totalWin);
				team.setRate(((float) totalWin / totalMacth) * 100);
			} else {
				team.setTotalmatch(0);
				team.setTotalwin(0);
				team.setRate(0);
			}
		}
		return team;
	}

	public void updateMembersInTeam(Team team) {
		profileRepository.updateMembersInTeam(team);
	}

	public ResponseQuery<?> getTeamNoTournament() {
		List<Team> listTeam = teamRepository.getTeamNoTournament();
		return ResponseQuery.success("Team no touranment", listTeam);

	}

	// update tour cho team
	public void newTournament(Integer idTeam, int idTournament) {
		teamRepository.newTournament(idTeam, idTournament);

	}

	// format lai tour trong team
	public void formatTournament(int idTournament) {
		teamRepository.formatTournament(idTournament);
	}

	public TeamDetail getTeamdetail(int idTeam, int idTournament) {
		Team team = getTeamById(idTeam);
		TeamDetail detail = new TeamDetail();
		detail.setTotalMatch(scheduleRepository.teamTotalMatch(idTeam));
		detail.setTotalWin(scheduleRepository.teamTotalWin(idTeam));
		detail.setTotalAdraw(scheduleRepository.teamAdraw(idTeam));
		detail.setTotalMatchByTour(scheduleRepository.teamTotalMatchByTour(idTeam, idTournament));
		detail.setTotalWinByTour(scheduleRepository.teamTotalWinByTour(idTeam, idTournament));
		detail.setTotalAdrawByTour(scheduleRepository.teamTotalAdrawByTour(idTeam, idTournament));
		int point = detail.getTotalWinByTour() * 3 + detail.getTotalAdrawByTour() * 1;
		int pointAll = detail.getTotalWin() * 3 + detail.getTotalAdraw() * 1;
		detail.setPointByTour(point);
		detail.setPointAll(pointAll);
		detail.setNameTeam(team.getNameTeam());
		detail.setLogo(team.getLogo());
		detail.setIdTeam(idTeam);
		return detail;
	}

	public void updateTeam(int id, Team team) {
		teamRepository.updateTeam(id, team);
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

	public void formatTourTeam(int idTeam) {
		teamRepository.formatTourTeam(idTeam);

	}

	public void createTournament(int idTeam, int idTournament) {
		teamRepository.createTournament(idTeam, idTournament);

	}

	public ResponseQuery<?> getHistory(int idTour, int idTeam, int idSchedule) {
		return ResponseQuery.success("Connect", teamRepository.getHistory(idTour, idTeam, idSchedule));
	}

	static String[] monthName = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };

	public List<MonthYearDto> teamSchedules(int idTeam) {
		List<Schedule> schedules = scheduleRepository.getAll().stream()
				.filter((schedule) -> (schedule.getIdTeam1() == idTeam || schedule.getIdTeam2() == idTeam))
				.collect(Collectors.toList());
		schedules.sort(new Comparator<Schedule>() {
			@Override
			public int compare(Schedule o1, Schedule o2) {
				if (o1.getTimeStart().compareTo(o2.getTimeStart()) > 1) {
					return 1;
				} else if (o1.getTimeStart().compareTo(o2.getTimeStart()) < 1) {
					return -1;
				}
				return 0;
			}
		});
		List<MonthYearDto> monthYearDtos = new ArrayList<MonthYearDto>();
		List<TeamScheduleDto> matchs = new ArrayList<TeamScheduleDto>();
		for (Schedule schedule : schedules) {
			Team team1 = teamRepository.getTeamById(schedule.getIdTeam1());
			Team team2 = teamRepository.getTeamById(schedule.getIdTeam2());
//			MonthYearDto monthYearDto = new MonthYearDto();
			TeamScheduleDto match = new TeamScheduleDto();
			match.setMonthStart(
					monthName[schedule.getTimeStart().getMonthValue() - 1] + " , " + schedule.getTimeStart().getYear());
			match.setDayStart(monthName[schedule.getTimeStart().getMonthValue()] + " , "
					+ schedule.getTimeStart().getDayOfMonth());
			match.setNameTeam1(team1.getNameTeam());
			match.setLogoTeam1(team1.getLogo());
			match.setNameTeam2(team2.getNameTeam());
			match.setLogoTeam2(team2.getLogo());
			if (schedule.getTimeStart().getMinute() > 9) {
				match.setTimeStart(schedule.getTimeStart().getHour() + ":" + schedule.getTimeStart().getMinute());
			} else {
				match.setTimeStart(schedule.getTimeStart().getHour() + ":0" + schedule.getTimeStart().getMinute());
			}

			match.setNameTour(tournamentRepository.getById(schedule.getIdTour()).getNameTournament());
			match.setStatus(schedule.getStatus());
			match.setScore1(schedule.getScore1());
			match.setScore2(schedule.getScore2());
			if(schedule.getTimeEnd() != null) {
				if (schedule.getTimeEnd().getMinute() > 9) {
					match.setTimeEnd(schedule.getTimeEnd().getHour() + ":" + schedule.getTimeEnd().getMinute());
				} else {
					match.setTimeEnd(schedule.getTimeEnd().getHour() + ":0" + schedule.getTimeEnd().getMinute());
				}
			}
			matchs.add(match);
		}

		Map<String, List<TeamScheduleDto>> maps = matchs.stream()
				.collect(Collectors.groupingBy(TeamScheduleDto::getMonthStart));
		maps.forEach((k, v) -> {
			monthYearDtos.add(new MonthYearDto(k, v));
		});
		return monthYearDtos;
	}

	public List<Tournament> getTourByTeam(int idTeam) {
		List<History> histories = historyRepository.getToursByTeam(idTeam);
		List<Tournament> tournaments = new ArrayList<Tournament>();
		Set<Integer> idTours = new HashSet<>();
		for (History history : histories) {
			idTours.add(history.getIdTournament());
		}
		for (Integer integer : idTours) {
			tournaments.add(tournamentRepository.getById(integer));
		}
		return tournaments;
	}

	public List<SquadDto> squad(int idTeam, int idTour) {
		Random ran = new Random();
		List<History> histories = historyRepository.historyMember(idTour, idTeam);
		List<SquadDto> squad = new ArrayList<>();
		for (History history : histories) {
			SquadDto squadDto = new SquadDto();
			Optional<Profile> profile = profileRepository.findProfileById(history.getIdMember().intValue());
			squadDto.setIdMember(history.getIdMember().intValue());
			squadDto.setName(profile.get().getName());
			squadDto.setPos(profile.get().getPosition());
			squadDto.setAge(profile.get().getAge());
			float heightRan = (float) (0.40 + (0.82) * ran.nextFloat());
			if (heightRan > 1) {
				squadDto.setHeight("6'" + (int) ((heightRan - 1) * 10));
			} else {
				squadDto.setHeight("5'" + (int) (heightRan * 10));
			}
			int weightRan = 130 + ran.nextInt(70);
			squadDto.setWeight(weightRan + " Ibs");
			squadDto.setNation(profile.get().getCountry());
			int played = 0 + (int) (Math.random() * 6);
			squadDto.setPlayed(played);
			if (profile.get().getPosition().equalsIgnoreCase("Goalkeepers")) {
				squadDto.setGoal(0);
				squadDto.setSave(0 + (int) (Math.random() * 12));
				squadDto.setAssists(0 + (int) (Math.random() * 12));
				squadDto.setGa(0 + (int) (Math.random() * 30));
			}

			if (profile.get().getPosition().equalsIgnoreCase("Forwards")) {
				squadDto.setGoal(0 + (int) (Math.random() * 12));
				squadDto.setSave(0);
				squadDto.setAssists(0 + (int) (Math.random() * 2));
			}

			if (profile.get().getPosition().equalsIgnoreCase("Midfielders")) {
				squadDto.setGoal(0 + (int) (Math.random() * 2));
				squadDto.setSave(0 + (int) (Math.random() * 2));
				squadDto.setAssists(0 + (int) (Math.random() * 12));
			}

			if (profile.get().getPosition().equalsIgnoreCase("Defenders")) {
				squadDto.setGoal(0 + (int) (Math.random() * 0));
				squadDto.setSave(0 + (int) (Math.random() * 6));
				squadDto.setAssists(0 + (int) (Math.random() * 6));
			}
			squadDto.setFc(0 + (int) (Math.random() * 8));
			if (squadDto.getFc() > 4 && squadDto.getFc() < 8) {
				squadDto.setYc(1);
				squadDto.setRc(0);
			} else if (squadDto.getFc() == 8) {
				squadDto.setYc(1);
				squadDto.setRc(0);
			} else {
				squadDto.setYc(0);
				squadDto.setRc(0);
			}
			squad.add(squadDto);
		}
		return squad;
	}

	public static <T extends Comparable<T>> T least(T a, T b) {
		return a == null ? b : (b == null ? a : (a.compareTo(b) < 0 ? a : b));
	}

	public List<TeamScheduleDto> playerNextMatch(int idPlayer) {
		Optional<Profile> profile = profileRepository.findProfileById(idPlayer);
		List<TeamScheduleDto> dtos = new ArrayList<TeamScheduleDto>();
		for (Schedule schedule : scheduleRepository.getByStatus(0)) {
			if (schedule.getIdTeam1() == profile.get().getIdTeam()
					|| schedule.getIdTeam2() == profile.get().getIdTeam()) {
				Team team1 = teamRepository.getTeamById(schedule.getIdTeam1());
				Team team2 = teamRepository.getTeamById(schedule.getIdTeam2());
				TeamScheduleDto dto = new TeamScheduleDto();
				dto.setDayStart(monthName[schedule.getTimeStart().getMonthValue()] + " , "
						+ schedule.getTimeStart().getDayOfMonth());
				if (schedule.getTimeStart().getMinute() > 9) {
					dto.setTimeStart(schedule.getTimeStart().getHour() + ":" + schedule.getTimeStart().getMinute());
				} else {
					dto.setTimeStart(schedule.getTimeStart().getHour() + ":0" + schedule.getTimeStart().getMinute());
				}
				dto.setNameTeam1(team1.getNameTeam());
				dto.setLogoTeam1(team1.getLogo());
				dto.setNameTeam2(team2.getNameTeam());
				dto.setLogoTeam2(team2.getLogo());
				dto.setNameTour(tournamentRepository.getById(schedule.getIdTour()).getNameTournament());
				dto.setYear(schedule.getTimeStart().getYear());
				dtos.add(dto);
			}
		}
		return dtos;
	}
}
