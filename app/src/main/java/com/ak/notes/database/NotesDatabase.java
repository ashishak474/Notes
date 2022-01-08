package com.ak.notes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NotesEntity.class,PinEntity.class},version = 7)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();

    public static NotesDatabase notesDatabase;

    public static NotesDatabase getInstance(Context context) {

        if (notesDatabase == null) {
            notesDatabase = Room.databaseBuilder(context, NotesDatabase.class, "notes-database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return notesDatabase;

    }
}
