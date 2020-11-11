package ssv.com.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ssv.com.entity.Schedule;
import ssv.com.mapper.ScheduleMapper;

@Repository
public class ScheduleRepository {
	@Autowired ScheduleMapper scheduleMapper;

	public List<Schedule> getByTournament(int idTournament) {
		return scheduleMapper.getByTournament(idTournament);
	}

	public void create(Schedule schedule) {
		scheduleMapper.create(schedule);
	}

	public Schedule getById(int idSchedule) {
		return scheduleMapper.getById(idSchedule);
	}

	public void Delete(int idSchedule) {
		scheduleMapper.delete(idSchedule);

	}

	public void edit(Schedule schedule) {
		scheduleMapper.edit(schedule);

	}

	public List<Schedule> getAll() {
		return scheduleMapper.getAll();
	}

	public void updateStatus(int idSchedule) {
		scheduleMapper.updateStatus(idSchedule);


	}

	public int teamTotalMatch(int idTeam) {
		// TODO Auto-generated method stub
		return scheduleMapper.teamTotalMatch(idTeam);
	}

	public int teamTotalWin(int idTeam) {
		// TODO Auto-generated method stub
		return scheduleMapper.teamTotalWin(idTeam);
	}

	public int teamTotalMatchByTour(int idTeam, int idTour) {
		// TODO Auto-generated method stub
		return scheduleMapper.teamTotalMatchByTour(idTeam,idTour);
	}

	public int teamTotalWinByTour(int idTeam, int idTour) {
		// TODO Auto-generated method stub
		return scheduleMapper.teamTotalWinByTour(idTeam,idTour);
	}

	public int teamTotalAdrawByTour(int idTeam, int idTour) {
		// TODO Auto-generated method stub
		return scheduleMapper.teamTotalAdrawByTour(idTeam,idTour);
	}

	public void update(Schedule schedule) {
		 scheduleMapper.udapte(schedule);

	}

	public void deleteByTeamTour(int idTeam, int idTournament) {
		scheduleMapper.deleteByTeamTour(idTeam,idTournament);

	}

	public void deleteByTour(int idTournament) {
		scheduleMapper.deleteByTour(idTournament);


	}

	public List<Schedule> getByStatus(int status) {
		return scheduleMapper.getByStatus(status);
	}


	public void updateStatus1(int idSchedule) {
		scheduleMapper.onGame(idSchedule);
	}

	public int teamAdraw(int idTeam) {
		// TODO Auto-generated method stub
		return scheduleMapper.teamAdraw(idTeam);
	}

	public List<Schedule> teamResults(int idTeam, int idTournament) {
		// TODO Auto-generated method stub
		return scheduleMapper.teamResults(idTeam,idTournament);
	}

	public List<Schedule> teamLastResults(int idTeam) {
		// TODO Auto-generated method stub
		return scheduleMapper.teamLastResults(idTeam);
	}

	public List<Schedule> scheduleTeam(int idTeam) {
		// TODO Auto-generated method stub
		return scheduleMapper.scheduleTeam(idTeam);
	}


}
