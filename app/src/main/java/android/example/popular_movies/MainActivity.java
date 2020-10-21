package android.example.popular_movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_LIST_ITEMS = 100;
    private PosterAdapter mAdapter;
    private RecyclerView mPosterList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MainActivity", BuildConfig.MovieApiKey);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPosterList = (RecyclerView) findViewById(R.id.rv_posters);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mPosterList.setLayoutManager(layoutManager);

        mPosterList.setHasFixedSize(true);

        mAdapter = new PosterAdapter(NUM_LIST_ITEMS);
        mPosterList.setAdapter(mAdapter);
    }

    void makeTopMoviesQuery() {

    }
}