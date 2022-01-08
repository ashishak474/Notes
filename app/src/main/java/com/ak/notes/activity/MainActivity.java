package com.ak.notes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ak.notes.R;
import com.ak.notes.adapter.NotesAdapter;
import com.ak.notes.database.NotesDatabase;
import com.ak.notes.database.NotesEntity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {


    private ImageView delete;
    private RecyclerView recyclerView;
    private FirebaseAnalytics mFirebaseAnalytics;

    private NotesAdapter.DataDelete dataDelete;

  //  private boolean isBack = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Intent i = new Intent(this,FirebaseMessaging.class);
        startService(i);



        delete = findViewById(R.id.delete);

        final List<NotesEntity> list =NotesDatabase.getInstance(MainActivity.this)
                .notesDao()
                .getAll();

        recyclerView = findViewById(R.id.rv_notes);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        dataDelete = new NotesAdapter.DataDelete() {
            @Override
            public void onLongClick() {
                delete.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "onLongClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(int id) {
              //  isBack = true;
                Intent i =new Intent(MainActivity.this, NotesShowActivity.class);
                i.putExtra("notesId",id);
                startActivity(i);
            }
        };

        final NotesAdapter notesAdapter = new NotesAdapter(list,this,dataDelete);
        recyclerView.setAdapter(notesAdapter);

        EditText searchbar = findViewById(R.id.search_bar);

        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            Log.e("MAINACTIVITY","beforeTextChanged:"+charSequence+" "+i+" "+i1+" "+i2);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("MAINACTIVITY","onTextChanged:"+charSequence+" "+i+" "+i1+" "+i2);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("MAINACTIVITY","afterTextChanged:"+editable.toString());

                searchNotes(editable.toString());
            }
        });

        




    }

    private void searchNotes(String s) {
        final List<NotesEntity> list =NotesDatabase.getInstance(MainActivity.this)
                .notesDao()
                .getAll();

        ArrayList<NotesEntity> newList= new ArrayList<>();

        for (NotesEntity notes:list) {
            if(notes.getTitle().toLowerCase().startsWith(s.toLowerCase())){
                newList.add(notes);
            }

        }


        final NotesAdapter notesAdapter = new NotesAdapter(newList,this,dataDelete);
        recyclerView.setAdapter(notesAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        delete.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
            delete.setVisibility(View.GONE);
    }

    public void edit(View view) {



        Intent i =new Intent(MainActivity.this,NotesActivity.class);
        startActivity(i);
    }


    public void my(View view) {
        Intent i = new Intent(MainActivity.this, PinActivity.class);
        startActivity(i);
    }
}