package pl.futuredev.popularmoviesudacitynd.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.futuredev.popularmoviesudacitynd.FavouriteActivity;
import pl.futuredev.popularmoviesudacitynd.R;
import pl.futuredev.popularmoviesudacitynd.models.Movie;
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

        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.textView = itemView.findViewById(R.id.textView);
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
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
        TextView titleTextView = holder.textView;
        ImageView imageView = holder.imageView;
        mCursor.moveToPosition(position);
        String title = mCursor.getString(FavouriteActivity.MOVIE_TITLE);
        titleTextView.setText(title);

        String imageUrl = UrlManager.IMAGE_BASE_URL;
        String urlId = imageUrl + mCursor.getString(FavouriteActivity.MOVIE_POSTER_PATCH);

        Picasso.get().load(urlId).into(imageView);

    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }
}

