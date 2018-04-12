package pl.futuredev.popularmoviesudacitynd.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MoviesContract {

    public static final String AUTHORITY = "pl.futuredev.popularmoviesudacitynd";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class MoviesDateBase implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        public static final String TABLE_NAME = "favouriteList";
        public static final String MOVIE_TITLE = "movieTitle";
        public static final String MOVIE_ID = "movieId";

    }

}
