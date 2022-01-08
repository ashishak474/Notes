package com.ak.notes.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NotesDao {
    @Insert
    void insertNotes(NotesEntity notesEntity);

    @Query("SELECT * FROM notes")
    List<NotesEntity> getAll();

    @Query("DELETE FROM notes where id==:id")
    void deleteNode(int id);

    @Query("SELECT * FROM notes WHERE id==:id")
    NotesEntity getNote(int id);

    @Update
    void updateNotes(NotesEntity notesEntity);

    @Insert(onConflict = REPLACE)
    void saveToPin(PinEntity pinEntity);

    @Query("SELECT * FROM pin")
    List<PinEntity> getPin();

    @Query("DELETE FROM pin where id==:id")
    void deletePin(int id);
}
