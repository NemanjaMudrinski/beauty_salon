package beautySalon.models;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Boolean confirmed = false;
	
	private Date reservationMade = Calendar.getInstance().getTime();
	
	@ManyToOne()
	private Schedule schedule;
	
	@ManyToOne()
	private Client client;
	
	public Reservation() {
		
	}
	
	
	public Reservation(Boolean confirmed, Date reservationMade, Schedule schedule, Client client) {
		super();
		this.confirmed = confirmed;
		this.reservationMade = reservationMade;
		this.schedule = schedule;
		this.client = client;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getReservationMade() {
		return reservationMade;
	}

	public void setReservationMade(Date reservationMade) {
		this.reservationMade = reservationMade;
	}
	
	
}
