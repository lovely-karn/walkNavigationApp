package com.lovely.walknavigationapp.ui.getLocationActivity.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class GetLocationModel implements Parcelable {

    private LatLng origin, destination;
    private List<LatLng> waypoints;

    public LatLng getOrigin() {
        return origin;
    }

    public void setOrigin(LatLng origin) {
        this.origin = origin;
    }

    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    public List<LatLng> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<LatLng> waypoints) {
        this.waypoints = waypoints;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.origin, flags);
        dest.writeParcelable(this.destination, flags);
        dest.writeTypedList(this.waypoints);
    }

    public GetLocationModel() {
    }

    protected GetLocationModel(Parcel in) {
        this.origin = in.readParcelable(LatLng.class.getClassLoader());
        this.destination = in.readParcelable(LatLng.class.getClassLoader());
        this.waypoints = in.createTypedArrayList(LatLng.CREATOR);
    }

    public static final Parcelable.Creator<GetLocationModel> CREATOR = new Parcelable.Creator<GetLocationModel>() {
        @Override
        public GetLocationModel createFromParcel(Parcel source) {
            return new GetLocationModel(source);
        }

        @Override
        public GetLocationModel[] newArray(int size) {
            return new GetLocationModel[size];
        }
    };
}
