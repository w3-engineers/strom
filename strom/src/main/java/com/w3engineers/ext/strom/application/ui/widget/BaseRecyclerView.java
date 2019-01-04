package com.w3engineers.ext.strom.application.ui.widget;

/*
 *  ****************************************************************************
 *  * Created by : Md. Azizul Islam on 7/4/2018 at 11:59 AM.
 *  *
 *  * Purpose: To customize android RecyclerView and provide our
 *  * default setting
 *  *
 *  * Last edited by : Md. Azizul Islam on 7/4/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.w3engineers.ext.strom.R;
import com.w3engineers.ext.strom.application.ui.base.BaseAdapter;

/**
 * Convenient widget for recyclerview. It is tightly couple with {@link BaseAdapter}.
 * It automatically manages the UI when user has no data to show in recycler.
 */
public class BaseRecyclerView extends RecyclerView {
    /**
     * Default animation enable disable
     */
    private boolean isDefaultAnimationEnable = false;
    /**
     * Horizontal or vertical mode value
     */
    private int mViewMode = 0;

    /**
     * Recycler view empty layout id
     */
    private int mEmptyLayoutId;

    /**
     * Progress view
     * This is optional property to show progress bar
     */
    private int mProgressViewId;
    /**
     * Scrolling mode vertical
     */
    private final int VERTICAL_MODE = 1;
    /**
     * Scrolling mode horizontal
     */
    private final int HORIZONTAL_MODE = 2;

    /**
     * Constructor
     *
     * @param context: context
     */
    public BaseRecyclerView(Context context) {
        super(context);

    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViewProperty(context, attrs);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        if (mViewMode != 0) {
            if (mViewMode == VERTICAL_MODE) {
                velocityY *= 0.7;
            } else if (mViewMode == HORIZONTAL_MODE) {
                velocityX *= 0.7;
            }
        }
        return super.fling(velocityX, velocityY);
    }

    /**
     * <h1>Init view property value</>
     *
     * @param context: context
     * @param attrs:   attributes
     */
    private void initViewProperty(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseRecyclerView);
        isDefaultAnimationEnable = typedArray.getBoolean(R.styleable.BaseRecyclerView_brv_defaultAnimation, false);
        mViewMode = typedArray.getInt(R.styleable.BaseRecyclerView_brv_viewMode, 0);
        mEmptyLayoutId = typedArray.getResourceId(R.styleable.BaseRecyclerView_brv_emptyLayoutId, 0);
        mProgressViewId = typedArray.getResourceId(R.styleable.BaseRecyclerView_brv_progressViewId, 0);

        if(mEmptyLayoutId == 0){
            throw new RuntimeException("Empty layout id not found");
        }
        disableDefaultAnimation();
    }

    private void disableDefaultAnimation() {
        if (!isDefaultAnimationEnable) {
            this.setItemAnimator(null);
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
    }

    public void setDefaultAnimationEnable(boolean defaultAnimationEnable) {
        isDefaultAnimationEnable = defaultAnimationEnable;
    }

    /**
     *
     * @return: int return empty view id
     *
     */
    public int getEmptyLayoutId(){
        return mEmptyLayoutId;
    }

    /**
     *
     * @return: Int progress view ai
     */
    public int getProgressViewId(){
        return mProgressViewId;
    }
}
