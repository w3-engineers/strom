package com.w3engineers.ext.strom.util.helper.imgpicker;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

import com.w3engineers.ext.strom.BR;
import timber.log.Timber;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-20 at 5:04 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-20 at 5:04 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-20 at 5:04 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class ImagePickerDataModel extends BaseObservable {

    @Bindable
    private Uri mImageUri;

    public Uri getImageUri() {
        return mImageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.mImageUri = imageUri;

        //Key to work imageView with databinding
        notifyPropertyChanged(BR.imageUri);
    }


    private static RequestOptions requestOptions = new RequestOptions();

    @BindingAdapter(value = {"imageUri", "placeholder"}, requireAll = false)
    public static void loadImage(ImageView view, Uri imageUri, Drawable placeholder) {

        if(view != null && imageUri != null) {


            Timber.d("Image path::%s", imageUri);
            RequestBuilder requestBuilder = Glide.with(view.getContext()).load(imageUri);

            if(placeholder != null) {
                requestBuilder = requestBuilder.apply(requestOptions.placeholder(placeholder));
            }

            requestBuilder.into(view);
        }

    }

}
