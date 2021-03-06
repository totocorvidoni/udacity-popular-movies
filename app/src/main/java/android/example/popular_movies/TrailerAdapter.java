package android.example.popular_movies;
import android.content.Context;
import android.example.popular_movies.utilities.NetworkUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    public interface TrailerItemClickListener {
        void onTrailerItemClick(int clickedPosterIndex);
    }

    private final JSONArray mTrailersData;
    private final TrailerItemClickListener mOnClickListener;

    public TrailerAdapter(JSONArray trailersData, TrailerItemClickListener listener) {
        mTrailersData = trailersData;
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        try {
            holder.bind(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (mTrailersData == null) {
            return 0;
        }
        return mTrailersData.length();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "TrailerViewHolder";
        TextView trailerNameTextView;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            trailerNameTextView = itemView.findViewById(R.id.tv_trailer_item_name);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) throws JSONException {
            JSONObject trailerData = mTrailersData.getJSONObject(listIndex);
            String trailerName = trailerData.getString("name");

            trailerNameTextView.setText(trailerName);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onTrailerItemClick(clickedPosition);
        }
    }
}
