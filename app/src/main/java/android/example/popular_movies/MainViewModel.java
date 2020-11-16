package android.example.popular_movies;

import android.app.Application;
import android.example.popular_movies.modules.Movie;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private List<Movie> movies;

    public MainViewModel() {}

    public List<Movie> getMovies() {
        return  movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
