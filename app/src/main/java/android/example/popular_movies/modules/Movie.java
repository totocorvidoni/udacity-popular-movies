package android.example.popular_movies.modules;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "favorite_movie")
public class Movie implements Parcelable {
    @PrimaryKey @NonNull
    private String id;
    private String title;
    private String posterPath;
    private String overview;
    private String voteAverage;
    private String releaseDate;

    public Movie() { }

    @Ignore
    public Movie(JSONObject data) {
        try {
            this.id = data.getString("id");
            this.title = data.getString("title");
            this.posterPath = data.getString("poster_path");
            this.overview = data.getString("overview");
            this.voteAverage = data.getString("vote_average");
            this.releaseDate = data.getString("release_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    public Movie(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.voteAverage = in.readString();
        this.releaseDate = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeString(this.voteAverage);
        dest.writeString(this.releaseDate);
    }
}
