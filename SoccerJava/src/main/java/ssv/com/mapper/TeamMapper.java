package ssv.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ssv.com.entity.Team;

@Mapper
public interface TeamMapper {
	List<Team> getTeams();
}
