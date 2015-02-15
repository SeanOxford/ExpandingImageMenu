package com.seanoxford.resume.activities;

import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;

public abstract class BaseActivity extends FragmentActivity {

    public Typeface getRobotoBlack(){
        return Typeface.createFromAsset(getAssets(), "Roboto-Black.ttf");
    }

    public Typeface getRobotoLight(){
        return Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
    }
    public Typeface getRobotoMedium(){
        return Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
    }
    public Typeface getRobotoThin(){
        return Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
    }










}
