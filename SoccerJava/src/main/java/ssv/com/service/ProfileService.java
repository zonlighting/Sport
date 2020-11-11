package ssv.com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssv.com.dto.ResponseQuery;
import ssv.com.dto.TeamScheduleDto;

import ssv.com.entity.Account;
import ssv.com.entity.Profile;
import ssv.com.entity.Schedule;
import ssv.com.entity.Team;
import ssv.com.exception.ResourceExistsException;
import ssv.com.file.UploadFile;
import ssv.com.form.ProfileForm;
import ssv.com.repository.AccountRepository;
import ssv.com.repository.ProfileRepository;
import ssv.com.repository.TeamRepository;
import ssv.com.repository.TournamentRepository;

@Service
public class ProfileService {
	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<Profile> getMembers() {
		return profileRepository.getMembers();

	}

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
//			String pass = new RandomPass().randomAlphaNumeric(8);
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

	public Optional<Profile> updateProfile(int id, ProfileForm profileForm) throws Exception, ResourceExistsException {
		Optional<Profile> oldProfile = profileRepository.getByEmail(profileForm.getEmail());
		Profile profileUpdate = modelMapper.map(profileForm, Profile.class);
		if (profileForm.getFile() != null && !profileForm.getFile().getOriginalFilename().isEmpty()) {
			profileUpdate.setAvatar(UploadFile.saveFile(profileForm.getFile()));
		} else {
			profileUpdate.setAvatar(oldProfile.get().getAvatar());
		}
		profileUpdate.setId(Long.valueOf(id));
		profileUpdate.setName(profileForm.getName());
		profileUpdate.setEmail(oldProfile.get().getEmail());
		profileUpdate.setPhone(profileForm.getPhone());
		profileUpdate.setAge(profileForm.getAge());
		profileUpdate.setGender(profileForm.getGender());
		profileUpdate.setCountry(profileForm.getCountry());
		profileUpdate.setPosition(profileForm.getPosition());
		try {
			profileRepository.updateProfile(profileUpdate);
		} catch (Exception e) {
			System.out.print(e);
		}
		return oldProfile;
	}

	public Optional<Profile> findProfileById(Integer id) {
		return profileRepository.findProfileById(id);
	}

	public HashSet<Profile> getTourGoal(int idTeam) {
		List<Profile> list = new ArrayList<Profile>();
		HashSet<Profile> profiles = new HashSet<Profile>();
		list = profileRepository.getByTeamTour(idTeam);
		for (Profile profile : list) {
			profile.setNumberGoal(profileRepository.getNumberGoal(idTeam, profile.getId()));
			profiles.add(profile);
		}

		return profiles;
	}

	public ResponseQuery<?> updateProfileUser(ProfileForm profileForm) {
		List<Profile> list = getAll();
		if (list == null || list.isEmpty()) {
			ResponseQuery.faild("Update faild", 400);
		}
		for (Profile profile : list) {
			if (profile.getEmail() == profileForm.getEmail()) {
				if (profileForm.getName() != null) {
					profile.setName(profileForm.getName());
				}
				if (profileForm.getFile() != null) {
					try {
						profile.setAvatar(UploadFile.saveFile(profileForm.getFile()));
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (profileForm.getPhone() != null) {
					profile.setName(profileForm.getPhone());
				}
				profileRepository.updateProfileUser(profile);
				return ResponseQuery.success("Update sussec", profile);
			}
		}
		return ResponseQuery.success("Worong data", null);
	}

	private List<Profile> getAll() {
		return profileRepository.getAll();
	}

	static String[] monthName = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };

	public List<TeamScheduleDto> lastFiveMatch(int idPlayer) {
		Profile profile = profileRepository.lastFiveMatch(idPlayer);
		List<TeamScheduleDto> matchs = new ArrayList<TeamScheduleDto>();
		for (Team team : profile.getTeams()) {
			for (Schedule schedule : team.getSchedule()) {
				TeamScheduleDto match = new TeamScheduleDto();
				Team team1 = teamRepository.getTeamById(schedule.getIdTeam1());
				Team team2 = teamRepository.getTeamById(schedule.getIdTeam2());
				if (team1.getIdTeam() == team.getIdTeam()) {
					match.setNameTeam1(team1.getNameTeam());
					match.setLogoTeam1(team1.getLogo());
					match.setScore1(schedule.getScore1());
					match.setScore2(schedule.getScore2());
					match.setNameTeam2(team2.getNameTeam());
					match.setLogoTeam2(team2.getLogo());
				} else {
					match.setScore1(schedule.getScore2());
					match.setScore2(schedule.getScore1());
					match.setNameTeam1(team2.getNameTeam());
					match.setLogoTeam1(team2.getLogo());
					match.setNameTeam2(team1.getNameTeam());
					match.setLogoTeam2(team1.getLogo());
				}
				match.setMonthStart(monthName[schedule.getTimeStart().getMonthValue() - 1] + " , "
						+ schedule.getTimeStart().getYear());
				match.setDayStart(monthName[schedule.getTimeStart().getMonthValue()] + " , "
						+ schedule.getTimeStart().getDayOfMonth());

				if (schedule.getTimeStart().getMinute() > 9) {
					match.setTimeStart(schedule.getTimeStart().getHour() + ":" + schedule.getTimeStart().getMinute());
				} else {
					match.setTimeStart(schedule.getTimeStart().getHour() + ":0" + schedule.getTimeStart().getMinute());
				}
				match.setNameTour(tournamentRepository.getById(schedule.getIdTour()).getNameTournament());
				if (schedule.getAdraw() != 0) {
					match.setStatus(2);
				} else if (schedule.getWinner() == team.getIdTeam()) {
					match.setStatus(0);
				} else {
					match.setStatus(1);
				}

				if (schedule.getTimeEnd() != null) {
					if (schedule.getTimeEnd().getMinute() > 9) {
						match.setTimeEnd(schedule.getTimeEnd().getHour() + ":" + schedule.getTimeEnd().getMinute());
					} else {
						match.setTimeEnd(schedule.getTimeEnd().getHour() + ":0" + schedule.getTimeEnd().getMinute());
					}
				}
				matchs.add(match);
			}
		}
		return matchs;
	}
}
