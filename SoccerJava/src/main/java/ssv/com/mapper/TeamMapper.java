package ssv.com.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import ssv.com.entity.Team;

@Mapper
public interface TeamMapper {
	List<Team> getTeams();

	Long createTeam(Team team);

	Team getTeamById(int idTeam);

	List<Team> getTeamNoTournament();

	void newTournament(int idTeam, int idTournament);

	void formatTournament(int idTournament);

	public void updateTeam(@Param("id") int id, @Param("team") Team team);
}
