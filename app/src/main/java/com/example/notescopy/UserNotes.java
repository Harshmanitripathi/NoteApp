package com.example.notescopy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserNotes extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private FloatingActionButton saveNoteData;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notes);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        saveNoteData = findViewById(R.id.save_notes);
        title = findViewById(R.id.editTextTitle);
        content = findViewById(R.id.editTextContent);
        dbHelper = new DBHelper(this);

        saveNoteData.setOnClickListener(v -> {
            String titleOfNote = title.getText().toString();
            String contentOfNote = content.getText().toString();
            Boolean checkForUserData = dbHelper.insertuserdata(titleOfNote,contentOfNote);
            if (checkForUserData) {
                Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data not inserted", Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(UserNotes.this, MainActivity.class));
        });

    }
}