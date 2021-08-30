package com.wu.housebooking.imagePicker.ui.camera;


import com.wu.housebooking.imagePicker.model.Image;
import com.wu.housebooking.imagePicker.ui.common.MvpView;

import java.util.List;

public interface CameraView extends MvpView {

    void finishPickImages(List<Image> images);
}
