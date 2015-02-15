package com.seanoxford.resume.widgets;

import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seanoxford.resume.customviews.CustomChildLayout;
import com.seanoxford.resume.customviews.CustomImageView;
import com.seanoxford.resume.customviews.CustomRelativeLayout;

public class ExpandAnimation extends Animation {

    public interface AnimationEndListener{
        void onAnimationEnd();
    }

    protected int[] mTopDimensions;
    protected int mTotalHeight;
    protected int mIndividualHeight;
    protected int mPreviousHeight = 0;
    protected int mRemainderOffset;

    protected float mIncrementedUpwardTransition;
    protected float mIncrementedDownwardTransition;
    protected float mUpwardDivisor = 0;
    protected float mDownwardDivisor = 0;

    protected boolean mToggle = true;
    protected boolean mIsMiddleView = false;

    protected CustomRelativeLayout mCustomRelativeLayout;
    protected CustomChildLayout mCustomChildLayout;
    protected CustomImageView mCustomImageView;
    protected TextView mSelectedLayoutTitle;



    protected AnimationEndListener mListener;

    public ExpandAnimation(CustomRelativeLayout customRelativeLayout, CustomChildLayout customChildLayout, AnimationEndListener listener){
        mListener = listener;
        mCustomRelativeLayout = customRelativeLayout;
        mCustomChildLayout = customChildLayout;
        mCustomImageView = (CustomImageView) mCustomChildLayout.getChildAt(CustomChildLayout.CUSTOMIMAGEVIEW_POSITION);
        mSelectedLayoutTitle = (TextView) mCustomChildLayout.getChildAt(CustomChildLayout.TITLE_POSITION);
        mTotalHeight = mCustomRelativeLayout.getTotalHeight();
        mIndividualHeight = mCustomRelativeLayout.getIndividualHeight();

        if(mCustomRelativeLayout.getChildCount() % 2 == 1)
            if(mCustomChildLayout.getViewPosition() == mCustomRelativeLayout.getChildCount() / 2)
                mIsMiddleView = true;


        if(mCustomChildLayout.getViewPosition() != 0)
            mUpwardDivisor =  ((float) (mCustomRelativeLayout.getChildCount() - 1) / (float) mCustomChildLayout.getViewPosition());

        if(mCustomChildLayout.getViewPosition() != mCustomRelativeLayout.getChildCount() - 1)
            mDownwardDivisor = ((float) (mCustomRelativeLayout.getChildCount() - 1) / (float) (mCustomRelativeLayout.getChildCount() - 1 - mCustomChildLayout.getViewPosition()));

        mTopDimensions = customRelativeLayout.mPositionsArray;
        mPreviousHeight = mCustomChildLayout.getMeasuredHeight();
        setFillEnabled(true);
        setFillAfter(true);
        setDuration(600);
        setInterpolator(new AccelerateDecelerateInterpolator());
        setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mCustomRelativeLayout.deactivateButtons();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCustomChildLayout.setClickable(true);
                mPreviousHeight = mCustomChildLayout.getMeasuredHeight();
                mIncrementedDownwardTransition = 0f;
                mIncrementedUpwardTransition = 0f;
                mListener.onAnimationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int totalGrowth = mTotalHeight - mIndividualHeight + mCustomRelativeLayout.getHeightRemainder();
        int currentHeight = mIndividualHeight + Math.round(totalGrowth * interpolatedTime);
        int heightDelta =  currentHeight - mPreviousHeight;

        float upwardTransitionIncrement = mCustomChildLayout.getViewPosition() != 0 ? (float) heightDelta / mUpwardDivisor : 0;
        float downwardTransitionIncrement = mCustomChildLayout.getViewPosition() != mCustomRelativeLayout.getChildCount() - 1 ? (float) heightDelta / mDownwardDivisor : 0;

        mIncrementedUpwardTransition -= upwardTransitionIncrement;
        mIncrementedDownwardTransition += downwardTransitionIncrement;

        if(interpolatedTime == 1) {
            mCustomChildLayout.layout(0, 0, mCustomChildLayout.getMeasuredWidth(), mCustomRelativeLayout.getTotalHeight());
            mCustomImageView.layout(0, 0, mCustomChildLayout.getMeasuredWidth(), mCustomRelativeLayout.getTotalHeight());
        } else {
            int topIncrement;
            int bottomIncrement;

            //When there is an odd number of items on the screen, the center one has an issue in expansion calculation which makes the animation jerky.
            //Alternating between rounding and flooring the floats smooths this out
            if(!mIsMiddleView){
                topIncrement = Math.round(mIncrementedUpwardTransition);
                bottomIncrement = Math.round(mIncrementedDownwardTransition);
            } else if(!mToggle && mIsMiddleView) {
                topIncrement = Math.round(mIncrementedUpwardTransition);
                bottomIncrement = (int) mIncrementedDownwardTransition;
                mToggle = true;
            } else {
                topIncrement = (int) mIncrementedUpwardTransition;
                bottomIncrement = Math.round(mIncrementedDownwardTransition);
                mToggle = false;
            }

            mCustomChildLayout.layout(0, mTopDimensions[mCustomChildLayout.getViewPosition()] + topIncrement, mCustomChildLayout.getMeasuredWidth(), mTopDimensions[mCustomChildLayout.getViewPosition()] + mCustomChildLayout.getMeasuredHeight() + bottomIncrement + mRemainderOffset);
            mCustomImageView.layout(0, 0, mCustomChildLayout.getMeasuredWidth(), mCustomChildLayout.getMeasuredHeight() - topIncrement + bottomIncrement + mRemainderOffset);
        }
        mPreviousHeight = currentHeight;
    }
}
