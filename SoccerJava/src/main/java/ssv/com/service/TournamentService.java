package ssv.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssv.com.repository.TournamentRepository;

@Service
public class TournamentService {
	@Autowired
	private TournamentRepository tournamentRepository;
}
