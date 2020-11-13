package android.example.popular_movies;

import android.app.Application;
import android.example.popular_movies.database.AppDatabase;
import android.example.popular_movies.modules.MovieData;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MovieDetailsViewModel extends ViewModel {
    private LiveData<MovieData> movie;

    public MovieDetailsViewModel(AppDatabase database, String id) {
        movie = database.favoriteMovieDAO().loadFavoriteById(id);
    }

    public LiveData<MovieData> getMovie() {
        return movie;
    }
}
