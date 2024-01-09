package com.amtechsolutions.becpbas.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.amtechsolutions.becpbas.R;
import com.amtechsolutions.becpbas.adapter.VPAdapter;
import com.amtechsolutions.becpbas.fragment.EmployeeFragment;
import com.amtechsolutions.becpbas.fragment.SupervisorFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LoginActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private VPAdapter vpAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#05143a"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        initView();
    }
    private void initView()
    {
        viewPager2 = (ViewPager2)findViewById(R.id.view_pager);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        vpAdapter = new VPAdapter(getSupportFragmentManager(), getLifecycle());
        vpAdapter.addFragment(new SupervisorFragment(), "Supervisor");
        vpAdapter.addFragment(new EmployeeFragment(), "Employee");
        viewPager2.setAdapter(vpAdapter);
        viewPager2.setOffscreenPageLimit(1);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {

            tab.setText(vpAdapter.fragmentTitle.get(position));
        }).attach();
        for(int i = 0; i < tabLayout.getTabCount(); i++)
        {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }
}