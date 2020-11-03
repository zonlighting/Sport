package ssv.com.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import ssv.com.entity.Profile;
import ssv.com.entity.Team;

@Mapper
public interface ProfileMapper {
	public Long saveProfile(Profile profile);

	public Optional<Profile> checkPhoneExist(String number);

	public Optional<Profile> findByEmail(String email);

	public List<Profile> getMembers();

	public void updateMembersInTeam(Team team);

	public void updateProfile(int id, Profile profile);

	public Optional<Profile> findProfileById(int id);
}
