package android.example.popular_movies;

import android.content.Context;
import android.example.popular_movies.utilities.NetworkUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {
    public interface PosterItemClickListener {
        void onPosterItemClick(int clickedPosterIndex);
    }

    private JSONArray mMoviesData;
    private PosterItemClickListener mOnClickListener;

    public PosterAdapter(JSONArray moviesData, PosterItemClickListener listener) {
        mMoviesData = moviesData;
        mOnClickListener = listener;
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

    class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "PosterViewHolder";
        ImageView listItemPosterView;

        public PosterViewHolder(View itemView) {
            super(itemView);
            listItemPosterView = (ImageView) itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) throws JSONException {
            JSONObject movieData = mMoviesData.getJSONObject(listIndex);
            String posterPath = movieData.getString("poster_path");
            URL imageUrl = NetworkUtils.buildImageUrl(posterPath);
            Picasso.get().load(String.valueOf(imageUrl)).into(listItemPosterView);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onPosterItemClick(clickedPosition);
        }
    }
}
