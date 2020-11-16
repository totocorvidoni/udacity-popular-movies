package android.example.popular_movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.example.popular_movies.database.AppDatabase;
import android.example.popular_movies.modules.Movie;
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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PosterAdapter.PosterItemClickListener {
    private static final String TAG = "MainActivity";
    private List<Movie> mMovies;

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
        } else if (clickedItemId == R.id.action_sort_favorite) {
            fetchFavorites();
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

    private void fetchFavorites() {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        LiveData<List<Movie>> favoriteMovies = db.favoriteMovieDAO().loadAllFavorites();
        favoriteMovies.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                mMovies = movies;
                Log.i(TAG, "fetchFavorites: " + mMovies);
                mountMovies();
            }
        });
    }

    @Override
    public void onPosterItemClick(int clickedPosterIndex) {
        Movie movie = mMovies.get(clickedPosterIndex);
        Context context = MainActivity.this;
        Class destinationActivity = MovieDetails.class;
        Intent intent = new Intent(context, destinationActivity);
        intent.putExtra("movieData", movie);

        startActivity(intent);
    }

    private void mountMovies() {
        RecyclerView mPosterList = findViewById(R.id.rv_posters);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mPosterList.setLayoutManager(layoutManager);

        mPosterList.setHasFixedSize(true);
        PosterAdapter mAdapter = new PosterAdapter(mMovies, MainActivity.this);
        mPosterList.setAdapter(mAdapter);
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
                    JSONArray moviesData = response.getJSONArray("results");

                    List<Movie> movies = new ArrayList<>();
                    for (int i = 0; i < moviesData.length(); i++) {
                        Movie movie = new Movie(moviesData.getJSONObject(i));
                        movies.add(movie);
                    }
                    mMovies = movies;

                    mountMovies();
                } catch (JSONException e) {
                    Log.i(TAG, "onPostExecute: Error Happened");
                    e.printStackTrace();
                }
            }
        }
    }
}