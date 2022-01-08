package com.ak.notes.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ak.notes.R;
import com.ak.notes.database.NotesDatabase;
import com.ak.notes.database.NotesEntity;
import com.ak.notes.database.PinEntity;

public class NotesShowActivity extends AppCompatActivity {

    private EditText ettittle;
    private EditText etnote;
    private Toolbar toolbar;
    private NotesEntity notesEntity;
    private int id;
    private TextView date;
    private View color_indicator;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_show);
        initViews();

        Intent intent = getIntent();
        id = intent.getIntExtra("notesId",0);


        refresh();
    }
    private void refresh() {
        notesEntity = NotesDatabase.getInstance(NotesShowActivity.this).notesDao().getNote(id);


        ettittle.setText(notesEntity.getTitle());
        etnote.setText(notesEntity.getDetails());
        date.setText(notesEntity.getDateTime());
        color_indicator.setBackgroundColor(Color.parseColor(notesEntity.getColor()));
     //   imageView.setImageResource(notesEntity.getImagePath());


    }

    private void initViews() {

        ettittle = findViewById(R.id.et_tittle);
        etnote = findViewById(R.id.et_note);
        toolbar = findViewById(R.id.toolbar);
        imageView=findViewById(R.id.image_view);
        color_indicator =findViewById(R.id.color_idicator);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
        date=findViewById(R.id.date_time);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.save) {

            String title = ettittle.getText().toString();
            String notes = etnote.getText().toString();

            notesEntity.setTitle(title);
            notesEntity.setDetails(notes);

            NotesDatabase notesDatabase = NotesDatabase.getInstance(NotesShowActivity.this);
            try {
                notesDatabase.notesDao().updateNotes(notesEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }

            refresh();


        }

        if(id == R.id.pin){
            String title = ettittle.getText().toString();
            String note = etnote.getText().toString();
            PinEntity pinEntity = new PinEntity(notesEntity.getId(),title,note);
            NotesDatabase.getInstance(NotesShowActivity.this).notesDao().saveToPin(pinEntity);
        }

        if(id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}