package android.example.popular_movies.utilities;

import android.example.popular_movies.BuildConfig;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {
    final static String BASE_URL = "https://api.themoviedb.org/3/";
    final static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p";
    final static String PARAM_API_KEY = "api_key";
    final static String PARAM_MINIMUM_RATING = "vote_average.gte";
    final static String PARAM_MINIMUM_VOTES = "vote_count.gte";
    final static String PARAM_SORT = "sort_by";
    final static String PATH_DISCOVER = "discover";
    final static String PATH_IMAGE_SIZE = "w185";
    final static String PATH_MOVIE = "movie";
    final static String VALUE_API_KEY = BuildConfig.MovieApiKey;
    final static String VALUE_MINIMUM_RATING = "4";
    final static String VALUE_MINIMUM_VOTES = "1000";
    final static String VALUE_SORT_POPULARITY = "popularity.desc";
    final static String VALUE_SORT_RATING = "vote_average.desc";

    public static URL buildDiscoverUrl(String sortBy) {
        String sortValue;
        if (sortBy.equals("rating")) {
            sortValue = VALUE_SORT_RATING;
        } else {
            sortValue = VALUE_SORT_POPULARITY;
        }

        Uri.Builder uriBuilder = Uri.parse(BASE_URL).buildUpon()
                .appendPath(PATH_DISCOVER)
                .appendPath(PATH_MOVIE)
                .appendQueryParameter(PARAM_API_KEY, VALUE_API_KEY)
                .appendQueryParameter(PARAM_SORT, sortValue);

        if (sortBy.equals("rating")) {
            uriBuilder.appendQueryParameter(PARAM_MINIMUM_RATING, VALUE_MINIMUM_RATING)
                    .appendQueryParameter(PARAM_MINIMUM_VOTES, VALUE_MINIMUM_VOTES);
        }

        Uri builtUri = uriBuilder.build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildImageUrl(String filePath) {
        Uri builtUri = Uri.parse(BASE_IMAGE_URL).buildUpon()
                .appendPath(PATH_IMAGE_SIZE)
                .appendEncodedPath(filePath)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    // https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
