package android.example.popular_movies.database;

import android.content.Context;
import android.example.popular_movies.modules.Movie;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "AppDatabase";
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "popular_movies";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME
                )
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }

        Log.d(TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract FavoriteMovieDAO favoriteMovieDAO();
}
