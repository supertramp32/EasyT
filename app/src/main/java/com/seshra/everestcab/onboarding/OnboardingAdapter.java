package com.seshra.everestcab.onboarding;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class OnboardingAdapter extends FragmentPagerAdapter {

    public OnboardingAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return OnboardingFragment.newInstance(position);
            case 1:
                return OnboardingFragment.newInstance(position);
            case 2:
                return OnboardingFragment.newInstance(position);
            case 3:
                return OnboardingFragment.newInstance(position);
            default:
                return OnboardingFragment.newInstance(position);
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
