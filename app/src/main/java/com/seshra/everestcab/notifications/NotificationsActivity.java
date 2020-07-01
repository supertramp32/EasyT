package com.seshra.everestcab.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.seshra.everestcab.R;
import com.seshra.everestcab.utils.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class NotificationsActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        viewPager = findViewById(R.id.viewPager);

        tabLayout =findViewById(R.id.tabLayoutNotifications);

        toolbar = findViewById(R.id.toolbarNotifications);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GeneralNotificationFragment(), "General");
        adapter.addFragment(new CampaignNotificationFragment(), "Campaigns");

        viewPager.setCurrentItem(0);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}
