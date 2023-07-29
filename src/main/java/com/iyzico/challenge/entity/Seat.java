package com.iyzico.challenge.entity;

import com.iyzico.challenge.dto.SeatStatus;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"seat_number", "flight_id"}))
public class Seat extends BaseEntity {

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "seat_status")
    private SeatStatus seatStatus = SeatStatus.EMPTY;

    @Column(name = "seat_price")
    private BigDecimal seatPrice;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Version
    private Long version;

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public BigDecimal getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(BigDecimal seatPrice) {
        this.seatPrice = seatPrice;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
