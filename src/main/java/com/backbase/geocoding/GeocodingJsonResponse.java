package com.backbase.geocoding;

public class GeocodingJsonResponse {
    private final String formattedAddress;
    private final double latitude;
    private final double longitude;

    public GeocodingJsonResponse(final String formattedAddress,
                                 final double latitude,
                                 final double longitude) {
        this.formattedAddress = formattedAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
