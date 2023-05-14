package com.art4musilm.artfoodCustomer.events;

import com.art4musilm.artfoodCustomer.models.response.CurrentLocation;

public class LocationEvent {
    CurrentLocation currentLocation;

    public LocationEvent(CurrentLocation currentLocation) {
        this.currentLocation = currentLocation;
    }

    public CurrentLocation getCurrentLocation() {
        return currentLocation;
    }
}
