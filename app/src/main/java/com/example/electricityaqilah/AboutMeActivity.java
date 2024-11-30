package com.example.electricityaqilah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutme);

        // Reference to the buttons

        ImageView btnCalculator = findViewById(R.id.btnCalculator);
        btnCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AboutMeActivity.this, "About Me Page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AboutMeActivity.this, MainActivity.class); // Use SecondActivity.class
                startActivity(intent);
            }

        });

    }
}
