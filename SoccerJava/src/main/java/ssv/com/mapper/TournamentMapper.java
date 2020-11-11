package ssv.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ssv.com.entity.Tournament;
@Mapper
public interface TournamentMapper {

	List<Tournament> getAll();

	void create(Tournament tournament);

	int getIdNew();

	Tournament getById(int idTournamnet);

	void update(Tournament tournament);

	void delete(int idTournament);

	List<Tournament> tournamentUpComming();

	List<Tournament> tournamentStatus(int status);

	void finished(int idTournament);

	void ongGame(int idTournament);

	List<Tournament> getAllSchedule();

	List<Tournament> getAllScheduleStatus(int status);

}
