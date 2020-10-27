package ssv.com.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@EnableGlobalAuthentication
@NoArgsConstructor
@JsonIgnoreProperties(value = {"authorities" })
public class Account {
	  private int id;
	  private String email;
	  private String password;
	  private String role;
	  private String token;
	  public List<GrantedAuthority> getAuthorities() {
		    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		      authorities.add(new SimpleGrantedAuthority(role));
		    return authorities;
		  }
	public Account(String email, String password) {
		this.email = email;
		this.password = password;
	}

}
