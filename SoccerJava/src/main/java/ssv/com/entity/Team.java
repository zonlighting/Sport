package ssv.com.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {
	private int idTeam;
	private String nameTeam;
	private int totalmatch;
	private int totalwin;
	private String type;
	private String description;
	private String logo;
	private LocalDate createDate;
	private int idTour;
	private Tournament tournament;
	private List<Profile> profile;
	private String tourName;
}
