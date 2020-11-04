package ssv.com.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ssv.com.entity.History;
import ssv.com.mapper.HistoryMapper;

@Repository
public class HistoryRepository {
	@Autowired
	private HistoryMapper historyMapper;

	public void create(History history) {
		historyMapper.create(history);
	}

	public void delete(int idTournament) {
		historyMapper.delete(idTournament);
		
	}

	public void deleteTeam(int idTeam, int idTournament) {
		historyMapper.deleteTeam(idTeam,idTournament);
		
	}

}	
