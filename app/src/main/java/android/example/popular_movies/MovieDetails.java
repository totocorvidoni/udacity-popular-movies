package android.example.popular_movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.example.popular_movies.modules.MovieData;
import android.example.popular_movies.utilities.NetworkUtils;
import android.icu.text.DateFormat;
import android.icu.text.DateFormatSymbols;
import android.icu.text.SimpleDateFormat;
import android.net.Network;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

public class MovieDetails extends AppCompatActivity {
    private static final String TAG = "MovieDetails";
    MovieData mMovieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle data = getIntent().getExtras();
        mMovieData = (MovieData) data.getParcelable("movieData");

        setTitle();
        setPoster();
        setYear();
        setRating();
        setOverview();
    }

    public void setTitle() {
        TextView titleView = (TextView) findViewById(R.id.tv_details_title);
        titleView.setText(mMovieData.getTitle());
    }

    public void setPoster() {
        ImageView posterView = (ImageView) findViewById(R.id.iv_details_poster);
        URL posterURL = NetworkUtils.buildImageUrl(mMovieData.getPosterPath());

        Picasso.get().load(String.valueOf(posterURL)).into(posterView);
    }

    public void setYear() {
        TextView yearView = (TextView) findViewById(R.id.tv_details_release_year);
        String year = mMovieData.getReleaseDate().substring(0, 4);
        yearView.setText(year);
    }

    public void setRating() {
        TextView ratingView = (TextView) findViewById(R.id.tv_details_rating);
        String rating = mMovieData.getVoteAverage() + " / 10";
        Log.i(TAG, "setRating: " + rating);
        ratingView.setText(rating);

    }

    public void setOverview() {
        TextView overviewView = (TextView) findViewById(R.id.tv_details_overview);
        overviewView.setText(mMovieData.getOverview());
    }


}