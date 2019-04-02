package com.lovely.walknavigationapp.ui.mapNavigationActivity;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
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
import com.lovely.walknavigationapp.databinding.DialogWaypointsBinding;
import com.lovely.walknavigationapp.ui.getLocationActivity.model.GetLocationModel;
import com.lovely.walknavigationapp.ui.mapNavigationActivity.viewmodel.NavigationViewModel;

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
    private GetLocationModel getLocationModel;

    private Dialog dialog;
    private String mWayPointsString;

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


        if (intent.hasExtra(Appconstant.INTENT_GEOLOCATION_DATA)) {
            getLocationModel = intent.getParcelableExtra(Appconstant.INTENT_GEOLOCATION_DATA);
        }

        if (getLocationModel != null) {
            origin = getLocationModel.getOrigin();
            destination = getLocationModel.getDestination();

            if (wayPoints != null) {
                wayPoints = getLocationModel.getWaypoints();
            }
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

    public void addMarker(final DirectionsResult results) {

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
    public void saveWayPointsInViewModel(final GeocodedWaypoint[] geocodedWaypointsList) {

        // uisng viewmodel values will be udated in viewModel & then value will be fetched via data-binding in the pop up ..
        String wayPointsString = "";
        for (GeocodedWaypoint item : geocodedWaypointsList) {
            wayPointsString = wayPointsString + item.placeId + "\n";
        }

        mWayPointsString = wayPointsString;
    }

    /**
     * set on click listener
     */
    private void setPolyLineOnClickListener() {

        googleMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                //via data binding show all waypoints marker ...
                showPopUP();
            }
        });
    }

    private void showPopUP() {

        // remove the instance of old dialog
        if (dialog != null) {
            dialog.dismiss();
        }

        dialog = new Dialog(this);

        // Inflate view and obtain an instance of the binding class.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DialogWaypointsBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(MapsNavigationActivity.this), R.layout.dialog_waypoints, null, false);
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Assign the component to a property in the binding class.
        binding.setWayPointsString(mWayPointsString);
    }
}
