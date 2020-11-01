package ssv.com.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class History {
	private Long idMember;
	private int idTeam;
	private int idTournament;
}
