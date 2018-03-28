package pl.futuredev.popularmoviesudacitynd;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setBackgroundTintList(ColorStateList.valueOf(Color
                        .parseColor("#ff0000")));
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void populateUI(Movie movie) {

        String imageUrl = UrlManager.IMAGE_BASE_URL;

        colapingToolbarLayout.setTitle(movie.getTitle());
        tvReleaseDate.setText(getString(R.string.release_date) + movie.getReleaseDate());

        Picasso.get().load(imageUrl + movie.getBackdropPath()).into(ivCollapsing);

        tvPlotSynopsis.setText(getString(R.string.plot_synopsis) + movie.getOverview());
        tvVoteAverage.setText(getString(R.string.vote_average) + movie.getVoteAverage());
    }
}
