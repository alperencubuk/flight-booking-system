package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.*;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.exception.GeneralException;
import com.iyzico.challenge.repository.FlightRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;

    public FlightService(FlightRepository flightRepository, ModelMapper modelMapper) {
        this.flightRepository = flightRepository;
        this.modelMapper = modelMapper;
    }

    public FlightResponse addFlight(FlightRequest flightRequest) {
        try {
            return modelMapper.map(flightRepository.save(modelMapper.map(flightRequest, Flight.class)), FlightResponse.class);
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException(HttpStatus.CONFLICT, "Flight " + flightRequest.getFlightName() + " already exist");
        }
    }

    public FlightResponse getFlight(Long flightId) {
        return modelMapper.map(getFlightDb(flightId), FlightResponse.class);
    }

    public FlightWithAvailableSeatsResponse getFlightWithAvailableSeats(Long flightId) {
        return mapToFlightResponseWithAvailableSeats(getFlightDb(flightId));
    }

    public Flight getFlightDb(Long flightId) {
        Flight flight = flightRepository.findById(flightId).orElse(null);
        if (flight == null) {
            throw new GeneralException(HttpStatus.NOT_FOUND, "Flight " + flightId + " not found");
        }
        return flight;
    }

    public Page<FlightResponse> getAllFlights(Pageable pageable) {
        Page<Flight> flightPage = flightRepository.findAll(pageable);
        return flightPage.map(this::mapToFlightResponse);
    }

    public Page<FlightWithAvailableSeatsResponse> getAllFlightsWithAvailableSeats(Pageable pageable) {
        Page<Flight> flightPage = flightRepository.findAll(pageable);
        return flightPage.map(this::mapToFlightResponseWithAvailableSeats);
    }

    public FlightResponse updateFlight(Long flightId, FlightUpdate flight) {
        try {
            Flight updatedFlight = getFlightDb(flightId);
            if (flight.getFlightName() != null) {
                updatedFlight.setFlightName(flight.getFlightName());
            }
            if (flight.getFlightDescription() != null) {
                updatedFlight.setFlightDescription(flight.getFlightDescription());
            }
            flightRepository.save(updatedFlight);
            return modelMapper.map(updatedFlight, FlightResponse.class);
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException(HttpStatus.CONFLICT, "Flight " + flight.getFlightName() + " already exist");
        }
    }

    public void deleteFlight(Long flightId) {
        flightRepository.deleteById(getFlight(flightId).getId());
    }

    private FlightResponse mapToFlightResponse(Flight flight) {
        return modelMapper.map(flight, FlightResponse.class);
    }

    private SeatResponse mapToSeatResponse(Seat seat) {
        return modelMapper.map(seat, SeatResponse.class);
    }

    private FlightWithAvailableSeatsResponse mapToFlightResponseWithAvailableSeats(Flight flight) {
        FlightWithAvailableSeatsResponse flightWithSeatsResponse = modelMapper.map(flight, FlightWithAvailableSeatsResponse.class);
        List<SeatResponse> seatResponses = flight.getAvailableSeats().stream()
                .map(this::mapToSeatResponse)
                .collect(Collectors.toList());
        flightWithSeatsResponse.setAvailableSeats(seatResponses);
        return flightWithSeatsResponse;
    }
}
