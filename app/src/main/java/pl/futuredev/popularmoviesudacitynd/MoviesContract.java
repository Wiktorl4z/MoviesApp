package pl.futuredev.popularmoviesudacitynd;

import android.provider.BaseColumns;

public class MoviesContract {


    public static final class MoviesDateBase implements BaseColumns {

        public static final String MOVIE_TITLE = "title";
        public static final String COLUMN_GUEST_NAME = "guestName";
        public static final String COLUMN_PARTY_SIZE = "partySize";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }
}
