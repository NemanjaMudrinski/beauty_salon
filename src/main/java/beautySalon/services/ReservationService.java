package beautySalon.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beautySalon.models.Reservation;
import beautySalon.repositories.ReservationRepository;

@Service
public class ReservationService {

	@Autowired
	ReservationRepository reservationRepository;
	
	public Iterable<Reservation> getAllReservations(){
		return reservationRepository.findAll();
	}
	
	public Optional<Reservation> getReservationById(Long id) {
		return reservationRepository.findById(id);
	}
	
	public void addReservation(Reservation reservation) {
		reservationRepository.save(reservation);
	}
	
	public void removeReservation(Long id) {
		Optional<Reservation> r = reservationRepository.findById(id);
		reservationRepository.delete(r.get());
	}
	
	public void editReservation(Long id, Reservation reservation) {
		Optional<Reservation> r = reservationRepository.findById(id);
		if(r.isPresent()) {
			reservation.setId(r.get().getId());
			reservationRepository.save(reservation);
		}
	}
	
	public void confirmReservation(Long id) {
		Optional<Reservation> reservation = reservationRepository.findById(id);
		Reservation r = reservation.get();
		r.setConfirmed(true);
		reservationRepository.save(r);
	}
	
	public Iterable<Optional<Reservation>> getConfirmed() {
		return reservationRepository.getConfirmedReservation();
	}
	
	public Iterable<Optional<Reservation>> getNotConfirmed(Long id) {
		return reservationRepository.getNotConfirmedReservation(id);
	}
	
	public ArrayList<Reservation> getClientConfirmedReservations(String username){
		Date day = new Date();
		return reservationRepository.getClintConfirmedReservations(username, day);
	}
	
	public ArrayList<Reservation> getAllReservationsByUser(String username){
		Date day = new Date();
		return reservationRepository.getAllReservationsByUsername(username, day);
	}
	
	public ArrayList<Reservation> getFutureReservations() {
		Date day = new Date();
		return reservationRepository.getFutureReservation(day);
	}
}
