package com.example.stutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Detail_3 extends AppCompatActivity {

    Button next_button;
    String IC, password, phone, fullName, dateOfBirth, gender, currentOccupation, email, qualification, school, fieldOfStudy, qualificationStartYear, qualificationEndYear;
    EditText institution, startYear, endYear;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_3);
        next_button = findViewById(R.id.next_button);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
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

        institution = findViewById(R.id.institution);
        startYear = findViewById(R.id.startYear);
        endYear = findViewById(R.id.endYear);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetail4();
                spinner.setVisibility(View.VISIBLE);
            }
        });
    }

    public void openDetail4(){
        if(     institution.getText().toString().equals("")||
                startYear.getText().toString().equals("")||
                endYear.getText().toString().equals("")){
            spinner.setVisibility(View.GONE);
            Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
        } else {
            spinner.setVisibility(View.GONE);
            Intent intent = new Intent(this, Detail_4.class);
            intent.putExtra("IC", IC);
            intent.putExtra("password", password);
            intent.putExtra("phone", phone);
            intent.putExtra("fullName", fullName);
            intent.putExtra("dateOfBirth", dateOfBirth);
            intent.putExtra("gender", gender);
            intent.putExtra("currentOccupation", currentOccupation);
            intent.putExtra("email", email);
            intent.putExtra("qualification", qualification);
            intent.putExtra("school", school);
            intent.putExtra("fieldOfStudy", fieldOfStudy);
            intent.putExtra("qualificationStartYear", qualificationStartYear);
            intent.putExtra("qualificationEndYear", qualificationEndYear);
            intent.putExtra("institution", institution.getText().toString());
            intent.putExtra("institutionStartYear", startYear.getText().toString());
            intent.putExtra("institutionEndYear", endYear.getText().toString());
            startActivity(intent);
        }
    }
}