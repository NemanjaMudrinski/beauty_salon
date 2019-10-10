package beautySalon.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beautySalon.models.Image;
import beautySalon.repositories.ImageRepository;

@Service
public class ImageService {

	@Autowired
	ImageRepository imageRepository;
	
	public Iterable<Image> getAllImages() {
		return imageRepository.findAll();
	}
	
	public Optional<Image> getImageById(Long id) {
		return imageRepository.findById(id);
	}
	
	public void addImage(Image image) {
		imageRepository.save(image);
	}
}
