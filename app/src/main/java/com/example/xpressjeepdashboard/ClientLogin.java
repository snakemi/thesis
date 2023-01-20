package com.example.xpressjeepdashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClientLogin extends Activity {
    private Button clientLoginB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_login);

        clientLoginB = findViewById(R.id.button4);

        clientLoginB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openViewMap();
            }
        });
    }
    public void openViewMap(){
        Intent intent = new Intent(this, ViewMap.class);
        startActivity(intent);
    }
}
