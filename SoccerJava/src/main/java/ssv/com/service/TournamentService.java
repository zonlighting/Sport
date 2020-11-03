package ssv.com.service;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.val;
import ssv.com.dto.ResponseQuery;
import ssv.com.dto.TournamentForm;
import ssv.com.entity.History;
import ssv.com.entity.Profile;
import ssv.com.entity.Tournament;
import ssv.com.file.UploadFile;
import ssv.com.repository.TournamentRepository;

@Service
public class TournamentService {
	@Autowired
	private ModelMapper modelMapper = new ModelMapper();
	@Autowired
	private TournamentRepository tournamentRepository;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private TeamService teamService;

	public List<Tournament> getAll() {
		return tournamentRepository.getAll();
	}

	public boolean checkName(String nameTournament) {
		for (Tournament tournament : tournamentRepository.getAll()) {
			if (tournament.getNameTournament().toLowerCase().compareTo(nameTournament.toLowerCase()) == 0) {
				return false;
			}
		}
		return true;
	}

	public ResponseQuery<?> create(TournamentForm tournamentForm) {
		Tournament tournament = modelMapper.map(tournamentForm, Tournament.class);
		try {
			if (tournamentForm.getBannerFile() != null) {
				tournament.setBanner(UploadFile.saveVideo(tournamentForm.getBannerFile()));
			}
			tournamentRepository.create(tournament);

			// id tournament new
			int idTournament = tournamentRepository.getIdNew();
			// tạo dữ liệu trong bảng history
			for (Integer listTeam : tournamentForm.getListTeam()) {
				// getByid lấy data team theo id
//				teamService.newTournament(listTeam, tournamentRepository.getIdNew());
				for (Profile profile : teamService.getTeamById(listTeam).getProfile()) {
					History history = new History(profile.getId(), listTeam, idTournament);
					historyService.create(history);
				}
			}

			return ResponseQuery.success("Create successful tournament", tournament);
		} catch (IllegalStateException e) {
			tournamentRepository.delete(tournamentRepository.getIdNew());
			return ResponseQuery.faild(e.getMessage(), 888);
		} catch (IOException e) {
			tournamentRepository.delete(tournamentRepository.getIdNew());
			return ResponseQuery.faild(e.getMessage(), 888);
		}

	}

	public ResponseQuery<?> addTeam(Integer[] listTeam, int idTournament) {
		for (Integer idTeam : listTeam) {
			if (teamService.getTeamById(idTeam).getIdTour() != 0) {
				return ResponseQuery.faild("Team participated in the tournament", teamService.getTeamById(idTeam));
			}
		}
		for (Integer idTeam : listTeam) {
			for (Profile profile : teamService.getTeamById(idTeam).getProfile()) {
				History history = new History(profile.getId(), idTeam, idTournament);
				historyService.create(history);
			}
		}

		return ResponseQuery.success("Add successful team", getById(idTournament));

	}

	public Tournament getById(int idTournament) {
		return tournamentRepository.getById(idTournament);
	}

	public ResponseQuery<?> deleteTeam(int idTeam, int idTournament) {
		if (teamService.getTeamById(idTeam).getIdTour() == idTournament) {
			for (Profile profile : teamService.getTeamById(idTeam).getProfile()) {
				History history = new History(profile.getId(), idTeam, idTournament);
				historyService.create(history);
			}
		}
		return ResponseQuery.faild("Team does not exist", 400);
	}

	public ResponseQuery<?> update(TournamentForm tournamentForm) {
		Tournament tournament = modelMapper.map(tournamentForm, Tournament.class);
		if (tournamentForm.getBannerFile().isEmpty()) {
			try {
				tournament.setBanner(UploadFile.saveVideo(tournamentForm.getBannerFile()));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		tournamentRepository.update(tournament, tournament.getIdTournament());
		return ResponseQuery.faild("Update successful tournament",
				tournamentRepository.getById(tournament.getIdTournament()));
	}

	public ResponseQuery<?> delete(int idTournament) {
		tournamentRepository.delete(idTournament);
//		teamService.formatTournament(idTournament);
		historyService.delete(idTournament);
		return ResponseQuery.success("Delete successful tournament", 200);
	}

	public List<Tournament> tournamentUpComming() {
		// TODO Auto-generated method stub
		return tournamentRepository.tournamentUpComming();
	}

}
