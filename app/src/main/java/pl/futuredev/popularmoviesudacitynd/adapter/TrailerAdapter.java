package pl.futuredev.popularmoviesudacitynd.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import pl.futuredev.popularmoviesudacitynd.R;
import pl.futuredev.popularmoviesudacitynd.models.TrailerList;
import pl.futuredev.popularmoviesudacitynd.utils.UrlManager;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private List<TrailerList> trailerList;
    private final TrailerAdapterOnClickHandler listClickHandler;

    public interface TrailerAdapterOnClickHandler {
        void onClick(int clickedItemIndex);
    }

    public TrailerAdapter(List<TrailerList> data, TrailerAdapterOnClickHandler listClickHandler) {
        this.trailerList = data;
        this.listClickHandler = listClickHandler;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTrailerImage;
        ImageButton ivImageButton;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivImageButton = itemView.findViewById(R.id.iv_image_button);
            this.ivTrailerImage = itemView.findViewById(R.id.iv_trailer_image);
            ivImageButton.setOnClickListener(this);
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
                .inflate(R.layout.content_trailer, parent, false);
        view.setFocusable(true);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {

        ImageView imageView = holder.ivTrailerImage;
        String imageYouTubeUrl = UrlManager.THUMBNAIL + trailerList.get(listPosition).getKey() + "/0.jpg";
        Picasso.get().load(imageYouTubeUrl).into(imageView);
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }
}
