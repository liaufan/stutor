package com.example.stutor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Random;

public class Register extends AppCompatActivity {
    EditText otp1, otp2, otp3, otp4, otp5, otp6, phone;
    Button next_button, requestOTPButton;
    FirebaseDatabase firebase;
    String IC, password;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);
        phone = findViewById(R.id.phone);
        next_button = findViewById(R.id.next_button);
        requestOTPButton = findViewById(R.id.requestOTPButton);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        firebase = FirebaseDatabase.getInstance("https://tutor-agent-fe351.firebaseio.com/");
        IC = getIntent().getStringExtra("IC");
        password = getIntent().getStringExtra("password");

        requestOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                if(phone.getText().toString().equals("")){
                    spinner.setVisibility(View.GONE);
                    Toast.makeText(Register.this, "Invalid phone number.",Toast.LENGTH_LONG).show();
                }else {
                    firebase.getReference("Tutors").orderByChild("phone").equalTo(phone.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                spinner.setVisibility(View.GONE);
                                Toast.makeText(Register.this, "This phone is registered", Toast.LENGTH_LONG).show();
                            } else {
                                Random random = new Random();
                                String otp = "";
                                for(int i=0;i<6;i++){
                                    int randomInt = random.nextInt(10);
                                    otp += randomInt;
                                }
                                firebase.getReference("OTP").child(phone.getText().toString()).setValue(otp);
                                spinner.setVisibility(View.GONE);
                                Toast.makeText(Register.this, "OTP: "+otp, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(otp1.getText().toString().length() == 1){
                    otp2.requestFocus();
                    otp2.setSelection(otp2.getText().length());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(otp1.getText().toString().length() > 1){
                    otp2.setText(otp1.getText().toString().substring(1));
                    otp1.setText(otp1.getText().toString().substring(0,1));
                    otp2.requestFocus();
                    otp2.setSelection(otp2.getText().length());
                }
            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(otp2.getText().toString().length() == 1){
                    otp3.requestFocus();
                    otp3.setSelection(otp3.getText().length());
                }else if(otp2.getText().toString().length() == 0){
                    otp1.requestFocus();
                    otp1.setSelection(otp1.getText().length());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(otp2.getText().toString().length() > 1){
                    otp3.setText(otp2.getText().toString().substring(1));
                    otp2.setText(otp2.getText().toString().substring(0,1));
                    otp3.requestFocus();
                    otp3.setSelection(otp3.getText().length());
                }
            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(otp3.getText().toString().length() == 1){
                    otp4.requestFocus();
                    otp4.setSelection(otp4.getText().length());
                }else if(otp3.getText().toString().length() == 0){
                    otp2.requestFocus();
                    otp2.setSelection(otp2.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otp3.getText().toString().length() > 1){
                    otp4.setText(otp3.getText().toString().substring(1));
                    otp3.setText(otp3.getText().toString().substring(0,1));
                    otp4.requestFocus();
                    otp4.setSelection(otp4.getText().length());
                }

            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(otp4.getText().toString().length() == 1){
                    otp5.requestFocus();
                    otp5.setSelection(otp5.getText().length());
                }else if(otp4.getText().toString().length() == 0){
                    otp3.requestFocus();
                    otp3.setSelection(otp3.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otp4.getText().toString().length() > 1){
                    otp5.setText(otp4.getText().toString().substring(1));
                    otp4.setText(otp4.getText().toString().substring(0,1));
                    otp5.requestFocus();
                    otp5.setSelection(otp5.getText().length());
                }

            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(otp5.getText().toString().length() == 1){
                    otp6.requestFocus();
                    otp6.setSelection(otp6.getText().length());
                }else if(otp5.getText().toString().length() == 0){
                    otp4.requestFocus();
                    otp4.setSelection(otp4.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otp5.getText().toString().length() > 1){
                    otp6.setText(otp5.getText().toString().substring(1));
                    otp5.setText(otp5.getText().toString().substring(0,1));
                    otp6.requestFocus();
                    otp6.setSelection(otp6.getText().length());
                }

            }
        });

        otp6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(otp6.getText().toString().length() == 0){
                    otp5.requestFocus();
                    otp5.setSelection(otp5.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otp6.getText().toString().length() > 1){
                    otp6.setText(otp6.getText().toString().substring(0,1));
                    otp6.setSelection(otp6.getText().length());
                }

            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                if(otp1.getText().toString().equals("") ||
                        otp2.getText().toString().equals("") ||
                        otp3.getText().toString().equals("") ||
                        otp4.getText().toString().equals("") ||
                        otp5.getText().toString().equals("") ||
                        otp6.getText().toString().equals("") ){

                }else {
                    final String otp = otp1.getText().toString() +
                            otp2.getText().toString() +
                            otp3.getText().toString() +
                            otp4.getText().toString() +
                            otp5.getText().toString() +
                            otp6.getText().toString();
                    firebase.getReference("OTP").child(phone.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String response = snapshot.getValue(String.class);
                            if(response.equals(otp)){
                                spinner.setVisibility(View.GONE);
                                openDetail1(phone.getText().toString());
                            } else {
                                spinner.setVisibility(View.GONE);
                                Toast.makeText(Register.this, "Incorrect OTP.",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }


            }
        });
    }

    public void openDetail1(String phone){
        Intent intent = new Intent(this, Detail_1.class);
        intent.putExtra("IC", IC);
        intent.putExtra("password", password);
        intent.putExtra("phone", phone);
        startActivity(intent);
    }
}