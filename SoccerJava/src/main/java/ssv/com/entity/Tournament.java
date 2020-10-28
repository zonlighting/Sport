package ssv.com.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Tournament {
	private int idTournament;
	private String nameTournament;
	private int status;
	private LocalDateTime timeEnd;
	private LocalDateTime timeStart;
	private String description;
	private String banner;
}