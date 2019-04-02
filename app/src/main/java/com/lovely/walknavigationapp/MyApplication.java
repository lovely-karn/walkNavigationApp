package com.lovely.walknavigationapp;

import android.support.multidex.MultiDexApplication;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        initialisePlacesSDK();

    }

    private void initialisePlacesSDK() {

        // Initialize Places.
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_api_key));

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);
    }
}
