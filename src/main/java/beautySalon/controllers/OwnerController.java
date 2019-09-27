package beautySalon.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

import beautySalon.models.Owner;
import beautySalon.services.FileService;
import beautySalon.services.OwnerService;
import beautySalon.utils.View.HideOptionalProperties;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/owner")
public class OwnerController {

	
	@Autowired
	OwnerService ownerService;
	
	@Autowired
	FileService fileService;
	
	@JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/all", method=RequestMethod.GET)
    public ResponseEntity<Iterable<Owner>> getAllOwners() {
        return new ResponseEntity<Iterable<Owner>>(ownerService.getOwners(), HttpStatus.OK);
    }
	
	/* TEST CONTROLLER */
//	@JsonView(HideOptionalProperties.class)
//    @RequestMapping(value="/add", method=RequestMethod.POST)
//    public ResponseEntity<Owner> addOwner(@RequestBody Owner owner) {
//    	ownerService.addOwner(owner);
//        return new ResponseEntity<Owner>(owner, HttpStatus.CREATED);
//    }


    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Owner> getOwnerById(@PathVariable Long id) {
        Optional<Owner> owner= ownerService.getOwnerById(id);
        if(owner.isPresent()) {
            return new ResponseEntity<Owner>(owner.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Owner>(HttpStatus.NOT_FOUND);
    }

    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/username/{username}", method=RequestMethod.GET)
    public ResponseEntity<Owner> getOwnerByUsername(@PathVariable String username) {
        Optional<Owner> owner = ownerService.getOwnerByUsername(username);
        if(owner.isPresent()) {
            return new ResponseEntity<Owner>(owner.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Owner>(HttpStatus.NOT_FOUND);
    }
    
    @JsonView(HideOptionalProperties.class)
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Owner> addOwner(@RequestPart("profileImage") Optional<MultipartFile> file, @RequestPart("data") String owner) throws IOException {
    	Owner o = new ObjectMapper().readValue(owner, Owner.class);
		if(file.isPresent()) {
			fileService.saveProfileImage(file.get(), "owner_" + o.getAccountData().getUsername(), o.getPersonalData());
		}
		ownerService.addOwner(o);
		return new ResponseEntity<Owner>(o, HttpStatus.CREATED);
	}

    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Owner> updateOwner(@PathVariable Long id, @RequestPart("profileImage") Optional<MultipartFile> file, @RequestPart("data") String owner) throws IOException {
    	Owner o = new ObjectMapper().readValue(owner, Owner.class);
		if(file.isPresent()) {
			fileService.saveProfileImage(file.get(), "owner_" + o.getAccountData().getUsername(), o.getPersonalData());
		}
    	ownerService.updateOwner(id, o);
        return new ResponseEntity<Owner>(o, HttpStatus.OK);
    }
    
   
//    @Secured("ROLE_PERSONAL_TRAINER")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Owner> removeOwner(@PathVariable Long id) {
        try {
        	ownerService.removeOwner(id);
        }catch (Exception e) {
            return new ResponseEntity<Owner>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Owner>(HttpStatus.NO_CONTENT);
    }
    
    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/isLoggedIn/{username}", method=RequestMethod.GET)
    public ResponseEntity<Owner> getLoggedIn(@PathVariable String username) {
        Optional<Owner> owner= ownerService.getLoggedInUser(username);
        if(owner.isPresent()) {
            return new ResponseEntity<Owner>(owner.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Owner>(HttpStatus.NOT_FOUND);
    }
   

    
}
