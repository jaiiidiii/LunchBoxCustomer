package com.jayzonsolutions.LunchBox.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jayzonsolutions.LunchBox.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.ic_message,
            R.drawable.ic_message,
            //    R.drawable.ic_message
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //return inflater.inflate(R.layout.fragment_message,container,false);
        //   return super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_message, container, false);

        viewPager = v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        return v;

    }

    private void setupViewPager(ViewPager viewPager) {
        //   ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        ViewPagerAdapter adapter = new ViewPagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager());
        adapter.addFragment(new DishesFragment(), "DISHES");
        adapter.addFragment(new TwoFragment(), "CATEGORY");

        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        //  Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[2]);

        //      tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        //    tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        //  tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
