package ssv.com.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import ssv.com.entity.Profile;

@Mapper
public interface ProfileMapper {
	public Long saveProfile(Profile profile);

	public Optional<Profile> checkPhoneExist(String number);

	public Optional<Profile> findByEmail(String email);
}
