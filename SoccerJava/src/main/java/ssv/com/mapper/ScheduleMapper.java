package ssv.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ssv.com.entity.Schedule;

@Mapper
public interface ScheduleMapper {

	List<Schedule> getByTournament(int idTour);

	void create(Schedule schedule);

	Schedule getById(int idSchedule);

	void delete(int idSchedule);

	void edit(Schedule schedule);

	List<Schedule> getAll();

	void updateStatus(int idSchedule);

	int teamTotalMatch(int idTeam);

	int teamTotalWin(int idTeam);

	int teamTotalMatchByTour(int idTeam, int idTour);

	int teamTotalWinByTour(int idTeam, int idTour);

	int teamTotalAdrawByTour(int idTeam, int idTour);

	void udapte(Schedule schedule);

	void deleteByTeamTour(int idTeam, int idTournament);

	void deleteByTour(int idTournament);

	List<Schedule> getByStatus(int status);

	void finished(int idSchedule);

	void onGame(int idSchedule);

	int teamAdraw(int idTeam);

	List<Schedule> teamResults(int idTeam, int idTournament);

}
