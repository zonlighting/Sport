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

	
}
