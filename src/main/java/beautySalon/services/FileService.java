package beautySalon.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import beautySalon.models.PersonalData;

@Service
public class FileService {

	private String defaultProfileImagePath = "src/main/resources/images/profile_images/default.png";
	
	
	public void saveProfileImage(MultipartFile file, String fileName, PersonalData personalData) throws IOException {
	    Tika tika = new Tika();
	    String mimeType = tika.detect(file.getBytes());
		if(file != null && (mimeType.equals("image/png") || mimeType.equals("image/jpeg") || mimeType.equals("image/mp4"))) {
			File convertFile = new File("src/main/resources/images/profile_images/" + fileName + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
			convertFile.createNewFile();
			FileOutputStream fout = new FileOutputStream(convertFile);
			fout.write(file.getBytes());
			fout.close();
			personalData.setProfileImagePath("images/profile_images/" + fileName + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
		}
		else {
			InputStream initialStream = new FileInputStream(new File(defaultProfileImagePath));
		    byte[] buffer = new byte[initialStream.available()];
		    initialStream.read(buffer);
		    File targetFile = new File("src/main/resources/images/profile_images/" + fileName + defaultProfileImagePath.substring(defaultProfileImagePath.lastIndexOf(".")));
		    targetFile.createNewFile();
		    OutputStream outStream = new FileOutputStream(targetFile);
		    outStream.write(buffer);
		    initialStream.close();
		    outStream.close();
		    personalData.setProfileImagePath("images/profile_images/" + fileName + defaultProfileImagePath.substring(defaultProfileImagePath.lastIndexOf(".")));
		}
	}

}
