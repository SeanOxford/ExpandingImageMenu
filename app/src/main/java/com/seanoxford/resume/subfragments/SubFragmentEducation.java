package com.seanoxford.resume.subfragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seanoxford.resume.R;
import com.seanoxford.resume.widgets.ResumeApplication;

public class SubFragmentEducation extends Fragment {

    protected ResumeApplication mApp;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.subfragment_education, container, false);

        TextView tvSchool = (TextView) v.findViewById(R.id.tv_education_school_detail);
        TextView tvGpa = (TextView) v.findViewById(R.id.tv_education_gpa_detail);
        TextView tvDegree = (TextView) v.findViewById(R.id.tv_education_degree_detail);

        tvSchool.setTypeface(mApp.getRobotoLight());
        tvGpa.setTypeface(mApp.getRobotoLight());
        tvDegree.setTypeface(mApp.getRobotoLight());

        return v;
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
