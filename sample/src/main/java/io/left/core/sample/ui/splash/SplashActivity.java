package io.left.core.sample.ui.splash;

/*
 *  ****************************************************************************
 *  * Created by : Md. Azizul Islam on 7/5/2018 at 4:28 PM.
 *  * Email : azizul@w3engineers.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md. Azizul Islam on 7/5/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.w3engineers.ext.strom.application.ui.base.BaseActivity;
import io.left.core.sample.R;
import io.left.core.sample.data.service.ServiceLocator;
import io.left.core.sample.databinding.ActivitySplashBinding;
import io.left.core.sample.ui.details.DetailsActivity;

public class SplashActivity extends BaseActivity {

    private ActivitySplashBinding dataInputBinding;
    private SplashViewModel mSplashViewModel;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void startUI() {
        dataInputBinding = (ActivitySplashBinding)getViewDataBinding();
        Animation downTop = AnimationUtils.loadAnimation(this, R.anim.downtop);
        dataInputBinding.imageAppName.setAnimation(downTop);

        mSplashViewModel = getSplashViewModel();
        mSplashViewModel.getRegistrationStatusWithDelay(null).observe(this, integer -> {

            //In sample no use of registration value, real app would need the value

            Intent intent = new Intent(this, DetailsActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });

        printData();
    }

    @SuppressWarnings("unchecked")
    private SplashViewModel getSplashViewModel() {

        return ViewModelProviders.of(this, new ViewModelProvider.Factory() {

            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {


                return (T) ServiceLocator.getInstance().getSplashViewModel(getApplication());

            }

        }).get(SplashViewModel.class);
    }

    /**
     * Only for test purpose. Please do not delete unless any serious requirement is there.
     */
    private void printData() {
        /*for(int I = 0; I < 100; I++) {
            Timber.d(TimeUtility.getTimeAgo(Utility.getRandomNumberInRange(153070, 153171) * 10000000L));
        }*/

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Timber.d("SharedPref %s", SharedPref.getSharedPref(getApplicationContext()).readInt("abc"));
            }
        }, "Thread 1").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SharedPref.getSharedPref(getApplicationContext()).write("abc", 45);
            }
        }, "Thread 2").start();*/
    }
}
