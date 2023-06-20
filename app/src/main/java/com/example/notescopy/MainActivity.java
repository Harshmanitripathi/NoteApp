package com.example.notescopy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{

    private FloatingActionButton addNotesButton;
    private RecyclerView recyclerView;
    private ArrayList<String> title;
    private ArrayList<String> content;
    private DBHelper dbHelper;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNotesButton = findViewById(R.id.add_notes_button);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addNotesButton.setOnClickListener(v -> {
            startActivity(new Intent(this,UserNotes.class));
        });
        dbHelper = new DBHelper(this);
        title = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewForNotesList);
        myAdapter = new MyAdapter(this, title, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();

    }


    private void displayData() {
        Cursor cursor = dbHelper.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Entry Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                title.add(cursor.getString(0));
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ViewDetails.class);
        intent.putExtra("title", title.get(position));
//        intent.putExtra("content", content.get(position));
        startActivity(intent);
    }
}