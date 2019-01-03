package com.w3engineers.ext.strom.application.ui.base;

import android.view.View;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : Azizul Islam
* * Date : 10/13/17
* * Email : aziz@w3engineers.com
* *
* * Purpose: In Recycler view, to get click events, you have to implement in this interface
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 03/16/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 03/16/18.
* ****************************************************************************
*/

/**
 * We have added one default interface method because developers often need the index of item along
 * with with item. Normally index leess interface is preferable to use.
 * Works with {@link BaseAdapter} normally
 */
public interface ItemClickListener<T> {
    /**
     * Called when a item has been clicked.
     *
     * @param view The view that was clicked.
     * @param item The T type object that was clicked.
     */
    void onItemClick(View view, T item);


    /**
     * Developers might often need the index of item.
     * @param view
     * @param item
     * @param index
     */
    default void onItemClick(View view, T item, int index) { }
}
