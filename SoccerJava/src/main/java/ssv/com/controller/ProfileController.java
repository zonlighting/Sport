package ssv.com.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssv.com.dto.ResponseQuery;
import ssv.com.form.ProfileForm;
import ssv.com.service.ProfileService;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@PostMapping("/createMember")
	public ResponseQuery<?> createMember(@ModelAttribute ProfileForm profileForm) throws Exception{
		try {
			profileService.saveMember(profileForm);
			return ResponseQuery.success("create success", 200);
		}catch (FileNotFoundException e) {
			return ResponseQuery.faild("invalid Image", 302);
		}
	}
}
