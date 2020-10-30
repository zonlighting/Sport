package ssv.com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ssv.com.entity.Profile;
import ssv.com.mapper.ProfileMapper;

@Repository
public class ProfileRepository {
	@Autowired
	private ProfileMapper profileMapper;

	public Long saveProfile(Profile profile) {
		return profileMapper.saveProfile(profile);
	}

	public Optional<Profile> getByEmail(String email) {
		return profileMapper.findByEmail(email);
	}

	public Optional<Profile> checkPhoneExist(String phone) {
		return profileMapper.checkPhoneExist(phone);
	}

	public List<Profile> getMembers() {
		return profileMapper.getMembers();
	}
}
