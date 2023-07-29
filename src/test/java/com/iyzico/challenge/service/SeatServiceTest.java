package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.SeatRequest;
import com.iyzico.challenge.dto.SeatResponse;
import com.iyzico.challenge.dto.SeatUpdate;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.repository.SeatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {

    @InjectMocks
    private SeatService seatService;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private FlightService flightService;

    @Test
    void shouldAddSeatSuccessfully() {
        SeatRequest seatRequest = new SeatRequest();
        seatRequest.setSeatNumber("A1");

        Seat seat = new Seat();
        seat.setSeatNumber("A1");

        SeatResponse seatResponse = new SeatResponse();
        seatResponse.setSeatNumber("A1");

        when(modelMapper.map(seatRequest, Seat.class)).thenReturn(seat);
        when(seatRepository.save(seat)).thenReturn(seat);
        when(modelMapper.map(seat, SeatResponse.class)).thenReturn(seatResponse);

        SeatResponse result = seatService.addSeat(seatRequest);

        assertThat(result.getSeatNumber()).isEqualTo(seatResponse.getSeatNumber());
        verify(seatRepository, times(1)).save(any(Seat.class));
    }

    @Test
    void shouldGetSeatSuccessfully() {
        Long seatId = 1L;
        Seat seat = new Seat();
        seat.setSeatNumber("A1");

        SeatResponse seatResponse = new SeatResponse();
        seatResponse.setSeatNumber("A1");

        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));
        when(modelMapper.map(seat, SeatResponse.class)).thenReturn(seatResponse);

        SeatResponse result = seatService.getSeat(seatId);

        assertThat(result.getSeatNumber()).isEqualTo(seatResponse.getSeatNumber());
    }

    @Test
    void shouldUpdateSeatSuccessfully() {
        Long seatId = 1L;
        SeatUpdate seatUpdate = new SeatUpdate();
        seatUpdate.setSeatNumber("B2");

        Seat seat = new Seat();
        seat.setSeatNumber("B2");

        SeatResponse seatResponse = new SeatResponse();
        seatResponse.setSeatNumber("B2");

        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));
        when(seatRepository.save(seat)).thenReturn(seat);
        when(modelMapper.map(seat, SeatResponse.class)).thenReturn(seatResponse);

        SeatResponse result = seatService.updateSeat(seatId, seatUpdate);

        assertThat(result.getSeatNumber()).isEqualTo(seatResponse.getSeatNumber());
    }

    @Test
    void shouldDeleteSeatSuccessfully() {
        Long seatId = 1L;
        Seat seat = new Seat();
        seat.setId(seatId);
        seat.setSeatNumber("A1");

        SeatResponse seatResponse = new SeatResponse();
        seatResponse.setId(seatId);

        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));
        when(modelMapper.map(seat, SeatResponse.class)).thenReturn(seatResponse);

        seatService.deleteSeat(seatId);

        verify(seatRepository, times(1)).deleteById(seatId);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(seatRepository, modelMapper, flightService);
    }

}
