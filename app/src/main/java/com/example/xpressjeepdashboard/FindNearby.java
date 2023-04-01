package com.example.xpressjeepdashboard;

import android.content.Intent;
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

import java.util.ArrayList;

public class FindNearby extends AppCompatActivity implements OnMapReadyCallback{


    private Button popupButton;
    private GoogleMap nMap;
    ArrayList<LatLng> arrayList = new ArrayList<com.google.android.gms.maps.model.LatLng>();
    public com.google.android.gms.maps.model.LatLng mainuc = new com.google.android.gms.maps.model.LatLng(16.40882770206025, 120.59780492089425);
    public com.google.android.gms.maps.model.LatLng uc = new com.google.android.gms.maps.model.LatLng(16.408742763023348, 120.59805698058953);
    public com.google.android.gms.maps.model.LatLng front = new com.google.android.gms.maps.model.LatLng(16.40799660175582, 120.59824205300256);
    public com.google.android.gms.maps.model.LatLng back = new com.google.android.gms.maps.model.LatLng(16.409306325909583, 120.59727092423904);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_nearby);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        arrayList.add(mainuc);
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

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap){
        nMap = googleMap;
        for (int i =0;i<arrayList.size();i++){
            nMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Marker"));
            nMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            nMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }
    }

    private void openPopupWindow(){
        Intent popupwindow = new Intent(FindNearby.this, PopupWindow.class);
        startActivity(popupwindow);
    }
}
