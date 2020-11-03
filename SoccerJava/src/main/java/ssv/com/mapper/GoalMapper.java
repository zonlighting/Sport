package ssv.com.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoalMapper {

	void create(Long idMember, int idSchedule, String time, int idTeam);

	void delete(int idSchedule);

}
