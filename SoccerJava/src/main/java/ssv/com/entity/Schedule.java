package ssv.com.entity;

import java.sql.Time;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Schedule {
	private int idSchedule;
	private int idTeam1;
	private int idTeam2;
	private int location;
	private int idTour;
	private int date;
	private Time timeEnd;
	private Time timeStart;
	private String image;
	private String video;
	private int Status;
	private int score1;
	private int score2;
}
