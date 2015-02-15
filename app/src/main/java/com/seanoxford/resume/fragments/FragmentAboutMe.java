package com.seanoxford.resume.fragments;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seanoxford.resume.R;
import com.seanoxford.resume.activities.ActivityMain;
import com.seanoxford.resume.activities.BaseActivity;
import com.seanoxford.resume.customviews.CustomChildLayout;
import com.seanoxford.resume.customviews.CustomRelativeLayout;
import com.seanoxford.resume.subfragments.SubFragmentContact;
import com.seanoxford.resume.subfragments.SubFragmentEducation;
import com.seanoxford.resume.subfragments.SubFragmentExperience;
import com.seanoxford.resume.subfragments.SubFragmentProficiencies;
import com.seanoxford.resume.widgets.BackPressedInfoContainer;
import com.seanoxford.resume.widgets.ResumeApplication;

public class FragmentAboutMe extends Fragment {

    public interface BackPressedListener{
        public void isResumeExpanded(BackPressedInfoContainer backPressedInfoContainer);
    }


    protected BaseActivity mParentActivity;
    protected ResumeApplication mApp;
    protected BackPressedListener mBackPressedListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mBackPressedListener = (BackPressedListener) activity;
        mApp = ResumeApplication.getInstance();
        mParentActivity = (BaseActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, null);

        TextView tvName = (TextView) v.findViewById(R.id.tv_sean_oxford);
        tvName.setTypeface(mApp.getRobotoBlack());

        TextView tvAndroidDev = (TextView) v.findViewById(R.id.tv_android_developer);
        tvAndroidDev.setText(Html.fromHtml("Android <font color=\"#2e2e2e\">Developer</font>"));

        final CustomRelativeLayout customRelativeLayout = (CustomRelativeLayout) v.findViewById(R.id.ll_about_me);
        customRelativeLayout.setIsExpandedInterface(new CustomRelativeLayout.IsResumeExpanded() {

            @Override
            public void isExpanded(BackPressedInfoContainer backPressedInfoContainer) {
                mBackPressedListener.isResumeExpanded(backPressedInfoContainer);
            }
        });
        customRelativeLayout.setFragmentManager(getChildFragmentManager());

        CustomChildLayout ccl = new CustomChildLayout(mParentActivity, "Contact", "#aa99cc00", R.drawable.contact_photo, mParentActivity.getRobotoLight(), new SubFragmentContact());
        CustomChildLayout ccl2 = new CustomChildLayout(mParentActivity, "Proficiencies", "#ccaa66cc", R.drawable.prof_photo, mParentActivity.getRobotoLight(), new SubFragmentProficiencies());
        CustomChildLayout ccl3 = new CustomChildLayout(mParentActivity, "Experience", "#bFff4444", R.drawable.exp_photo, mParentActivity.getRobotoLight(), new SubFragmentExperience());
        CustomChildLayout ccl4 = new CustomChildLayout(mParentActivity, "Education", "#aa33b5e5", R.drawable.edu_photo, mParentActivity.getRobotoLight(), new SubFragmentEducation());
        CustomChildLayout ccl5 = new CustomChildLayout(mParentActivity, "pillow", "#84007700", R.drawable.edu_photo, mParentActivity.getRobotoMedium(),  new SubFragmentEducation());
        CustomChildLayout ccl6 = new CustomChildLayout(mParentActivity, "rhino", "#70259244", R.drawable.exp_photo, mParentActivity.getRobotoMedium(), new SubFragmentEducation());
        CustomChildLayout ccl7 = new CustomChildLayout(mParentActivity, "wombat", "#70849302", R.drawable.contact_photo, mParentActivity.getRobotoMedium(), new SubFragmentEducation());
        CustomChildLayout ccl8 = new CustomChildLayout(mParentActivity, "squid", "#70849302", R.drawable.exp_photo, mParentActivity.getRobotoMedium(), new SubFragmentEducation());


        ccl4.alignImageBottomOnResize(true);

        customRelativeLayout.addChildLayout(ccl);
        customRelativeLayout.addChildLayout(ccl2);
        customRelativeLayout.addChildLayout(ccl3);
        customRelativeLayout.addChildLayout(ccl4);
//        customRelativeLayout.addChildLayout(ccl5);
//        customRelativeLayout.addChildLayout(ccl6);
//        customRelativeLayout.addChildLayout(ccl7);
//        view.addChildLayout(ccl8);


        return v;
    }
}
