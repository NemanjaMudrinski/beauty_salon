package beautySalon.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import beautySalon.models.Administrator;
import beautySalon.models.Company;
import beautySalon.services.CompanyService;
import beautySalon.utils.View.HideOptionalProperties;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	CompanyService companyService;
	
	@JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/all", method=RequestMethod.GET)
    public ResponseEntity<Iterable<Company>> getAllCompany() {
        return new ResponseEntity<Iterable<Company>>(companyService.getCompany(), HttpStatus.OK);
    }
	
    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Optional<Company> company = companyService.getCompanyById(id);
        if(company.isPresent()) {
            return new ResponseEntity<Company>(company.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Company>(HttpStatus.NOT_FOUND);
    }
	
	@JsonView(HideOptionalProperties.class)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Company> addCompany(@RequestBody Company company){
		companyService.addCompany(company);
		return new ResponseEntity<Company>(company, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Company> editCompany(@PathVariable Long id, @RequestBody Company company){
		companyService.updateCompany(id, company);
		return new ResponseEntity<Company>(company, HttpStatus.OK);
	}

    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Company> removeCompany(@PathVariable Long id) {
        try {
        	companyService.removeCompany(id);
        }catch (Exception e) {
            return new ResponseEntity<Company>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Company>(HttpStatus.NO_CONTENT);
    }

}
