package pl.futuredev.popularmoviesudacitynd;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.futuredev.popularmoviesudacitynd.adapter.FavouriteAdapter;
import pl.futuredev.popularmoviesudacitynd.adapter.MovieAdapter;
import pl.futuredev.popularmoviesudacitynd.data.MoviesContract;
import pl.futuredev.popularmoviesudacitynd.models.Movie;
import pl.futuredev.popularmoviesudacitynd.models.MovieList;
import pl.futuredev.popularmoviesudacitynd.service.APIService;
import pl.futuredev.popularmoviesudacitynd.service.HttpConnector;
import pl.futuredev.popularmoviesudacitynd.utils.UrlManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;
    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private APIService service;
    private MovieList movieList;
    private List<Movie> movie;
    private static final String CLASS_TAG = "TestActivity";
    private Toast toast;
    private FavouriteAdapter favouriteAdapter;
    private int mPosition = RecyclerView.NO_POSITION;
    private static final int ID_DETAIL_LOADER = 300;
    public static final String[] FAVOURITE_MOVIE_TABLE = {
            MoviesContract.MoviesDateBase.MOVIE_TITLE,
            MoviesContract.MoviesDateBase.MOVIE_POSTER_PATCH,
            MoviesContract.MoviesDateBase.RELEASE_DATE,
            MoviesContract.MoviesDateBase.VOTE_AVERAGE,
            MoviesContract.MoviesDateBase.VOTE_COUNT
    };
    public static final int MOVIE_TITLE = 0;
    public static final int MOVIE_POSTER_PATCH = 1;
    public static final int RELEASE_DATE = 2;
    public static final int VOTE_AVERAGE = 3;
    public static final int VOTE_COUNT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (UrlManager.API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.api_key_message, Toast.LENGTH_LONG).show();
        }

        service = HttpConnector.getService(APIService.class);
        popularMoviesListFromService();
    }

    private void popularMoviesListFromService() {
        service.getPopularMoviesList().enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                settingUpView(response);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    ;

    private void topRatedMoviesFromService() {
        service.getTopRatedMovies().enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                settingUpView(response);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void settingUpView(Response<MovieList> response) {
        if (response.isSuccessful()) {
            movie = response.body().results;
            adapter = new MovieAdapter(movie, MainActivity.this::onClick);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        } else {
            try {
                Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT)
                        .show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    ;

    private void favouriteMovies() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        favouriteAdapter = new FavouriteAdapter(this);
        recyclerView.setAdapter(favouriteAdapter);
        showLoading();
        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER, null, this);
    }

    ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.top_rated:
                topRatedMoviesFromService();
                return true;
            case R.id.popular:
                popularMoviesListFromService();
                return true;
            case R.id.favourite:
                favouriteMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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


    @Override
    public void onClick(int clickedItemIndex) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("movie", movie.get(clickedItemIndex));
        startActivity(intent);
    }

}