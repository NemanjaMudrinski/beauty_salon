package beautySalon.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
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
import beautySalon.services.ScheduleService;
import beautySalon.utils.View.HideOptionalProperties;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;
	
	@JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/all", method=RequestMethod.GET)
    public ResponseEntity<Iterable<Schedule>> getSchedules() {
        return new ResponseEntity<Iterable<Schedule>>(scheduleService.getSchedules(), HttpStatus.OK);
    }

    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        Optional<Schedule> schedule = scheduleService.getScheduleById(id);
        if(schedule.isPresent()) {
            return new ResponseEntity<Schedule>(schedule.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Schedule>(HttpStatus.NOT_FOUND);
    }
     
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public ResponseEntity<Schedule> addSchedule(@RequestBody Schedule schedule) {
        scheduleService.addSchedule(schedule);
        return new ResponseEntity<Schedule>(schedule, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
    	scheduleService.editSchedule(id, schedule);
        return new ResponseEntity<Schedule>(schedule, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Schedule> removeSchedule(@PathVariable Long id) {
        try {
            scheduleService.removeSchedule(id);
        }catch (Exception e) {
            return new ResponseEntity<Schedule>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Schedule>(HttpStatus.NO_CONTENT);
    }
    
    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/future", method=RequestMethod.GET)
    public ResponseEntity<ArrayList<Schedule>> getScheduleByDay() {
        return new ResponseEntity<ArrayList<Schedule>>(scheduleService.getScheduleByDay(), HttpStatus.OK);
    }

    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/past", method=RequestMethod.GET)
    public ResponseEntity<ArrayList<Schedule>> getScheduleHistory() {
        return new ResponseEntity<ArrayList<Schedule>>(scheduleService.getScheduleHistory(), HttpStatus.OK);
    }
    
    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/time/{id}", method=RequestMethod.GET)
    public ResponseEntity<ArrayList<Schedule>> getScheduleTimeByDay(@PathVariable("id")@DateTimeFormat(pattern = "yyyy-MM-dd") Date id) {
        return new ResponseEntity<ArrayList<Schedule>>(scheduleService.getScheduleTimeByDay(id), HttpStatus.OK);
    }
}
