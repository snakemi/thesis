package com.example.xpressjeepdashboard;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.Nullable;


public class ViewMap extends AppCompatActivity implements OnMapReadyCallback {
    private Button findNearbyBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.view_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
        findNearbyBtn = findViewById(R.id.button8);

        findNearbyBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openNearby();
            }
        });



    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap){
    }
    public void openNearby(){
        Intent intent = new Intent(this, FindNearby.class);
        startActivity(intent);
    }
}