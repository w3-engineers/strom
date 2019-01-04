package core.left.io.util.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;


/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-16 at 1:11 PM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-16 at 1:11 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-16 at 1:11 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/

/**
 * Convenient RxScheduler. Generated this rule to behave rx as linear and in a single thread.
 * It will be basically helpful when any rx operation take a significant time in any non default thread.
 * Apply this rule in all of your rx related testing.
 * For further info you can read:
 * @see <a href="https://medium.com/@nicolas.duponchel/testing-viewmodel-in-mvvm-using-livedata-and-rxjava-b27878495220">Testing With RxJava</a>
 */
public class RxSchedulerRule implements TestRule {

    private static Scheduler SCHEDULER_INSTANCE = Schedulers.trampoline();


    @Override
    public Statement apply(Statement base, Description description) {

        Statement statement = new Statement() {
            @Override
            public void evaluate() throws Throwable {

                RxAndroidPlugins.reset();
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> SCHEDULER_INSTANCE);

                RxJavaPlugins.reset();
                RxJavaPlugins.setIoSchedulerHandler(schedulerCallable -> SCHEDULER_INSTANCE);
                RxJavaPlugins.setNewThreadSchedulerHandler(schedulerCallable -> SCHEDULER_INSTANCE);
                RxJavaPlugins.setComputationSchedulerHandler(schedulerCallable -> SCHEDULER_INSTANCE);

                base.evaluate();
            }
        };

        return statement;
    }
}
