package ssv.com.mapper;

import org.apache.ibatis.annotations.Mapper;

import ssv.com.entity.Profile;

@Mapper
public interface ProfileMapper {
	public Long saveProfile(Profile profile);

	public Profile checkPhoneExist(String number);

	public Profile findByEmail(String email);
}
