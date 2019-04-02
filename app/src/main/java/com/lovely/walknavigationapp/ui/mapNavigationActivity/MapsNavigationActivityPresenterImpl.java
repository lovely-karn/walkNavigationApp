package com.lovely.walknavigationapp.ui.mapNavigationActivity;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.Distance;
import com.google.maps.model.GeocodedWaypoint;
import com.lovely.walknavigationapp.R;
import com.lovely.walknavigationapp.data.network.ApiError;
import com.lovely.walknavigationapp.util.RouteDecode;

import java.util.List;

import static com.lovely.walknavigationapp.constant.Appconstant.FOUR_THOUSAND;
import static com.lovely.walknavigationapp.constant.Appconstant.TWENTY_THOUSAND;

public class MapsNavigationActivityPresenterImpl
        implements MapsNavigationActivityPresenter, MapsNavigationActivityInteractor.GetDirectionResultListener {


    private MapsNavigationActivityView view;
    private MapsNavigationActivityInteractor interactor;

    MapsNavigationActivityPresenterImpl(final MapsNavigationActivityView view,
                                        final MapsNavigationActivityInteractor interactor) {

        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void askInteractorToGetDirections(LatLng origin, LatLng destination, List<LatLng> wayPoints) {


        if (origin != null && destination != null) {

            // here wayPoints can be picked from common data..
            interactor.apiHitTOGetDirectionResult(origin, destination, this);
        }
    }

    @Override
    public void getDirectionResultSuccess(final DirectionsResult directionResultsResponse) {

        manipulateTheDirectionResponse(directionResultsResponse);
    }

    private void manipulateTheDirectionResponse(final DirectionsResult directionResultsResponse) {

        List<LatLng> decodedPath = RouteDecode.decodePoly(directionResultsResponse.routes[0].overviewPolyline.getEncodedPath());

        int polylineColor = getPolyLineColor(directionResultsResponse.routes[0].legs[0].distance);

        view.addMarker(directionResultsResponse);

        GeocodedWaypoint geocodedWaypointsList[] = directionResultsResponse.geocodedWaypoints;

        view.saveWayPointsInViewModel(geocodedWaypointsList);

        view.showNavigationLines(decodedPath, polylineColor);
    }

    private int getPolyLineColor(final Distance startToEndDistance) {

        if (startToEndDistance.inMeters < FOUR_THOUSAND) {
            return Color.BLACK;

        } else if (startToEndDistance.inMeters > FOUR_THOUSAND
                && startToEndDistance.inMeters < TWENTY_THOUSAND) {
            return Color.BLUE;

        } else if (startToEndDistance.inMeters > TWENTY_THOUSAND) {
            return R.color.purple;

        }

        return Color.BLACK;
    }

    @Override
    public void getDirectionResultFailure(final ApiError error) {

        view.showErrorMessage(error.getMessage());
    }
}
