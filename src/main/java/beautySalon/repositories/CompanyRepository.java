package beautySalon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import beautySalon.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{

}
