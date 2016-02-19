package com.example.ase_group_1.health_app;


import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ljm7b on 2/18/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    private String email;
    private String badEmail;
    private String password;
    private String badPassword;

    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Before
    public void initValidString(){
        email = "admin@admin.com";
        badEmail = "cat";
        password = "admin";
        badPassword = "a";
    }

    @Test
    public void testSuccessLogin() throws Exception{
        Intents.init();

        onView(withId(R.id.email))
                .perform(typeText(email));

        onView(withId(R.id.password))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        rule.launchActivity(new Intent());
        intended(hasComponent(LoginActivity.class.getName()));
        intended(hasComponent(MainActivity.class.getName()), times(1));
        Intents.release();
    }

    @Test
    public void testFailedLoginBadPassword() throws Exception{
        Intents.init();

        onView(withId(R.id.email))
                .perform(typeText(email));

        onView(withId(R.id.password))
                .perform(typeText(badPassword), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        rule.launchActivity(new Intent());
        intended(hasComponent(LoginActivity.class.getName()));
        intended(hasComponent(MainActivity.class.getName()), times(0));
        Intents.release();
    }

    @Test
    public void testFailedLoginBadEmail() throws Exception{
        Intents.init();

        onView(withId(R.id.email))
                .perform(typeText(badEmail));

        onView(withId(R.id.password))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        rule.launchActivity(new Intent());
        intended(hasComponent(LoginActivity.class.getName()));
        intended(hasComponent(MainActivity.class.getName()), times(0));
        Intents.release();
    }

    @Test
    public void testFailedLoginBadEmailAndBadPassword() throws Exception{
        Intents.init();

        onView(withId(R.id.email))
                .perform(typeText(badEmail));

        onView(withId(R.id.password))
                .perform(typeText(badPassword), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        rule.launchActivity(new Intent());
        intended(hasComponent(LoginActivity.class.getName()));
        intended(hasComponent(MainActivity.class.getName()), times(0));
        Intents.release();
    }

}
