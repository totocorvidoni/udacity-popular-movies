package android.example.popular_movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.example.popular_movies.modules.MovieData;
import android.example.popular_movies.utilities.NetworkUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements PosterAdapter.PosterItemClickListener {
    private static final String TAG = "MainActivity";
    private JSONArray moviesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeMoviesQuery("popular");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int clickedItemId = item.getItemId();
        if (clickedItemId == R.id.action_sort_popular) {
            makeMoviesQuery("popular");
        } else if (clickedItemId == R.id.action_sort_top) {
            makeMoviesQuery("rating");
        }

        return true;
    }

    void makeMoviesQuery(String sortBy) {
        if (NetworkUtils.isOnline()) {
            URL queryURL = NetworkUtils.buildDiscoverUrl(sortBy);
            new MoviesQueryTask().execute(queryURL);
        } else {
            Toast.makeText(getApplicationContext(), "Please check your internet connection and restart the app", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPosterItemClick(int clickedPosterIndex) {
        MovieData movieData = null;
        try {
            JSONObject movieDataObject = moviesData.getJSONObject(clickedPosterIndex);
            movieData = new MovieData(movieDataObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Context context = MainActivity.this;
        Class destinationActivity = MovieDetails.class;
        Intent intent = new Intent(context, destinationActivity);
        intent.putExtra("movieData", movieData);

        startActivity(intent);
    }

    public class MoviesQueryTask extends AsyncTask<URL, Void, String> {
        private static final String TAG = "MoviesQueryTask";

        @Override
        protected String doInBackground(URL... urls) {
            URL queryURL = urls[0];
            String posterResults = null;
            try {
                posterResults = NetworkUtils.getResponseFromHttpUrl(queryURL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return posterResults;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                try {
                    JSONObject response = new JSONObject(s);
                    moviesData = response.getJSONArray("results");

                    RecyclerView mPosterList = findViewById(R.id.rv_posters);
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    mPosterList.setLayoutManager(layoutManager);

                    mPosterList.setHasFixedSize(true);
                    PosterAdapter mAdapter = new PosterAdapter(moviesData, MainActivity.this);
                    mPosterList.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}