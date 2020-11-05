package ssv.com.service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssv.com.dto.GoalDto;
import ssv.com.dto.RankDto;
import ssv.com.dto.ResponseQuery;
import ssv.com.entity.Goal;
import ssv.com.entity.Schedule;
import ssv.com.entity.Team;
import ssv.com.file.UploadFile;
import ssv.com.form.ScheduleForm;
import ssv.com.repository.GoalRepository;
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
		return scheduleRepository.getByTournament(idTournament);
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
							return "Must be bigger : " + oldPattern.format(scheduleExit.getTimeStart());
						}
					}
				}
			}
		}
		return null;
	}

	public Schedule getById(int idSchedule) {
		return scheduleRepository.getById(idSchedule);
	}

	public void Delete(int idSchedule) {
		scheduleRepository.Delete(idSchedule);

	}

	public ResponseQuery<?> edit(Schedule schedule) {
		String result = checkSchedule(schedule);
		if (result == null) {
			scheduleRepository.edit(schedule);
			return ResponseQuery.success("Create Success", scheduleRepository.getById(schedule.getIdSchedule()));
		}
		return ResponseQuery.faild(result, 400);

	}

	public void statusAuto() {
		List<Schedule> list = scheduleRepository.getAll();
		LocalDate now = LocalDate.now();
		for (Schedule schedule : list) {
			if (schedule.getTimeStart().equals(now)) {
				scheduleRepository.updateStatus(schedule.getIdSchedule());
			}
		}
	}

	public List<Schedule> getAll() {
		return scheduleRepository.getAll();
	}

	public ResponseQuery<?> update(ScheduleForm scheduleForm) {
		Schedule schedule = modelMapper.map(scheduleForm, Schedule.class);
		LocalDateTime time = LocalDateTime.now();
		schedule.setTimeEnd(time);
		try {
			if(scheduleForm.getImageFile()!=null) {
				schedule.setImage(UploadFile.saveFile(scheduleForm.getImageFile()));

			}if(scheduleForm.getVideoFile()!=null) {
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
			goalRepository.create(goal.getProfile().getId(), goal.getIdSchedule(), goal.getTime(),
					goal.getTeam());
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
		List<Schedule> schedules=scheduleRepository.getByStatus(2);
		for (int i = schedules.size()-1; i >= 0; i--) {
			if(schedules.get(i).getVideo()!=null||schedules.get(i).getVideo()!="") {
				return schedules.get(i).getVideo();
			}
		}
		return null;
	}

	public List<Schedule> lastResults() {
		return scheduleRepository.getByStatus(2);
	}

}
