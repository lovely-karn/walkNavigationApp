package com.lovely.walknavigationapp.ui.mapNavigationActivity.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

public class NavigationViewModel extends AndroidViewModel {

    private String wayPointsStringWithPlaceId;


    public NavigationViewModel(@NonNull Application application) {
        super(application);
    }

    public String getWayPointsStringWithPlaceId() {
        return wayPointsStringWithPlaceId;
    }

    public void saveWayPoints(final String wayPointsString) {
        wayPointsStringWithPlaceId = wayPointsString;
    }
}
