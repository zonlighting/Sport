package ssv.com.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDetail {
	private String nameTeam;
	private String logo;
	private int totalMatch;
	private int totalWin;
	private int totalMatchByTour;
	private int totalWinByTour;
	private int pointByTour;
	private int totalAdrawByTour;
}
