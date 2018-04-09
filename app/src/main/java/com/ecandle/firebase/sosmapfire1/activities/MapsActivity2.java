package com.ecandle.firebase.sosmapfire1.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ecandle.firebase.sosmapfire1.R;
import com.ecandle.firebase.sosmapfire1.services.TrackGPS;
import com.ecandle.firebase.sosmapfire1.tasks.GetNearbyPlacesData;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback, GeoQueryEventListener , GoogleMap.OnCameraMoveListener {

    private String LOG_TAG = MapsActivity2.class.getSimpleName();
    private GoogleMap mMap;
    double latitude;
    double longitude;
    private static final int PROXIMITY_RADIUS = 10000;
    private static final int PERMISSION_CODE_1 = 23;
    private static final String WEB_SERVICE_API_KEY = "AIzaSyCgTKjgxxNCd9erFCbrHlhELeOMFhWSOdk";
    private static final GeoLocation INITIAL_CENTER = new GeoLocation(-34, 151);
    private static final int INITIAL_ZOOM_LEVEL = 14;

    private static final String GEO_FIRE_DB = "https://publicdata-transit.firebaseio.com";
    private static final String GEO_FIRE_REF = "incidente_location";

    //GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    //LocationRequest mLocationRequest;
    private TrackGPS gps;
    private String mLatitude;
    private String mLongitude;
    private Marker mCurrLocationMarker;
    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;
    private Circle searchCircle;
    private GeoFire geoFire;
    private GeoQuery geoQuery;
    private Map<String,Marker> markers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps4);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        LatLng latLngCenter = new LatLng(-34, 151);
//        searchCircle = mMap.addCircle(new CircleOptions().center(latLngCenter).radius(1000));
//        searchCircle.setFillColor(Color.argb(66, 255, 0, 255));
//        searchCircle.setStrokeColor(Color.argb(66, 0, 0, 0));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngCenter, INITIAL_ZOOM_LEVEL));



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

        // Add a marker in Sydney and move the camera

//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        getCurrentLocation();
        LatLng latLngCenter = new LatLng(gps.latitude, gps.latitude);
        searchCircle = mMap.addCircle(new CircleOptions().center(latLngCenter).radius(1000));
        searchCircle.setFillColor(Color.argb(66, 255, 0, 255));
        searchCircle.setStrokeColor(Color.argb(66, 0, 0, 0));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngCenter, INITIAL_ZOOM_LEVEL));

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            mMap.setMyLocationEnabled(true);
        }
        ImageButton btnRestaurant = (ImageButton) findViewById(R.id.btnRestaurant);
        btnRestaurant.setOnClickListener(new View.OnClickListener() {
            String Restaurant = "incidentes";
            @Override
            public void onClick(View v) {
                Log.d("onClick", "ImageButton is Clicked");
                //mMap.clear();
//                String url = getUrl(gps.latitude, gps.longitude, Restaurant);
//                Object[] DataTransfer = new Object[2];
//                DataTransfer[0] = mMap;
//                DataTransfer[1] = url;
//                Log.d("onClick", url);
//                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
//                getNearbyPlacesData.execute(DataTransfer);
//                Toast.makeText(MapsActivity2.this,"Nearby Incidentes", Toast.LENGTH_LONG).show();
                //FirebaseOptions options = new FirebaseOptions.Builder().setApplicationId("geofire").setDatabaseUrl(GEO_FIRE_DB).build();
                //FirebaseApp app = FirebaseApp.initializeApp(this, options);


                createMarkers();
            }
        });

        ImageButton btnBank = (ImageButton) findViewById(R.id.btnBank);
        btnBank.setOnClickListener(new View.OnClickListener() {
            String Bank = "bank";
            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(gps.latitude, gps.longitude, Bank);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(MapsActivity2.this,"Nearby Hospitals", Toast.LENGTH_LONG).show();
            }
        });

        ImageButton btnAtm = (ImageButton) findViewById(R.id.btnAtm);
        btnAtm.setOnClickListener(new View.OnClickListener() {
            String Atm = "atm";
            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                String url = getUrl(gps.latitude, gps.longitude, Atm);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(MapsActivity2.this,"Nearby Schools", Toast.LENGTH_LONG).show();
            }
        });

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(GEO_FIRE_REF);
        // setup GeoFire
        geoFire = new GeoFire(mFirebaseDatabase);
        // radius in km
        GeoLocation initial_center = new GeoLocation(gps.latitude, gps.longitude);
        geoQuery = geoFire.queryAtLocation(initial_center, 1);

        // setup markers
        markers = new HashMap<String, Marker>();

        //showMarkers();

        mMap.setOnCameraMoveListener(this);

    }

    private void getCurrentLocation(){
        gps = new TrackGPS(this);
        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //requestpermisions();
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, Manifest.permission.ACCESS_FINE_LOCATION, Toast.LENGTH_SHORT).show();

                    requestpermisions();

                } else {

                    // No explanation needed, we can request the permission.

                    requestpermisions();

                }
            }

            if(gps.canGetLocation()){
                Log.d("onLocationChanged", "entered");
                mLatitude = String.valueOf(gps.latitude);
                mLongitude = String.valueOf(gps.longitude);
                Toast.makeText(this,"Longitude:"+ mLongitude +"\nLatitude:"+mLatitude, Toast.LENGTH_SHORT).show();
            }
            else {

                gps.showSettingsAlert();
            }

        } else {
            if(gps.canGetLocation()){

                mLatitude = String.valueOf(gps.latitude);
                mLongitude = String.valueOf(gps.longitude);
                Toast.makeText(this,"Longitude:"+ mLongitude +"\nLatitude:"+mLatitude, Toast.LENGTH_SHORT).show();

            }
            else
            {

                gps.showSettingsAlert();
            }
        }
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker

        LatLng latLng = new LatLng(gps.latitude, gps.longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        Toast.makeText(MapsActivity2.this,"Your Current Location", Toast.LENGTH_LONG).show();

        Log.d(LOG_TAG,"getCurrentLocation:"+String.format("latitude:%.3f longitude:%.3f",latitude,longitude));

//                //stop location updates
//                if (mGoogleApiClient != null) {
//                    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//                    Log.d("onLocationChanged", "Removing Location Updates");
//                }
        Log.d(LOG_TAG,"getCurrentLocation:"+ "Exit");

    }
    public void requestpermisions() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE_1);

    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + WEB_SERVICE_API_KEY);
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    private void showMarkers(){
        getCurrentLocation();
        GeoLocation initial_center = new GeoLocation(gps.latitude, gps.longitude);
        geoQuery = geoFire.queryAtLocation(initial_center, 1);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                //retrieve data
                LatLng latLng = new LatLng(location.latitude, location.longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("tipoIncidente");
                markerOptions.snippet("descripcionIncidente");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                mMap.addMarker(markerOptions);
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }

        });

    }

    private void createMarkers() {
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("incidente");
        mFirebaseDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Double lat = 0.0,lon =  0.0;
                String tipoIncidente = "";
                String descripcionIncidente = "";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if (postSnapshot.getKey().equals("latitud")){
                        String latitud = postSnapshot.getValue(String.class).toString();
                        if (!latitud.equals("")) lat = Double.parseDouble(latitud);
                    }
                    if (postSnapshot.getKey().equals("longitud")){
                        String longitud = postSnapshot.getValue(String.class).toString();
                        if (!longitud.equals("")) lon = Double.parseDouble(longitud);
                    }
                    if (postSnapshot.getKey().equals("tipo_incidente")){
                        tipoIncidente = postSnapshot.getValue(String.class).toString();
                    }
                    if (postSnapshot.getKey().equals("descripcion_incidente")){
                        descripcionIncidente = postSnapshot.getValue(String.class).toString();
                    }
                    if (!lat.equals(0.0) && !lon.equals(0.0)) {
                        LatLng latLng = new LatLng(lat, lon);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(tipoIncidente);
                        markerOptions.snippet(descripcionIncidente);
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                        mMap.addMarker(markerOptions);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private double zoomLevelToRadius(double zoomLevel) {
        // Approximation to fit circle into view
        return 16384000/Math.pow(2, zoomLevel);
    }


    @Override
    protected void onStop() {
        super.onStop();
        // remove all event listeners to stop updating in the background
        geoQuery.removeAllListeners();
        for (Marker marker: markers.values()) {
            marker.remove();
        }
        markers.clear();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // add an event listener to start updating locations again
        //geoQuery.addGeoQueryEventListener(this);
        showMarkers();
    }

    @Override
    public void onKeyEntered(String key, GeoLocation location) {
        // Add a new marker to the map
        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.latitude, location.longitude)));
        markers.put(key, marker);
    }

    @Override
    public void onKeyExited(String key) {
        // Remove any old marker
        Marker marker = markers.get(key);
        if (marker != null) {
            marker.remove();
            markers.remove(key);
        }
    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        // Move the marker
        Marker marker = markers.get(key);
        if (marker != null) {
            animateMarkerTo(marker, location.latitude, location.longitude);
        }
    }

    @Override
    public void onGeoQueryReady() {

    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("There was an unexpected error querying GeoFire: " + error.getMessage())
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

//    @Override
//    public void onCameraChange(CameraPosition cameraPosition) {
//        // Update the search criteria for this geoQuery and the circle on the map
//        LatLng center = cameraPosition.target;
//        double radius = zoomLevelToRadius(cameraPosition.zoom);
//        searchCircle.setCenter(center);
//        searchCircle.setRadius(radius);
//        geoQuery.setCenter(new GeoLocation(center.latitude, center.longitude));
//        // radius in km
//        geoQuery.setRadius(radius/1000);
//    }

    @Override
    public void onCameraMove() {
        // Update the search criteria for this geoQuery and the circle on the map
        //LatLng center = getCacameraPosition.target;
        if (mMap != null){
            CameraPosition currentCameraPosition = mMap.getCameraPosition();
            //LatLng center = currentCameraPosition.target;
            //getCurrentLocation();
            //if(gps.canGetLocation()){
                LatLng center = new LatLng(gps.latitude, gps.longitude);
                double radius = zoomLevelToRadius(currentCameraPosition.zoom);
                searchCircle.setCenter(center);
                searchCircle.setRadius(radius);
                geoQuery.setCenter(new GeoLocation(center.latitude, center.longitude));
                // radius in km
                geoQuery.setRadius(radius/1000);
            //}
        }
    }

    // Animation handler for old APIs without animation support
    private void animateMarkerTo(final Marker marker, final double lat, final double lng) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long DURATION_MS = 3000;
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final LatLng startPosition = marker.getPosition();
        handler.post(new Runnable() {
            @Override
            public void run() {
                float elapsed = SystemClock.uptimeMillis() - start;
                float t = elapsed/DURATION_MS;
                float v = interpolator.getInterpolation(t);

                double currentLat = (lat - startPosition.latitude) * v + startPosition.latitude;
                double currentLng = (lng - startPosition.longitude) * v + startPosition.longitude;
                marker.setPosition(new LatLng(currentLat, currentLng));

                // if animation is not finished yet, repeat
                if (t < 1) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

}


