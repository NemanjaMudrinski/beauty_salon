package beautySalon.controllers;

import java.util.ArrayList;
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

import beautySalon.models.Client;
import beautySalon.models.Reservation;
import beautySalon.services.ReservationService;
import beautySalon.utils.View.HideOptionalProperties;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired
	ReservationService reservationService;
	
	@JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/all", method=RequestMethod.GET)
    public ResponseEntity<Iterable<Reservation>> getReservations() {
        return new ResponseEntity<Iterable<Reservation>>(reservationService.getAllReservations(), HttpStatus.OK);
    }

    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        if(reservation.isPresent()) {
            return new ResponseEntity<Reservation>(reservation.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Reservation>(HttpStatus.NOT_FOUND);
    }
    
    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/confirmed-reservation", method=RequestMethod.GET)
    public ResponseEntity<Iterable<Optional<Reservation>>> getConfirmed() {
        Iterable<Optional<Reservation>> reservation = reservationService.getConfirmed();
        return new ResponseEntity<Iterable<Optional<Reservation>>>(reservation, HttpStatus.OK);
    }
    
    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/not-confirmed-reservation/{id}", method=RequestMethod.GET)
    public ResponseEntity<Iterable<Optional<Reservation>>> getNotConfirmed(@PathVariable Long id) {
        Iterable<Optional<Reservation>> reservation = reservationService.getNotConfirmed(id);
        return new ResponseEntity<Iterable<Optional<Reservation>>>(reservation, HttpStatus.OK);
    }
    
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        reservationService.addReservation(reservation);
        return new ResponseEntity<Reservation>(reservation, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
    	reservationService.editReservation(id, reservation);
        return new ResponseEntity<Reservation>(reservation, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Reservation> removeReservation(@PathVariable Long id) {
        try {
            reservationService.removeReservation(id);
        }catch (Exception e) {
            return new ResponseEntity<Reservation>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Reservation>(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value="/confirmed/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Reservation> confirmReservation(@PathVariable Long id) {
    	try {
    		reservationService.confirmReservation(id);
    	} catch (Exception e) {
    		return new ResponseEntity<Reservation>(HttpStatus.NOT_FOUND);
    	}
    	
    	return new ResponseEntity<Reservation>(HttpStatus.CREATED);
    }
    
    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/confirmed/client/{clientUsername}", method=RequestMethod.GET)
    public ResponseEntity<ArrayList<Reservation>> getClientConfirmedReservationByUsername(@PathVariable String clientUsername) {
    	return new ResponseEntity<ArrayList<Reservation>>(reservationService.getClientConfirmedReservations(clientUsername), HttpStatus.OK);
    }
    
    @JsonView(HideOptionalProperties.class)
    @RequestMapping(value="/all-by-username/{clientUsername}", method=RequestMethod.GET)
    public ResponseEntity<ArrayList<Reservation>> getAllReservationsByUsername(@PathVariable String clientUsername) {
    	return new ResponseEntity<ArrayList<Reservation>>(reservationService.getAllReservationsByUser(clientUsername), HttpStatus.OK);
    }

}
