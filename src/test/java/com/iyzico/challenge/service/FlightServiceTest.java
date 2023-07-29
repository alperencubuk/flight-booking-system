package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.*;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.exception.GeneralException;
import com.iyzico.challenge.repository.FlightRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @InjectMocks
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void shouldAddFlightSuccessfully() {
        FlightRequest flightRequest = new FlightRequest();
        flightRequest.setFlightName("Test Flight");

        Flight flight = new Flight();
        flight.setFlightName("Test Flight");

        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setFlightName("Test Flight");

        when(modelMapper.map(flightRequest, Flight.class)).thenReturn(flight);
        when(flightRepository.save(flight)).thenReturn(flight);
        when(modelMapper.map(flight, FlightResponse.class)).thenReturn(flightResponse);

        FlightResponse result = flightService.addFlight(flightRequest);

        assertThat(result.getFlightName()).isEqualTo(flightResponse.getFlightName());
        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    void shouldGetFlightSuccessfully() {
        Long flightId = 1L;
        Flight flight = new Flight();
        flight.setFlightName("Test Flight");

        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setFlightName("Test Flight");

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(modelMapper.map(flight, FlightResponse.class)).thenReturn(flightResponse);

        FlightResponse result = flightService.getFlight(flightId);

        assertThat(result.getFlightName()).isEqualTo(flightResponse.getFlightName());
    }

    @Test
    void shouldGetAllFlightsSuccessfully() {
        Flight flight = new Flight();
        flight.setFlightName("Test Flight");

        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setFlightName("Test Flight");

        Page<Flight> flightPage = new PageImpl<>(Collections.singletonList(flight));
        when(flightRepository.findAll(any(Pageable.class))).thenReturn(flightPage);
        when(modelMapper.map(flight, FlightResponse.class)).thenReturn(flightResponse);

        Page<FlightResponse> result = flightService.getAllFlights(Pageable.unpaged());

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getFlightName()).isEqualTo(flightResponse.getFlightName());
    }

    @Test
    void shouldUpdateFlightSuccessfully() {
        Long flightId = 1L;
        FlightUpdate flightUpdate = new FlightUpdate();
        flightUpdate.setFlightName("Updated Test Flight");

        Flight flight = new Flight();
        flight.setFlightName("Updated Test Flight");

        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setFlightName("Updated Test Flight");

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(flightRepository.save(flight)).thenReturn(flight);
        when(modelMapper.map(flight, FlightResponse.class)).thenReturn(flightResponse);

        FlightResponse result = flightService.updateFlight(flightId, flightUpdate);

        assertThat(result.getFlightName()).isEqualTo(flightResponse.getFlightName());
    }

    @Test
    void shouldDeleteFlightSuccessfully() {
        Long flightId = 1L;
        Flight flight = new Flight();
        flight.setId(flightId);
        flight.setFlightName("Test Flight");

        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setId(flightId);

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(modelMapper.map(flight, FlightResponse.class)).thenReturn(flightResponse);

        flightService.deleteFlight(flightId);

        verify(flightRepository, times(1)).deleteById(flightId);
    }

    @Test
    void shouldThrowExceptionWhenFlightDoesNotExist() {
        Long flightId = 1L;
        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        assertThrows(GeneralException.class, () -> flightService.getFlight(flightId));
    }

    @Test
    void shouldGetFlightWithAvailableSeatsSuccessfully() {
        Long flightId = 1L;
        Flight flight = new Flight();
        flight.setFlightName("Test Flight");
        Seat seat = new Seat();
        seat.setSeatNumber("A1");
        flight.setSeats(Collections.singletonList(seat));

        FlightWithAvailableSeatsResponse flightResponse = new FlightWithAvailableSeatsResponse();
        flightResponse.setFlightName("Test Flight");
        SeatResponse seatResponse = new SeatResponse();
        seatResponse.setSeatNumber("A1");
        flightResponse.setAvailableSeats(Collections.singletonList(seatResponse));

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(modelMapper.map(flight, FlightWithAvailableSeatsResponse.class)).thenReturn(flightResponse);
        when(modelMapper.map(seat, SeatResponse.class)).thenReturn(seatResponse);

        FlightWithAvailableSeatsResponse result = flightService.getFlightWithAvailableSeats(flightId);

        assertThat(result.getFlightName()).isEqualTo(flightResponse.getFlightName());
        assertThat(result.getAvailableSeats()).hasSize(1);
        assertThat(result.getAvailableSeats().get(0).getSeatNumber()).isEqualTo(seatResponse.getSeatNumber());
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(flightRepository, modelMapper);
    }

}
