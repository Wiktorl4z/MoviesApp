package pl.futuredev.popularmoviesudacitynd;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.futuredev.popularmoviesudacitynd.adapter.ReviewAdapter;
import pl.futuredev.popularmoviesudacitynd.adapter.TrailerAdapter;
import pl.futuredev.popularmoviesudacitynd.data.MoviesContract;
import pl.futuredev.popularmoviesudacitynd.models.Movie;
import pl.futuredev.popularmoviesudacitynd.models.Review;
import pl.futuredev.popularmoviesudacitynd.models.ReviewList;
import pl.futuredev.popularmoviesudacitynd.models.Trailer;
import pl.futuredev.popularmoviesudacitynd.models.TrailerList;
import pl.futuredev.popularmoviesudacitynd.service.APIService;
import pl.futuredev.popularmoviesudacitynd.service.HttpConnector;
import pl.futuredev.popularmoviesudacitynd.utils.UrlManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static pl.futuredev.popularmoviesudacitynd.data.MoviesContract.BASE_CONTENT_URI;
import static pl.futuredev.popularmoviesudacitynd.data.MoviesContract.PATH_MOVIES;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {

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
    @BindView(R.id.recyclerViewForTrailers)
    RecyclerView recyclerViewForTrailers;
    @BindView(R.id.recyclerViewForReviews)
    RecyclerView recyclerViewForReviews;
    @BindView(R.id.tv_imageView)
    ImageView tvImageView;
    @BindView(R.id.tv_popularity)
    TextView tvPopularity;

    private SQLiteOpenHelper mDbHelper;
    private Cursor mData;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private List<TrailerList> trailerList;
    private List<ReviewList> reviewList;
    private int movieId;
    private static boolean isFavourite;
    private APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_detail);
        ButterKnife.bind(this);

        supportPostponeEnterTransition();

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");
        movieId = movie.getId();
        populateUI(movie);

        service = HttpConnector.getService(APIService.class);
        gettingObjectsForTrailer();
        gettingObjectsForReview();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        colapingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        LinearLayoutManager trailerLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewForTrailers.setLayoutManager(trailerLayoutManager);
        recyclerViewForTrailers.setHasFixedSize(true);

        recyclerViewForReviews.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewForReviews.setHasFixedSize(true);

        new ContentProviderAsyncTask().execute();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        if (isFavourite) {
                            int position = deletingFavouriteState();
                            if (position == 1)
                                isFavourite = false;
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
                        if (!isFavourite) {
                            Toast.makeText(DetailActivity.this, R.string.add_to_fav, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailActivity.this, R.string.remove_from_fav, Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();
            }
        });
    }

    private void gettingObjectsForTrailer() {
        service.getTrailer("" + movieId).enqueue(new Callback<Trailer>() {
            @Override
            public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                responseForTrailer(response);
            }

            @Override
            public void onFailure(Call<Trailer> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    ;

    private void responseForTrailer(Response<Trailer> response) {
        if (response.isSuccessful()) {
            trailerList = response.body().results;
            trailerAdapter = new TrailerAdapter(trailerList, DetailActivity.this::onClick);
            recyclerViewForTrailers.setAdapter(trailerAdapter);
        } else {
            try {
                Toast.makeText(DetailActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT)
                        .show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    ;

    private void gettingObjectsForReview() {
        service.getReview("" + movieId).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                responseForReview(response);
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    ;

    private void responseForReview(Response<Review> response) {
        if (response.isSuccessful()) {
            reviewList = response.body().results;
            reviewAdapter = new ReviewAdapter(reviewList, DetailActivity.this::onClick);
            recyclerViewForReviews.setAdapter(reviewAdapter);
        } else {
            try {
                Toast.makeText(DetailActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT)
                        .show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    ;

    private int deletingFavouriteState() {
        ContentResolver resolver = getContentResolver();
        Uri uri = getUri();
        int deleted = resolver.delete(uri, null, null);
        return deleted;
    }

    private Uri getUri() {
        return BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .appendPath(movieId + "")
                .build();
    }

    private Uri insertingIntoDataBase(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MoviesDateBase.MOVIE_TITLE, movie.getTitle());
        contentValues.put(MoviesContract.MoviesDateBase.MOVIE_ID, movie.getId());
        contentValues.put(MoviesContract.MoviesDateBase.MOVIE_POSTER_PATCH, movie.getPosterPath());

        Uri uri = getContentResolver().insert(MoviesContract.MoviesDateBase.CONTENT_URI, contentValues);

        if (uri != null) {
            return uri;
        }
        return null;
    }

    private void populateUI(Movie movie) {
        String imageUrl = UrlManager.IMAGE_BASE_URL;
        colapingToolbarLayout.setTitle(movie.getTitle());
        tvReleaseDate.setText(movie.getReleaseDate());
        Picasso.get().load(imageUrl + movie.getBackdropPath()).into(ivCollapsing);
        tvPlotSynopsis.setText(movie.getOverview());
        tvVoteAverage.setText("" + movie.getVoteAverage() + "/10");
        tvPopularity.setText(movie.getPopularity() + " votes");
        Picasso.get().load(imageUrl + movie.getPosterPath()).into(tvImageView);
    }

    private class ContentProviderAsyncTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            ContentResolver resolver = getContentResolver();
            Uri CONTENT_URI = getUri();
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

    @Override
    public void onClick(int clickedItemIndex) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlManager.YOUTUBE_URL + trailerList.get(clickedItemIndex).getKey()));
        startActivity(intent);
    }
}

