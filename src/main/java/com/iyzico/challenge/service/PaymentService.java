package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.PaymentRequest;
import com.iyzico.challenge.dto.SeatStatus;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.exception.GeneralException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

@Service
public class PaymentService {
    private final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final SeatService seatService;
    private final FlightService flightService;
    private final PaymentServiceClients paymentServiceClients;

    public PaymentService(SeatService seatService, FlightService flightService, PaymentServiceClients paymentServiceClients) {
        this.seatService = seatService;
        this.flightService = flightService;
        this.paymentServiceClients = paymentServiceClients;
    }

    @Transactional
    public String processPayment(PaymentRequest paymentRequest) {
        try {
            flightService.getFlightDb(paymentRequest.getFlightId());
            Seat seat = seatService.getSeatDb(paymentRequest.getSeatId());
            seatService.updateSeatStatus(paymentRequest.getSeatId(), SeatStatus.OCCUPIED);
            CompletableFuture<String> paymentStatus = paymentServiceClients.call(seat.getSeatPrice());
            if (!paymentStatus.get().equals("success")) {
                seatService.updateSeatStatus(paymentRequest.getSeatId(), SeatStatus.EMPTY);
            }
            return paymentStatus.get();
        } catch (ExecutionException | InterruptedException | CompletionException e) {
            Thread.currentThread().interrupt();
            logger.error("Error while payment: {}", e.toString());
            throw new GeneralException(HttpStatus.BAD_REQUEST, "Payment failed");
        }
    }
}
