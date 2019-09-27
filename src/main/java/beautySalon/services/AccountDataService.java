package beautySalon.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import beautySalon.models.AccountData;
import beautySalon.models.Email;
import beautySalon.repositories.AccountDataRepository;

@Service
public class AccountDataService {

	@Autowired
	AccountDataRepository accountDataRepository;
	
	 @Autowired
	 JavaMailSender javaMailSender; 
	 
	 @Autowired
	 Email email;

	
	public Iterable<AccountData> getAllAccoutDatas() {
		return accountDataRepository.findAll();
	}
	
	public Optional<AccountData> getAccountDataById(Long id) {
		return accountDataRepository.findById(id);
	}
	
	public Optional<AccountData> getAccountDataUsername(String username) {
        return accountDataRepository.findByUsername(username);
    }
	
    public void addAccountData(AccountData accountData) {
        accountDataRepository.save(accountData);
    }
    
    public void removeAccountData(Long id) {
        Optional<AccountData> accountData = accountDataRepository.findById(id);
        accountDataRepository.delete(accountData.get());
    }

    public void updateAccountData(Long id, AccountData accountData) {
        Optional<AccountData> Acc = accountDataRepository.findById(id);
        if(Acc.isPresent()) {
            accountData.setId(Acc.get().getId());
            accountDataRepository.save(accountData);
        }
    }
    
    public void sendMail(Email email) {
    	SimpleMailMessage message = new SimpleMailMessage();
    		message.setTo(email.getTo());
	    	message.setSubject(email.getSubject());
	    	message.setText(email.getText());
	    javaMailSender.send(message);
    
    }

}
