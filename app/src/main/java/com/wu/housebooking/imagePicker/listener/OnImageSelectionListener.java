package com.wu.housebooking.imagePicker.listener;


import com.wu.housebooking.imagePicker.model.Image;

import java.util.List;


public interface OnImageSelectionListener {
    void onSelectionUpdate(List<Image> images);
}
