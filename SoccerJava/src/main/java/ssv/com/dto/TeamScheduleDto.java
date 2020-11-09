package ssv.com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamScheduleDto{
	private String monthStart;
	private String dayStart;
	private String nameTeam1;
	private String logoTeam1;
	private String nameTeam2;
	private String logoTeam2;
	private String timeStart;
	private String nameTour;
	private int status;

}