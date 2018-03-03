package pl.futuredev.popularmoviesudacitynd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.futuredev.popularmoviesudacitynd.models.Movie;
import pl.futuredev.popularmoviesudacitynd.utils.UrlManager;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> data;

    public MovieAdapter(List<Movie> data) {
        this.data = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder.IMyViewHolderClicks mListener;
        ImageView imageView;

        public ViewHolder(View itemView, IMyViewHolderClicks vegetables) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.iv_image_single);
        }

        public static interface IMyViewHolderClicks {
            public void onImage(ImageView callerImage);
        }

        @Override
        public void onClick(View v) {
            if (v instanceof ImageView) {
                mListener.onImage((ImageView) v);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_layout, parent, false);

        ViewHolder vh = new ViewHolder(view, new ViewHolder.IMyViewHolderClicks() {

            @Override
            public void onImage(ImageView callerImage) {
                Log.d("Working Image", "I-M-A-G-E");
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {

        ImageView imageView = holder.imageView;

        String imageUrl = UrlManager.IMAGE_BASE_URL;
        String urlId = imageUrl + data.get(listPosition).getBackdropPath();

        Context context = holder.imageView.getContext();

        Picasso.with(context).load(urlId).into(imageView);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
