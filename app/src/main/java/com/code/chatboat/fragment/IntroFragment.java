package com.code.chatboat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.code.chatboat.R;

// IntroFragment.java
public class IntroFragment extends Fragment {

    int position;

    public IntroFragment(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        ImageView imageView = view.findViewById(R.id.imageViewIntro);
        TextView textView = view.findViewById(R.id.textViewIntro);
        if (position == 0) {
            imageView.setImageResource(R.drawable.ic_intro_1);
            textView.setText("The average menstrual cycle lasts 28 days. The cycle starts with the first day of one period and ends with the first day of the next period.");
        } else if (position == 1) {
            imageView.setImageResource(R.drawable.ic_intro_2);
            textView.setText("View your current and future period dates");
        } else if (position == 2) {
            imageView.setImageResource(R.drawable.ic_intro_3);
            textView.setText("The resulting lack of information about menstruation leads to unhygienic and unhealthy menstrual practices");
        }
        // Set the image and text for this intro screen



        return view;
    }
}
