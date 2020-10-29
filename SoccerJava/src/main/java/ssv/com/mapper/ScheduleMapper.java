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

}
