package com.example.wesh9.hw3;

/**
 * Created by wesh9 on 10/17/2017.
 */

public class Movie {

    private String title;
    private String date;
    private String rating;
    private String datee;
    private String img;
    private String overview;
    private boolean fav = false;



    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setFav(boolean set){
        fav = set;
    }

    public boolean getFav(){
        return fav;
    }

    public String getTitle() {

        return title;
    }

    public String getDate() {
        return date;
    }

    public String getRating() {
        return rating;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
