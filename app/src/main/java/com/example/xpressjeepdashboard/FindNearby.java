package com.example.xpressjeepdashboard;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FindNearby extends AppCompatActivity implements OnMapReadyCallback{


    private Button popupButton;
    private GoogleMap nMap;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference latitudeDB;
    DatabaseReference longtitudeDB;
    DatabaseReference loc;

    public double latitude;

    public double longtitude;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;


    ArrayList<LatLng> arrayList = new ArrayList<com.google.android.gms.maps.model.LatLng>();
    public com.google.android.gms.maps.model.LatLng mainuc = new com.google.android.gms.maps.model.LatLng(16.40882770206025, 120.59780492089425);
    public com.google.android.gms.maps.model.LatLng uc = new com.google.android.gms.maps.model.LatLng(16.408742763023348, 120.59805698058953);
    public com.google.android.gms.maps.model.LatLng front = new com.google.android.gms.maps.model.LatLng(16.40799660175582, 120.59824205300256);
    public com.google.android.gms.maps.model.LatLng back = new com.google.android.gms.maps.model.LatLng(16.409306325909583, 120.59727092423904);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_nearby);
        firebaseDatabase = FirebaseDatabase.getInstance();
        latitudeDB = firebaseDatabase.getReference("Users/Jeepney/Location/latitude");
        longtitudeDB = firebaseDatabase.getReference("Users/Jeepney/Location/longitude");
        loc = firebaseDatabase.getReference("Users/Jeepney/Location/");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLatitude();
        getLongitude();
        readData(new FirebaseCallback() {
            @Override
            public void onCallback(double val) {
                latitude = val;
            }
        });

        System.out.println(latitude);
        System.out.println(longtitude);

        //com.google.android.gms.maps.model.LatLng driver = new LatLng(latitude,longtitude);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //arrayList.add(driver);
        arrayList.add(uc);
        arrayList.add(front);
        arrayList.add(back);

        popupButton = findViewById(R.id.button10);

        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopupWindow();
            }
        });
        getJeepLoc();
    }
    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap){
        nMap = googleMap;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                nMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            nMap.setMyLocationEnabled(true);
        }

        //LatLng location = new LatLng(latitude, longtitude);
        //nMap.addMarker(new MarkerOptions().position(location).title("Marker"));
        for (int i =0;i<arrayList.size();i++){
            nMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Marker"));
            nMap.animateCamera(CameraUpdateFactory.zoomTo(20.0f));
            nMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }
    }
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                //mCurrLocationMarker = nMap.addMarker(markerOptions);

                //move map camera
                nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            }
        }
    };

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(FindNearby.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        nMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void openPopupWindow(){
        Intent popupwindow = new Intent(FindNearby.this, PopupWindow.class);
        startActivity(popupwindow);
    }
    public void getLatitude() {

        // calling add value event listener method
        // for getting the values from database.
        latitudeDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                double value = snapshot.getValue(Double.class);

                // after getting the value we are setting
                // our value to our text view in below line.
                latitude = value;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(FindNearby.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getLongitude() {

        // calling add value event listener method
        // for getting the values from database.
        longtitudeDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                double value =  snapshot.getValue(Double.class);

                // after getting the value we are setting
                // our value to our text view in below line.
                longtitude = value;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(FindNearby.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getJeepLoc(){
        loc = firebaseDatabase.getReference("Users/Jeepney/Location/");
        loc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                latitude = snapshot.child("latitude").getValue(Double.class);
                longtitude = snapshot.child("longitude").getValue(Double.class);
                LatLng latLng = new LatLng(latitude, longtitude);
                nMap.addMarker(new MarkerOptions().position(latLng).title("ABC123"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void readData(FirebaseCallback firebaseCallback){
        latitudeDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                double value = snapshot.getValue(Double.class);

                // after getting the value we are setting
                // our value to our text view in below line.
                firebaseCallback.onCallback(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(FindNearby.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private interface FirebaseCallback{
        void onCallback(double val);
    }

}
