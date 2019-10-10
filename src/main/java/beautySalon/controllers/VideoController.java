package beautySalon.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import beautySalon.models.Video;
import beautySalon.services.VideoService;
import beautySalon.utils.View.HideOptionalProperties;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/video")
public class VideoController {

	@Autowired
	VideoService videoService;
	
	@JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/all", method=RequestMethod.GET)
    public ResponseEntity<Iterable<Video>> getVideo() {
        return new ResponseEntity<Iterable<Video>>(videoService.getVideos(), HttpStatus.OK);
    }

    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Video> getVideoById(@PathVariable Long id) {
        Optional<Video> video = videoService.getVideoById(id);
        if(video.isPresent()) {
            return new ResponseEntity<Video>(video.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Video>(HttpStatus.NOT_FOUND);
    }
     
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public ResponseEntity<Video> addVideo(@RequestBody Video video) {
    	videoService.addVideo(video);
        return new ResponseEntity<Video>(video, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Video> updateVideo(@PathVariable Long id, @RequestBody Video video) {
    	videoService.updateVideo(id, video);
        return new ResponseEntity<Video>(video, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Video> removeVideo(@PathVariable Long id) {
        try {
        	videoService.deleteVideo(id);
        }catch (Exception e) {
            return new ResponseEntity<Video>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Video>(HttpStatus.NO_CONTENT);
    }

}
