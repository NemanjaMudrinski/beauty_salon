package beautySalon.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import beautySalon.models.Client;
import beautySalon.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
    PersonalDataService personalService;
    
	@Autowired
    LoginService loginService;
    
    @Autowired
    AccountDataService accountService;
    
    @Autowired
	PasswordEncoder passwordEncoder;
    
    public Iterable<Client> getClients() {
    	return clientRepository.findAll();
    }
    
    public Optional<Client> getClientById(Long id) {
    	return clientRepository.findById(id);
    }
    
    
    public Optional<Client> getClientByUsername(String username) {
    	return clientRepository.getByUsername(username);
    }
    
    public Optional<Client> getConfirmedAppoitment(Long id){
    	return clientRepository.getConfirmedAppoitmant(id);
    }
    
    public void addClient(Client client) {
    	loginService.addPermsion(client.getAccountData(), "ROLE_CLIENT");
    	client.getAccountData().setPassword(passwordEncoder.encode(client.getAccountData().getPassword()));
    	clientRepository.save(client);
    }
    
    public void removeClient(Long id) {
    	Optional<Client> client = clientRepository.findById(id);
    	if(client.isPresent()) {
    		clientRepository.delete(client.get());
    	}
    }
    
    
    public void updateClient(Long id, Client client) {
    	Optional<Client> c = clientRepository.findById(id);
    	if(c.isPresent()) {
    		client.setId(c.get().getId());
    		client.getAccountData().setPassword(passwordEncoder.encode(client.getAccountData().getPassword()));
            accountService.updateAccountData(client.getAccountData().getId(), client.getAccountData());  
            personalService.updatePersonalData(client.getPersonalData().getId(), client.getPersonalData());
            clientRepository.save(client);
    	}
    }

}
