package beautySalon.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import beautySalon.models.Schedule;
import beautySalon.models.ScheduleTime;
import beautySalon.services.ScheduleTimeService;
import beautySalon.utils.View.HideOptionalProperties;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/schedule-time")
public class ScheduleTimeController {

	@Autowired
	ScheduleTimeService scheduleTimeService;
	
	@JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/all", method=RequestMethod.GET)
    public ResponseEntity<Iterable<ScheduleTime>> getSchedulesTime() {
        return new ResponseEntity<Iterable<ScheduleTime>>(scheduleTimeService.getSchedulesTime(), HttpStatus.OK);
    }

    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<ScheduleTime> getScheduleTimeById(@PathVariable Long id) {
        Optional<ScheduleTime> schedule = scheduleTimeService.getScheduleTimeById(id);
        if(schedule.isPresent()) {
            return new ResponseEntity<ScheduleTime>(schedule.get(), HttpStatus.OK);
        }
        return new ResponseEntity<ScheduleTime>(HttpStatus.NOT_FOUND);
    }
     
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public ResponseEntity<ScheduleTime> addScheduleTime(@RequestBody ScheduleTime  schedule) {
    	scheduleTimeService.addScheduleTime(schedule);
        return new ResponseEntity<ScheduleTime>(schedule, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<ScheduleTime> updateScheduleTime(@PathVariable Long id, @RequestBody ScheduleTime schedule) {
    	scheduleTimeService.editScheduleTime(id, schedule);
        return new ResponseEntity<ScheduleTime>(schedule, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<ScheduleTime> removeScheduleTime(@PathVariable Long id) {
        try {
        	scheduleTimeService.removeScheduleTime(id);
        }catch (Exception e) {
            return new ResponseEntity<ScheduleTime>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ScheduleTime>(HttpStatus.NO_CONTENT);
    }

}
