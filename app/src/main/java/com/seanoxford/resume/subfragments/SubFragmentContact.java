package com.seanoxford.resume.subfragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.seanoxford.resume.R;
import com.seanoxford.resume.widgets.ResumeApplication;

public class SubFragmentContact extends Fragment {

    protected ResumeApplication mApp;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.subfragment_contact, container, false);

        TextView tvAddress = (TextView) v.findViewById(R.id.tv_contact_address_detail);
        TextView tvPhone = (TextView) v.findViewById(R.id.tv_contact_phone_detail);
        TextView tvPhoneDetail = (TextView) v.findViewById(R.id.tv_contact_phone_detail);
        TextView tvEmail = (TextView) v.findViewById(R.id.tv_contact_email_detail);
        TextView tvEmailDetail = (TextView) v.findViewById(R.id.tv_contact_email_detail);

        tvAddress.setTypeface(mApp.getRobotoLight());
        tvPhone.setTypeface(mApp.getRobotoLight());
        tvEmail.setTypeface(mApp.getRobotoLight());

        tvPhoneDetail.setPaintFlags(tvEmailDetail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvPhoneDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToContacts();
            }
        });

        tvEmailDetail.setPaintFlags(tvEmailDetail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvEmailDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = ResumeApplication.getInstance();
    }

    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "oxford.sean@gmail.com", null));
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void addToContacts(){
        AlertDialog.Builder addContactsDialog = new AlertDialog.Builder(getActivity());
        addContactsDialog.setTitle("Add to Contacts");
        addContactsDialog.setMessage("Would you like to add me to your contacts?");
        addContactsDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

                intent.putExtra(ContactsContract.Intents.Insert.NAME, "Sean Oxford - Android Developer");
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, "760-504-7920");
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, "oxford.sean@gmail.com");

                startActivity(intent);
            }
        });
        addContactsDialog.setNegativeButton(android.R.string.no, null);

        addContactsDialog.show();


    }
}
