package com.example.notescopy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class ViewDetails extends AppCompatActivity {

    private TextView title;
    private Cursor getUserData;
    private TextView content;
    private FloatingActionButton deleteUserData;
    private FloatingActionButton updateUserdata;
    private DBHelper dbHelper;
    private String contentFromDatabse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatedelete);
        title = findViewById(R.id.TextTitle);
        content = findViewById(R.id.TextContent);
        deleteUserData = findViewById(R.id.deleteButton);
        updateUserdata = findViewById(R.id.updateButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String titleFromIntent = getIntent().getStringExtra("title");
        dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getAllData(titleFromIntent);
        if (dbHelper.getData() != null) {
            getUserData = dbHelper.getData();
            if (getUserData.getCount() == 0) {
                Toast.makeText(this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                return;
            } else {
                title.setText(titleFromIntent);
                if (cursor.moveToFirst()) {
                    contentFromDatabse = cursor.getString(1);
                    content.setText(contentFromDatabse);
                }
            }
        } else {
            Toast.makeText(this, "DB is empty", Toast.LENGTH_SHORT).show();
        }
        updateUserdata.setOnClickListener(v -> {
            Intent intent = new Intent(this, Update.class);
            intent.putExtra("title", titleFromIntent);
            intent.putExtra("content",contentFromDatabse);
            startActivity(intent);
        });
        deleteUserData.setOnClickListener(v -> {
            DBHelper dbHelper = new DBHelper(this);
            Boolean checkForUpdatedData = dbHelper.deleteuserdata(titleFromIntent);
            if (checkForUpdatedData) {
                Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}