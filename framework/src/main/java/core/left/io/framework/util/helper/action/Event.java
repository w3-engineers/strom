package core.left.io.framework.util.helper.action;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-17 at 12:14 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-17 at 12:14 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-17 at 12:14 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/

/**
 * Intended to observe any particular action through live data.
 * (Developers comment) https://medium.com/google-developers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
public class Event<T> {

    private T mContent;

    private boolean mHasBeenHandled;

    public Event(T content) {

        this.mContent = content;

    }

    /**
     * Returns the content and prevents its use again.
     */
    public T getContentIfNotHandled() {

        if(mHasBeenHandled) {
            return null;
        }

        mHasBeenHandled = true;

        return mContent;
    }

    public T peekContent() {
        return mContent;
    }

    public boolean isHasBeenHandled() {
        return mHasBeenHandled;
    }

}
