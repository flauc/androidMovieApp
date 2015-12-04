package com.example.detectives.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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

public class MainActivityFragmentPopularity extends Fragment {

    // Views
    View rootView;
    GridView gridView;
   // The grid adapter
    private GridAdapter gridAdapter;

    // The list of movie object
    ArrayList<MovieGridObject> movieGridObjects = new ArrayList<MovieGridObject>();


    public MainActivityFragmentPopularity() {
    }


    // Required for options menu
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.grid_view_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.refresh) {
//            GetMovies getMovies = new GetMovies();
//            getMovies.execute("popularity.desc");
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Set the root view instance
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Set the grid view instance
        gridView = (GridView) rootView.findViewById(R.id.main_gridview);

        // Preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString("pref_sortOrder", getString(R.string.pref_sort_default));

        // Execute the async task
        GetMovies getMovies = new GetMovies();
        getMovies.execute("popularity.desc");

        // Click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int p, long l) {
                MovieGridObject movie = gridAdapter.getItem(p);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra("imageLink", movie.imageLink)
                        .putExtra("imageSize", movie.imageSize)
                        .putExtra("title", movie.title)
                        .putExtra("overview", movie.overview)
                        .putExtra("rDate", movie.rDate)
                        .putExtra("gaImage", movie.gaImage)
                        .putExtra("gaImageSize", movie.gaImageSize)
                        .putExtra("voteAverage", movie.voteAverage)
                        .putExtra("movieId", movie.movieID);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public class GetMovies extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = GetMovies.class.getSimpleName();


        private String[] parsJson(String a, int num_res) throws JSONException {

            JSONObject theJson = new JSONObject(a);
            JSONArray theArr = theJson.getJSONArray("results");


            String[] res = new String[num_res];
            for (int i = 0; i < theArr.length(); i++) {

                JSONObject single = theArr.getJSONObject(i);

                String title = single.getString("title");
                String release_date = single.getString("release_date");
                String overview = single.getString("overview");
                String path = single.getString("poster_path");
                String path2 = single.getString("backdrop_path");
                String voteAverage = single.getString("vote_average");
                String movieId = single.getString("id");

                res[i] = title + "<>" + overview + "<>" + release_date + "<>" + path + "<>" + path2 + "<>" + voteAverage + "<>" + movieId;
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

            String movieJson = null;

            // This is the amount of title returned more titles then this would require additional api calls
            int num_res = 20;

            try {

                // URL Builder params
                String base = "http://api.themoviedb.org/3/discover/movie?";
                String sort = "sort_by";
                String apiKey = "api_key";

                Uri theUrl = Uri.parse(base).buildUpon()
                        .appendQueryParameter(sort, params[0])
                        .appendQueryParameter(apiKey, BuildConfig.ApiKey)
                        .build();

                URL url = new URL(theUrl.toString());

                Log.d("bla",theUrl.toString());

                // http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=70e31d63b926a5c1f11c80d39ddc64f1

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
                movieJson = buffer.toString();
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
                return parsJson(movieJson, num_res);
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
                String imgSize = "w185";
                String gaImgSize = "w500";

                movieGridObjects.clear();
                String[] current;
                for(String a : res) {
                    current = a.split("<>");
                    for(int i = 0; i < current.length; i++) {
                        Log.d("Adding Movies", current[i]);
                    }
                    movieGridObjects.add(new MovieGridObject(current[3], imgSize, current[0], current[1], current[2], current[4], gaImgSize, current[5], current[6]));
                }

                Log.d("GridObject", movieGridObjects.toString());

                // Instance of the grid adapter
                gridAdapter = new GridAdapter(getActivity(),movieGridObjects);

                // Setting the grid vies adapter to be the grid adapter
                gridView.setAdapter(gridAdapter);
            }
        }
    }
}
