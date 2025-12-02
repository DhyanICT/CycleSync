package com.code.chatboat.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.code.chatboat.fragment.IntroFragment;

// IntroPagerAdapter.java
public class IntroPagerAdapter extends FragmentStateAdapter {

    public IntroPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Create and return the appropriate fragment for the given position
        return new IntroFragment(position);
    }

    @Override
    public int getItemCount() {
        // Return the total number of intro screens
        return 3;
    }
}
