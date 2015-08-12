package me.jefferey.imguruploader.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jefferey.imguruploader.R;
import me.jefferey.imguruploader.UploaderApplication;
import me.jefferey.imguruploader.imgur.model.Image;
import me.jefferey.imguruploader.imgur.network.RequestManager;
import me.jefferey.imguruploader.imgur.response.ResponseGallery;
import me.jefferey.imguruploader.imgur.response.ResponseWrapper;
import me.jefferey.imguruploader.ui.adapters.ImageListAdapter;
import me.jefferey.imguruploader.utils.PreferencesManager;
import retrofit.client.Response;


public class ImageListFragment extends Fragment {

    public static final String TAG = "ImageListFragment";

    private Callback mActivityCallback;

    @Inject Bus mBus;
    @Inject RequestManager mRequestManager;
    @Inject PreferencesManager mPreferencesManager;

    @Bind(R.id.ImageList_recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.ImageList_upload_image) View mAddImageButton;

    private RecyclerView.LayoutManager mLayoutManager;
    private ImageListAdapter mImageListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UploaderApplication.getMainComponent().inject(this);
        mBus.register(this);
        if (mPreferencesManager.isLoggedIn()) {
            mRequestManager.getUserSubmissions(TAG, 0);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Callback) {
            mActivityCallback = (Callback) activity;
        }
        mLayoutManager = new GridLayoutManager(activity, 2);
        mImageListAdapter = new ImageListAdapter(LayoutInflater.from(activity));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View content = inflater.inflate(R.layout.fragment_image_list, parent, false);
        ButterKnife.bind(this, content);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mImageListAdapter);
        return content;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Subscribe
    public void onSubmissionsReceived(ResponseGallery responseGallery) {
        if (responseGallery.getSuccess()) {
            int beforeLength = mImageListAdapter.getItemCount();
            List<Image> images = responseGallery.getData().data;
            mImageListAdapter.addImages(images);
            mImageListAdapter.notifyItemRangeInserted(beforeLength, images.size());
        } else {
            handleNetworkError(responseGallery);
        }
    }

    @Subscribe
    public void onNewImage(Image image) {
        // Add the image to the front of the list
        mImageListAdapter.addImage(0, image);
        mImageListAdapter.notifyItemInserted(0);
    }

    public void handleNetworkError(ResponseWrapper responseWrapper) {
        Response response = responseWrapper.getResponse();
        if (response != null) {
            if (response.getStatus() == 401 || response.getStatus() == 403) {
                if (mActivityCallback != null) {
                    mActivityCallback.authRequired();
                }
            }
        }
    }

    @OnClick(R.id.ImageList_upload_image)
    public void onUploadImageClick() {
        if (mActivityCallback != null) {
            mActivityCallback.onUploadImageClick();
        }
    }

    public interface Callback {
        void onUploadImageClick();
        void authRequired();
    }

}
