package com.example.detectives.movieapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null) {

            if(intent.hasExtra("title")) {
                String title = intent.getStringExtra("title");
                getActivity().setTitle(title);
            }

            if(intent.hasExtra("gaImage") && intent.hasExtra("gaImageSize")) {
                ImageView imgView = (ImageView) rootView.findViewById(R.id.intro_img);
                String gaImage = intent.getStringExtra("gaImage");
                String gaImageSize = intent.getStringExtra("gaImageSize");
                Picasso
                        .with(getContext())
                        .load("http://image.tmdb.org/t/p/" + gaImageSize + gaImage).into(imgView);
            }

            if (intent.hasExtra("voteAverage")) {
                float vAverage = Float.parseFloat(intent.getStringExtra("voteAverage"));

                float scale = getResources().getDisplayMetrics().density;
//                int paToAdd = (int) (1.5*scale + 0.5f);

                LinearLayout lm = (LinearLayout) rootView.findViewById(R.id.votes_wrapper);
                int bigStars = (int)((Math.floor(vAverage/2)));
                for(int i=0; i<bigStars;i++) {
                    ImageView a = new ImageView(getContext());
                    a.setImageResource(R.mipmap.vote_star);
//                    a.setPadding(paToAdd,paToAdd,paToAdd,paToAdd);
                    lm.addView(a);
                }

                if(((vAverage/2) - bigStars) >= 0.5) {
                    ImageView a = new ImageView(getContext());
                    a.setImageResource(R.mipmap.half_vote_star);
//                    a.setPadding(paToAdd,paToAdd,paToAdd,paToAdd);
                    lm.addView(a);
                }
            }

            if(intent.hasExtra("overview")) {
                String overview = intent.getStringExtra("overview");
                ((TextView) rootView.findViewById(R.id.movie_overview))
                        .setText(overview);
            }

            if(intent.hasExtra("rDate")) {
                String rel = intent.getStringExtra("rDate");
                ((TextView) rootView.findViewById(R.id.movie_rel))
                        .setText(rel);
            }

        }

        return rootView;
    }
}

