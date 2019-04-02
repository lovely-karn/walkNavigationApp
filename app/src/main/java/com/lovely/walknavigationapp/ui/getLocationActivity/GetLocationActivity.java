package com.lovely.walknavigationapp.ui.getLocationActivity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.lovely.walknavigationapp.R;
import com.lovely.walknavigationapp.constant.Appconstant;
import com.lovely.walknavigationapp.data.model.directionResult.Location;
import com.lovely.walknavigationapp.ui.getLocationActivity.adapter.AdditionalAddressesAdapter;
import com.lovely.walknavigationapp.ui.getLocationActivity.model.GetLocationModel;
import com.lovely.walknavigationapp.ui.mapNavigationActivity.MapsNavigationActivity;
import com.lovely.walknavigationapp.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class GetLocationActivity
        extends AppCompatActivity
        implements View.OnClickListener, GetLocationActivityView {

    private AppCompatButton btnWalkNavigation;
    private AppCompatTextView tvOrigin, tvDestination;
    private AppCompatTextView tvWayPoints;
    private int clickedItem = -1;

    private Double latitude, longitude;
    private String title, address;
    private String placeId;

    private Location origin, destination;
    private List<Location> waypoints = new ArrayList<>();

    private RecyclerView recyclerView;

    private AppCompatTextView tvAdditionalAddresses;

    private GetLocationActivityPresenter presenter;

    private AdditionalAddressesAdapter additionalAddressesAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);

        // instantiate presenter instance
        presenter = new GetLocationActivityPresenterImpl(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        init();

        setOnCLickListener(btnWalkNavigation, tvOrigin, tvDestination, tvWayPoints);
    }

    private void instantiatingRecyclerView() {

        recyclerView = findViewById(R.id.recyclerView);

        additionalAddressesAdapter = new AdditionalAddressesAdapter(waypoints);

        recyclerView.setAdapter(additionalAddressesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * initialising views
     */
    private void init() {

        tvWayPoints = findViewById(R.id.tvWayPoints);
        tvAdditionalAddresses = findViewById(R.id.tvAdditionalAddresses);

        tvOrigin = findViewById(R.id.etOrigin);
        tvDestination = findViewById(R.id.etDestination);

        btnWalkNavigation = findViewById(R.id.btnWalkNavigation);

        instantiatingRecyclerView();
    }

    /**
     * adding on click listener
     *
     * @param views views on which on click listener has to be added
     */
    private void setOnCLickListener(final View... views) {

        for (View item : views) {
            item.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(final View v) {

        switch (v.getId()) {

            case R.id.btnWalkNavigation:

                // TODO :- has to test this part
                GetLocationModel getLocationModel = createGeoLocationModel();

                Intent intent = new Intent(GetLocationActivity.this, MapsNavigationActivity.class);
                intent.putExtra(Appconstant.INTENT_GEOLOCATION_DATA, getLocationModel);
                startActivity(intent);
                break;

            case R.id.etOrigin:
                clickedItem = Appconstant.ORIGIN_CLICKED;
                presenter.askPresenterToOpenPlaceAutoComplete();
                break;

            case R.id.etDestination:
                clickedItem = Appconstant.DESTINATION_CLICKED;
                presenter.askPresenterToOpenPlaceAutoComplete();
                break;

            case R.id.tvWayPoints:
                if (waypoints != null && waypoints.size() < 2) {
                    clickedItem = Appconstant.WAYPOINTS_CLICKED;
                    presenter.askPresenterToOpenPlaceAutoComplete();
                } else {
                    Toast.makeText(this, getString(R.string.prompt_additional_addresses_limit), Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }

    private GetLocationModel createGeoLocationModel() {

        GetLocationModel getLocationModel = new GetLocationModel();
        getLocationModel.setOrigin(new LatLng(origin.getLat(), origin.getLng()));
        getLocationModel.setDestination(new LatLng(destination.getLat(), destination.getLng()));

        if (waypoints != null) {

            List<LatLng> wayPointLatLngList = new ArrayList<>();
            for (Location itemLocation : waypoints) {

                wayPointLatLngList.add(new LatLng(itemLocation.getLat(), itemLocation.getLng()));

            }

            getLocationModel.setWaypoints(wayPointLatLngList);
        }

        return getLocationModel;
    }

    @Override
    public void openPlaceAutoCompleteByGoogle() {
        try {
            findPlace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }
    }

    /**
     * find places as per entered text in AutocompleteFilter
     *
     * @throws GooglePlayServicesRepairableException the google play services repairable exception
     */
    public void findPlace()
            throws GooglePlayServicesRepairableException {

        if (CommonUtil.isNetworkAvailable(this)) {
            try {

                Intent intent =
                        new PlaceAutocomplete
                                .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                .build(this);

                startActivityForResult(intent, 1);

            } catch (GooglePlayServicesNotAvailableException ignored) {
            }
        } else {
            Toast.makeText(GetLocationActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    private void enableButtonNavigation() {

        if (!(tvOrigin.getText().toString().isEmpty()
                || tvDestination.getText().toString().isEmpty())) {
            btnWalkNavigation.setEnabled(true);
            btnWalkNavigation.setClickable(true);

            // show add more waypoints option also
            tvWayPoints.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            // callback from PlaceAutoComplete
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                handleAutoCompleteResult(place.getLatLng().latitude,
                        place.getLatLng().longitude,
                        place.getAddress().toString());


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Log.e("Tag", "user canceled");
            }
        }
    }

    /**
     * @param latitude  - latitude of selected address
     * @param longitude - longitude of selected address
     * @param address   - formatted address of selected address
     */
    private void handleAutoCompleteResult(final Double latitude,
                                          final Double longitude,
                                          final String address) {

        if (clickedItem == Appconstant.ORIGIN_CLICKED) {

            if (origin == null) {
                origin = new Location();
            }
            origin.setLat(latitude);
            origin.setLng(longitude);
            origin.setFormattedAddress(address);

            tvOrigin.setText(address);


        } else if (clickedItem == Appconstant.DESTINATION_CLICKED) {

            if (destination == null) {
                destination = new Location();
            }

            destination.setLat(latitude);
            destination.setLng(longitude);
            destination.setFormattedAddress(address);

            tvDestination.setText(address);

        } else if (clickedItem == Appconstant.WAYPOINTS_CLICKED) {

            Location location = new Location();
            location.setLat(latitude);
            location.setLng(longitude);
            location.setFormattedAddress(address);

            if (waypoints == null) {
                waypoints = new ArrayList<>();
            }

            waypoints.add(location);

            Log.e("wayPoints", "" + waypoints.size());

            additionalAddressesAdapter.addTheAddresses(waypoints);

            tvAdditionalAddresses.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);

        }

        enableButtonNavigation();
    }
}
