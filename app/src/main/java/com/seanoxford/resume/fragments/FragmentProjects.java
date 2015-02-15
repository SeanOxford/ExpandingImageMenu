package com.seanoxford.resume.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.seanoxford.resume.R;
import com.seanoxford.resume.apis.MyAsyncTask;


public class FragmentProjects extends Fragment {

    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        mContext = getActivity();

        TextView testText = (TextView) view.findViewById(R.id.test_text);

        Button testButton = (Button) view.findViewById(R.id.test_button);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String back = new MyAsyncTask.(new Pair<Context, String>(mContext, "woof"));
//               new MyAsyncTask().execute(new Pair<Context, String>(mContext, "woof"));
                new MyAsyncTask().execute(new Pair<Context, String>(mContext, "Manfred"));
            }
        });




        return view;
    }
}
