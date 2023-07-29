package com.iyzico.challenge.controller;

import com.iyzico.challenge.dto.SeatRequest;
import com.iyzico.challenge.dto.SeatResponse;
import com.iyzico.challenge.dto.SeatUpdate;
import com.iyzico.challenge.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/seats")
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    public ResponseEntity<SeatResponse> add(@Valid @RequestBody SeatRequest seatRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(seatService.addSeat(seatRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatResponse> getSeat(@PathVariable("id") Long seatId) {
        return ResponseEntity.ok(seatService.getSeat(seatId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatResponse> update(@PathVariable("id") Long seatId, @Valid @RequestBody SeatUpdate seat) {
        return ResponseEntity.ok(seatService.updateSeat(seatId, seat));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long seatId) {
        seatService.deleteSeat(seatId);
    }
}
