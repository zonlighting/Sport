package ssv.com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankDto{
	private int idTeam;
	private String teamName;
	private String logo;
	private int matchPlayed;
	private int win;
	private int lose;
	private int tie;
	private int point;
}


