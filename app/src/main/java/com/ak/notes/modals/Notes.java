package com.ak.notes.modals;

import android.os.Parcel;
import android.os.Parcelable;

public class Notes implements Parcelable {

    private final String Tittle;
    private final String Discription;

    public Notes(String tittle, String discription) {
        Tittle = tittle;
        Discription = discription;
    }

    protected Notes(Parcel in) {
        Tittle = in.readString();
        Discription = in.readString();
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    public String getTittle() {
        return Tittle;
    }

    public String getDiscription() {
        return Discription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Tittle);
        parcel.writeString(Discription);
    }
}
