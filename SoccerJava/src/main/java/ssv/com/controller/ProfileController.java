package ssv.com.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssv.com.dto.ResponseQuery;
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
			// TODO: handle exception
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
	public ResponseQuery<?> updateProfileUser(@PathVariable(value = "id") int id, @ModelAttribute ProfileForm profileForm){
		try {
			return ResponseQuery.success("Upadate Success", profileService.updateProfile(id, profileForm));
		} catch (Exception e) {
			return ResponseQuery.faild("Failed To Update", e);
		}
	}

}
