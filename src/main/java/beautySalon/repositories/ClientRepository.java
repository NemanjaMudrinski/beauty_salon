package beautySalon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import beautySalon.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	@Query("SELECT c FROM Client c WHERE c.accountData.username = ?1")
	Optional<Client> getByUsername(String username);
	
	@Query("SELECT r.client FROM Reservation r WHERE r.confirmed = true")
	Optional<Client> getConfirmedAppoitmant(Long id);
	
	@Query("SELECT c FROM Client c WHERE c.accountData.username = ?1")
	Optional<Client> getLoggedUser(String username);
	
}
