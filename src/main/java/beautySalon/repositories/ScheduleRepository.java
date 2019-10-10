package beautySalon.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import beautySalon.models.Schedule;
import beautySalon.models.ScheduleTime;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	@Query("SELECT sch FROM Schedule sch WHERE sch.day >= ?1")
	ArrayList<Schedule> getComingSchedule(Date day);
	
	@Query("SELECT sch FROM Schedule sch WHERE sch.day < ?1")
	ArrayList<Schedule> getScheduleHistory(Date day);
	
	@Query("SELECT sch FROM Schedule sch WHERE sch.day = ?1")
	ArrayList<Schedule> getScheduleTimeByDay(Date id);
	
	@Query("SELECT e FROM Schedule e WHERE DAY(e.day)=?1 AND MONTH(e.day)=?2 AND YEAR(e.day)=?3")
	ArrayList<Schedule> findEventsInDay(Integer day, Integer month, Integer year);
	
	@Query("SELECT e FROM Schedule e WHERE MONTH(e.day)=?1 AND YEAR(e.day)=?2")
	ArrayList<Schedule> findEventsInMonth(Integer month, Integer year);
	

}

