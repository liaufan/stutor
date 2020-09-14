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

public class Detail_2 extends AppCompatActivity {
    Spinner education_spinner;
    Button next_button;
    String IC, password, phone, fullName, dateOfBirth, gender, currentOccupation, email;
    EditText school, fieldOfStudy, startYear, endYear;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_2);

        education_spinner = findViewById(R.id.education_spinner);
        next_button = findViewById(R.id.next_button);

        IC = getIntent().getStringExtra("IC");
        password = getIntent().getStringExtra("password");
        phone = getIntent().getStringExtra("phone");
        fullName = getIntent().getStringExtra("fullName");
        dateOfBirth = getIntent().getStringExtra("dateOfBirth");
        gender = getIntent().getStringExtra("gender");
        currentOccupation = getIntent().getStringExtra("currentOccupation");
        email = getIntent().getStringExtra("email");
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        school = findViewById(R.id.school);
        fieldOfStudy = findViewById(R.id.fieldOfStudy);
        startYear = findViewById(R.id.startYear);
        endYear = findViewById(R.id.endYear);

        String[] items = new String[]{"High School", "Foundation", "Diploma", "Degree", "Master", "PhD"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        education_spinner.setAdapter(adapter);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetail3();
                spinner.setVisibility(View.VISIBLE);
            }
        });
    }

    public void openDetail3(){
        if(     school.getText().toString().equals("")||
                fieldOfStudy.getText().toString().equals("")||
                startYear.getText().toString().equals("")||
                endYear.getText().toString().equals("")){
            spinner.setVisibility(View.GONE);
            Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
        } else {
            spinner.setVisibility(View.GONE);
            Intent intent = new Intent(this, Detail_3.class);
            intent.putExtra("IC", IC);
            intent.putExtra("password", password);
            intent.putExtra("phone", phone);
            intent.putExtra("fullName", fullName);
            intent.putExtra("dateOfBirth", dateOfBirth);
            intent.putExtra("gender", gender);
            intent.putExtra("currentOccupation", currentOccupation);
            intent.putExtra("email", email);
            intent.putExtra("qualification", education_spinner.getSelectedItem().toString());
            intent.putExtra("school", school.getText().toString());
            intent.putExtra("fieldOfStudy", fieldOfStudy.getText().toString());
            intent.putExtra("qualificationStartYear", startYear.getText().toString());
            intent.putExtra("qualificationEndYear", endYear.getText().toString());
            startActivity(intent);
        }
    }
}