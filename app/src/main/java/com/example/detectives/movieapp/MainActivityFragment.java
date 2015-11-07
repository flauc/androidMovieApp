package com.example.detectives.movieapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivityFragment extends Fragment {

    private GridAdapter gridAdapter;
    ArrayList<MovieGridObject> movieGridObjects = new ArrayList<MovieGridObject>();
    private int cPos = GridView.INVALID_POSITION;

    public MainActivityFragment() {
    }


    // Required so you can have options
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.grid_view_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh) {
            GetMovies getMovies = new GetMovies();
            getMovies.execute("popularity.desc");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        GetMovies getMovies = new GetMovies();
        getMovies.execute("popularity.desc");


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        gridAdapter = new GridAdapter(getActivity(), movieGridObjects);

        GridView gridView = (GridView) rootView.findViewById(R.id.main_gridview);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int p, long l) {
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, "bla");
                startActivity(intent);
            }
        });

        return rootView;
    }

    public class GetMovies extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = GetMovies.class.getSimpleName();


        private String[] parsJson(String a, int num_res) throws JSONException {

            JSONObject theJson = new JSONObject(a);
            JSONArray theArr = theJson.getJSONArray("results");

            String[] res = new String[num_res];
            for (int i = 0; i < theArr.length(); i++) {

                JSONObject single = theArr.getJSONObject(i);

                String path = single.getString("poster_path");

                res[i] = path;
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

                movieGridObjects.clear();
                Log.d("PostExecute", " clearing array");

                for(String a : res) {
                    movieGridObjects.add(new MovieGridObject(a, imgSize));
                }
                gridAdapter = new GridAdapter(getActivity(),movieGridObjects);
            }
        }
    }
}
