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

import beautySalon.models.Employee;
import beautySalon.services.EmployeeService;
import beautySalon.services.FileService;
import beautySalon.utils.View.HideOptionalProperties;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	FileService fileService;
	
	@JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/all", method=RequestMethod.GET)
    public ResponseEntity<Iterable<Employee>> getAllEmployees() {
        return new ResponseEntity<Iterable<Employee>>(employeeService.getEmployees(), HttpStatus.OK);
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
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if(employee.isPresent()) {
            return new ResponseEntity<Employee>(employee.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
    }

    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/username/{username}", method=RequestMethod.GET)
    public ResponseEntity<Employee> getEmployeeByUsername(@PathVariable String username) {
        Optional<Employee> employee= employeeService.getEmployeeByUsername(username);
        if(employee.isPresent()) {
            return new ResponseEntity<Employee>(employee.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
    }
    
    @JsonView(HideOptionalProperties.class)
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Employee> addEmployee(@RequestPart("profileImage") Optional<MultipartFile> file, @RequestPart("data") String employee) throws IOException {
    	Employee e = new ObjectMapper().readValue(employee, Employee.class);
		if(file.isPresent()) {
			fileService.saveProfileImage(file.get(), "employee_" + e.getAccountData().getUsername(), e.getPersonalData());
		}
		employeeService.addEmployee(e);
		return new ResponseEntity<Employee>(e, HttpStatus.CREATED);
	}

    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestPart("profileImage") Optional<MultipartFile> file, @RequestPart("data") String employee) throws IOException {
    	Employee e = new ObjectMapper().readValue(employee, Employee.class);
		if(file.isPresent()) {
			fileService.saveProfileImage(file.get(), "employeet_" + e.getAccountData().getUsername(), e.getPersonalData());
		}
		employeeService.updateEmployee(id, e);
        return new ResponseEntity<Employee>(e, HttpStatus.OK);
    }
    
   
//    @Secured("ROLE_PERSONAL_TRAINER")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Employee> removeEmployee(@PathVariable Long id) {
        try {
        	employeeService.removeEmployee(id);
        }catch (Exception e) {
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }

    

}

