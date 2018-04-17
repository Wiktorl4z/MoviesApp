package pl.futuredev.popularmoviesudacitynd;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.futuredev.popularmoviesudacitynd.data.MoviesContract;
import pl.futuredev.popularmoviesudacitynd.models.Movie;
import pl.futuredev.popularmoviesudacitynd.utils.UrlManager;

import static pl.futuredev.popularmoviesudacitynd.data.MoviesContract.BASE_CONTENT_URI;
import static pl.futuredev.popularmoviesudacitynd.data.MoviesContract.PATH_MOVIES;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_vote_average)
    TextView tvVoteAverage;
    @BindView(R.id.tv_plot_synopsis)
    TextView tvPlotSynopsis;
    @BindView(R.id.iv_collapsing)
    ImageView ivCollapsing;
    @BindView(R.id.colapingToolbarLayout)
    CollapsingToolbarLayout colapingToolbarLayout;
    @BindView(R.id.toolbarid)
    Toolbar toolbarid;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private SQLiteOpenHelper mDbHelper;
    private Cursor mData;
    private int movieId;
    static boolean isFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_detail);
        ButterKnife.bind(this);

        supportPostponeEnterTransition();

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");
        populateUI(movie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        colapingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        FloatingActionButton fab = findViewById(R.id.fab);
        movieId = movie.getId();
        new ContentProviderAsyncTask().execute();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        if (isFavourite) {
                            int position = deletingFavouriteState();
                            if (position == 1) {
                                isFavourite = false;
                            } else {
                                isFavourite = true;
                            }
                        } else {
                            insertingIntoDataBase(movie);
                            isFavourite = true;
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        colorSwicherForFAB();
                    }
                }.execute();
                if (!isFavourite) {
                    Toast.makeText(DetailActivity.this, R.string.add_to_fav, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailActivity.this, R.string.remove_from_fav, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int deletingFavouriteState() {
        ContentResolver resolver = getContentResolver();
        Uri uri = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .appendPath(movieId + "")
                .build();
        int deleted = resolver.delete(uri, null, null);
        return deleted;
    }

    private Uri insertingIntoDataBase(Movie movie) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MoviesDateBase.MOVIE_TITLE, movie.getTitle());
        contentValues.put(MoviesContract.MoviesDateBase.MOVIE_ID, movie.getId());
        //  contentValues.put(MoviesContract.MoviesDateBase.MOVIE_IMAGE, movie.getPosterPath());

        Uri uri = getContentResolver().insert(MoviesContract.MoviesDateBase.CONTENT_URI, contentValues);

        if (uri != null) {
            return uri;
        }
        return null;
    }

    private void populateUI(Movie movie) {
        String imageUrl = UrlManager.IMAGE_BASE_URL;
        colapingToolbarLayout.setTitle(movie.getTitle());
        tvReleaseDate.setText(getString(R.string.release_date) + movie.getReleaseDate());
        Picasso.get().load(imageUrl + movie.getBackdropPath()).into(ivCollapsing);
        tvPlotSynopsis.setText(getString(R.string.plot_synopsis) + movie.getOverview());
        tvVoteAverage.setText(getString(R.string.vote_average) + movie.getVoteAverage());
    }

    private class ContentProviderAsyncTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            ContentResolver resolver = getContentResolver();
            Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_MOVIES)
                    .appendPath(movieId + "")
                    .build();
            Cursor cursor = resolver.query(CONTENT_URI,
                    null, null, null, null);
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            if (cursor.getCount() != 0) {
                isFavourite = true;
            } else {
                isFavourite = false;
            }
            colorSwicherForFAB();
        }
    }

    private void colorSwicherForFAB() {
        if (isFavourite) {
            fab.setBackgroundTintList(ColorStateList.valueOf(Color
                    .parseColor("#ff0000")));
        } else {
            fab.setBackgroundTintList(ColorStateList.valueOf(Color
                    .parseColor("#cdf7fb")));
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}
