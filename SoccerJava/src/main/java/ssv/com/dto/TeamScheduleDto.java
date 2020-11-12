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
	private int idSchedule;
	private int year;
	private String monthStart;
	private String dayStart;
	private String nameTeam1;
	private String logoTeam1;
	private String nameTeam2;
	private String logoTeam2;
	private int score1;
	private int score2;
	private String timeStart;
	private String timeEnd;
	private String nameTour;
	private int status;
}