package com.example.detectives.movieapp;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Detectives on 11/5/2015.
 */
public class GridAdapter extends ArrayAdapter<MovieGridObject> {

    // This is used for logging
    private static final String LOG_TAG = GridAdapter.class.getSimpleName();

    public GridAdapter(Activity context, List<MovieGridObject> movieGridObject) {
        super(context, 0, movieGridObject);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("Log:", "Adapter got called");
        MovieGridObject movieGridObject = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_grid, parent, false);
        }

        ImageView imgView = (ImageView) convertView.findViewById(R.id.poster_image);

        Picasso
                .with(getContext())
                .load("http://image.tmdb.org/t/p/" + movieGridObject.imageSize + movieGridObject.imageLink).into(imgView);

        Log.d(LOG_TAG, "http://image.tmdb.org/t/p/" + movieGridObject.imageSize + movieGridObject.imageLink);

        return convertView;
    }
}
