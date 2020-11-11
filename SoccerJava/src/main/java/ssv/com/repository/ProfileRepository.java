package ssv.com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ssv.com.entity.Profile;
import ssv.com.entity.Team;
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

	public void updateMembersInTeam(Team team) {
		profileMapper.updateMembersInTeam(team);
	}

	public void updateProfile(Profile profile) {
		profileMapper.updateProfile(profile);
	}

	public Optional<Profile> findProfileById(int id) {
		return profileMapper.findProfileById(id);
	}

	public Optional<Profile> findProfileById(Integer id) {
		return profileMapper.findProfileById(id);
	}

	public List<Profile> getByTeamTour(int idTeam) {
		return profileMapper.getByTeamTour(idTeam);
	}

	public int getNumberGoal(int idTeam, Long idMember) {
		// TODO Auto-generated method stub
		return profileMapper.getNumberGoal(idTeam,idMember);
	}

	public List<Profile> getAll() {
		return profileMapper.getAll();

	}

	public void updateProfileUser(Profile profile) {
		 profileMapper.updateProfileUser(profile);
  }
	public Profile lastFiveMatch(int idPlayer) {
		return profileMapper.lastFiveMatch(idPlayer);

	}
}
