package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.PaymentRequest;
import com.iyzico.challenge.dto.SeatStatus;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.exception.GeneralException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private SeatService seatService;

    @Mock
    private FlightService flightService;

    @Mock
    private PaymentServiceClients paymentServiceClients;

    @Test
    void shouldProcessPaymentSuccessfully() throws ExecutionException, InterruptedException {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setFlightId(1L);
        paymentRequest.setSeatId(1L);

        Seat seat = new Seat();
        seat.setSeatPrice(new BigDecimal("100.0"));

        when(flightService.getFlightDb(anyLong())).thenReturn(null);
        when(seatService.getSeatDb(anyLong())).thenReturn(seat);
        doNothing().when(seatService).updateSeatStatus(anyLong(), any(SeatStatus.class));
        when(paymentServiceClients.call(any(BigDecimal.class))).thenReturn(CompletableFuture.completedFuture("Payment Successful"));

        String result = paymentService.processPayment(paymentRequest);

        assertThat(result).isEqualTo("Payment Successful");
    }

    @Test
    void shouldThrowExceptionWhenPaymentFails() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setFlightId(1L);
        paymentRequest.setSeatId(1L);

        Seat seat = new Seat();
        seat.setSeatPrice(new BigDecimal("100.0"));

        when(flightService.getFlightDb(anyLong())).thenReturn(null);
        when(seatService.getSeatDb(anyLong())).thenReturn(seat);
        doNothing().when(seatService).updateSeatStatus(anyLong(), any(SeatStatus.class));
        when(paymentServiceClients.call(any(BigDecimal.class))).thenThrow(CompletionException.class);

        assertThrows(GeneralException.class, () -> paymentService.processPayment(paymentRequest));
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(seatService, flightService, paymentServiceClients);
    }
}
