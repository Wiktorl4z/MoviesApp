package pl.futuredev.popularmoviesudacitynd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.futuredev.popularmoviesudacitynd.models.Movie;

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
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movie");
        populateUI(movie);
    }

    private void populateUI(Movie movie){

        tvTitle.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getReleaseDate());

        Picasso.get().load(movie.getBackdropPath()).into(ivMoviePoster);

        tvPlotSynopsis.setText(movie.getOverview());
        tvVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
    }

}
