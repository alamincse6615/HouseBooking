package com.wu.housebooking.imagePicker.ui.imagepicker;


import com.wu.housebooking.imagePicker.model.Folder;
import com.wu.housebooking.imagePicker.model.Image;
import com.wu.housebooking.imagePicker.ui.common.MvpView;

import java.util.List;

/**
 * Created by hoanglam on 8/17/17.
 */

public interface ImagePickerView extends MvpView {

    void showLoading(boolean isLoading);

    void showFetchCompleted(List<Image> images, List<Folder> folders);

    void showError(Throwable throwable);

    void showEmpty();

    void showCapturedImage(List<Image> images);

    void finishPickImages(List<Image> images);

}