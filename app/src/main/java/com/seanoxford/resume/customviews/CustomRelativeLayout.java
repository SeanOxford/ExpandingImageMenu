package com.seanoxford.resume.customviews;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.seanoxford.resume.widgets.BackPressedInfoContainer;
import com.seanoxford.resume.widgets.ContractAnimation;
import com.seanoxford.resume.widgets.ExpandAnimation;

public class CustomRelativeLayout extends RelativeLayout {


    public interface IsResumeExpanded{
        public void isExpanded(BackPressedInfoContainer backPressedInfoContainer);
    }



    private static final int DEFAULT_MENU_POSITION = -1;

    public int[] mPositionsArray;
    protected int mSelectedViewPos = DEFAULT_MENU_POSITION;
    protected int mChildCount = 0;
    protected int mTotalHeight = -1;
    protected int mTotalWidth = -1;
    protected int mIndividualHeight = -1;
    protected int mHeightRemainder = 0;

    protected boolean mShouldExpand = true;
    protected boolean mConstantsInitiated = false;

    protected FragmentManager mFragmentManager = null;
    protected CustomRelativeLayout mCustomRelativeLayout;
    protected IsResumeExpanded mIsResumeExpanded;

    public CustomRelativeLayout(Context context) {
        super(context);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getHeightRemainder() {
        return mHeightRemainder;
    }

    public int getTotalHeight() {
        return mTotalHeight;
    }

    public int getIndividualHeight() {
        return mIndividualHeight;
    }

    public int getSelectedViewPosition() {
        return mSelectedViewPos;
    }

    public void setFragmentManager(FragmentManager fm) {
        mFragmentManager = fm;
    }

    public void setIsExpandedInterface(IsResumeExpanded isExpandedInterface){
        mIsResumeExpanded = isExpandedInterface;
    }

    public void addChildLayout(CustomChildLayout rl) {
        rl.setFragmentManager(mFragmentManager);
        addView(rl);
        rl.setViewPosition(mChildCount);
        mChildCount++;
    }

    private void initConstants() {
        if (!mConstantsInitiated) {
            mCustomRelativeLayout = this;
            //The distances from the top of this layout to place each sublayout
            mPositionsArray = new int[getChildCount()];

            mTotalHeight = getMeasuredHeight();
            mTotalWidth = getMeasuredWidth();
            mIndividualHeight = Math.round((float) mTotalHeight / (float) mChildCount);

            //Workaround for height issue when measuredHeight is not perfectly divisible by item count
            if (mIndividualHeight * getChildCount() != mTotalHeight) {
                mTotalHeight = mIndividualHeight * getChildCount();
                mHeightRemainder = getMeasuredHeight() - mTotalHeight;
            }

            mIndividualHeight += 1;
            mTotalHeight = mIndividualHeight * getChildCount();

            //Incremented int to set the values of increments
            int tempHeight = 0;

            for (int i = 0; i < mChildCount; i++) {
                mPositionsArray[i] = tempHeight;
                tempHeight += mIndividualHeight;
            }


            //Preventing measure calculations, accomodating for initial erroneous XML measurements
            if (mIndividualHeight == getChildAt(0).getMeasuredHeight()) {
                mConstantsInitiated = true;
            }

            //Get the value of which to increment the matrix crop
            float matrixCropPercentIncrement = (1f / (mChildCount - 1f));
            float percentOffset = 0;

            //Initialize image crop
            for (int i = 0; i < mChildCount; i++) {
                CustomChildLayout rlChild = (CustomChildLayout) getChildAt(i);

                //Iterate through child views and set where in each image the imageview should crop out
                CustomImageView civ = (CustomImageView) rlChild.getChildAt(0);
                if (civ.getPercentage() == CustomImageView.VALUE_UNSET) {
                    civ.setPercentage(percentOffset);
                    percentOffset += matrixCropPercentIncrement;
                }
            }
        }
    }

    private void initListener(final CustomChildLayout rl) {
        rl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShouldExpand)
                    onExpandClick(rl);
                else
                    onContractClick(rl);
            }
        });
    }

    public void onExpandClick(final CustomChildLayout ccl) {
        //Set Z-index to top
        ccl.bringToFront();
        mSelectedViewPos = ccl.getViewPosition();

        ExpandAnimation expandAnim = new ExpandAnimation(this, ccl, new ExpandAnimation.AnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                ccl.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                ccl.onExpanded();
                mIsResumeExpanded.isExpanded(new BackPressedInfoContainer(true, mCustomRelativeLayout, ccl));
            }
        });
        ccl.startAnimation(expandAnim);

        mShouldExpand = false;
    }

    public void onContractClick(final CustomChildLayout ccl) {
        //sets visibility gone
        ccl.onCollapse(new CustomChildLayout.CollapseFinishedListener() {
            @Override
            public void onCollapseFinish() {
                ContractAnimation contractAnim = new ContractAnimation(mCustomRelativeLayout, ccl, new ContractAnimation.Listener() {
                    @Override
                    public void onContractFinish() {
                        resetButtons();
                        ccl.requestLayout();
                        mIsResumeExpanded.isExpanded(new BackPressedInfoContainer(false, mCustomRelativeLayout, ccl));

                    }
                });
                ccl.startAnimation(contractAnim);
                mShouldExpand = true;
            }
        });

    }

    private void resetButtons() {
        //Reset selected view position
        mSelectedViewPos = DEFAULT_MENU_POSITION;

        //Create an array of the buttons
        CustomChildLayout[] layouts = new CustomChildLayout[getChildCount()];

        //Fill the array in the order of the menu default
        for (int i = 0; i < mChildCount; i++) {
            CustomChildLayout tempLayout = (CustomChildLayout) getChildAt(i);
            layouts[tempLayout.getViewPosition()] = tempLayout;
        }

        //Place them in the default order for easier z-axis
        for (CustomChildLayout layout : layouts)
            layout.bringToFront();

        requestLayout();
    }


    public void deactivateButtons() {
        for (int i = 0; i < mChildCount; i++) {
            CustomChildLayout rl = (CustomChildLayout) getChildAt(i);
            rl.setClickable(false);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initConstants();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //If at the default menu
        if (mSelectedViewPos == DEFAULT_MENU_POSITION) {
            for (int i = 0; i < mChildCount; i++) {
                CustomChildLayout rlChild = (CustomChildLayout) getChildAt(i);
                int childHeight = mIndividualHeight;

                //Set the the mIndividualHeight of each relative layout to fill this layout
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, childHeight);
                rlChild.setLayoutParams(params);

                //Initialize the buttons
                initListener(rlChild);

                //Layout the child layout within this one
                rlChild.layout(0, mPositionsArray[i], mTotalWidth, mPositionsArray[i] + childHeight);
            }
        } else /* A child layout has been selected */ {
            layoutUnselectedLayouts();
        }
    }

    private void layoutUnselectedLayouts() {
        boolean pivot = false;
        for (int j = 0; j < mChildCount - 1; j++) {
            CustomChildLayout unselectedChildLayout = (CustomChildLayout) getChildAt(j);

            int y;
            //if j is the selected view's cached default position, make all iterations incremented by one to skip it...It's complicated.
            if (j == mSelectedViewPos || pivot == true) {
                y = j + 1;
                pivot = true;
            } else {
                y = j;
            }

            unselectedChildLayout.layout(0, mPositionsArray[y], mTotalWidth, mPositionsArray[y] + mIndividualHeight);
        }
    }

}
