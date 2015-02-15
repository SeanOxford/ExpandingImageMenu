package com.seanoxford.resume.widgets;

import com.seanoxford.resume.customviews.CustomChildLayout;
import com.seanoxford.resume.customviews.CustomRelativeLayout;

/**
 * Created by labtob on 2/13/2015.
 */
public class BackPressedInfoContainer {
    boolean mIsExpanded;
    CustomRelativeLayout mCustomRelativeLayout;
    CustomChildLayout mCustomChildLayout;

    public BackPressedInfoContainer(boolean isExpanded, CustomRelativeLayout customRelativeLayout, CustomChildLayout customChildLayout) {
        mIsExpanded = isExpanded;
        mCustomRelativeLayout = customRelativeLayout;
        mCustomChildLayout = customChildLayout;
    }

    public boolean getIsExpanded() {
        return mIsExpanded;
    }

    public CustomRelativeLayout getCustomRelativeLayout() {
        return mCustomRelativeLayout;
    }

    public CustomChildLayout getCustomChildLayout() {
        return mCustomChildLayout;
    }

    public void contractLayout() {
        if (mIsExpanded)
            mCustomRelativeLayout.onContractClick(mCustomChildLayout);
    }

}