package com.example.detectives.movieapp;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Detectives on 11/28/2015.
 */
public class ReviewAdapter extends ArrayAdapter<ReviewObject> {

    public ReviewAdapter(Activity context, List<ReviewObject> reviewObject) {
        super(context, 0, reviewObject);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("Log:", "Review adapter got called");
        ReviewObject reviewObject = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_review, parent, false);
        }

        TextView authorView = (TextView) convertView.findViewById(R.id.review_author);
        authorView.setText(reviewObject.author);
        TextView contentView = (TextView) convertView.findViewById(R.id.review_content);
        contentView.setText(reviewObject.content);

        return convertView;
    }

}
