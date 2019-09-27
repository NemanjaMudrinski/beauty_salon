package beautySalon.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import org.apache.tika.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

import beautySalon.models.Image;
import beautySalon.services.FileService;
import beautySalon.services.ImageService;
import beautySalon.utils.View.HideOptionalProperties;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/image")
public class ImageController {
	
	@Autowired
	ImageService imageService;
	
	@Autowired
	FileService fileService;

	@RequestMapping(value = "/images/{type}/{file}", method = RequestMethod.GET, produces = MediaType.ALL_VALUE)
    public ResponseEntity<byte[]> getFileFromImages(@PathVariable String type, @PathVariable String file) throws IOException {
    	FileInputStream fis = new FileInputStream("src/main/resources/images/" + type +"/"+ file);
    	byte[] bytes = IOUtils.toByteArray(fis);
		fis.close();
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(bytes);
    }
    
    @JsonView(HideOptionalProperties.class)
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Image> addImage(@RequestPart("image") Optional<MultipartFile> file, @RequestPart("data") String image) throws IOException {
    	Image i = new ObjectMapper().readValue(image, Image.class);
		if(file.isPresent()) {
			fileService.saveImages(file.get(), "image_" + i.getOwner().getAccountData().getUsername(), i);
		}
		imageService.addImage(i);
		return new ResponseEntity<Image>(i, HttpStatus.CREATED);
	}
}
