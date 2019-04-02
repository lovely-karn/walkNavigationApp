package com.lovely.walknavigationapp.ui.mapNavigationActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.model.DirectionsResult;
import com.lovely.walknavigationapp.data.model.CommonResponse;
import com.lovely.walknavigationapp.data.model.directionResult.DirectionResults;
import com.lovely.walknavigationapp.data.network.ApiError;
import com.lovely.walknavigationapp.data.network.ApiInterface;

import java.util.List;

/**
 * The interface Maps navigation activity interactor.
 */
public interface MapsNavigationActivityInteractor {


    /**
     * Api hit to get direction result.
     *
     * @param Origin      the origin
     * @param destination the destination
     * @param listener    the listener
     */
    void apiHitTOGetDirectionResult(final LatLng Origin, final LatLng destination, GetDirectionResultListener listener);

    /**
     * The interface Get direction result listener.
     */
    interface GetDirectionResultListener {

        /**
         * Gets direction result success.
         *
         * @param response the direction results response
         */
        void getDirectionResultSuccess(final DirectionsResult response);

        /**
         * Gets direction result failure.
         *
         * @param error the error
         */
        void getDirectionResultFailure(final ApiError error);
    }
}
