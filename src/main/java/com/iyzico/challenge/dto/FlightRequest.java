package com.iyzico.challenge.dto;

import javax.validation.constraints.NotNull;

public class FlightRequest {
    @NotNull
    private String flightName;
    private String flightDescription;

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
}
