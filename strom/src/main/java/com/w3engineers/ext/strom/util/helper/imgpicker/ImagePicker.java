package com.w3engineers.ext.strom.util.helper.imgpicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-20 at 4:38 PM].
 * <br>Email: azim@w3engineers.com
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
    private static final String TAG = "ImagePicker";
    private static final String TEMP_IMAGE_NAME = "tempImage.jpg";
    private static String lastGeneratedFileName;


    /**
     * Picks Gallery and other image source so that a single picker is enough.
     * @param context
     * @return
     */
    public static Intent getPickImageIntent(Context context) {
        Intent chooserIntent = null;

        List<Intent> intentList = new ArrayList<>();

        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra("return-data", true);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getEmptyTempFile(context)));
        intentList = addIntentsToList(context, intentList, pickIntent);
        intentList = addIntentsToList(context, intentList, takePhotoIntent);

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                    "Pick image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }

        return chooserIntent;
    }

    private static List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
            Log.d(TAG, "Intent: " + intent.getAction() + " package: " + packageName);
        }
        return list;
    }


    public static Uri getImageUri(Context context, int resultCode,
                                  Intent imageReturnedIntent) {
        Log.d(TAG, "getImageFromResult, resultCode: " + resultCode);
        File imageFile = getTempFile(context);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImage;
            boolean isCamera = (imageReturnedIntent == null ||
                    imageReturnedIntent.getData() == null ||
                    (imageFile != null && imageReturnedIntent.getData().toString().contains(imageFile.toString())));
            if (isCamera) {     /** CAMERA **/
                selectedImage = Uri.fromFile(imageFile);
            } else {            /** ALBUM **/
                selectedImage = imageReturnedIntent.getData();

                if(imageFile != null) {//deleting last created unnecessary file
                    imageFile.delete();
                }
            }
            return selectedImage;
        }
        return null;
    }

    private static File getEmptyTempFile(Context context) {
        File imageFile = new File(context.getExternalCacheDir(), getFileName());

        imageFile.delete();

        File file = imageFile.getParentFile();

        if (file == null) {
            file = imageFile;
        }

        file.mkdirs();

        return imageFile;
    }

    private static File getTempFile(Context context) {
        File imageFile = new File(context.getExternalCacheDir(), lastGeneratedFileName);
        File file = imageFile.getParentFile();
        if (file == null) {// FIXME: 4/30/2018 provided a temp fix
            file = imageFile;
        }
        file.mkdirs();
        return imageFile;
    }

    private static String getFileName() {
        lastGeneratedFileName = System.currentTimeMillis() + TEMP_IMAGE_NAME;
        return lastGeneratedFileName;
    }
}