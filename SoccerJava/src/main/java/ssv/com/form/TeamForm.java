package ssv.com.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamForm {
	private String nameTeam;
	private String description;
	private String country;
	private MultipartFile file;
}
