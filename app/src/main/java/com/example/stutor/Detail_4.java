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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Detail_4 extends AppCompatActivity {
    Spinner transportation_spinner;
    Button next_button;
    String IC, password, phone, fullName, dateOfBirth, gender, currentOccupation, email, qualification, school, fieldOfStudy, qualificationStartYear, qualificationEndYear, institution, institutionStartYear, institutionEndYear;
    EditText location;
    FirebaseDatabase firebase;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_4);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        transportation_spinner = findViewById(R.id.transportation_spinner);
        next_button = findViewById(R.id.next_button);
        firebase = FirebaseDatabase.getInstance("https://tutor-agent-fe351.firebaseio.com/");

        IC = getIntent().getStringExtra("IC");
        password = getIntent().getStringExtra("password");
        phone = getIntent().getStringExtra("phone");
        fullName = getIntent().getStringExtra("fullName");
        dateOfBirth = getIntent().getStringExtra("dateOfBirth");
        gender = getIntent().getStringExtra("gender");
        currentOccupation = getIntent().getStringExtra("currentOccupation");
        email = getIntent().getStringExtra("email");
        qualification = getIntent().getStringExtra("qualification");
        school = getIntent().getStringExtra("school");
        fieldOfStudy = getIntent().getStringExtra("fieldOfStudy");
        qualificationStartYear = getIntent().getStringExtra("qualificationStartYear");
        qualificationEndYear = getIntent().getStringExtra("qualificationEndYear");
        institution = getIntent().getStringExtra("institution");
        institutionStartYear = getIntent().getStringExtra("institutionStartYear");
        institutionEndYear = getIntent().getStringExtra("institutionEndYear");

        location = findViewById(R.id.location);

        String[] items = new String[]{"Yes", "No"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        transportation_spinner.setAdapter(adapter);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetail3();
                spinner.setVisibility(View.VISIBLE);
            }
        });



    }

    public void openDetail3(){
        if (location.getText().toString().equals("")){
            spinner.setVisibility(View.GONE);
            Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference wRef = firebase.getReference("Tutors").child(IC);
            wRef.child("name").setValue(fullName);
            wRef.child("ic").setValue(IC);
            wRef.child("password").setValue(password);
            wRef.child("phone").setValue(phone);
            wRef.child("dateOfBirth").setValue(dateOfBirth);
            wRef.child("gender").setValue(gender);
            wRef.child("currentOccupation").setValue(currentOccupation);
            wRef.child("email").setValue(email);
            wRef.child("qualification").setValue(qualification);
            wRef.child("school").setValue(school);
            wRef.child("fieldOfStudy").setValue(fieldOfStudy);
            wRef.child("qualificationStartYear").setValue(qualificationStartYear);
            wRef.child("qualificationEndYear").setValue(qualificationEndYear);
            wRef.child("institution").setValue(institution);
            wRef.child("institutionStartYear").setValue(institutionStartYear);
            wRef.child("institutionEndYear").setValue(institutionEndYear);
            wRef.child("transportation").setValue(transportation_spinner.getSelectedItem().toString());
            wRef.child("location").setValue(location.getText().toString());
            wRef.child("status").setValue("pending");
            spinner.setVisibility(View.GONE);
            Intent intent = new Intent(this, Detail_5.class);
            intent.putExtra("email", email);
            startActivity(intent);
        }
    }
}