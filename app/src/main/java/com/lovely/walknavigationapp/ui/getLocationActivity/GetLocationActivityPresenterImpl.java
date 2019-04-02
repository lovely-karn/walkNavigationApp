package com.lovely.walknavigationapp.ui.getLocationActivity;

public class GetLocationActivityPresenterImpl implements GetLocationActivityPresenter {


    private GetLocationActivityView view;


    GetLocationActivityPresenterImpl(final GetLocationActivityView view) {

        this.view = view;
    }


    @Override
    public void askPresenterToOpenPlaceAutoComplete() {

        view.openPlaceAutoCompleteByGoogle();
    }
}
