package com.example.coordinator;

import android.os.Parcel;
import android.os.Parcelable;

public class Musica implements Parcelable {
    private String videoId;

    public Musica(String videoId) {
        this.videoId = videoId;
    }

    protected Musica(Parcel in) {
        videoId = in.readString();
    }

    public static final Creator<Musica> CREATOR = new Creator<Musica>() {
        @Override
        public Musica createFromParcel(Parcel in) {
            return new Musica(in);
        }

        @Override
        public Musica[] newArray(int size) {
            return new Musica[size];
        }
    };

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(videoId);
    }
}
