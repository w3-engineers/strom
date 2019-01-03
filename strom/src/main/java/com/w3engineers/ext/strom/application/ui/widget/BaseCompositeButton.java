package com.w3engineers.ext.strom.application.ui.widget;

/*
 *  ****************************************************************************
 *  * Created by : Md. Azizul Islam on 7/2/2018 at 7:00 PM.
 *  * Email : azizul@w3engineers.com
 *  *
 *  * Purpose: Custom button view with various options
 *  *
 *  * Last edited by : Md. Azizul Islam on 7/2/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.w3engineers.ext.strom.R;
import com.w3engineers.ext.strom.util.Text;

/**
 * Convenient Button to apply various formatting. Developers can set round, square, icon, icon less,
 * enable color, disable color etc utility property.
 * We will try to combine the benefit of {@link BaseButton} and this {@link BaseCompositeButton} in
 * single widget in next version.
 */
public class BaseCompositeButton extends LinearLayout {

    public static final String TAG = BaseCompositeButton.class.getSimpleName();
    private Map<String, Typeface> cachedFontMap = new HashMap<>();
    private Context mContext;

    /************** Background drawable *****************************/
    // Optional property developer can use a drawable or not
    private Drawable mBackgroundDrawable;

    /******************** Background Attributes ***********************/
    private int mDefaultBackgroundColor = Color.BLACK;
    private int mFocusBackgroundColor = 0;
    private int mDisabledBackgroundColor = Color.parseColor("#f6f7f9");
    private int mDisabledTextColor = Color.parseColor("#bec2c9");
    private int mDisabledBorderColor = Color.parseColor("#dddfe2");


    /******************** Text Attributes ****************************/
    private int mDefaultTextColor = Color.WHITE;
    private int mDefaultIconColor = Color.WHITE;
    private int mDefaultTextSize = spToPx(getContext(), 15);
    private int mDefaultTextGravity = 0x11; // Gravity.CENTER
    private String mText = null;


    /******************** Icon Attributes ****************************/
    private Drawable mIconResource = null;
    private int mFontIconSize = spToPx(getContext(), 15);
    private String mFontIcon = null;
    private int mIconPosition = 1;

    /******************** Padding Attributes ****************************/
    private int mIconPaddingLeft = 10;
    private int mIconPaddingRight = 10;
    private int mIconPaddingTop = 0;
    private int mIconPaddingBottom = 0;

    /******************** Button boarder color  ****************************/
    private int mBorderColor = Color.TRANSPARENT;
    private int mBorderWidth = 0;

    /****************** Button corner radius **************************/
    private int mRadius = 0;
    private int mRadiusTopLeft = 0;
    private int mRadiusTopRight = 0;
    private int mRadiusBottomLeft = 0;
    private int mRadiusBottomRight = 0;

    private boolean mEnabled = true;

    private boolean mTextAllCaps = false;

    private Typeface mTextTypeFace = null;
    private Typeface mIconTypeFace = null;
    private int textStyle;

    /****************** Button Icon position **************************/
    public static final int POSITION_LEFT = 1;
    public static final int POSITION_TOP = 3;
    public static final int POSITION_BOTTOM = 4;

    private String mDefaultIconFont = "fontawesome.ttf";
    private String mDefaultTextFont = "robotoregular.ttf";

    private ImageView mIconView;
    private TextView mFontIconView;
    private TextView mTextView;

    private boolean mGhost = false; // Default is a solid button !
    private boolean mUseSystemFont = false; // Default is using robotoregular.ttf


    /**
     * <p>Default constructor</p>
     * <p>
     * </br>Initialize context
     *
     * @param context : Context
     */
    public BaseCompositeButton(Context context) {
        super(context);
        this.mContext = context;

        mTextTypeFace = findFont(mContext, mDefaultTextFont, null);
        mIconTypeFace = findFont(mContext, mDefaultIconFont, null);
        initializeFancyButton();
    }


    /**
     * <p>Default constructor called from Layouts</p>
     * <p>
     * </br> Init context and get attribute from xml
     *
     * @param context : Context(required)
     * @param attrs   : Attributes Array(required)
     */
    public BaseCompositeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        TypedArray attrsArray = context.obtainStyledAttributes(attrs, R.styleable.BaseCompositeButton, 0, 0);
        initAttributesArray(attrsArray);
        attrsArray.recycle();

        initializeFancyButton();

    }

    /**
     * <p>Initialize Button dependencies</p>
     * <p>
     * </br> Initialize Button Container : The LinearLayout
     * </br> Initialize Button TextView
     * </br>Initialize Button Icon
     * </br>Initialize Button Font Icon
     */
    private void initializeFancyButton() {

        initializeButtonContainer();

        mTextView = setupTextView();
        mIconView = setupIconView();
        mFontIconView = setupFontIconView();

        this.removeAllViews();
        setupBackground();

        ArrayList<View> views = new ArrayList<>();

        if (mIconPosition == POSITION_LEFT || mIconPosition == POSITION_TOP) {

            if (mIconView != null) {
                views.add(mIconView);
            }

            if (mFontIconView != null) {
                views.add(mFontIconView);
            }
            if (mTextView != null) {
                views.add(mTextView);
            }

        } else {
            if (mTextView != null) {
                views.add(mTextView);
            }

            if (mIconView != null) {
                views.add(mIconView);
            }

            if (mFontIconView != null) {
                views.add(mFontIconView);
            }
        }

        for (View view : views) {
            this.addView(view);
        }
    }

    /**
     * <p>Setup Text View</p>
     * </br> responsible to create dynamic text view
     * </br> and inflate in linear layout
     *
     * @return : TextView
     */
    private TextView setupTextView() {
        if (mText == null) {
            mText = "Fancy Button";
        }

        TextView textView = new TextView(mContext);
        textView.setText(mText);

        textView.setGravity(mDefaultTextGravity);
        textView.setTextColor(mEnabled ? mDefaultTextColor : mDisabledTextColor);
        textView.setTextSize(pxToSp(getContext(), mDefaultTextSize));
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        if (!isInEditMode() && !mUseSystemFont) {
            textView.setTypeface(mTextTypeFace, textStyle); //we can pass null in first arg
        }
        return textView;
    }

    /**
     * Setup Font Icon View
     *
     * @return : TextView
     */
    private TextView setupFontIconView() {

        if (mFontIcon != null) {
            TextView fontIconView = new TextView(mContext);
            fontIconView.setTextColor(mEnabled ? mDefaultIconColor : mDisabledTextColor);

            LayoutParams iconTextViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            iconTextViewParams.rightMargin = mIconPaddingRight;
            iconTextViewParams.leftMargin = mIconPaddingLeft;
            iconTextViewParams.topMargin = mIconPaddingTop;
            iconTextViewParams.bottomMargin = mIconPaddingBottom;

            if (mTextView != null) {

                if (mIconPosition == POSITION_TOP || mIconPosition == POSITION_BOTTOM) {
                    iconTextViewParams.gravity = Gravity.CENTER;
                    fontIconView.setGravity(Gravity.CENTER);
                } else {
                    fontIconView.setGravity(Gravity.CENTER_VERTICAL);
                    iconTextViewParams.gravity = Gravity.CENTER_VERTICAL;
                }
            } else {
                iconTextViewParams.gravity = Gravity.CENTER;
                fontIconView.setGravity(Gravity.CENTER_VERTICAL);
            }


            fontIconView.setLayoutParams(iconTextViewParams);
            if (!isInEditMode()) {
                fontIconView.setTextSize(pxToSp(getContext(), mFontIconSize));
                fontIconView.setText(mFontIcon);
                fontIconView.setTypeface(mIconTypeFace);
            } else {
                fontIconView.setTextSize(pxToSp(getContext(), mFontIconSize));
                fontIconView.setText("O");
            }
            return fontIconView;
        }
        return null;
    }

    /**
     * Text Icon resource view
     *
     * @return : ImageView
     */
    private ImageView setupIconView() {
        if (mIconResource != null) {
            ImageView iconView = new ImageView(mContext);
            iconView.setImageDrawable(mIconResource);
            iconView.setPadding(mIconPaddingLeft, mIconPaddingTop, mIconPaddingRight, mIconPaddingBottom);

            LayoutParams iconViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            if (mTextView != null) {
                if (mIconPosition == POSITION_TOP || mIconPosition == POSITION_BOTTOM)
                    iconViewParams.gravity = Gravity.CENTER;
                else
                    iconViewParams.gravity = Gravity.START;

                iconViewParams.rightMargin = 10;
                iconViewParams.leftMargin = 10;
            } else {
                iconViewParams.gravity = Gravity.CENTER_VERTICAL;
            }
            iconView.setLayoutParams(iconViewParams);

            return iconView;
        }
        return null;
    }

    /**
     * <h1>Initialize Attributes arrays</h1>
     * init all view attributes
     *
     * @param attrsArray : Attributes array
     */
    private void initAttributesArray(TypedArray attrsArray) {

        mDefaultBackgroundColor = attrsArray.getColor(R.styleable.BaseCompositeButton_btn_defaultColor, mDefaultBackgroundColor);
        mFocusBackgroundColor = attrsArray.getColor(R.styleable.BaseCompositeButton_btn_focusColor, mFocusBackgroundColor);
        mDisabledBackgroundColor = attrsArray.getColor(R.styleable.BaseCompositeButton_btn_disabledColor, mDisabledBackgroundColor);

        mEnabled = attrsArray.getBoolean(R.styleable.BaseCompositeButton_android_enabled, true);

        mDisabledTextColor = attrsArray.getColor(R.styleable.BaseCompositeButton_btn_disabledTextColor, mDisabledTextColor);
        mDisabledBorderColor = attrsArray.getColor(R.styleable.BaseCompositeButton_btn_disabledBorderColor, mDisabledBorderColor);
        mDefaultTextColor = attrsArray.getColor(R.styleable.BaseCompositeButton_btn_textColor, mDefaultTextColor);
        // if default color is set then the icon's color is the same (the default for icon's color)
        mDefaultIconColor = attrsArray.getColor(R.styleable.BaseCompositeButton_btn_iconColor, mDefaultTextColor);

        mDefaultTextSize = (int) attrsArray.getDimension(R.styleable.BaseCompositeButton_btn_textSize, mDefaultTextSize);
        mDefaultTextSize = (int) attrsArray.getDimension(R.styleable.BaseCompositeButton_android_textSize, mDefaultTextSize);

        mDefaultTextGravity = attrsArray.getInt(R.styleable.BaseCompositeButton_btn_textGravity, mDefaultTextGravity);

        mBorderColor = attrsArray.getColor(R.styleable.BaseCompositeButton_btn_borderColor, mBorderColor);
        mBorderWidth = (int) attrsArray.getDimension(R.styleable.BaseCompositeButton_btn_borderWidth, mBorderWidth);

        mRadius = (int) attrsArray.getDimension(R.styleable.BaseCompositeButton_btn_radius, mRadius);

        mRadiusTopLeft = (int) attrsArray.getDimension(R.styleable.BaseCompositeButton_btn_radiusTopLeft, mRadius);
        mRadiusTopRight = (int) attrsArray.getDimension(R.styleable.BaseCompositeButton_btn_radiusTopRight, mRadius);
        mRadiusBottomLeft = (int) attrsArray.getDimension(R.styleable.BaseCompositeButton_btn_radiusBottomLeft, mRadius);
        mRadiusBottomRight = (int) attrsArray.getDimension(R.styleable.BaseCompositeButton_btn_radiusBottomRight, mRadius);

        mFontIconSize = (int) attrsArray.getDimension(R.styleable.BaseCompositeButton_btn_fontIconSize, mFontIconSize);

        mIconPaddingLeft = (int) attrsArray.getDimension(R.styleable.BaseCompositeButton_btn_iconPaddingLeft, mIconPaddingLeft);
        mIconPaddingRight = (int) attrsArray.getDimension(R.styleable.BaseCompositeButton_btn_iconPaddingRight, mIconPaddingRight);
        mIconPaddingTop = (int) attrsArray.getDimension(R.styleable.BaseCompositeButton_btn_iconPaddingTop, mIconPaddingTop);
        mIconPaddingBottom = (int) attrsArray.getDimension(R.styleable.BaseCompositeButton_btn_iconPaddingBottom, mIconPaddingBottom);

        mTextAllCaps = attrsArray.getBoolean(R.styleable.BaseCompositeButton_btn_textAllCaps, false);
        mTextAllCaps = attrsArray.getBoolean(R.styleable.BaseCompositeButton_android_textAllCaps, false);

        mGhost = attrsArray.getBoolean(R.styleable.BaseCompositeButton_btn_ghost, mGhost);
        mUseSystemFont = attrsArray.getBoolean(R.styleable.BaseCompositeButton_btn_useSystemFont, mUseSystemFont);
        mBackgroundDrawable = attrsArray.getDrawable(R.styleable.BaseCompositeButton_btn_backgroundDrawable);

        String text = attrsArray.getString(R.styleable.BaseCompositeButton_btn_text);

        if (text == null) { //no fb_text attribute
            text = attrsArray.getString(R.styleable.BaseCompositeButton_android_text);
        }

        mIconPosition = attrsArray.getInt(R.styleable.BaseCompositeButton_btn_iconPosition, mIconPosition);

        textStyle = attrsArray.getInt(R.styleable.BaseCompositeButton_android_textStyle, Typeface.NORMAL);

        String fontIcon = attrsArray.getString(R.styleable.BaseCompositeButton_btn_fontIconResource);

        String iconFontFamily = attrsArray.getString(R.styleable.BaseCompositeButton_btn_iconFont);
        String textFontFamily = attrsArray.getString(R.styleable.BaseCompositeButton_btn_textFont);

        try {
            mIconResource = attrsArray.getDrawable(R.styleable.BaseCompositeButton_btn_iconResource);
        } catch (Exception e) {
            mIconResource = null;
        }

        if (fontIcon != null)
            mFontIcon = fontIcon;

        if (text != null)
            mText = mTextAllCaps ? text.toUpperCase() : text;

        if (!isInEditMode()) {
            mIconTypeFace = iconFontFamily != null
                    ? findFont(mContext, iconFontFamily, mDefaultIconFont)
                    : findFont(mContext, mDefaultIconFont, null);

            mTextTypeFace = textFontFamily != null
                    ? findFont(mContext, textFontFamily, mDefaultTextFont)
                    : findFont(mContext, mDefaultTextFont, null);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Drawable getRippleDrawable(Drawable defaultDrawable, Drawable focusDrawable, Drawable disabledDrawable) {
        if (!mEnabled) {
            return disabledDrawable;
        } else {
            return new RippleDrawable(ColorStateList.valueOf(mFocusBackgroundColor), defaultDrawable, focusDrawable);
        }

    }

    /**
     * This method applies radius to the drawable corners
     * Specify radius for each corner if radius attribute is not defined
     *
     * @param drawable Drawable
     */
    private void applyRadius(GradientDrawable drawable) {
        if (mRadius > 0) {
            drawable.setCornerRadius(mRadius);
        } else {
            drawable.setCornerRadii(new float[]{mRadiusTopLeft, mRadiusTopLeft, mRadiusTopRight, mRadiusTopRight,
                    mRadiusBottomRight, mRadiusBottomRight, mRadiusBottomLeft, mRadiusBottomLeft});
        }
    }

    @SuppressLint("NewApi")
    private void setupBackground() {
        // Default Drawable
        GradientDrawable defaultDrawable = new GradientDrawable();
        applyRadius(defaultDrawable);


        if (mGhost) {
            defaultDrawable.setColor(getResources().getColor(android.R.color.transparent)); // Hollow Background
        } else {
            defaultDrawable.setColor(mDefaultBackgroundColor);
        }

        //Focus Drawable
        GradientDrawable focusDrawable = new GradientDrawable();
        applyRadius(focusDrawable);

        focusDrawable.setColor(mFocusBackgroundColor);

        // Disabled Drawable
        GradientDrawable disabledDrawable = new GradientDrawable();
        applyRadius(disabledDrawable);

        disabledDrawable.setColor(mDisabledBackgroundColor);
        disabledDrawable.setStroke(mBorderWidth, mDisabledBorderColor);

        // Handle Border
        if (mBorderColor != 0) {
            defaultDrawable.setStroke(mBorderWidth, mBorderColor);
        }

        // Handle disabled border color
        if (!mEnabled) {
            defaultDrawable.setStroke(mBorderWidth, mDisabledBorderColor);
            if (mGhost) {
                disabledDrawable.setColor(getResources().getColor(android.R.color.transparent));
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            this.setBackground(getRippleDrawable(defaultDrawable, focusDrawable, disabledDrawable));

        } else {

            StateListDrawable states = new StateListDrawable();

            // Focus/Pressed Drawable
            GradientDrawable drawable2 = new GradientDrawable();
            applyRadius(drawable2);

            if (mGhost) {
                drawable2.setColor(getResources().getColor(android.R.color.transparent)); // No focus color
            } else {
                drawable2.setColor(mFocusBackgroundColor);
            }

            // Handle Button Border
            if (mBorderColor != 0) {
                if (mGhost) {
                    drawable2.setStroke(mBorderWidth, mFocusBackgroundColor); // Border is the main part of button now
                } else {
                    drawable2.setStroke(mBorderWidth, mBorderColor);
                }
            }

            if (!mEnabled) {
                if (mGhost) {
                    drawable2.setStroke(mBorderWidth, mDisabledBorderColor);
                } else {
                    drawable2.setStroke(mBorderWidth, mDisabledBorderColor);
                }
            }

            if (mFocusBackgroundColor != 0) {
                states.addState(new int[]{android.R.attr.state_pressed}, drawable2);
                states.addState(new int[]{android.R.attr.state_focused}, drawable2);
                states.addState(new int[]{-android.R.attr.state_enabled}, disabledDrawable);
            }
            states.addState(new int[]{}, defaultDrawable);
            this.setBackground(states);
        }


        /**
         * Background from xml drawable
         * this is optional developer can
         * set the property from main layout or through xml drawable
         */
        if(mBackgroundDrawable != null){
            setBackground(mBackgroundDrawable);
        }
    }


    /**
     * Initialize button container
     */
    private void initializeButtonContainer() {

        if (mIconPosition == POSITION_TOP || mIconPosition == POSITION_BOTTOM) {
            this.setOrientation(LinearLayout.VERTICAL);
        } else {
            this.setOrientation(LinearLayout.HORIZONTAL);
        }

        if (this.getLayoutParams() == null) {
            LayoutParams containerParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(containerParams);
        }

        this.setGravity(Gravity.CENTER);
        // disable click listeners for fix bug in this issue as:
        // https://github.com/medyo/Fancybuttons/issues/100
        //this.setClickable(true);
        //this.setFocusable(true);

        if (mIconResource == null && mFontIcon == null && getPaddingLeft() == 0 && getPaddingRight() == 0 && getPaddingTop() == 0 && getPaddingBottom() == 0) {
            //fix for all version of androids and screens
            this.setPadding(20, 0, 20, 0);
        }
    }

    /**
     * Set Text of the button
     *
     * @param text : Text
     */
    public void setText(String text) {
        text = mTextAllCaps ? text.toUpperCase() : text;
        this.mText = text;
        if (mTextView == null)
            initializeFancyButton();
        else
            mTextView.setText(text);
    }

    /**
     * Set the capitalization of text
     *
     * @param textAllCaps : is text to be capitalized
     */
    public void setTextAllCaps(boolean textAllCaps) {
        this.mTextAllCaps = textAllCaps;
        setText(mText);
    }

    /**
     * Set the color of text
     *
     * @param color : Color
     *              use Color.parse('#code')
     */
    public void setTextColor(int color) {
        this.mDefaultTextColor = color;
        if (mTextView == null)
            initializeFancyButton();
        else
            mTextView.setTextColor(color);

    }

    /**
     * Setting the icon's color independent of the text color
     *
     * @param color : Color
     */
    public void setIconColor(int color) {
        if (mFontIconView != null) {
            mFontIconView.setTextColor(color);
        }
    }

    /**
     * Set Background color of the button
     *
     * @param color : use Color.parse('#code')
     */
    public void setBackgroundColor(int color) {
        this.mDefaultBackgroundColor = color;
        if (mIconView != null || mFontIconView != null || mTextView != null) {
            this.setupBackground();
        }
    }

    /**
     * Set Focus color of the button
     *
     * @param color : use Color.parse('#code')
     */
    public void setFocusBackgroundColor(int color) {
        this.mFocusBackgroundColor = color;
        if (mIconView != null || mFontIconView != null || mTextView != null)
            this.setupBackground();

    }

    /**
     * Set Disabled state color of the button
     *
     * @param color : use Color.parse('#code')
     */
    public void setDisableBackgroundColor(int color) {
        this.mDisabledBackgroundColor = color;
        if (mIconView != null || mFontIconView != null || mTextView != null)
            this.setupBackground();

    }

    /**
     * Set Disabled state color of the button text
     *
     * @param color : use Color.parse('#code')
     */
    public void setDisableTextColor(int color) {
        this.mDisabledTextColor = color;
        if (mTextView == null)
            initializeFancyButton();
        else if (!mEnabled)
            mTextView.setTextColor(color);

    }

    /**
     * Set Disabled state color of the button border
     *
     * @param color : use Color.parse('#code')
     */
    public void setDisableBorderColor(int color) {
        this.mDisabledBorderColor = color;
        if (mIconView != null || mFontIconView != null || mTextView != null) {
            this.setupBackground();
        }

    }

    /**
     * Set the size of Text in sp
     *
     * @param textSize : Text Size
     */
    public void setTextSize(int textSize) {
        this.mDefaultTextSize = spToPx(getContext(), textSize);
        if (mTextView != null)
            mTextView.setTextSize(textSize);
    }

    /**
     * Set the gravity of Text
     *
     * @param gravity : Text Gravity
     */

    public void setTextGravity(int gravity) {
        this.mDefaultTextGravity = gravity;
        if (mTextView != null) {
            this.setGravity(gravity);
        }
    }

    /**
     * Set Padding for mIconView and mFontIconSize
     *
     * @param paddingLeft   : Padding Left
     * @param paddingTop    : Padding Top
     * @param paddingRight  : Padding Right
     * @param paddingBottom : Padding Bottom
     */
    public void setIconPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        this.mIconPaddingLeft = paddingLeft;
        this.mIconPaddingTop = paddingTop;
        this.mIconPaddingRight = paddingRight;
        this.mIconPaddingBottom = paddingBottom;
        if (mIconView != null) {
            mIconView.setPadding(this.mIconPaddingLeft, this.mIconPaddingTop, this.mIconPaddingRight, this.mIconPaddingBottom);
        }
        if (mFontIconView != null) {
            mFontIconView.setPadding(this.mIconPaddingLeft, this.mIconPaddingTop, this.mIconPaddingRight, this.mIconPaddingBottom);
        }
    }

    /**
     * Set an icon from resources to the button
     *
     * @param drawable : Int resource
     */
    public void setIconResource(int drawable) {
        this.mIconResource = mContext.getResources().getDrawable(drawable);
        if (mIconView == null || mFontIconView != null) {
            mFontIconView = null;
            initializeFancyButton();
        } else
            mIconView.setImageDrawable(mIconResource);
    }

    /**
     * Set a drawable to the button
     *
     * @param drawable : Drawable resource
     */
    public void setIconResource(Drawable drawable) {
        this.mIconResource = drawable;
        if (mIconView == null || mFontIconView != null) {
            mFontIconView = null;
            initializeFancyButton();
        } else
            mIconView.setImageDrawable(mIconResource);
    }

    /**
     * Set a font icon to the button (eg FFontAwesome or Entypo...)
     *
     * @param icon : Icon value eg : \uf082
     */
    public void setIconResource(String icon) {
        this.mFontIcon = icon;
        if (mFontIconView == null) {
            mIconView = null;
            initializeFancyButton();
        } else
            mFontIconView.setText(icon);
    }

    /**
     * Set Icon size of the button (for only font icons) in sp
     *
     * @param iconSize : Icon Size
     */
    public void setFontIconSize(int iconSize) {
        this.mFontIconSize = spToPx(getContext(), iconSize);
        if (mFontIconView != null)
            mFontIconView.setTextSize(iconSize);
    }

    /**
     * Set Icon Position
     * Use the global variables (FancyButton.POSITION_LEFT, FancyButton.POSITION_RIGHT,
     * FancyButton.POSITION_TOP, FancyButton.POSITION_BOTTOM)
     *
     * @param position : Position
     */
    public void setIconPosition(int position) {
        if (position > 0 && position < 5)
            mIconPosition = position;
        else
            mIconPosition = POSITION_LEFT;

        initializeFancyButton();
    }

    /**
     * Set color of the button border
     *
     * @param color : Color
     *              use Color.parse('#code')
     */
    public void setBorderColor(int color) {
        this.mBorderColor = color;
        if (mIconView != null || mFontIconView != null || mTextView != null) {
            this.setupBackground();
        }
    }

    /**
     * Set Width of the button
     *
     * @param width : Width
     */
    public void setBorderWidth(int width) {
        this.mBorderWidth = width;
        if (mIconView != null || mFontIconView != null || mTextView != null) {
            this.setupBackground();
        }
    }

    /**
     * Set Border Radius of the button
     *
     * @param radius : Radius
     */
    public void setRadius(int radius) {
        this.mRadius = radius;
        if (mIconView != null || mFontIconView != null || mTextView != null) {
            this.setupBackground();
        }
    }

    /**
     * Set Border Radius for each button corner
     * Top Left, Top Right, Bottom Left, Bottom Right
     *
     * @param radius : Array of int
     */
    public void setRadius(int[] radius) {
        this.mRadiusTopLeft = radius[0];
        this.mRadiusTopRight = radius[1];
        this.mRadiusBottomLeft = radius[2];
        this.mRadiusBottomRight = radius[3];

        if (mIconView != null || mFontIconView != null || mTextView != null) {
            this.setupBackground();
        }
    }

    /**
     * Set custom font for button Text
     *
     * @param fontName : Font Name
     *                 Place your text fonts in assets
     */
    public void setCustomTextFont(String fontName) {
        mTextTypeFace = findFont(mContext, fontName, mDefaultTextFont);

        if (mTextView == null)
            initializeFancyButton();
        else
            mTextView.setTypeface(mTextTypeFace, textStyle);
    }

    /**
     * Set Custom font for button icon
     *
     * @param fontName : Font Name
     *                 Place your icon fonts in assets
     */
    public void setCustomIconFont(String fontName) {

        mIconTypeFace = findFont(mContext, fontName, mDefaultIconFont);

        if (mFontIconView == null)
            initializeFancyButton();
        else
            mFontIconView.setTypeface(mIconTypeFace);

    }

    /**
     * Override setEnabled and rebuild the fancybutton view
     * To redraw the button according to the state : enabled or disabled
     *
     * @param value
     */
    @Override
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        this.mEnabled = value;
        initializeFancyButton();

    }

    /**
     * Setting the button to have hollow or solid shape
     *
     * @param ghost
     */
    public void setGhost(boolean ghost) {
        this.mGhost = ghost;

        if (mIconView != null || mFontIconView != null || mTextView != null) {
            this.setupBackground();
        }

    }

    /**
     * If enabled, the button title will ignore its custom font and use the default system font
     *
     * @param status : true || false
     */
    public void setUsingSystemFont(boolean status) {
        this.mUseSystemFont = status;
    }

    /**
     * Return Text of the button
     *
     * @return Text
     */
    public CharSequence getText() {
        if (mTextView != null)
            return mTextView.getText();
        else
            return "";
    }

    /**
     * Return TextView Object of the FancyButton
     *
     * @return TextView Object
     */
    public TextView getTextViewObject() {
        return mTextView;
    }

    /**
     * Return Icon Font of the FancyButton
     *
     * @return TextView Object
     */
    public TextView getIconFontObject() {
        return mFontIconView;
    }

    /**
     * Return Icon of the FancyButton
     *
     * @return ImageView Object
     */
    public ImageView getIconImageObject() {
        return mIconView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private class CustomOutline extends ViewOutlineProvider {

        int width;
        int height;

        CustomOutline(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public void getOutline(View view, Outline outline) {

            if (mRadius == 0) {
                outline.setRect(0, 10, width, height);
            } else {
                outline.setRoundRect(0, 10, width, height, mRadius);
            }

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new CustomOutline(w, h));
        }
    }

    /**
     * @param context : context
     * @param px : pixel
     * @return : int
     */
    private int pxToSp(final Context context, final float px) {
        return Math.round(px / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * @param context : context
     * @param sp:size
     * @return int: value
     */
    private int spToPx(final Context context, final float sp) {
        return Math.round(sp * context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * <h1>Load all custom font </h1>
     *
     * @param context:         context
     * @param fontPath:        fontPath
     * @param defaultFontPath: defaultPath
     * @return Typeface: typeface
     */
    private Typeface findFont(Context context, String fontPath, String defaultFontPath) {

        if (fontPath == null) {
            return Typeface.DEFAULT;
        }

        String fontName = new File(fontPath).getName();
        String defaultFontName = "";
        if (Text.isNotEmpty(defaultFontPath)) {
            defaultFontName = new File(defaultFontPath).getName();
        }

        if (cachedFontMap.containsKey(fontName)) {
            return cachedFontMap.get(fontName);
        } else {
            try {
                AssetManager assets = context.getResources().getAssets();

                if (Arrays.asList(assets.list("")).contains(fontPath)) {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
                    cachedFontMap.put(fontName, typeface);
                    return typeface;
                } else if (Arrays.asList(assets.list("fonts")).contains(fontName)) {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s", fontName));
                    cachedFontMap.put(fontName, typeface);
                    return typeface;
                } else if (Arrays.asList(assets.list("iconfonts")).contains(fontName)) {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), String.format("iconfonts/%s", fontName));
                    cachedFontMap.put(fontName, typeface);
                    return typeface;
                } else if (Text.isNotEmpty(defaultFontPath) && Arrays.asList(assets.list("")).contains(defaultFontPath)) {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), defaultFontPath);
                    cachedFontMap.put(defaultFontName, typeface);
                    return typeface;
                } else {
                    throw new Exception("Font not Found");
                }

            } catch (Exception e) {
                cachedFontMap.put(fontName, Typeface.DEFAULT);
                return Typeface.DEFAULT;
            }
        }
    }
}
