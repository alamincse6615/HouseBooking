package com.wu.housebooking.imagePicker.ui.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.wu.housebooking.R;
import com.wu.housebooking.imagePicker.listener.OnImageLoaderListener;
import com.wu.housebooking.imagePicker.model.Config;
import com.wu.housebooking.imagePicker.model.Folder;
import com.wu.housebooking.imagePicker.model.Image;
import com.wu.housebooking.imagePicker.ui.camera.CameraModule;
import com.wu.housebooking.imagePicker.ui.camera.DefaultCameraModule;
import com.wu.housebooking.imagePicker.ui.camera.OnImageReadyListener;
import com.wu.housebooking.imagePicker.ui.common.BasePresenter;

import java.io.File;
import java.util.List;

/**
 * Created by hoanglam on 8/17/17.
 */

public class ImagePickerPresenter extends BasePresenter<ImagePickerView> {

    private ImageFileLoader imageLoader;
    private CameraModule cameraModule = new DefaultCameraModule();
    private Handler handler = new Handler(Looper.getMainLooper());

    public ImagePickerPresenter(ImageFileLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public void abortLoading() {
        imageLoader.abortLoadImages();
    }

    public void loadImages(boolean isFolderMode) {
        if (!isViewAttached()) return;

        getView().showLoading(true);
        imageLoader.loadDeviceImages(isFolderMode, new OnImageLoaderListener() {
            @Override
            public void onImageLoaded(final List<Image> images, final List<Folder> folders) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isViewAttached()) {
                            getView().showFetchCompleted(images, folders);
                            final boolean isEmpty = folders != null ? folders.isEmpty() : images.isEmpty();
                            if (isEmpty) {
                                getView().showEmpty();
                            } else {
                                getView().showLoading(false);
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailed(final Throwable throwable) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isViewAttached()) {
                            getView().showError(throwable);
                        }
                    }
                });
            }
        });
    }

    void captureImage(Activity activity, Config config, int requestCode) {
        Context context = activity.getApplicationContext();
        Intent intent = cameraModule.getCameraIntent(activity, config);
        if (intent == null) {
            Toast.makeText(context, context.getString(R.string.imagepicker_error_create_image_file), Toast.LENGTH_LONG).show();
            return;
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public void finishCaptureImage(Context context, Intent data, final Config config) {
        cameraModule.getImage(context, data, new OnImageReadyListener() {
            @Override
            public void onImageReady(List<Image> images) {
                if (!config.isMultipleMode()) {
                    getView().finishPickImages(images);
                } else {
                    getView().showCapturedImage(images);
                }
            }
        });
    }

    public void onDoneSelectImages(List<Image> selectedImages) {
        if (selectedImages != null && !selectedImages.isEmpty()) {
            for (int i = 0; i < selectedImages.size(); i++) {
                Image image = selectedImages.get(i);
                File file = new File(image.getPath());
                if (!file.exists()) {
                    selectedImages.remove(i);
                    i--;
                }
            }
        }
        getView().finishPickImages(selectedImages);
    }

}
