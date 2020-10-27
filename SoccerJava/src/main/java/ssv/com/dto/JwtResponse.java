package ssv.com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ssv.com.entity.Account;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private Account account;
	private String token;
}
