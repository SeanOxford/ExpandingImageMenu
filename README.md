# ExpandingImageMenu
(Last Updated in 2014)

This library allows for a cropped-image menu to expand into fragments/layouts for a professional look and feel. 

Potato-quality demo gif:

![Alt text](https://github.com/SeanOxford/ExpandingImageMenu/blob/master/images/demo.gif?raw=true)


## Usage

### 1.)

Place the CustomRelativeLayout into an XML file wherever you'd like.

		<?xml version="1.0" encoding="utf-8"?>
		<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
			android:layout_width="match_parent"
			android:layout_height="match_parent">

			<TextView
				android:layout_width="wrap_content"
				android:layout_height="wrap_content"
				android:layout_marginLeft="5dp"
				android:id="@+id/tv_sean_oxford"
				android:textSize="45sp"
				android:includeFontPadding="false"
				android:textColor="#2e2e2e"
				android:text="Sean Oxford" />

			<TextView
				android:layout_marginTop="-10dp"
				android:layout_marginBottom="16dp"
				android:layout_below="@id/tv_sean_oxford"
				android:layout_alignEnd="@id/tv_sean_oxford"
				android:layout_width="wrap_content"
				android:layout_height="wrap_content"
				android:id="@+id/tv_android_developer"
				android:textSize="18sp"
				android:textColor="#8cac33"
				android:text="Android Developer" />


			<com.seanoxford.expandingimagemenu.views.CustomRelativeLayout
				android:layout_below="@id/tv_android_developer"
				android:id="@+id/ll_about_me"
				android:layout_alignParentBottom="true"
				android:layout_width="match_parent"
				android:splitMotionEvents="false"
				android:layout_height="match_parent">

			</com.seanoxford.expandingimagemenu.views.CustomRelativeLayout>
		</RelativeLayout>


### 2.)


In your activity or fragment, reference the CustomRelativeLayout and manually set its fragment manager (or child fragment manager, if nesting fragments).


        CustomRelativeLayout customRelativeLayout = (CustomRelativeLayout) findViewById(R.id.ll_about_me);
        customRelativeLayout.setFragmentManager(getSupportFragmentManager());
		

### 3.)

Create your ChildLayoutViews, passing in context, title, overlay color (minding transparency), bitmap image background as a drawable, font and fragment/xmlLayout (both supported). It can support as many ChildViews as necessary.


        CustomChildLayout cclContact = new CustomChildLayout(mParentActivity, "Contact", "#aa99cc00", R.drawable.contact_photo, mParentActivity.getRobotoLight(), new SubFragmentContact());
        CustomChildLayout cclProf = new CustomChildLayout(mParentActivity, "Proficiencies", "#ccaa66cc", R.drawable.prof_photo, mParentActivity.getRobotoLight(), new SubFragmentProficiencies());
        CustomChildLayout cclExp = new CustomChildLayout(mParentActivity, "Experience", "#bFff4444", R.drawable.exp_photo, mParentActivity.getRobotoLight(), new SubFragmentExperience());
        CustomChildLayout cclEdu = new CustomChildLayout(mParentActivity, "Education", "#aa33b5e5", R.drawable.edu_photo, mParentActivity.getRobotoLight(), new SubFragmentEducation());	



### 4.)

Add each CustomChildLayout to the CustomRelativeLayout.


        customRelativeLayout.addChildLayout(cclContact);
        customRelativeLayout.addChildLayout(cclProf);
        customRelativeLayout.addChildLayout(cclExp);
        customRelativeLayout.addChildLayout(cclEdu);



## Settings and Support

* Images are set by default to expand or contract to the entire relative layout's size while keeping the aspect ratio, which can cause some cropping. This can be disabled via calling setScaleToFill(false) on a CustomChildLayout before it is added to the CustomRelativeLayout.

* Images are also aligned to the top, when they are expanded/contracted in the fill feature. This can sometimes be undesirable when the bottom portion of an image's content is important to presentation. This can be fixed by calling alignImageBottomOnResize(true) on a CustomChildLayout before it is added to the CustomRelativeLayout.


## Developed By

	Sean Oxford - oxford.sean@gmail.com


##License

    Copyright 2015 Sean Oxford

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

