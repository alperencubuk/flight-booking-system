package com.iyzico.challenge.controller;

import com.iyzico.challenge.dto.FlightRequest;
import com.iyzico.challenge.dto.FlightResponse;
import com.iyzico.challenge.dto.FlightUpdate;
import com.iyzico.challenge.dto.FlightWithAvailableSeatsResponse;
import com.iyzico.challenge.service.FlightService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<FlightResponse> add(@Valid @RequestBody FlightRequest flightRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.addFlight(flightRequest));
    }

    @GetMapping
    public Page<FlightResponse> getAll(Pageable pageable) {
        return flightService.getAllFlights(pageable);
    }

    @GetMapping("/empty")
    public Page<FlightWithAvailableSeatsResponse> getAllWithAvailableSeats(Pageable pageable) {
        return flightService.getAllFlightsWithAvailableSeats(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFlight(@PathVariable("id") Long flightId) {
        return ResponseEntity.ok(flightService.getFlight(flightId));
    }

    @GetMapping("/{id}/empty")
    public ResponseEntity<FlightWithAvailableSeatsResponse> getFlightWithAvailableSeats(@PathVariable("id") Long flightId) {
        return ResponseEntity.ok(flightService.getFlightWithAvailableSeats(flightId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> update(@PathVariable("id") Long flightId, @Valid @RequestBody FlightUpdate flight) {
        return ResponseEntity.ok(flightService.updateFlight(flightId, flight));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long flightId) {
        flightService.deleteFlight(flightId);
    }
}
