package com.example.fern.hltvparser.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fern on 07/03/2017.
 */

public class Match implements Parcelable {
    public static final String MATCH_KEY = "match_key";

    private String team1;
    private String team2;
    private String score1;
    private String score2;
    private String map;
    private String date;

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.team1);
        dest.writeString(this.team2);
        dest.writeString(this.map);
        dest.writeString(this.score1);
        dest.writeString(this.score2);
        dest.writeString(this.date);
    }

    public Match() {

    }
    protected Match(Parcel in) {
        this.team1 = in.readString();
        this.team2 = in.readString();
        this.map = in.readString();
        this.score1 = in.readString();
        this.score2 = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Match> CREATOR = new Parcelable.Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel source) {
            return new Match(source);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };
}
