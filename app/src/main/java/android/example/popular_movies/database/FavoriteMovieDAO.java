package android.example.popular_movies.database;

import android.example.popular_movies.modules.MovieData;

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
    LiveData<List<MovieData>> loadAllFavorites();

    @Query("SELECT * FROM favorite_movie WHERE id=:id")
    LiveData<MovieData> loadFavoriteById(String id);

    @Insert
    void insertFavorite(MovieData movieData);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(MovieData movieData);

    @Delete
    void deleteFavorite(MovieData movieData);
}
