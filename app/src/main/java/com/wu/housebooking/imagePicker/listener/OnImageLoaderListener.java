package com.wu.housebooking.imagePicker.listener;



import com.wu.housebooking.imagePicker.model.Folder;
import com.wu.housebooking.imagePicker.model.Image;

import java.util.List;


public interface OnImageLoaderListener {
    void onImageLoaded(List<Image> images, List<Folder> folders);

    void onFailed(Throwable throwable);
}
