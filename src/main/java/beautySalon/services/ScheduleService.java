package beautySalon.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beautySalon.models.Schedule;
import beautySalon.repositories.ScheduleRepository;

@Service
public class ScheduleService {

	@Autowired
	ScheduleRepository scheduleRepository;
	
	public Iterable<Schedule> getSchedules() {
		return scheduleRepository.findAll();
	}
	
	public Optional<Schedule> getScheduleById(Long id) {
		return scheduleRepository.findById(id);
	}
	
	public void addSchedule(Schedule schedule) {
		scheduleRepository.save(schedule);
	}
	
	public void removeSchedule(Long id) {
		Optional<Schedule> schedule = scheduleRepository.findById(id);
		if(schedule.isPresent()) {
			scheduleRepository.delete(schedule.get());
		}
	}
	
	public void editSchedule(Long id, Schedule schedule) {
		Optional<Schedule> sch = scheduleRepository.findById(id);
		if(sch.isPresent()) {
			schedule.setId(sch.get().getId());
			scheduleRepository.save(schedule);
		}
	}
	
	public ArrayList<Schedule> getScheduleByDay() {
		Date day = new Date();
		return scheduleRepository.getComingSchedule(day);
	}
	
	public ArrayList<Schedule> getScheduleHistory() {
		Date day = new Date();
		return scheduleRepository.getScheduleHistory(day);
	}
	
	public ArrayList<Schedule> getScheduleTimeByDay(Date id) {
		return scheduleRepository.getScheduleTimeByDay(id);
	}
	
	public ArrayList<Schedule> getOneDayEvents(Integer day, Integer month, Integer year) {
		return scheduleRepository.findEventsInDay(day, month, year);
	}
	
	public ArrayList<Schedule> getEventCount(Integer month, Integer year) {
		return scheduleRepository.findEventsInMonth(month, year);
	}


}
