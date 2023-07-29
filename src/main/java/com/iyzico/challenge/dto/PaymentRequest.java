package com.iyzico.challenge.dto;

import javax.validation.constraints.NotNull;

public class PaymentRequest {
    @NotNull
    private Long flightId;
    @NotNull
    private Long seatId;

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }
}
