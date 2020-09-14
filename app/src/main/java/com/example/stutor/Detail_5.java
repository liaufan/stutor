package com.example.stutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Detail_5 extends AppCompatActivity {
    Button done_button;
    TextView email_view;
    String email;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_5);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        done_button = findViewById(R.id.done_button);
        email_view = findViewById(R.id.email_view);
        email = getIntent().getStringExtra("email");

        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
                spinner.setVisibility(View.VISIBLE);
            }
        });

        email_view.setText(email);
    }

    public void openLogin(){
        spinner.setVisibility(View.GONE);
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}