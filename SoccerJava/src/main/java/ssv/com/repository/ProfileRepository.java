package ssv.com.repository;

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
}
