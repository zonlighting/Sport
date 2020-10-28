package ssv.com.entity;

import java.time.LocalDate;
import java.time.LocalTime;

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
	private LocalDate date;
	private LocalTime timeEnd;
	private LocalTime timeStart;
	private String image;
	private String video;
	private int Status;
	private int score1;
	private int score2;
}
