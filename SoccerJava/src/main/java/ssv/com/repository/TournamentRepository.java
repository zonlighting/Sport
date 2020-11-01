package ssv.com.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ssv.com.entity.Tournament;
import ssv.com.mapper.TournamentMapper;

@Repository
public class TournamentRepository {
	@Autowired
	private TournamentMapper tournamentMapper;

	public List<Tournament> getAll() {
		return tournamentMapper.getAll();
	}

	public void create(Tournament tournament) {
		tournamentMapper.create(tournament);
		
	}

	public int getIdNew() {
		return tournamentMapper.getIdNew();
	}

	public Tournament getById(int idTournament) {
		return tournamentMapper.getById(idTournament);
	}

	public void update(Tournament tournament, int idTournament) {
		 tournamentMapper.update(tournament,idTournament);	
	}

	public void delete(int idTournament) {
		 tournamentMapper.delete(idTournament);	

		
	}

	public List<Tournament> tournamentUpComming() {
		// TODO Auto-generated method stub
		return tournamentMapper.tournamentUpComming();
	}
}
