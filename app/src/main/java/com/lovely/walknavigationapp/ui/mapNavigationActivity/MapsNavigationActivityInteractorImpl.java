package com.lovely.walknavigationapp.ui.mapNavigationActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.lovely.walknavigationapp.BuildConfig;
import com.lovely.walknavigationapp.R;
import com.lovely.walknavigationapp.constant.ApiKeyconstant;
import com.lovely.walknavigationapp.data.model.CommonResponse;
import com.lovely.walknavigationapp.data.model.directionResult.DirectionResults;
import com.lovely.walknavigationapp.data.network.ApiError;
import com.lovely.walknavigationapp.data.network.ApiInterface;
import com.lovely.walknavigationapp.data.network.CommonParams;
import com.lovely.walknavigationapp.data.network.ResponseResolver;

import java.io.IOException;
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

        CommonParams.Builder builder = new CommonParams.Builder();
        DirectionsResult directionsResult;

        String originStr = origin.latitude + "," + origin.longitude;
        String destStr = destination.latitude + "," + destination.longitude;

        builder.add(ApiKeyconstant.KEY_ORIGIN, originStr);
        builder.add(ApiKeyconstant.KEY_DESTINATION, destStr);
        builder.add(ApiKeyconstant.KEY_TRAVEL_MODE, TravelMode.WALKING);
        builder.add(ApiKeyconstant.MAPS_API_KEY, mapsKey);


        try {
            directionsResult = DirectionsApi.newRequest(getGeoContext())
                    .mode(TravelMode.WALKING).origin(originStr)
                    .destination(destStr)
                    .await();

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
