package pl.futuredev.popularmoviesudacitynd;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.futuredev.popularmoviesudacitynd.adapter.FavouriteAdapter;
import pl.futuredev.popularmoviesudacitynd.data.MoviesContract;

public class FavouriteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_DETAIL_LOADER = 300;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;
    private Uri mUri;
    private FavouriteAdapter favouriteAdapter;
    private int mPosition = RecyclerView.NO_POSITION;

    public static final String[] FAVOURITE_MOVIE_TABLE = {
            MoviesContract.MoviesDateBase.MOVIE_TITLE,
            MoviesContract.MoviesDateBase.MOVIE_POSTER_PATCH
    };

    public static final int MOVIE_TITLE = 0;
    public static final int MOVIE_POSTER_PATCH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        favouriteAdapter = new FavouriteAdapter(this);
        recyclerView.setAdapter(favouriteAdapter);
        showLoading();

        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_DETAIL_LOADER:

                Uri queryUri = MoviesContract.MoviesDateBase.CONTENT_URI;

                return new CursorLoader(this,
                        queryUri,
                        FAVOURITE_MOVIE_TABLE,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        favouriteAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        recyclerView.smoothScrollToPosition(mPosition);
        if (data.getCount() != 0) showFavouriteMovies();
    }

    private void showFavouriteMovies() {
        loadingIndicator.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        recyclerView.setVisibility(View.INVISIBLE);
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        favouriteAdapter.swapCursor(null);
    }
}
