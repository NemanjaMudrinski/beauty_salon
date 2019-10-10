package beautySalon.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import beautySalon.models.Owner;
import beautySalon.repositories.OwnerRepository;

@Service
public class OwnerService {

	@Autowired
	OwnerRepository ownerRepository;
	
	@Autowired
    PersonalDataService personalService;
    
	@Autowired
    LoginService loginService;
    
    @Autowired
    AccountDataService accountService;
    
    @Autowired
	PasswordEncoder passwordEncoder;
    
    public Iterable<Owner> getOwners() {
    	return ownerRepository.findAll();
    }
    
    public Optional<Owner> getOwnerById(Long id) {
    	return ownerRepository.findById(id);
    }
    
    public Owner getOwnerById1(Long id) {
    	return ownerRepository.findById(id).orElse(null);
    }
    
    public Optional<Owner> getOwnerByUsername(String username) {
    	return ownerRepository.getByUsername(username);
    }
    
    public void addOwner(Owner owner) {
    	loginService.addPermsion(owner.getAccountData(), "ROLE_OWNER");
    	owner.getAccountData().setPassword(passwordEncoder.encode(owner.getAccountData().getPassword()));
    	ownerRepository.save(owner);
    }
    
    public void removeOwner(Long id) {
    	Optional<Owner> owner = ownerRepository.findById(id);
    	if(owner.isPresent()) {
    		ownerRepository.delete(owner.get());
    	}
    }
    
    
    public void updateOwner(Long id, Owner owner) {
    	Optional<Owner> o = ownerRepository.findById(id);
    	if(o.isPresent()) {
    		owner.setId(o.get().getId());
    		owner.getAccountData().setPassword(passwordEncoder.encode(owner.getAccountData().getPassword()));
            accountService.updateAccountData(owner.getAccountData().getId(), owner.getAccountData());  
            personalService.updatePersonalData(owner.getPersonalData().getId(), owner.getPersonalData());
            ownerRepository.save(owner);
    	}
    }
    
    public Optional<Owner> getLoggedInUser(String username) {
    	return ownerRepository.getLoggedUser(username);
    }
    

}
