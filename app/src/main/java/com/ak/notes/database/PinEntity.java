package com.ak.notes.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pin")
public class PinEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "notes_title")
    private String title;
    @ColumnInfo(name = "notes_detail")
    private String detail;

    public PinEntity(int id, String title, String detail) {
        this.id = id;
        this.title = title;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }
}
