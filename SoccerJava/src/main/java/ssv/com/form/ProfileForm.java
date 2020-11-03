package ssv.com.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileForm {
	private String name;
	private String email;
	private String phone;
	private String age;
	private String gender;
	private String country;
	private String position;
	private String avatar;
	private MultipartFile file;
}
