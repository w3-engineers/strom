package com.w3engineers.ext.strom.application.ui.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Html;

import com.w3engineers.ext.strom.R;
import com.w3engineers.ext.strom.util.Text;



/*
 *  ****************************************************************************
 *  * Created by : Md. Azizul Islam on 7/2/2018 at 7:00 PM.
 *  * Email : azizul@w3engineers.com
 *  *
 *  * Purpose: Dialog util class
 *  *
 *  * Last edited by : Md. Azizul Islam on 7/2/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

public class DialogUtil {

    /**
     * <h1>Show dialog with default positive or negative</h1>
     *
     * @param context  : context
     * @param message  : Message to show
     * @param listener : callback
     */

    public static AlertDialog showDialog(Context context, String message, DialogListener listener) {
        String positiveBtn = context.getString(R.string.ok);
        String negativeBtn = context.getString(R.string.cancel);
        return showDialog(context, null, message, positiveBtn, negativeBtn, listener);
    }

    /**
     * <p>Message and title added</p>
     *
     * @param context  : context
     * @param title    : Title
     * @param message  : message to show
     * @param listener : callback
     */

    public static void showDialog(Context context, String title, String message, DialogListener listener) {
        String positiveBtn = context.getString(R.string.ok);
        String negativeBtn = context.getString(R.string.cancel);
        showDialog(context, title, message, positiveBtn, negativeBtn, listener);
    }

    /**
     * <h1>Main dialog util class</h1>
     *
     * @param context : context
     * @param title : title text
     * @param message : message
     * @param positiveText : positive button text
     * @param negativeText : negative button text
     * @param listener : callback
     */
    public static AlertDialog showDialog(Context context, String title, String message, String positiveText, String negativeText, final DialogListener listener) {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DefaultAlertDialogStyle);
            if(Text.isNotEmpty(title)) {
                alertDialogBuilder.setTitle(Html.fromHtml("<b>" + title + "</b>"));
            }
            alertDialogBuilder.setMessage(Html.fromHtml("<font color='#757575'>" + message + "</font>"));

            if (Text.isNotEmpty(positiveText)) {
                alertDialogBuilder.setPositiveButton(positiveText, (dialog, which) -> {
                    listener.onClickPositive();
                });
            }
            if (Text.isNotEmpty(negativeText)) {
                alertDialogBuilder.setNegativeButton(negativeText, (dialog, which) -> {
                    listener.onClickNegative();
                });
            }
            return alertDialogBuilder.show();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    /**
     *<h1>Interface for callback</h1>
     *<br> Need to implement to respective state
     *
     */
    public interface DialogListener {
        void onClickPositive();
        void onClickNegative();
    }
}
