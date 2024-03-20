package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_welcome);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);

        // Delay for 2 seconds and then start the Login activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the Login activity
                Intent intent = new Intent(Welcome.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
