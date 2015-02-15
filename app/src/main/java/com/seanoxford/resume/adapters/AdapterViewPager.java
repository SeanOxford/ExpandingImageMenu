package com.seanoxford.resume.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.seanoxford.resume.fragments.FragmentAboutMe;
import com.seanoxford.resume.fragments.FragmentGuestBook;
import com.seanoxford.resume.fragments.FragmentProjects;
public class AdapterViewPager extends FragmentStatePagerAdapter {


    final private static int FRAGMENT_ABOUT = 0;
    final private static int FRAGMENT_PROJECTS = 1;
    final private static int FRAGMENT_GUESTBOOK = 2;

    protected FragmentManager mFragmentManager;



    public AdapterViewPager(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch(position){
            case FRAGMENT_ABOUT:
                fragment = new FragmentAboutMe();
                break;
            case FRAGMENT_PROJECTS:
                fragment = new FragmentProjects();
                break;
            case FRAGMENT_GUESTBOOK:
                fragment = new FragmentGuestBook();
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";

        switch(position){
            case FRAGMENT_ABOUT:
                title = "About";
                break;
            case FRAGMENT_PROJECTS:
                title = "Projects";
                break;
            case FRAGMENT_GUESTBOOK:
                title = "Interview";
                break;
        }

        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
