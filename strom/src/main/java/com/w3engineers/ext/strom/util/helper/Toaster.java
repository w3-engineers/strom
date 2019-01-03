package com.w3engineers.ext.strom.util.helper;

import android.widget.Toast;

import com.w3engineers.ext.strom.App;
import com.w3engineers.ext.strom.util.Text;
import es.dmoral.toasty.Toasty;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : SUDIPTA KUMAR PAIK
* * Date : 2/1/18
* * Email : sudipta@w3engineers.com
* *
* * Purpose : Toaster for all type of toast showing
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 2/1/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 2/1/18.
* ****************************************************************************
*/

/**
 * App should use this class in the whole app instead of native {@link Toast}
 */
public class Toaster {

    /*
    * Private constructor. Don't make it public
    * */
    private Toaster() {
    }

    /**
     * We might pick color from app automatically in next version
     * @param color of toasts
     */
    public static void init(int color) {

        if(App.getContext() != null) {
            Toasty.Config.getInstance()
                    .setInfoColor(color)
                    .tintIcon(true)
                    .apply();
        }
    }

    /**
     * Show long toast message.
     * It checks the text and displays accordingly
     * @param string
     */
    public static void showLong(String string) {
        show(string, Toast.LENGTH_LONG);
    }

    /**
     * Show short toast message.
     * It checks the text and displays accordingly
     * @param string
     */
    public static void showShort(String string) {
        show(string, Toast.LENGTH_SHORT);
    }

    /**
     * It checks the text and displays accordingly
     * @param string
     * @param lengthFlag
     */
    private static void show(String string, int lengthFlag) {

        if(App.getContext() != null && Text.isNotEmpty(string)) {
            Toasty.info(App.getContext(), string, lengthFlag).show();
        }
    }
}