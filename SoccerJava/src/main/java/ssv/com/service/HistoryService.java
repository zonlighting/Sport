package ssv.com.service;

import java.util.List;

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

	public void deleteTeam(int idTeam, int idTournament) {
		historyRepository.deleteTeam(idTeam,idTournament);

	}

	public List<History> historyMember(int idTour, int idTeam) {
		return historyRepository.historyMember(idTour,idTeam);
	}
}
