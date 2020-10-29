package ssv.com.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TournamentForm {
	private String nameTournament;
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private LocalDate timeEnd;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate timeStart;
	private String description;
	private MultipartFile bannerFile;
	private String banner;
	private Integer[] listTeam;
}
