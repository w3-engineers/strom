package core.left.io.framework.application.ui.widget;

/*
 *  ****************************************************************************
 *  * Created by : Md. Azizul Islam on 7/11/2018 at 3:50 PM.
 *  * Email : azizul@w3engineers.com
 *  *
 *  * Purpose: Custom button wrapper class to make mandatory put a drawable
 *  *
 *  * Last edited by : Md. Azizul Islam on 7/11/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import core.left.io.framework.R;

/**
 * Convenient button extended from {@link AppCompatButton}. Developers should use this button as it
 * forces developer to include a drawable which should specify enable disable state separation.
 */
public class BaseButton extends AppCompatButton {
    /**
     * Background drawable
     */
    private Drawable mDrawable;
    private Context mContext;
    public BaseButton(Context context) {
        super(context);
        mContext = context;
    }

    /**
     * <h1>Constructor to init attribute</h1>
     *
     * @param context: context
     * @param attrs : attributes
     */
    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray attrsArray = context.obtainStyledAttributes(attrs, R.styleable.BaseButton, 0, 0);
        initAttributesArray(attrsArray);
    }


    /**
     * <h1>Init attributes</h1>
     *
     * @param attrsArray: attributes
     */
    private void initAttributesArray(TypedArray attrsArray){
        mDrawable = attrsArray.getDrawable(R.styleable.BaseButton_bb_drawable);
        attrsArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(mDrawable == null){
            throw new RuntimeException(mContext.getString(R.string.drawable_exception));
        }else {
            setBackground(mDrawable);
        }
    }
}
