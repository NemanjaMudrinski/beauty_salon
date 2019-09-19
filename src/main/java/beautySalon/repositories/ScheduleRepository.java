package beautySalon.repositories;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import beautySalon.models.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	@Query("SELECT sch FROM Schedule sch WHERE sch.day >= ?1")
	ArrayList<Schedule> getComingSchedule(Date day);
	
	@Query("SELECT sch FROM Schedule sch WHERE sch.day < ?1")
	ArrayList<Schedule> getScheduleHistory(Date day);
	
	
}
