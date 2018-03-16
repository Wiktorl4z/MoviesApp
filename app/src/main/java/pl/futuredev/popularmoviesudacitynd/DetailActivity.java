package pl.futuredev.popularmoviesudacitynd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.futuredev.popularmoviesudacitynd.models.Movie;
import pl.futuredev.popularmoviesudacitynd.utils.UrlManager;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_movie_poster)
    ImageView ivMoviePoster;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_vote_average)
    TextView tvVoteAverage;
    @BindView(R.id.tv_plot_synopsis)
    TextView tvPlotSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");
        populateUI(movie);
    }

    private void populateUI(Movie movie) {

        String imageUrl = UrlManager.IMAGE_BASE_URL;

        tvTitle.setText(getString(R.string.title) + movie.getTitle());
        tvReleaseDate.setText(getString(R.string.release_date) + movie.getReleaseDate());

        Picasso.get().load(imageUrl + movie.getBackdropPath()).into(ivMoviePoster);

        tvPlotSynopsis.setText(getString(R.string.plot_synopsis) + movie.getOverview());
        tvVoteAverage.setText(getString(R.string.vote_average) + movie.getVoteAverage());
    }
}
