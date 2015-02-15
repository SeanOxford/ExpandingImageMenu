package com.seanoxford.resume.widgets;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by labtob on 1/30/2015.
 */
public class ResumeApplication extends Application {

    private static ResumeApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    static public ResumeApplication getInstance(){
        return instance;
    }


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
