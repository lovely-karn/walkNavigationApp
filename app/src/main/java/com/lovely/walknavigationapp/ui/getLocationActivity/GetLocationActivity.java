package com.lovely.walknavigationapp.ui.getLocationActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.lovely.walknavigationapp.R;
import com.lovely.walknavigationapp.ui.mapNavigationActivity.MapsNavigationActivity;

public class GetLocationActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton btnWalkNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location2);
    }

    @Override
    protected void onStart() {
        super.onStart();

        init();

        setOnCLickListener(btnWalkNavigation);
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

    /**
     * initialising views
     */
    private void init() {

        btnWalkNavigation = findViewById(R.id.btnWalkNavigation);
    }

    @Override
    public void onClick(final View v) {

        switch (v.getId()) {

            case R.id.btnWalkNavigation:

                Intent intent = new Intent(GetLocationActivity.this, MapsNavigationActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
