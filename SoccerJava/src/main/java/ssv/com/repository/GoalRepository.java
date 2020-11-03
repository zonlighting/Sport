package ssv.com.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ssv.com.mapper.GoalMapper;

@Repository
public class GoalRepository {
	@Autowired
	private GoalMapper goalMapper;
	public void create(Long idMember, int idSchedule, String time, int idTeam) {
		goalMapper.create(idMember,idSchedule,time,idTeam);
		
	}
	public void format(int idSchedule) {
		goalMapper.delete(idSchedule);
		
	}
	
}
