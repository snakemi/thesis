package com.example.xpressjeepdashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    private Button clientLoginButton;
    private Button driverLoginButton;
    private Button clientSUButton;
    private Button driverSUButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clientLoginButton = findViewById(R.id.button);
        driverLoginButton = findViewById(R.id.button2);
        clientSUButton = findViewById(R.id.button3);
        driverSUButton = findViewById(R.id.button5);

        clientLoginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openClientLogin();
            }
        });
        driverLoginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openDriverLogin();
            }
        });
        clientSUButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openClientSU();
            }
        });
        driverSUButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openDriverSU();
            }
        });

    }
    public void openClientLogin(){
        Intent intent = new Intent(this, ClientLogin.class);
        startActivity(intent);
    }
    public void openDriverLogin(){
        Intent intent = new Intent(this, DriverLogin.class);
        startActivity(intent);
    }
    public void openClientSU(){
        Intent intent = new Intent(this, ClientSU.class);
        startActivity(intent);
    }
    public void openDriverSU(){
        Intent intent = new Intent(this, DriverSU.class);
        startActivity(intent);
    }

}