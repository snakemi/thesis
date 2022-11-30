package com.example.xpressjeepdashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverLogin extends Activity {
    private Button driverLoginB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_login);

        driverLoginB = findViewById(R.id.button4);

        driverLoginB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openJeepInfo();
            }
        });
    }
    public void openJeepInfo(){
        Intent intent = new Intent(this, JeepInfo.class);
        startActivity(intent);
    }
}