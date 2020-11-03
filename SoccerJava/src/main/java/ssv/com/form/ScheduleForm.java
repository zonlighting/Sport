package ssv.com.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleForm {
	private int idSchedule;
	private MultipartFile imageFile;
	private MultipartFile videoFile;
	private int score1;
	private int score2;
	private String image;
	private String video;
}
