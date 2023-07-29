package com.iyzico.challenge.dto;

import java.util.List;

public class FlightWithAvailableSeatsResponse {
    private Long id;
    private String flightName;
    private String flightDescription;
    private List<SeatResponse> availableSeats;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<SeatResponse> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<SeatResponse> availableSeats) {
        this.availableSeats = availableSeats;
    }
}
