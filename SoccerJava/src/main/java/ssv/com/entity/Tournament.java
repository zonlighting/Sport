package ssv.com.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Tournament {
	private int idTournament;
	private String nameTournament;
	private int status;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate timeEnd;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate timeStart;
	private String description;
	private String banner;
	private List<Team> team;
	private List<Schedule> schedule;
}
