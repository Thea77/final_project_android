package com.example.afinal;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

    Context context;
    int totalTab;

    public MyViewPagerAdapter(@NonNull FragmentManager fm, Context context, int totalTab) {
        super(fm);
        this.context = context;
        this.totalTab = totalTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                  return new NewFeedFragment();
            case 1:
                return new ProfileFragment();
//            case 2:
//                return new CallFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTab;
    }
}
