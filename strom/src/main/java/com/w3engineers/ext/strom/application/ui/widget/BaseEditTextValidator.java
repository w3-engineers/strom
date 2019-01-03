package com.w3engineers.ext.strom.application.ui.widget;

import android.support.annotation.NonNull;

/**
 * Base Validator class to either implement or inherit from for custom validation
 */
public abstract class BaseEditTextValidator {

  /**
   * Error message that the view will display if validation fails.
   * <p/>
   * This is protected, so you can change this dynamically in your {@link #isValid(CharSequence, boolean)}
   * implementation. If necessary, you can also interact with this via its getter and setter.
   */
  protected String mErrorMessage;

  public BaseEditTextValidator(@NonNull String errorMessage) {
    this.mErrorMessage = errorMessage;
  }

  public void setmErrorMessage(@NonNull String mErrorMessage) {
    this.mErrorMessage = mErrorMessage;
  }

  @NonNull
  public String getmErrorMessage() {
    return this.mErrorMessage;
  }

  /**
   * Abstract method to implement your own validation checking.
   *
   * @param text    The CharSequence representation of the text in the EditText field. Cannot be null, but may be empty.
   * @param isEmpty Boolean indicating whether or not the text param is empty
   * @return True if valid, false if not
   */
  public abstract boolean isValid(@NonNull CharSequence text, boolean isEmpty);

}
