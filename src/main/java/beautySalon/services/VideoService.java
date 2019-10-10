package beautySalon.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beautySalon.models.Video;
import beautySalon.repositories.VideoRepository;

@Service
public class VideoService {

	@Autowired
	VideoRepository videoRepository;
	
	public Iterable<Video> getVideos(){
		return videoRepository.findAll();
	}
	
	public Optional<Video> getVideoById(Long id) {
		return videoRepository.findById(id);
	}
	
	public Video addVideo(Video video) {
		 return videoRepository.save(video);
	}
	
	public void deleteVideo(Long id) {
		Optional<Video> video = videoRepository.findById(id);
		if(video.isPresent()) {
			videoRepository.delete(video.get());
		}
	}
	
	public void updateVideo(Long id, Video video) {
		Optional<Video> v = videoRepository.findById(id);
		if(v.isPresent()) {
			video.setId(v.get().getId());
			videoRepository.save(video);
		}
	}
}
