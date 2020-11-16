package android.example.popular_movies.database;

import android.example.popular_movies.modules.Movie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteMovieDAO {
    @Query("SELECT * FROM favorite_movie ORDER BY releaseDate")
    LiveData<List<Movie>> loadAllFavorites();

    @Query("SELECT * FROM favorite_movie WHERE id=:id")
    LiveData<Movie> loadFavoriteById(String id);

    @Insert
    void insertFavorite(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(Movie movie);

    @Delete
    void deleteFavorite(Movie movie);
}
