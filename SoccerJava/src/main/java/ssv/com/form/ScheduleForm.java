package ssv.com.form;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleForm {
	private MultipartFile imageFile;
	private MultipartFile videoFile;
	private int score1;
	private int score2;
}
