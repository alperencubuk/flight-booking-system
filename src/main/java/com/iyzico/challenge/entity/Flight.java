package com.iyzico.challenge.entity;

import com.iyzico.challenge.dto.SeatStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Flight extends BaseEntity {

    @Column(name = "flight_name", unique = true)
    private String flightName;

    @Column(name = "flight_description")
    private String flightDescription;

    @OneToMany(mappedBy = "flight", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getFlightDescription() {
        return flightDescription;
    }

    public void setFlightDescription(String flightDescription) {
        this.flightDescription = flightDescription;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Seat> getAvailableSeats() {
        return seats.stream()
                .filter(seat -> SeatStatus.EMPTY.equals(seat.getSeatStatus()))
                .collect(Collectors.toList());
    }
}
