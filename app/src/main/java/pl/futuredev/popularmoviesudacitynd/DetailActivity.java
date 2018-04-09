package pl.futuredev.popularmoviesudacitynd;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
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

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.futuredev.popularmoviesudacitynd.data.MoviesContract;
import pl.futuredev.popularmoviesudacitynd.data.MoviesProvider;
import pl.futuredev.popularmoviesudacitynd.models.Movie;
import pl.futuredev.popularmoviesudacitynd.utils.UrlManager;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_vote_average)
    TextView tvVoteAverage;
    @BindView(R.id.favoriteBox)
    CheckBox favoriteBox;
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
    private int mMovieID;

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

        new ContentProviderAsyncTask().execute();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                if (mMovieID == movie.getId()) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color
                            .parseColor("##eaf6f7")));
                } else {
                    insertingIntoDataBase(movie);
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color
                            .parseColor("#ff0000")));
                }
            }
        });

    }

    private void readingFromDataBase() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                MoviesContract.MoviesDateBase._ID
        };

        Cursor cursor = db.query(
                MoviesContract.MoviesDateBase.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }

    private void insertingIntoDataBase(Movie movie) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MoviesContract.MoviesDateBase.MOVIE_TITLE, movie.getTitle());
        values.put(MoviesContract.MoviesDateBase.MOVIE_ID, movie.getId());
        values.put(MoviesContract.MoviesDateBase.MOVIE_IMAGE, movie.getPosterPath());

        long newRowId = db.insert(MoviesContract.MoviesDateBase.TABLE_NAME, null, values);
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

            Cursor cursor = resolver.query(MoviesContract.BASE_CONTENT_URI,
                    null, null, null, null);
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            mData = cursor;
            mMovieID = mData.getColumnIndex(MoviesContract.MoviesDateBase.MOVIE_ID);
            nextWord();
        }

        public void nextWord() {
            if (mData != null) {
                if (!mData.moveToNext()) {
                    mData.moveToFirst();
                }
            }
        }
    }

}
