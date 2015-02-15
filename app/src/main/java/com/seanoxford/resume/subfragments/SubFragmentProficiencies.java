package com.seanoxford.resume.subfragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.seanoxford.resume.R;
import com.seanoxford.resume.widgets.ResumeApplication;

public class SubFragmentProficiencies extends Fragment {


    protected ResumeApplication mApp;
    TextView mWeb;
    TextView mSoftware;
    TextView mLanguages;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.subfragment_proficiencies, container, false);

        mSoftware = (TextView) v.findViewById(R.id.tv_prof_software_detail);
        mLanguages = (TextView) v.findViewById(R.id.tv_prof_languages_detail);
        mWeb = (TextView) v.findViewById(R.id.tv_prof_web_detail);

        mSoftware.setTypeface(mApp.getRobotoLight());
        mLanguages.setTypeface(mApp.getRobotoLight());
        mWeb.setTypeface(mApp.getRobotoLight());

        return v;
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = ResumeApplication.getInstance();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
