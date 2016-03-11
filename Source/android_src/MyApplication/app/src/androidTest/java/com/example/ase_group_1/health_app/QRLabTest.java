package com.example.ase_group_1.health_app;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.Intents.times;
/**
 * Created by ljm7b on 3/10/2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class QRLabTest {



    @Rule
    public ActivityTestRule<ClinicalTest> rule =
            new ActivityTestRule<>(ClinicalTest.class);


    @Test
    public void DateOpensQRTest() throws Exception{
        Intents.init();
        SystemClock.sleep(5000);
        onView(withId(R.id.clinical_test_date_list)).perform(click());

        rule.launchActivity(new Intent());
        SystemClock.sleep(5000);
        intended(hasComponent(ClinicalTest.class.getName()));
        intended(hasComponent(QRCodeAndDetailsActivity.class.getName()), times(1));
        Intents.release();
    }
}
