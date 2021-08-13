package com.devina.weatherapplication.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.devina.weatherapplication.ui.fragment.CurrentDayFragment;
import com.devina.weatherapplication.ui.fragment.NextDaysFragment;

public class HomeTabsAdapter extends FragmentPagerAdapter {

    int totalTabs;
    Context context;

    public HomeTabsAdapter(Context context, FragmentManager fm, int totalTabs)
    {
        super(fm);
        this.context=context;
        this.totalTabs=totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {

        switch (i)
        {
            case 0:
                CurrentDayFragment currentDayFragment=new CurrentDayFragment();
                return currentDayFragment;
            case 1:
                NextDaysFragment nextDaysFragment=new NextDaysFragment();
                return nextDaysFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
