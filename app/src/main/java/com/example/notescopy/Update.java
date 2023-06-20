package com.example.notescopy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Update extends AppCompatActivity {

    private EditText contentToBeChanged;
    private TextView unchangedTitle;
    private FloatingActionButton updatedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        contentToBeChanged = findViewById(R.id.editTextContent);
        unchangedTitle = findViewById(R.id.TextTitle);
        updatedData = findViewById(R.id.save_notes);
        unchangedTitle.setText(getIntent().getStringExtra("title"));
        contentToBeChanged.setText(getIntent().getStringExtra("content"));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatedData.setOnClickListener(v -> {
            String title = unchangedTitle.getText().toString();
            String content = contentToBeChanged.getText().toString();
            DBHelper dbHelper = new DBHelper(this);
            Boolean checkForUpdatedData = dbHelper.updateuserdata(title,content);
            if (checkForUpdatedData) {
                Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data Not Updated", Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(this, MainActivity.class));
        });

    }
}