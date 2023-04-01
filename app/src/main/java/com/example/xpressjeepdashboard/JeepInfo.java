package com.example.xpressjeepdashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JeepInfo extends AppCompatActivity {
    private Button cameraViews;
    // creating a variable for
    // our Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference passengerCount;
    DatabaseReference licensePlate;
    // variable for Text view.
    private TextView passengerText;
    private TextView licensePlateText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jeep_info);

        cameraViews = findViewById(R.id.cameraView);
        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        passengerCount = firebaseDatabase.getReference("Users/Jeepney/Passenger Count");
        licensePlate = firebaseDatabase.getReference("Users/Jeepney/Plate Number");


        // below line is used to get
        // reference for our database.

        // initializing our object class variable.
        passengerText = findViewById(R.id.textView14);
        licensePlateText = findViewById(R.id.textView12);

        // calling method
        // for getting data.
        getPassengerCount();
        getLicensePlate();
        cameraViews.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


            }
        });
    }
    private void getPassengerCount() {

        // calling add value event listener method
        // for getting the values from database.
        passengerCount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                long value = snapshot.getValue(long.class);

                // after getting the value we are setting
                // our value to our text view in below line.
                passengerText.setText(String.valueOf(value));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(JeepInfo.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getLicensePlate() {

        // calling add value event listener method
        // for getting the values from database.
        licensePlate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                String value = snapshot.getValue(String.class);

                // after getting the value we are setting
                // our value to our text view in below line.
                licensePlateText.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(JeepInfo.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}