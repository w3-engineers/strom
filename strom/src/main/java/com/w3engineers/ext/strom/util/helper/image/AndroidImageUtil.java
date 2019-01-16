package com.w3engineers.ext.strom.util.helper.image;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;

/**
 * ============================================================================
 * Copyright (C) 2019 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2019-01-15 at 1:15 PM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: strom.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2019-01-15 at 1:15 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2019-01-15 at 1:15 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class AndroidImageUtil {

    /**
     * Update media scanner or Gallery with the specific file. If this method is not called then gallery
     * app's can not immediately realise about the file
     * @param context
     * @param filePath for which file media scanner needs to update
     */
    public static void updateMediaScanner(Context context, String filePath) {

        if(context == null || TextUtils.isEmpty(filePath)) {
            return;
        }

        File file = new File(filePath);
        if(!file.exists()) {
            return;
        }

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

}
