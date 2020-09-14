package com.example.stutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

public class Detail_1 extends AppCompatActivity {
    Spinner gender_spinner;
    Button next_button;
    EditText email, fullName, currentOccupation, dateOfBirth;
    String IC, password, phone;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_1);

        gender_spinner = findViewById(R.id.gender_spinner);
        next_button = findViewById(R.id.next_button);
        email = findViewById(R.id.email);
        fullName = findViewById(R.id.fullName);
        currentOccupation = findViewById(R.id.currentOccupation);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        IC = getIntent().getStringExtra("IC");
        password = getIntent().getStringExtra("password");
        phone = getIntent().getStringExtra("phone");
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        String[] items = new String[]{"Female", "Male"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        gender_spinner.setAdapter(adapter);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                openDetail2();
            }
        });
    }

    public void openDetail2(){
        if (    email.getText().toString().equals("")||
                fullName.getText().toString().equals("")||
                currentOccupation.getText().toString().equals("")||
                dateOfBirth.getText().toString().equals("")){
            spinner.setVisibility(View.GONE);
            Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
        } else {
            spinner.setVisibility(View.GONE);
            Intent intent = new Intent(this, Detail_2.class);
            intent.putExtra("IC", IC);
            intent.putExtra("password", password);
            intent.putExtra("phone", phone);
            intent.putExtra("fullName", fullName.getText().toString());
            intent.putExtra("dateOfBirth", dateOfBirth.getText().toString());
            intent.putExtra("gender", gender_spinner.getSelectedItem().toString());
            intent.putExtra("currentOccupation", currentOccupation.getText().toString());
            intent.putExtra("email", email.getText().toString());
            startActivity(intent);
        }

    }
}