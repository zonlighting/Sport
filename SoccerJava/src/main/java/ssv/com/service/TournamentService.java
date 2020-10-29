package ssv.com.service;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public void create(TournamentForm tournamentForm) {
		Tournament tournament = modelMapper.map(tournamentForm, Tournament.class);
		try {
			tournament.setBanner(UploadFile.saveVideo(tournamentForm.getBannerFile()));
			tournamentRepository.create(tournament);
			// id tournament new
			int idTournament = tournamentRepository.getIdNew();
			// tạo dữ liệu trong bảng history
			for (Integer listTeam : tournamentForm.getListTeam()) {
				// getByid lấy data team theo id
				for (Profile profile : teamService.getById(listTeam).getListTeam()) {
					History history = new History(profile.getIdTeam(), listTeam, idTournament);
					historyService.create(history);
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ResponseQuery<?> addTeam(Integer[] listTeam, int idTournament) {
		for (Integer idTeam : listTeam) {
			if(teamService.getById(idTeam).getStatus()!=0) {
				return ResponseQuery.faild("Team participated in the tournament", teamService.getById(idTeam));
			}
		}
		for (Integer idTeam : listTeam) {
			for (Profile profile : teamService.getById(idTeam).getListTeam()) {
				History history = new History(profile.getIdTeam(), idTeam, idTournament);
				historyService.create(history);
			}
		}
		
		return ResponseQuery.success("Add successful team", getById(idTournament));

	}

	public Tournament getById(int idTournament) {
		return tournamentRepository.getById(idTournament);
	}

	public ResponseQuery<?> deleteTeam(int idTeam, int idTournament) {
		if(teamService.getById(idTeam).getIdTour==idTournament) {
			for (Profile profile : teamService.getById(idTeam).getListTeam()) {
				History history = new History(profile.getIdTeam(), idTeam, idTournament);
				historyService.create(history);
			}
		}
		return ResponseQuery.faild("Team does not exist", 400);
	}

	public ResponseQuery<?> update(TournamentForm tournamentForm, int idTournament) {
		Tournament tournament = modelMapper.map(tournamentForm, Tournament.class);
		if(tournamentForm.getBannerFile().isEmpty()) {
			try {
				tournament.setBanner(UploadFile.saveVideo(tournamentForm.getBannerFile()));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		tournamentRepository.update(tournament,idTournament);
		return ResponseQuery.faild("Update successful tournament", tournamentRepository.getById(idTournament));
	}
	

}
