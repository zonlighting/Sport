package ssv.com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ssv.com.dto.ResponseQuery;
import ssv.com.entity.Profile;
import ssv.com.entity.Team;
import ssv.com.exception.ResourceExistsException;
import ssv.com.form.ProfileForm;
import ssv.com.service.ProfileService;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@PostMapping("/createMember")
	public ResponseQuery<?> createMember(@ModelAttribute ProfileForm profileForm) {
		try {
			return ResponseQuery.success("create success", profileService.saveMember(profileForm));
		} catch (ResourceExistsException e) {
			return ResponseQuery.faild(e.getMessage(), e.getCode());
		} catch (Exception e) {
			return ResponseQuery.faild("Create Failed", 401);
		}
	}

	@GetMapping("/members")
	public ResponseQuery<?> members() {
		try {
			return ResponseQuery.success("Data recived", profileService.getMembers());
		} catch (Exception e) {
			return ResponseQuery.faild("Failed To Revice Data", null);
		}
	}

	@PostMapping("/update/{id}")
	public ResponseQuery<?> updateProfileUser(@PathVariable(value = "id") int id,
			@ModelAttribute ProfileForm profileForm) {
		List<Profile> teams = profileService.getMembers();
		Optional<Profile> currentProfile = profileService.findProfileById(id);
		try {
			for (Profile profile : teams) {
				if (profile.getPhone().equalsIgnoreCase(profileForm.getPhone())
						&& !currentProfile.get().getPhone().equalsIgnoreCase(profileForm.getPhone())) {
					return ResponseQuery.faild("Phone Number has exists", 301);
				}
			}
			return ResponseQuery.success("Upadate Success", profileService.updateProfile(id, profileForm));
		} catch (Exception e) {
			return ResponseQuery.faild("Failed To Update", e);
		}
	}

	@GetMapping("/{profileId}")
	public ResponseQuery<?> getProfileById(@PathVariable Integer profileId) {
		if (profileService.findProfileById(profileId) != null) {
			return ResponseQuery.success("Profile found!", profileService.findProfileById(profileId));
		}
		return ResponseQuery.faild("Profile not found!!!", null);
	}

	// Hiển thị thành viên có số bàn thắng trong tour
	@GetMapping("/getTourGoal")
	public ResponseQuery<?> getTourGoal(@RequestParam int idTeam) {
		if (profileService.getTourGoal(idTeam) != null) {
			return ResponseQuery.success("Connect!", profileService.getTourGoal(idTeam));
		}
		return ResponseQuery.faild("Profile not join!!!", null);
	}

	@PostMapping(value = "updateProfileUser")
	public ResponseQuery<?> updateProfileUser(@ModelAttribute ProfileForm profileForm) {
		return profileService.updateProfileUser(profileForm);
	}

	@GetMapping("/lastFiveMatch/{idPlayer}")
	public ResponseQuery<?> lastFiveMatch(@PathVariable int idPlayer) {
		if (profileService.lastFiveMatch(idPlayer) != null) {
			return ResponseQuery.success("Connect!", profileService.lastFiveMatch(idPlayer));
		}
		return ResponseQuery.faild("Don't have any data", null);

	}
}
