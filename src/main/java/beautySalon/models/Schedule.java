package beautySalon.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;

import beautySalon.utils.View.ShowReservation;

@Entity
public class Schedule {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date day;
	
	@ManyToOne(cascade= {CascadeType.MERGE})
	private ScheduleTime time;
	
	@ManyToOne(cascade= {CascadeType.REFRESH, CascadeType.MERGE})
	private Owner owner;
	
	@JsonView(ShowReservation.class)
	@OneToMany(mappedBy="schedule")
	private Set<Reservation> reservation;
	
	public Schedule() {
		
	}
	
	

//	public ScheduleMakeUp(Date day, ScheduleTime time, Owner owner) {
//	super();
//	this.day = day;
//	this.time = time;
//	this.owner = owner;
//}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}



//	public ScheduleTime getTime() {
//		return time;
//	}
//
//
//
//	public void setTime(ScheduleTime time) {
//		this.time = time;
//	}



	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Set<Reservation> getReservation() {
		return reservation;
	}

	public void setReservation(Set<Reservation> reservation) {
		this.reservation = reservation;
	}
	
	
}
