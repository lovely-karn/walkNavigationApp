package com.lovely.walknavigationapp.ui.mapNavigationActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.GeocodedWaypoint;

import java.util.List;

/**
 * The interface Maps navigation activity view.
 */
public interface MapsNavigationActivityView {


    /**
     * Show navigation lines.
     *
     * @param decodedPath   the rect line
     * @param polyLineColor the poly line color
     */
    void showNavigationLines(final List<LatLng> decodedPath, final int polyLineColor);


    /**
     * Show progress bar.
     */
    void showProgressBar();

    /**
     * Hide progress bar.
     */
    void hideProgressBar();


    /**
     * Show error message.
     *
     * @param errorMsg the error msg
     */
    void showErrorMessage(String errorMsg);


    void addMarkersToMap(DirectionsResult results);

    /**
     * @param geocodedWaypointsList waypoint list
     */
    void addMarkersToMap(GeocodedWaypoint geocodedWaypointsList[]);

}
