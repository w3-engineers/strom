package com.w3engineers.ext.strom.util.helper;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

import timber.log.Timber;

/**
 * ============================================================================
 * Copyright (C) 2019 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2019-01-08 at 11:16 AM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: strom.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2019-01-08 at 11:16 AM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2019-01-08 at 11:16 AM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class FileUtil {


    /**
     *
     * @param context
     * @param fileName
     * @param directoryType
     * @return An empty file in the specified directory type
     */
    public static File getEmptyFile(Context context, String fileName, String directoryType) {

        if(TextUtils.isEmpty(fileName)) {
            return null;
        }

        File storageDirectory = TextUtils.isEmpty(directoryType) ? context.getCacheDir() :
                Environment.getExternalStoragePublicDirectory(directoryType);
        File file = new  File(storageDirectory, fileName);

        try {
            file.createNewFile();
        } catch (IOException ioException) {
            Timber.e(ioException);
            return null;
        }

        if (file.getParentFile() == null) {
            file.mkdirs();
        } else {
            file.getParentFile().mkdirs();
        }

        return file;
    }

}
