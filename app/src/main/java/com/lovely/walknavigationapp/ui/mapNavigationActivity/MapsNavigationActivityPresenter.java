package com.lovely.walknavigationapp.ui.mapNavigationActivity;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * The interface Maps navigation activity presenter.
 */
public interface MapsNavigationActivityPresenter {

    /**
     * Ask interactor to get directions.
     *
     * @param origin      the origin
     * @param destination the destination
     * @param wayPoints   the way points
     */
    void askInteractorToGetDirections(LatLng origin, LatLng destination, List<LatLng> wayPoints);

}
