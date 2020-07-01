package com.seshra.everestcab.utils;



import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 30/05/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();
    private final List<Integer> tabNo = new ArrayList<>();

    public  static int tabPostion;


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentTitleList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return fragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title){

        fragmentList.add(fragment);
        fragmentTitleList.add(title);


    }

    public void addFragment(Fragment fragment, String title, int tabno) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
        tabNo.add(tabno);
        tabPostion = tabno;
    }
}
