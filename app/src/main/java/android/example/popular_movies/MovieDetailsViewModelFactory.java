package android.example.popular_movies;

import android.example.popular_movies.database.AppDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MovieDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase mDb;
    private final String mId;

    public MovieDetailsViewModelFactory(AppDatabase database, String id) {
        mDb = database;
        mId = id;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetailsViewModel(mDb, mId);
    }
}
