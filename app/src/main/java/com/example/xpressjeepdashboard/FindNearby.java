package com.example.xpressjeepdashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FindNearby extends AppCompatActivity {
    private Button popupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_nearby);

        popupButton = findViewById(R.id.button10);

        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopupWindow();
            }
        });

    }
    private void openPopupWindow(){
        Intent popupwindow = new Intent(FindNearby.this, PopupWindow.class);
        startActivity(popupwindow);
    }
}

