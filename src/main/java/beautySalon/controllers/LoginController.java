package beautySalon.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import beautySalon.models.AccountData;
import beautySalon.services.LoginService;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@RequestMapping(path = "/", method = RequestMethod.POST)
	public ResponseEntity<HashMap<String, String>> login(@RequestBody AccountData accountData) {		
		return loginService.login(accountData);
	}
}
