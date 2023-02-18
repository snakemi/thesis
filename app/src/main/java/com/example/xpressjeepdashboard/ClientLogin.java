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

public class ClientLogin extends Activity {
    private FirebaseAuth mAuth;
    private EditText EmailAddress, Password;
    private Button clientLoginB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_login);
        mAuth = FirebaseAuth.getInstance();
        EmailAddress = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);


        clientLoginB = findViewById(R.id.button4);

        clientLoginB.setOnClickListener(new View.OnClickListener(){

            @Override
            //public void onClick(View view) {
//                openViewMap();
//            }
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
                    Toast.makeText(ClientLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    openViewMap();
                }
                else{
                    Toast.makeText(ClientLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openViewMap(){
        Intent intent = new Intent(this, ViewMap.class);
        startActivity(intent);
    }
}
