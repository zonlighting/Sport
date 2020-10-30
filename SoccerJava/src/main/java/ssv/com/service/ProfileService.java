package ssv.com.service;

import java.io.IOException;
import java.util.Optional;
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
import ssv.com.exception.ResourceExistsException;
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
	public Profile saveMember(ProfileForm profileForm) throws Exception, ResourceExistsException {
		if (profileRepository.getByEmail(profileForm.getEmail()).isPresent()) {
			throw new ResourceExistsException("Email number has already exists", 300);
		} else if (profileRepository.checkPhoneExist(profileForm.getPhone()).isPresent()) {
			throw new ResourceExistsException("Phone number has already exists", 301);
		} else {
			// Create Account
			Account account = new Account();
			account.setEmail(profileForm.getEmail());
			String pass = new RandomPass().randomAlphaNumeric(8);
			account.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt(12)));
			account.setRole("ROLE_MEMBER");

			// Create Profile
			Profile profile = modelMapper.map(profileForm, Profile.class);
			if (profileForm.getFile() != null && !profileForm.getFile().getOriginalFilename().isEmpty()) {
				profile.setAvatar(UploadFile.saveFile(profileForm.getFile()));
			}
			accountRepository.create(account);
			profileRepository.saveProfile(profile);
//			SimpleMailMessage message = new SimpleMailMessage();
//			message.setTo(profileForm.getEmail());
//			message.setSubject("Email and password");
//			message.setText(account.getUsername() + "-" + pass);
//			this.emailSender.send(message);

			return profile;
		}
	}
}
