package com.fzls.moiveranking;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 风之凌殇 on 2016/2/6.
 */
public class Movie implements Parcelable {
    private String title;
    private String release_date;
    private String poster_url;
    private double vote_average;
    private String plot_overview;

    public Movie(String poster_url, String title){
        this.poster_url=poster_url;
        this.title=title;
    }
    public Movie(String title, String release_date, String poster_url, double vote_average, String plot_overview ){
        this.title=title;
        this.release_date=release_date;
        this.poster_url=poster_url;
        this.vote_average=vote_average;
        this.plot_overview=plot_overview;
    }


    public void setPlot_overview(String plot_overview) {
        this.plot_overview = plot_overview;
    }


    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getPlot_overview() {
        return plot_overview;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTitle() {
        return title;
    }


    protected Movie(Parcel in) {
        title = in.readString();
        release_date = in.readString();
        poster_url = in.readString();
        vote_average = in.readDouble();
        plot_overview = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(poster_url);
        dest.writeDouble(vote_average);
        dest.writeString(plot_overview);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}