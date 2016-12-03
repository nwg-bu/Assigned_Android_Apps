package com.example.sse.minisink;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
//import android.location.LocationManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    final static String MYTAG = "BOSTON";

    private GoogleMap mMap;
    private CameraUpdate camUpdate;

    private Menu mapsMenu;
    boolean MyLocationEnabled = false;
    boolean MapZoomEnabled = false;
    private Button btnPinLatLong;
    private EditText edtLat;
    private EditText edtLon;

    private Button btnGeoLocate;
    private EditText edtGeoLocation;
    private ArrayList<LatLng> alLatLngs;

    private Button btnCurrentLocation;
    private LocationManager lm;
    private LocationListener ll;

    private Button btnStart, btnStop, btnDistance;
    private float distance = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.i(MYTAG, "onCreate Called.");

        btnPinLatLong = (Button) findViewById(R.id.btnPinLatLong);
        edtLat = (EditText) findViewById(R.id.edtLat);
        edtLon = (EditText) findViewById(R.id.edtLong);

        btnGeoLocate = (Button) findViewById(R.id.btnGeoLocate);
        edtGeoLocation = (EditText) findViewById(R.id.edtGeoLocation);
        btnStart = (Button)findViewById(R.id.btnStart);
        btnStop = (Button)findViewById(R.id.btnStop);
        btnDistance = (Button)findViewById(R.id.btnDistance);

        alLatLngs = new ArrayList<LatLng>();  //creating an array to store a set of Lat/Longs. (Part of in class activity for later) :)

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll = new MyLocationListener();

        String value;
        Intent i = getIntent();


            if (i.hasExtra("GeneralIntentData"))
                value = i.getStringExtra("GeneralIntentData");

            //-----------Create Event Handlers  ----------------------------------//
           //TODO:  added this to drop initial pin and begin updating location every 5000 miliseconds (5 seconds)
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 0.0f, ll);
                        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 0.0f, ll);
                    }catch(SecurityException e){
                        Toast.makeText(MapsActivity.this, "Test toast", Toast.LENGTH_LONG).show();
                    }
                }
            });
            //TODO:  added this to pin the final stopping location and stop updating location
            btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                    // pin final stopping location
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 0.0f, ll);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 0.0f, ll);
                    // stop location updates
                    lm.removeUpdates(ll);
                }
                catch(SecurityException e){

                }
                }
            });

            //TODO:  added this to calc the distance traveled and toast the result
            btnDistance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MapsActivity.this, "Total distance traveled: " + distance + " meters)", Toast.LENGTH_SHORT).show();
                }
            });

            btnPinLatLong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float lat, lon;
                    lat = Float.parseFloat(edtLat.getText().toString());
                    lon = Float.parseFloat(edtLon.getText().toString());
                    gotoLocation(lat, lon, 12);
                }
            });

            btnGeoLocate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strLoc = edtGeoLocation.getText().toString();
                    List<Address> addresses;
                    try {
                        Geocoder gc = new Geocoder(MapsActivity.this);
                        try {
    //                        addresses = gc.getFromLocationName("Eifel Tower", 1);  //address, max number of address resolutions.
                            addresses = gc.getFromLocationName(strLoc, 1);  //address, max number of address resolutions.a
                            strLoc = addresses.get(0).getLocality();  //Retrieving the "known" name from Location Services (might be different than the string we submitted.)
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }

                        double lat = addresses.get(0).getLatitude();
                        double lon = addresses.get(0).getLongitude();

                        edtLat.setText(new Double(lat).toString());
                        edtLon.setText(new Double(lon).toString());

                        gotoLocation((float) lat, (float) lon, 12, strLoc);

                    } catch (SecurityException e) {
                        e.printStackTrace();
                        Toast.makeText(MapsActivity.this, " GPS not Setup. ", Toast.LENGTH_LONG);
                    }
                }

            });
//----------------------------------------------------------------------//

////*******Setup the Location Manager and the Location Listener********//
//        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        ll = new MyLocationListener();
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 0.0f, ll);    //Question, is there a better place then onCreate for this?  Yes, think about when to turn services on/off when app goes in background...
//        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 0.0f, ll);
////******************************************************************//

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    //----this is what happens when a language doesn(Java) 't have default parms! icky... --------//
    void gotoLocation(float aLat, float aLong) {
        gotoLocation(aLat, aLong, 12, "BU Headquarters");
    }

    void gotoLocation(float aLat, float aLong, int aZoom) {
        gotoLocation(aLat, aLong, aZoom, "BU Headquarters");
    }

    void gotoLocation(float aLat, float aLong, String aStrLoc) {
        gotoLocation(aLat, aLong, 12, aStrLoc);
    }

    void gotoLocation(float aLat, float aLong, int aZoom, String aStrLoc) {
        LatLng latLng = new LatLng(aLat, aLong);
        camUpdate = CameraUpdateFactory.newLatLngZoom(latLng, aZoom);
        mMap.animateCamera(camUpdate);
        mMap.addMarker(new MarkerOptions().position(latLng).title(aStrLoc));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));  //don't call zoomTo, buggy.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        mapsMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.MapTypeNormal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.MapTypeHybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.MapTypeSatellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.MapTypeTerrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.MapZoomIn:
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                break;
            case R.id.MapZoomOut:
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
                break;
            case R.id.MapToggleMyLocation:
                MenuItem myLocationMenuItem = mapsMenu.findItem(R.id.MapToggleMyLocation);  //ref: http://stackoverflow.com/questions/7066657/android-how-to-dynamically-change-menu-item-text-outside-of-onoptionsitemssele
                if (MyLocationEnabled) {
                    myLocationMenuItem.setTitle("Enable My Location");
                } else {
                    myLocationMenuItem.setTitle("Disable My Location");
                }
                MyLocationEnabled = !MyLocationEnabled;
                try {
                    mMap.setMyLocationEnabled(MyLocationEnabled);
                }catch(SecurityException e){

                }
                break;

            case R.id.MapZoomEnabled:
                MenuItem MapZoomMenuItem = mapsMenu.findItem(R.id.MapZoomEnabled);  //ref: http://stackoverflow.com/questions/7066657/android-how-to-dynamically-change-menu-item-text-outside-of-onoptionsitemssele
                if (MapZoomEnabled) {
                    MapZoomMenuItem.setTitle("Enable Zoom Controls");
                } else {
                    MapZoomMenuItem.setTitle("Disable Zoom Controls");
                }
                MapZoomEnabled = !MapZoomEnabled;
                mMap.getUiSettings().setZoomControlsEnabled(MapZoomEnabled);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(MYTAG, "onResume Called, Requesting Location Updates");
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2500L, 0.0f, ll);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2500L, 0.0f, ll);
        }catch(SecurityException e){

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(MYTAG, "onPause Called, Disabling Location Updates (it's the nice thing to do. :)");
        try {
            lm.removeUpdates(ll);
        }catch (SecurityException e){

        }
    }

    //INNER CLASS.
    class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            float lat = (float) location.getLatitude();
            float lon = (float) location.getLongitude();

            float[] resultsReturn = {-1.0f};
            Location lastLocation = null;
            try {
                lastLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }catch (SecurityException e){

            }

            if (lastLocation != null) {  //ref: http://stackoverflow.com/questions/13814928/getlastknownlocation-from-network-provider-on-phone-returns-null
                LatLng prevLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                location.distanceBetween(prevLatLng.latitude, prevLatLng.longitude, lat, lon, resultsReturn);
            }

            LatLng curLatLng = new LatLng(lat,lon);
            alLatLngs.add(curLatLng);
            distance += resultsReturn[0];
            Toast.makeText(MapsActivity.this, "New Location: " + lat  + ", " + lon +" (moved = " + resultsReturn[0] + " meters)", Toast.LENGTH_SHORT).show();

            gotoLocation(lat, lon, 15);
            Log.i(MYTAG, "Location Has Changed. (" + lat + ", " + lon + ")");
            Log.i(MYTAG, "Distance moved (in meters) = " + resultsReturn[0]);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(MYTAG, "Location Provider Status Has Changed. " + provider);
        }

        public void onProviderEnabled(String provider) {
            Log.i(MYTAG, "Location Provider Has been DISabled. " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i(MYTAG, "Location Provider Has been ENabled. " + provider);
        }
    }


//*********** AUTOGENERATED CODE DOWN HERE... ***********//
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
        Log.i(MYTAG, "onMapReady Called.");
        Toast.makeText(MapsActivity.this, " onMapReady ", Toast.LENGTH_LONG);

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(MYTAG, "onStart Called.");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sse.minisink/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(MYTAG, "onStop Called.");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sse.minisink/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
//************ END AUTOGENERATED CODE ************//
}