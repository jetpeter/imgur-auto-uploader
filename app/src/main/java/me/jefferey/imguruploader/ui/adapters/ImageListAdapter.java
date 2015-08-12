package me.jefferey.imguruploader.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.jefferey.imguruploader.R;
import me.jefferey.imguruploader.imgur.model.Image;
import me.jefferey.imguruploader.utils.ThumbnailUrlUtil;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    private final List<Image> mImages = new ArrayList<>();
    private final LayoutInflater mLayoutInflater;

    public ImageListAdapter(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    public void setImages(List<Image> images) {
        mImages.clear();
        mImages.addAll(images);
    }

    public void addImages(List<Image> images) {
        mImages.addAll(images);
    }

    public void clear() {
        mImages.clear();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = mLayoutInflater.inflate(R.layout.adapter_row_image, parent, false);
        return new ImageViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Image image = mImages.get(position);
        String imageLink = ThumbnailUrlUtil.getThumbnailUrl(image, ThumbnailUrlUtil.LARGE_THUMBNAIL);
        Picasso.with(holder.imageView.getContext())
                .load(imageLink)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ImageRow_image) public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
