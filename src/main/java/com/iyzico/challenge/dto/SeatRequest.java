package com.iyzico.challenge.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SeatRequest {
    @NotNull
    private String seatNumber;
    @NotNull
    private BigDecimal seatPrice;
    @NotNull
    private Long flightId;

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public BigDecimal getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(BigDecimal seatPrice) {
        this.seatPrice = seatPrice;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
}
