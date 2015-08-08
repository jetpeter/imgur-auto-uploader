package me.jefferey.imguruploader.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import me.jefferey.imguruploader.R;
import me.jefferey.imguruploader.UploaderApplication;
import me.jefferey.imguruploader.imgur.model.Image;
import me.jefferey.imguruploader.imgur.network.RequestManager;
import me.jefferey.imguruploader.imgur.response.ResponseGallery;
import me.jefferey.imguruploader.imgur.response.ResponseWrapper;
import me.jefferey.imguruploader.ui.adapters.ImageListAdapter;
import retrofit.client.Response;


public class ImageListFragment extends Fragment {

    public static final String TAG = "ImageListFragment";

    private Callback mActivityCallback;

    @Inject Bus mBus;
    @Inject RequestManager mRequestManager;

    @Bind(R.id.ImageList_recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.ImageList_upload_image) View mAddImageButton;

    private RecyclerView.LayoutManager mLayoutManager;
    private ImageListAdapter mImageListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UploaderApplication.getMainComponent().inject(this);
        mBus.register(this);
        mRequestManager.getUserSubmissions(TAG, 0);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Callback) {
            mActivityCallback = (Callback) activity;
        }
        mLayoutManager = new LinearLayoutManager(activity);
        mImageListAdapter = new ImageListAdapter(LayoutInflater.from(activity));
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Image> images = realm.allObjects(Image.class);
        mImageListAdapter.setImages(images);
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

    @Subscribe
    public void onSubmissionsReceived(ResponseGallery responseGallery) {
        if (responseGallery.getSuccess()) {
            mImageListAdapter.setImages(responseGallery.getData().data);
            mImageListAdapter.notifyDataSetChanged();
        } else {
            handleNetworkError(responseGallery);
        }
    }

    public void handleNetworkError(ResponseWrapper responseWrapper) {
        Response response = responseWrapper.getResponse();
        if (response.getStatus() == 401 || response.getStatus() == 403) {
            if (mActivityCallback != null) {
                mActivityCallback.authRequired();
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
