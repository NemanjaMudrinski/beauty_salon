package beautySalon.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import beautySalon.models.Reservation;
import beautySalon.models.Schedule;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query("SELECT r FROM Reservation r WHERE r.confirmed = true")
	Iterable<Optional<Reservation>> getConfirmedReservation();
	
	@Query("SELECT r FROM Reservation r WHERE r.confirmed = false")
	Iterable<Optional<Reservation>> getNotConfirmedReservation(Long id);
	
	@Query("SELECT DISTINCT r FROM Reservation r WHERE r.client.accountData.username = ?1 AND r.confirmed = true AND r.schedule.day >= ?2")
	ArrayList<Reservation> getClintConfirmedReservations(String username, Date today);
	
	@Query("SELECT DISTINCT r FROM Reservation r WHERE r.client.accountData.username = ?1 AND r.schedule.day >= ?2")
	ArrayList<Reservation> getAllReservationsByUsername(String username, Date today);
	
	@Query("SELECT r FROM Reservation r WHERE r.schedule.day >= ?1")
	ArrayList<Reservation> getFutureReservation(Date day);
	
	
}
