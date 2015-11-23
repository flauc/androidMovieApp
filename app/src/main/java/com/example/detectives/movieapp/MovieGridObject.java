package com.example.detectives.movieapp;

/**
 * Created by Detectives on 11/5/2015.
 */
public class MovieGridObject {
    String imageLink;
    String imageSize;
    String title;
    String overview;
    String rDate;
    String gaImage;
    String gaImageSize;
    String voteAverage;

    public MovieGridObject(String a, String b, String c, String d, String e, String i, String j, String k) {
        this.imageLink = a;
        this.imageSize = b;
        this.title = c;
        this.overview = d;
        this.rDate = e;
        this.gaImage = i;
        this.gaImageSize = j;
        this.voteAverage = k;
    }
}
