package com.iyzico.challenge.dto;

import java.math.BigDecimal;

public class SeatUpdate {
    private String seatNumber;
    private BigDecimal seatPrice;

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
}
