package com.example.xpressjeepdashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ClientSU<clientSUB> extends AppCompatActivity {

    private Button clientSUB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_signup);

        clientSUB = findViewById(R.id.clientSUbutton);

        clientSUB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openClientLogin();
            }
        });
    }
    public void openClientLogin(){
        Intent intent = new Intent(this, ClientLogin.class);
        startActivity(intent);
    }
}
