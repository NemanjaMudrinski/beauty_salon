package beautySalon.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beautySalon.models.ScheduleTime;
import beautySalon.repositories.ScheduleTimeRepository;

@Service
public class ScheduleTimeService {

	@Autowired
	ScheduleTimeRepository scheduleTimeRepository;
	
	public Iterable<ScheduleTime> getSchedulesTime() {
		return scheduleTimeRepository.findAll();
	}
	
	public Optional<ScheduleTime> getScheduleTimeById(Long id) {
		return scheduleTimeRepository.findById(id);
	}
	
	public void addScheduleTime(ScheduleTime schedule) {
		scheduleTimeRepository.save(schedule);
	}
	
	public void removeScheduleTime(Long id) {
		Optional<ScheduleTime> schedule = scheduleTimeRepository.findById(id);
		if(schedule.isPresent()) {
			scheduleTimeRepository.delete(schedule.get());
		}
	}
	
	public void editScheduleTime(Long id, ScheduleTime  schedule) {
		Optional<ScheduleTime> sch = scheduleTimeRepository.findById(id);
		if(sch.isPresent()) {
			schedule.setId(sch.get().getId());
			scheduleTimeRepository.save(schedule);
		}
	}
}
