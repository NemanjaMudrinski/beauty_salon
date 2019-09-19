package beautySalon.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import beautySalon.models.Client;
import beautySalon.models.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query("SELECT r FROM Reservation r WHERE r.confirmed = true")
	Iterable<Optional<Reservation>> getConfirmedReservation();
	
	@Query("SELECT r FROM Reservation r WHERE r.confirmed = false")
	Iterable<Optional<Reservation>> getNotConfirmedReservation(Long id);
	
	@Query("SELECT DISTINCT r FROM Reservation r WHERE r.client.accountData.username = ?1 AND r.confirmed = true")
	ArrayList<Reservation> getClintConfirmedReservations(String username);
	
}