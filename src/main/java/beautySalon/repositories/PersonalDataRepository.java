package beautySalon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import beautySalon.models.PersonalData;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

	@Query("SELECT p FROM PersonalData p WHERE p.profileImagePath LIKE ?1")
	Optional<PersonalData> getByUsername(String username);
}
