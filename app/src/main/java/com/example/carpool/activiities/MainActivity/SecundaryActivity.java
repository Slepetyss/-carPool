package com.example.carpool.activiities.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carpool.R;

public class SecundaryActivity extends AppCompatActivity {

    TextView name, sits;

    String nameText, sitsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundary);

        name = findViewById(R.id.textViewName2);
        sits = findViewById(R.id.textViewSits2);

        getData();
        setData();
    }

    private void getData() {
        if(getIntent().hasExtra("Name") & getIntent().hasExtra("Available Sits")) {

            nameText = getIntent().getStringExtra("Name");
            sitsText = getIntent().getStringExtra("Available Sits");

        } else {
            Toast.makeText(this, "No data...", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        name.setText(nameText);
        sits.setText("Available Sits: " + sitsText);
    }
}