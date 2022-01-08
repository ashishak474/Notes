package com.ak.notes.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NotesEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "notes_title")
    private String title;
    @ColumnInfo(name = "notes_details")
    private String details;
    @ColumnInfo(name = "date_time")
    private String dateTime;
    @ColumnInfo(name = "image_path")
    private String imagePath;
    @ColumnInfo(name = "notes_color")
    private String color;
    @ColumnInfo(name = "web_link")
    private String webLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;


    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }
}
