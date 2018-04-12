package pl.futuredev.popularmoviesudacitynd.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static pl.futuredev.popularmoviesudacitynd.data.MoviesContract.MoviesDateBase.TABLE_NAME;

public class MoviesProvider extends ContentProvider{

    public static final int CODE_MOVIE = 100;
    public static final int CODE_MOVIE_WITH_ID = 101;

    private MoviesDbHelper moviesDbHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoviesContract.AUTHORITY;

        uriMatcher.addURI(authority, MoviesContract.PATH_MOVIES, CODE_MOVIE);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_MOVIES + "/#", CODE_MOVIE_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        moviesDbHelper = new MoviesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = moviesDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor;

        switch (match) {
            case CODE_MOVIE:
                cursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
                case CODE_MOVIE_WITH_ID:
                    String id = uri.getPathSegments().get(1);
                    String mSelection = "_id=?";
                    String[] mSelectionArgs = new String[]{id};

                    cursor = db.query(TABLE_NAME,
                            projection,
                            mSelection,
                            mSelectionArgs,
                            null,
                            null,
                            sortOrder);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);

        switch (match) {
            case CODE_MOVIE:
                return "vnd.android.cursor.dir" + "/" +
                        MoviesContract.AUTHORITY + "/" + MoviesContract.PATH_MOVIES;
            case CODE_MOVIE_WITH_ID:
                return "vnd.android.cursor.item" + "/" +
                        MoviesContract.AUTHORITY + "/" + MoviesContract.PATH_MOVIES;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case CODE_MOVIE:
                long id = db.insert(TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(MoviesContract.MoviesDateBase.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;
        final SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int movieDeleted;
        switch (match) {
            case CODE_MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};
                movieDeleted = db.delete(TABLE_NAME, mSelection, mSelectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (movieDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return movieDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
