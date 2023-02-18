package com.example.xpressjeepdashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverLogin extends Activity {
    private FirebaseAuth mAuth;
    private EditText EmailAddress, Password;
    private Button driverLoginB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_login);
        mAuth = FirebaseAuth.getInstance();
        EmailAddress = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);
        driverLoginB = findViewById(R.id.button4);

        driverLoginB.setOnClickListener(new View.OnClickListener(){

            @Override
            //public void onClick(View view) {
            //    openJeepInfo();
            //}
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String user = EmailAddress.getText().toString().trim();
        String pass = Password.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DriverLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    openJeepInfo();
                }
                else{
                    Toast.makeText(DriverLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openJeepInfo(){
        Intent intent = new Intent(this, JeepInfo.class);
        startActivity(intent);
    }
}