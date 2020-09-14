package com.example.stutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class Login extends AppCompatActivity {
    Button register_button, login_button;
    EditText IC, password;
    FirebaseDatabase firebase;
    ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register_button = findViewById(R.id.register_button);
        login_button = findViewById(R.id.login_button);
        IC = findViewById(R.id.IC);
        password = findViewById(R.id.password);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        firebase = FirebaseDatabase.getInstance("https://tutor-agent-fe351.firebaseio.com/");
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            startActivity(new Intent(this, MainActivity.class));
        }


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                try {
                    openRegisterActivity();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                login();
            }
        });
    }

    public void login() {
        final SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        if(IC.getText().toString().equals("") || password.getText().toString().equals("")){
            spinner.setVisibility(View.GONE);
            Toast.makeText(this, "Please enter your IC number and select a password.", Toast.LENGTH_LONG).show();
        } else if (password.getText().toString().length()<8){
            spinner.setVisibility(View.GONE);
            Toast.makeText(this, "The password has to be at least 8 characters.", Toast.LENGTH_LONG).show();
        } else if (IC.getText().toString().length()!=12) {
            spinner.setVisibility(View.GONE);
            Toast.makeText(this, "The IC number has to be 12 digits.", Toast.LENGTH_LONG).show();
        } else {
            DatabaseReference dataRef = firebase.getReference("Tutors").child(IC.getText().toString());
            dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        Map<String,String> response = (Map)snapshot.getValue();
                        String storedPassword = response.get("password");
                        String status = response.get("status");
                        if(!storedPassword.equals(password.getText().toString())){
                            spinner.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "Wrong Password.", Toast.LENGTH_LONG).show();
                        } else if (!status.equals("approve")){
                            spinner.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "Account is not active.", Toast.LENGTH_LONG).show();
                        } else {
                            spinner.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_LONG).show();
                            sp.edit().putString("ic",IC.getText().toString()).apply();
                            sp.edit().putBoolean("logged",true).apply();
                            startActivity(new Intent(Login.this, MainActivity.class));
                        }
                    } else {
                        spinner.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "This IC is unregistered, please register.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    public void openRegisterActivity() throws IOException, KeyManagementException, NoSuchAlgorithmException, InterruptedException {

        if(IC.getText().toString().equals("") || password.getText().toString().equals("")){
            spinner.setVisibility(View.GONE);
            Toast.makeText(this, "Please enter your IC number and select a password.", Toast.LENGTH_LONG).show();
        } else if (password.getText().toString().length()<8){
            spinner.setVisibility(View.GONE);
            Toast.makeText(this, "The password has to be at least 8 characters.", Toast.LENGTH_LONG).show();
        } else if (IC.getText().toString().length()!=12) {
            spinner.setVisibility(View.GONE);
            Toast.makeText(this, "The IC number has to be 12 digits.", Toast.LENGTH_LONG).show();
        } else {

            DatabaseReference dataRef = firebase.getReference("Tutors").child(IC.getText().toString());

            dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        spinner.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "This IC is registered, please login.", Toast.LENGTH_LONG).show();
                    } else {
                        spinner.setVisibility(View.GONE);
                        Intent intent = new Intent(Login.this, Register.class);
                        intent.putExtra("IC", IC.getText().toString());
                        intent.putExtra("password", password.getText().toString());
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    spinner.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Database Error.", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}