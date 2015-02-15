package com.seanoxford.resume.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seanoxford.resume.R;
import com.seanoxford.resume.widgets.ResumeApplication;

/**
 * Created by labtob on 1/30/2015.
 */
public class CustomTextView extends TextView {

    final private static int SIZING_DEFAULT = -1;
    final private static int SIZING_FULL = 0;
    final private static int SIZING_HALF = 1;
    ResumeApplication mApp = ResumeApplication.getInstance();

    protected int mSizingAction = SIZING_DEFAULT;


    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView, 0, 0);



        try {
            mSizingAction = a.getInteger(R.styleable.CustomTextView_textSizing, -1);

        } finally {
            a.recycle();
        }


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        resizeText();
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


    }

    private void resizeText(){
        switch (mSizingAction) {
            case SIZING_DEFAULT:
                break;
            case SIZING_FULL:
                setTextSize(TypedValue.COMPLEX_UNIT_SP, pixelsToSp(getHeight() - 3));
                break;
            case SIZING_HALF:
                setTextSize(TypedValue.COMPLEX_UNIT_SP, pixelsToSp(getHeight()) * .4f);
                break;
            default:
                break;
        }
    }

    public float pixelsToSp(float px) {
        return px / getContext().getResources().getDisplayMetrics().scaledDensity;
    }
}
