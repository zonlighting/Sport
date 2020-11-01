package ssv.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssv.com.entity.History;
import ssv.com.repository.HistoryRepository;

@Service
public class HistoryService {
	@Autowired
	private HistoryRepository historyRepository;

	public void create(History history) {
		historyRepository.create(history);
		
	}

	public void delete(int idTournament) {
		historyRepository.delete(idTournament);		
	}
}
