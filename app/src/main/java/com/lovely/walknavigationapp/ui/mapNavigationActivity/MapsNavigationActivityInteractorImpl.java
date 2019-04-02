package com.lovely.walknavigationapp.ui.mapNavigationActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.lovely.walknavigationapp.data.network.ApiInterface;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MapsNavigationActivityInteractorImpl implements MapsNavigationActivityInteractor {

    private ApiInterface apiInterface;
    private String mapsKey;

    MapsNavigationActivityInteractorImpl(final ApiInterface apiInterface, final String mapsKey) {
        this.apiInterface = apiInterface;
        this.mapsKey = mapsKey;
    }


    @Override
    public void apiHitTOGetDirectionResult(final LatLng origin,
                                           final LatLng destination,
                                           final GetDirectionResultListener listener) {

        DirectionsResult directionsResult;

        String originStr = origin.latitude + "," + origin.longitude;
        String destStr = destination.latitude + "," + destination.longitude;


        try {

            DirectionsApiRequest directionsApiRequest = DirectionsApi.newRequest(getGeoContext())
                    .mode(TravelMode.WALKING).origin(originStr)
                    .destination(destStr);

//            // adding way points
//            if (wayPoints != null && wayPoints.size() > 0) {
//
//                directionsApiRequest.waypoints(wayPoints.toArray(new com.google.maps.model.LatLng[wayPoints.size()]));
//
//            }


            directionsResult = directionsApiRequest.await();

            //pass the result to the listener
            listener.getDirectionResultSuccess(directionsResult);

        } catch (ApiException e) {
            e.printStackTrace();

            // we can send the error in msg form
//            listener.getDirectionResultFailure();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(10)
                .setApiKey(mapsKey)
                .setConnectTimeout(120, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }
}
