package ssv.com.service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssv.com.dto.ConsecutiveDetail;
import ssv.com.dto.GoalDto;
import ssv.com.dto.ResponseQuery;
import ssv.com.entity.Schedule;
import ssv.com.entity.Team;
import ssv.com.file.UploadFile;
import ssv.com.form.ScheduleForm;
import ssv.com.repository.GoalRepository;
import ssv.com.repository.HistoryRepository;
import ssv.com.repository.ScheduleRepository;
import ssv.com.repository.TournamentRepository;

@Service
public class ScheduleService {
	@Autowired
	private ModelMapper modelMapper = new ModelMapper();
	@Autowired
	private ScheduleRepository scheduleRepository;
	@Autowired
	private TournamentRepository tournamentRepository;
	@Autowired
	private GoalRepository goalRepository;
	@Autowired
	private HistoryRepository historyRepository;

	// mỗi trận đấu phải cách nhau ít nhất 3h
	public ResponseQuery<?> create(Schedule schedule) {
		String result = checkSchedule(schedule);
		if (result == null) {
			scheduleRepository.create(schedule);
			return ResponseQuery.success("Create Success", schedule);
		}
		return ResponseQuery.faild(result, 400);
	}

	public List<Schedule> getByTournament(int idTournament) {
		List<Schedule> schedules = scheduleRepository.getByTournament(idTournament);
		for (Schedule schedule : schedules) {
			if (schedule.getIdTeam1() > schedule.getIdTeam2()) {
				if (schedule.getTeam().get(0).getIdTeam() < schedule.getTeam().get(1).getIdTeam()) {
					List<Team> list = new ArrayList<Team>();
					list.add(schedule.getTeam().get(1));
					list.add(schedule.getTeam().get(0));
					schedule.setTeam(list);
				}
			}
		}
		return schedules;
	}

	// Kiểm tra điều kiện ngày và team có đang thi đấu h đó k
	public String checkSchedule(Schedule schedule) {
		DateTimeFormatter oldPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Date date = Date.from(schedule.getTimeStart().atZone(ZoneId.systemDefault()).toInstant());
		Instant instant = Instant.ofEpochMilli(date.getTime());
		LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		LocalDate timeTour = tournamentRepository.getById(schedule.getIdTour()).getTimeStart();
		LocalDate timeTourEnd = tournamentRepository.getById(schedule.getIdTour()).getTimeEnd();
		if (localDate.isBefore(timeTour) || localDate.isAfter(timeTourEnd)) {
			return "out of the tournament";
		} else {

			List<Schedule> list = scheduleRepository.getByTournament(schedule.getIdTour());
			if (list == null) {
				return null;
			} else {
				for (Schedule scheduleExit : list) {
					if (scheduleExit.getIdSchedule() == schedule.getIdSchedule()) {
						continue;
					} else {
						if (!schedule.getTimeStart().plusHours(3).isAfter(list.get(0).getTimeStart())) {
							return null;
						} else if (!schedule.getTimeStart().isAfter(scheduleExit.getTimeStart().plusHours(3))) {
							if (schedule.getTimeStart().plusHours(3).isAfter(scheduleExit.getTimeStart())) {
								return "Must be bigger : " + oldPattern.format(scheduleExit.getTimeStart());

							}
						}
					}
				}
			}
		}
		return null;
	}

	public Schedule getById(int idSchedule) {
		Schedule schedule = scheduleRepository.getById(idSchedule);
		if (schedule.getIdTeam1() > schedule.getIdTeam2()) {
			if (schedule.getTeam().get(0).getIdTeam() < schedule.getTeam().get(1).getIdTeam()) {
				List<Team> list = new ArrayList<Team>();
				list.add(schedule.getTeam().get(1));
				list.add(schedule.getTeam().get(0));
				schedule.setTeam(list);
			}
			return schedule;

		} else {
			return schedule;
		}
	}

	public void Delete(int idSchedule) {
		scheduleRepository.Delete(idSchedule);

	}

	public ResponseQuery<?> edit(Schedule schedule) {
		String result = checkSchedule(schedule);
		if (result == null) {
			scheduleRepository.edit(schedule);
			return ResponseQuery.success("Edit Success", scheduleRepository.getById(schedule.getIdSchedule()));
		}
		return ResponseQuery.faild(result, 400);

	}

	public void statusAuto() {
		List<Schedule> list = scheduleRepository.getAll();
		LocalDateTime now = LocalDateTime.now();
		for (Schedule schedule : list) {
			if (schedule.getTimeEnd() == null) {
				if (schedule.getTimeStart().isBefore(now)) {
					scheduleRepository.updateStatus1(schedule.getIdSchedule());
				}
			}

		}
	}

	public List<Schedule> getAll() {
		List<Schedule> schedules = scheduleRepository.getAll();
		for (Schedule schedule : schedules) {
			if (schedule.getIdTeam1() > schedule.getIdTeam2()) {
				if (schedule.getTeam().get(0).getIdTeam() < schedule.getTeam().get(1).getIdTeam()) {
					List<Team> list = new ArrayList<Team>();
					list.add(schedule.getTeam().get(1));
					list.add(schedule.getTeam().get(0));
					schedule.setTeam(list);
				}
			}
		}
		return schedules;
	}

	public ResponseQuery<?> update(ScheduleForm scheduleForm) {
		Schedule schedule = modelMapper.map(scheduleForm, Schedule.class);
		LocalDateTime time = LocalDateTime.now();
		schedule.setTimeEnd(time);
		try {
			if (scheduleForm.getImageFile() != null) {
				schedule.setImage(UploadFile.saveFile(scheduleForm.getImageFile()));

			}
			if (scheduleForm.getVideoFile() != null) {
				schedule.setVideo(UploadFile.saveVideo(scheduleForm.getVideoFile()));

			}
			if (schedule.getScore1() > schedule.getScore2()) {
				schedule.setWinner(scheduleRepository.getById(schedule.getIdSchedule()).getIdTeam1());
				schedule.setAdraw(0);
			}
			if (schedule.getScore1() < schedule.getScore2()) {
				schedule.setWinner(scheduleRepository.getById(schedule.getIdSchedule()).getIdTeam2());
				schedule.setAdraw(0);
			}
			if (schedule.getScore1() == schedule.getScore2()) {
				schedule.setWinner(0);
				schedule.setAdraw(1);
			}
			scheduleRepository.update(schedule);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseQuery.success("Update Success", 0);

	}

	public ResponseQuery<?> goal(List<GoalDto> goals) {
		if (goals.size() != 0 && goals != null) {
			goalRepository.format(goals.get(0).getIdSchedule());
		}
		for (GoalDto goal : goals) {
			goalRepository.create(goal.getProfile().getId(), goal.getIdSchedule(), goal.getTime(), goal.getTeam());
		}
		return ResponseQuery.success("Update Success", goals);
	}

	public void deleteByTeamTour(int idTeam, int idTournament) {
		scheduleRepository.deleteByTeamTour(idTeam, idTournament);

	}

	public void deleteByTour(int idTournament) {
		scheduleRepository.deleteByTour(idTournament);

	}

	public ResponseQuery<?> recentMatch() {
		Schedule schedule = getByStatus(0).get(0);
		return ResponseQuery.success("Connect", schedule);

	}

	public List<Schedule> getByStatus(int status) {
		return scheduleRepository.getByStatus(status);
	}

	public String lastVideo() {
		List<Schedule> schedules = scheduleRepository.getByStatus(2);
		for (int i = schedules.size() - 1; i >= 0; i--) {
			if (schedules.get(i).getVideo() != null || schedules.get(i).getVideo() != "") {
				return schedules.get(i).getVideo();
			}
		}
		return null;
	}

	public List<Schedule> lastResults() {
		return scheduleRepository.getByStatus(2);
	}

	public List<Schedule> teamResults(int idTeam, int idTournament) {
		// TODO Auto-generated method stub
		return scheduleRepository.teamResults(idTeam, idTournament);
	}

	public List<Schedule> teamLastResults(int idTeam) {
		// TODO Auto-generated method stub
		return scheduleRepository.teamLastResults(idTeam);
	}

	public List<Schedule> scheduleTeam(int idTeam) {
		return scheduleRepository.scheduleTeam(idTeam);
	}

	public ConsecutiveDetail consecutiveDetail(int idTeam) {
		ConsecutiveDetail consecutiveDetail = new ConsecutiveDetail();
		int consecutiveWin = 0;
		int consecutiveLose = 0;
		int consecutiveDraw = 0;
		List<Integer> listTour = historyRepository.getByTeam(idTeam);
		for (Integer integer : listTour) {
			int maxWin = 0;
			int maxLose = 0;
			int maxDraw = 0;

			List<Schedule> schedules = scheduleRepository.getScheduleByTeam(idTeam, integer);
			if (schedules.size() > 0) {
				if (schedules.get(0).getWinner() == idTeam) {
					maxWin = 1;
					if (maxWin >= consecutiveWin) {
						consecutiveWin = maxWin;
					}
					maxDraw = 1;
					if (maxDraw >= consecutiveDraw) {
						consecutiveDraw = maxDraw;
					}
				} else if (schedules.get(0).getAdraw() == 1) {
					maxDraw = 1;
					if (maxDraw >= consecutiveDraw) {
						consecutiveDraw = maxDraw;
					}
				} else {
					maxLose = 1;
					if (maxLose >= consecutiveLose) {
						consecutiveLose = maxLose;
					}
				}
				for (int i = 1; i < schedules.size(); i++) {
					if (schedules.get(i).getWinner() == idTeam && schedules.get(i - 1).getWinner() == idTeam) {
						maxWin++;
						maxLose = 0;
						maxDraw++;
						if (maxDraw >= consecutiveDraw) {
							consecutiveDraw = maxDraw;
						}
						if (maxWin >= consecutiveWin) {
							consecutiveWin = maxWin;
						}
					} else if (schedules.get(i).getAdraw() == 0||schedules.get(i).getWinner()==idTeam) {
						maxDraw++;
						maxWin = 0;
						maxLose = 0;
						if (maxDraw >= consecutiveDraw) {
							consecutiveDraw = maxDraw;
						}
					} else {
						maxDraw = 0;
						maxLose++;
						maxWin = 0;
						if (maxLose >= consecutiveLose) {
							consecutiveLose = maxLose;
						}
					}
				}
			}
		}
		consecutiveDetail.setAdrwa(consecutiveDraw);
		consecutiveDetail.setLose(consecutiveLose);
		consecutiveDetail.setWin(consecutiveWin);
		return consecutiveDetail;
	}

}
