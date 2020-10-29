package ssv.com.service;

import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssv.com.RandomPass;
import ssv.com.dto.ResponseQuery;
import ssv.com.entity.Account;
import ssv.com.entity.Profile;
import ssv.com.file.UploadFile;
import ssv.com.form.ProfileForm;
import ssv.com.repository.AccountRepository;
import ssv.com.repository.ProfileRepository;

@Service
public class ProfileService {
	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ModelMapper modelMapper;

	public void saveProfile(Profile profile) {
		try {
			profileRepository.saveProfile(profile);

		} catch (Exception e) {
			ResponseQuery.faild("Create profile failed", profile);
		}
	}

	@Transactional
	public ResponseQuery<?> saveMember(ProfileForm profileForm) throws Exception {
		Random rand = new Random();
		if (profileRepository.getByEmail(profileForm.getEmail()) != null) {
			return ResponseQuery.faild("Email has already exists", 300);
		}else if(profileRepository.checkPhoneExist(profileForm.getPhone()) != null){
			return ResponseQuery.faild("Phone number has already exists", 301);
		}
		else {

			Account account = new Account();
			account.setEmail(profileForm.getEmail());
			String pass = new RandomPass().randomAlphaNumeric(8);
			account.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt(12)));
			account.setRole("ROLE_MEMBER");

			Profile profile = modelMapper.map(profileForm, Profile.class);
			profile.setAvatar(UploadFile.saveFile(profileForm.getFile()));
			accountRepository.create(account);
			profileRepository.saveProfile(profile);

//			SimpleMailMessage message = new SimpleMailMessage();
//			message.setTo(profileForm.getEmail());
//			message.setSubject("Email and password");
//			message.setText(account.getUsername() + "-" + pass);
//			this.emailSender.send(message);

			return ResponseQuery.success("Created memeber succcess", profile);
		}
	}
}
