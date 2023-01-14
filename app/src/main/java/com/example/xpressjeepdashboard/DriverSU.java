package com.example.xpressjeepdashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DriverSU<driverSUB> extends AppCompatActivity {
    private Button driverSUB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_signup);

        driverSUB = findViewById(R.id.driverSUB);

        driverSUB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openDriverLogin();
            }
        });
    }
    public void openDriverLogin(){
        Intent intent = new Intent(this, DriverLogin.class);
        startActivity(intent);
    }
}