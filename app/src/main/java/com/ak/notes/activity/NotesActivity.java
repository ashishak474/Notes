package com.ak.notes.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ak.notes.R;
import com.ak.notes.database.NotesDatabase;
import com.ak.notes.database.NotesEntity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotesActivity extends AppCompatActivity {

    private EditText ettittle;
    private EditText etnote;
    private Toolbar toolbar;
    private NotesEntity notesEntity;
    private TextView url;
    private TextView date;
    private String selectedColor;
    private View colorIndicator;
    private String selectedImagePath;
    private ImageView imageView;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        initViews();
        date.setText(new SimpleDateFormat("d MMM yyyy, HH:mm a", Locale.getDefault()).format(new Date()));


        selectedColor = "#FFFFFF";
        selectedImagePath = "";


    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(NotesActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initViews() {

        ettittle = findViewById(R.id.et_tittle);
        etnote = findViewById(R.id.et_note);
        toolbar = findViewById(R.id.toolbar);
        imageView = findViewById(R.id.image_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
        date = findViewById(R.id.date_time);
        colorIndicator = findViewById(R.id.color_idicator);
        url = findViewById(R.id.text_url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.save) {
            String title = ettittle.getText().toString();
            String note = etnote.getText().toString();

            Log.e("NOTESACTIVITY", "Save Clicked");

            notesEntity = new NotesEntity();
            notesEntity.setTitle(title);
            notesEntity.setDetails(note);
            notesEntity.setDateTime(date.getText().toString());
            notesEntity.setColor(selectedColor);
            notesEntity.setImagePath(selectedImagePath);



            if (url.getVisibility() == View.VISIBLE) {

                notesEntity.setWebLink(url.getText().toString());
            }

            Log.e("NOTESACTIVITY", notesEntity.getColor());
            NotesDatabase notesDatabase = NotesDatabase.getInstance(NotesActivity.this);

            notesDatabase.notesDao().insertNotes(notesEntity);
            Log.e("NOTESACTIVITY", "Note Insert");
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            this.recreate();
            this.finish();
            Toast.makeText(NotesActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();


        }

        if (id == R.id.pin) {


            Log.e("NOTESACTIVITY", "Pin Clicked");
            if (ContextCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {
                Log.e("NOTESACTIVITY", "Permission grant");

                ActivityCompat.requestPermissions(
                        NotesActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_STORAGE_PERMISSION

                );
            } else {
                Log.e("NOTESACTIVITY", "else");
                selectImage();


            }
        }
        if (id == R.id.web) {


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(NotesActivity.this);
            alertDialog.setTitle("Enter Url");

            final EditText input = new EditText(this);

            alertDialog.setView(input);
            alertDialog.setIcon(R.drawable.web);
            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            if (input.getText().toString().trim().isEmpty()) {
                                Toast.makeText(NotesActivity.this, "Enter Url", Toast.LENGTH_SHORT).show();
                            } else if (!Patterns.WEB_URL.matcher(input.getText().toString()).matches()) {
                                Toast.makeText(NotesActivity.this, "Enter Valid Url", Toast.LENGTH_SHORT).show();

                            } else {
                                url.setText(input.getText().toString());
                                url.setVisibility(View.VISIBLE);
                                dialog.dismiss();

                            }

                        }
                    });
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });


            alertDialog.show();
        }


        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    public void color(View view) {
        BottomSheetDialog bottomSheet = new BottomSheetDialog(this);
        View view1 = LayoutInflater.from(NotesActivity.this).inflate(R.layout.bottom_layout, findViewById(R.id.bottom_linear));
        bottomSheet.setContentView(view1);
        bottomSheet.show();

        ImageView imageColor1 = view1.findViewById(R.id.color1);
        ImageView imageColor2 = view1.findViewById(R.id.color2);
        ImageView imageColor3 = view1.findViewById(R.id.color3);
        ImageView imageColor4 = view1.findViewById(R.id.color4);

        view1.findViewById(R.id.view_color1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor = "#FFCD36";
                imageColor1.setImageResource(R.drawable.save_icon);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                setColorIndicator();
            }
        });

        view1.findViewById(R.id.view_color2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor = "#00BCD4";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(R.drawable.save_icon);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                setColorIndicator();
            }
        });

        view1.findViewById(R.id.view_color3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor = "#F44336";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(R.drawable.save_icon);
                imageColor4.setImageResource(0);
                setColorIndicator();
            }
        });

        view1.findViewById(R.id.view_color4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor = "#FFFFFF";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(R.drawable.save_icon);
                setColorIndicator();
            }
        });


    }

    private void setColorIndicator() {
        GradientDrawable gradientDrawable = (GradientDrawable) colorIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedColor));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageView.setImageBitmap(bitmap);
                        imageView.setVisibility(View.VISIBLE);
                        selectedImagePath = getPathFromUri(selectedImageUri);
                    } catch (Exception exception) {
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
    }

    public void image(View view) {

    }

    private String getPathFromUri(Uri contentUri) {
        String filePath;
        Cursor cursor = getContentResolver()
                .query(contentUri, null, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;

    }
}