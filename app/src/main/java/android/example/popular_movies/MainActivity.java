package android.example.popular_movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.example.popular_movies.utilities.NetworkUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_LIST_ITEMS = 100;
    private PosterAdapter mAdapter;
    private RecyclerView mPosterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makePopularMoviesQuery();

        mPosterList = (RecyclerView) findViewById(R.id.rv_posters);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mPosterList.setLayoutManager(layoutManager);

        mPosterList.setHasFixedSize(true);


    }

    void makePopularMoviesQuery() {
        URL queryURL = NetworkUtils.buildDiscoverUrl("popularity");
        new MoviesQueryTask().execute(queryURL);
    }

    void makeTopRatedMoviesQuery() {
        URL queryURL = NetworkUtils.buildDiscoverUrl("rating");
        new MoviesQueryTask().execute(queryURL);
    }

    public class MoviesQueryTask extends AsyncTask<URL, Void, String> {
        private static final String TAG = "MoviesQueryTask";
        @Override
        protected String doInBackground(URL... urls) {
            URL queryURL = urls[0];
            String movieResults = null;
            try {
                movieResults = NetworkUtils.getResponseFromHttpUrl(queryURL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return movieResults;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                try {
                    JSONObject response = new JSONObject(s);
                    JSONArray moviesData = response.getJSONArray("results");

                    mAdapter = new PosterAdapter(moviesData);
                    mPosterList.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}