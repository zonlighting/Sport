package ssv.com.file;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class UploadFile {
	public static String saveFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
		String destination = System.getProperty("user.dir") + "/src/main/webapp/images/"
				+ multipartFile.getOriginalFilename();
		File file = new File(destination);
		multipartFile.transferTo(file);
		return "/images/" + multipartFile.getOriginalFilename();
	}

	public static String saveVideo(MultipartFile multipartFile) throws IllegalStateException, IOException {
		String destination = System.getProperty("user.dir") + "/src/main/webapp/videos/"
				+ multipartFile.getOriginalFilename();
		File file = new File(destination);
		multipartFile.transferTo(file);
		return "/videos/" + multipartFile.getOriginalFilename();
	}

}
