package com.example.xpressjeepdashboard;

import static com.example.xpressjeepdashboard.R.id.opencv_surface_view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.Collections;
import java.util.List;


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