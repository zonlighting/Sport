package ssv.com.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class History {
	private int idHistory;
	private Long idMember;
	private int idTeam;
	private int idTournament;
	private Profile profile;
	public History(Long idMember, int idTeam, int idTournament) {
		this.idMember = idMember;
		this.idTeam = idTeam;
		this.idTournament = idTournament;
	}
}
