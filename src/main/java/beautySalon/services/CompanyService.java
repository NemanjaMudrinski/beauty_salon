package beautySalon.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beautySalon.models.Company;
import beautySalon.repositories.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepository;
	
	public Iterable<Company> getCompany() {
    	return companyRepository.findAll();
    }
    
    public Optional<Company> getCompanyById(Long id) {
    	return companyRepository.findById(id);
    }
      
    public void addCompany(Company company) {
    	companyRepository.save(company);
    }
    
    public void removeCompany(Long id) {
    	Optional<Company> company = companyRepository.findById(id);
    	if(company.isPresent()) {
    		companyRepository.delete(company.get());
    	}
    }
    
    
    public void updateCompany(Long id, Company company) {
    	Optional<Company> c = companyRepository.findById(id);
    	if(c.isPresent()) {
    		company.setId(c.get().getId());
    		companyRepository.save(company);
        }
    }
}
