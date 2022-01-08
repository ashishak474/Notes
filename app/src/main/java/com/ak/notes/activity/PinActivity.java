package com.ak.notes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.ak.notes.R;
import com.ak.notes.adapter.PinAdapter;
import com.ak.notes.database.NotesDatabase;
import com.ak.notes.database.PinEntity;

import java.util.List;

public class PinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        List<PinEntity> list = NotesDatabase.getInstance(PinActivity.this)
                .notesDao()
                .getPin();
        RecyclerView recyclerView = findViewById(R.id.rv_view);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        PinAdapter pinAdapter = new PinAdapter(list,this);
        recyclerView.setAdapter(pinAdapter);
    }
}