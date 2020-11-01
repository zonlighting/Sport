package ssv.com.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.var;
import ssv.com.dto.ResponseQuery;
import ssv.com.entity.Schedule;
import ssv.com.form.ScheduleForm;
import ssv.com.repository.ScheduleRepository;
import ssv.com.repository.TournamentRepository;

@Service
public class ScheduleService {
	@Autowired
	private ScheduleRepository scheduleRepository;
	@Autowired
	private TournamentRepository tournamentRepository;

	// mỗi trận đấu phải cách nhau ít nhất 3h
	public ResponseQuery<?> create(Schedule schedule) {
		if(tournamentRepository.getById(schedule.getIdTour()).getSchedule().size()==0) {
			scheduleRepository.create(schedule);
			return ResponseQuery.success("Create Success", schedule);
		}
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
	//Kiểm tra điều kiện ngày và team có đang thi đấu h đó k
	public String checkSchedule(Schedule schedule) {
		Date date = Date.from(schedule.getTimeStart().atZone(ZoneId.systemDefault()).toInstant());
		Instant instant = Instant.ofEpochMilli(date.getTime());
		LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		LocalDate timeTour = tournamentRepository.getById(schedule.getIdTour()).getTimeStart();
		LocalDate timeTourEnd = tournamentRepository.getById(schedule.getIdTour()).getTimeEnd();
		if (localDate.isAfter(timeTour)&&localDate.isBefore(timeTourEnd)) {
			var a=scheduleRepository.getByTournament(schedule.getIdTour());
			List<Schedule> list = scheduleRepository.getByTournament(schedule.getIdTour());
			LocalDateTime timeStart = schedule.getTimeStart();
			for (Schedule scheduleExit : list) {
				LocalDateTime timeStartExit = scheduleExit.getTimeStart();
				if (schedule.getLocation() == scheduleExit.getLocation()) {
					if (timeStart.equals(timeStartExit)) {
						return "Time coincides with" + schedule.getTimeStart();
					} else {
						if (!timeStart.plusHours(3).isAfter(timeStartExit)) {
							break;
						}
						if (!timeStart.isAfter(timeStartExit.plusHours(3))) {
							return "Time must way " + schedule.getTimeStart();
						}
					}
					if (schedule.getIdTeam1() == scheduleExit.getIdTeam1()
							|| schedule.getIdTeam1() == scheduleExit.getIdTeam2()) {
						if (timeStart.plusHours(3).isAfter(timeStartExit)
								|| !timeStart.isAfter(timeStartExit.plusHours(3))) {
							return "The team 1 is playing around this time " + timeStartExit;
						}
					}
					if (schedule.getIdTeam2() == scheduleExit.getIdTeam1()
							|| schedule.getIdTeam2() == scheduleExit.getIdTeam2()) {
						if (timeStart.plusHours(3).isAfter(timeStartExit)
								|| !timeStart.isAfter(timeStartExit.plusHours(3))) {
							return "The team 2 is playing around this time" + timeStartExit;
						}

					}
				} else {
					if (schedule.getIdTeam1() == scheduleExit.getIdTeam1()
							|| schedule.getIdTeam1() == scheduleExit.getIdTeam2()) {
						if (timeStart.plusHours(3).isAfter(timeStartExit)
								|| !timeStart.isAfter(timeStartExit.plusHours(3))) {
							return "The team 1 is playing around this time " + timeStartExit;
						}
					}
					if (schedule.getIdTeam2() == scheduleExit.getIdTeam1()
							|| schedule.getIdTeam2() == scheduleExit.getIdTeam2()) {
						if (timeStart.plusHours(3).isAfter(timeStartExit)
								|| !timeStart.isAfter(timeStartExit.plusHours(3))) {
							return "The team 2 is playing around this time" + timeStartExit;
						}
					}

				}
			}
			return "In addition to tournament time." +"Time tournament : "+ timeTour +"->" +timeTourEnd;
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
		List<Schedule> list=scheduleRepository.getAll();
		LocalDate now=LocalDate.now();
		for (Schedule schedule : list) {
			if(schedule.getTimeStart().equals(now)) {
				scheduleRepository.updateStatus(schedule.getIdSchedule());
			}
		}
	}

	public List<Schedule> getAll() {
		return scheduleRepository.getAll();
	}

	public ResponseQuery<?> update(ScheduleForm scheduleForm) {
		// TODO Auto-generated method stub
		return null;
	}
}
