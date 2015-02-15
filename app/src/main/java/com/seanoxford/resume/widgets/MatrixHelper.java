package com.seanoxford.resume.widgets;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;

import com.seanoxford.resume.customviews.CustomImageView;

public class MatrixHelper {

    protected CustomImageView mCustomImageView;
    protected int mViewWidth = -1;
    protected float mScale = -1;
    protected int mDrawableWidth = -1;
    protected int mDrawableHeight = -1;
    protected float mViewToDrawableWidth = -1;
    protected float mXOffset;
    protected float mWidthPercent = -1;
    protected float mHeightPercent = -1;
    protected int mMaxHeight = 0;
    protected int mHeightDeltaOffset = 0;

    public MatrixHelper(CustomImageView customImageView, float percent, int maxHeight, int heightDeltaOffset){
        mHeightDeltaOffset = heightDeltaOffset;
        mCustomImageView = customImageView;
        mHeightPercent = percent;
        mMaxHeight = maxHeight;
    }

    public Matrix matrixIt(){
        Matrix matrix = mCustomImageView.getImageMatrix();

        if(mViewWidth == -1)
            mViewWidth = mCustomImageView.getWidth() - mCustomImageView.getPaddingLeft() - mCustomImageView.getPaddingRight();
        int viewHeight = mCustomImageView.getHeight() - mCustomImageView.getPaddingTop() - mCustomImageView.getPaddingBottom();

        if(mDrawableWidth == -1)
            mDrawableWidth = mCustomImageView.getDrawable().getIntrinsicWidth();

        if(mDrawableHeight == -1)
            mDrawableHeight = mMaxHeight;

        if(mScale == -1)
            if (mDrawableWidth * viewHeight > mDrawableHeight * mViewWidth)
                mScale = (float) viewHeight / (float) mDrawableHeight;
            else
                mScale = (float) mViewWidth / (float) mDrawableWidth;

        mScale = 1;


        if(mViewToDrawableWidth == -1)
            mViewToDrawableWidth = mViewWidth / mScale;
        float viewToDrawableHeight = Math.round(viewHeight / mScale);

        if(mXOffset == -1)
            mXOffset = mWidthPercent * (mDrawableWidth - mViewToDrawableWidth);
        float yOffset = Math.round((mHeightPercent * (mDrawableHeight - viewToDrawableHeight)) + mHeightDeltaOffset);

        RectF drawableRect = new RectF(mXOffset, yOffset, mXOffset + mViewToDrawableWidth,
                yOffset + viewToDrawableHeight);

        RectF viewRect = new RectF(0, 0, mViewWidth, viewHeight);
        matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.FILL);

        return matrix;
    }

}
