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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import beautySalon.models.AccountData;
import beautySalon.models.Email;
import beautySalon.services.AccountDataService;
import beautySalon.utils.View.HideOptionalProperties;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/account-data")
public class AccountDataController {

	@Autowired
	AccountDataService accountDataService;
	
	@JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/all", method=RequestMethod.GET)
    public ResponseEntity<Iterable<AccountData>> getAccountData() {
        return new ResponseEntity<Iterable<AccountData>>(accountDataService.getAllAccoutDatas(), HttpStatus.OK);
    }
	
	@RequestMapping(value="/sendMail", method=RequestMethod.POST)
	public ResponseEntity<Email> sendEmail(@RequestBody Email email) {
		accountDataService.sendMail(email);
		return new ResponseEntity<Email>(email, HttpStatus.CREATED);
	}

    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<AccountData> getAccountDataById(@PathVariable Long id) {
        Optional<AccountData> accountData = accountDataService.getAccountDataById(id);
        if(accountData.isPresent()) {
            return new ResponseEntity<AccountData>(accountData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<AccountData>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public ResponseEntity<AccountData> addAccountData(@RequestBody AccountData AccountData) {
        accountDataService.addAccountData(AccountData);
        return new ResponseEntity<AccountData>(AccountData, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<AccountData> updateAccountData(@PathVariable Long id, @RequestBody AccountData AccountData) {
        accountDataService.updateAccountData(id, AccountData);
        return new ResponseEntity<AccountData>(AccountData, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<AccountData> removeAccountData(@PathVariable Long id) {
        try {
            accountDataService.removeAccountData(id);
        }catch (Exception e) {
            return new ResponseEntity<AccountData>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<AccountData>(HttpStatus.NO_CONTENT);
    }

}
