package android.example.popular_movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.example.popular_movies.databinding.ActivityMovieDetailsBinding;
import android.example.popular_movies.modules.MovieData;
import android.example.popular_movies.utilities.NetworkUtils;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;

public class MovieDetails extends AppCompatActivity {
    MovieData mMovieData;
    ActivityMovieDetailsBinding mDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle data = getIntent().getExtras();
        mMovieData = data.getParcelable("movieData");

        mDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        mDetailsBinding.setMovie(mMovieData);

        setPoster();
    }

    public void setPoster() {
        ImageView posterView = findViewById(R.id.iv_details_poster);
        URL posterURL = NetworkUtils.buildImageUrl(mMovieData.getPosterPath());

        Picasso.get().load(String.valueOf(posterURL)).into(posterView);
    }

}