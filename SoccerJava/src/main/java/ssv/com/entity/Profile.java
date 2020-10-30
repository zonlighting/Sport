package ssv.com.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
	private Long id;
	private String name;
	private String email;
	private String phone;
	private String age;
	private String gender;
	private String country;
	private String avatar = "/images/default_user.png";
	private int idTeam;
	private String position;
	private LocalDate createDate = LocalDate.now();
}