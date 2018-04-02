package pl.futuredev.popularmoviesudacitynd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class MoviesContract {

    public static final class MoviesDateBase implements BaseColumns {

        public static final String TABLE_NAME = "favouriteList";
        public static final String MOVIE_TITLE = "movieTitle";
        public static final String MOVIE_ID = "movieId";
        public static final String MOVIE_IMAGE = "moveImage";

    }

    public class MoviesDbHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "favouriteMovieList.db";

        private static final int DATABASE_VERSION = 1;

        public MoviesDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String SQL_CREATE_FAV_TABLE = "CREATE TABLE " + MoviesDateBase.TABLE_NAME + " (" +
                    MoviesDateBase.MOVIE_ID + " INTEGER NOT NULL, " +
                    MoviesDateBase.MOVIE_TITLE + " TEXT NOT NULL, " +
                    MoviesDateBase.MOVIE_IMAGE + " INTEGER NOT NULL " +
                    ");";

            db.execSQL(SQL_CREATE_FAV_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + MoviesDateBase.TABLE_NAME);
            onCreate(db);
        }
    }
}
