package beautySalon.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

import beautySalon.utils.View.ShowCompany;
import beautySalon.utils.View.ShowSchedule;

@Entity
public class Owner {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private AccountData accountData;

	@ManyToOne(cascade=CascadeType.ALL)
	private PersonalData personalData;
	
	@JsonView(ShowCompany.class)
	@OneToMany(mappedBy = "owner")
	private Set<Company> company;
	
	@JsonView(ShowSchedule.class)
	@OneToMany(mappedBy="owner")
	private Set<Schedule> schedule;

	public Owner() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AccountData getAccountData() {
		return accountData;
	}

	public void setAccountData(AccountData accountData) {
		this.accountData = accountData;
	}

	public PersonalData getPersonalData() {
		return personalData;
	}

	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}

	public Set<Company> getCompany() {
		return company;
	}

	public void setCompany(Set<Company> company) {
		this.company = company;
	}

	public Set<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(Set<Schedule> schedule) {
		this.schedule = schedule;
	}
	
	

		
}
