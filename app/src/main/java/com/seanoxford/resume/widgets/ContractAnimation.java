package com.seanoxford.resume.widgets;

import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seanoxford.resume.customviews.CustomChildLayout;
import com.seanoxford.resume.customviews.CustomImageView;
import com.seanoxford.resume.customviews.CustomRelativeLayout;

public class ContractAnimation extends Animation {

    public interface Listener {
        abstract void onContractFinish();
    }

    protected CustomRelativeLayout mCustomRelativeLayout;
    protected CustomChildLayout mCustomChildLayout;
    protected CustomImageView mCustomImageView;

    protected int[] mTopDimensions;
    protected int mTotalHeight;
    protected int mIndividualHeight;
    protected int mPreviousHeight = 0;

    protected float mIncrementedDownwardTransition;
    protected float mIncrementedUpwardTransition;
    protected float mDownwardDivisor = 0;
    protected float mUpwardDivisor = 0;

    protected boolean mToggle = true;
    protected boolean mIsMiddleView = false;

    protected Listener mListener;

    public ContractAnimation(CustomRelativeLayout customRelativeLayout, CustomChildLayout customChildLayout, Listener listener) {
        mListener = listener;
        mCustomRelativeLayout = customRelativeLayout;
        mCustomChildLayout = customChildLayout;
        mCustomImageView = (CustomImageView) mCustomChildLayout.getChildAt(CustomChildLayout.CUSTOMIMAGEVIEW_POSITION);
        mTotalHeight = mCustomRelativeLayout.getTotalHeight();
        mIndividualHeight = mCustomRelativeLayout.getIndividualHeight();
        mTopDimensions = customRelativeLayout.mPositionsArray;
        mPreviousHeight = mTotalHeight;

        if (mCustomRelativeLayout.getChildCount() % 2 == 1)
            if (mCustomChildLayout.getViewPosition() == mCustomRelativeLayout.getChildCount() / 2)
                mIsMiddleView = true;

        if (mCustomChildLayout.getViewPosition() != 0)
            mDownwardDivisor = ((float) (mCustomRelativeLayout.getChildCount() - 1) / (float) mCustomChildLayout.getViewPosition());

        if (mCustomChildLayout.getViewPosition() != mCustomRelativeLayout.getChildCount() - 1)
            mUpwardDivisor = ((float) (mCustomRelativeLayout.getChildCount() - 1) / (float) (mCustomRelativeLayout.getChildCount() - 1 - mCustomChildLayout.getViewPosition()));

        setDuration(600);
        setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mCustomRelativeLayout.deactivateButtons();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mListener.onContractFinish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int totalGrowth = mTotalHeight - mIndividualHeight;
        int currentHeight = mTotalHeight - Math.round((totalGrowth * interpolatedTime));
        int heightDelta = mPreviousHeight - currentHeight;

        float downwardTransitionIncrement = mCustomChildLayout.getViewPosition() != 0 ? (float) heightDelta / mDownwardDivisor : 0;
        float upwardTransitionIncrement = mCustomChildLayout.getViewPosition() != mCustomRelativeLayout.getChildCount() - 1 ? (float) heightDelta / mUpwardDivisor : 0;

        mIncrementedDownwardTransition += downwardTransitionIncrement;
        mIncrementedUpwardTransition -= upwardTransitionIncrement;

        if (interpolatedTime == 1) {
            mCustomChildLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mIndividualHeight));
        } else {
            int topIncrement;
            int bottomIncrement;

            if (!mIsMiddleView) {
                bottomIncrement = Math.round(mIncrementedUpwardTransition);
                topIncrement = Math.round(mIncrementedDownwardTransition);
            } else if (!mToggle && mIsMiddleView) {
                bottomIncrement = Math.round(mIncrementedUpwardTransition);
                topIncrement = (int) mIncrementedDownwardTransition;
                mToggle = true;
            } else {
                bottomIncrement = (int) mIncrementedUpwardTransition;
                topIncrement = Math.round(mIncrementedDownwardTransition);
                mToggle = false;
            }

            int layoutBottom = mTotalHeight + bottomIncrement;
            int imageBottom = mTotalHeight - topIncrement + bottomIncrement;

            mCustomChildLayout.layout(0, topIncrement, mCustomChildLayout.getMeasuredWidth(), layoutBottom);
            mCustomImageView.layout(0, 0, mCustomChildLayout.getMeasuredWidth(), imageBottom);

        }
        mPreviousHeight = currentHeight;
    }


}
