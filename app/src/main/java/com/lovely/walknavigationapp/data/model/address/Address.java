package com.lovely.walknavigationapp.data.model.address;

import com.google.android.gms.maps.model.LatLng;

public class Address {

    private LatLng latLng;
    private String formattedAddress;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(final LatLng latLng) {
        this.latLng = latLng;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(final String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}
