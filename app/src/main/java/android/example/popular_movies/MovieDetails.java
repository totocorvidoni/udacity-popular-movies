package android.example.popular_movies;

import androidx.appcompat.app.AppCompatActivity;

import android.example.popular_movies.modules.MovieData;
import android.os.Bundle;
import android.util.Log;

public class MovieDetails extends AppCompatActivity {
    MovieData mMovieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle data = getIntent().getExtras();
        mMovieData = (MovieData) data.getParcelable("movieData");


    }
}