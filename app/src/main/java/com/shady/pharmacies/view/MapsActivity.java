package com.shady.pharmacies.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shady.pharmacies.R;
import com.shady.pharmacies.model.GPSTracker;
import com.shady.pharmacies.model.Place;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Place place;
    private GPSTracker gps;
    private double latitude;
    private double longitude;

    public static Intent getIntent(Context context, Place place, double lat, double lng){
        return new Intent(context, MapsActivity.class)
                .putExtra("place", place);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        place = (Place) getIntent().getSerializableExtra("place");

        gps=new GPSTracker(this);
        if (gps.canGetLocation())
        {
            latitude=gps.getLatitude();
            longitude= gps.getLongitude();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude, longitude), 17
        ));

        googleMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).
                title("my Location").
                anchor(0.0f, 1.0f).
                position(new LatLng(latitude, longitude)));

        mMap.addMarker(new MarkerOptions()
                .title(place.getName())
                .position(
                        new LatLng(place.getLatitude(), place.getLongitude()))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                .snippet(place.getVicinity()));
    }
}
