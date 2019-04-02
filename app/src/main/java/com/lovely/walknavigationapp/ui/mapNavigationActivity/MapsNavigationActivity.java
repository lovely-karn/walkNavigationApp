package com.lovely.walknavigationapp.ui.mapNavigationActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.GeocodedWaypoint;
import com.lovely.walknavigationapp.R;
import com.lovely.walknavigationapp.constant.Appconstant;
import com.lovely.walknavigationapp.data.network.RestClient;

import java.util.List;


/**
 * The type Maps navigation activity.
 */
public class MapsNavigationActivity
        extends AppCompatActivity
        implements MapsNavigationActivityView,
        OnMapReadyCallback {

    private static final int ZOOM_LEVEL = 10;
    private LatLng origin, destination;
    private MapsNavigationActivityPresenter presenter;
    private List<LatLng> wayPoints;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_navigation);

        // getIntentData
        getIntentData();

        // attach the presenter and make interactor call to get the results
        presenter = new MapsNavigationActivityPresenterImpl(this,
                new MapsNavigationActivityInteractorImpl(RestClient.getGoogleApiInterface(),
                        getString(R.string.google_maps_api_key)));

    }


    private void getIntentData() {

        // let's see how in between addresses will come
        // ? as waypoints will be needed ... the array would come as parcelable array sort of

        Intent intent = getIntent();

        // dummy for now
        origin = new LatLng(-20.291825, 57.448668);
        destination = new LatLng(-20.179724, 57.613463);


        if (intent.hasExtra(Appconstant.INTENT_ORIGIN)) {

        }

        if (intent.hasExtra(Appconstant.INTENT_DESTINATION)) {

        }

        if (intent.hasExtra(Appconstant.INTENT_WAYPOINTS)) {

        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        init();
    }

    private void init() {

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // adding the callback
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void showNavigationLines(final List<LatLng> decodedPath,
                                    final int polyLineColor) {

        // show navigation lines on view
        // Adding route on the map
        if (googleMap != null) {

            PolylineOptions polylineOptions = new PolylineOptions().color(getResources().getColor(polyLineColor)).addAll(decodedPath);

            setPolyLineOnClickListener();

            Polyline polyline = googleMap.addPolyline(polylineOptions);

            polyline.setClickable(true);
        }

    }

    /**
     * setMarker
     *
     * @param map      - on which marker has to be shown
     * @param location - the location where marker has to be shown
     * @param title    - title of the address
     * @param address  - address
     */
    private void setMarker(final GoogleMap map, final LatLng location, final String title, final String address) {
        if (origin != null) {
            LatLng latlng = new LatLng(location.latitude, location.longitude);
            map.clear();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, ZOOM_LEVEL));
            map.addMarker(new MarkerOptions()
                    .title(title)
                    .snippet(address)
                    .position(latlng));
        }
    }

    @Override
    public void showProgressBar() {

//        show progressbar on toolbar
    }

    @Override
    public void hideProgressBar() {

        // hide the progressbar if running

    }

    @Override
    public void showErrorMessage(final String errorMsg) {

        // an show alert dialog / snack bar either
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onMapReady(final GoogleMap mgoogleMap) {

        this.googleMap = mgoogleMap;

        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, ZOOM_LEVEL));

        // getIntentData()
        getIntentData();

        googleMap.addMarker(new MarkerOptions()
                .title("origin")
                .position(origin));


        presenter.askInteractorToGetDirections(origin, destination, wayPoints);
    }

    public void addMarkersToMap(final DirectionsResult results) {

        if (googleMap != null) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0]
                    .startLocation.lat, results.routes[0].legs[0].startLocation.lng))
                    .title(results.routes[0].legs[0].startAddress));

            googleMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0]
                    .legs[0].endLocation.lat, results.routes[0].legs[0]
                    .endLocation.lng)).title(results.routes[0].legs[0].startAddress));
        }
    }

    @Override
    public void addMarkersToMap(final GeocodedWaypoint[] geocodedWaypointsList) {

       // uisng viewmodel values will be udated in viewModel & then value will be fetched via data-binding in the pop up ..

    }

    /**
     * set on click listener
     */
    private void setPolyLineOnClickListener() {

        googleMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                //via data binding show all waypoints marker ...

//                Toast.makeText(MapsNavigationActivity.this, "poly line clicked", Toast.LENGTH_LONG).show();

            }
        });

    }
}
