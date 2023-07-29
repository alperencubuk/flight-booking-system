package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.SeatRequest;
import com.iyzico.challenge.dto.SeatResponse;
import com.iyzico.challenge.dto.SeatStatus;
import com.iyzico.challenge.dto.SeatUpdate;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.exception.GeneralException;
import com.iyzico.challenge.repository.SeatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final ModelMapper modelMapper;
    private final FlightService flightService;

    public SeatService(SeatRepository seatRepository, ModelMapper modelMapper, FlightService flightService) {
        this.seatRepository = seatRepository;
        this.modelMapper = modelMapper;
        this.flightService = flightService;
    }

    public SeatResponse addSeat(SeatRequest seatRequest) {
        try {
            flightService.getFlightDb(seatRequest.getFlightId());
            return modelMapper.map(seatRepository.save(modelMapper.map(seatRequest, Seat.class)), SeatResponse.class);
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException(HttpStatus.CONFLICT, "Seat " + seatRequest.getSeatNumber() + " already exist");
        }
    }

    public SeatResponse getSeat(Long seatId) {
        return modelMapper.map(getSeatDb(seatId), SeatResponse.class);
    }

    public Seat getSeatDb(Long seatId) {
        Seat seat = seatRepository.findById(seatId).orElse(null);
        if (seat == null) {
            throw new GeneralException(HttpStatus.NOT_FOUND, "Seat " + seatId + " not found");
        }
        return seat;
    }

    public SeatResponse updateSeat(Long seatId, SeatUpdate seat) {
        try {
            Seat updatedSeat = getSeatDb(seatId);
            if (seat.getSeatNumber() != null) {
                updatedSeat.setSeatNumber(seat.getSeatNumber());
            }
            if (seat.getSeatPrice() != null) {
                updatedSeat.setSeatPrice(seat.getSeatPrice());
            }
            seatRepository.save(updatedSeat);
            return modelMapper.map(updatedSeat, SeatResponse.class);
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException(HttpStatus.CONFLICT, "Flight " + seat.getSeatNumber() + " already exist");
        }
    }

    public void updateSeatStatus(Long seatId, SeatStatus status) {
        Seat seat = getSeatDb(seatId);
        if (seat.getSeatStatus() != SeatStatus.EMPTY) {
            throw new GeneralException(HttpStatus.BAD_REQUEST, "This seat is not available");
        }
        seat.setSeatStatus(status);
        seatRepository.save(seat);
    }

    public void deleteSeat(Long seatId) {
        seatRepository.deleteById(getSeat(seatId).getId());
    }
}
