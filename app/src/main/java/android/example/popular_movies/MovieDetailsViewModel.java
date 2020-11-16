package android.example.popular_movies;

import android.example.popular_movies.database.AppDatabase;
import android.example.popular_movies.modules.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MovieDetailsViewModel extends ViewModel {
    final private LiveData<Movie> movie;

    public MovieDetailsViewModel(AppDatabase database, String id) {
        movie = database.favoriteMovieDAO().loadFavoriteById(id);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
