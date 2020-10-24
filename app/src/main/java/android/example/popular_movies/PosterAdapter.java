package android.example.popular_movies;

import android.content.Context;
import android.example.popular_movies.utilities.NetworkUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {
    private JSONArray mMoviesData;

    public PosterAdapter(JSONArray moviesData) {
        mMoviesData = moviesData;
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.poster_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        try {
            holder.bind(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mMoviesData.length();
    }

    class PosterViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "PosterViewHolder";
        ImageView listItemPosterView;

        public PosterViewHolder(View itemView) {
            super(itemView);
            listItemPosterView = (ImageView) itemView.findViewById(R.id.iv_poster);
        }

        void bind(int listIndex) throws JSONException {
            JSONObject movieData = mMoviesData.getJSONObject(listIndex);
            String posterPath = movieData.getString("poster_path");
            URL imageUrl = NetworkUtils.buildPosterImageUrl(posterPath);
            Log.i(TAG, "bind: " + imageUrl);
            Picasso.get().load(String.valueOf(imageUrl)).into(listItemPosterView);
        }
    }
}
