package com.w3engineers.ext.strom.util.helper.image.imgpicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.w3engineers.ext.strom.R;
import com.w3engineers.ext.strom.application.ui.base.BaseActivity;
import com.w3engineers.ext.strom.application.ui.base.BaseFragment;
import com.w3engineers.ext.strom.util.helper.FileUtil;
import com.w3engineers.ext.strom.util.helper.image.AndroidImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-20 at 4:38 PM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-20 at 4:38 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-20 at 4:38 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/

/**
 * Generates an empty temp file with current time stamp. Delete that file if image is taken from Gallery.
 * If image snap is taken using camera then earlier provided particular file uri is returned. The File name
 * has a life cycle so please do not modify unless you are sure of the life cycle.
 * The lifecycle works as camera hardware has global single resource access. If by somehow camera resource
 * can be handled simultaneously then the mechanism would break which may not happen ever.
 * We are generating different file name each time so that it can properly co exist with Glide or
 * other image library caching.
 *
 * Very well test on all kind of devices are required.
 */
public class ImagePicker {

    public static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static ImagePicker mImagePicker = new ImagePicker();

    private final String TITLE = "Pick image";
    private static final String TEMP_IMAGE_NAME = ".jpg";
    private String lastGeneratedFileName;

    private File mCapturedFile;

    private ImagePicker() {}

    public static ImagePicker getInstance() {
        return mImagePicker;
    }


    /**
     * Picks Gallery and other image source so that a single picker is enough. Manage Nougat camera
     * permission by managing a separate file technique.
     * Pocked or captured image is available by {@link BaseActivity#onImage(Uri)}
     * or {@link BaseFragment#onImage(Uri)}
     * @param context
     * @return
     */
    public Intent getImagePickerIntent(Context context) {

        if(context == null)
            return null;


        Intent chooserIntent = null;
        List<Intent> intentList = new ArrayList();

        //Image picker intent. e.g: gallery
        Intent pickPhotoIntent = new Intent();
        String st = context.getString(R.string.image_picker_intent_type_image);
        pickPhotoIntent.setType(st);
        pickPhotoIntent.setAction(Intent.ACTION_GET_CONTENT);

        //Image capture intent. e.g: camera
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra(context.getString(R.string.image_picker_intent_extra_return_data), true);

        //empty file to write captured image data
        File emptyFile = FileUtil.getEmptyFile(context, getFileName(), Environment.DIRECTORY_PICTURES);
        if (emptyFile == null) {
            return null;
        } else {
            mCapturedFile = emptyFile;
        }

        //generating authority string for nougat
        String myPackageName = context.getApplicationContext().getPackageName();
        String authority = myPackageName + context.getString(R.string.image_picker_authority_suffix);

        //Above Marshmallow or from Nougat we add permission for our captured file
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context,
                    authority, mCapturedFile));
            takePhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            takePhotoIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(emptyFile));
        }

        //Adding pick intents
        intentList = addIntentsToList(context, intentList, pickPhotoIntent);
        //Adding capture intents
        intentList = addIntentsToList(context, intentList, takePhotoIntent);

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1), TITLE);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }

        return chooserIntent;
    }

    private List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
            Timber.d("Intent: " + intent.getAction() + " package: " + packageName);
        }
        return list;
    }

    /**
     * generate a schematic file name
     * @return
     */
    private String getFileName() {
        lastGeneratedFileName = System.currentTimeMillis() + TEMP_IMAGE_NAME;
        return lastGeneratedFileName;
    }


    /**
     * Post processing of image upon pick or capture
     * @param imageReturnedIntent
     * @param isCamera
     * @return
     */
    private Uri getImageUri(Intent imageReturnedIntent, boolean isCamera) {
        File imageFile = mCapturedFile;
        Uri selectedImage;
        if (isCamera) {     /** CAMERA **/
            selectedImage = Uri.fromFile(imageFile);
        } else {            /** ALBUM **/
            selectedImage = imageReturnedIntent.getData();
        }
        return selectedImage;
    }

    /**
     * Post processing method for external caller upon image pick or capture
     *
     * @param context
     * @param requestCode
     * @param resultCode
     * @param imageReturnedIntent
     * @return
     */
    public Uri postProcessImagePick(Context context, int requestCode, int resultCode, Intent imageReturnedIntent) {

        switch (requestCode) {
            case REQUEST_CODE_PICK_IMAGE:

                boolean isCamera = false;
                Uri imageUri = null;
                if(resultCode == Activity.RESULT_OK) {

                    isCamera = (imageReturnedIntent == null ||
                            imageReturnedIntent.getData() == null ||(imageReturnedIntent.getAction() != null
                            && imageReturnedIntent.getAction().equals(MediaStore.ACTION_IMAGE_CAPTURE)) ||
                            (mCapturedFile != null && imageReturnedIntent.getData().toString().contains(mCapturedFile.toString())));
                    imageUri = getImageUri(imageReturnedIntent, isCamera);
                }

                resetPicker(context, !isCamera);
                return imageUri;
             default:
                 return null;
        }
    }

    /**
     * Reset all boilerplate code internally. Delete empty file if image is not captured.
     * @param isToDeleteFile
     */
    private void resetPicker(Context context, boolean isToDeleteFile) {

        boolean isValidFile = mCapturedFile != null && mCapturedFile.exists();
        if(isToDeleteFile && isValidFile) {
            mCapturedFile.delete();
        } else if(isValidFile){
            AndroidImageUtil.updateMediaScanner(context, mCapturedFile.getPath());
        }
        mCapturedFile = null;

    }
}