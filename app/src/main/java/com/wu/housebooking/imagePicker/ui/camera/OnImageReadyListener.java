package com.wu.housebooking.imagePicker.ui.camera;


import com.wu.housebooking.imagePicker.model.Image;

import java.util.List;

public interface OnImageReadyListener {
    void onImageReady(List<Image> images);
}
