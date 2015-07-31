package me.jefferey.screenshotuploader.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.jefferey.screenshotuploader.R;


public class ImageListFragment extends Fragment {

    public static ImageListFragment newInstance() {
        return new ImageListFragment();
    }

    @Bind(R.id.ImageList_recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.ImageList_upload_image) View mAddImageButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View content = inflater.inflate(R.layout.fragment_image_list, parent, false);
        ButterKnife.bind(this, content);
        return content;
    }

}
