package com.example.android.mobilecourse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabsAdapter tabsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.main_activity_view_pager);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager(), getLifecycle());

        viewPager.setAdapter(tabsAdapter);

        final String[] tabTitles = new String[]{"Robots", "Tab 2", "Profile"};
        final TabLayout tabLayout = findViewById(R.id.main_activity_tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();
    }
}