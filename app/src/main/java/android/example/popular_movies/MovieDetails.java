package android.example.popular_movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.popular_movies.database.AppDatabase;
import android.example.popular_movies.databinding.ActivityMovieDetailsBinding;
import android.example.popular_movies.modules.MovieData;
import android.example.popular_movies.utilities.NetworkUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MovieDetails extends AppCompatActivity implements TrailerAdapter.TrailerItemClickListener {
    private static final String TAG = "MovieDetails";

    private MovieData mMovieData;
    private ActivityMovieDetailsBinding mDetailsBinding;
    private JSONArray mTrailersData;

    private Button mFavoriteButton;
    private Button mDeleteButton;

    private AppDatabase mDb;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle data = getIntent().getExtras();
        mMovieData = data.getParcelable("movieData");

        mDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        mDetailsBinding.setMovie(mMovieData);

        mDb = AppDatabase.getInstance(getApplicationContext());

        setPoster();
        setFavoriteButton();

        URL trailersUrl = NetworkUtils.buildMovieVideosUrl(mMovieData.getId());
        new FetchTrailersTask().execute(trailersUrl);

        URL reviewsUrl = NetworkUtils.buildMovieReviewsUrl(mMovieData.getId());
        new FetchReviewsTask().execute(reviewsUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setPoster() {
        ImageView posterView = findViewById(R.id.iv_details_poster);
        URL posterURL = NetworkUtils.buildImageUrl(mMovieData.getPosterPath());

        Picasso.get().load(String.valueOf(posterURL)).into(posterView);
    }

    private void setFavoriteButton() {
        LiveData<MovieData> movie = mDb.favoriteMovieDAO().loadFavoriteById(mMovieData.getId());
        Log.i(TAG, "setFavoriteButton: " + movie);
        Button favoriteButton = findViewById(R.id.b_favorite_action);

         movie.observe(this, new Observer<MovieData>() {
            @Override
            public void onChanged(MovieData movie) {
                if (movie == null) {
                    favoriteButton.setText(R.string.favorite_button_text);
                    setAddFavoriteClickListener(favoriteButton);
                } else {
                    favoriteButton.setText(R.string.favorite_button_delete_text);
                    setDeleteFavoriteClickListener(favoriteButton);
                }
            }
        });
    }

    private void setAddFavoriteClickListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.favoriteMovieDAO().insertFavorite(mMovieData);
                    }
                });

            }
        });
    }

    private void setDeleteFavoriteClickListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.favoriteMovieDAO().deleteFavorite(mMovieData);
                    }
                });

            }
        });
    }

    @Override
    public void onTrailerItemClick(int clickedTrailerIndex) {
        try {
            JSONObject trailer = mTrailersData.getJSONObject(clickedTrailerIndex);
            Uri trailerUri = NetworkUtils.buildTrailerUri(trailer.getString("key"));

            Intent trailerIntent = new Intent(Intent.ACTION_VIEW, trailerUri);
            startActivity(trailerIntent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class FetchTrailersTask extends AsyncTask<URL, Void, String> {
        private static final String TAG = "FetchTrailersTask";

        @Override
        protected String doInBackground(URL... urls) {
            URL queryURL = urls[0];
            String trailerResults = null;
            try {
                trailerResults = NetworkUtils.getResponseFromHttpUrl(queryURL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return trailerResults;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                try {
                    JSONObject response = new JSONObject(s);
                    mTrailersData = response.getJSONArray("results");

                    RecyclerView trailerList = findViewById(R.id.rv_trailers);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MovieDetails.this);
                    trailerList.setLayoutManager(layoutManager);

                    trailerList.setHasFixedSize(true);
                    TrailerAdapter adapter = new TrailerAdapter(mTrailersData, MovieDetails.this);
                    trailerList.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class FetchReviewsTask extends AsyncTask<URL, Void, String> {
        private static final String TAG = "FetchReviewsTask";

        @Override
        protected String doInBackground(URL... urls) {
            URL queryURL = urls[0];
            String reviewResults = null;
            try {
                reviewResults = NetworkUtils.getResponseFromHttpUrl(queryURL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return reviewResults;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                try {
                    JSONObject response = new JSONObject(s);
                    JSONArray reviewsData = response.getJSONArray("results");

                    Log.i(TAG, "onPostExecute: " + reviewsData);

                    RecyclerView reviewList = findViewById(R.id.rv_reviews);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MovieDetails.this);
                    reviewList.setLayoutManager(layoutManager);

                    reviewList.setHasFixedSize(true);
                    ReviewAdapter adapter = new ReviewAdapter(reviewsData);
                    reviewList.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}