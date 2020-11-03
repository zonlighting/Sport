package ssv.com.form;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ssv.com.entity.Profile;
import ssv.com.entity.Tournament;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamForm {
	private String nameTeam;
	private String description;
	private String country;
	private MultipartFile file;
}
