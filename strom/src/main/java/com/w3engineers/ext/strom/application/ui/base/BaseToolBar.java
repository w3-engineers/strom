package com.w3engineers.ext.strom.application.ui.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;

import com.w3engineers.ext.strom.R;

/**
 * Created by Anjan Debnath on 6/27/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 *
 * Application will use this toolbar normally. It facilitates to set title directly. It is internally
 * coupled with {@link BaseActivity}. A boolean to set back button in a convenient way.
 *
 * Pending: Inflate app bar added toolbar. Internally connected with Fragment
 */
public class BaseToolBar extends Toolbar implements ISetActivity {

    private boolean showHomeButton;
    private String toolbarTitle = null;
    private AppCompatActivity activity;


    @Override
    public void setActivityContext(AppCompatActivity activity) {
        this.activity = activity;

        // if true then home button will be enabled
        if(showHomeButton){
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        //this will set custom toolbar text
        if(toolbarTitle!= null){
            Log.e("title", toolbarTitle);
            activity.getSupportActionBar().setTitle(toolbarTitle);
        }

    }

    public BaseToolBar(Context context) {
        super(context);
        init(context, null);
    }

    public BaseToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attributeSet){

        if(attributeSet != null){
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.BaseToolBar);
            try {
                showHomeButton = typedArray.getBoolean(R.styleable.BaseToolBar_showHomeButton, false);

                if(typedArray.hasValue(R.styleable.BaseToolBar_customTitle)){
                    toolbarTitle = typedArray.getString(R.styleable.BaseToolBar_customTitle);
                }

            } finally {
                typedArray.recycle();
            }
        }

    }
}
