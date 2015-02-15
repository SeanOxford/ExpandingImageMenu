package com.seanoxford.resume.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.seanoxford.resume.widgets.MatrixHelper;

public class CustomImageView extends ImageView {

    public static final int VALUE_UNSET = -1;

    protected boolean mHasBeenResized = false;
    protected boolean mAlignImageBottom = false;
    protected boolean mScaleToFill = true;
    protected int mHeightDeltaOffset = 0;
    protected int mParentOrder = VALUE_UNSET;
    protected float mHeightPercent = VALUE_UNSET;

    protected String mHexColorOverlay;
    protected MatrixHelper mMatrixHelper = null;
    protected Context mContext;
    protected CustomRelativeLayout mGrandParentLayout;
    protected CustomChildLayout mParentLayout;


    public void setParentOrder(int order) {
        mParentOrder = order;
    }

    public void alignImageBottomOnResize(boolean alignBottom){
        mAlignImageBottom = alignBottom;
    }

    public int getParentOrder() {
        return mParentOrder;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setScaleToFill(boolean scaleToFill){
        mScaleToFill = scaleToFill;
    }

    public void setPercentage(float percent) {
        mHeightPercent = percent;
    }

    public float getPercentage() {
        return mHeightPercent;
    }

    public void setColor(String color) {
        mHexColorOverlay = color;
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomImageView(Context context) {
        super(context);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mHexColorOverlay != null)
            canvas.drawColor(Color.parseColor(mHexColorOverlay));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mGrandParentLayout = (CustomRelativeLayout) getParent().getParent();
        mParentLayout = (CustomChildLayout) getParent();
        if (!mHasBeenResized && mScaleToFill)
            scaleToFill();

        setMatrix();
        super.onLayout(changed, l, t, r, b);
    }

    private void scaleToFill() {
        //Grow bitmap to match_parent if resource isn't large enough; Toggleable.
        Bitmap bm = ((BitmapDrawable) getDrawable()).getBitmap();
        int bitmapHeight = bm.getHeight();
        int bitmapWidth = bm.getWidth();
        int parentHeight = mGrandParentLayout.getTotalHeight();
        int parentWidth = mGrandParentLayout.getMeasuredWidth();

        int widthDelta = parentWidth - bitmapWidth;
        int heightDelta = parentHeight - bitmapHeight;

        if (widthDelta > 0 || heightDelta > 0) {
            int newWidth = bitmapWidth;
            int newHeight = bitmapHeight;

            if (widthDelta > 0 && heightDelta > 0) {
                if (widthDelta > heightDelta) {
                    newWidth = parentWidth;
                    newHeight = (parentWidth / bitmapWidth) * parentHeight;
                } else {
                    newHeight = parentHeight;
                    newWidth = (parentHeight / bitmapHeight) * parentWidth;
                }
            } else if (widthDelta > 0) {
                newWidth = parentWidth;
                newHeight = (parentWidth / bitmapWidth) * bitmapHeight;
            } else if (heightDelta > 0) {
                newHeight = parentHeight;
                newWidth = (parentHeight / bitmapHeight) * bitmapWidth;
            }
            setImageBitmap(Bitmap.createScaledBitmap(bm, newWidth, newHeight, true));
        } else if(widthDelta < 0 || heightDelta < 0) {
                int newWidth = parentWidth;
                int newHeight = parentHeight;

                if (widthDelta < 0 && heightDelta < 0) {
                    if (widthDelta > heightDelta) {
                        if(mAlignImageBottom)
                            mHeightDeltaOffset = heightDelta * -1;
                        newWidth = parentWidth;
                        newHeight = (bitmapWidth / parentWidth) * bitmapHeight;
                    } else {
                        newHeight = parentHeight;
                        newWidth = (bitmapHeight / parentHeight) * bitmapWidth;
                    }
                } else if (widthDelta < 0) {
                    if(mAlignImageBottom)
                        mHeightDeltaOffset = heightDelta * -1;
                    newWidth = parentWidth;
                    newHeight = (bitmapWidth / parentWidth) * bitmapHeight;
                } else if (heightDelta < 0) {
                    newHeight = parentHeight;
                    newWidth = (bitmapHeight / parentHeight) * bitmapWidth;
                }

                setImageBitmap(Bitmap.createScaledBitmap(bm, newWidth, newHeight, true));
        }
        mHasBeenResized = true;
    }

    public void setMatrix() {
        if (mMatrixHelper == null) {
            //Used to cache some values for the matrix calculations
            mMatrixHelper = new MatrixHelper(this, mHeightPercent, mGrandParentLayout.getTotalHeight(), mHeightDeltaOffset);
        }
        setImageMatrix(mMatrixHelper.matrixIt());
    }

}