package beautySalon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import beautySalon.models.ScheduleTime;

@Repository
public interface ScheduleTimeRepository extends JpaRepository<ScheduleTime, Long> {

}
