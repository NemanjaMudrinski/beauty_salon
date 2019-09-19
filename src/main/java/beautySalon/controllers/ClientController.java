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

import beautySalon.models.Client;
import beautySalon.services.ClientService;
import beautySalon.services.FileService;
import beautySalon.utils.View.HideOptionalProperties;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	ClientService clientService;
	
	@Autowired
	FileService fileService;
	
	@JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/all", method=RequestMethod.GET)
    public ResponseEntity<Iterable<Client>> getAllClients() {
        return new ResponseEntity<Iterable<Client>>(clientService.getClients(), HttpStatus.OK);
    }
	
	/* TEST CONTROLLER */
//	@JsonView(HideOptionalProperties.class)
//    @RequestMapping(value="/add", method=RequestMethod.POST)
//    public ResponseEntity<Client> addClient(@RequestBody Client client) {
//    	clientService.addClient(client);
//        return new ResponseEntity<Client>(client, HttpStatus.CREATED);
//    }


    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientService.getClientById(id);
        if(client.isPresent()) {
            return new ResponseEntity<Client>(client.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
    }

    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/c/{id}", method=RequestMethod.GET)
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        Optional<Client> client = clientService.getConfirmedAppoitment(id);
        if(client.isPresent()) {
            return new ResponseEntity<Client>(client.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
    }
    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/username/{username}", method=RequestMethod.GET)
    public ResponseEntity<Client> getClientByUsername(@PathVariable String username) {
        Optional<Client> client= clientService.getClientByUsername(username);
        if(client.isPresent()) {
            return new ResponseEntity<Client>(client.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
    }
    
    @JsonView(HideOptionalProperties.class)
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Client> addClient(@RequestPart("profileImage") Optional<MultipartFile> file, @RequestPart("data") String client) throws IOException {
    	Client c = new ObjectMapper().readValue(client, Client.class);
		if(file.isPresent()) {
			fileService.saveProfileImage(file.get(), "client_" + c.getAccountData().getUsername(), c.getPersonalData());
		}
		clientService.addClient(c);
		return new ResponseEntity<Client>(c, HttpStatus.CREATED);
	}

    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestPart("profileImage") Optional<MultipartFile> file, @RequestPart("data") String client) throws IOException {
    	Client c = new ObjectMapper().readValue(client, Client.class);
		if(file.isPresent()) {
			fileService.saveProfileImage(file.get(), "client_" + c.getAccountData().getUsername(), c.getPersonalData());
		}
    	clientService.updateClient(id, c);
        return new ResponseEntity<Client>(c, HttpStatus.OK);
    }
    
   
//    @Secured("ROLE_PERSONAL_TRAINER")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Client> removeClient(@PathVariable Long id) {
        try {
        	clientService.removeClient(id);
        }catch (Exception e) {
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
    }

    

}
