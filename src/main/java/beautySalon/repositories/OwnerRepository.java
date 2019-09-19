package beautySalon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import beautySalon.models.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

	@Query("SELECT o FROM Owner o WHERE o.accountData.username = ?1")
	Optional<Owner> getByUsername(String username);
}
