package com.w3engineers.ext.strom.application.ui.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.w3engineers.ext.strom.R;
import com.w3engineers.ext.strom.application.data.BaseResponse;
import com.w3engineers.ext.strom.util.helper.Toaster;
import com.w3engineers.ext.strom.util.helper.image.imgpicker.ImagePicker;

import io.reactivex.disposables.CompositeDisposable;

/*
 * ****************************************************************************
 * * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
 * *
 * * Created by:
 * * Name : Anjan Debnath
 * * Date : 10/25/17
 * *
 * * Purpose: Abstract activity that every other Activity in this application must extends.
 * *
 * * Last Edited by : SUDIPTA KUMAR PAIK on 03/15/18.
 * * History:
 * * 1:
 * * 2:
 * *
 * * Last Reviewed by : SUDIPTA KUMAR PAIK on 03/15/18.
 * ****************************************************************************
 */

/**
 * Abstract class. All common activity related task happens here.
 */
public abstract class BaseActivity
        extends AppCompatActivity implements View.OnClickListener {

    //FixMe: have to enable for commit Fragment in Activity
    protected BaseFragment mBaseCurrentFragment;
    private ViewDataBinding mViewDataBinding;
    private Menu mMenu;
    private BaseToolBar baseToolbar;
    private CompositeDisposable mCompositeDisposable;
    //https://stackoverflow.com/questions/8849121/android-why-should-ids-of-views-should-be-positive-numbers
    private final static int DEFAULT_ID_VALUE = 0;

    /*
     * Start Activity with intent
     * */
    protected static void runCurrentActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    /*
     * Child class have to implement this method. On this method you will pass the layout file of current activity
     * */
    protected abstract int getLayoutId();

    /*
     * Child class can(optional) override this method. On this method you will pass the toolbar id of current layout
     * */
    protected int getToolbarId() {
        return DEFAULT_ID_VALUE;
    }

    /*
     * Child class can(optional) override this method. On this method you will pass the menu file of current activity
     * */
    protected int getMenuId() {
        return DEFAULT_ID_VALUE;
    }

    /*
     * Child class can(optional) override this method. On this method you will pass the color id
     * */
    protected int statusBarColor() {
        return DEFAULT_ID_VALUE;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layoutId = getLayoutId();
        if (layoutId > DEFAULT_ID_VALUE) {

            mCompositeDisposable = new CompositeDisposable();

            updateLayoutView(layoutId);

            setStatusBarColor();

            int toolbarId = getToolbarId();
            if (toolbarId > DEFAULT_ID_VALUE) {
                baseToolbar = findViewById(toolbarId);
                setSupportActionBar(baseToolbar);
                baseToolbar.setActivityContext(this);
            }

        }
        startUI();
    }

    private void updateLayoutView(int layoutId) {
        try {
            mViewDataBinding = DataBindingUtil.setContentView(this, layoutId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mViewDataBinding == null) {
            setContentView(layoutId);
        }
    }

    /*
     * Child class have to implement this method. This method run on onStart lifecycle
     * */
    protected abstract void startUI();

    /*
     * Child class have to implement this method. This method run on onDestroy lifecycle
     * */
    protected void stopUI() {}

    /*
     * Return current viewDataBinding
     * */
    protected ViewDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }

    private void setStatusBarColor() {

        int statusBarColor = statusBarColor();

        if (statusBarColor > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(this, statusBarColor));
            }
        }
    }

    @Override
    public void onClick(View view) {
    }

    /*
     * To get the current menu. It will return current menu if you set it. Otherwise return null.
     **/
    protected Menu getMenu() {
        return mMenu;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuId() > DEFAULT_ID_VALUE) {
            getMenuInflater().inflate(getMenuId(), menu);
            this.mMenu = menu;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void refreshMenu() {
        supportInvalidateOptionsMenu();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.stopUI();
    }

    /**
     * To set title on toolbar
     *
     * @param title string value
     */
    protected void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    /**
     * To set sub title on toolbar
     *
     * @param subtitle string value
     */
    public void setSubtitle(String subtitle) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(subtitle);
        }
    }

    /**
     * To set both title and subtitle in toolbar
     *
     * @param title    string value
     * @param subtitle string value
     */
    public void setToolbarText(String title, String subtitle) {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setSubtitle(subtitle);
        }
    }

    /**
     * To set click listener on any view, You can pass multiple view at a time
     *
     * @param views View as params
     */
    protected void setClickListener(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    /**
     * To set animation on any view
     *
     * @param views View as params
     */
    protected void setAnimation(View... views) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.grow_effect);

        for (View view : views) {
            view.startAnimation(animation);
        }
    }

    /**
     * Commit child fragment of BaseFragment on a frameLayout
     *
     * @param viewId       int value
     * @param baseFragment BaseFragment object
     */
    protected void commitFragment(int viewId, BaseFragment baseFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(viewId, baseFragment, baseFragment.getClass().getName())
                .commit();

        setCurrentFragment(baseFragment);
    }

    /*
     * Get current running fragment
     * */
    protected BaseFragment getCurrentFragment() {
        return mBaseCurrentFragment;
    }

    private void setCurrentFragment(BaseFragment baseFragment) {
        this.mBaseCurrentFragment = baseFragment;
    }

    /**
     * Process response. <br/>
     * If fail then show toast and call {@link #onFailResponse(BaseResponse)} <br/>
     * If success then call {@link #onSuccessResponse(BaseResponse)}. Upon failure show passed fauled
     * text. If no text passed then it tries to generate appropriate message
     * @param baseResponse
     * @param failMessage
     * @param <T>
     * @return
     */
    protected <T extends BaseResponse> boolean onResponse(T baseResponse, String failMessage) {
        String messageToShow = failMessage;

        if(baseResponse != null) {
            if(baseResponse.mResponseCode == BaseResponse.RESPONSE_SUCCESS) {

                onSuccessResponse(baseResponse);
                return true;

            } else if(baseResponse.mResponseCode == BaseResponse.RESPONSE_PARAMETER_ILLEGAL ||
                    baseResponse.mResponseCode == BaseResponse.RESPONSE_COULD_NOT_REACH_SERVER) {

                if(TextUtils.isEmpty(failMessage)) {

                    messageToShow = baseResponse.mResponseMessage;

                }

            }
        }

        Toaster.showLong(messageToShow);

        onFailResponse(baseResponse);
        return false;
    }

    /**
     * Call back method upon success response.
     * @param baseResponse
     * @param <T>
     */
    protected <T extends BaseResponse> void onSuccessResponse(@NonNull T baseResponse) {
        // TODO: 7/26/2018 Will notify base adapter object and such wiring will be provided hopefully in next version
        //So that we will be able to manage, empty layout, progress bar, fail message and other such
        // kind of common task from a framework layer or from a common place
    }

    /**
     * Call back method upon fail response
     * @param <T>
     */
    protected <T extends BaseResponse> void onFailResponse(T baseResponse) {}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ImagePicker.REQUEST_CODE_PICK_IMAGE:

                onImage(ImagePicker.getInstance().postProcessImagePick(getApplicationContext(), requestCode, resultCode, data));

                break;

            default:
                break;
        }
    }

    protected void onImage(Uri imageUri) {}
}
