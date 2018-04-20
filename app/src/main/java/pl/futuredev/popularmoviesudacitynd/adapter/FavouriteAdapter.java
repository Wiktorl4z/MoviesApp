package pl.futuredev.popularmoviesudacitynd.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pl.futuredev.popularmoviesudacitynd.MainActivity;
import pl.futuredev.popularmoviesudacitynd.R;
import pl.futuredev.popularmoviesudacitynd.utils.UrlManager;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private final Context mContext;
    private Cursor mCursor;

    public FavouriteAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvReleaseDate;
        ImageView tv_imageView;
        TextView titleText;
        RatingBar ratingBar;
        TextView tvVoteAverage;
        TextView tvVoteCount;

        public ViewHolder(View itemView) {
            super(itemView);
            this.titleText = itemView.findViewById(R.id.tv_title_text);
            this.tv_imageView = itemView.findViewById(R.id.tv_imageView);
            this.tvReleaseDate = itemView.findViewById(R.id.tv_release_date);
            this.ratingBar = itemView.findViewById(R.id.rating_bar);
            this.tvVoteAverage = itemView.findViewById(R.id.tv_vote_average);
            this.tvVoteCount = itemView.findViewById(R.id.tv_vote_count);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.content_favourite, parent, false);
        view.setFocusable(true);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView titleTextView = holder.titleText;
        ImageView imageView = holder.tv_imageView;
        TextView tvReleaseDate = holder.tvReleaseDate;
        RatingBar ratingBar = holder.ratingBar;
        TextView tvVoteAverage = holder.tvVoteAverage;
        TextView tvVoteCount = holder.tvVoteCount;

        mCursor.moveToPosition(position);
        String title = mCursor.getString(MainActivity.MOVIE_TITLE);
        titleTextView.setText(title);

        String votes = mCursor.getString(MainActivity.VOTE_AVERAGE);

        String release= mCursor.getString(MainActivity.RELEASE_DATE);
        String year = release.substring(0, Math.min(release.length(), 4));
        tvReleaseDate.setText(year);

        ratingBar.setRating(settingRatingBar(votes));
        tvVoteAverage.setText(mCursor.getString(MainActivity.VOTE_AVERAGE)+ mContext.getString(R.string.scores));
        tvVoteCount.setText(mCursor.getString(MainActivity.VOTE_COUNT)+ mContext.getString(R.string.votes));

        String imageUrl = UrlManager.IMAGE_BASE_URL;
        String urlId = imageUrl + mCursor.getString(MainActivity.MOVIE_POSTER_PATCH);
        Picasso.get().load(urlId).into(imageView);

    }

    private int settingRatingBar(String votes) {
        int L = Math.round(Float.parseFloat(votes));
        int i = Integer.valueOf(L);
        int voteScore = i / 2;
        return voteScore;
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }
}

