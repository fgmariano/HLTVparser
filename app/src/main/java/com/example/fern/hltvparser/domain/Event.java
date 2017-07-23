package com.example.fern.hltvparser.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fern on 06/03/2017.
 */

public class Event implements Parcelable {
    public static final String EVENTS_KEY = "events_key";

    private String event_name;
    private String event_start;
    private String event_end;
    private String event_url;

    public String getEvent_name() {
        return event_name;
    }
    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }
    public String getEvent_start() {
        return event_start;
    }
    public void setEvent_start(String event_start) {
        this.event_start = event_start;
    }
    public String getEvent_end() {
        return event_end;
    }
    public void setEvent_end(String event_end) {
        this.event_end = event_end;
    }
    public String getEvent_url() {
        return event_url;
    }
    public void setEvent_url(String event_url) {
        this.event_url = event_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.event_name);
        dest.writeString(this.event_start);
        dest.writeString(this.event_end);
        dest.writeString(this.event_url);
    }

    public Event() {

    }
    protected Event(Parcel in) {
        this.event_name = in.readString();
        this.event_start = in.readString();
        this.event_end = in.readString();
        this.event_url = in.readString();
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
