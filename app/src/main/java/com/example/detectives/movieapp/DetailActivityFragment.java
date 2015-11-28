package com.example.detectives.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    Intent intent;

    // Used in reviews
    private ReviewAdapter reviewAdapter;
    ArrayList<ReviewObject> reviewObjects = new ArrayList<>();
    ListView reviewListView;

    // Used in videos
    FrameLayout videoLayout;


    String nowShown = "empty";

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // Accordion like setup for reviews and videos
        videoLayout = (FrameLayout) rootView.findViewById(R.id.video_layout);
        final Button videoButton = (Button) rootView.findViewById(R.id.show_videos_button);
        final Button reviewButton = (Button) rootView.findViewById(R.id.show_reviews_button);
        reviewListView = (ListView) rootView.findViewById(R.id.review_list_view);

        intent = getActivity().getIntent();
        if (intent != null) {

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

        final String id = intent.getStringExtra("movieId");
        final GetReviews getReviews = new GetReviews();

        // Click listener for accordions video tab
        videoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!nowShown.equals("video")) {
                    nowShown = "video";
                    videoButton.setClickable(false);
                    videoLayout.setVisibility(View.VISIBLE);
                    if (reviewListView.getVisibility() == View.VISIBLE) {
                        reviewListView.setVisibility(View.GONE);
                    }
                }
            }
        });

        // Click listener for accordions review tab
        reviewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!nowShown.equals("review")) {
                    nowShown = "review";
                    reviewButton.setClickable(false);
                    reviewListView.setVisibility(View.VISIBLE);
                    getReviews.execute(id);
                    if(videoLayout.getVisibility() == View.VISIBLE) {
                        videoLayout.setVisibility(View.GONE);
                    }
                }
            }
        });


        return rootView;
    }

    public class GetReviews extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = GetReviews.class.getSimpleName();


        private String[] parsJson(String a) throws JSONException {

            JSONObject theJson = new JSONObject(a);
            JSONArray theArr = theJson.getJSONArray("results");


            String[] res = new String[theArr.length()];
            for (int i = 0; i < theArr.length(); i++) {

                JSONObject single = theArr.getJSONObject(i);

                String author = single.getString("author");
                String content = single.getString("content");

                res[i] = author + "<>" + content;
            }

            return res;
        }

        @Override
        protected String[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String reviewJson = null;

            try {

                // URL Builder params
                String base = "https://api.themoviedb.org/3/movie/";
                String rev = "reviews";
                String apiKey = "api_key";

                Uri theUrl = Uri.parse(base).buildUpon()
                        .appendPath(params[0])
                        .appendPath(rev)
                        .appendQueryParameter(apiKey, BuildConfig.ApiKey)
                        .build();

                URL url = new URL(theUrl.toString());

                Log.d("bla", theUrl.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                reviewJson = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error while closing stream", e);
                    }
                }
            }

            try {
                Log.d(LOG_TAG, "Parsed");
                return parsJson(reviewJson);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        // This displays data from the async task
        @Override
        protected void onPostExecute(String[] res) {
            if (res != null) {
                reviewObjects.clear();
                String[] current;
                for(String a : res) {
                    current = a.split("<>");
                    for(int i = 0; i < current.length; i++) {
                        Log.d("Adding Review", current[i]);
                    }
                    reviewObjects.add(new ReviewObject(current[0], current[1]));
                }

                Log.d("ReviewObject", reviewObjects.toString());

                // Instance of the grid adapter
                reviewAdapter = new ReviewAdapter(getActivity(),reviewObjects);

                // Setting the grid vies adapter to be the grid adapter
                reviewListView.setAdapter(reviewAdapter);
            }
        }
    }
}



