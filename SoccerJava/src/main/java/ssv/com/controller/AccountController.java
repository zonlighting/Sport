	package ssv.com.controller;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ssv.com.RandomPass;
import ssv.com.dto.JwtResponse;
import ssv.com.dto.ResponseQuery;
import ssv.com.entity.Account;
import ssv.com.entity.Profile;
import ssv.com.service.AccountService;
import ssv.com.service.JwtService;
//import ssv.com.service.ProfileService;
import ssv.com.service.ProfileService;

@RestController
@RequestMapping("/api/v1/account")
@ResponseBody
public class AccountController {
	@Autowired
	private AccountService accountService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private JwtService jwtService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseQuery<?> login(HttpServletRequest request, @RequestBody Account user) {
		String result = "";
		JwtResponse jwtResponse = new JwtResponse();
		if (!user.getEmail().isEmpty() && !user.getPassword().isEmpty()) {
			try {
				if (accountService.checkLogin(user)) {
					result = jwtService.generateTokenLogin(user.getEmail());
					jwtResponse.setAccount(accountService.loadUserByEmail(user.getEmail()));
					jwtResponse.setToken(result);

					return ResponseQuery.success("Login success", jwtResponse);
				} else {
					result = "Wrong info account";
					return ResponseQuery.faild("Wrong email or password", null);
				}
			} catch (Exception ex) {

				return ResponseQuery.faild("INTERNAL_SERVER_ERROR", null);
			}
		}
		return ResponseQuery.faild("Wrong input data", null);
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseQuery<?> sigup(@RequestBody Account account) {
		String emailPattern = "\\w+@\\w+[.]\\w+";
		if (!account.getEmail().isEmpty() && !account.getPassword().isEmpty()
				&& account.getEmail().matches(emailPattern)) {
			if (accountService.checkEmail(account.getEmail()) == false) {
				return ResponseQuery.faild("Email has existed", 0);
			} else {
				// create account
				String hash = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(12));
				account.setPassword(hash);
				account.setRole("ROLE_ADMIN");
				accountService.create(account);

				// create default profile
				Profile profile = new Profile();
				profile.setName(account.getEmail().substring(0, 4));
				profile.setEmail(account.getEmail());
				profile.setAvatar("/images/defaultuser.png");
				profileService.saveProfile(profile);

				return ResponseQuery.success("Created profile succcess", account);
			}
		}
		return ResponseQuery.faild("Form data has wrong type value", account);
	}

	@PostMapping(value = "/autoLogin")
	public ResponseQuery<?> autoLogin(HttpServletRequest request) {
		Account account = (Account) request.getSession().getAttribute("userInfo");
		return ResponseQuery.success("Recivce Success", account);
	}

	@RequestMapping(value = "/forget/{email}", method = RequestMethod.POST)
	public ResponseQuery<?> forget(@PathVariable String email) {
		String emailPattern = "\\w+@\\w+[.]\\w+";
		if (email.isEmpty() && email.matches(emailPattern)) {
			return ResponseQuery.faild("Wrong email pattern", null);
		}
		try {
			if (!accountService.checkEmail(email)) {
				SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(email);
				message.setSubject("Password");
				Account account = new Account();
				account.setEmail(email);
				account.setPassword(new RandomPass().randomAlphaNumeric(8));
//				accountService.replacePass(account);
				message.setText(account.getPassword());
				this.emailSender.send(message);
				return ResponseQuery.success("Change password success , please check email to recive password",
						account);
			} else {
				return ResponseQuery.faild("Email not found ", email);
			}

		} catch (Exception e) {

		}

		return ResponseQuery.faild("Failed To Change Password ", null);

	}
}
