package beautySalon.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import beautySalon.models.Client;
import beautySalon.models.Employee;
import beautySalon.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
    PersonalDataService personalService;
    
	@Autowired
    LoginService loginService;
    
    @Autowired
    AccountDataService accountService;
    
    @Autowired
	PasswordEncoder passwordEncoder;
    
    public Iterable<Employee> getEmployees() {
    	return employeeRepository.findAll();
    }
    
    public Optional<Employee> getEmployeeById(Long id) {
    	return employeeRepository.findById(id);
    }
    
    
    public Optional<Employee> getEmployeeByUsername(String username) {
    	return employeeRepository.getByUsername(username);
    }
    
    public void addEmployee(Employee employee) {
    	loginService.addPermsion(employee.getAccountData(), "ROLE_EMPLOYEE");
    	employee.getAccountData().setPassword(passwordEncoder.encode(employee.getAccountData().getPassword()));
    	employeeRepository.save(employee);
    }
    
    public void removeEmployee(Long id) {
    	Optional<Employee> employee = employeeRepository.findById(id);
    	if(employee.isPresent()) {
    		employeeRepository.delete(employee.get());
    	}
    }
    
    
    public void updateEmployee(Long id, Employee employee) {
    	Optional<Employee> e = employeeRepository.findById(id);
    	if(e.isPresent()) {
    		employee.setId(e.get().getId());
    		employee.getAccountData().setPassword(passwordEncoder.encode(employee.getAccountData().getPassword()));
            accountService.updateAccountData(employee.getAccountData().getId(), employee.getAccountData());  
            personalService.updatePersonalData(employee.getPersonalData().getId(), employee.getPersonalData());
            employeeRepository.save(employee);
    	}
    }

}
