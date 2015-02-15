package com.seanoxford.resume.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.seanoxford.resume.R;
import com.seanoxford.resume.adapters.AdapterViewPager;
import com.seanoxford.resume.customviews.CustomRelativeLayout;
import com.seanoxford.resume.fragments.FragmentAboutMe;
import com.seanoxford.resume.widgets.BackPressedInfoContainer;


public class ActivityMain extends BaseActivity implements FragmentAboutMe.BackPressedListener{
    protected ViewPager mViewPager;
    protected BackPressedInfoContainer mBackPressedInfoContainer;

    @Override
    public void onBackPressed() {
        if(mBackPressedInfoContainer == null || !mBackPressedInfoContainer.getIsExpanded())
            super.onBackPressed();
        else
            mBackPressedInfoContainer.contractLayout();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mViewPager.setAdapter(new AdapterViewPager(getSupportFragmentManager()));


        PagerSlidingTabStrip pageSliderWidget = (PagerSlidingTabStrip) findViewById(R.id.widget_tabs);
        pageSliderWidget.setViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void isResumeExpanded(BackPressedInfoContainer backPressedInfoContainer) {
        mBackPressedInfoContainer = backPressedInfoContainer;
    }
}
