package com.seanoxford.resume.subfragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seanoxford.resume.R;
import com.seanoxford.resume.widgets.ResumeApplication;

public class SubFragmentExperience extends Fragment {



    ResumeApplication mApp;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.subfragment_experience, container, false);

        TextView mobileSentienceDetail = (TextView) v.findViewById(R.id.tv_exp_mobilesent_detail);
        TextView responsibilitiesDetail = (TextView) v.findViewById(R.id.tv_exp_responsibilities_detail);


        mobileSentienceDetail.setTypeface(mApp.getRobotoLight());
        responsibilitiesDetail.setTypeface(mApp.getRobotoLight());


//        responsibilitiesDetail.setText(Html.fromHtml("• Lead Android UX design, vector illustration, animation, iconography and implementation<br />" +
//                "• Contributed to multithreaded local and remote database synchronization.<br />" +
//                "• Designed and developed dynamic end-user generated webpage using JSP and CSS/HTML.<br />" +
//                "• Assisted in construction of large JSON-parsing infrastructure.<br />" +
//                "• Thorough debugging, optimization and quality assurance.<br />" +
//                "• Collaborated in RFID-integrated Android App prototype, completed within seven hours.<br />" +
//                "• Produced advertisements and promotional materials.<br />"));



        responsibilitiesDetail.setText("• Lead Android UX design, vector illustration, animation, iconography and implementation\n" +
                "• Contributed to multithreaded local and remote database synchronization.\n" +
                "• Designed and developed dynamic end-user generated webpage using JSP and CSS/HTML.\n" +
                "• Assisted in construction of large JSON-parsing infrastructure.\n" +
                "• Thorough debugging, optimization and quality assurance.\n" +
                "• Collaborated in RFID-integrated Android App prototype, completed within seven hours.\n" +
                "• Produced advertisements and promotional materials.");






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
