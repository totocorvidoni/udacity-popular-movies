package android.example.popular_movies;

import android.content.Context;
import android.example.popular_movies.modules.Movie;
import android.example.popular_movies.utilities.NetworkUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.net.URL;
import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {
    public interface PosterItemClickListener {
        void onPosterItemClick(int clickedPosterIndex);
    }

    private final List<Movie> mMovies;
    private final PosterItemClickListener mOnClickListener;

    public PosterAdapter(List<Movie> movies, PosterItemClickListener listener) {
        mMovies = movies;
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
        if (mMovies == null) {
            return 0;
        }
        return mMovies.size();
    }

    class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "PosterViewHolder";
        final ImageView listItemPosterView;

        public PosterViewHolder(View itemView) {
            super(itemView);
            listItemPosterView = itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) throws JSONException {
            Movie movie = mMovies.get(listIndex);
            String posterPath = movie.getPosterPath();
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
