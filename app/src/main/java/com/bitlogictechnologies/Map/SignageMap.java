package com.bitlogictechnologies.Map;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.bitlogictechnologies.signcapture.Page1;
import com.bitlogictechnologies.signcapture.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.MyLocationTracking;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationSource;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.bitlogictechnologies.signcapture.R.id.map;

/**
 * Created by Mike-berg on 12-07-2017.
 */

public class SignageMap extends AppCompatActivity implements LocationListener {
    TextView test;
    private LocationManager locationManager;
    String mprovider;
    private MapView mapView;
    private MapboxMap myMap;
    Location location;
    //double lon, lat;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getApplicationContext(), getString(R.string.access_token));
        setContentView(R.layout.map_ui);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        //Location variables
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        mprovider = locationManager.getBestProvider(criteria, false);
        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);

            if (location != null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
        }

        test=(TextView) findViewById(R.id.tv_test);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap myMap) {

                //lon=location.getLongitude();
                //lat=location.getLatitude();
                // set MapBox streets style.
                mapView.setStyleUrl(Style.SATELLITE_STREETS);

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(-0.185946666666666,5.562515)) // Sets the center of the map to Chicago
                        .zoom(11)                            // Sets the zoom
                        .build();

                myMap.moveCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));

                //Add a marker to the map in the specified location
                myMap.addMarker(new MarkerOptions()
                        .position(new LatLng(-0.185946666666666,5.562515))
                        .title("Loc Tag")
                        .snippet("Welcome to Loc Marker."));

               // Toast.makeText(getApplicationContext(),"The Cord are "+lon +" "+lat,Toast.LENGTH_SHORT);


            }
        });

        //Retrieve data from previous activity
        Bundle page1_data = getIntent().getExtras();
        String company_name = page1_data.getString("company_name");
        String phone = page1_data.getString("phone");
        String sign_type = page1_data.getString("sign_type");

        Toast.makeText(getApplicationContext(), "The data is " + company_name + " " + phone + " " + sign_type, Toast.LENGTH_LONG).show();


        floatingActionButton = (FloatingActionButton) findViewById(R.id.location_toggle_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });




    }



    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    

    private void loadMapView() {
        Intent intent = new Intent(this, SignageMap.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void loadInt() {
        Intent intent = new Intent(this, Page1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    @Override
    public void onLocationChanged(Location location) {
        String slon = String.valueOf(location.getLongitude());
       String slat = String.valueOf(location.getLatitude());
        //String lat=String.valueOf(myMap.getMyLocation().getLatitude()) ;

        test.setText(slon+ "  "+slat);

    }

    public void getActualLocation(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + " " + obj.getLocality();
            String addre=add;
            //TextView txt_location=(TextView)findViewById(R.id.txt_location);
            //txt_location.setText(addre);
            Toast.makeText(this,"The Current Location is "+ addre, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void get_location(View view){
        getActualLocation(location);

    }

}


