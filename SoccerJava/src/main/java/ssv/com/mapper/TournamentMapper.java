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

	void update(Tournament tournament, int idTournament);
}
