package io.left.core.sample.ui.details;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import core.left.io.framework.application.ui.widget.BaseRecyclerView;
import io.left.core.sample.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/*
 *  ****************************************************************************
 *  * Created by : Md. Azizul Islam on 7/11/2018 at 10:17 PM.
 *  * Email : azizul@w3engineers.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md. Azizul Islam on 7/11/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

public class DetailsActivityTest {
    @Rule
    public ActivityTestRule<DetailsActivity> activityTestRule = new ActivityTestRule<DetailsActivity>(DetailsActivity.class);

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void deleteMessage(){
        delay(1000);

        if (getRowCount() > 0) {
            onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return null;
                }

                @Override
                public void perform(UiController uiController, View view) {
                    view.findViewById(R.id.btn_delete).performClick();
                }
            }));
        }

        delay(2000);
    }
    private int getRowCount() {
        BaseRecyclerView recyclerView = (BaseRecyclerView) activityTestRule.getActivity().findViewById(R.id.rv);
        return recyclerView.getAdapter().getItemCount();
    }

    private void delay(long item) {
        try {
            Thread.sleep(item);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}