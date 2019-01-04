package io.left.core.sample.ui.datainput;

import android.provider.ContactsContract;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.left.core.sample.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.typeText;

import static org.junit.Assert.*;

/*
 *  ****************************************************************************
 *  * Created by : Md. Azizul Islam on 7/11/2018 at 3:42 PM.
 *  *
 *  * Purpose: To test data input activity
 *  *
 *  * Last edited by : Md. Azizul Islam on 7/11/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */
@RunWith(AndroidJUnit4.class)
public class DataInputActivityTest {

    @Rule
    public ActivityTestRule<DataInputActivity>  activityTestRule = new ActivityTestRule<>(DataInputActivity.class);

    @Before
    public void setUP(){}
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDataInput(){
        delay(1000);
        onView(withId(R.id.name_tv)).perform(typeText("Test "+System.currentTimeMillis()));

        delay(1000);
        onView(withId(R.id.btn_add_item)).perform(click());

        delay(1000);

    }
    private void delay(long item) {
        try {
            Thread.sleep(item);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}