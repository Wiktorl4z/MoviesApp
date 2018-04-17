package pl.futuredev.popularmoviesudacitynd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.futuredev.popularmoviesudacitynd.R;
import pl.futuredev.popularmoviesudacitynd.models.Movie;
import pl.futuredev.popularmoviesudacitynd.utils.UrlManager;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> data;
    private final MovieAdapterOnClickHandler listClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(int clickedItemIndex);
    }

    public MovieAdapter(List<Movie> data, MovieAdapterOnClickHandler listClickHandler) {
        this.data = data;
        this.listClickHandler = listClickHandler;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.iv_image_single);
            this.progressBar = itemView.findViewById(R.id.progressBar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickPosition = getAdapterPosition();
            listClickHandler.onClick(clickPosition);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_layout, parent, false);
        view.setFocusable(true);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {

        ImageView imageView = holder.imageView;
        ProgressBar progressBar = holder.progressBar;

        String imageUrl = UrlManager.IMAGE_BASE_URL;
        String urlId = imageUrl + data.get(listPosition).getPosterPath();

        progressBar.setVisibility(View.GONE);
        Picasso.get().load(urlId).into(imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
