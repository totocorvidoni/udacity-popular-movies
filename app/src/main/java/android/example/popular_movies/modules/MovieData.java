package android.example.popular_movies.modules;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieData implements Parcelable {
    private String id;
    private String title;
    private String posterPath;
    private String overview;
    private double voteAverage;
    private String releaseDate;

    public MovieData(JSONObject data) {
        try {
            this.id = data.getString("id");
            this.title = data.getString("title");
            this.posterPath = data.getString("poster_path");
            this.overview = data.getString("overview");
            this.voteAverage = data.getDouble("vote_average");
            this.releaseDate = data.getString("release_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public MovieData(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.voteAverage = in.readDouble();
        this.releaseDate = in.readString();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage + " / 10";
    }

    public String getReleaseDate() {
        return releaseDate.substring(0, 4);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MovieData> CREATOR
            = new Parcelable.Creator<MovieData>() {
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.releaseDate);
    }
}
