package com.seshra.everestcab.rides;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.seshra.everestcab.R;
import com.seshra.everestcab.utils.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class YourRideActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_ride);

        viewPager = findViewById(R.id.viewPager);

        tabLayout =findViewById(R.id.tabLayoutYourRides);

        toolbar = findViewById(R.id.toolbarYourRide);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ActiveRideFragment(), "Active Rides");
        adapter.addFragment(new PastRideFragment(), "Past Rides");

        viewPager.setCurrentItem(0);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }
}
