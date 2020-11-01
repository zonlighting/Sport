package ssv.com.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Schedule {
	private int idSchedule;
	private int idTeam1;
	private int idTeam2;
	private String location;
	private int idTour;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timeEnd;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timeStart;
	private String image;
	private String video;
	private int Status;
	private int score1;
	private int score2;
	private List<Team> team;
}
