package android.example.popular_movies;

import androidx.appcompat.app.AppCompatActivity;

import android.example.popular_movies.modules.MovieData;
import android.example.popular_movies.utilities.NetworkUtils;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;

public class MovieDetails extends AppCompatActivity {
    private static final String TAG = "MovieDetails";
    MovieData mMovieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle data = getIntent().getExtras();
        mMovieData = data.getParcelable("movieData");

        setTitle();
        setPoster();
        setYear();
        setRating();
        setOverview();
    }

    public void setTitle() {
        TextView titleView = findViewById(R.id.tv_details_title);
        titleView.setText(mMovieData.getTitle());
    }

    public void setPoster() {
        ImageView posterView = findViewById(R.id.iv_details_poster);
        URL posterURL = NetworkUtils.buildImageUrl(mMovieData.getPosterPath());

        Picasso.get().load(String.valueOf(posterURL)).into(posterView);
    }

    public void setYear() {
        TextView yearView = findViewById(R.id.tv_details_release_year);
        String year = mMovieData.getReleaseDate().substring(0, 4);
        yearView.setText(year);
    }

    public void setRating() {
        TextView ratingView = findViewById(R.id.tv_details_rating);
        String rating = mMovieData.getVoteAverage() + " / 10";
        Log.i(TAG, "setRating: " + rating);
        ratingView.setText(rating);

    }

    public void setOverview() {
        TextView overviewView = findViewById(R.id.tv_details_overview);
        overviewView.setText(mMovieData.getOverview());
    }
}