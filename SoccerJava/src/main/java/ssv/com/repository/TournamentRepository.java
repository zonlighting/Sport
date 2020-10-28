package ssv.com.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ssv.com.mapper.TournamentMapper;

@Repository
public class TournamentRepository {
	@Autowired
	private TournamentMapper tournamentMapper;
}
