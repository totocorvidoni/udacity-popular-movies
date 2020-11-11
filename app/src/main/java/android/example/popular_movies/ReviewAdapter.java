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

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private final JSONArray mReviewData;

    public ReviewAdapter(JSONArray trailersData) {
        mReviewData = trailersData;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        try {
            holder.bind(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (mReviewData == null) {
            return 0;
        }
        return mReviewData.length();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ReviewViewHolder";
        final TextView authorTextView;
        final TextView contentTextView;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.tv_review_author);
            contentTextView = itemView.findViewById(R.id.tv_review_content);
        }

        void bind(int listIndex) throws JSONException {
            JSONObject reviewData = mReviewData.getJSONObject(listIndex);
            String author = reviewData.getString("author");
            String content = reviewData.getString("content");

            authorTextView.setText(author);
            contentTextView.setText(content);
        }
    }
}
